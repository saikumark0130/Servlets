package sai.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServletURL")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static Connection getConnection(){
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/sys","root","123456");
			System.out.println("Connecting Database..........");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	Connection connection;
	private void InIt() {
		 connection = Register.getConnection();
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String confirmpassword = request.getParameter("confirmpassword");
		
		if (password.equals(confirmpassword)) {
			PrintWriter out = response.getWriter();
			
			String query="insert into register(firstname, lastname, username, password) values (?,?,?,?)";
			
			PreparedStatement ps=null;
			try {
				ps = connection.prepareStatement(query);
				ps.setString(1, firstname);
				ps.setString(2, lastname);
				ps.setString(3, username);
				ps.setString(4, password);
			
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			out.println("Welcome to Servlets");
			out.println();
			out.println(firstname + "\n" + lastname);
			
		}
		else{
			RequestDispatcher rs=request.getRequestDispatcher("Register.html");
			rs.forward(request, response);
//			response.sendRedirect("Register.html");
		
		
		}
		
	}

}
