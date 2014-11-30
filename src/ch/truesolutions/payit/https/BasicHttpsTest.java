package ch.truesolutions.payit.https;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;

/**
 * TODO enter class description for class
 * 
 * @author <a href=mailto:daniel.seiler@truesolutions.ch>Daniel Seiler </a>
 *         Copyright (c) Daniel Seiler 2004
 */

public class BasicHttpsTest {

	public static void main(String[] args) throws Exception {

	    Protocol easyhttps = new Protocol("https", new EasySSLProtocolSocketFactory(), 443);
	    Protocol.registerProtocol("https", easyhttps);
				
		HttpClient client = new HttpClient();
		
		// set the credentials in the https header
		
		// production
		// client.getState().setCredentials(null, "csp1.billingservices.ch",
		// new UsernamePasswordCredentials("41010263658477579", "d7361s"));
		
		// training
		client.getState().setCredentials(null, "csp0.billingservices.ch",
				new UsernamePasswordCredentials("41010247375633592", "123456"));
		
		
		// create a POST method that reads a file over HTTPS, we're assuming
		// that this file requires basic authentication using the realm above.
		PostMethod post = new PostMethod("https://csp0.billingservices.ch");

		// Tell the POST method to automatically handle authentication. The
		// method will use any appropriate credentials to handle basic
		// authentication requests. Setting this value to false will cause
		// any request for authentication to return with a status of 401.
		// It will then be up to the client to handle the authentication.
		post.setDoAuthentication(true);
		
		// send the post request containing the EDIAActivation message
		String ediaActivationMsg = readXmlMsgFile("EDIActivation.xml");
		post.setRequestBody(ediaActivationMsg);
		int status = client.executeMethod(post);
		System.out.println("status: "+status+", response: '" + post.getResponseBody()+"'");
		// release any connection resources used by the method
		post.releaseConnection();
		
		// get the shipmentlist
		GetMethod get = new GetMethod("https://csp0.billingservices.ch/getshipmentlist.eval?USERNAME=PI_41010247375633592&PASSWORD=TestPayIt_11");
		int status2= client.executeMethod(get);
		System.out.println("status2: "+status2+", response: '" + new String(get.getResponseBody())+"'");
		
		// get the payment (with the shipmentid obtained from the shipmentlist)
		GetMethod getShipment = new GetMethod("https://csp0.billingservices.ch/getshipment.eval?USERNAME=PI_41010247375633592&PASSWORD=TestPayIt_11&SHIPMENTID=SC-29101603310000135882");
		int statusShipment= client.executeMethod(getShipment);		
		System.out.println("statusShipment: "+statusShipment+", response: '" + new String(getShipment.getResponseBody())+"'");
		
		// get the shipment pdf (again with the hardcoded shipmentid)
		/*
		GetMethod getShipmentPdf = new GetMethod("https://csp0.billingservices.ch/getshipment.eval?USERNAME=PI_41010247375633592&PASSWORD=TestPayIt_11&SHIPMENTID=SC-29101603310000135883");
		int statusShipmentPdf = client.executeMethod(getShipmentPdf);
		writeToFile("ShipmentPDF.pdf", getShipmentPdf.getResponseBody());
		System.out.println("statusShipmentPdf: "+statusShipmentPdf);
		*/

		// TODO send acknowledge of received shipment
		/*
		GetMethod getShipmentAck1 = new GetMethod("https://csp0.billingservices.ch/confirmsubmitted.eval?USERNAME=PI_41010247375633592&PASSWORD=TestPayIt_11&SHIPMENTID=SC-29101603310000135882");
		int statusShipmentAck1= client.executeMethod(getShipmentAck1);		
		System.out.println("statusShipmentAck1: "+statusShipmentAck1+", response: '" + new String(getShipmentAck1.getResponseBody())+"'");

		GetMethod getShipmentAck2 = new GetMethod("https://csp0.billingservices.ch/confirmsubmitted.eval?USERNAME=PI_41010247375633592&PASSWORD=TestPayIt_11&SHIPMENTID=SC-29101603310000135883");
		int statusShipmentAck2= client.executeMethod(getShipmentAck2);		
		System.out.println("statusShipmentAck2: "+statusShipmentAck2+", response: '" + new String(getShipmentAck2.getResponseBody())+"'");
		*/
		
		// release any connection resources used by the method
		//get.releaseConnection();

	}
	
	static void writeToFile(String fileName, byte[] data) {
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			fos.write(data);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	static String readXmlMsgFile(String fileName) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(fileName)));
		StringBuffer xml = new StringBuffer();
		while(br.ready()){
			xml.append(br.readLine());
		}
		br.close();
		return xml.toString();
	}


}