package com.src.stream;

import java.util.Scanner;

interface Mobile{
    String getModel();
    Integer getPrice();
}

class IPhone8 implements Mobile{

    @Override
    public String getModel() {
        return "Iphone 8";
    }

    @Override
    public Integer getPrice() {
        return 65000;
    }
}

class SamsungGT implements Mobile{

    @Override
    public String getModel() {
        return "Samsung Galaxy Tab 3";
    }

    @Override
    public Integer getPrice() {
        return 45000;
    }
}

class MobileFacade{
    IPhone8 iPhone;
    SamsungGT samsung;

    public MobileFacade(){
        iPhone = new IPhone8();
        samsung = new SamsungGT();
    }

    public String getIPhoneDetails(){
        return iPhone.getModel() + "--" + iPhone.getPrice();
    }

    public String getSamsungDetails(){
        return samsung.getModel() + "--" + samsung.getPrice();
    }
}

public class FacadeEx {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String inputMobile = scanner.nextLine();
        MobileFacade mobileFacade = new MobileFacade();
        switch (inputMobile){
            case "samsung":{
                System.out.println(mobileFacade.getSamsungDetails());
                break;
            }
            case "iphone":{
                System.out.println(mobileFacade.getIPhoneDetails());
                break;
            }
            default:{
                System.out.println("NA");
            }
        }
    }
}