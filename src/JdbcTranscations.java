import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class JdbcTranscations {

    public static void main(String[] args) {

        Connection myConn = null;
        Statement myStmt = null;

        try {
            Properties props = new Properties();
            props.load(new FileInputStream("jdbc-test/sql/demo.properties"));

            // 1. Get a connection to database
            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String password = props.getProperty("password");
            myConn = DriverManager.getConnection(url, user, password);

            // Turn off auto-commit (for transaction management)
            myConn.setAutoCommit(false);

            // Show salaries BEFORE the transaction
            System.out.println("Salaries BEFORE\n");
            showSalaries(myConn, "HR");
            showSalaries(myConn, "Engineering");

            // Create a statement object
            myStmt = myConn.createStatement();

            // Transaction Step 1: Delete all HR employees
            myStmt.executeUpdate("delete from employees where department='HR'");

            // Transaction Step 2: Set salaries to 300,000 for all Engineering employees
            myStmt.executeUpdate("update employees set salary=300000 where department='Engineering'");

            System.out.println("\n>> Transaction steps are ready.\n");

            // Ask the user if it is okay to save the transaction
            boolean ok = askUserIfOkToSave();

            if (ok) {
                // Commit the transaction
                myConn.commit();
                System.out.println("\n>> Transaction COMMITTED.\n");
            } else {
                // Rollback the transaction
                myConn.rollback();
                System.out.println("\n>> Transaction ROLLED BACK.\n");
            }

            // Show salaries AFTER the transaction
            System.out.println("Salaries AFTER\n");
            showSalaries(myConn, "HR");
            showSalaries(myConn, "Engineering");

        } catch (Exception exc) {
            exc.printStackTrace();

            try {
                if (myConn != null) {
                    // Rollback transaction on exception
                    myConn.rollback();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            // Close connection and statement
            close(myConn, myStmt);
        }
    }

    private static void showSalaries(Connection myConn, String department) throws SQLException {
        // Prepare the query
        String sql = "select first_name, last_name, salary from employees where department=?";
        PreparedStatement myStmt = myConn.prepareStatement(sql);
        myStmt.setString(1, department);

        // Execute query
        ResultSet myRs = myStmt.executeQuery();

        // Display the result set
        while (myRs.next()) {
            String firstName = myRs.getString("first_name");
            String lastName = myRs.getString("last_name");
            double salary = myRs.getDouble("salary");

            System.out.printf("%s %s: %.2f\n", firstName, lastName, salary);
        }

        // Close ResultSet and Statement
        myRs.close();
        myStmt.close();
    }

    private static boolean askUserIfOkToSave() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Is it okay to save the transaction? (yes/no): ");
        String input = scanner.nextLine();

        return input.equalsIgnoreCase("yes");
    }

    private static void close(Connection myConn, Statement myStmt) {
        try {
            if (myStmt != null) {
                myStmt.close();
            }

            if (myConn != null) {
                myConn.close();
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}

