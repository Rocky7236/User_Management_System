package com.prog.UserMangement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.prog.UserMangement.bean.User;

public class UserDao {
	
	private String jdbcDriver = "com.mysql.cj.jdbc.Driver";
	private String jdbcUrl = "jdbc:mysql://localhost:3306/userdb";
	private String jdbcUsername="root";
	private String jdbcPassword="root";
	
	private static final String Insert_User ="insert into users (name,email,country) values(?,?,?)";	
	private static final String Select_User_By_Id = "select * from users where id=?";
	private static final String Select_All_User = "select * from users";
	private static final String Delete_User = "delete from users where id = ?";
	private static final String Update_User ="update users set name=?,email=?,country=? where id=?";
	
	
	public UserDao()
	{
		//constructor
	}
	
	protected Connection getConnection()	
	{
		Connection con = null;
		
		try {
			Class.forName(jdbcDriver);
			con=DriverManager.getConnection(jdbcUrl,jdbcUsername,jdbcPassword);
		} catch (ClassNotFoundException |SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return con;
	}
	
	//INSERT_USER
	
	public void insertUser(User user)
	{
		System.out.println(Insert_User);
		try(Connection con = getConnection();
			PreparedStatement psmt = con.prepareStatement(Insert_User);)
		{
				psmt.setString(1,user.getName());
				psmt.setString(2,user.getEmail());
				psmt.setString(3,user.getCountry());
				
				psmt.executeUpdate();				
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	//SELECT_ALL_USER_BY_ID
	
	public User selectUser(int id)
	{
		User user = null;
		try(Connection con = getConnection();
			PreparedStatement psmt = con.prepareStatement(Select_User_By_Id))
		{
			psmt.setInt(1, id);
			ResultSet rs =psmt.executeQuery();
			while(rs.next())
			{
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				user = new User(id,name,email,country);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return user;
	}
	
	//SELECT ALL USER
	public List<User> selectAllUser() {
		
		List<User> users = new ArrayList<>();
	try(Connection con = getConnection();
		PreparedStatement psmt = con.prepareStatement(Select_All_User)	)
	{
		ResultSet rs = psmt.executeQuery();
		while(rs.next())
		{
			int id = rs.getInt("id");
			String name = rs.getString("name");
			String email = rs.getString("email");
			String country = rs.getString("country");
			users.add(new User(id,name,email,country));
		}
	}catch(Exception e)
	{
		e.printStackTrace();
	}
	return users;
	}
	
	//UPDATE USER
	public boolean UpdateUser(User user)
	{
		boolean rowUpdate = false;
		
		try(Connection con = getConnection();
		PreparedStatement psmt = con.prepareStatement(Update_User)	)
		{
			
			psmt.setString(1,user.getName());
			psmt.setString(2,user.getEmail());
			psmt.setString(3,user.getCountry());
			psmt.setInt(4,user.getId());
			
			rowUpdate=psmt.executeUpdate()>0;
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}	
		return rowUpdate;
	}
	
	//DELETE USER
	
	public boolean DeleteUser(int id)
	{
		boolean rowDeleted = false;

		try(Connection con = getConnection();
		PreparedStatement psmt = con.prepareStatement(Delete_User)	)
		{
			psmt.setInt(1,id);
			rowDeleted =psmt.executeUpdate()>0;
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return rowDeleted;
	}
	
}
