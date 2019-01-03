package com.servlet;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.listener.getCodeListener;
import com.client.getCodeClient;

/**
 * Servlet implementation class CodeServlet
 */
public class CodeServlet extends HttpServlet implements getCodeListener{
	private static final long serialVersionUID = 1L;
    private HttpServletResponse response;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CodeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.response = response;
		response.setHeader("Content-type", "text/html;charset=ISO-8859-1");
		if(!security.check(request)){
			response.getWriter().write("<div>the key is not right</div>");
		}
		FileInputStream in=new FileInputStream("C:\\superclass\\WebContent\\abc.jpeg");
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		byte[] tmp = new byte[1024];
		int tmpLen = 0;
		while ((tmpLen = in.read(tmp)) != -1) {
			bout.write(tmp, 0, tmpLen);
		}
		in.close();
		byte[] b = bout.toByteArray();
		if(b.length==0){
			getCodeClient client = new getCodeClient();
			client.setCodeListener(this);
			client.start();
		}
		bout.close();
		System.out.println(b.length);
		System.out.println(new String(b).length());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	@Override
	public void onGetCode(byte[] b) {
		// TODO Auto-generated method stub
		try {
			FileOutputStream out=new FileOutputStream("D:\\abc.jpeg");
			out.write(b);
			out.flush();
			out.close();
		} catch (Exception e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		try {
			String s=new String(b,"ISO-8859-1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		
	}
	@Override
	public void onGetCodeError(int code, String msg) {
		// TODO Auto-generated method stub
		System.out.println(code+msg);
	}

}
