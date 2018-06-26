package co.com.sc.nexura.superfinanciera.action.generic.services.trm.action;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.2.5
 * 2018-06-26T06:55:34.619-05:00
 * Generated source version: 3.2.5
 *
 */
@WebService(targetNamespace = "http://action.trm.services.generic.action.superfinanciera.nexura.sc.com.co/", name = "TCRMServicesInterface")
@XmlSeeAlso({ObjectFactory.class})
public interface TCRMServicesInterface {

    @WebMethod
    @RequestWrapper(localName = "queryTCRM", targetNamespace = "http://action.trm.services.generic.action.superfinanciera.nexura.sc.com.co/", className = "co.com.sc.nexura.superfinanciera.action.generic.services.trm.action.QueryTCRM")
    @ResponseWrapper(localName = "queryTCRMResponse", targetNamespace = "http://action.trm.services.generic.action.superfinanciera.nexura.sc.com.co/", className = "co.com.sc.nexura.superfinanciera.action.generic.services.trm.action.QueryTCRMResponse")
    @WebResult(name = "return", targetNamespace = "")
    public co.com.sc.nexura.superfinanciera.action.generic.services.trm.action.TcrmResponse queryTCRM(
        @WebParam(name = "tcrmQueryAssociatedDate", targetNamespace = "")
        javax.xml.datatype.XMLGregorianCalendar tcrmQueryAssociatedDate
    );
}
