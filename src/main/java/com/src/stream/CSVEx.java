package com.src.stream;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class CSVEx {

    public static void main(String[] args) throws IOException, JAXBException {

        //Read csv
        try(Scanner scanner = new Scanner(new File("src/main/resources/file1.csv"))) {
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                String elem = scanner.next();
                if (elem.contains("ip")) {
                    System.out.println(elem.split(":")[1]);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
