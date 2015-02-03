package org.ghost.loginCrawler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.ghost.loginCrawler.SSL.TrustAllHostnameVerifier;
import org.ghost.loginCrawler.SSL.TrustAllTrustManager;
import org.ghost.loginCrawler.model.URLWithOption;

/**
 * @author Hyeonwook Kim, Youngkwang Han
 * @email khw0867@gmail.com
 * @classname LoginCrawler.java
 * @package org.ghost.loginCrawler
 * @date 2015. 2. 03.
 * @purpose : Crawling web page which needs login process
 *
 * @comment :
 *
 */
public class LoginCrawler {
	private String cookies;
	private List<URLWithOption> loginUrls;

	/** Constructors **/
	public LoginCrawler(URLWithOption url) {
		List<URLWithOption> urlList = new ArrayList<URLWithOption>();
		urlList.add(url);
		loginUrls = urlList;
		cookies = "";
	}

	public LoginCrawler(List<URLWithOption> urls) {
		this.loginUrls = urls;
		cookies = "";
	}

	/** Getter and Setter **/
	public String getCookies() {
		return cookies;
	}

	/**
	 * @param String[] loginURLs - properly ordered url strings which is used for login.
	 * @return String which contains cookie values
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public String doLogin() throws IOException, NoSuchAlgorithmException,
			KeyManagementException {
		cookies = "";

		// do login process
		Iterator<URLWithOption> i = loginUrls.iterator();
		while (i.hasNext()) {
			this.makeCookie(i.next());
		}

		return cookies;
	}

	/**
	 * @param url - URL String you want to crawl.
	 * @return InputStream - InputStream contains crawled HTML sources.
	 * @throws IOException
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	public InputStream crawl(String url) throws IOException,
			KeyManagementException, NoSuchAlgorithmException {

		URLConnection con = null;
		URL urlObj = new URL(url);

		String protocol = urlObj.getProtocol();

		if (protocol.equals("https")) {
			con = (HttpsURLConnection) urlObj
					.openConnection();
		} else {
			con = (HttpURLConnection) urlObj.openConnection();
		}

		con.setDoOutput(true);
		con.setRequestProperty("Cookie", cookies);

		return con.getInputStream();
	}

	/**
	 * allow all certificates over HTTPS
	 */
	public void allowAllCertificates() throws NoSuchAlgorithmException,
			KeyManagementException {

		TrustManager[] trustManager = new TrustManager[1];
		trustManager[0] = new TrustAllTrustManager();

		SSLContext socketContext = SSLContext.getInstance("SSL");
		socketContext.init(null, trustManager, null);

		HttpsURLConnection.setDefaultSSLSocketFactory(socketContext
				.getSocketFactory());
		HttpsURLConnection
				.setDefaultHostnameVerifier(new TrustAllHostnameVerifier());
	}

	private void makeCookie(URLWithOption urlwo) throws IOException,
			NoSuchAlgorithmException, KeyManagementException {

		URL urlObj = new URL(urlwo.getUrl());
		URLConnection con = null;

		String host = urlObj.getHost();
		String protocol = urlObj.getProtocol();

		if (protocol.equals("https")) {
			con = (HttpsURLConnection) urlObj.openConnection();
		} else {
			con = (HttpURLConnection) urlObj.openConnection();
		}

		con.setUseCaches(true);
		con.setDoOutput(true);
		((HttpURLConnection) con).setRequestMethod(urlwo.getMethod());
		((HttpURLConnection) con).setInstanceFollowRedirects(false);
		con.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		con.setRequestProperty("Host", host);
		con.setRequestProperty("Cookie", cookies);

		if (urlwo.getParam() != null) {
			OutputStream outputStream = con.getOutputStream();
			outputStream.write(urlwo.getParam().getBytes());
			outputStream.flush();
			outputStream.close();
		}

		getCookieFromURLConnection(con);
	}

	private void getCookieFromURLConnection(URLConnection con) {
		Map<String, List<String>> cookie = con.getHeaderFields();

		if (cookie.containsKey("Set-Cookie")) {
			StringBuilder sb = new StringBuilder();

			List<String> cookieList = cookie.get("Set-Cookie");
			Iterator<String> i = cookieList.iterator();

			while (i.hasNext()) {
				sb.append(i.next());
				sb.append(";");
			}

			sb.append(cookies);
			cookies = sb.toString();
		} else {
			cookies = "";
		}
	}
}
