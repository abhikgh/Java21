package com.src.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@Data
public class Student {

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("standard")
    private Integer standard;

    @JsonProperty("rollNumber")
    private String rollNumber;

    @JsonProperty("house")
    private String house;

    @JsonProperty("marks")
    private Marks marks;

    @JsonProperty("totalMarks")
    private Integer totalMarks;

    @JsonProperty("sports")
    private List<Sport> sports;

    @JsonProperty("hobbies")
    private List<String> hobbies;
}
