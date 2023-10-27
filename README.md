# Capture Receipt SDK - Android

## Introduction

TIKI Capture Receipt SDK is designed to empower users to capture and license their purchase data from various sources, including scanning physical receipts, scraping email accounts, and connecting online retailer accounts. All data collected is considered zero-party data, making it legally owned by end-users and licensed to businesses in exchange for fair compensation.

With this SDK, companies can easily integrate data extraction from receipts, manage data property and licensing, and seamlessly publish data to the TIKI platform.

Receipt parsing is handled on-device, ensuring security, privacy, and compliance with App Store and Play Store regulations. This process is facilitated by the closed-source, licensed SDK [Microblink](https://microblink.com). For new customers, we offer a **free Microblink license**. Schedule a meeting at [mytiki.com](https://mytiki.com) to acquire a license key.

Raw receipt data is securely stored in a hosted [Iceberg](http://iceberg.apache.org) cleanroom, allowing you to query, ETL (Extract, Transform, Load), and train models against it. A sample cleanroom can be found in our [purchase repository](https://github.com/tiki/purchase).

## Features

### 1. Get Purchase Data from Physical and Digital Receipts

Capture Receipt seamlessly integrates with the [Microblink](https://microblink.com/) platform to extract data from both physical and digital receipts. This versatile feature ensures that you can collect information from a wide variety of sources.

### 2. Handle Data Licensing and Compensation with TIKI

Leverage TIKI to efficiently manage data licensing and compensation. Utilize TIKI's data infrastructure to set up licensing agreements, define terms, and ensure fair compensation for your users.

### 3. Publish Data to TIKI

Once users have captured and licensed their data, it is seamlessly published to the TIKI platform. This feature streamlines the process of making the data accessible to a wider audience and facilitates data monetization.

## Getting Started

### Prerequisites

Before getting started, ensure your Android project meets the following requirements:

- `compileSdkVersion` should be equal to or greater than 33.
- `targetSdkVersion` should be equal to or greater than 33.

### 1. Add Capture Receipt SDK Dependency

To integrate the Capture Receipt SDK into your project, add the following dependency to your Gradle build file:

```gradle
implementation "com.mytiki:capture-receipt:0.0.1"
```

### 2. Add Microblink Repository

As Microblink is closed-source, you need to add the Maven endpoint to your project's `build.gradle` or `settings.gradle` file:

```gradle
allprojects {
  repositories {
    // other repositories
    maven { url "https://maven.microblink.com" }
  }
}
```

### 3. Set Packaging Options

Depending on your project's configuration, you may need to add the following `packagingOptions` to your app's `build.gradle` file:

```gradle
android {
    //... other Android build configurations
    
    packagingOptions {
        exclude("META-INF/LICENSE-notice.md")
        exclude("META-INF/LICENSE.md")
        exclude("META-INF/NOTICE.md")
    }
}
```

### Initialization

During SDK initialization, Capture Receipt initializes the TIKI and Microblink SDKs and creates a License Record for the data provided by users. You need to provide the company's information and the API keys for TIKI and Microblink. Here's how you can initialize the SDK in both Kotlin and Java:

**Kotlin:**

```kotlin
CaptureReceipt.initialize(
    "YOUR USER ID",
    Config(
        Company(
            "Company Inc.",
            "Tennessee, USA",
            "https://your-co.com/privacy",
            "https://your-co.com/terms",
        ),
        Key(
            "YOUR TIKI PUBLISHING ID",
            "YOUR MICROBLINK ANDROID LICENSE KEY",
            "YOUR MICROBLINK PRODUCT INTELLIGENCE KEY",
        )
    )
)
```

**Java:**

```java
CaptureReceipt.initialize(
    "YOUR USER ID",
    new Config(
        new Company(
            "Company Inc.",
            "Tennessee, USA",
            "https://your-co.com/privacy",
            "https://your-co.com/terms"
        ),
        new Key(
            "YOUR TIKI PUBLISHING ID",
            "YOUR MICROBLINK ANDROID LICENSE KEY",
            "YOUR MICROBLINK PRODUCT INTELLIGENCE KEY"
        )
    )
);
```

Now, users can provide their receipt data, and the SDK will handle the licensing and publishing of it.

### Initialize Gmail and/or Outlook APIs

Capture Receipt utilizes IMAP for email scraping as the default method. For an enhanced user experience and improved accuracy, we recommend considering the use of the [Gmail API](https://developers.google.com/gmail/api) and [Outlook API](https://docs.microsoft.com/en-us/outlook/rest/overview) for email scraping. The utilization of these APIs is optional, and you have the flexibility to choose either one, or both.

**Kotlin:**

```kotlin
CaptureReceipt.initialize(
    "THE USER ID",
    Config(
        Company(
            "Company Inc.",
            "Tennessee, USA",
            "https://your-co.com/privacy",
            "https://your-co.com/terms",
        ),
        Key(
            "YOUR TIKI PUBLISHING ID",
            "YOUR MICROBLINK ANDROID LICENSE KEY",
            "YOUR MICROBLINK IOS LICENSE KEY",
            "YOUR MICROBLINK PRODUCT INTELLIGENCE KEY"
        )
    )
    .gmail("YOUR GMAIL API KEY")
    .outlook("YOUR OUTLOOK API KEY")
)
```

**Java:**

```java
CaptureReceipt.initialize(
    "YOUR USER ID",
    new Config(
        new Company(
            "Company Inc.",
            "Tennessee, USA",
            "https://your-co.com/privacy",
            "https://your-co.com/terms"
        ),
        new Key(
            "YOUR TIKI PUBLISHING ID",
            "YOUR MICROBLINK ANDROID LICENSE KEY",
            "YOUR MICROBLINK IOS LICENSE KEY",
            "YOUR MICROBLINK PRODUCT INTELLIGENCE KEY"
        )
    )
    .gmail("YOUR GMAIL API KEY")
    .outlook("YOUR OUTLOOK API KEY")
);
```

## SDK Usage

### Scanning a Physical Receipt
The scan function initiates the process of scanning a physical receipt using the device's camera. It is designed to make capture receipt an efficient and straightforward task within your Android application. Here's how the function works:

1. The SDK opens the device's camera for the user.
2. The user can take a picture of the physical receipt using the camera.
3. The captured image is processed locally on the user's device, utilizing the Microblink SDK to extract the receipt data.
4. The TIKI SDK adds the license to the receipt data and publishes it to the TIKI platform.
5. The function returns a Receipt object containing the details of the scanned receipt.

**Kotlin:**

```kotlin
CaptureReceipt.scan(
    context = applicationContext,
    onReceipt = { receipt ->
        // Process the retrieved receipt data
        println("Receipt Data: $receipt")
    },
    onError = { error ->
        // Handle errors during the scanning process
        println("Error: $error")
    },
    onComplete = {
        // Perform actions upon completion of the scan process
        println("Scan process completed")
    }
)
```

**Java:**
```java
CaptureReceipt.scan(
    applicationContext,
    receipt -> {
        // Process the retrieved receipt data
        System.out.println("Receipt Data: " + receipt);
    },
    error -> {
        // Handle errors during the scanning process
        System.out.println("Error: " + error);
    },
    () -> {
        // Perform actions upon completion of the scan process
        System.out.println("Scan process completed");
    }
);
```

### Add an Email or Retailer Account
Before scraping emails for receipts or grabbing orders from retailer accounts, users need to log in to their accounts. This process varies from one retailer or email provider to another, including 2FA, app passwords, and OAuth authentication. However, all this complexity is handled internally by our SDK. You need to call `CaptureReceipt.login` method with two callbacks for success and error. If the login succeeds, it will call the success callback, passing the Account. If it fails, it will return the error callback with the error.

**Kotlin:**
```kotlin
CaptureReceipt.login(
    context,
    "USERNAME FOR LOGIN",
    "PASSWORD FOR LOGIN",
    AccountCommon.GMAIL, // an enum that identifies the possible accounts
    { account -> print(account) }, // on success callback
    { error -> throw error } // on error callback 
)
```

**Java:**
```java
CaptureReceipt.login(
    context, 
    "USERNAME FOR LOGIN",
    "PASSWORD FOR LOGIN", 
    AccountCommon.GMAIL, // an enum that identifies the account type
    account -> {
        System.out.println(account);
    }, // on success callback
    error -> {
        throw error;
    } // on error callback 
);
```

### List Connected Accounts
```
CaptureReceipt.accounts()
```

### Remove Accounts

**Kotlin:**
```kotlin
CaptureReceipt.logout(
    context,
    "USERNAME FOR LOGIN",
    AccountCommon.GMAIL, // an enum that identifies the possible accounts
    { account -> print(account) }, // on success callback
    { error -> throw error } // on error callback 
)
```

**Java:**
```java
CaptureReceipt.logout(
    context, 
    "USERNAME FOR LOGIN",
    AccountCommon.GMAIL, // an enum that identifies the account type
    account -> {
        System.out.println(account);
    }, // on success callback
    error -> {
        throw error;
    } // on error callback 
);
```

Don't worry; license records issued are backed up to TIKI's immutable, hosted storage for free. After the user logs back in, call `.initialize`, and the SDK will rebuild their license history for you.

### Get Digital Receipt Data

#### One Account

**Kotlin:**
```kotlin
val account: Account = CaptureReceipt.account(
    "ACCOUNT USERNAME",
    AccountCommon.GMAIL,
)

CaptureReceipt.receipts(
    context,
    account,
    { receipt -> println("Receipt Data: $receipt")},
    { error -> throw error }, // error callback
    { print("Get receipts for ${account.username} completed") } // on complete callback
)
```

**Java:**
```java
Account account = CaptureReceipt.account("ACCOUNT USERNAME", AccountCommon.GMAIL);
CaptureReceipt.receipts(context, account,
    (LicenseRecord licenseRecord) -> {
        System.out.println(licenseRecord);
    },
    (Exception error) -> {
        throw error;
    },
    () -> {
        System.out.println("Get receipts for " + account.getUsername() + " completed");
    }
);
```

#### All Email or All Retailer Accounts

**Kotlin:**
```kotlin
CaptureReceipt.receipts(context,
    AccountType.EMAIL, // or AccountType.RETAILER
    { receipt -> println("Receipt Data: $receipt")},
    { error -> throw error }, // error callback
    { print("Get receipts for ${account.username} completed") } // on complete callback
)
```

**Java:**
```java
CaptureReceipt.receipts(context,
    AccountType.EMAIL, // or AccountType.RETAILER
    (LicenseRecord licenseRecord) -> {
        System.out.println(licenseRecord);
    },
    (Exception error) -> {
        throw error;
    },
    () -> {
        System.out.println("Email scraping for all accounts completed");
    }
);
```

#### All Connected Accounts

**Kotlin:**

```kotlin
CaptureReceipt.receipts(context,
    { receipt -> println("Receipt Data: $receipt")},
    { error -> throw error }, // error callback
    { print("Get receipts for ${account.username} completed") } // on complete callback
)
```

**Java:**

```java
CaptureReceipt.receipts(context,
    (LicenseRecord licenseRecord) -> {
        System.out.println(licenseRecord);
    },
    (Exception error) -> {
        throw error;
    },
    () -> {
        System.out.println("Email scraping for all accounts completed");
    }
);
```

## Example

While this README is helpful, it's always easier to see it in action. In the `/example` directory, there is a simple demo app. On launch, it generates a new random user id, with a button called "Start."

[This example app is available on PlayStore]()

## Open Issues

You can find active issues here on GitHub under [Issues](). If you run into a bug or have a question, just create a new Issue or reach out to a team member on [Discord](https://discord.gg/tiki).

# Contributing

- Use [GitHub Issues](https://github.com/tiki/tiki-receipt-capacitor/issues) to report any bugs you find or to request enhancements.
- If you'd like to get in touch with our team or other active contributors, join our [Discord](https://discord.gg/tiki) server.
- Please use [conventional commits](https://www.conventionalcommits.org/en/v1.0.0/) if you intend to add code to this project.
