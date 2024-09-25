package com.src.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Banks {
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
