package com.src.stream;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.src.model.OrderItem;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"orderId","country","customerType","status","orderItems"})
@ToString
@Data
public class OrderInput {

    @JsonProperty("orderId")
    private String orderId;
    @JsonProperty("country")
    private String country;
    @JsonProperty("customerType")
    private String customerType;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("orderItems")
    private List<OrderItem> orderItems;
}
