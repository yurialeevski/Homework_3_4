import java.sql.*;
import java.util.List;

public class Application {
    public static void main(String[] args) throws SQLException {
        // Задание 1
        //1. Создать maven-проект с архетипом org.apache.maven.archetypes:maven-archetype-webapp.
        //2. Добавить зависимость PostgreSQL с сайта mvnrepository и плагин maven-compiler-plugin в pom.xml-файл.
        //3. Создать класс Application  и настроить в нем подключение к созданной ранее базе данных skypro.
        task_1();

        /* Задание 2
        Создать классы Employee и City с полями, аналогично созданным таблицам.
        Создать интерфейс EmployeeDAO.
        Создать в интерфейсе методы:
            Создание (добавление) сущности Employee в таблицу.
            Получение конкретного объекта Employee по id.
            Получение списка всех объектов Employee из базы.
            Изменение конкретного объекта Employee в базе по id.
            Удаление конкретного объекта Employee из базы по id.
        Реализовать сервис EmployeeDAO и каждый его метод в отдельном классе.
        Проверить корректность работы всех методов в классе Application. */
        task_2();
    }
    public static void task_1() throws SQLException {
        final String user = "postgres";
        final String password = "ya030423";
        final String url = "jdbc:postgresql://localhost:5432/skypro";

//4. Получить и вывести в консоль полные данные об одном из работников(имя, фамилия, пол, город) по id.
        try (final Connection connection =
                     DriverManager.getConnection(url, user, password);
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM employee WHERE id = 1")) {

            System.out.println("Соединение установлено!");

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            int idOfEmployee = resultSet.getInt("id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String gender = resultSet.getString("gender");
            int age = resultSet.getInt("age");
            int cityId = resultSet.getInt("city_id");

            System.out.println("ID сотрудника: " + idOfEmployee + " \tимя: " + firstName + " \tфамилия: " + lastName +
                    " \tпол: " + gender + " \tвозраст: " + age + " \tID города: " + cityId);
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к базе данных!");
            e.printStackTrace();
            // Исключение для обработки возможных ошибок при подключении
        }
    }
    public static void task_2() throws SQLException {
        EmployeeDAO employeeDAO = new EmployeeDAOImpl();

        System.out.println("Создание (добавление) сущности Employee в таблицу");
        employeeDAO.createEmployee("Николай", "Николаев", "муж", 41, 2);

        System.out.println("\nПолучение конкретного объекта Employee по id");
        Employee employee = employeeDAO.getEmployeeById(3);
        printEmployee(employee);

        System.out.println("\nПолучение списка всех объектов Employee из базы");
        List<Employee> employeeList = employeeDAO.getAllEmployees();
        for (Employee employee_1 : employeeList) {
            printEmployee(employee_1);
        }

        System.out.println("\nИзменение конкретного объекта Employee в базе по id.");
        employeeDAO.updateEmployeeById(10, "Петр", "Петров","муж",37, 1);
        Employee employeeUpdated = employeeDAO.getEmployeeById(10);
        printEmployee(employeeUpdated);

        System.out.println("\nУдаление конкретного объекта Employee из базы по id.");
        employeeDAO.deleteEmployeeById(10);

        System.out.println("\nПолучение списка всех объектов Employee из базы");
        employeeList = employeeDAO.getAllEmployees();
        for (Employee employee_2 : employeeList) {
            printEmployee(employee_2);
        }
    }

    public static void printEmployee(Employee employee) {
        System.out.println("ID сотрудника : " + employee.getId() +
                " \tимя: " + employee.getFirstName() + "" + " фамилия: " + employee.getLastName() +
                " пол: " + employee.getGender() + " возраст: " + employee.getAge() +
                " ID города: " + employee.getCityId());
    }
}
