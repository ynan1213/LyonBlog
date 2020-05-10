
package com.epichust.cxf.ws.sap;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.epichust.cxf.ws.sap package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SaveDeliveryData_QNAME = new QName("http://sap.ws.cxf.epichust.com/", "saveDeliveryData");
    private final static QName _SaveDeliveryDataResponse_QNAME = new QName("http://sap.ws.cxf.epichust.com/", "saveDeliveryDataResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.epichust.cxf.ws.sap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SaveDeliveryData }
     * 
     */
    public SaveDeliveryData createSaveDeliveryData() {
        return new SaveDeliveryData();
    }

    /**
     * Create an instance of {@link SaveDeliveryDataResponse }
     * 
     */
    public SaveDeliveryDataResponse createSaveDeliveryDataResponse() {
        return new SaveDeliveryDataResponse();
    }

    /**
     * Create an instance of {@link DeliveryDataTO }
     * 
     */
    public DeliveryDataTO createDeliveryDataTO() {
        return new DeliveryDataTO();
    }

    /**
     * Create an instance of {@link DeliveryDetailDataTO }
     * 
     */
    public DeliveryDetailDataTO createDeliveryDetailDataTO() {
        return new DeliveryDetailDataTO();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveDeliveryData }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SaveDeliveryData }{@code >}
     */
    @XmlElementDecl(namespace = "http://sap.ws.cxf.epichust.com/", name = "saveDeliveryData")
    public JAXBElement<SaveDeliveryData> createSaveDeliveryData(SaveDeliveryData value) {
        return new JAXBElement<SaveDeliveryData>(_SaveDeliveryData_QNAME, SaveDeliveryData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveDeliveryDataResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SaveDeliveryDataResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://sap.ws.cxf.epichust.com/", name = "saveDeliveryDataResponse")
    public JAXBElement<SaveDeliveryDataResponse> createSaveDeliveryDataResponse(SaveDeliveryDataResponse value) {
        return new JAXBElement<SaveDeliveryDataResponse>(_SaveDeliveryDataResponse_QNAME, SaveDeliveryDataResponse.class, null, value);
    }

}
