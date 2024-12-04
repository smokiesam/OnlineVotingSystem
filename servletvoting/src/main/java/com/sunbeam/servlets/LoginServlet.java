package com.sunbeam.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunbeam.daos.UserDao;
import com.sunbeam.daos.UserDaoImpl;
import com.sunbeam.entities.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException,IOException {
		processRequest(req,resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException,IOException {
		processRequest(req,resp);
	}

	protected void processRequest(HttpServletRequest req, HttpServletResponse resp)throws ServletException,IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("passwd");
		
		//to verify user's received data we are creating object of user dao
		try(UserDao userDao = new UserDaoImpl()) {
			User dbUser = userDao.findByEmail(email);	//verifying from db
			
			//if valid user
			if(dbUser!=null && dbUser.getPassword().equals(password)) {
				//create cookie
				Cookie c1 = new Cookie("uname",dbUser.getFirstName());
				c1.setMaxAge(3600);
				resp.addCookie(c1);
				Cookie c2 = new Cookie("role",dbUser.getRole());
				c2.setMaxAge(3600);
				resp.addCookie(c2);
				
				
				
				
				
				
				
				if(dbUser.getRole().equals("admin")){
					resp.sendRedirect("result"); // go to ResultServlet
					//RequestDispatcher rd = req.getRequestDispatcher("result");
					//rd.forward(req, resp);
				} else { // if(dbUser.getRole().equals("voter"))
					resp.sendRedirect("candlist"); // go to CandidateListServlet
				}
				//if not a valid user
			}else {	
				resp.setContentType("text/html");
				PrintWriter out = resp.getWriter();
				out.println("<html>");
					out.println("<head>");
					out.println("<title>Login</title>");
					out.println("</head>");
					out.println("<body>");
					out.println("<h2>Login Failed</h2>");
					out.println("<p>Sorry, Invalid email or password.</p>");
					out.println("<p><a href='index.html'>Login Again</a></p>");
					out.println("</body>");
					out.println("</html>");
			}	
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServletException(e);	//wrapping up the default exception
		}
	}
}






//protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException,IOException {
//	
//}
//
//protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException,IOException {
//	
//}
//
//protected void processRequest(HttpServletRequest req, HttpServletResponse resp)throws ServletException,IOException {
//	
//}
