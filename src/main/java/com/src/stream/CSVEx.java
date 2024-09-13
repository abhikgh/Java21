package com.src.stream;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class CSVEx {

    public static void main(String[] args) throws IOException, JAXBException {

        //Read csv - scanner
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

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/abcNew.txt"))){
            String line = bufferedReader.readLine();
            while (Objects.nonNull(line)) {
                System.out.println(line);
                // read next line
                line = bufferedReader.readLine();
            }
        }

    }
}
