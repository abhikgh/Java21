package com.src.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"cabinet","sofa","chairs","tables"})
@ToString
@Data
public class OrderItem {

    @JsonProperty("cabinet")
    private BigDecimal cabinet;
    @JsonProperty("sofa")
    private BigDecimal sofa;
    @JsonProperty("chairs")
    private BigDecimal chairs;
    @JsonProperty("tables")
    private BigDecimal tables;
}
