package ren.amanda.service;

import java.util.Locale;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

import ren.amanda.config.JHipsterProperties;
import ren.amanda.domain.User;

/**
 * Service for sending sms.
 * <p>
 * We use the @Async annotation to send sms asynchronously.
 * </p>
 */
@Service
public class SmsService {

    private final Logger log = LoggerFactory.getLogger(SmsService.class);

    @Inject
    private JHipsterProperties jHipsterProperties;


	@Async
	public void sendCode(String code, String recNum) {
		log.debug("Send sms[number '{}']", code, recNum);

		try {
			TaobaoClient client = new DefaultTaobaoClient(jHipsterProperties.getSms().getUrl(),
					jHipsterProperties.getSms().getAppkey(), jHipsterProperties.getSms().getSecret());
			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
			req.setExtend("");
			req.setSmsType("normal");
			req.setSmsFreeSignName(jHipsterProperties.getSms().getFreeSignName());
			req.setSmsParamString("{number:'code'}".replaceAll("code", code));
			req.setRecNum(recNum);
			req.setSmsTemplateCode(jHipsterProperties.getSms().getTemplateCode());
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
			System.out.println(rsp.getBody());

			log.debug("Sent sms to mobile '{}'", recNum);
		} catch (Exception e) {
			log.warn("SMS could not be sent to mobile '{}'", recNum, e);
		}
        
    }
    
    
    @Async
    public void sendActivationCode(User user) {
    	log.debug("Sending activation code to '{}'", "18622587275");
    	sendCode(user.getActivationKey(), "18622587275");
    }
    
    @Async
    public void sendActivationCode(User user, String number) {
    	log.debug("Sending activation code to '{}'", "18622587275");
    	sendCode(number, "18622587275");
    }

}
