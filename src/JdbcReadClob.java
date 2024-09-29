import java.io.*;
import java.sql.*;
import java.util.Properties;

public class JdbcReadClob {
    public static void main(String[] args) throws Exception {
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;
        Reader input = null;
        FileWriter output = null;

        try {
            // 1. Get a connection to database
            Properties props = new Properties();
            props.load(new FileInputStream("jdbc-test/sql/demo.properties"));
            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String password = props.getProperty("password");
            myConn = DriverManager.getConnection(url, user, password);
            // 2. Create a statement
            myStmt = myConn.createStatement();

            // 3. Execute the SQL query to retrieve the resume (CLOB data)
            String sql = "select resume from employees_clob where email='john.doe@foo.com'";
            myRs = myStmt.executeQuery(sql);

            // 4. Set up a handle to the output file
            File theFile = new File("IdeaProjects/jdbc-test/Resources/clob_resume_from_db.txt");
            output = new FileWriter(theFile);

            // 5. Process the result set
            if (myRs.next()) {
                // Get the CLOB data as a character stream
                input = myRs.getCharacterStream("resume");
                System.out.println("Reading resume from database...");
                System.out.println(sql);

                // Read the CLOB data and write it to the output file
                int theChar;
                while ((theChar = input.read()) != -1) {
                    output.write(theChar);
                }

                System.out.println("Resume saved as: " + theFile.getAbsolutePath());
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            // Close the input/output streams and database resources
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
