import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class DeleteDetails extends JFrame {
    private JTextField employeeIdField;
    private JTextArea resultArea;
    private JButton deleteButton;
    DbStore db = new DbStore();

    public DeleteDetails() {
        setTitle("Delete Employee");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Delete panel for ID input
        JPanel deletePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel idLabel = new JLabel("Employee ID:");
        employeeIdField = new JTextField(15);
        JButton findButton = new JButton("Find");
        deleteButton = new JButton("Delete");
        deleteButton.setEnabled(false); // Initially disabled until employee is found

        deletePanel.add(idLabel);
        deletePanel.add(employeeIdField);
        deletePanel.add(findButton);
        deletePanel.add(deleteButton);

        // Results area
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 14));

        // Add components to main panel
        mainPanel.add(deletePanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Find button action listener
        findButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findEmployee();
            }
        });

        // Delete button action listener
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteEmployee();
            }
        });

        add(mainPanel);
    }

    private void findEmployee() {
        String employeeId = employeeIdField.getText().trim();
        if (!employeeId.isEmpty()) {
            try {
                int id = Integer.parseInt(employeeId);
                String[] data = db.searchData(id);
                
                if (data != null && data.length > 0) {
                    // Create a formatted string with employee details
                    StringBuilder result = new StringBuilder();
                    result.append("Employee Details to be deleted:\n\n");
                    result.append("ID: ").append(data[0]).append("\n");
                    result.append("Name: ").append(data[1]).append("\n");
                    result.append("Age: ").append(data[2]).append("\n");
                    result.append("Department: ").append(data[3]).append("\n");
                    result.append("Email: ").append(data[4]).append("\n\n");
                    result.append("Click 'Delete' button to confirm deletion.");
                    
                    // Set the formatted text in result area
                    resultArea.setText(result.toString());
                    deleteButton.setEnabled(true); // Enable delete button
                } else {
                    resultArea.setText("No employee found with ID: " + id);
                    deleteButton.setEnabled(false);
                }
            } catch (NumberFormatException ex) {
                resultArea.setText("Please enter a valid numeric ID");
                deleteButton.setEnabled(false);
            } catch (Exception ex) {
                resultArea.setText("Error retrieving employee data: " + ex.getMessage());
                deleteButton.setEnabled(false);
            }
        } else {
            resultArea.setText("Please enter an Employee ID");
            deleteButton.setEnabled(false);
        }
    }

    private void deleteEmployee() {
        String employeeId = employeeIdField.getText().trim();
        try {
            int id = Integer.parseInt(employeeId);
            
            // Show confirmation dialog
            int response = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this employee?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (response == JOptionPane.YES_OPTION) {
                boolean deleted = db.deleteData(id);
                if (deleted) {
                    resultArea.setText("Employee with ID " + id + " has been successfully deleted.");
                    deleteButton.setEnabled(false);
                    employeeIdField.setText("");
                } else {
                    resultArea.setText("Failed to delete employee with ID " + id);
                }
            }
        } catch (NumberFormatException ex) {
            resultArea.setText("Invalid employee ID format");
        } catch (Exception ex) {
            resultArea.setText("Error deleting employee: " + ex.getMessage());
        }
    }


}