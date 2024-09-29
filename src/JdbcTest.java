import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;
import java.io.FileInputStream;

public class JdbcTest {
    public static void main(String[] args) throws SQLException {

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            Properties props = new Properties();
            props.load(new FileInputStream("jdbc-test/sql/demo.properties"));

            // 1. Get a connection to database
            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String password = props.getProperty("password");
            myConn = DriverManager.getConnection(url, user, password);

            // 2. Create a statement
            myStmt = myConn.prepareStatement("select * from employees where salary > ? and department = ?");
            myStmt.setDouble(1, 80000);
            myStmt.setString(2, "Legal");
            myRs = myStmt.executeQuery();

            // 4. Process the result set

            while (myRs.next()) {
                System.out.println(myRs.getString("last_name") + ", " + myRs.getString("first_name"));
            }
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        finally {
            if (myRs != null) {
                myRs.close();
            }

            if (myStmt != null) {
                myStmt.close();
            }

            if (myConn != null) {
                myConn.close();
            }
        }
    }

}