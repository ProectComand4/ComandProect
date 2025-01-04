package ru.comand.repository;

import ru.comand.Exceptions.ProductNotFoundException;
import ru.comand.model.Order;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class OrderRepository {

    public final Path pathOrder =
            Path.of("src/main/java/ru/comand/repository/files/order.txt");
    public final Path pathIdOrder =
            Path.of("src/main/java/ru/comand/repository/files/idOrder.txt");
    public Integer idOrder = 0;

    public OrderRepository() {

        try {

            if (!Files.exists(Paths.get(pathOrder.toString()))) {
                Files.createFile(pathOrder);
            }
            if (!Files.exists(Paths.get(pathIdOrder.toString()))) {
                Files.createFile(pathIdOrder);
                Files.write(pathIdOrder, idOrder.toString().getBytes());

            } else {
                idOrder = Integer.parseInt(Files.readString(pathIdOrder));

            }

        } catch (FileAlreadyExistsException e) {
            System.out.println("File already exists - " + e.getMessage());
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public Order save(Order newOrder) {
        newOrder.setId(++idOrder);
        try {
            Files.write(pathOrder, (newOrder.toStringForFiles()).getBytes(), StandardOpenOption.APPEND);
            Files.write(pathIdOrder, idOrder.toString().getBytes());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return newOrder;
    }

    public List<Order> findAll() {
        try {
            return Files.readAllLines(pathOrder)
                    .stream()
                    .map(Order::new)
                    .toList();
        } catch (IOException e) {
            throw new ProductNotFoundException(e.getMessage());
        }

    }

    public Order findById(int index) {
        return findAll().stream()
                .filter(c -> c.getId().equals(index))
                .findFirst().orElse(null);

    }
}
