import javax.swing.*;
import java.awt.*;

public class AddCustomerForm extends JFrame {

    private JTextField idField;
    private JTextField enterpriseNameField;
    private JTextField fullNameField;
    private JTextField addressField;
    private JTextField phoneNumberField;

    public AddCustomerForm(ContractManagementSystem system) {
        setTitle("Додати нового замовника");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

        panel.add(new JLabel("ID контракту:"));
        idField = new JTextField();
        panel.add(idField);

        panel.add(new JLabel("Назва підприємства:"));
        enterpriseNameField = new JTextField();
        panel.add(enterpriseNameField);

        panel.add(new JLabel("ПІБ:"));
        fullNameField = new JTextField();
        panel.add(fullNameField);

        panel.add(new JLabel("Адреса:"));
        addressField = new JTextField();
        panel.add(addressField);

        panel.add(new JLabel("Телефон:"));
        phoneNumberField = new JTextField();
        panel.add(phoneNumberField);

        JButton saveButton = new JButton("Зберегти");
        saveButton.addActionListener(e -> saveCustomer(system));
        panel.add(saveButton);

        JButton cancelButton = new JButton("Скасувати");
        cancelButton.addActionListener(e -> dispose());
        panel.add(cancelButton);

        add(panel);
        setVisible(true);
    }

    private void saveCustomer(ContractManagementSystem system) {
        StringBuilder errorMessage = new StringBuilder();

        try {
            int contractId = Integer.parseInt(idField.getText().trim());
            if (!system.isCustomerContractIdUnique(contractId)) {
                errorMessage.append("Замовник із таким ID контракту вже існує.\n");
            }

            String enterpriseName = enterpriseNameField.getText().trim();
            if (enterpriseName.isEmpty()) {
                errorMessage.append("Назва підприємства не може бути порожньою.\n");
            }

            String fullName = fullNameField.getText().trim();
            if (fullName.isEmpty()) {
                errorMessage.append("ПІБ не може бути порожнім.\n");
            }

            String address = addressField.getText().trim();
            if (address.isEmpty()) {
                errorMessage.append("Адреса не може бути порожньою.\n");
            }

            String phoneNumber = phoneNumberField.getText().trim();
            if (!phoneNumber.matches("\\+\\d{12}")) {
                errorMessage.append("Номер телефону повинен бути у форматі +380XXXXXXXXX.\n");
            }

            if (errorMessage.length() > 0) {
                JOptionPane.showMessageDialog(this, errorMessage.toString(), "Помилка", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Customer customer = new Customer(enterpriseName, fullName, address, phoneNumber, contractId);
            system.addCustomer(customer);

            JOptionPane.showMessageDialog(this, "Замовника успішно додано!");
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Перевірте формат числових полів (ID контракту).", "Помилка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
