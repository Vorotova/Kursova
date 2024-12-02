import javax.swing.*;
import java.awt.*;

public class ContractsWindow extends JFrame {

    public ContractsWindow(ContractManagementSystem system) {
        setTitle("Контракти");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));

        JButton addButton = new JButton("Додати контракт");
        addButton.addActionListener(e -> new AddContractForm(system));
        panel.add(addButton);

        JButton deleteButton = new JButton("Видалити контракт");
        deleteButton.addActionListener(e -> deleteContract(system));

        JButton viewButton = new JButton("Переглянути контракти");
        viewButton.addActionListener(e -> viewContracts(system));

        JButton avgButton = new JButton("Середня кількість продуктів");
        avgButton.addActionListener(e -> calculateAverage(system));

        JButton maxCostButton = new JButton("Найбільша вартість");
        maxCostButton.addActionListener(e -> findMaxCostContract(system));

        JButton maxTermButton = new JButton("Найбільший термін поставки");
        maxTermButton.addActionListener(e -> findMaxDeliveryTerm(system));

        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(viewButton);
        panel.add(avgButton);
        panel.add(maxCostButton);
        panel.add(maxTermButton);

        add(panel);
        setVisible(true);
    }

    private void deleteContract(ContractManagementSystem system) {
        if (system.getContracts().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Список контрактів порожній.", "Помилка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] contracts = system.getContracts().stream()
                .map(c -> "ID: " + c.getContractId() + ", " + c.getProductType())
                .toArray(String[]::new);

        String selectedContract = (String) JOptionPane.showInputDialog(
                this,
                "Оберіть контракт для видалення:",
                "Видалення контракту",
                JOptionPane.PLAIN_MESSAGE,
                null,
                contracts,
                contracts[0]
        );

        if (selectedContract != null) {
            system.getContracts().removeIf(c -> selectedContract.contains("ID: " + c.getContractId()));
            JOptionPane.showMessageDialog(this, "Контракт видалено успішно!");
        }
    }

    private void viewContracts(ContractManagementSystem system) {
        if (system.getContracts().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Список контрактів порожній.", "Помилка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] columnNames = {"ID", "Тип продукту", "Кількість", "Термін поставки", "Вартість"};
        Object[][] data = system.getContracts().stream()
                .map(contract -> new Object[]{
                        contract.getContractId(),
                        contract.getProductType(),
                        contract.getQuantity(),
                        contract.getDeliveryTerm(),
                        contract.getCost()
                })
                .toArray(Object[][]::new);

        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);

        JFrame tableFrame = new JFrame("Список контрактів");
        tableFrame.setSize(600, 400);
        tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tableFrame.setLocationRelativeTo(this);

        JScrollPane scrollPane = new JScrollPane(table);
        tableFrame.add(scrollPane);

        tableFrame.setVisible(true);
    }

    private void calculateAverage(ContractManagementSystem system) {
        double average = system.getAverageProductQuantity();
        JOptionPane.showMessageDialog(this, "Середня кількість продуктів у контракті: " + average);
    }

    private void findMaxCostContract(ContractManagementSystem system) {
        SupplyContract contract = system.getMaxCostContract();
        if (contract != null) {
            JOptionPane.showMessageDialog(this, "Контракт з найбільшою вартістю:\n" + contract.toString());
        } else {
            JOptionPane.showMessageDialog(this, "Контрактів немає.", "Помилка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void findMaxDeliveryTerm(ContractManagementSystem system) {
        SupplyContract contract = system.getMaxDeliveryTermContract();
        if (contract != null) {
            JOptionPane.showMessageDialog(this, "Контракт із найбільшим терміном поставки:\n" + contract.toString());
        } else {
            JOptionPane.showMessageDialog(this, "Контрактів немає.", "Помилка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
