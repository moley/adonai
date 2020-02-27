
package generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{}INFORMATION"/&gt;
 *         &lt;element ref="{}BIBLEBOOK" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{}APPINFO" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="biblename" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="status" type="{}ModuleStatus" /&gt;
 *       &lt;attribute name="version" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="revision" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="type" type="{}ModuleType" default="x-bible" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "information",
    "biblebook",
    "appinfo"
})
public class XMLBIBLE {

    @XmlElementRef(name = "INFORMATION", type = JAXBElement.class)
    protected JAXBElement<INFORMATION> information;
    @XmlElementRef(name = "BIBLEBOOK", type = JAXBElement.class)
    protected List<JAXBElement<BIBLEBOOK>> biblebook;
    @XmlElementRef(name = "APPINFO", type = JAXBElement.class, required = false)
    protected List<JAXBElement<APPINFO>> appinfo;
    @XmlAttribute(name = "biblename", required = true)
    protected String biblename;
    @XmlAttribute(name = "status")
    protected ModuleStatus status;
    @XmlAttribute(name = "version")
    protected String version;
    @XmlAttribute(name = "revision")
    protected BigInteger revision;
    @XmlAttribute(name = "type")
    protected ModuleType type;

    /**
     * Ruft den Wert der information-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link INFORMATION }{@code >}
     *     {@link JAXBElement }{@code <}{@link INFORMATION }{@code >}
     *     {@link JAXBElement }{@code <}{@link INFORMATION }{@code >}
     *     
     */
    public JAXBElement<INFORMATION> getINFORMATION() {
        return information;
    }

    /**
     * Legt den Wert der information-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link INFORMATION }{@code >}
     *     {@link JAXBElement }{@code <}{@link INFORMATION }{@code >}
     *     {@link JAXBElement }{@code <}{@link INFORMATION }{@code >}
     *     
     */
    public void setINFORMATION(JAXBElement<INFORMATION> value) {
        this.information = value;
    }

    /**
     * Gets the value of the biblebook property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the biblebook property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBIBLEBOOK().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link BIBLEBOOK }{@code >}
     * {@link JAXBElement }{@code <}{@link BIBLEBOOK }{@code >}
     * {@link JAXBElement }{@code <}{@link BIBLEBOOK }{@code >}
     * 
     * 
     */
    public List<JAXBElement<BIBLEBOOK>> getBIBLEBOOK() {
        if (biblebook == null) {
            biblebook = new ArrayList<JAXBElement<BIBLEBOOK>>();
        }
        return this.biblebook;
    }

    /**
     * Gets the value of the appinfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the appinfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAPPINFO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link APPINFO }{@code >}
     * {@link JAXBElement }{@code <}{@link APPINFO }{@code >}
     * {@link JAXBElement }{@code <}{@link APPINFO }{@code >}
     * 
     * 
     */
    public List<JAXBElement<APPINFO>> getAPPINFO() {
        if (appinfo == null) {
            appinfo = new ArrayList<JAXBElement<APPINFO>>();
        }
        return this.appinfo;
    }

    /**
     * Ruft den Wert der biblename-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBiblename() {
        return biblename;
    }

    /**
     * Legt den Wert der biblename-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBiblename(String value) {
        this.biblename = value;
    }

    /**
     * Ruft den Wert der status-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ModuleStatus }
     *     
     */
    public ModuleStatus getStatus() {
        return status;
    }

    /**
     * Legt den Wert der status-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ModuleStatus }
     *     
     */
    public void setStatus(ModuleStatus value) {
        this.status = value;
    }

    /**
     * Ruft den Wert der version-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Legt den Wert der version-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Ruft den Wert der revision-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRevision() {
        return revision;
    }

    /**
     * Legt den Wert der revision-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRevision(BigInteger value) {
        this.revision = value;
    }

    /**
     * Ruft den Wert der type-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ModuleType }
     *     
     */
    public ModuleType getType() {
        if (type == null) {
            return ModuleType.X_BIBLE;
        } else {
            return type;
        }
    }

    /**
     * Legt den Wert der type-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ModuleType }
     *     
     */
    public void setType(ModuleType value) {
        this.type = value;
    }

}
