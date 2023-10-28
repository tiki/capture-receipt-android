package com.mytiki.capture_receipt.retailer

/**
 * An enumeration representing various retailers.
 *
 * @property value The integer value associated with the retailer.
 */
enum class RetailerEnum(val value: Int) {
    ACME_MARKETS(com.microblink.linking.ACME_MARKETS),
    ALBERTSONS(com.microblink.linking.ALBERTSONS),
    AMAZON(com.microblink.linking.AMAZON_BETA),
    AMAZON_CA(com.microblink.linking.AMAZON_CA_BETA),
    AMAZON_UK(com.microblink.linking.AMAZON_UK_BETA),
    BED_BATH_AND_BEYOND(com.microblink.linking.BED_BATH_AND_BEYOND),
    BESTBUY(com.microblink.linking.BESTBUY),
    BJS_WHOLESALE(com.microblink.linking.BJS_WHOLESALE),
    CHEWY(com.microblink.linking.CHEWY),
    COSTCO(com.microblink.linking.COSTCO),
    CVS(com.microblink.linking.CVS),
    DICKS_SPORTING_GOODS(com.microblink.linking.DICKS_SPORTING_GOODS),
    DOLLAR_GENERAL(com.microblink.linking.DOLLAR_GENERAL),
    DOLLAR_TREE(com.microblink.linking.DOLLAR_TREE),
    DOMINOS_PIZZA(com.microblink.linking.DOMINOS_PIZZA),
    DOOR_DASH(com.microblink.linking.DOOR_DASH),
    DRIZLY(com.microblink.linking.DRIZLY),
    FAMILY_DOLLAR(com.microblink.linking.FAMILY_DOLLAR),
    FOOD_4_LESS(com.microblink.linking.FOOD_4_LESS),
    FOOD_LION(com.microblink.linking.FOOD_LION),
    FRED_MEYER(com.microblink.linking.FRED_MEYER),
    GAP(com.microblink.linking.GAP),
    GIANT_EAGLE(com.microblink.linking.GIANT_EAGLE),
    GRUBHUB(com.microblink.linking.GRUBHUB),
    HEB(com.microblink.linking.HEB),
    HOME_DEPOT(com.microblink.linking.HOME_DEPOT),
    HYVEE(com.microblink.linking.HYVEE),
    INSTACART(com.microblink.linking.INSTACART),
    JEWEL_OSCO(com.microblink.linking.JEWEL_OSCO),
    KOHLS(com.microblink.linking.KOHLS),
    KROGER(com.microblink.linking.KROGER),
    LOWES(com.microblink.linking.LOWES),
    MACYS(com.microblink.linking.MACYS),
    MARSHALLS(com.microblink.linking.MARSHALLS),
    MEIJER(com.microblink.linking.MEIJER),
    NIKE(com.microblink.linking.NIKE),
    PUBLIX(com.microblink.linking.PUBLIX),
    RALPHS(com.microblink.linking.RALPHS),
    RITE_AID(com.microblink.linking.RITE_AID),
    SAFEWAY(com.microblink.linking.SAFEWAY),
    SAMS_CLUB(com.microblink.linking.SAMS_CLUB),
    SEAMLESS(com.microblink.linking.SEAMLESS),
    SEPHORA(com.microblink.linking.SEPHORA),
    SHIPT(com.microblink.linking.SHIPT),
    SHOPRITE(com.microblink.linking.SHOPRITE),
    SPROUTS(com.microblink.linking.SPROUTS),
    STAPLES(com.microblink.linking.STAPLES),
    STAPLES_CA(com.microblink.linking.STAPLES_CA),
    STARBUCKS(com.microblink.linking.STARBUCKS),
    TACO_BELL(com.microblink.linking.TACO_BELL),
    TARGET(com.microblink.linking.TARGET),
    TJ_MAXX(com.microblink.linking.TJ_MAXX),
    UBER_EATS(com.microblink.linking.UBER_EATS),
    ULTA(com.microblink.linking.ULTA),
    VONS(com.microblink.linking.VONS),
    WALGREENS(com.microblink.linking.WALGREENS),
    WALMART(com.microblink.linking.WALMART),
    WALMART_CA(com.microblink.linking.WALMART_CA),
    WEGMANS(com.microblink.linking.WEGMANS);

    /**
     * Converts the retailer to its corresponding integer value.
     *
     * @return The integer value of the retailer.
     */
    fun toMbInt(): Int = this.value

    /**
     * Returns the string representation of the retailer.
     *
     * @return The name of the retailer.
     */
    override fun toString() = this.name

    companion object {
        /**
         * Retrieves a retailer based on its integer value.
         *
         * @param intValue The integer value of the retailer.
         * @return The retailer enum matching the provided integer value.
         * @throws NoSuchElementException if no retailer with the given integer value is found.
         */
        fun fromMbInt(intValue: Int) = values().first { it.value == intValue }

        /**
         * Retrieves a retailer based on its string representation.
         *
         * @param stringValue The string representation of the retailer.
         * @return The retailer enum matching the provided string representation.
         * @throws NoSuchElementException if no retailer with the given string representation is found.
         */
        fun fromString(stringValue: String) = values().first { it.name == stringValue }
    }
}

/**
 * enum class Retailer(val retailerId: Int, val bannerId: Int) {
 *     ACME(116, 17),
 *     ALBERTSONS(102, 20),
 *     ALBERTSONS_1(132, 20),
 *     ALBERTSONS_2(133, 20),
 *     ALBERTSONS_3(
 *         134,
 *         20
 *     ),
 *     AMAZON(1, 8643),
 *     BABIES_R_US(27, 13),
 *     BED_BATH(2, 6074),
 *     BESTBUY(3, 177),
 *     BJS_WHOLESALE(
 *         100,
 *         45
 *     ),
 *     BURGER_KING(10000, 6210),
 *     COSTCO(29, 2),
 *     CVS(103, 3),
 *     DUANE_READE(101, 18),
 *     FAMILY_DOLLAR(
 *         107,
 *         165
 *     ),
 *     FRED_MEYER(108, 53),
 *     FRYS(129, 60),
 *     GIANT(128, 58),
 *     HARRIS_TEETER(123, 33),
 *     HEB(
 *         117,
 *         44
 *     ),
 *     HOME_DEPOT(22, 15),
 *     HYVEE(125, 56),
 *     JEWEL_OSCO(115, 23),
 *     KING_SOOPERS(148, 36),
 *     KMART(
 *         10,
 *         6062
 *     ),
 *     KOHLS(11, 5929),
 *     KROGER(111, 6),
 *     LOWES(28, 16),
 *     MACYS(12, 6802),
 *     MARSHALLS(109, 5917),
 *     MEIJER(
 *         112,
 *         19
 *     ),
 *     MILITARY_COMMISSARY(124, 48),
 *     OFFICE_DEPOT(31, 6125),
 *     OFFICE_MAX(15, 6126),
 *     PETCO(
 *         106,
 *         6146
 *     ),
 *     PETSMART(105, 6152),
 *     POPEYES(100005, 6351),
 *     PUBLIX(118, 7),
 *     SAFEWAY(119, 8),
 *     SAFEWAY_1(
 *         135,
 *         8
 *     ),
 *     SAFEWAY_2(136, 8),
 *     SAMS_CLUB(99, 9),
 *     SHAWS(113, 26),
 *     SEARS(18, 5853),
 *     SHOPRITE(120, 22),
 *     SMITHS(
 *         130,
 *         42
 *     ),
 *     SPROUTS(127, 54),
 *     STAPES(20, 20),
 *     STAR_MARKET(114, 25),
 *     STOP_N_SHOP(121, 46),
 *     TARGET(
 *         21,
 *         1
 *     ),
 *     TOYS_R_US(23, 14),
 *     TRADER_JOES(104, 7823),
 *     WALGREENS(24, 10),
 *     WALMART(25, 11),
 *     WINCO(
 *         110,
 *         51
 *     ),
 *     WINNDIXIE(122, 47),
 *     WEGMANS(126, 52),
 *     WHOLE_FOODS(131, 55),
 *     WALMART_QR(149, 11),
 *     INSTACART(
 *         0,
 *         8652
 *     ),
 *     WALMART_GROCERY(0, 8897),
 *     STARBUCKS(0, 6677),
 *     SHIPT(0, 9016),
 *     SEPHORA(0, 171),
 *     CHEWY(
 *         0,
 *         9947
 *     ),
 *     STAPLES(0, 20),
 *     GRUBHUB(0, 10208),
 *     DOLLAR_GENERAL(0, 4),
 *     DOLLAR_TREE(0, 5),
 *     FOOD_LION(
 *         0,
 *         142
 *     ),
 *     DOMINOS_PIZZA(0, 8366),
 *     TACO_BELL(0, 8598),
 *     DOOR_DASH(0, 10241),
 *     GIANT_EAGLE(0, 144),
 *     POSTMATES(
 *         0,
 *         19671
 *     ),
 *     MAKRO(0, 10902),
 *     UNKNOWN(0, 0);
 * }
 */
