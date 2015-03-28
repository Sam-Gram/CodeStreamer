import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;


/**
 * Used for registering things with the servlet container. Currently, it just registers the
 * database with it.
 */
@WebListener
public class CodeStreamerContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Database db = new Database();
        servletContextEvent.getServletContext().setAttribute("db", db);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        Database db = (Database) servletContextEvent.getServletContext().getAttribute("db");
        try {
            db.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
