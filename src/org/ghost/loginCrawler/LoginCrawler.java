package org.ghost.loginCrawler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.ghost.loginCrawler.SSL.TrustAllHostnameVerifier;
import org.ghost.loginCrawler.SSL.TrustAllTrustManager;
import org.ghost.loginCrawler.model.URLWithOption;

/**
 * @author Hyeonwook Kim
 * @email khw0867@gmail.com
 * @classname LoginCrawler.java
 * @package org.ghost.loginCrawler
 * @date 2015. 1. 30.
 * @purpose : Crawling web page which needs login process
 *
 * @comment :
 *
 */
public class LoginCrawler {
	private String cookies;

	/** Constructor **/
	public LoginCrawler() {
		cookies = null;
	}

	/** Getter and Setter **/
	public String getCookies() {
		return cookies;
	}

	/**
	 * @param String
	 *            [] loginURLs : properly ordered url strings which is used for
	 *            login.
	 * @return String which contains cookie values
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public String doLogin(URLWithOption[] loginURLs) throws IOException,
			NoSuchAlgorithmException, KeyManagementException {

		// clear previous cookie data
		cookies = null;

		// do login process
		for (int i = 0; i < loginURLs.length; i++) {
			this.makeCookie(loginURLs[i].getUrl(), loginURLs[i].getParam(),
					loginURLs[i].getMethod());
		}

		return cookies;
	}

	public InputStream crawl(String url) throws IOException,
			KeyManagementException, NoSuchAlgorithmException {
		URLConnection con;
		URL urlObj = new URL(url);

		String protocol = urlObj.getProtocol();

		if (protocol.equals("https")) {
			con = (HttpsURLConnection) urlObj.openConnection();
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
		trustManager[1] = new TrustAllTrustManager();

		SSLContext socketContext = SSLContext.getInstance("SSL");
		socketContext.init(null, trustManager, null);

		HttpsURLConnection.setDefaultSSLSocketFactory(socketContext
				.getSocketFactory());
		HttpsURLConnection
				.setDefaultHostnameVerifier(new TrustAllHostnameVerifier());
	}

	private void makeCookie(String urlString, String param, String method)
			throws IOException, NoSuchAlgorithmException,
			KeyManagementException {
		URLConnection con;
		URL url = new URL(urlString);

		String host = url.getHost();
		String protocol = url.getProtocol();

		if (protocol.equals("https")) {
			con = (HttpsURLConnection) url.openConnection();
		} else {
			con = (HttpURLConnection) url.openConnection();
		}

		con.setUseCaches(true);
		con.setDoOutput(true);
		((HttpURLConnection) con).setRequestMethod(method);
		((HttpURLConnection) con).setInstanceFollowRedirects(false);
		con.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		con.setRequestProperty("Host", host);
		con.setRequestProperty("Cookie", cookies);

		if (param != null) {
			OutputStream outputStream = con.getOutputStream();
			outputStream.write(param.getBytes());
			outputStream.flush();
			outputStream.close();
		}

		getCookieFromURLConnection(con);
	}

	private void getCookieFromURLConnection(URLConnection con) {
		String cookieHeader = con.getHeaderField("Set-Cookie");

		if (cookieHeader != null) {
			if (cookies != null) {
				cookies += ";" + cookieHeader;
			} else {
				cookies = cookieHeader;
			}
		}
	}
}
