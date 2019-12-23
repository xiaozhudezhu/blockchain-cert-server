package com.swinginwind.core.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ApplicationPropsUtil {

	public static final String ATTACHMENT_UPLOADDIR = "attachment.uploadDir";
	public static final String SEND_SMS_CODE = "send.sms.code";
	public static final String SEND_SMS_SERVER = "send.sms.server";
	public static final String SEND_SMS_TIMES = "send.sms.times";
	
	/**
	 * 登录是否启用
	 */
	public static final String LOG_LOGIN_ENABLE = "log.login.enable";
	
	public static final String NETWORK_PROXY_HOST = "network.proxy.host";
	public static final String NETWORK_PROXY_PORT = "network.proxy.port";
	
	private static List<Integer> adminIds;
	
	
	private static Properties props = null;
	private ApplicationPropsUtil(){}
	
	public static Properties getProps(){
		if(props == null){
			props = new Properties();
			try {
				props.load(ApplicationPropsUtil.class.getResourceAsStream("/application.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return props;
	}
	
	public static String getPropsValue(String key){
		Properties p = getProps();
		return p.getProperty(key, "");
	}
	
	public static String getPropsValue(String key, String defaultVal){
		Properties p = getProps();
		return p.getProperty(key, defaultVal);
	}

	public static List<Integer> getAdminIds() {
		if(adminIds == null) {
			adminIds = new ArrayList<Integer>();
			String idStr = getPropsValue("adminIds");
			String[] idStrs = idStr.split(",");
			for(String id : idStrs) {
				adminIds.add(Integer.parseInt(id));
			}
		}
		return adminIds;
	}

	
}
