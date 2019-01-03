package com.client;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.api.listener.getCodeListener;

public class getCodeClient {
	private getCodeListener listener;
    private byte[] buf;
	private byte[] buf2;
    int len = 0;
	public void setCodeListener(getCodeListener l){
		this.listener = l;
	}
	public void start(){
		int end=0;
		//while(end==0) {
			new Thread(new Runnable() {
				public void run() {
					String path = "http://qg.peizheng.net.cn/sys/ValidateCode.aspx?t=211";
					try {
						cookieIniter cook = new cookieIniter();
						String cookie = cook.getCookie();
						String referer = "http://qg.peizheng.net.cn/ZNPK/TeacherKBFB.aspx";
						URL url = new URL(path);
						HttpURLConnection con = (HttpURLConnection) url.openConnection();
						con.setRequestMethod("GET");
						con.setDoOutput(true);
						con.setDoInput(true);
						con.setRequestProperty("Cookie", cookie);
						con.setRequestProperty("Referer", referer);
						InputStream in = con.getInputStream();
						len = 0;
						ByteArrayOutputStream bout = new ByteArrayOutputStream();
						byte[] tmp = new byte[1024];
						int tmpLen = 0;
						while ((tmpLen = in.read(tmp)) != -1) {
							bout.write(tmp, 0, tmpLen);
							len += tmpLen;
						}
						buf = bout.toByteArray();
						in.close();
						con.disconnect();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			end = cutJpg(buf, len);
		//}
        buf2 = new byte[end + 1];
        for (int i = 0; i < end; i++) {
            buf2[i] = buf[i];
        }
		if(buf2 == null){
			listener.onGetCodeError(101, "获取验证码失败");
			return;
		}
		listener.onGetCode(buf2);
		
	}
	private static int cutJpg(byte[] buf,int len){
		int end = 0;
		for(int i=0;i<len-1;i++){
			byte p = buf[i];
			byte n = buf[i+1];
			if(p==-1 && n==-39){
				end = i+2;
				break;
			}
		}
		return end;
	}
	
}
