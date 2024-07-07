package com.src.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@Data
public class Marks {

    @JsonProperty("maths")
    private Integer maths;

    @JsonProperty("english")
    private Integer english;

    @JsonProperty("science")
    private Integer science;

    @JsonProperty("history")
    private Integer history;
}
