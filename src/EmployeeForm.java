import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeForm extends JPanel {
    private JLabel successLabel; // Add success message label
    DbStore db = new DbStore();
    EmployeeForm() {
        setLayout(new BorderLayout()); // Set main layout to BorderLayout

        // Panel to draw the title
        JPanel topPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTitle(g);
            }
        };
        topPanel.setPreferredSize(new Dimension(400, 50));
        add(topPanel, BorderLayout.NORTH);

        // Form panel to add input fields
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        JLabel empIdLable = new JLabel("Employee Id:");
        JTextField empIdField = new JTextField(20);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);

        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField(20);

        JLabel departmentLabel = new JLabel("Department:");
        JTextField departmentField = new JTextField(20);

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);

        formPanel.add(empIdLable);
        formPanel.add(empIdField);
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(ageLabel);
        formPanel.add(ageField);
        formPanel.add(departmentLabel);
        formPanel.add(departmentField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        add(formPanel, BorderLayout.CENTER);

        // Create a panel for buttons and success message
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        // Button panel with reduced button sizes
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton submitButton = new JButton("Submit Details");
        submitButton.setPreferredSize(new Dimension(120, 30));

        JButton searchButton = new JButton("Search Details");
        searchButton.setPreferredSize(new Dimension(120, 30));

        JButton deleteButton = new JButton("Delete Details");
        searchButton.setPreferredSize(new Dimension(120, 30));

        // Success message label
        successLabel = new JLabel("Successfully Entered Details");
        successLabel.setFont(new Font("Arial", Font.BOLD, 16));
        successLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        successLabel.setVisible(false); // Initially hidden

        // Action listener for submit button
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                String id = empIdField.getText();
                String name = nameField.getText();
                String age = ageField.getText();
                String department = departmentField.getText();
                String email = emailField.getText();
                if(id.isEmpty() || name.isEmpty() || age.isEmpty() || department.isEmpty() || email.isEmpty()){
                    successLabel.setText("Please Enter the Data");
                    successLabel.setForeground(Color.RED);
                }else{

                    db.storeData(Integer.parseInt(id), name, Integer.parseInt(age), department, email);
                    successLabel.setText("Successfully Data Entered");
                    successLabel.setForeground(Color.GREEN);
                    
                    empIdField.setText("");
                    nameField.setText("");
                    ageField.setText("");
                    departmentField.setText("");
                    emailField.setText("");
                }

                // Show success message
                successLabel.setVisible(true);
                
                // Optional: Hide the message after 3 seconds
                Timer timer = new Timer(3000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        successLabel.setVisible(false);
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                JFrame searchFrame = new SearchForm();
                searchFrame.setVisible(true);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                JFrame deleteFrame = new DeleteDetails();
                deleteFrame.setVisible(true);
            }
        });
        buttonPanel.add(submitButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(deleteButton);
        // Add components to bottom panel
        bottomPanel.add(buttonPanel);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing
        bottomPanel.add(successLabel);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing

        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void drawTitle(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Enter Employee Details", 120, 30);
    }

}