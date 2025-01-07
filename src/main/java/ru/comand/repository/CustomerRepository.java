package ru.comand.repository;

import ru.comand.model.Customer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;


public class CustomerRepository {
    private final Path filePath =
            Path.of("src/main/java/ru/comand/repository/files/customer.txt");
    private final Path filePathID =
            Path.of("src/main/java/ru/comand/repository/files/idCustomer.txt");
    private Integer id = 0;

    public CustomerRepository() {
        try {
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
            if (Files.exists(filePathID)) {
                if (Objects.equals(Files.readString(filePathID), "")) {
                    Customer customer = Files.readAllLines(filePath).stream()
                            .map(Customer::new)
                            .max((c1, c2) -> Integer.compare(c1.getId(), c2.getId()))
                            .get();
                    Files.write(filePathID, customer.getId().toString().getBytes());
                } // работает, но, если файл с покупателями пустой, программа начинается с описания исключения NoSuchElementException
                id = Integer.parseInt(Files.readString(filePathID));
            } else {
                Files.createFile(filePathID);
                Files.write(filePathID, id.toString().getBytes());
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла - " + e.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println("Файл с покупателями пустой");
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
