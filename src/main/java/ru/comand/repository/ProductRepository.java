package ru.comand.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.comand.Exceptions.ProductNotFoundException;
import ru.comand.model.Product;

import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;


public class ProductRepository {
    public final Path pathProducts =
            Path.of("src/main/java/ru/comand/repository/files/products.txt");
    public final Path pathIDProducts =
            Path.of("src/main/java/ru/comand/repository/files/idProducts.txt");
    public Integer idProduct = 0;
    private static final Logger logger = LoggerFactory.getLogger(ProductRepository.class);


    public ProductRepository() {

        try {

            if (!Files.exists(Paths.get(pathProducts.toString()))) {
                Files.createFile(pathProducts);
            }
            if (Files.exists(pathIDProducts)) {
                if (Files.readAllLines(pathProducts).stream()
                        .map(Product::new)
                        .max(Comparator.comparingInt(Product::getId))
                        .isPresent()) {
                    Product product = Files.readAllLines(pathProducts).stream()
                            .map(Product::new)
                            .max(Comparator.comparingInt(Product::getId))
                            .get();
                    Files.write(pathIDProducts, product.getId().toString().getBytes());
                    idProduct = Integer.parseInt(Files.readString(pathIDProducts));
                }
            } else {
                Files.createFile(pathIDProducts);
                Files.write(pathIDProducts, idProduct.toString().getBytes());
            }

        } catch (FileAlreadyExistsException e) {
            System.out.println("File already exists - " + e.getMessage());
        } catch (IOException e) {
            logger.warn(e.getMessage());
        } catch (NoSuchElementException e) {
            logger.warn("Файл с товарами пустой");
        }

    }

    /**
     * Сохраняет информацию о продукте в файл
     * @param newProduct продукт
     * @return продукт
     */
    public void save(Product newProduct) {
        newProduct.setId(++idProduct);

        try {
            Files.write(pathProducts, (newProduct.toStringForFiles() + "\n").getBytes(), StandardOpenOption.APPEND);
            Files.write(pathIDProducts, idProduct.toString().getBytes());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Собирает все продукты в список
     * @return список продуктов
     */
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

    /**
     * Находит продукт по id
     * @param id id заказа
     * @return заказ с указанным ID
     */
    public Product findById(int id) {
        return findAll().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst().orElse(null);

    }


}


