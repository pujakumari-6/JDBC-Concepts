import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class CallMySqlStoredProcedure4 {

    public static void main(String[] args) throws Exception {

        Connection myConn = null;
        CallableStatement myStmt = null;
        ResultSet myRs = null;

        try {
            // 1. Get a connection to database
            Properties props = new Properties();
            props.load(new FileInputStream("IdeaProjects/jdbc-test/sql/demo.properties"));
            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String password = props.getProperty("password");
            myConn = DriverManager.getConnection(url, user, password);

            // Define the department
            String theDepartment = "Engineering";

            // Prepare the stored procedure call
            myStmt = myConn.prepareCall("{call get_employees_for_department(?)}");

            // Set the parameter
            myStmt.setString(1, theDepartment);

            System.out.println("Calling stored procedure: get_employees_for_department('" + theDepartment + "')");

            // Call the stored procedure
            myStmt.execute();
            System.out.println("Finished calling stored procedure.\n");

            // Get the result set
            myRs = myStmt.getResultSet();

            // Display the result set
            display(myRs);

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            // Close connections
            close(myConn, myStmt, myRs);
        }
    }

    private static void display(ResultSet myRs) throws SQLException {
        // Loop through the result set and display data
        while (myRs.next()) {
            String firstName = myRs.getString("first_name");
            String lastName = myRs.getString("last_name");
            String department = myRs.getString("department");
            double salary = myRs.getDouble("salary");

            System.out.printf("%s %s (%s): %.2f\n", firstName, lastName, department, salary);
        }
    }

    private static void close(Connection myConn, CallableStatement myStmt, ResultSet myRs) {
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
}

