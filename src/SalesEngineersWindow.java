import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

class SalesEngineersWindow extends JFrame {

    public SalesEngineersWindow(ContractManagementSystem system) {
        setTitle("Інженери з продажу");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        addButton(panel, "Додати інженера", e -> new AddEngineerForm(system));
        addButton(panel, "Видалити інженера", e -> deleteEngineer(system));
        addButton(panel, "Переглянути інженерів", e -> viewEngineers(system));

        add(panel);
        setVisible(true);
    }

    private void addButton(JPanel panel, String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        panel.add(button);
    }

    private void deleteEngineer(ContractManagementSystem system) {
        if (system.getSalesEngineers().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Список інженерів порожній.");
            return;
        }

        String[] engineers = system.getSalesEngineers().stream()
                .map(e -> e.getFullName() + " (" + e.getEnterpriseName() + ")")
                .toArray(String[]::new);

        String selectedEngineer = (String) JOptionPane.showInputDialog(
                this,
                "Оберіть інженера для видалення:",
                "Видалення інженера",
                JOptionPane.PLAIN_MESSAGE,
                null,
                engineers,
                engineers[0]
        );

        if (selectedEngineer != null) {
            system.getSalesEngineers().removeIf(e -> selectedEngineer.contains(e.getFullName()));
            JOptionPane.showMessageDialog(this, "Інженера видалено успішно!");
        }
    }

    private void viewEngineers(ContractManagementSystem system) {
        if (system.getSalesEngineers().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Список інженерів порожній.");
            return;
        }

        String[] columnNames = {"Підприємство", "Інженер", "Адреса", "Телефон", "Стаж роботи"};
        Object[][] data = system.getSalesEngineers().stream()
                .map(e -> new Object[]{e.getEnterpriseName(), e.getFullName(), e.getAddress(), e.getPhoneNumber(), e.getWorkExperience()})
                .toArray(Object[][]::new);

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        JFrame frame = new JFrame("Список інженерів");
        frame.setSize(600, 400);
        frame.add(scrollPane);
        frame.setVisible(true);
    }
}
