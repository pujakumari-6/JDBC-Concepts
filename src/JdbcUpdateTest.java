import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
public class JdbcUpdateTest {

    public static void main(String[] args) {
        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            Properties props = new Properties();
            props.load(new FileInputStream("jdbc-test/sql/demo.properties"));
            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String password = props.getProperty("password");
            myConn = DriverManager.getConnection(url, user, password);

            // Prepare the SQL update statement
            String sql = "UPDATE employees SET email = ? WHERE id = ?";
            myStmt = myConn.prepareStatement(sql);

            // Set parameters
            myStmt.setString(1, "demo@luv2code.com");
            myStmt.setInt(2, 9); // Assuming 'id' is of type int

            // Execute the prepared statement
            int rowsAffected = myStmt.executeUpdate();

            System.out.println("Rows affected: " + rowsAffected);
            System.out.println("Update complete.");
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            // Close resources in reverse order of creation
            try {
                if (myStmt != null) {
                    myStmt.close();
                }
                if (myConn != null) {
                    myConn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
