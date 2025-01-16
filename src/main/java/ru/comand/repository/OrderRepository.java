package ru.comand.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.comand.model.Order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class OrderRepository {

    private final Path filePath;
    private final Path filePathID;
    private Integer id;
    private static final Logger logger = LoggerFactory.getLogger(OrderRepository.class);

    public OrderRepository(String path, String pathId) {
        id = 0;
        this.filePath = Path.of(path);
        this.filePathID = Path.of(pathId);
        try {
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
            if (Files.exists(filePathID)) {
                if (Files.readAllLines(filePath).stream()
                        .map(Order::new)
                        .max(Comparator.comparingInt(Order::getId))
                        .isPresent()) {
                    Order order = Files.readAllLines(filePath).stream()
                            .map(Order::new)
                            .max(Comparator.comparingInt(Order::getId))
                            .get();
                    Files.write(filePathID, order.getId().toString().getBytes());
                    id = Integer.parseInt(Files.readString(filePathID));
                }
            } else {
                Files.createFile(filePathID);
                Files.write(filePathID, id.toString().getBytes());
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла - " + e.getMessage());
        } catch (NoSuchElementException e) {
            logger.warn("Файл с заказами пустой");
        }
    }

    /**
     * Сохраняет информацию о заказе в файл
     * @param order заказ
     * @return заказ
     */
    public Order save(Order order) {
        order.setId(++id);
        try {
            Files.write(filePath, (order.toStringForFiles() + "\n").getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла - " + e.getMessage());
        }
        try {
            Files.write(filePathID, id.toString().getBytes());
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла - " + e.getMessage());
        }
        return order;
    }

    /**
     * Собирает все заказы в список
     * @return список заказов
     */
    public List<Order> findAll() {
        try {
            return Files.readAllLines(filePath).stream()
                    .map(Order::new)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла - " + e.getMessage());
        }
    }

    /**
     * Находит заказ по ID
     * @param ID ID заказа
     * @return заказ с указанным ID
     */
    public Order findById(int ID) {
        return findAll().stream()
                .filter(o -> o.getId().equals(ID))
                .findFirst().orElse(null);
    }

    public void saveStatus(List<Order> orderList) {
        try {
            Files.write(filePath, "".getBytes());
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла - " + e.getMessage());
        }
        for (Order order : orderList) {
            try {
                Files.write(filePath, (order.toStringForFiles() + "\n").getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                System.out.println("Ошибка чтения файла - " + e.getMessage());
            }
        }
    }
}
