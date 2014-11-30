package ch.truesolutions.payit.https;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.httpclient.protocol.SecureProtocolSocketFactory;

/**
 * TODO enter class description for class 
 * 
 * @author <a href=mailto:daniel.seiler@truesolutions.ch>Daniel Seiler</a>
 * Copyright (c) Daniel Seiler 2004
 */

public class JessieSocketFactory implements SecureProtocolSocketFactory {

	SSLContext ctx = null;
	SSLSocketFactory factory = null;
	
	public JessieSocketFactory() {
		try {
			Class.forName("COM.claymoresystems.ptls.SSLContext");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ctx = SSLContext.getInstance("SSLv3", "ClaymoreProvider");
			//ctx = new SSLContext();
			ctx.init(null, null, null);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    factory = ctx.getSocketFactory();

	}
	/* (non-Javadoc)
	 * @see org.apache.commons.httpclient.protocol.SecureProtocolSocketFactory#createSocket(java.net.Socket, java.lang.String, int, boolean)
	 */
	public Socket createSocket(Socket s, String host, int port, boolean autoClose)
			throws IOException, UnknownHostException {
	    Socket socket = factory.createSocket(s, host, port, autoClose);
	    ((SSLSocket) socket).setEnableSessionCreation(true);

		return socket;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.httpclient.protocol.ProtocolSocketFactory#createSocket(java.lang.String, int, java.net.InetAddress, int)
	 */
	public Socket createSocket(String host, int port, InetAddress localHost, int localPort)
			throws IOException, UnknownHostException {
	    Socket socket = factory.createSocket(host, port, localHost, localPort);
	    ((SSLSocket) socket).setEnableSessionCreation(true);

		return socket;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.httpclient.protocol.ProtocolSocketFactory#createSocket(java.lang.String, int)
	 */
	public Socket createSocket(String host, int port) throws IOException,
			UnknownHostException {
	    Socket socket = factory.createSocket(host, port);
	    ((SSLSocket) socket).setEnableSessionCreation(true);

		return socket;
	}

}
