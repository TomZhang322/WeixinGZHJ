package com.model.accesstoken;

/**
 * 微信通用接口凭证
 * 在微信公众号开发者文档中是这样子定义accessToken的：
 * access_token是公众号的全局唯一接口调用凭据，公众号调用各接口时都需使用access_token。开发者需要进行妥善保存。access_token的存储至少要保留512个字符空间。
 * access_token的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取的access_token失效。主要是在调用很多微信接口的时候需要使用到accessToken作为凭证，
 * 所以说这个东西是微信公众号开发时必须进行获取的！
 */
public class AccessToken {

	//access_token
	private String access_token;

	//有效时间（两个小时，7200s）
	private int	expires_in;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
}
