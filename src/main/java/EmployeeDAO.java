import java.util.List;

public interface EmployeeDAO {
    void createEmployee(String firstName, String lastName, String gender, int age, int cityId);
    List<Employee> getAllEmployees();
    Employee getEmployeeById(int id);
    void deleteEmployeeById(int id);
    void updateEmployeeById(int id, String inputFirstName, String inputLastName, String inputGender, int inputAge, int inputCityId);
}
