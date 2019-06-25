package collectData;
import java.sql.*;
public class JDBCFacade {

    private Connection conn=null;
    private Statement statement=null;

    public void open(String driver,String jdbcUrl,String userName,String userPwd) {
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(jdbcUrl,userName,userPwd);
            statement = conn.createStatement();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int executeUpdate(String sql) {
        try {
            return statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void executeQuery(String sql) {
        try {
             statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void close() {
        try {
            conn.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
