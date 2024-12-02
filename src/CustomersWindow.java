import javax.swing.*;
import java.awt.*;

public class CustomersWindow extends JFrame {

    public CustomersWindow(ContractManagementSystem system) {
        setTitle("Замовники");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton addButton = new JButton("Додати замовника");
        addButton.addActionListener(e -> new AddCustomerForm(system));
        panel.add(addButton);


        JButton deleteButton = new JButton("Видалити замовника");
        deleteButton.addActionListener(e -> deleteCustomer(system));

        JButton viewButton = new JButton("Переглянути замовників");
        viewButton.addActionListener(e -> viewCustomers(system));

        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(viewButton);

        add(panel);
        setVisible(true);
    }

    private void deleteCustomer(ContractManagementSystem system) {
        if (system.getCustomers().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Список замовників порожній.", "Помилка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] customers = system.getCustomers().stream()
                .map(c -> "ПІБ: " + c.getFullName() + ", Підприємство: " + c.getEnterpriseName())
                .toArray(String[]::new);

        String selectedCustomer = (String) JOptionPane.showInputDialog(
                this,
                "Оберіть замовника для видалення:",
                "Видалення замовника",
                JOptionPane.PLAIN_MESSAGE,
                null,
                customers,
                customers[0]
        );

        if (selectedCustomer != null) {
            system.getCustomers().removeIf(c -> selectedCustomer.contains(c.getFullName()));
            JOptionPane.showMessageDialog(this, "Замовника видалено успішно!");
        }
    }

    private void viewCustomers(ContractManagementSystem system) {
        if (system.getCustomers().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Список замовників порожній.", "Помилка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] columnNames = {"ID Контракту", "Підприємство", "Замовник", "Адреса", "Телефон"};
        Object[][] data = system.getCustomers().stream()
                .map(customer -> new Object[]{
                        customer.getContractId(),
                        customer.getEnterpriseName(),
                        customer.getFullName(),
                        customer.getAddress(),
                        customer.getPhoneNumber()
                })
                .toArray(Object[][]::new);

        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);

        JFrame tableFrame = new JFrame("Список замовників");
        tableFrame.setSize(600, 400);
        tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tableFrame.setLocationRelativeTo(this);

        JScrollPane scrollPane = new JScrollPane(table);
        tableFrame.add(scrollPane);

        tableFrame.setVisible(true);
    }
}


