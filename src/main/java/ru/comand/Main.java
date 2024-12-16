package ru.comand;

import ru.comand.model.Product;

public class Main {
    public static void main(String[] args) {

        Product product = new Product(1, "Яблоко", 200, "Food");
        System.out.println(product);
    }
}