import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {

    public MainWindow(ContractManagementSystem system) {
        setTitle("Відділ збуту готової продукції");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        addButton(panel, "Контракти", e -> new ContractsWindow(system));
        addButton(panel, "Замовники", e -> new CustomersWindow(system));
        addButton(panel, "Інженери з продажу", e -> new SalesEngineersWindow(system));
        addButton(panel, "Зберегти дані", e -> system.saveData(this));

        addButton(panel, "Завантажити дані", e -> {
            system.loadData(this);
            JOptionPane.showMessageDialog(this, "Дані успішно завантажені!");
        });

        add(panel);
        setVisible(true);
    }

    private void addButton(JPanel panel, String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        panel.add(button);
    }

    public static void main(String[] args) {
        ContractManagementSystem system = new ContractManagementSystem();
        new MainWindow(system);
    }
}


