package com.mytiki.sdk.capture.receipt.capacitor

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mytiki.capture_receipt.example.Input
import com.mytiki.sdk.capture.receipt.capacitor.account.AccountCommon
import com.mytiki.sdk.capture.receipt.capacitor.ui.theme.CaptureReceiptTheme
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import java.util.UUID

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var errorOutput by remember {
                mutableStateOf("")
            }
            var loginOutput by remember {
                mutableStateOf("")
            }
            var accountsOutput by remember {
                mutableStateOf("")
            }
            var username by remember {
                mutableStateOf("")
            }
            var password by remember {
                mutableStateOf("")
            }

            fun onError(error: Throwable) {
                errorOutput = ""
                loginOutput = ""
                accountsOutput = ""
                error.message?.let { errorOutput = it }
            }


            CaptureReceipt.config(config) { onError(it) }
            CaptureReceipt.initialize(
                UUID.randomUUID().toString(),
                this@MainActivity
            ) { onError(it) }
            CaptureReceiptTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 15.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(40.dp))
                        Text(
                            text = "Tiki Example",
                        )
                        if (errorOutput.isNotBlank()) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = errorOutput,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Justify
                            )
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        Input(
                            tile = "Email",
                            text = username,
                            isShow = true,
                            onChange = { username = it })
                        Spacer(modifier = Modifier.height(20.dp))
                        Input(
                            tile = "Password",
                            text = password,
                            isShow = false,
                            onChange = { password = it })
                        if (loginOutput.isNotBlank()) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = loginOutput,
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Justify
                            )
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        MainButton(text = "Scan Physical") {
                            errorOutput = ""
                            loginOutput = ""
                            accountsOutput = ""
                            CaptureReceipt.scan(this@MainActivity)
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        MainButton(text = "Scan Email") {
                            errorOutput = ""
                            loginOutput = ""
                            accountsOutput = ""
                            CaptureReceipt.login(
                                this@MainActivity,
                                username,
                                password,
                                AccountCommon.GMAIL,
                                {loginOutput = it.toString()},
                                {errorOutput = it}
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        MainButton(text = "Scan Retailer") {

                        }
                        if (loginOutput.isNotBlank()) {
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = accountsOutput,
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Justify
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        MainButton(text = "Accounts") {
                            MainScope().async {
                                accountsOutput = CaptureReceipt.accounts(this@MainActivity) { errorOutput = it }.await().toString()
                            }
                        }
                    }
                }
            }
        }
    }
}

val config = Configuration(
    "be19730a-00d5-45f5-b18e-2e19eb25f311",
    "sRwAAAAoY29tLm15dGlraS5zZGsuY2FwdHVyZS5yZWNlaXB0LmNhcGFjaXRvcgY6SQlVDCCrMOCc/jLI1A3BmOhqNvtZLzShMcb3/OLQLiqgWjuHuFiqGfg4fnAiPtRcc5uRJ6bCBRkg8EsKabMQkEsMOuVjvEOejVD497WkMgobMbk/X+bdfhPPGdcAHWn5Vnz86SmGdHX5xs6RgYe5jmJCSLiPmB7cjWmxY5ihkCG12Q==",
    "wSNX3mu+YGc/2I1DDd0NmrYHS6zS1BQt2geMUH7DDowER43JGeJRUErOHVwU2tz6xHDXia8BuvXQI3j37I0uYw==",
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus lobortis risus ac ultrices faucibus. Nullam vel pulvinar neque. Morbi ultrices maximus est, quis blandit urna vestibulum nec. Morbi et finibus nisi. Vestibulum dignissim rutrum mi sit amet sagittis. Aenean id ligula eget enim feugiat luctus vitae vitae orci. Maecenas aliquam semper nunc vel pellentesque. Ut cursus neque non est mattis consequat. Duis posuere odio et tellus aliquam, et tristique erat pharetra. Mauris sollicitudin lorem ligula. Ut lacinia, neque ac ornare gravida, libero turpis fermentum nibh, eget sodales diam magna sit amet lacus. Aliquam pretium suscipit mi eget luctus. Aliquam ut velit ut magna elementum sollicitudin in et magna. Ut a elementum tellus, eu cursus lacus. Pellentesque neque nisi, semper ac mi vel, fringilla semper nisl. Morbi at vulputate lectus, non ornare nulla." +
            "Vestibulum convallis rutrum tellus sed vulputate. Suspendisse condimentum mauris quis odio aliquet, at posuere augue egestas. Nulla finibus nibh ac placerat pretium. Mauris volutpat urna sit amet vehicula fermentum. Praesent semper est diam, sit amet elementum orci luctus ac. Quisque condimentum ipsum in venenatis rutrum. Donec rutrum nisl id elit porttitor, vel scelerisque quam ultricies. Donec vulputate, mi at tempor hendrerit, risus tortor consequat neque, non laoreet orci ante tempor dolor. Curabitur placerat convallis risus, a facilisis diam mollis in." +
            "Mauris in ex dolor. Nunc eu mollis mi. Integer ut nulla egestas, finibus tellus in, congue sem. Vestibulum sit amet velit cursus, consequat purus id, porttitor ligula. Aliquam pellentesque non augue quis tincidunt. Duis a pulvinar odio, non ultrices metus. Sed eu risus quam. Nam vehicula ligula id aliquet aliquet. Quisque faucibus odio pulvinar tellus tristique, eget tempus tellus accumsan. Nulla vehicula nunc quis dapibus lobortis. Sed urna magna, commodo vitae enim eget, scelerisque hendrerit mi. Pellentesque lobortis lectus vitae convallis facilisis." +
            "Phasellus lobortis purus sit amet sodales efficitur. Mauris sapien lorem, pretium id turpis eu, tristique maximus tellus. Donec porttitor, enim ut scelerisque dapibus, lectus tellus laoreet ante, a ornare dolor nisi sed risus. Vestibulum facilisis mollis urna in suscipit. Pellentesque sit amet lobortis nulla. Fusce semper rhoncus urna a gravida. In congue nec nisi eu hendrerit. Donec sed felis elementum lacus posuere porttitor eget quis dolor. Maecenas eu iaculis dolor. Nam venenatis tempor velit vel finibus. Phasellus purus nunc, condimentum sit amet porttitor nec, rhoncus et ante. Fusce tristique nibh quis sem varius ultricies. Maecenas egestas justo sed enim maximus consectetur." +
            "Phasellus malesuada magna a ex mollis varius. Quisque a vulputate metus. Cras in nibh lorem. Proin in enim efficitur, pellentesque elit sed, dictum turpis. Duis sagittis lectus eu magna imperdiet maximus. Nullam condimentum scelerisque arcu ac auctor. Phasellus malesuada erat quis gravida mollis.",
)
