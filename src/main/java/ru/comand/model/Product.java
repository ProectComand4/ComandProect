package ru.comand.model;

import ru.comand.Enums.CategoryProduct;
import ru.comand.Exceptions.ProductNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Product {
    private CategoryProduct productCategory;
    private Integer id;
    private String productName;
    private Integer productPrice;

    public Product(Integer id, String productName,
                   Integer productPrice,
                   CategoryProduct categoryProduct) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productCategory = categoryProduct;
    }

    public Product(String productFromFile) {
        String[] parts = productFromFile.split(",");
        this.id = Integer.parseInt(parts[0]);
        this.productName = parts[1];
        this.productPrice = Integer.parseInt(parts[2]);
        this.productCategory = CategoryProduct.toCategoryRus(parts[3]);

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

    public CategoryProduct getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = CategoryProduct.valueOf(productCategory);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(productName, product.productName)
                && Objects.equals(productPrice, product.productPrice)
                && Objects.equals(productCategory, product.productCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, productPrice, productCategory);
    }

    @Override
    public String toString() {
        return
                id + "," + productName + "," + productPrice + "," + productCategory.getRus() + "\n";
    }

    public String toStringForFiles() {
        return id + "," + productName + "," + productPrice + "," + productCategory;
    }

    /**
     * Находит товар по ID
     * @param id ID товара
     * @return товар с указанным ID
     */
    public static Product toProduct(int id) {
        Path filePath = Path.of("src/main/java/ru/comand/repository/Files/products.txt");
        try {
            return Files.readAllLines(filePath).stream()
                    .map(Product::new)
                    .filter(p -> p.getId().equals(id))
                    .findFirst().orElse(null);
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла - " + e.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println("Файл с товарами пустой");
        }
        throw new ProductNotFoundException("Товаров с таким ID нет");

    }
}