package co.com.sc.nexura.superfinanciera.action.generic.services.trm.action;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.2.5
 * 2018-06-26T06:55:34.626-05:00
 * Generated source version: 3.2.5
 *
 */
@WebServiceClient(name = "TCRMServicesWebService",
                  wsdlLocation = "https://www.superfinanciera.gov.co/SuperfinancieraWebServiceTRM/TCRMServicesWebService/TCRMServicesWebService?WSDL",
                  targetNamespace = "http://action.trm.services.generic.action.superfinanciera.nexura.sc.com.co/")
public class TCRMServicesWebService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://action.trm.services.generic.action.superfinanciera.nexura.sc.com.co/", "TCRMServicesWebService");
    public final static QName TCRMServicesWebServicePort = new QName("http://action.trm.services.generic.action.superfinanciera.nexura.sc.com.co/", "TCRMServicesWebServicePort");
    static {
        URL url = null;
        try {
            url = new URL("https://www.superfinanciera.gov.co/SuperfinancieraWebServiceTRM/TCRMServicesWebService/TCRMServicesWebService?WSDL");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(TCRMServicesWebService.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "https://www.superfinanciera.gov.co/SuperfinancieraWebServiceTRM/TCRMServicesWebService/TCRMServicesWebService?WSDL");
        }
        WSDL_LOCATION = url;
    }

    public TCRMServicesWebService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public TCRMServicesWebService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public TCRMServicesWebService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public TCRMServicesWebService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public TCRMServicesWebService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public TCRMServicesWebService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns TCRMServicesInterface
     */
    @WebEndpoint(name = "TCRMServicesWebServicePort")
    public TCRMServicesInterface getTCRMServicesWebServicePort() {
        return super.getPort(TCRMServicesWebServicePort, TCRMServicesInterface.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns TCRMServicesInterface
     */
    @WebEndpoint(name = "TCRMServicesWebServicePort")
    public TCRMServicesInterface getTCRMServicesWebServicePort(WebServiceFeature... features) {
        return super.getPort(TCRMServicesWebServicePort, TCRMServicesInterface.class, features);
    }

}