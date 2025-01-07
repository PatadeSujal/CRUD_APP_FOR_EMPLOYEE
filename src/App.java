import java.awt.FlowLayout;

import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame();
        EmployeeForm employeeForm = new EmployeeForm();
        frame.add(employeeForm);
        frame.setVisible(true);
        frame.setSize(1000,700);
        frame.setLayout(new FlowLayout());
        
    }
}
