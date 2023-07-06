package com.progress.repository.entities.enums;

public enum CurrencyCodes {

    USD("United States Dollar"), EUR("Euro"),
    GBP("British Pound Sterling"), JPY("Japanese Yen"),
    AUD("Australian Dollar"), CAD("Canadian Dollar"),
    CHF("Swiss Franc"), CNY("Chinese Yuan"),
    SEK("Swedish Krona"), NZD("New Zealand Dollar"),
    MXN("Mexican Peso"), SGD("Singapore Dollar"),
    HKD("Hong Kong Dollar"), NOK("Norwegian Krone"),
    KRW("South Korean Won"), TRY("Turkish Lira"),
    RUB("Russian Ruble"), INR("Indian Rupee"),
    BRL("Brazilian Real"), ZAR("South African Rand"),
    AED("United Arab Emirates Dirham"), JOD("Jordanian Dinar"),
    SAR("Saudi Riyal");

    private final String name;

    CurrencyCodes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
