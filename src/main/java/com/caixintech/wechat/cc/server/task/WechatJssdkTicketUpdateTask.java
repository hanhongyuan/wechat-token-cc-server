package com.caixintech.wechat.cc.server.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.caixintech.wechat.cc.server.service.WechatServerService;

@Component
public class WechatJssdkTicketUpdateTask {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WechatServerService weixinAppServiceImpl;
	
	@Scheduled(cron="0 0 0/2 * * ?")
	public void updateKey(){
		try {
			weixinAppServiceImpl.updateAccessTokenAndJssdkTicket();
		} catch (Exception e) {
			logger.info("update access token and jsticket fail:{}",e.getMessage());
		}
		logger.info("update access token and jsticket success!");
	}
}
