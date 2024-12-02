import javax.swing.*;
import java.awt.*;

public class AddContractForm extends JFrame {

    private JTextField idField;
    private JTextField productTypeField;
    private JTextField quantityField;
    private JTextField deliveryTermField;
    private JTextField costField;

    public AddContractForm(ContractManagementSystem system) {
        setTitle("Додати новий контракт");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

        panel.add(new JLabel("ID контракту:"));
        idField = new JTextField();
        panel.add(idField);

        panel.add(new JLabel("Тип продукту:"));
        productTypeField = new JTextField();
        panel.add(productTypeField);

        panel.add(new JLabel("Кількість:"));
        quantityField = new JTextField();
        panel.add(quantityField);

        panel.add(new JLabel("Термін поставки:"));
        deliveryTermField = new JTextField();
        panel.add(deliveryTermField);

        panel.add(new JLabel("Вартість:"));
        costField = new JTextField();
        panel.add(costField);

        JButton saveButton = new JButton("Зберегти");
        saveButton.addActionListener(e -> saveContract(system));
        panel.add(saveButton);

        JButton cancelButton = new JButton("Скасувати");
        cancelButton.addActionListener(e -> dispose());
        panel.add(cancelButton);

        add(panel);
        setVisible(true);
    }

    private void saveContract(ContractManagementSystem system) {
        StringBuilder errorMessage = new StringBuilder();

        try {
            int contractId = Integer.parseInt(idField.getText().trim());
            if (!system.isContractIdUnique(contractId)) {
                errorMessage.append("ID контракту вже існує.\n");
            }

            String productType = productTypeField.getText().trim();
            if (productType.isEmpty()) {
                errorMessage.append("Тип продукту не може бути порожнім.\n");
            }

            int quantity = Integer.parseInt(quantityField.getText().trim());
            if (quantity <= 0) {
                errorMessage.append("Кількість повинна бути більше 0.\n");
            }

            String deliveryTerm = deliveryTermField.getText().trim();
            if (deliveryTerm.isEmpty()) {
                errorMessage.append("Термін поставки не може бути порожнім.\n");
            }

            double cost = Double.parseDouble(costField.getText().trim());
            if (cost <= 0) {
                errorMessage.append("Вартість повинна бути більше 0.\n");
            }

            if (errorMessage.length() > 0) {
                JOptionPane.showMessageDialog(this, errorMessage.toString(), "Помилка", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SupplyContract contract = new SupplyContract(productType, quantity, deliveryTerm, cost, contractId);
            system.addContract(contract);
            JOptionPane.showMessageDialog(this, "Контракт успішно додано!");
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Перевірте формат числових полів (ID, кількість, вартість).", "Помилка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
