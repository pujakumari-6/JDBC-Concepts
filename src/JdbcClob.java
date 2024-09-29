import java.io.*;
import java.sql.*;
import java.util.Properties;

public class JdbcClob {
    public static void main(String[] args) throws Exception {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        FileReader input = null;

        try {
            // 1. Get a connection to database
            Properties props = new Properties();
            props.load(new FileInputStream("IdeaProjects/jdbc-test/sql/demo.properties"));
            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String password = props.getProperty("password");
            myConn = DriverManager.getConnection(url, user, password);

            // 2. Prepare statement
            String sql = "update employees_clob set resume = ? where email = 'john.doe@foo.com'";
            myStmt = myConn.prepareStatement(sql);

            // 3. Set parameter for resume file (CLOB data)
            File theFile = new File("/home/delhivery/IdeaProjects/jdbc-test/Resources/sample_resume.txt");
            input = new FileReader(theFile);
            myStmt.setCharacterStream(1, input, (int) theFile.length());

            System.out.println("Reading input file: " + theFile.getAbsolutePath());

            // 4. Execute statement
            System.out.println("\nStoring resume in database: " + theFile);
            System.out.println(sql);

            myStmt.executeUpdate();

            System.out.println("\nCompleted successfully!");
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            // Close the resources
            if (input != null) {
                input.close();
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
