package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

	private static String host = "localhost";
	private static String username = "root";
	private static String password = "";
	private static String dbName = "superclass";
	
	private DBConnection(){
		;
	}
	
	public static Connection getConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://"+host+"/"+dbName+
			"?user="+username+
			"&password="+password+
			"&useUnicode=true"+
			"&characterEncoding=utf-8");
			return conn;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public static Statement getSmt(Connection conn){
		try {
			Statement smt= conn.createStatement();
			return smt;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		
		}
		return null;
	}
	
	public static void close(Statement smt , Connection conn){
		if(smt != null){
			try {
				smt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void close(ResultSet rs, Statement smt , Connection conn){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		close(smt, conn);
	}
}
