
package com.epichust.cxf.ws.sap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>deliveryDetailDataTO complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="deliveryDetailDataTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="deliveryDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="deliveryPlan" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="deliveryPlanNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lineItemTypeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lotCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="mrlCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="mrlGrade" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="orderCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="orderRowNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="planPriQty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="rowNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="taskCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deliveryDetailDataTO", propOrder = {
    "deliveryDate",
    "deliveryPlan",
    "deliveryPlanNum",
    "lineItemTypeCode",
    "lotCode",
    "mrlCode",
    "mrlGrade",
    "orderCode",
    "orderRowNum",
    "planPriQty",
    "rowNum",
    "taskCode"
})
public class DeliveryDetailDataTO {

    protected String deliveryDate;
    protected String deliveryPlan;
    protected String deliveryPlanNum;
    protected String lineItemTypeCode;
    protected String lotCode;
    protected String mrlCode;
    protected String mrlGrade;
    protected String orderCode;
    protected String orderRowNum;
    protected String planPriQty;
    protected String rowNum;
    protected String taskCode;

    /**
     * 获取deliveryDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * 设置deliveryDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryDate(String value) {
        this.deliveryDate = value;
    }

    /**
     * 获取deliveryPlan属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryPlan() {
        return deliveryPlan;
    }

    /**
     * 设置deliveryPlan属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryPlan(String value) {
        this.deliveryPlan = value;
    }

    /**
     * 获取deliveryPlanNum属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryPlanNum() {
        return deliveryPlanNum;
    }

    /**
     * 设置deliveryPlanNum属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryPlanNum(String value) {
        this.deliveryPlanNum = value;
    }

    /**
     * 获取lineItemTypeCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLineItemTypeCode() {
        return lineItemTypeCode;
    }

    /**
     * 设置lineItemTypeCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLineItemTypeCode(String value) {
        this.lineItemTypeCode = value;
    }

    /**
     * 获取lotCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLotCode() {
        return lotCode;
    }

    /**
     * 设置lotCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLotCode(String value) {
        this.lotCode = value;
    }

    /**
     * 获取mrlCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMrlCode() {
        return mrlCode;
    }

    /**
     * 设置mrlCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMrlCode(String value) {
        this.mrlCode = value;
    }

    /**
     * 获取mrlGrade属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMrlGrade() {
        return mrlGrade;
    }

    /**
     * 设置mrlGrade属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMrlGrade(String value) {
        this.mrlGrade = value;
    }

    /**
     * 获取orderCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderCode() {
        return orderCode;
    }

    /**
     * 设置orderCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderCode(String value) {
        this.orderCode = value;
    }

    /**
     * 获取orderRowNum属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderRowNum() {
        return orderRowNum;
    }

    /**
     * 设置orderRowNum属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderRowNum(String value) {
        this.orderRowNum = value;
    }

    /**
     * 获取planPriQty属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanPriQty() {
        return planPriQty;
    }

    /**
     * 设置planPriQty属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanPriQty(String value) {
        this.planPriQty = value;
    }

    /**
     * 获取rowNum属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRowNum() {
        return rowNum;
    }

    /**
     * 设置rowNum属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRowNum(String value) {
        this.rowNum = value;
    }

    /**
     * 获取taskCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaskCode() {
        return taskCode;
    }

    /**
     * 设置taskCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaskCode(String value) {
        this.taskCode = value;
    }

}
