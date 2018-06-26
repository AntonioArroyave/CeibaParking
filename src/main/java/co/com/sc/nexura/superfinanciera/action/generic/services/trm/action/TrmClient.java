package co.com.sc.nexura.superfinanciera.action.generic.services.trm.action;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;

import co.com.sc.nexura.superfinanciera.action.generic.services.trm.action.TCRMServicesInterface;
import co.com.sc.nexura.superfinanciera.action.generic.services.trm.action.TCRMServicesWebService;
import co.com.sc.nexura.superfinanciera.action.generic.services.trm.action.TcrmResponse;


public class TrmClient {
	
	TCRMServicesInterface trm;
	
	public TrmClient(String endpoitn){
		if (null == trm) {
			try {
				TCRMServicesWebService webService = new TCRMServicesWebService();
				this.trm = webService.getTCRMServicesWebServicePort();
				BindingProvider bindprovider = (BindingProvider) this.trm;
				bindprovider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoitn);
			} catch (Exception e) {
				System.out.println("error iniciando endpoint");
			}
		}
		
	}
	
	public Float getTrm(){
		TcrmResponse  response = null;
	
		GregorianCalendar hoy = new GregorianCalendar();
		XMLGregorianCalendar hoyXML = null;
		try {
			hoyXML = DatatypeFactory.newInstance().newXMLGregorianCalendar(hoy);
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		response = trm.queryTCRM(hoyXML);
		return response.getValue();
		
	}
	

}
