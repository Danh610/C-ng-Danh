package KiemTra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class EmployeeManagement {
    private Connection connection;

    public EmployeeManagement() {
        connection = SQLConnector.getConnection();
    }
    public void writeEmployeesToFile(List<Employee> employees, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Employee employee : employees) {
                String employeeData = employee.toFileString(); 
                writer.write(employeeData);
                writer.newLine();
            }
            System.out.println("Employees have been written to file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<Employee> readEmployeesFromFile(String filePath) {
        List<Employee> employees = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Employee employee = Employee.fromString(line);
                employees.add(employee);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public void addEmployee(Employee employee) {
        String query = "INSERT INTO employees (ID, FullName, BirthDay, Phone, Email, EmployeeType) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, employee.getID());
            ps.setString(2, employee.getFullName());
            ps.setString(3, employee.getBirthDay());
            ps.setString(4, employee.getPhone());
            ps.setString(5, employee.getEmail());
            ps.setString(6, employee.getEmployeeType());

            ps.executeUpdate();
            System.out.println("Employee added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayAllEmployees() {
        String query = "SELECT * FROM employees";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ID = rs.getInt("ID");
                String fullName = rs.getString("FullName");
                String birthDay = rs.getString("BirthDay");
                String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                String employeeType = rs.getString("EmployeeType");

                System.out.println("ID: " + ID);
                System.out.println("Full Name: " + fullName);
                System.out.println("Birthday: " + birthDay);
                System.out.println("Phone: " + phone);
                System.out.println("Email: " + email);
                System.out.println("Employee Type: " + employeeType);
                System.out.println("-------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployee(int ID) {
        String query = "DELETE FROM employees WHERE ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, ID);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Employee with ID " + ID + " has been deleted.");
            } else {
                System.out.println("Employee with ID " + ID + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(int ID, Employee updatedEmployee) {
        String query = "UPDATE employees SET FullName = ?, BirthDay = ?, Phone = ?, Email = ?, EmployeeType = ? WHERE ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, updatedEmployee.getFullName());
            ps.setString(2, updatedEmployee.getBirthDay());
            ps.setString(3, updatedEmployee.getPhone());
            ps.setString(4, updatedEmployee.getEmail());
            ps.setString(5, updatedEmployee.getEmployeeType());
            ps.setInt(6, ID);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Employee with ID " + ID + " has been updated.");
            } else {
                System.out.println("Employee with ID " + ID + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
