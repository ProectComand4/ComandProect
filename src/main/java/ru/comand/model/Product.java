package ru.comand.model;

import java.util.Objects;

public class Product {
    private Integer id;
    private String productName;
    private Integer productPrice;
    private String productCategory;

    public Product(Integer id, String productName, Integer productPrice, String productCategory) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(productName, product.productName) && Objects.equals(productPrice, product.productPrice) && Objects.equals(productCategory, product.productCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, productPrice, productCategory);
    }

    @Override
    public String toString() {
        return
                "id=" + id + " " + productName +
                        ", за " + productPrice + " рублей" +
                        " из категории " + productCategory;
    }
}
