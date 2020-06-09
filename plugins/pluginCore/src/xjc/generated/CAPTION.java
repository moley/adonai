
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
 *         &lt;element ref="{}STYLE" minOccurs="0"/&gt;
 *         &lt;element ref="{}BR" minOccurs="0"/&gt;
 *         &lt;element ref="{}GRAM" minOccurs="0"/&gt;
 *         &lt;element ref="{}XREF" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}SUP" minOccurs="0"/&gt;
 *       &lt;/choice&gt;
 *       &lt;attribute name="vref" use="required" type="{}TVref" /&gt;
 *       &lt;attribute name="count" type="{}TVref" /&gt;
 *       &lt;attribute name="type" type="{}CaptionType" /&gt;
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
public class CAPTION {

    @XmlElementRefs({
        @XmlElementRef(name = "BR", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "STYLE", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "XREF", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "SUP", type = SUP.class, required = false),
        @XmlElementRef(name = "GRAM", type = JAXBElement.class, required = false)
    })
    @XmlMixed
    protected List<Object> content;
    @XmlAttribute(name = "vref", required = true)
    protected BigInteger vref;
    @XmlAttribute(name = "count")
    protected BigInteger count;
    @XmlAttribute(name = "type")
    protected CaptionType type;

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
     * {@link String }
     * {@link JAXBElement }{@code <}{@link STYLE }{@code >}
     * {@link JAXBElement }{@code <}{@link GRAM }{@code >}
     * {@link JAXBElement }{@code <}{@link STYLE }{@code >}
     * {@link JAXBElement }{@code <}{@link BR }{@code >}
     * {@link JAXBElement }{@code <}{@link XREF }{@code >}
     * {@link SUP }
     * {@link JAXBElement }{@code <}{@link BR }{@code >}
     * {@link JAXBElement }{@code <}{@link STYLE }{@code >}
     * {@link JAXBElement }{@code <}{@link GRAM }{@code >}
     * {@link JAXBElement }{@code <}{@link XREF }{@code >}
     * {@link JAXBElement }{@code <}{@link STYLE }{@code >}
     * {@link JAXBElement }{@code <}{@link XREF }{@code >}
     * {@link JAXBElement }{@code <}{@link GRAM }{@code >}
     * {@link JAXBElement }{@code <}{@link GRAM }{@code >}
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
     * Ruft den Wert der vref-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVref() {
        return vref;
    }

    /**
     * Legt den Wert der vref-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVref(BigInteger value) {
        this.vref = value;
    }

    /**
     * Ruft den Wert der count-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCount() {
        return count;
    }

    /**
     * Legt den Wert der count-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCount(BigInteger value) {
        this.count = value;
    }

    /**
     * Ruft den Wert der type-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link CaptionType }
     *     
     */
    public CaptionType getType() {
        return type;
    }

    /**
     * Legt den Wert der type-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link CaptionType }
     *     
     */
    public void setType(CaptionType value) {
        this.type = value;
    }

}
