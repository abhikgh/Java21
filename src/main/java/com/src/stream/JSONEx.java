package com.src.stream;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.OptBoolean;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.src.model.Banks;
import com.src.model.Movie;
import com.src.model.OrderInput;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)     //spring.jackson.deserialization.fail-on-unknown-properties=false
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"sfgOrgId", "subOrgTxt", "sfgId", "personId"})
class PersistPersonResponse {

    @JsonProperty("sfgOrgId")
    private String sfgOrgId;

    @JsonProperty("subOrgTxt")
    private String subOrgTxt;

    @JsonProperty("sfgId")
    private String sfgId;

    @JsonProperty("personId")
    private String personId;

    @JsonProperty("priceType")
    private String priceType;

    //for property to be NOT serialized to JSON / NOT deserialized from JSON - add @JsonIgnore AND remove @JsonProperty
    @JsonIgnore
    private String test;

    @JsonProperty("testBoolean")
    private Boolean testBoolean;

    @JsonProperty("testBoolean2")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT) //don't show default values
    private Boolean testBoolean2;

    @JsonFormat
            (
                    shape = JsonFormat.Shape.STRING,
                    pattern = "yyyy-MM-dd HH:mm:ss",
                    timezone = "UTC",
                    lenient = OptBoolean.FALSE
            )
    @JsonProperty("validFromDate")
    private String validFromDate;

    @JsonProperty("discount")
    private String discount;

    @JsonAnySetter
    private List<String> list;

    private Map<String, String> map = new HashMap<>();

    //returns Map, only 1 JsonAnyGetter in class
    //map is not a field in the class
    //set the full map - persistPersonResponse.setMap(map) - here getting the values of the map
    @JsonAnyGetter
    public Map<String, String> getMap() {
        return map;
    }

    private Map<String, String> addressDetails = new HashMap<>();

    //2 arguments
    //fallback in JSON deserialization
    //addressDetails is a field in the class
    //set individual attributes of the map - persistPersonResponse.setAddress("aa", "bb");
    @JsonAnySetter
    public void setAddress(String name, String value) {
        this.addressDetails.put(name, value);
    }

}

public class JSONEx {

    public static void main(String[] args) throws IOException {

        ObjectMapper objectMapper =
                JsonMapper
                        .builder()
                        .enable(SerializationFeature.INDENT_OUTPUT)
                        .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
                        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                        .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                        .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                        .serializationInclusion(JsonInclude.Include.NON_NULL)
                        .build();

        //Java to JSON
        PersistPersonResponse persistPersonResponse = new PersistPersonResponse();
        persistPersonResponse.setSfgOrgId("fdslkjs");
        persistPersonResponse.setSubOrgTxt("sadfsf");
        persistPersonResponse.setPersonId("13123");
        persistPersonResponse.setSfgId("132esf");
        persistPersonResponse.setValidFromDate("1993-12-13 14:56:23");
        persistPersonResponse.setTestBoolean(true);
        persistPersonResponse.setTest("testing..........");

        String discountType = "Family";
        switch (com.src.model.PersistPersonResponse.DiscountType.fromValue(discountType)) {
            case FAMILY:
                persistPersonResponse.setDiscount(discountType + "ff");
                break;
            case FAMILY_PRICE:
                persistPersonResponse.setDiscount(discountType + "fp");
                break;
            case VOUCHER:
                persistPersonResponse.setDiscount(discountType + "v");
        }

        Map<String, String> map = new HashMap<>();
        map.put("mapKey1", "332");
        map.put("mapKey2", "3222");
        persistPersonResponse.setMap(map);

        List<String> list = new ArrayList<>();
        list.add("33333");
        list.add("11111");
        list.add("22222");
        list.add("44444");
        persistPersonResponse.setList(list);
        persistPersonResponse.setAddress("aa", "bb");
        persistPersonResponse.setPriceType(EnumEx.PriceType.FAMILY_PRICE.name());
        System.out.println("---------JAVA to JSON---------");
        String jsonRequest = objectMapper.writeValueAsString(persistPersonResponse);
        System.out.println(jsonRequest);
       /*
            {
        "sfgOrgId" : "fdslkjs",
        "subOrgTxt" : "sadfsf",
        "sfgId" : "132esf",
        "personId" : "13123",
        "addressDetails" : {
        "aa" : "bb"
        },
        "list" : [ "33333", "11111", "22222", "44444" ],
        "priceType" : "FAMILY_PRICE",
        "testBoolean" : true,
        "validFromDate" : "1993-12-13 14:56:23",
        "discount" : "Familyff",
        "mapKey2" : "3222",
        "mapKey1" : "332"
        }
       */

        //JSON to Java
        System.out.println("---------JSON to JAVA---------");
        File file = new File("src/main/resources/Input.json");
        PersistPersonResponse persistPersonResponse2 = objectMapper.readValue(file, PersistPersonResponse.class);
        System.out.println(persistPersonResponse2);
        //PersistPersonResponse(sfgOrgId=fdslkjs, subOrgTxt=sadfsf, sfgId=132esf, personId=13123, priceType=RegularSalesUnitPrice, test=null, testBoolean=true, testBoolean2=null, validFromDate=1993-12-13'T14:56:23, discount=Familyff, list=[33333, 11111, 22222, 44444], map={}, addressDetails={mapKey2=3222, mapKey1=332})

        //JSON array to Java list
        System.out.println("---------JSON array to JAVA list ---------");
        file = new File("src/main/resources/allMoviesOfHeroRequest.json");
        List<Movie> movies = Arrays.asList(objectMapper.readValue(file, Movie[].class));
        movies.forEach(System.out::println);

        //JSON array to String
        System.out.println("---------JSON array to String ---------");
        Path path = Paths.get("src/main/resources/allMoviesOfHeroRequest.json");
        String jsonString = Files.readString(path);
        System.out.println(jsonString);

        //JSON to Java with variable fields

        //@JsonIgnoreProperties(ignoreUnknown = true)
        //@JsonInclude(JsonInclude.Include.NON_NULL)
        String location = "HK";
        file = new File("src/main/resources/banks.json");
        Banks banks = objectMapper.readValue(file, Banks.class);
        System.out.println("------Reading variable fields  in JSON -------------");
        if ("UK".equalsIgnoreCase(location)) {
            System.out.println(banks.getBank1() + "-" + banks.getBank2() + "-" + banks.getBank9());
        } else if ("HK".equalsIgnoreCase(location)) {
            System.out.println(banks.getBank4() + "-" + banks.getBank5() + "-" + banks.getBank8());
        }

        //JSON Masking
        File fileUnmasked = new File("src/main/resources/Input.json");
        JsonNode jsonTree = objectMapper.readTree(fileUnmasked);

        String fieldsToMask = "subOrgTxt,personId";
        String[] listFieldsToMask = fieldsToMask.split(",");

        for (String fieldToMask : listFieldsToMask) {
            if (Objects.nonNull(jsonTree.findValue(fieldToMask))) {
                for (JsonNode parent : jsonTree.findParents(fieldToMask)) {
                    Optional.ofNullable(parent).ifPresent(parentNode -> ((ObjectNode) parentNode).put(fieldToMask, "XXXXXX"));
                }
            }
        }
        String finalJSON = jsonTree.toPrettyString();
        System.out.println("Masked JSON...");
        System.out.println(finalJSON);

        //Read all jsons in a folder
        Path pathJsonFilesInput = Paths.get("src/main/resources/json/input");
        Map<String, OrderInput> orderInputMap = new LinkedHashMap<>();
        Files.walk(pathJsonFilesInput)
                .filter(path2 -> path2.toFile().isFile() && path2.getFileName().toString().endsWith(".json"))
                .map(Path::toFile)
                .forEach(file2 -> {
                    try {
                        orderInputMap.put(file2.getName(), objectMapper.readValue(file2, OrderInput.class));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

        orderInputMap.values().forEach(orderInput -> orderInput.setStatus(1));
        System.out.println(orderInputMap);

        //put all the objects as json in a folder
        orderInputMap.forEach((fileName, orderInput) -> {
            try {
                Path filePath = Paths.get("src/main/resources/json/interim/", fileName);
                String json = objectMapper.writeValueAsString(orderInput);
                Files.writeString(filePath, json, StandardOpenOption.APPEND);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println("Interim files written");


    }
}

