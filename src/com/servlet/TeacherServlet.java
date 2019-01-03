package com.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.listener.getTeacherListener;
import com.api.model.teacher;
import com.client.getTeacherClient;
import com.dbop.TeacherDBOP;

/**
 * Servlet implementation class TeacherServlet
 */
public class TeacherServlet extends HttpServlet implements getTeacherListener{
	private static final long serialVersionUID = 1L;
	private  HttpServletResponse response;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeacherServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		this.response=response;
		if(!security.check(request)){
			response.getWriter().write("<div>the key is not right</div>");
			return;
		}
		ArrayList<teacher> list=TeacherDBOP.queryAll();
		if(list.isEmpty()){
		getTeacherClient client = new getTeacherClient();
		client.setListener(this);
		client.star();
		}
		else{
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
	public void onGetTeacher(ArrayList<teacher> list) {
		// TODO Auto-generated method stub
		write(list);
		TeacherDBOP.insert(list);
	}
	private void write(ArrayList<teacher> list) {
		// TODO Auto-generated method stub
		OnGetTeacherError(0,"ok");
		try {
			response.getWriter().write("<select>");
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		for(teacher t:list){
			try {
				response.getWriter().write("<option value="+t.getId()+">"+t.getName()+"</option>");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			response.getWriter().write("</select>");
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
	}
	@Override
	public void OnGetTeacherError(int code, String msg) {
		// TODO Auto-generated method stub
		try {
			response.getWriter().write("<p value="+code+">服务器"+msg+"</p>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
