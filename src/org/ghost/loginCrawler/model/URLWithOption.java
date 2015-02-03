package org.ghost.loginCrawler.model;

/**
 * @author Hyeonwook Kim, Youngkwnag Han
 * @email khw0867@gmail.com
 * @classname URLWithOption.java
 * @package org.ghost.loginCrawler.model
 * @date 2015. 2. 03.
 * @purpose : Model for Crawler. 
 *
 * @comment :
 *
 */
public class URLWithOption {
	private String url;
	private String param;
	private String method;
	
	public static final String GET = "GET";
	public static final String POST = "POST";

	/** Constructor **/
	/**
	 * @param url - should specify protocol(http or https), default is http.
	 * @param param - request parameter.
	 * @param method - GET or POST value. others automatically converted to GET.
	 */
	public URLWithOption(String url, String param, String method) {
		this.setUrl(url);
		
		this.setParam(param);
		
		this.setMethod(method);
	}

	/** Getter and Setter **/
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		if(url.startsWith("http"))
			this.url = url;
		else
			this.url = "http://" + url;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		String mtd = method.toUpperCase();
		if(mtd.equals("GET") || mtd.equals("POST"))
			this.method = mtd;
		else
			this.method = "GET";
	}
}
