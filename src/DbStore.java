import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DbStore {
    static String url;
    static String username;
    static String password;
    String[] data = new String[5];

    static {
        try {
            Properties props = new Properties();
            FileInputStream fis = new FileInputStream("src\\config.properties");
            props.load(fis);
            url = props.getProperty("db.url");
            username = props.getProperty("db.username");
            password = props.getProperty("db.password");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1); // Exit if configuration is not loaded
        }
    }

    public void storeData(int id, String name, int age, String department, String email) {
        String sql = "INSERT INTO emp (id, name, age, department, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preStatement = connection.prepareStatement(sql)) {

            preStatement.setInt(1, id);
            preStatement.setString(2, name);
            preStatement.setInt(3, age);
            preStatement.setString(4, department);
            preStatement.setString(5, email);

            int rowsAffected = preStatement.executeUpdate();
            System.out.println("Data inserted into database. Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String[] searchData(int id) {
        String query = "SELECT id, name, age, department, email FROM emp WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                data[0] = Integer.toString(resultSet.getInt("id"));
                data[1] = resultSet.getString("name");
                data[2] = Integer.toString(resultSet.getInt("age"));
                data[3] = resultSet.getString("department");
                data[4] = resultSet.getString("email");
            } else {
                System.out.println("No employee found with ID: " + id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void updateEmployee(int id, String name, int age, String department, String email) {
        String query = "UPDATE emp SET name = ?, age = ?, department = ?, email = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            statement.setInt(2, age);
            statement.setString(3, department);
            statement.setString(4, email);
            statement.setInt(5, id);

            int rowsAffected = statement.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteData(int id) {
        String query = "DELETE FROM emp WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
