package ch.truesolutions.payit.https;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;

/**
 * TODO enter class description for class 
 * 
 * @author <a href=mailto:daniel.seiler@truesolutions.ch>Daniel Seiler</a>
 * Copyright (c) Daniel Seiler 2004
 */

public class PayNetConsoleClient {

	private static final String PAYNET_TRAINING_URL = "csp0.billingservices.ch";
	private static final String PID_TRAINING = "41010247375633592";
	private static final String ACTIVATION_KEY_TRAINING = "123456";
	private static final String PAYNET_PRODUCTION_URL = "csp1.billingservices.ch";
	
	private HttpClient client = null;
	private final String PAYNET_URL;
	private final String PID;
	private final String ACTIVATION_KEY;
	public static void main(String[] args) {
		new PayNetConsoleClient();
	}

	public PayNetConsoleClient() {
		PAYNET_URL = PAYNET_TRAINING_URL;
		PID = PID_TRAINING;
		ACTIVATION_KEY = ACTIVATION_KEY_TRAINING;
	    Protocol easyhttps = new Protocol("https", new EasySSLProtocolSocketFactory(), 443);
	    Protocol.registerProtocol("https", easyhttps);
				
		client = new HttpClient();

		//logger.info("testing commons logging!");
		while (true) {
			System.out.print("> ");
			String command = null;
			try {
				command =
					new BufferedReader(new InputStreamReader(System.in)).readLine();
				if(!"".equals(command.trim())) {
					System.out.println(evaluateCommand(command));
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (RuntimeException e2) {
				e2.printStackTrace();
			}
		}
	}
		
	public String evaluateCommand(String command) throws IOException, HttpException {
		StringBuffer sb = new StringBuffer();
		StringTokenizer st = new StringTokenizer(command," ");
		st.nextToken();
		int status = -1;
		if("-h".equals(command)){
			sb.append("Commands for Abalone client console:\n");
			sb.append("===============================================================\n");
			sb.append("-activate                           activate the edi data channel\n"); 
			sb.append("-deactivate                         deactivate the edi channel\n");
			sb.append("-shipmentlist                       get the shipmentlist\n");
			sb.append("-shipment <shipment-id>             fetch a shipment\n");
			sb.append("-ackshipment <shipment-id>          acknowledge a received shipment\n");
			sb.append("-q                                  quit the client console\n");
		} else if(command.startsWith("-activate")){
			client.getState().setCredentials(null, PAYNET_URL,
					new UsernamePasswordCredentials(PID, ACTIVATION_KEY));
			// create a POST method that reads a file over HTTPS
			PostMethod post = new PostMethod("https://"+PAYNET_URL);
			// Tell the POST method to automatically handle authentication. The
			// method will use any appropriate credentials to handle basic
			// authentication requests. Setting this value to false will cause
			// any request for authentication to return with a status of 401.
			// It will then be up to the client to handle the authentication.
			post.setDoAuthentication(true);			
			// send the post request containing the EDIAActivation message
			String ediaActivationMsg = readXmlMsgFile("EDIActivation.xml");
			post.setRequestBody(ediaActivationMsg);
			status = client.executeMethod(post);
			sb.append(new String(post.getResponseBody()));
			// release any connection resources used by the method
			post.releaseConnection();		
		} else if(command.startsWith("-shipmentlist")){
			GetMethod get = new GetMethod("https://"+PAYNET_URL+"/getshipmentlist.eval?USERNAME=PI_"+PID+"&PASSWORD=TestPayIt_11");
			status = client.executeMethod(get);
			sb.append(new String(get.getResponseBody()));
		} else if(command.startsWith("-shipment")){
			String shipment_id = null;
			if(st.hasMoreTokens()) {
				shipment_id = st.nextToken();
			}
			// for test only
			boolean isPdf = false;
			if(shipment_id == null) {
				shipment_id = "SC-29101603310000135882";
			} else if(shipment_id.equals("pdf")) {
				isPdf = true;
				shipment_id = "SC-29101603310000135883";
			}
			GetMethod get = new GetMethod("https://"+PAYNET_URL+"/getshipment.eval?USERNAME=PI_"+PID+"&PASSWORD=TestPayIt_11&SHIPMENTID="+shipment_id);
			status = client.executeMethod(get);
			if(isPdf) {
				// if its a pdf we write it to a file
				writeToFile("ShipmentPDF.pdf", get.getResponseBody());
			} else {
				sb.append(new String(get.getResponseBody()));
			}		
		} else if(command.startsWith("-ackshipment")){
			String shipment_id = null;
			if(st.hasMoreTokens()) {
				shipment_id = st.nextToken();
			}
			// for test only
			boolean isPdf = false;
			if(shipment_id == null) {
				shipment_id = "SC-29101603310000135882";
			} else if(shipment_id.equals("pdf")) {
				isPdf = true;
				shipment_id = "SC-29101603310000135883";
			}
			GetMethod get = new GetMethod("https://"+PAYNET_URL+"/confirmsubmitted.eval?USERNAME=PI_"+PID+"&PASSWORD=TestPayIt_11&SHIPMENTID="+shipment_id);
			status = client.executeMethod(get);		
			sb.append(new String(get.getResponseBody()));

		} else if(command.startsWith("-deactivate")){
			client.getState().setCredentials(null, PAYNET_URL,
					new UsernamePasswordCredentials(PID, ACTIVATION_KEY));
			// create a POST method that reads a file over HTTPS
			PostMethod post = new PostMethod("https://"+PAYNET_URL);
			// Tell the POST method to automatically handle authentication. The
			// method will use any appropriate credentials to handle basic
			// authentication requests. Setting this value to false will cause
			// any request for authentication to return with a status of 401.
			// It will then be up to the client to handle the authentication.
			post.setDoAuthentication(true);			
			// send the post request containing the EDIAActivation message
			String ediDeactivationMsg = readXmlMsgFile("EDIDeactivation.xml");
			post.setRequestBody(ediDeactivationMsg);
			status = client.executeMethod(post);
			sb.append(new String(post.getResponseBody()));
			// release any connection resources used by the method
			post.releaseConnection();		
		
		} else if(command.startsWith("-q")){
			System.out.println("bye...");
			System.exit(0);
		}

		sb.append("\nResponse status: ");
		sb.append(status);
		return sb.toString();
	}

	void writeToFile(String fileName, byte[] data) {
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			fos.write(data);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	String readXmlMsgFile(String fileName){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(fileName)));
			StringBuffer xml = new StringBuffer();
			while(br.ready()){
				xml.append(br.readLine());
			}
			br.close();
			return xml.toString();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
