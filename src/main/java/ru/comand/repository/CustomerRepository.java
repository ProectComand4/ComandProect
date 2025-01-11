package ru.comand.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.comand.model.Customer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;


public class CustomerRepository {
    private final Path filePath;
    private final Path filePathID;
    private Integer id;
    private static final Logger logger = LoggerFactory.getLogger(CustomerRepository.class);

    public CustomerRepository() {
        id = 0;
        this.filePath = Path.of("src/main/java/ru/comand/repository/Files/customer.txt");
        this.filePathID = Path.of("src/main/java/ru/comand/repository/Files/customerID.txt");
        try {
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
            if (Files.exists(filePathID)) {
                if (Files.readAllLines(filePath).stream()
                        .map(Customer::new)
                        .max(Comparator.comparingInt(Customer::getId))
                        .isPresent()) {
                    Customer customer = Files.readAllLines(filePath).stream()
                            .map(Customer::new)
                            .max(Comparator.comparingInt(Customer::getId))
                            .get();
                    Files.write(filePathID, customer.getId().toString().getBytes());
                    id = Integer.parseInt(Files.readString(filePathID));
                }
            } else {
                Files.createFile(filePathID);
                Files.write(filePathID, id.toString().getBytes());
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла - " + e.getMessage());
        } catch (NoSuchElementException e) {
            logger.warn("Файл с покупателями пустой");
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
            System.out.println("Ошибка чтения файла - " + e.getMessage());
        }
        try {
            Files.write(filePathID, id.toString().getBytes());
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла - " + e.getMessage());
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
                    .map(Customer::new)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла - " + e.getMessage());
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
