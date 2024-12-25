package ru.comand.repository;

import ru.comand.model.Customer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class CustomerRepository {
    private final Path filePath;
    private final Path filePathID;
    private Integer id;

    public CustomerRepository(String file) {
        id = 0;
        this.filePath = Path.of(file);
        this.filePathID = Path.of(file + "_id");
        try {
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
            if (Files.exists(filePathID)) {
                id = Integer.parseInt(Files.readString(filePathID));
            } else {
                Files.createFile(filePathID);
                Files.write(filePathID, id.toString().getBytes());
            }
        } catch (IOException e) {
            System.out.println("Файл не найден - " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Файл с ID пустой");
        }
    }

    /**
     * Сохраняет информацию о покупателе в файл
     * @param customer покупатель
     * @return покупатель
     */
    public Customer save(Customer customer) {
        customer.setId(++id);
        try {
            Files.write(filePath, (customer.toStringForFiles() + "\n").getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Файл не найден - " + e.getMessage());
        }
        try {
            Files.write(filePathID, id.toString().getBytes());
        } catch (IOException e) {
            System.out.println("Файл не найден - " + e.getMessage());
        }
        return customer;
    }

    /**
     * Собирает всех покупателей в список
     * @return список покупателей
     */
    public List<Customer> findAll() {
        try {
            return Files.readAllLines(filePath).stream()
                    .map(l -> new Customer(l))
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("Не удалось найти файл - " + e.getMessage());
        }
    }

    /**
     * Находит покупателя по ID
     * @param ID ID покупателя
     * @return покупатель с указанным ID
     */
    public Customer findById(int ID) {
        return findAll().stream()
                .filter(c -> c.getId().equals(ID))
                .findFirst().orElse(null);
    }
}
