package meinservletpackage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MeinErstesServlet extends HttpServlet {

    static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest requ, HttpServletResponse resp) {
        resp.setContentType("text/html");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<html>");
            out.println("<h3> Hallo, mein erstes Servlet meldet sich </h3>");
            out.println("<a href='/'>zur&uuml;ck</a>");
            out.println("</html>");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}