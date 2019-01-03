package com.client;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.api.listener.getTeacherListener;
import com.api.model.teacher;

public class getTeacherClient {
	private HttpURLConnection con;
	private OutputStream out;
	public getTeacherListener listener;
	private String result=null;
	public void setListener(getTeacherListener l){
		this.listener = l;
	}
	public void star(){
		ArrayList<teacher> list = new ArrayList<teacher>();
		String data = null;
		try {
			data= catchData();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			listener.OnGetTeacherError(303, "网络异常");
			return;
		}
		if(data==null){
			listener.OnGetTeacherError(404, "网络异常");
			return;
		}
		//data="<select name=Sel_JS style='width:220'><option></option><option value=0000916>Albert WOLFE</option><option value=0001968>Alexander SAWYER</option></select>";
		String temp = data.substring(data.indexOf("<option"), data.lastIndexOf("</select>"));
		Document doc = Jsoup.parse(temp);
		Elements nodes = doc.select("option");
		for(Element n:nodes){
			if(n.text().length()==0){
				continue;
			}
			teacher teach = new teacher();
			teach.setId(n.attr("value"));
			teach.setName(n.text());
			list.add(teach);
		}
		if(list.isEmpty()){
			listener.OnGetTeacherError(505, "获取数据失败");
		}
		else{
			listener.onGetTeacher(list);
		}
	}
	private String catchData() throws Exception{

		String path="http://qg.peizheng.net.cn/ZNPK/Private/List_JS.aspx?xnxq=20170&js=";
		String cookie;
		cookieIniter cook=new cookieIniter();
		cookie=cook.getCookie();
		String referer="http://qg.peizheng.net.cn/ZNPK/TeacherKBFB.aspx";
		final String params="xnxq=20170&js=";

		URL url = new URL(path);
		con = (HttpURLConnection) url.openConnection();
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setRequestMethod("GET");
		con.setRequestProperty("Cookie", cookie);
		con.setRequestProperty("Referer", referer);
		
		new Thread(new Runnable() {
			public void run() {
				try {
					out = con.getOutputStream();
					out.write(params.getBytes());
					out.flush();
					InputStream in = con.getInputStream();
					
					ByteArrayOutputStream bout = new ByteArrayOutputStream();
					byte[] buf = new byte[1024];
					int len = 0;
					while((len=in.read(buf))!= (-1)){
						bout.write(buf, 0, len);
					}
					byte[] buf1 = bout.toByteArray();
					
					result = new String(buf1,"GBK");				
					in.close();
					bout.close();
					out.close();
					con.disconnect();
				} catch (Exception e) {
					return ;
				}
		
			}
		}).start();
		Thread.sleep(2000);
		return result;
	}
}
