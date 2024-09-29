import java.io.*;
import java.sql.*;
import java.util.Properties;

public class JdbcBlob {
    public static void main(String[] args) throws Exception {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        FileInputStream input = null;

        try {
            // 1. Get a connection to database
            Properties props = new Properties();
            props.load(new FileInputStream("IdeaProjects/jdbc-test/sql/demo.properties"));
            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String password = props.getProperty("password");
            myConn = DriverManager.getConnection(url, user, password);

            // 2. Prepare statement
            String sql = "update employees_blob set resume = ? where email = 'john.doe@foo.com'";
            myStmt = myConn.prepareStatement(sql);

            // 3. Set parameter for resume file
            File theFile = new File("/home/delhivery/IdeaProjects/jdbc-test/Resources/sample_resume.pdf");
            input = new FileInputStream(theFile);
            myStmt.setBinaryStream(1, input);

            System.out.println("Reading input file: " + theFile.getAbsolutePath());

            // 4. Execute statement
            System.out.println("\nStoring resume in database: " + theFile);
            System.out.println(sql);

            myStmt.executeUpdate();

            System.out.println("\nCompleted successfully!");
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            // Close resources
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
