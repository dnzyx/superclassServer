package com.client;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class cookieIniter {

	private static String cookie ="";


	public String getCookie() {
		if(cookie.length()==0){
			init();
		}
		return cookie;
	}

    public void setCookie(String cookie) {
        if(cookie.length()==0)
        cookieIniter.cookie = cookie;
    }

    private void init(){
		new Thread(new Runnable() {
			public void run() {
				try {
				    String path = "http://qg.peizheng.net.cn/sys/ValidateCode.aspx?t=211";
			    	URL url = new URL(path);
			    	HttpURLConnection con = (HttpURLConnection) url.openConnection();
			    	con.setRequestMethod("GET");
			    	con.setDoOutput(true);
			    	con.setDoInput(true);
			    	InputStream in = con.getInputStream();
		    		//cookie = con.getHeaderField("set-Cookie");
                    Map<String,List<String>> map=con.getHeaderFields();
                    List<String> list=map.get("Set-Cookie");
                    for(String s:list){
                        if(s.contains("ASP.NET_SessionId")){
                            int index=s.indexOf(";");
                            cookie=s.substring(0,index)+"; safedog-flow-item=; BIGipServerpool_jw_7ceng=3406301706.20480.0000";
                        }
                    }

			} catch (Exception e) {
				// TODO: handle exception
			}
			}
		}).start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		}

}
