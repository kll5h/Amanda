package ren.amanda.security;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import ren.amanda.service.util.IPUtil;

public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {
	private static final long serialVersionUID = -5253875772426515760L;

	private final Logger log = LoggerFactory.getLogger(CustomWebAuthenticationDetails.class);

	private final String clientIp;

	public CustomWebAuthenticationDetails(HttpServletRequest request) {
		super(request);
		clientIp = IPUtil.getClientIP(request);
	}

	public String getClientIp() {
		return clientIp;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append("; clientIp: ").append(this.getClientIp());

		return sb.toString();
	}
}