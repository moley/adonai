
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
 *       &lt;choice maxOccurs="unbounded"&gt;
 *         &lt;element ref="{}CHAPTER" maxOccurs="unbounded"/&gt;
 *       &lt;/choice&gt;
 *       &lt;attribute name="bnumber" use="required" type="{}TVref" /&gt;
 *       &lt;attribute name="bname" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="bsname" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "chapter"
})
public class BIBLEBOOK {

    @XmlElementRef(name = "CHAPTER", type = JAXBElement.class, required = false)
    protected List<JAXBElement<CHAPTER>> chapter;
    @XmlAttribute(name = "bnumber", required = true)
    protected BigInteger bnumber;
    @XmlAttribute(name = "bname")
    protected String bname;
    @XmlAttribute(name = "bsname")
    protected String bsname;

    /**
     * Gets the value of the chapter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the chapter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCHAPTER().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link CHAPTER }{@code >}
     * {@link JAXBElement }{@code <}{@link CHAPTER }{@code >}
     * {@link JAXBElement }{@code <}{@link CHAPTER }{@code >}
     * 
     * 
     */
    public List<JAXBElement<CHAPTER>> getCHAPTER() {
        if (chapter == null) {
            chapter = new ArrayList<JAXBElement<CHAPTER>>();
        }
        return this.chapter;
    }

    /**
     * Ruft den Wert der bnumber-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBnumber() {
        return bnumber;
    }

    /**
     * Legt den Wert der bnumber-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBnumber(BigInteger value) {
        this.bnumber = value;
    }

    /**
     * Ruft den Wert der bname-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBname() {
        return bname;
    }

    /**
     * Legt den Wert der bname-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBname(String value) {
        this.bname = value;
    }

    /**
     * Ruft den Wert der bsname-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBsname() {
        return bsname;
    }

    /**
     * Legt den Wert der bsname-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBsname(String value) {
        this.bsname = value;
    }

}
