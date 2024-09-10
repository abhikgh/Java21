package com.src.stream;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonFormat;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.src.model.Movie;
import com.src.model.OrderInput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"sfgOrgId", "subOrgTxt", "sfgId", "personId", "test"})
class PersistPersonResponse {

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
    @JsonProperty("testBoolean")
    private Boolean testBoolean;
    @JsonProperty("testBoolean2")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT) //don't show default values
    private Boolean testBoolean2;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss",
            timezone = "UTC",
            lenient = OptBoolean.FALSE
    )
    @JsonProperty("validFromDate")
    private String validFromDate;
    @JsonProperty("discount")
    private String discount;

    @JsonAnySetter
    private Map<String, String> map = new HashMap<>();
    private List<String> list;

    @JsonAnyGetter
    public Map<String, String> getMap() {
        return map;
    }


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

}

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
//spring.jackson.deserialization.fail-on-unknown-properties=false
class Banks {
    @JsonProperty("bank1")
    private String bank1;

    @JsonProperty("bank2")
    private String bank2;

    @JsonProperty("bank3")
    private String bank3;

    @JsonProperty("bank4")
    private String bank4;

    @JsonProperty("bank5")
    private String bank5;

    @JsonProperty("bank6")
    private String bank6;

    @JsonProperty("bank7")
    private String bank7;

    @JsonProperty("bank8")
    private String bank8;

    @JsonProperty("bank9")
    private String bank9;
}

public class JSONEx {

    public static void main(String[] args) throws IOException {

        PersistPersonResponse persistPersonResponse = new PersistPersonResponse();
        persistPersonResponse.setSfgOrgId("fdslkjs");
        persistPersonResponse.setSubOrgTxt("sadfsf");
        persistPersonResponse.setPersonId("13123");
        persistPersonResponse.setSfgId("132esf");
        persistPersonResponse.setValidFromDate("1993-12-13'T14:56:23");
        persistPersonResponse.setTestBoolean(true);

        String discountType = "Family";
        switch (PersistPersonResponse.DiscountType.fromValue(discountType)) {
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

        ObjectMapper objectMapper =
                JsonMapper
                        .builder()
                        .enable(SerializationFeature.INDENT_OUTPUT)
                        .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
                        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                        .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                        //.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                        .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                        .serializationInclusion(JsonInclude.Include.NON_NULL)
                        .build();

        //Java to JSON
        String jsonRequest = objectMapper.writeValueAsString(persistPersonResponse);
        System.out.println(jsonRequest);

        //JSON to Java object
        File file = new File("src/main/resources/Input.json");
        PersistPersonResponse persistPersonResponse2 = objectMapper.readValue(file, PersistPersonResponse.class);
        System.out.println(persistPersonResponse2);
        persistPersonResponse2.getMap().forEach((k, v) -> System.out.println(k + "-" + v));
        persistPersonResponse2.getList().forEach(System.out::println);
        //PersistPersonResponse(sfgOrgId=fdslkjs, subOrgTxt=sadfsf, sfgId=132esf, personId=13123, test=null, testBoolean=true, testBoolean2=null, validFromDate=1993-12-13'T14:56:23, discount=Familyff, map={mapKey2=3222, mapKey1=332}, list=[33333, 11111, 22222, 44444])

        //JSON array to Java list
        Path path = Paths.get("src/main/resources/allMoviesOfHeroRequest.json");
        String allMovies = Files.readString(path);
        List<Movie> movies = Arrays.asList(objectMapper.readValue(allMovies, Movie[].class));
        movies.forEach(System.out::println);

        //JSON to Java with variable fields
        //@JsonIgnoreProperties(ignoreUnknown = true)
        //@JsonInclude(JsonInclude.Include.NON_NULL)
        String location = "HK";
        path = Paths.get("src/main/resources/banks.json");
        String input = Files.readString(path);
        Banks banks = objectMapper.readValue(input, Banks.class);
        System.out.println("Reading variable fields...");
        if ("UK".equalsIgnoreCase(location)) {
            System.out.println(banks.getBank1() + "-" + banks.getBank2() + "-" + banks.getBank9());
        } else if ("HK".equalsIgnoreCase(location)) {
            System.out.println(banks.getBank4() + "-" + banks.getBank5() + "-" + banks.getBank8());
        }

        //Data masking
        path = Paths.get("src/main/resources/customerInput.json");
        String payload = Files.readString(path);
        String[] keyToBeMasked = new String[]{"customerId", "customerDept"};
        JsonNode jsonNode = new ObjectMapper().readTree(payload);
        for (String key : keyToBeMasked) {
            if (Objects.nonNull(jsonNode.findValue(key))) {
                for (JsonNode parentNode : jsonNode.findParents(key)) {
                    Optional.ofNullable(parentNode).ifPresent(parent -> ((ObjectNode) parent).put(key, "XXXXXXX"));
                }
            }
        }
        System.out.println("Masked payload");
        System.out.println(jsonNode.toPrettyString());

        path = Paths.get("src/main/resources/customerOutput.json");
        Files.deleteIfExists(path);
        Files.createFile(path);
        Files.writeString(path, jsonNode.toPrettyString(), StandardOpenOption.APPEND);

        //String sourceAsString = ((JSONObject) parser.parse(new FileReader(requestFilePath))).toJSONString();
        //String sourceAsString = ((JSONArray) parser.parse(new FileReader(requestFilePath))).toJSONString();

        //GSON
        //Java to JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String persistPersonJSON = gson.toJson(persistPersonResponse);
        System.out.println(persistPersonJSON);

        //JSON to Java
        path = Paths.get("src/main/resources/persistPerson.json");
        input = Files.readString(path);
        PersistPersonResponse persistPersonResponse1 = gson.fromJson(input, PersistPersonResponse.class);
        System.out.println(persistPersonResponse1);


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
                Files.writeString(filePath, json);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println("Interim files written");


        //setting particular value in a field
        File file22 = new File("src/main/resources/Input.json");
        PersistPersonResponse persistPersonResponse22 = objectMapper.readValue(file22, PersistPersonResponse.class);
        String payloadTC = objectMapper.writeValueAsString(persistPersonResponse22);
        List<String> fieldNamesToChange = List.of("sfgOrgId");

        JsonNode jsonNodeTC = new ObjectMapper().readTree(payloadTC);
        fieldNamesToChange.forEach(fieldName -> {
            if (Objects.nonNull(jsonNodeTC.findValue(fieldName))) {
                for (JsonNode parentNode : jsonNodeTC.findParents(fieldName)) {
                    Optional.ofNullable(parentNode).ifPresent(parent -> ((ObjectNode) parent).put(fieldName, ""));
                }
            }
        });
        payloadTC = jsonNodeTC.toPrettyString();
        persistPersonResponse22 = objectMapper.readValue(payloadTC, PersistPersonResponse.class);

        System.out.println("Changed payload");
        System.out.println(persistPersonResponse22);
        System.out.println(objectMapper.writeValueAsString(persistPersonResponse22));


    }
}
