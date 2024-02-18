package starhub2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCtest {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        String url = "jdbc:mysql://localhost:3306/employee_data";
        String username = "YZ1";
        String password = "ILoveInfosys";

        String sqlUpdate = "UPDATE salary_details " +
                "SET base_salary = base_salary + 10000 " +
                "WHERE employee_id = ( " +
                "    SELECT employee_id " +
                "    FROM employee_details " +
                "    WHERE name = ? " +
                ");";

        String sqlSelect = "SELECT base_salary " +
                "FROM salary_details " +
                "WHERE employee_id = ( " +
                "    SELECT employee_id " +
                "    FROM employee_details " +
                "    WHERE name = ? " +
                ");";

        try (
                Connection conn = DriverManager.getConnection(url, username, password);
                PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate);
                PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect)) {

            // Set the name parameter for both prepared statements
            String employeeName = "John Doe";
            pstmtUpdate.setString(1, employeeName);
            pstmtSelect.setString(1, employeeName);

            // Execute the UPDATE statement
            int rowsAffected = pstmtUpdate.executeUpdate();
            System.out.println("Rows affected by update: " + rowsAffected);

            // Execute the SELECT statement to get the old and new base salary
            ResultSet rs = pstmtSelect.executeQuery();
            if (rs.next()) {
                double oldBaseSalary = rs.getDouble("base_salary") - 10000; // Old base salary before update
                double newBaseSalary = rs.getDouble("base_salary"); // New base salary after update
                System.out.println("Old base salary: " + oldBaseSalary);
                System.out.println("New base salary: " + newBaseSalary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
