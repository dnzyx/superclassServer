package com.api.model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.db.DBConnection;

public class key implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	private static final String form="api_key";
	
	private String ak,uid,sha1,sha1_dev,pac,content,name;
	private int statu,appid;
	
	public int getAppid() {
		return appid;
	}
	public void setAppid(int appid) {
		this.appid = appid;
	}
	public String getAk() {
		return ak;
	}
	public void setAk(String ak) {
		this.ak = ak;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getSha1() {
		return sha1;
	}
	public void setSha1(String sha1) {
		this.sha1 = sha1;
	}
	public String getSha1_dev() {
		return sha1_dev;
	}
	public void setSha1_dev(String sha1_dev) {
		this.sha1_dev = sha1_dev;
	}
	public String getPac() {
		return pac;
	}
	public void setPac(String pac) {
		this.pac = pac;
	}
	public int getStatu() {
		return statu;
	}
	public void setStatu(int statu) {
		this.statu = statu;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "key [ak=" + ak + ", uid=" + uid + ", sha1=" + sha1 + ", sha1_dev=" + sha1_dev + ", pac=" + pac
				+ ", content=" + content + ", statu=" + statu + "]";
	}
	/* ���� Javadoc��
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ak == null) ? 0 : ak.hashCode());
		result = prime * result + ((pac == null) ? 0 : pac.hashCode());
		result = prime * result + ((sha1 == null) ? 0 : sha1.hashCode());
		result = prime * result + ((sha1_dev == null) ? 0 : sha1_dev.hashCode());
		return result;
	}
	/* ���� Javadoc��
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof key)) {
			return false;
		}
		key other = (key) obj;
		if(other.getStatu()!=1) return false;
		if (ak == null) {
			if (other.ak != null) {
				return false;
			}
		} 
		else if (!ak.equals(other.ak)) {
			return false;
		}
		
		if (pac == null) {
			if (other.pac != null) {
				return false;
			}
		} 
		else if (!pac.equals(other.pac)) {
			return false;
		}
		
		if (sha1 == null) {
			if (other.sha1 != null) {
				return false;
			}
		} else if (sha1.equals(other.sha1)||sha1.equals(other.sha1_dev)) {
			return true;
		}
		
		return false;
	}
	public key() {
		super();
	}
	
	public key(String ak, String uid, String sha1, String sha1_dev, String pac, String content, int statu) {
		super();
		this.ak = ak;
		this.uid = uid;
		this.sha1 = sha1;
		this.sha1_dev = sha1_dev;
		this.pac = pac;
		this.content = content;
		this.statu = statu;
	}
	
	
	public static ArrayList<key> selectAll(String uid,int statu) {
		Connection conn =  DBConnection.getConnection();
		String sql = "select * from "
				+form
				+ " where userid ='"+uid+ "'"
				+ " and statu="+statu+ ";";
		Statement smt = DBConnection.getSmt(conn);
		ResultSet rs = null;
		try {
			rs = (ResultSet) smt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ArrayList<key> list = new ArrayList<key>();
		try {
			while (rs.next()) {
				key u= new key();
				
				u.setAk(rs.getString(1));
				u.setUid(rs.getString(2));
				u.setSha1(rs.getString(3));
				u.setSha1_dev(rs.getString(4));
				u.setPac(rs.getString(5));
				u.setStatu(rs.getInt(6));
				u.setContent(rs.getString(7));
				u.setAppid(rs.getInt(8));
				u.setName(rs.getString(9));
				list.add(u);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DBConnection.close(rs, smt, conn);
		return list;
	}

	public static key selectu(String ak) {

		Connection conn = (Connection) DBConnection.getConnection();
		String sql = "select * from "
				+form
				+ " where ak='"+ ak+"';";
		Statement smt = (Statement) DBConnection.getSmt(conn);
		ResultSet rs = null;
		try {
			rs = (ResultSet) smt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}

		key u= new key();
		try {
			if(rs.next()==false)
			{
				return null;
			};
			
			u.setAk(rs.getString(1));
			u.setUid(rs.getString(2));
			u.setSha1(rs.getString(3));
			u.setSha1_dev(rs.getString(4));
			u.setPac(rs.getString(5));
			u.setStatu(rs.getInt(6));
			u.setContent(rs.getString(7));
			u.setAppid(rs.getInt(8));
			u.setName(rs.getString(9));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}

		DBConnection.close(rs, smt, conn);
		return u;

	}
	
	public static void insert(key s) {
		Connection conn = (Connection) DBConnection.getConnection();
		Statement smt = (Statement) DBConnection.getSmt(conn);

		String sql = "insert into "
				+form
				+ "(ak,"
				+ "userid"
				+ ",sha1"
				+ ",sha1_dev"
				+ ",package"
				+ ",statu"
				+ ",content"
				+ ",name"
				+ ") values("
				+"'"+s.getAk() + "'," 
				+"'"+s.getUid() + "'," 
				+"'"+s.getSha1() +"'," 
				+"'"+s.getSha1_dev() +"'," 
				+"'"+s.getPac() +"'," 
				+s.getStatu()+","
				+"'"+s.getContent() +"'," 
				+"'"+s.getName() +"'" 
				+");";

		try {
			smt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(sql);
		}
		DBConnection.close(smt, conn);
	}

	public static void delete(String sid) {
		Connection conn = (Connection) DBConnection.getConnection();
		String sql = "delete from "
				+form
				+ " where appid=" + sid + ";";

		Statement smt = (Statement) DBConnection.getSmt(conn);

		try {
			System.out.println(smt.execute(sql));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DBConnection.close(smt, conn);

	}
	
	public static void delete(key u) {
		Connection conn = (Connection) DBConnection.getConnection();
		String sql = "delete from "
				+form
				+ " where appid='" + u.getAppid()+ "';";

		Statement smt = (Statement) DBConnection.getSmt(conn);

		try {
			System.out.println(smt.execute(sql));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DBConnection.close(smt, conn);

	}

	public static void update(key s) {
		Connection conn = (Connection) DBConnection.getConnection();
		Statement smt = (Statement) DBConnection.getSmt(conn);

		String sql = 
				"update "
				+form
				+ " set "
				+"sha1='"+s.getSha1()+"',"
				+"name='"+s.getName()+"',"
				+"sha1_dev='"+s.getSha1_dev()+"',"
				+"package='"+s.getPac()+"',"
				+"content='"+s.getContent()+"',"
				+ "statu=" + s.getStatu()
				+ " where appid= " + s.getAppid() + ";";
		try {
			smt.execute(sql);
		} catch (SQLException e) {
			System.out.println(sql);
			e.printStackTrace();
		}
		DBConnection.close(smt, conn);

	}
}
