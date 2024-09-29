import java.io.*;
import java.sql.*;
import java.util.Properties;

public class JdbcReadBlob {
    public static void main(String[] args) throws Exception {
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        InputStream input = null;
        FileOutputStream output = null;

        try {
            Properties props = new Properties();
            props.load(new FileInputStream("jdbc-test/sql/demo.properties"));
            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String password = props.getProperty("password");
            // 1. Get a connection to the database
            myConn = DriverManager.getConnection(url, user, password);

            // 2. Create a statement
            myStmt = myConn.createStatement();

            // 3. Execute a query to get the resume (BLOB data)
            String sql = "select resume from employees_blob where email='john.doe@foo.com'";
            myRs = myStmt.executeQuery(sql);

            // 4. Set up a handle to the file to write the resume from the database
            File theFile = new File("jdbc-test/Resources/resume_from_db.pdf");
            output = new FileOutputStream(theFile);

            if (myRs.next()) {
                // 5. Get the binary stream of the resume (BLOB data)
                input = myRs.getBinaryStream("resume");

                System.out.println("Reading resume from database...");
                System.out.println(sql);

                // 6. Write the file from the binary stream
                byte[] buffer = new byte[1024];
                while (input.read(buffer) > 0) {
                    output.write(buffer);
                }

                System.out.println("Resume saved as: " + theFile.getAbsolutePath());
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            // 7. Close the input/output streams and database resources
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }
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
