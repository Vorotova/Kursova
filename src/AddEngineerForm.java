import javax.swing.*;
import java.awt.*;

public class AddEngineerForm extends JFrame {

    private JTextField enterpriseNameField;
    private JTextField fullNameField;
    private JTextField addressField;
    private JTextField phoneNumberField;
    private JTextField workExperienceField;

    public AddEngineerForm(ContractManagementSystem system) {
        setTitle("Додати нового інженера");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

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

        panel.add(new JLabel("Стаж роботи (у роках):"));
        workExperienceField = new JTextField();
        panel.add(workExperienceField);

        JButton saveButton = new JButton("Зберегти");
        saveButton.addActionListener(e -> saveEngineer(system));
        panel.add(saveButton);

        JButton cancelButton = new JButton("Скасувати");
        cancelButton.addActionListener(e -> dispose());
        panel.add(cancelButton);

        add(panel);
        setVisible(true);
    }

    private void saveEngineer(ContractManagementSystem system) {
        StringBuilder errorMessage = new StringBuilder();

        try {
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

            int workExperience = Integer.parseInt(workExperienceField.getText().trim());
            if (workExperience < 0) {
                errorMessage.append("Стаж роботи не може бути від'ємним.\n");
            }

            if (!system.isEngineerUnique(fullName, enterpriseName)) {
                errorMessage.append("Інженер із таким ПІБ і підприємством вже існує.\n");
            }

            if (errorMessage.length() > 0) {
                JOptionPane.showMessageDialog(this, errorMessage.toString(), "Помилка", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SalesEngineer engineer = new SalesEngineer(enterpriseName, fullName, address, phoneNumber, workExperience);
            system.addSalesEngineer(engineer);

            JOptionPane.showMessageDialog(this, "Інженера успішно додано!");
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Перевірте формат числового поля (Стаж роботи).", "Помилка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
