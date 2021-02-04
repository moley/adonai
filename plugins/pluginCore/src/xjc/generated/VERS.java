
package generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
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
 *       &lt;choice maxOccurs="unbounded" minOccurs="0"&gt;
 *         &lt;element ref="{}GRAM" minOccurs="0"/&gt;
 *         &lt;element ref="{}STYLE" minOccurs="0"/&gt;
 *         &lt;element ref="{}NOTE" minOccurs="0"/&gt;
 *         &lt;element ref="{}BR" minOccurs="0"/&gt;
 *         &lt;element ref="{}SUP" minOccurs="0"/&gt;
 *         &lt;element ref="{}XREF" minOccurs="0"/&gt;
 *       &lt;/choice&gt;
 *       &lt;attribute name="vnumber" type="{}TVref" /&gt;
 *       &lt;attribute name="v" type="{}TVref" /&gt;
 *       &lt;attribute name="e" type="{}TAix" /&gt;
 *       &lt;attribute name="aix" type="{}TAix" /&gt;
 *       &lt;attribute name="p" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "content"
})
public class VERS {

    @XmlElementRefs({
        @XmlElementRef(name = "GRAM", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "NOTE", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "XREF", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "STYLE", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "BR", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "SUP", type = SUP.class, required = false)
    })
    @XmlMixed
    protected List<Object> content;
    @XmlAttribute(name = "vnumber")
    protected BigInteger vnumber;
    @XmlAttribute(name = "v")
    protected BigInteger v;
    @XmlAttribute(name = "e")
    protected String e;
    @XmlAttribute(name = "aix")
    protected String aix;
    @XmlAttribute(name = "p")
    protected Boolean p;

    /**
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link BR }{@code >}
     * {@link JAXBElement }{@code <}{@link STYLE }{@code >}
     * {@link JAXBElement }{@code <}{@link NOTE }{@code >}
     * {@link JAXBElement }{@code <}{@link GRAM }{@code >}
     * {@link JAXBElement }{@code <}{@link XREF }{@code >}
     * {@link JAXBElement }{@code <}{@link STYLE }{@code >}
     * {@link JAXBElement }{@code <}{@link NOTE }{@code >}
     * {@link JAXBElement }{@code <}{@link XREF }{@code >}
     * {@link JAXBElement }{@code <}{@link GRAM }{@code >}
     * {@link JAXBElement }{@code <}{@link NOTE }{@code >}
     * {@link JAXBElement }{@code <}{@link XREF }{@code >}
     * {@link JAXBElement }{@code <}{@link GRAM }{@code >}
     * {@link JAXBElement }{@code <}{@link BR }{@code >}
     * {@link JAXBElement }{@code <}{@link GRAM }{@code >}
     * {@link JAXBElement }{@code <}{@link STYLE }{@code >}
     * {@link JAXBElement }{@code <}{@link STYLE }{@code >}
     * {@link SUP }
     * {@link String }
     * 
     * 
     */
    public List<Object> getContent() {
        if (content == null) {
            content = new ArrayList<Object>();
        }
        return this.content;
    }

    /**
     * Ruft den Wert der vnumber-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVnumber() {
        return vnumber;
    }

    /**
     * Legt den Wert der vnumber-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVnumber(BigInteger value) {
        this.vnumber = value;
    }

    /**
     * Ruft den Wert der v-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getV() {
        return v;
    }

    /**
     * Legt den Wert der v-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setV(BigInteger value) {
        this.v = value;
    }

    /**
     * Ruft den Wert der e-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getE() {
        return e;
    }

    /**
     * Legt den Wert der e-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setE(String value) {
        this.e = value;
    }

    /**
     * Ruft den Wert der aix-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAix() {
        return aix;
    }

    /**
     * Legt den Wert der aix-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAix(String value) {
        this.aix = value;
    }

    /**
     * Ruft den Wert der p-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isP() {
        return p;
    }

    /**
     * Legt den Wert der p-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setP(Boolean value) {
        this.p = value;
    }

}
