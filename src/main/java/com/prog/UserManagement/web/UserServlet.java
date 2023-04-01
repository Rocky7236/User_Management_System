package com.prog.UserManagement.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.prog.UserMangement.bean.User;
import com.prog.UserMangement.dao.UserDao;
@WebServlet("/")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserDao userDao;
	
	public void init() throws ServletException {
		userDao = new UserDao();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getServletPath();
		
		switch(action)
		{
		case "/new" :
					showNewForm(request,response);
					break;
			
		case "/insert" :
					insertUser(request,response);
					break;
			
		case "/delete" :
					deleteUser(request,response);
					break;
			
		case "/edit" :
					showEditForm(request,response);
					break;
			
		case "/update" :
					updateUser(request,response);
					break;
			
		default :
					listUser(request,response);
					break;
		}
		
		
	}
	
	//new
	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException
	{
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		dispatcher.forward(request, response);
	}
	//insert
	private void insertUser(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException
	{
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		
		User newUser = new User(name,email,country);
		
		userDao.insertUser(newUser);
		response.sendRedirect("list");
		
	}
	//delete
	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException
	{
		int id = Integer.parseInt(request.getParameter("id"));
		try {
			userDao.DeleteUser(id);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		response.sendRedirect("list");
	}
	//edit
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException
	{
		int id = Integer.parseInt(request.getParameter("id"));
		User existingUser;
		try {
			existingUser = userDao.selectUser(id);
			RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
			request.setAttribute("user",existingUser);
			dispatcher.forward(request, response);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	//update
	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException
	{
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		
		User user = new User(id, name , email,country);
		userDao.UpdateUser(user);
		response.sendRedirect("list");		
	}
	
	//default
	private void listUser(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException
	{
		try
		{
			List<User> listUser = userDao.selectAllUser();
			request.setAttribute("listUser",listUser);
			RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
			dispatcher.forward(request, response);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
}

