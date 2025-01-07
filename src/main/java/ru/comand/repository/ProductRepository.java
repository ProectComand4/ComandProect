package ru.comand.repository;

import ru.comand.Exceptions.ProductNotFoundException;
import ru.comand.model.Product;

import java.io.IOException;
import java.nio.file.*;

import java.util.List;


public class ProductRepository {
    public final Path pathProducts =
            Path.of("src/main/java/ru/comand/repository/files/products.txt");
    public final Path pathIDProducts =
            Path.of("src/main/java/ru/comand/repository/files/idProducts.txt");
    public Integer idProduct = 0;

    public ProductRepository() {

        try {

            if (!Files.exists(Paths.get(pathProducts.toString()))) {
                Files.createFile(pathProducts);
            }
            if (!Files.exists(Paths.get(pathIDProducts.toString()))) {
                Files.createFile(pathIDProducts);
                Files.write(pathIDProducts, idProduct.toString().getBytes());

            } else {
                idProduct = Integer.parseInt(Files.readString(pathIDProducts));

            }

        } catch (FileAlreadyExistsException e) {
            System.out.println("File already exists - " + e.getMessage());
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public void save(Product newProduct) {
        newProduct.setId(++idProduct);

        try {
            Files.write(pathProducts, (newProduct.toStringForFiles() + "\n").getBytes(), StandardOpenOption.APPEND);
            Files.write(pathIDProducts, idProduct.toString().getBytes());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public List<Product> findAll() {
        try {
            return Files.readAllLines(pathProducts)
                    .stream()
                    .map(Product::new)
                    .toList();
        } catch (IOException e) {
            throw new ProductNotFoundException(e.getMessage());
        }

    }

    public Product findById(int index) {
        return findAll().stream()
                .filter(c -> c.getId().equals(index))
                .findFirst().orElse(null);

    }


}


