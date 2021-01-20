
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
 *         &lt;element ref="{}PROLOG" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}CAPTION" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}VERS" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{}REMARK" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}XREF" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}MEDIA" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/choice&gt;
 *       &lt;attribute name="cnumber" use="required" type="{}TVref" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "prologOrCAPTIONOrVERS"
})
public class CHAPTER {

    @XmlElementRefs({
        @XmlElementRef(name = "CAPTION", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "VERS", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "XREF", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "MEDIA", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "PROLOG", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "REMARK", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<?>> prologOrCAPTIONOrVERS;
    @XmlAttribute(name = "cnumber", required = true)
    protected BigInteger cnumber;

    /**
     * Gets the value of the prologOrCAPTIONOrVERS property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the prologOrCAPTIONOrVERS property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPROLOGOrCAPTIONOrVERS().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link REMARK }{@code >}
     * {@link JAXBElement }{@code <}{@link TMedia }{@code >}
     * {@link JAXBElement }{@code <}{@link VERS }{@code >}
     * {@link JAXBElement }{@code <}{@link XREF }{@code >}
     * {@link JAXBElement }{@code <}{@link VERS }{@code >}
     * {@link JAXBElement }{@code <}{@link PROLOG }{@code >}
     * {@link JAXBElement }{@code <}{@link CAPTION }{@code >}
     * {@link JAXBElement }{@code <}{@link PROLOG }{@code >}
     * {@link JAXBElement }{@code <}{@link VERS }{@code >}
     * {@link JAXBElement }{@code <}{@link PROLOG }{@code >}
     * {@link JAXBElement }{@code <}{@link XREF }{@code >}
     * {@link JAXBElement }{@code <}{@link TMedia }{@code >}
     * {@link JAXBElement }{@code <}{@link CAPTION }{@code >}
     * {@link JAXBElement }{@code <}{@link REMARK }{@code >}
     * {@link JAXBElement }{@code <}{@link XREF }{@code >}
     * {@link JAXBElement }{@code <}{@link REMARK }{@code >}
     * {@link JAXBElement }{@code <}{@link CAPTION }{@code >}
     * {@link JAXBElement }{@code <}{@link TMedia }{@code >}
     * 
     * 
     */
    public List<JAXBElement<?>> getPROLOGOrCAPTIONOrVERS() {
        if (prologOrCAPTIONOrVERS == null) {
            prologOrCAPTIONOrVERS = new ArrayList<JAXBElement<?>>();
        }
        return this.prologOrCAPTIONOrVERS;
    }

    /**
     * Ruft den Wert der cnumber-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCnumber() {
        return cnumber;
    }

    /**
     * Legt den Wert der cnumber-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCnumber(BigInteger value) {
        this.cnumber = value;
    }

}
