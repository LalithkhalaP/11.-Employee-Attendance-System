package employee;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {

    Connection con;

  public void init() throws ServletException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_db","root","lalli@2007");
                
            System.out.println("Database connected.");
        } catch (Exception e) {
            throw new ServletException("DB connection error: " + e.getMessage());
        }
    }

    
    public void service(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String action = req.getParameter("action");

        if ("register".equals(action)) {
            String name = req.getParameter("name");
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            try {
                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO Employee (name, username, password) VALUES (?, ?, ?)"
                );
                ps.setString(1, name);
                ps.setString(2, username);
                ps.setString(3, password);

                int i = ps.executeUpdate();
                if (i > 0) {
                    out.println("Registration Successful!<br>");
                    out.println("<a href='login.html'>Go to Login</a>");
                } else {
                    out.println("Registration Failed!<br>");
                    out.println("<a href='register.html'>Try Again</a>");
                }
            } catch (SQLException e) {
                out.println("Error: " + e.getMessage());
            }

        } else if ("login".equals(action)) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            try {
                PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM Employee WHERE username=? AND password=?"
                );
                ps.setString(1, username);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    out.println("Login Successful!<br>");
                    out.println("Welcome, " + rs.getString("name") + "<br>");
                   
                } else {
                    out.println("Login Failed! Invalid credentials.<br>");
                    out.println("<a href='login.html'>Try Again</a>");
                }
            } catch (SQLException e) {
                out.println("Error: " + e.getMessage());
            }
        } else {
            out.println("Invalid Action!<br>");
            out.println("<a href='register.html'>Register</a> | <a href='login.html'>Login</a>");
        }
    }

    
    public void destroy() {
        try {
            if (con != null) con.close();
            System.out.println("Database connection closed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
