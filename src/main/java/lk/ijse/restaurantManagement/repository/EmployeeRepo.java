package lk.ijse.restaurantManagement.repository;

import lk.ijse.restaurantManagement.db.DbConnection;
import lk.ijse.restaurantManagement.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepo {

    public static List<Employee> getAll() throws SQLException {
        String sql = "SELECT * FROM Employee";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Employee> employeeList = new ArrayList<>();
        while (resultSet.next()) {
            String employeeId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String contact = resultSet.getString(4);
            String position = resultSet.getString(5);
            String basicSalary = resultSet.getString(6);

            Employee employee = new Employee(employeeId, name, address, contact, position, basicSalary);
            employeeList.add(employee);
        }
        return employeeList;
    }

    public static boolean save(Employee employee) throws SQLException {
        String sql = "INSERT INTO Employee VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, employee.getEmployeeId());
        pstm.setObject(2, employee.getName());
        pstm.setObject(3, employee.getAddress());
        pstm.setObject(4, employee.getContact());
        pstm.setObject(5, employee.getPositon());
        pstm.setObject(6, employee.getBasicSalary());

        return pstm.executeUpdate() > 0;
    }

    public static Employee searchByContact(String id) throws SQLException {
        String sql = "SELECT * FROM Employee WHERE contact = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);
        ResultSet resultSet = pstm.executeQuery();

        Employee employee = null;

        if (resultSet.next()) {
            String employeeId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String contact = resultSet.getString(4);
            String position = resultSet.getString(5);
            String basicSalary = resultSet.getString(6);


            employee = new Employee(employeeId, name, address, contact, position, basicSalary);
        }
        return employee;
    }

    public static boolean update(Employee employee) throws SQLException {

        String sql = "UPDATE Employee SET employeeId = ?, name = ?, address = ?,position = ?, basicSalary = ? WHERE contact = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, employee.getEmployeeId());
        pstm.setObject(2, employee.getName());
        pstm.setObject(3, employee.getAddress());
        pstm.setObject(4, employee.getPositon());
        pstm.setObject(5, employee.getBasicSalary());
        pstm.setObject(6, employee.getContact());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String employeeId) throws SQLException {
        String sql = "DELETE FROM Employee WHERE contact = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, employeeId);

        return pstm.executeUpdate() > 0;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT employeeId FROM Employee";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }

    public static int getEmployeeCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS employee_count FROM Employee";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        int employeeCount = 0;
        if(resultSet.next()) {
            employeeCount = resultSet.getInt("employee_count");
        }
        return employeeCount;
    }
    public String autoGenarateEmployeeId() throws SQLException {
        String sql = "SELECT employeeId from Employee order by employeeId desc limit 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            String employeeId = resultSet.getString("employeeId");
            String numericPart = employeeId.replaceAll("\\D+", "");
            int newEmployeeId = Integer.parseInt(numericPart) + 1;
            return String.format("E%03d", newEmployeeId);

        } else {
            return "E001";

        }
    }
}

