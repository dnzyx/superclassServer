package com.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.api.listener.getCourseListener;
import com.api.model.course;

public class getCourseClient {
	private String result;
	private HttpURLConnection con;
	private OutputStream out;
	public getCourseListener listener;
	
	public void setListener(getCourseListener l){
		this.listener = l;
	}
	public void start(String year,String teacherid,String code){
		ArrayList<course> list = new ArrayList<course>();
		String data = null;
		try {
			data = catchData(year,teacherid,code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			listener.onGetCourseError(101, "数据加载失败");
			return;
		}
		if(data==null){
			listener.onGetCourseError(202, "数据为空");
			return;
		}
		Document doc = Jsoup.parse(data);
		Elements node1s = doc.select("script");
		if(node1s.isEmpty()==false)
		{
			listener.onGetCourseError(606, "验证码失败");
			return;
		}
		Elements nodes = doc.select("td[valign=top]");
		int i = 0;
		for(Element n:nodes){
			course course = new course();
			course.setContent(n.text());
			course.setNumber(i/7+1);
			course.setDate(i%7+1);
			course.setYear(year);
			course.setTeacher_id(teacherid);
			list.add(course);
			i++;
		}
		if(list.isEmpty()){
			listener.onGetCourseError(505, "无课");
			return;
		}
		listener.onGetCourse(list);
		
	}
	private String catchData(String year,String teacherid,String code) throws Exception {
		cookieIniter cook = new cookieIniter();
		String path="http://qg.peizheng.net.cn/ZNPK/TeacherKBFB_rpt.aspx";
		String cookie=cook.getCookie();
		String referer="http://qg.peizheng.net.cn/ZNPK/TeacherKBFB.aspx";
		final String params="Sel_XNXQ="+year+"&Sel_JS="+teacherid+"&type=1&txt_yzm="+code;
		URL url = new URL(path);
		con = (HttpURLConnection) url.openConnection();
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		con.setRequestProperty("Cookie", cookie);
		con.setRequestProperty("Referer", referer);
		
		new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				try {
					out = con.getOutputStream();
					out.write(params.getBytes());
					out.flush();
					InputStream in = con.getInputStream();
					ByteArrayOutputStream bout = new ByteArrayOutputStream();
					byte[] buf = new byte[1024];
					int len = 0;
					while((len=in.read(buf))!=-1){
						bout.write(buf, 0, len);
					}
					byte[] buf1 = bout.toByteArray();
					result = new String(buf1,"GBK");
					bout.close();
					in.close();
					out.close();
					con.disconnect();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					return;
				}
				
			}
		}).start();
		Thread.sleep(2000);
		return result;
	}
}
