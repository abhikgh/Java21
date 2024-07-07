package com.src.stream;

import java.util.HashMap;
import java.util.Map;

//Enum :: When a variable have a fixed set of values we use Enum as it provides Type Safety
//       as Enum allows only certain values
public class EnumEx {

    public enum PriceType{

        //1st thing of Enum is the actual values
        REGULAR_PRICES("RegularSalesUnitPrice"),
        FAMILY_PRICE("FamilySalesUnitPrice");

        private String priceTypeVar;

        PriceType(String priceTypeVar){
            this.priceTypeVar = priceTypeVar;
        }

        private static Map<String, PriceType> constants = new HashMap<>();

        static {
            for(PriceType p : values()){
                constants.put(p.priceTypeVar, p);
            }
        }

        public static PriceType fromValue(String priceTypeVar){
            return constants.get(priceTypeVar);
        }

    }

    public static void main(String[] args) {

        PriceType priceType = PriceType.fromValue("RegularSalesUnitPrice");
        System.out.println(priceType); //REGULAR_PRICES
    }
}
