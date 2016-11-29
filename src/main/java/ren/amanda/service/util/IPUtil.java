package ren.amanda.service.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取IP的工具类
 * 
 * @version v1.1
 * @history v1.1-->增加获取服务端IP的方法
 * @history v1.0-->增加获取客户端IP的方法
 * @update Aug 27, 2015 4:35:46 PM
 * @create 2015年4月14日 下午8:55:09
 * @author 玄玉<http://blog.csdn.net/jadyer>
 */
public final class IPUtil {

	private IPUtil() {
	}

	private static final Map<String, String> subnetMaskMap = new HashMap<String, String>();
	static {
		subnetMaskMap.put("8", "255.0.0.0");
		subnetMaskMap.put("16", "255.255.0.0");
		subnetMaskMap.put("24", "255.255.255.0");
		subnetMaskMap.put("128", "(::1/128");
		subnetMaskMap.put("10", "fe80::203:baff:fe27:1243/10");
	}

	/**
	 * 获取客户端IP
	 * 
	 * @see --------------------------------------------------------------------------------------------------
	 * @see 1.JSP中获取客户端IP地址的方法是request.getRemoteAddr(),这种方法在大部分情况下都是有效的
	 * @see 但是在通过了Apache,Squid等反向代理软件后,获取到的就不是客户端的真实IP地址了
	 * @see 2.经过代理后,由于在客户端和服务端之前增加了中间层,因此服务器无法直接拿到客户端IP
	 * @see 但在转发请求的HTTP头中增加了X-FORWARDED-FOR,用以跟踪原有的客户端IP地址和原来客户端请求的服务器地址
	 * @see 3.经过多级反向代理后,X-FORWARDED-FOR的值就会不止一个,而是一串IP值,那么有效IP便是第一个非unknown的字符串
	 * @see 更多详细介绍见http://dpn525.iteye.com/blog/1132318
	 * @see --------------------------------------------------------------------------------------------------
	 */
	public static String getClientIP(HttpServletRequest request) {
		String IP = request.getHeader("x-forwarded-for");
		
		if (null == IP || 0 == IP.length() || "unknown".equalsIgnoreCase(IP)) {
			IP = request.getHeader("Proxy-Client-IP");
		}
		if (null == IP || 0 == IP.length() || "unknown".equalsIgnoreCase(IP)) {
			IP = request.getHeader("WL-Proxy-Client-IP");
		}
		if (null == IP || 0 == IP.length() || "unknown".equalsIgnoreCase(IP)) {
			IP = request.getRemoteAddr();
		}
		// 对于通过多个代理的情况,第一个IP为客户端真实IP
		// 多个IP会按照','分割('***.***.***.***'.length()=15)
		if (null != IP && IP.length() > 15) {
			if (IP.indexOf(",") > 0) {
				IP = IP.substring(0, IP.indexOf(","));
				System.out.println("multi proxy : " + IP);
			}
		}
		
		return IP;
	}
}
