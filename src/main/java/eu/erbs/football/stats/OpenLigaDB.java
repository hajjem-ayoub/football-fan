package eu.erbs.football.stats;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;


@SuppressWarnings("restriction")
public class OpenLigaDB {

	/**
	 * Starting point for the SAAJ - SOAP Client Testing
	 */
	public static void main(String args[]) {
		try {
			// Create SOAP Connection
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();

			// Send SOAP Message to SOAP Server
			String url = "http://www.openligadb.de/Webservices/Sportsdata.asmx";
			SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), url);

			//        	POST /Webservices/Sportsdata.asmx HTTP/1.1
			//        	Host: www.openligadb.de
			//        	Content-Type: application/soap+xml; charset=utf-8
			//        	Content-Length: length

			// Process the SOAP Response
			printSOAPResponse(soapResponse);

			soapConnection.close();
		} catch (Exception e) {
			System.err.println("Error occurred while sending SOAP Request to Server");
			e.printStackTrace();
		}
	}

	private static SOAPMessage createSOAPRequest() throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();

		String serverURI = "http://msiggi.de/Sportsdata/Webservices";

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("", serverURI);

		//    	<?xml version="1.0" encoding="utf-8"?>
		//    	<soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
		//    	  <soap12:Body>
		//    	    <GetMatchdataByGroupLeagueSaison xmlns="http://msiggi.de/Sportsdata/Webservices">
		//    	      <groupOrderID>int</groupOrderID>
		//    	      <leagueShortcut>string</leagueShortcut>
		//    	      <leagueSaison>string</leagueSaison>
		//    	    </GetMatchdataByGroupLeagueSaison>
		//    	  </soap12:Body>
		//    	</soap12:Envelope>


		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", "http://msiggi.de/Sportsdata/Webservices/GetMatchdataByGroupLeagueSaison");

		// SOAP Body
		SOAPBody soapBody = envelope.getBody();
		SOAPElement actionElement = soapBody.addChildElement("GetMatchdataByGroupLeagueSaison");
		SOAPElement groupOrderIDElement = actionElement.addChildElement("groupOrderID");
		groupOrderIDElement.addTextNode("1");
		SOAPElement leagueShortcutElement = actionElement.addChildElement("leagueShortcut");
		leagueShortcutElement.addTextNode("bl1");
		SOAPElement leagueSaisonElement = actionElement.addChildElement("leagueSaison");
		leagueSaisonElement.addTextNode("2016");

		soapMessage.saveChanges();

		/* Print the request message */
		System.out.print("Request SOAP Message = ");
		soapMessage.writeTo(System.out);
		System.out.println();

		return soapMessage;
	}

	/**
	 * Method used to print the SOAP Response
	 */
	private static void printSOAPResponse(SOAPMessage soapResponse) throws Exception {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		Source sourceContent = soapResponse.getSOAPPart().getContent();
		System.out.print("\nResponse SOAP Message = ");
		StreamResult result = new StreamResult(System.out);
		transformer.transform(sourceContent, result);
	}		    
}