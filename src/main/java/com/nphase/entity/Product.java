package com.nphase.entity;

import java.math.BigDecimal;

import static com.nphase.utils.Constants.DEFAULT_CATEGORY_NAME;

public class Product {
    private final String name;

    private final BigDecimal pricePerUnit;
    private final int quantity;

    private String category = DEFAULT_CATEGORY_NAME;

    public Product(String name, BigDecimal pricePerUnit, int quantity) {
        this.name = name;
        this.pricePerUnit = pricePerUnit;
        this.quantity = quantity;
    }
    public Product(String name, BigDecimal pricePerUnit, int quantity, String category) {
        this.name = name;
        this.pricePerUnit = pricePerUnit;
        this.quantity = quantity;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public int getQuantity() {
        return quantity;
    }
}
