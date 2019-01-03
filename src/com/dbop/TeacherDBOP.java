package com.dbop;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.api.model.teacher;
import com.db.DBConnection;

public class TeacherDBOP {
	 public static void insert(teacher t){
		 Connection conn =  DBConnection.getConnection();
		 Statement smt = DBConnection.getSmt(conn);  
	     String sql = "insert into teachers(teacher_id,teacher_name,teacher_state)values(" + "'" + t.getId() + "'," + "'" + t.getName() + "',"+t.getState()+")";
	     try {
			smt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(sql);
		} 
	     DBConnection.close(smt, conn);
	  }
	 public static void insert(ArrayList<teacher> list){
		Connection conn = DBConnection.getConnection();
		Statement smt = DBConnection.getSmt(conn);
		for (teacher t : list) {
			String sql ="insert into teachers(teacher_id,teacher_name,teacher_state)values(" + "'" + t.getId() + "',"
					+ "'" + t.getName() + "'," + t.getState() + ");\n";
			try {
				smt.execute(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					FileOutputStream out = new FileOutputStream("D:\\superclass.sql");
					out.write(sql.getBytes());
					out.close();
				} catch (FileNotFoundException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
				System.out.println(sql);
			}
		}
		DBConnection.close(smt, conn);
		System.out.println("insert sussessfully");
	}

	   public static ArrayList<teacher> queryAll(){
	       Connection conn = DBConnection.getConnection();
	       Statement smt = DBConnection.getSmt(conn);
	       String sql = "select * from teachers";
	       ResultSet rs = null;
	       try {
			rs = smt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	       ArrayList<teacher> list = new ArrayList<teacher>();
	        try {
				while(rs.next()){
				    teacher t = new teacher();
			
				    t.setId(rs.getString(2));
				    t.setName(rs.getString(3));
				    t.setState(rs.getInt(4));
				    list.add(t);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				System.out.println(sql);
			}
	        DBConnection.close(rs,smt, conn);
	        return list;
	    }

	    public static teacher queryOne(String id){
	    	Connection conn = DBConnection.getConnection();
	    	Statement smt = DBConnection.getSmt(conn);
	        String sql = "select * from teachers where teacher_id = '"+id+"';";
	        ResultSet c = null;
	        try {
				c = smt.executeQuery(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        teacher t = new teacher();
	        try {
				if (c.next()==false){

				    return null;
				}
				t.setId(c.getString(2));
				t.setName(c.getString(3));
				t.setState(c.getInt(4));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(sql);
			}
	        DBConnection.close(c, smt, conn);
	        return t;
	    }

	    public static ArrayList<teacher> queryLike(String name){
	    	Connection conn = DBConnection.getConnection();
	    	Statement smt = DBConnection.getSmt(conn);
	    	String sql = "select * from teachers where teacher_name like '%"+name+"%'";
	    	ResultSet c = null;
	    	try {
				c = smt.executeQuery(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(sql);
			}    
	       
	        ArrayList<teacher> list = new ArrayList<teacher>();
	        try {
				while(c.next()){
				    teacher t = new teacher();

				    t.setId(c.getString(2));
				    t.setName(c.getString(3));
				    t.setState(c.getInt(4));

				    list.add(t);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(sql);
			}
	        DBConnection.close(c, smt, conn);
	        return list;
	    }

	    public static void delete(String id){
	    	
	    	Connection conn = DBConnection.getConnection();
	    	Statement smt = DBConnection.getSmt(conn);
	    	
	        String sql = "delete from teachers where teacher_id = '" +id+"';";
	        try {
				smt.execute(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(sql);
			}
	        DBConnection.close(smt, conn);
	    }

	    public static void update(teacher t){
	    	Connection conn = DBConnection.getConnection();
	    	Statement smt = DBConnection.getSmt(conn);
	        String sql = "update teachers set teacher_id='"+t.getId()+"' ,teacher_name='"+t.getName()+"', teacher_state="+t.getState()+ " where teacher_id= '" + t.getId() + "';";;
	        try {
				smt.execute(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(sql);
			}
	        DBConnection.close(smt, conn);
	    }
	    
	   
}
