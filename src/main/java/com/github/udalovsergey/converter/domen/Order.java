package com.github.udalovsergey.converter.domen;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@JsonPropertyOrder({"orderId", "amount", "currency", "comment"})
public class Order {
    @NotNull(message = "'orderId' must not be null")
    @Min(value = 0, message = "'orderId' must be greater than or equal to 0")
    private Long orderId;
    @Min(value = 0, message = "'amount' must be greater than or equal to 0")
    private BigDecimal amount;
    @NotBlank(message = "'currency' must not be blank")
    private String currency;
    @NotBlank(message = "'comment' must not be blank")
    private String comment;

    public Order() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
