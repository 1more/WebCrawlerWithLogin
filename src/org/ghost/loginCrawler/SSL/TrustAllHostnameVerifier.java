package org.ghost.loginCrawler.SSL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * @author Hyeonwook Kim, Youngkwnag Han
 * @email khw0867@gmail.com
 * @classname TrustAllHostnameVerifier.java
 * @package org.ghost.loginCrawler.SSL
 * @date 2015. 2. 01.
 *
 */
public class TrustAllHostnameVerifier implements HostnameVerifier {
	
	@Override
	public boolean verify(String hostname, SSLSession session) {
		// print warning message
		// System.out.println("hostname: " + hostname+ ", session: " + session.getPeerHost());
		return true;
	}

}
