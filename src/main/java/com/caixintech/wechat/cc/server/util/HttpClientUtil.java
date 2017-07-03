package com.caixintech.wechat.cc.server.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);


	public static String httpReader(String url, String code){
		logger.debug("GetPage:"+url);
		
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet method = new HttpGet(url);	
		
		String result = null;
		try {
			CloseableHttpResponse httpResponse = client.execute(method);
			int status = httpResponse.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_OK) {
				result = EntityUtils.toString(httpResponse.getEntity());
			} else {
				logger.error("Method failed: " + httpResponse.getStatusLine());
			}
		} catch (IOException e) {
			// 发生网络异常
			logger.error("发生网络异常！");
			e.printStackTrace();
		} finally{
			// 释放连接
			if(method!=null)method.releaseConnection();
			method = null;
			client = null;
		}
		return result;
	}
	
	public static String httpGet(String url,String code) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);	
		httpGet.addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2");
		try {
			CloseableHttpResponse httpResponse = httpclient.execute(httpGet);
			String result = EntityUtils.toString(httpResponse.getEntity());
			return result;
		}catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String httpPost(String url, Map<String, String> paramMap, String code) {
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		try {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Iterator<String> it = paramMap.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next() + "";
				Object o = paramMap.get(key);
				if (o != null && o instanceof String) {
					nvps.add(new BasicNameValuePair(key, o.toString()));
				}
				if (o != null && o instanceof String[]) {
					String[] s = (String[]) o;
					if (s != null)
						for (int i = 0; i < s.length; i++) {
							nvps.add(new BasicNameValuePair(key, s[i]));
						}
				}
			}
	        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			CloseableHttpResponse httpResponse = httpclient.execute(httpPost);
			String result = EntityUtils.toString(httpResponse.getEntity(),code);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String httpPost(String url, Map<String, String> paramMap) {
		return HttpClientUtil.httpPost(url, paramMap, "UTF-8");
	}
}