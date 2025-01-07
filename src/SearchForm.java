import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class SearchForm extends JFrame {
    private JTextField employeeIdField;
    private JTextArea resultArea;
    private JButton editButton;
    private JButton saveButton;
    private boolean isEditing = false;
    DbStore db = new DbStore();

    public SearchForm() {
        setTitle("Search Employee");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Search panel for ID input
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel idLabel = new JLabel("Employee ID:");
        employeeIdField = new JTextField(15);
        JButton findButton = new JButton("Find");
        editButton = new JButton("Edit");
        saveButton = new JButton("Save");
        
        // Initially set button states
        editButton.setEnabled(false);
        saveButton.setVisible(false);

        searchPanel.add(idLabel);
        searchPanel.add(employeeIdField);
        searchPanel.add(findButton);
        searchPanel.add(editButton);
        searchPanel.add(saveButton);

        // Results area
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false); // Initially not editable
        JScrollPane scrollPane = new JScrollPane(resultArea);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 14));

        // Add components to main panel
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Find button action listener
        findButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String employeeId = employeeIdField.getText().trim();
                if (!employeeId.isEmpty()) {
                    try {
                        int id = Integer.parseInt(employeeId);
                        String[] data = db.searchData(id);
                        
                        if (data != null && data.length > 0) {
                            // Create a formatted string with employee details
                            StringBuilder result = new StringBuilder();
                            result.append("Employee Details:\n\n");
                            result.append("ID: ").append(data[0]).append("\n");
                            result.append("Name: ").append(data[1]).append("\n");
                            result.append("Age: ").append(data[2]).append("\n");
                            result.append("Department: ").append(data[3]).append("\n");
                            result.append("Email: ").append(data[4]);
                            
                            resultArea.setText(result.toString());
                            editButton.setEnabled(true);
                            saveButton.setVisible(false);
                            resultArea.setEditable(false);
                            isEditing = false;
                        } else {
                            resultArea.setText("No employee found with ID: " + id);
                            editButton.setEnabled(false);
                            saveButton.setVisible(false);
                        }
                    } catch (NumberFormatException ex) {
                        resultArea.setText("Please enter a valid numeric ID");
                        editButton.setEnabled(false);
                        saveButton.setVisible(false);
                    } catch (Exception ex) {
                        resultArea.setText("Error retrieving employee data: " + ex.getMessage());
                        editButton.setEnabled(false);
                        saveButton.setVisible(false);
                    }
                } else {
                    resultArea.setText("Please enter an Employee ID");
                    editButton.setEnabled(false);
                    saveButton.setVisible(false);
                }
            }
        });

        // Edit button action listener
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!isEditing) {
                    // Enable editing
                    resultArea.setEditable(true);
                    isEditing = true;
                    editButton.setVisible(false);
                    saveButton.setVisible(true);
                    resultArea.requestFocus();
                }
            }
        });

        // Save button action listener
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Parse the modified text to extract updated values
                    String modifiedText = resultArea.getText();
                    String[] lines = modifiedText.split("\n");
                    int id = Integer.parseInt(lines[2].split(": ")[1].trim());
                    String name = lines[3].split(": ")[1].trim();
                    int age = Integer.parseInt(lines[4].split(": ")[1].trim());
                    String department = lines[5].split(": ")[1].trim();
                    String email = lines[6].split(": ")[1].trim();

                   
                    db.updateEmployee( id, name,  age,  department,  email);

                    JOptionPane.showMessageDialog(SearchForm.this,
                        "Employee details updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                    // Reset editing state
                    resultArea.setEditable(false);
                    isEditing = false;
                    saveButton.setVisible(false);
                    editButton.setVisible(true);
                    editButton.setEnabled(true);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(SearchForm.this,
                        "Error updating employee details: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(mainPanel);
    }
}