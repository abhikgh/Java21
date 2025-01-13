package com.src.stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.src.model.Sport;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileEx {

    public static void main(String[] args) throws IOException {



	// Files.readString(path)
	// Files.writeString(path, stringContent, StandardOpenOption.APPEND)
	// Files.deleteIfExists(path) 
	// Files.createFile(path)    
	// Files.walk(directory)    
	// Files.isDirectory
	// Files.isRegularFile
	// Files.readAllBytes(path); // Files.readString cannot read pdf
    // Files.lines(path) //get each line of the file


	 // Paths.get
	 // Path.toFile
         // Path.toFile.isFile

        Path path = Paths.get("src/main/resources/abc.txt");

        //Read file
        String data = Files.readString(path);
        System.out.println("File contents :: ");
        System.out.println(data);

        //Create file
        path = Paths.get("src/main/resources/abc2.txt");
        Files.deleteIfExists(path);
        Files.createFile(path);


        //Write to file
        Files.writeString(path, "Sample text again", StandardOpenOption.APPEND);

        //----Read all folders and files in a directory-----
        //Files.walk(directory)
        String directory = "D:\\workspaceI";
        System.out.println("Reading folders---------");
        Files.walk(Paths.get(directory))
                .filter(Files::isDirectory)
                .forEach(c -> System.out.println(c.getFileName()));
        System.out.println("Reading files---------");
        Files.walk(Paths.get(directory))
                .filter(Files::isRegularFile)
                .forEach(x -> System.out.println(x.getFileName()));

        //Read all jsons in a folder
        ObjectMapper objectMapper = new ObjectMapper();
        Path pathJsonFilesInput = Paths.get("src/main/resources/json/input");
        Map<String, OrderInput> orderInputMap = new LinkedHashMap<>();
        Files.walk(pathJsonFilesInput)
                .filter(path2 -> path2.toFile().isFile() && path2.getFileName().toString().endsWith(".json"))
                .map(Path::toFile)
                .forEach(file2 -> {
                    try {
                        orderInputMap.put(file2.getName(),objectMapper.readValue(file2, OrderInput.class));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

        orderInputMap.values().forEach(orderInput -> orderInput.setStatus(1));

        System.out.println(orderInputMap);

        //put all the objects as json in a folder
        orderInputMap.forEach((fileName, orderInput) -> {
            try {
                Path filePath =  Paths.get("src/main/resources/json/interim/", fileName);
                String json = objectMapper.writeValueAsString(orderInput);
                Files.writeString(filePath, json);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println("Interim files written");

	//read a file char-by-char as input stream
	Path pathSt = Paths.get("src/main/resources/abc.txt");
        File fileSt = path.toFile();
        try (FileInputStream fis = new FileInputStream(fileSt)) {
            int i;
            while( (i = fis.read()) !=-1){
                System.out.println((char)i);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //read the file line-by-line and check each line if the line contains the pattern and if so return the group
        Pattern pattern = Pattern.compile(".*(ipaddress:\\d{7}).*");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/abcNew.txt"))) {
            String line = bufferedReader.readLine();
            while (Objects.nonNull(line)) {
                boolean matches = pattern.asMatchPredicate().test(line);
                if (matches) {
                    Matcher matcher = pattern.matcher(line);
                    while (matcher.find()) {
                        System.out.println("ipAddress is :: " + matcher.group(1).split(":")[1]);
                    }
                }
                // read next line
                line = bufferedReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //read the file line-by-line and check each line if the line contains the pattern and if so return the group
        pattern = Pattern.compile(".*(apple=\\w+\\s).*");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/applesNew.txt"))) {
            String line = bufferedReader.readLine();
            while(Objects.nonNull(line)){
                if(pattern.asMatchPredicate().test(line)){
                    Matcher matcher = pattern.matcher(line);
                    while(matcher.find()){
                        System.out.println("Apple Colour :: " + matcher.group(1).split("=")[1]);
                    }
                }line = bufferedReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

		//read the file for a list of items
		path = Paths.get("src/main/resources/sports.json");
		data = Files.readString(path);
		List<Sport> sportList = Arrays.asList(objectMapper.readValue(data, Sport[].class));
		sportList.forEach(sport -> System.out.println(sport.getSportName()+"-"+sport.getStatus()));

        //Encode file to String and Decode
        path = Paths.get("src/main/resources/Flight Ticket.pdf");
        byte[] bArrFile = Files.readAllBytes(path); // Files.readString cannot read pdf
        String str = java.util.Base64.getEncoder().encodeToString(bArrFile);
        System.out.println("encodedFile :: " + str);  //JVBERi0xLjcNCiW1tbW1DQoxIDAgb2JqDQo8PC9UeXBlL0NhdGFsb2cvUGFnZXMgMiAwIFI

        byte[] bArrFile2 = java.util.Base64.getDecoder().decode(str.getBytes(StandardCharsets.UTF_8));
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("src/main/resources/Flight Ticket2.pdf"));
        bufferedOutputStream.write(bArrFile2);
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
        System.out.println("New file generated...");

        //Read a large file parallely
        Path pathFile = Paths.get("src/main/resources/abc.log");
        BigDecimal amountSumGTLakh = Files.lines(pathFile)
                                        .parallel() //changing to parallel stream
                                        .map(FileEx::getAmountFromLog)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("amountSumGTLakh :: " + amountSumGTLakh); //280000


    }

    public static BigDecimal getAmountFromLog(String line){
        BigDecimal amount = new BigDecimal(line.split(",")[2]);
        return amount.compareTo(BigDecimal.valueOf(100000)) > 0 ?amount:BigDecimal.ZERO;


    }

}
