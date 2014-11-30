/*
 * Created on 13.07.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ch.truesolutions.payit.https;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import HTTPClient.HTTPConnection;
import HTTPClient.HTTPResponse;
import HTTPClient.ModuleException;
import HTTPClient.NVPair;
import HTTPClient.ParseException;

/**
 * @author Daniel
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class HttpsTest {

	public static void main(String[] args) {
		new HttpsTest().init();
	}

	private void init() {
		//	 Create a trust manager that does not validate certificate chains
		
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			//HttpsURLConnection
			//		.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HTTPConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Now you can access an https URL without having the certificate in the
		// truststore
		try {
//			String input = "41010263658477579:d7361s";
//			byte[] result = org.apache.commons.codec.binary.Base64
//					.encodeBase64(input.getBytes());
//			String auth = new String(result);
//
//			NVPair nvp1 = new NVPair("Connection", "keep-alive");
//			NVPair nvp2 = new NVPair("User-Agent", "PayIT");
//			NVPair nvp3 = new NVPair("Authorization", "Basic " + auth);
			//NVPair nvp3 = new NVPair("Username","41010263658477579");
			//NVPair nvp4 = new NVPair("Password","d7361s");
//			NVPair[] nvps = new NVPair[3];
//			nvps[0] = nvp1;
//			nvps[1] = nvp2;
//			nvps[2] = nvp3;
			//nvps[3] = nvp4;

			//HTTPConnection con = new HTTPConnection("https", "bsp0.billingservices.ch",-1);
			HTTPConnection con = new HTTPConnection("https", "csp1.billingservices.ch",-1);
			con.addBasicAuthorization(null,"41010263658477579","d7361s");
			//con.setAllowUserInteraction(true);
			//con.setDefaultHeaders(nvps);
			HTTPResponse resp = con
					.Get("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>"
							+ "<!DOCTYPE EDIActivationMessage SYSTEM \"CUX_V1.0.dtd\">"
							+ "<EDIActivationMessage xmlns=\"urn:CUXInterfaces:DataTypes\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
							+ "<SignOnRq>"
							+ "<DtClient>2004-07-18T00:01:59Z</DtClient>"
							+ "<ClientPID>41010263658477579</ClientPID>"
							+ "<Language>GER</Language>" + "</SignOnRq>"
							+ "<TrnRq>"
							+ "<TrnUID>Test Transaction Nr</TrnUID>"
							+ "<ClientCookie>EDIActivation</ClientCookie>"
							+ "</TrnRq>" + "<EDIActivationRequest>"
							+ "<CustomerPID>41010263658477579</CustomerPID>"
							+ "<CommunicationEDI>"
							+ "<InvoiceFormat>EIXML_LIGHT</InvoiceFormat>"
							+ "<ID IDType=\"USERNAME\">41010263658477579</ID>"
							+ "<Password>d7361s</Password>"
							+ "</CommunicationEDI>" + "<CommunicationPDF>"
							+ "<ID IDType=\"USERNAME\">dseiler</ID>"
							+ "<Password>d7361s</Password>"
							+ "</CommunicationPDF>" + "</EDIActivationRequest>"
							+ "</EDIActivationMessage>");

			/*
			HTTPResponse resp =
			con.Get("getshipmentlist.evl?username=41010263658477579&password=d7361s");
			*/
			System.out.println(resp.getText());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ModuleException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (ParseException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}
	}

}