import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcInsertTest {

    public static void main(String[] args) {
        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            // 1. Get a connection to database
            Properties props = new Properties();
            props.load(new FileInputStream("IdeaProjects/jdbc-test/sql/demo.properties"));
            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String password = props.getProperty("password");
            myConn = DriverManager.getConnection(url, user, password);

            // 2. Prepare the SQL statement
            String sql = "INSERT INTO employees (last_name, first_name, email) VALUES (?, ?, ?)";
            myStmt = myConn.prepareStatement(sql);

            // 3. Set parameters
            myStmt.setString(1, "Brown");
            myStmt.setString(2, "David");
            myStmt.setString(3, "david.brown@foo.com");

            // 4. Execute the prepared statement
            myStmt.executeUpdate();

            System.out.println("Insert complete.");
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            // Close resources in the reverse order of creation
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
