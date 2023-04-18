import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    final String user = "postgres";
    final String password = "ya030423";
    final String url = "jdbc:postgresql://localhost:5432/skypro";

    @Override
    public void createEmployee(String inputFirstName, String inputLastName, String inputGender, int inputAge, int inputCityId) {
        String insertString = "INSERT INTO employee(first_name, last_name, gender, age, city_id) VALUES (" +
                "'" + inputFirstName + "'," +
                "'" + inputLastName + "'," +
                "'" + inputGender + "'," +
                inputAge + "," +
                inputCityId + ")";
        try (final Connection connection =
                     DriverManager.getConnection(url, user, password);
             PreparedStatement statement =
                     connection.prepareStatement(insertString)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к базе данных!");
            e.printStackTrace();
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();

        try (final Connection connection =
                     DriverManager.getConnection(url, user, password);
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM employee")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idOfEmployee = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String gender = resultSet.getString("gender");
                int age = resultSet.getInt("age");
                int cityId = resultSet.getInt("city_id");
                employees.add(new Employee(idOfEmployee, firstName, lastName, gender, age, cityId));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к базе данных!");
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public Employee getEmployeeById(int id) {
        Employee employee = null;
        try (final Connection connection =
             DriverManager.getConnection(url, user, password);
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM employee WHERE id = " + id)) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
                int idOfEmployee = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String gender = resultSet.getString("gender");
                int age = resultSet.getInt("age");
                int cityId = resultSet.getInt("city_id");
                employee = new Employee(idOfEmployee, firstName, lastName, gender, age, cityId);
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к базе данных!");
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public void updateEmployeeById(int id, String inputFirstName, String inputLastName, String inputGender, int inputAge, int inputCityId) {
        String queryString = "UPDATE employee SET " +
                "first_name = " + "'" + inputFirstName + "'," +
                "last_name = " + "'" + inputLastName + "'," +
                "gender = " + "'" + inputGender + "'," +
                "age = " + inputAge + "," +
                "city_id = " + inputCityId + "WHERE id = " + id;


        try (final Connection connection =
                     DriverManager.getConnection(url, user, password);
             PreparedStatement statement =
                     connection.prepareStatement(queryString)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к базе данных!");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEmployeeById(int id) {
        try (final Connection connection =
                     DriverManager.getConnection(url, user, password);
             PreparedStatement statement =
                     connection.prepareStatement("DELETE FROM employee WHERE id = " + id)) {
            statement.executeUpdate();
            System.out.println("Удален сотрудник с id: " + id);
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к базе данных!");
            e.printStackTrace();
        }
    }
}
