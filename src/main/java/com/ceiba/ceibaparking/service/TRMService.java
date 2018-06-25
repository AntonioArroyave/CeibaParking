package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import wsdl.*;


public class TRMService extends WebServiceGatewaySupport {

	private static final Logger log = LoggerFactory.getLogger(TRMService.class);

	public QueryTCRMResponse getQuote(String ticker) {

		QueryTCRM request = new QueryTCRM();

		QueryTCRMResponse response = (QueryTCRMResponse) getWebServiceTemplate()
				.marshalSendAndReceive("http://www.webservicex.com/stockquote.asmx",
						request,
						new SoapActionCallback("http://www.webserviceX.NET/GetQuote"));

		return response;
	}

}