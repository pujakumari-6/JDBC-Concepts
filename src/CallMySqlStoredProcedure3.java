import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class CallMySqlStoredProcedure3 {

    public static void main(String[] args) throws Exception {
        Connection myConn = null;
        CallableStatement myStmt = null;

        try {
            // Get a connection to the database
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
            myStmt = myConn.prepareCall("{call get_count_for_department(?, ?)}");

            // Set the input parameter (IN)
            myStmt.setString(1, theDepartment);

            // Register the output parameter (OUT)
            myStmt.registerOutParameter(2, Types.INTEGER);

            // Call the stored procedure
            System.out.println("Calling stored procedure get_count_for_department('" + theDepartment + "')");
            myStmt.execute();
            System.out.println("Finished calling stored procedure");

            // Get the value of the OUT parameter
            int theCount = myStmt.getInt(2);

            System.out.println("\nThe count of employees in " + theDepartment + " department = " + theCount);

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
