package KiemTra;

public class Main {
    public static void main(String[] args) {
        EmployeeManagement manager = new EmployeeManagement();

        Experience exp1 = new Experience(1, "John Doe", "1990-05-20", "123456789", "john.doe@example.com", 5, "Java, SQL");
        Fresher fr1 = new Fresher(2, "Jane Smith", "1995-12-10", "987654321", "jane.smith@example.com", "2023-07-15", "Excellent", "ABC University");
        Intern intern1 = new Intern(3, "Michael Johnson", "2000-03-03", "456789123", "michael.johnson@example.com", "Computer Science", "Spring 2024", "XYZ University");

        manager.addEmployee(exp1);
        manager.addEmployee(fr1);
        manager.addEmployee(intern1);

        System.out.println("All Employees:");
        manager.displayAllEmployees();

        System.out.println("\nDeleting Employee with ID 2:");
        manager.deleteEmployee(2);

        System.out.println("\nUpdating Employee with ID 3:");
        Intern updatedIntern = new Intern(3, "Lê Nguyễn Công Danh", "2005-10-06", "835851421", "danhdeptrai@gmail.com", "Data Science", "Summer 2024", "Việt Hàn University");
        manager.updateEmployee(3, updatedIntern);
        
        
        System.out.println("\nAfter Changes:");
        manager.displayAllEmployees();
    }
}
