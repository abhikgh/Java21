package com.src.stream;

import java.util.HashMap;
import java.util.Map;

public enum DiscountType {

    //1st thing of Enum is the actual values
    FAMILY("Family"),
    FAMILY_PRICE("Family_Price"),
    VOUCHER("Voucher");

    private static final Map<String, DiscountType> CONSTANTS = new HashMap<>();

    static {
        DiscountType[] discountTypeArr = values();
        for (int i = 0; i < discountTypeArr.length; i++) {
            DiscountType d0 = discountTypeArr[i];
            CONSTANTS.put(d0.value, d0);
        }

    }

    private final String value;

    DiscountType(String value) {
        this.value = value;
    }

    public static DiscountType fromValue(String value) {
        return CONSTANTS.get(value);

    }

}
