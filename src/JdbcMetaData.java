import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class JdbcMetaData {

    public static void main(String[] args) throws SQLException {
        Connection myConn = null;
        ResultSet myRs = null;

        String catalog = null;
        String schemaPattern = null;
        String tableNamePattern = null;
        String[] types = null;
        String columnNamePattern = null;
        try {
            // 1. Get a connection to database
            Properties props = new Properties();
            props.load(new FileInputStream("IdeaProjects/jdbc-test/sql/demo.properties"));
            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String password = props.getProperty("password");
            myConn = DriverManager.getConnection(url, user, password);
            // 2. Get metadata
            DatabaseMetaData databaseMetaData = myConn.getMetaData();

            // 3. Display info about the database
            System.out.println("Product name: " + databaseMetaData.getDatabaseProductName());
            System.out.println("Product version: " + databaseMetaData.getDatabaseProductVersion());

            // 4. Display info about JDBC Driver
            System.out.println("JDBC Driver name: " + databaseMetaData.getDriverName());
            System.out.println("JDBC Driver version: " + databaseMetaData.getDriverVersion());

            myRs = databaseMetaData.getTables(catalog, schemaPattern, tableNamePattern, types);

            while (myRs.next()) {
                System.out.println(myRs.getString("TABLE_NAME"));
            }

            System.out.println("\n\nList of Columns");

            System.out.println(" --");

            myRs = databaseMetaData.getColumns (catalog, schemaPattern, "employees", columnNamePattern);

            while (myRs.next()) {
                System.out.println(myRs.getString("COLUMN_NAME"));
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            close(myConn);
        }
    }

    private static void close(Connection myConn) {
        try {
            if (myConn != null) {
                myConn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
