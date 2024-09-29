import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class CallMySqlStoredProcedure {

    public static void main(String[] args) {
        Connection myConn = null;
        CallableStatement myStmt = null;

        try {
            // 1. Get a connection to database
            Properties props = new Properties();
            props.load(new FileInputStream("IdeaProjects/jdbc-test/sql/demo.properties"));
            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String password = props.getProperty("password");
            myConn = DriverManager.getConnection(url, user, password);

            // Define the department and increase amount
            String theDepartment = "Engineering";
            int theIncreaseAmount = 10000;

            // Show salaries BEFORE
            System.out.println("Salaries BEFORE\n");
            showSalaries(myConn, theDepartment);

            // Prepare the stored procedure call
            myStmt = myConn.prepareCall("{call increase_salaries_for_department(?, ?)}");

            // Set the parameters
            myStmt.setString(1, theDepartment);
            myStmt.setInt(2, theIncreaseAmount);

            // Call the stored procedure
            System.out.println("\n\nCalling stored procedure. increase_salaries_for_department('" + theDepartment + "', " + theIncreaseAmount + ")");
            myStmt.execute();
            System.out.println("Finished calling stored procedure");

            // Show salaries AFTER
            System.out.println("\n\nSalaries AFTER\n");
            showSalaries(myConn, theDepartment);

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            close(myConn, myStmt, null);
        }
    }

    private static void showSalaries(Connection myConn, String department) throws SQLException {
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            // Prepare statement
            myStmt = myConn.prepareStatement("SELECT first_name, last_name, salary FROM employees WHERE department=?");

            // Set parameter
            myStmt.setString(1, department);

            // Execute SQL query
            myRs = myStmt.executeQuery();

            // Process result set
            while (myRs.next()) {
                String firstName = myRs.getString("first_name");
                String lastName = myRs.getString("last_name");
                double salary = myRs.getDouble("salary");

                System.out.printf("%s %s: %.2f\n", firstName, lastName, salary);
            }
        } finally {
            close(myStmt, myRs);
        }
    }

    private static void close(Connection myConn, Statement myStmt, ResultSet myRs) {
        try {
            if (myRs != null) {
                myRs.close();
            }

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

    private static void close(Statement myStmt, ResultSet myRs) {
        close(null, myStmt, myRs);
    }
}
