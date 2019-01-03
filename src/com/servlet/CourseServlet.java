package com.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.listener.getCodeListener;
import com.api.listener.getCourseListener;
import com.api.model.course;
import com.api.model.teacher;
import com.client.getCodeClient;
import com.client.getCourseClient;
import com.dbop.CourseDBOP;
import com.dbop.TeacherDBOP;

/**
 * Servlet implementation class CourseServlet
 */
public class CourseServlet extends HttpServlet implements getCourseListener,getCodeListener{
	private static final long serialVersionUID = 1L;
	private HttpServletResponse response;
	String year;
	String teacherid;
	String code;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CourseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		this.response = response;
		if(!security.check(request)){
			response.getWriter().write("<div>the key is not right</div>");
			return;
		}
		year = request.getParameter("year");
		teacherid = request.getParameter("teacherid");
		code = request.getParameter("code");
		ArrayList<course> list =CourseDBOP.queryAll(teacherid, year);
		if(list.isEmpty()){
			getCourseClient client = new getCourseClient();
			client.setListener(this);
			client.start(year, teacherid, code);
		}
		else
		{
			write(list);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	@Override
	public void onGetCourse(ArrayList<course> list) {
		// TODO Auto-generated method stub
		write(list);
		CourseDBOP.insert(list);
		teacher t=TeacherDBOP.queryOne(teacherid);
		t.setState(1);
		TeacherDBOP.update(t);
	}

	private void write(ArrayList<course> list){
		onGetCourseError(0, "ok");
		try {
			response.getWriter().write("<table BORDER=1><tr height=25>");
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		int i=0;
		for(course c :list){
			i++;
			try {
				response.getWriter().write("<td  WIDTH=120 valign=top"+ 
						" date="+c.getDate()+
						" number="+c.getNumber()+
						">"+c.getContent()+"</td>");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(i%7==0){
				try {
					response.getWriter().write("</tr><tr height=25>");
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
		try {
			response.getWriter().write("</tr></table>");
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
	@Override
	public void onGetCourseError(int code, String msg) {
		if(code==606){//验证码错误，强制更新
			getCodeClient client=new getCodeClient();
			client.setCodeListener(this);
			client.start();
			try {
				response.getWriter().write("<script language=javascript>alert('验证码错误！');</script>");
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		// TODO Auto-generated method stub
		try {
			response.getWriter().write("<p value="+code+">"+msg+"</p>");
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(code==505){
			teacher t=TeacherDBOP.queryOne(teacherid);
			t.setState(-1);
			TeacherDBOP.update(t);
		}
	}
	@Override
	public void onGetCode(byte[] b) {
		try {
			//C:\\superclass\\WebContent\\abc.jpeg
			//F:\\mysoft\\2017winter\\workSpace\\superclassServer\\WebContent\\abc.jpeg
			FileOutputStream out=new FileOutputStream("E:\\superclass\\WebContent\\abc.jpeg");
			out.write(b);
			out.close();
		} catch (Exception e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		
	}
	@Override
	public void onGetCodeError(int code, String msg) {
		// TODO Auto-generated method stub
		System.out.println(code+msg);
	}
}
