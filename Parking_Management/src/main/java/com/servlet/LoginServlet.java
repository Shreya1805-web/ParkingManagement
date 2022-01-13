package com.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DB.DBConnect;
import com.dao.UserDAO;
import com.entity.user;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			String em = req.getParameter("email");
			String pw = req.getParameter("password");
			user u = new user();
			HttpSession session = req.getSession();
			if("admin@gmail.com".equals(em) && "1234".equals(pw))
			{
				u.setRole("admin");
				session.setAttribute("userobj", u);
				resp.sendRedirect("admin.jsp");
				
			}else{
				UserDAO dao = new UserDAO(DBConnect.getConn());
				  user user = dao.login(em, pw);
				  if(user != null) {
					  session.setAttribute("userobj",user);
					  resp.sendRedirect("home.jsp");
				  }else {
					  session.setAttribute("invalidmsg","Invalid email and password");
					  resp.sendRedirect("login.jsp");
			}
		} }catch (Exception e) {
			e.printStackTrace();
		}
	
		
	}

}
