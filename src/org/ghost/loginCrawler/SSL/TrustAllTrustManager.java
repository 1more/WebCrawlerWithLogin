package org.ghost.loginCrawler.SSL;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * @author Hyeonwook Kim
 * @email khw0867@gmail.com
 * @classname TrustAllTrustManager.java
 * @package org.ghost.loginCrawler.SSL
 * @date 2015. 1. 31.
 *
 */
public class TrustAllTrustManager implements TrustManager, X509TrustManager {

	@Override
	public void checkClientTrusted(X509Certificate[] arg0, String arg1)
			throws CertificateException {
	}

	@Override
	public void checkServerTrusted(X509Certificate[] arg0, String arg1)
			throws CertificateException {
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}
