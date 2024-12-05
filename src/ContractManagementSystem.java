import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.io.*;
import javax.swing.*;

public class ContractManagementSystem {
    private List<SalesEngineer> engineers;
    private List<Customer> customers;
    private List<SupplyContract> contracts;

    public ContractManagementSystem() {
        engineers = new ArrayList<>();
        customers = new ArrayList<>();
        contracts = new ArrayList<>();
    }

    public void addSalesEngineer(SalesEngineer engineer) {
        engineers.add(engineer);
    }

    public List<SalesEngineer> getSalesEngineers() {
        return engineers;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void addContract(SupplyContract contract) {
        contracts.add(contract);
    }

    public List<SupplyContract> getContracts() {
        return contracts;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public double getAverageProductQuantity() {
        if (contracts.isEmpty()) {
            return 0;
        }
        int totalQuantity = 0;
        for (SupplyContract contract : contracts) {
            totalQuantity += contract.getQuantity();
        }
        return (double) totalQuantity / contracts.size();
    }

    public SupplyContract getMaxCostContract() {
        return contracts.stream()
                .max(Comparator.comparingDouble(SupplyContract::getCost))
                .orElse(null);
    }

    public SupplyContract getMaxDeliveryTermContract() {
        return contracts.stream()
                .max(Comparator.comparingInt(SupplyContract::getDeliveryTermInDays))
                .orElse(null);
    }

    public boolean isContractIdUnique(int contractId) {
        return contracts.stream().noneMatch(contract -> contract.getContractId() == contractId);
    }

    public boolean isCustomerContractIdUnique(int contractId) {
        return customers.stream().noneMatch(customer -> customer.getContractId() == contractId);
    }

    public boolean isEngineerUnique(String fullName, String enterpriseName) {
        return engineers.stream().noneMatch(engineer ->
                engineer.getFullName().equalsIgnoreCase(fullName) &&
                        engineer.getEnterpriseName().equalsIgnoreCase(enterpriseName)
        );
    }

    public void saveData(JFrame parent) {
        if (contracts.isEmpty() && customers.isEmpty() && engineers.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Контракти, замовники та інженери відсутні. Немає даних для збереження.", "Попередження", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            File directory = new File("src");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            try (BufferedWriter contractWriter = new BufferedWriter(new FileWriter("src/data/contracts.txt"));
                 BufferedWriter customerWriter = new BufferedWriter(new FileWriter("src/data/customers.txt"));
                 BufferedWriter engineerWriter = new BufferedWriter(new FileWriter("src/data/sales_engineers.txt"))) {

                if (!contracts.isEmpty()) {
                    for (SupplyContract contract : contracts) {
                        contractWriter.write("ID: " + contract.getContractId());
                        contractWriter.newLine();
                        contractWriter.write("Тип продукту: " + contract.getProductType());
                        contractWriter.newLine();
                        contractWriter.write("Кількість: " + contract.getQuantity());
                        contractWriter.newLine();
                        contractWriter.write("Термін поставки: " + contract.getDeliveryTerm());
                        contractWriter.newLine();
                        contractWriter.write("Вартість: " + contract.getCost());
                        contractWriter.newLine();
                        contractWriter.newLine();
                    }
                }

                if (!customers.isEmpty()) {
                    for (Customer customer : customers) {
                        customerWriter.write("ID контракту: " + customer.getContractId());
                        customerWriter.newLine();
                        customerWriter.write("Підприємство: " + customer.getEnterpriseName());
                        customerWriter.newLine();
                        customerWriter.write("Замовник: " + customer.getFullName());
                        customerWriter.newLine();
                        customerWriter.write("Адреса: " + customer.getAddress());
                        customerWriter.newLine();
                        customerWriter.write("Телефон: " + customer.getPhoneNumber());
                        customerWriter.newLine();
                        customerWriter.newLine();
                    }
                }

                if (!engineers.isEmpty()) {
                    for (SalesEngineer engineer : engineers) {
                        engineerWriter.write("Підприємство: " + engineer.getEnterpriseName());
                        engineerWriter.newLine();
                        engineerWriter.write("Інженер: " + engineer.getFullName());
                        engineerWriter.newLine();
                        engineerWriter.write("Адреса: " + engineer.getAddress());
                        engineerWriter.newLine();
                        engineerWriter.write("Телефон: " + engineer.getPhoneNumber());
                        engineerWriter.newLine();
                        engineerWriter.write("Стаж роботи: " + engineer.getWorkExperience() + " років");
                        engineerWriter.newLine();
                        engineerWriter.newLine();
                    }
                }

                JOptionPane.showMessageDialog(parent, "Дані успішно збережені!", "Успіх", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(parent, "Помилка під час збереження даних: " + e.getMessage(), "Помилка", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadData(JFrame parent) {
        try {
            File directory = new File("src");
            if (!directory.exists()) {
                throw new FileNotFoundException("Папка src не знайдена. Спершу збережіть дані.");
            }

            try (BufferedReader contractReader = new BufferedReader(new FileReader("src/data/contracts.txt"));
                 BufferedReader customerReader = new BufferedReader(new FileReader("src/data/customers.txt"));
                 BufferedReader engineerReader = new BufferedReader(new FileReader("src/data/sales_engineers.txt"))) {

                String line;
                while ((line = contractReader.readLine()) != null) {
                    if (line.startsWith("ID: ")) {
                        int contractId = Integer.parseInt(line.substring(4).trim());
                        String productType = contractReader.readLine().substring(13).trim();
                        int quantity = Integer.parseInt(contractReader.readLine().substring(10).trim());
                        String deliveryTerm = contractReader.readLine().substring(16).trim();
                        double cost = Double.parseDouble(contractReader.readLine().substring(9).trim());
                        contracts.add(new SupplyContract(productType, quantity, deliveryTerm, cost, contractId));
                        contractReader.readLine();
                    }
                }

                while ((line = customerReader.readLine()) != null) {
                    if (line.startsWith("ID контракту: ")) {
                        int contractId = Integer.parseInt(line.substring(14).trim());
                        String enterpriseName = customerReader.readLine().substring(13).trim();
                        String fullName = customerReader.readLine().substring(9).trim();
                        String address = customerReader.readLine().substring(8).trim();
                        String phoneNumber = customerReader.readLine().substring(9).trim();
                        customers.add(new Customer(enterpriseName, fullName, address, phoneNumber, contractId));
                        customerReader.readLine();
                    }
                }

                while ((line = engineerReader.readLine()) != null) {
                    if (line.startsWith("Підприємство: ")) {
                        String enterpriseName = line.substring(14).trim();
                        String fullName = engineerReader.readLine().substring(9).trim();
                        String address = engineerReader.readLine().substring(8).trim();
                        String phoneNumber = engineerReader.readLine().substring(9).trim();
                        int workExperience = Integer.parseInt(engineerReader.readLine().substring(13).trim().replace(" років", ""));
                        engineers.add(new SalesEngineer(enterpriseName, fullName, address, phoneNumber, workExperience));
                        engineerReader.readLine();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(parent, "Файли або папка src не знайдені. Будь ласка, спершу збережіть дані.", "Попередження", JOptionPane.WARNING_MESSAGE);
        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(parent, "Помилка під час завантаження даних: " + e.getMessage(), "Помилка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
