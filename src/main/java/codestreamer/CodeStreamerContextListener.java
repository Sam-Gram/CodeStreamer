package codestreamer;

import codestreamer.model.Stream;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;
import java.util.HashMap;


/**
 * Used for registering things with the servlet container. Currently, it just registers the
 * database with it.
 */
@WebListener
public class CodeStreamerContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
//        codestreamer.model.Database db = new codestreamer.model.Database();
//        servletContextEvent.getServletContext().setAttribute("db", db);

        HashMap<String, Stream> streams = new HashMap<>();
        streams.put("test", new Stream("test-owner"));
        servletContextEvent.getServletContext().setAttribute("streams", streams);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
//        codestreamer.model.Database db = (codestreamer.model.Database) servletContextEvent.getServletContext().getAttribute("db");
//        try {
//            db.getConnection().close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}
