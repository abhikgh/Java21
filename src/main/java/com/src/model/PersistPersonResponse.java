package com.src.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"sfgOrgId","subOrgTxt","sfgId","personId","test"})
public class PersistPersonResponse {

    @JsonProperty("sfgOrgId")
    private String sfgOrgId;
    @JsonProperty("subOrgTxt")
    private String subOrgTxt;
    @JsonProperty("sfgId")
    private String sfgId;
    @JsonProperty("personId")
    private String personId;
    @JsonProperty("test")
    private String test;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss",
            timezone = "UTC"
    )
    @JsonProperty("validFromDate")
    private String validFromDate;
    @JsonProperty("discount")
    private String discount;

    public enum DiscountType{

        FAMILY("Family"),
        FAMILY_PRICE("Family_Price"),
        VOUCHER("Voucher");

        private final String value;

        private DiscountType(String value) {
            this.value = value;
        }

        private static final Map<String, DiscountType> CONSTANTS = new HashMap<>();

        static {
            DiscountType[] discountTypeArr = values();
            for(int i=0;i<discountTypeArr.length;i++){
                DiscountType d0 = discountTypeArr[i];
                CONSTANTS.put(d0.value,d0);
            }

        }

        public static DiscountType fromValue(String value){
            return CONSTANTS.get(value);

        }


    }



}