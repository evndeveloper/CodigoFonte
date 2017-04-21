import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	public Connection getConnection() {
        try {
            return (Connection) DriverManager.getConnection(
          "jdbc:mysql://localhost/theone", "root", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
