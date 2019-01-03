package com.servlet;

import javax.servlet.http.HttpServletRequest;

import com.api.model.key;

public class security {
	public static boolean check(HttpServletRequest request){
		String ak=request.getParameter("ak");
		String sha5=request.getParameter("sha");
		String pac=request.getParameter("pac");
		key k=new key();
		k.setAk(ak);
		k.setPac(pac);
		k.setSha1(sha5);
		System.out.println(k.toString());
		return k.equals(key.selectu(ak));
	}
}
