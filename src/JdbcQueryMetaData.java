import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcQueryMetaData {

    public static void main(String[] args) {
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {

            Properties props = new Properties();
            props.load(new FileInputStream("IdeaProjects/jdbc-test/sql/demo.properties"));

            // 1. Get a connection to database
            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String password = props.getProperty("password");
            myConn = DriverManager.getConnection(url, user, password);

            // 2. Run query
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery("SELECT id, last_name, first_name, salary FROM employees");

            // 3. Get result set metadata
            ResultSetMetaData raMetaData = myRs.getMetaData();

            // 4. Display info
            int columnCount = raMetaData.getColumnCount();
            System.out.println("Column count: " + columnCount + "\n");

            for (int column = 1; column <= columnCount; column++) {
                System.out.println("Column name: " + raMetaData.getColumnName(column));
                System.out.println("Column type name: " + raMetaData.getColumnTypeName(column));
                System.out.println("Is Nullable: " + raMetaData.isNullable(column));
                System.out.println("Is Auto Increment: " + raMetaData.isAutoIncrement(column) + "\n");
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            close(myConn, myStmt, myRs);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
