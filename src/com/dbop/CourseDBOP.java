package com.dbop;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.api.model.*;
import com.db.*;



public class CourseDBOP {
	public static void insert(course c) {
			if(c.getContent().length()==0){
				return;
			}
        	Connection conn = DBConnection.getConnection();
        	Statement smt = DBConnection.getSmt(conn);
            String sql = "insert into " +
                    "courses(date,number,year,teacher_id,course_content)" +
                    "values(" + c.getDate() + "," + c.getNumber() + ",'" + c.getYear() + "','" + c.getTeacher_id() + "','" + c.getContent() + "');";
            try {
				smt.execute(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(sql);
			}
            DBConnection.close(smt, conn);
           
    }
	public static void insert(ArrayList<course> list) {
    	Connection conn = DBConnection.getConnection();
    	Statement smt = DBConnection.getSmt(conn);
    	for(course c:list){
    		if(c.getContent().length()==0){
    			continue;
    		}
    		String sql ="insert into " +
                "courses(date,number,year,teacher_id,course_content)" +
                "values(" + c.getDate() + "," + c.getNumber() + ",'" + c.getYear() + "','" + c.getTeacher_id() + "','" + c.getContent() + "');";
    		try {
    			smt.execute(sql);
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			System.out.println(sql);
    		}
    	}
        DBConnection.close(smt, conn);
       
}

    public static ArrayList<course> queryAll(String id,String year){
    	Connection conn = DBConnection.getConnection();
    	Statement smt = DBConnection.getSmt(conn);
    	ResultSet c = null;
        ArrayList<course> list = new ArrayList<course>();
        
        String sql="select * from courses where teacher_id='"+id+"' and year='"+year+"';";//显示Teacher_id和year
        try {
			c = smt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
       
       try {
		while(c.next()){
		       course s = new course();
		       s.setId(c.getInt(1));
		       s.setDate(c.getInt(2));
		       s.setNumber(c.getInt(3));
		       s.setYear(c.getString(4));
		       s.setTeacher_id(c.getString(5));
		       s.setContent(c.getString(6));
		       list.add(s);
		   }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println(sql);
	}
       	DBConnection.close(c, smt, conn);
        return list;
    }
    public static course queryOne(int id){
        course s = new course();
        Connection conn = DBConnection.getConnection();
    	Statement smt = DBConnection.getSmt(conn);
    	ResultSet c = null;
        String sql = "slecte * from where course_id="+id+";";
        try {
			c = smt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

        try {
			if (c.next()==false){
			    return null;
			}
		       s.setId(c.getInt(1));
		       s.setDate(c.getInt(2));
		       s.setNumber(c.getInt(3));
		       s.setYear(c.getString(4));
		       s.setTeacher_id(c.getString(5));
		       s.setContent(c.getString(6));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(sql);
		}
        DBConnection.close(c, smt, conn);
        return s;
    }
    public static void delete( int id){
    	Connection conn = DBConnection.getConnection();
    	Statement smt = DBConnection.getSmt(conn);
        String sql = "delete from courses where course_id="+id+";";//删除表中你需要删除的ID
        try {
			smt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        DBConnection.close(smt, conn);
    }

    public static void update(course s){
    	Connection conn = DBConnection.getConnection();
    	Statement smt = DBConnection.getSmt(conn);
    	String sql = "update courses set course_id = "+s.getId()+""
    	+"date = "+s.getDate()+"number = "+s.getNumber()+"year='"+s.getYear()+""+
    	"'teacher_id = '"+s.getTeacher_id()+"'course_content='"+s.getContent()+"';";
    	try {
			smt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	DBConnection.close(smt, conn);
    }

}
