import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class CallMySqlStoredProcedure2 {

    public static void main(String[] args) throws Exception {
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

            // Define the department
            String theDepartment = "Engineering";

            // Prepare the stored procedure call
            myStmt = myConn.prepareCall("{call greet_the_department(?)}");

            // Set the parameters
            myStmt.registerOutParameter(1, Types.VARCHAR); // This is for the INOUT parameter
            myStmt.setString(1, theDepartment);

            // Call the stored procedure
            System.out.println("Calling stored procedure greet_the_department('" + theDepartment + "')");
            myStmt.execute();
            System.out.println("Finished calling stored procedure");

            // Get the value of the INOUT parameter
            String theResult = myStmt.getString(1);

            System.out.println("\nThe result = " + theResult);

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            // Close connection
            close(myConn, myStmt);
        }
    }

    private static void close(Connection myConn, CallableStatement myStmt) {
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
