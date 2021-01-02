
package generated;

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
 *         &lt;element ref="{}STYLE" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}BR" minOccurs="0"/&gt;
 *         &lt;element ref="{}GRAM" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}XREF" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}SUP" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/choice&gt;
 *       &lt;attribute name="type" type="{}TNotesFix" /&gt;
 *       &lt;attribute name="n1" type="{}TNotesFix" /&gt;
 *       &lt;attribute name="n2" type="{}TNotesFix" /&gt;
 *       &lt;attribute name="n3" type="{}TNotesFix" /&gt;
 *       &lt;attribute name="n4" type="{}TNotesFix" /&gt;
 *       &lt;attribute name="n5" type="{}TNotesFix" /&gt;
 *       &lt;attribute name="ex" type="{}TNoteExternClass" /&gt;
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
public class NOTE {

    @XmlElementRefs({
        @XmlElementRef(name = "STYLE", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "SUP", type = SUP.class, required = false),
        @XmlElementRef(name = "XREF", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "GRAM", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "BR", type = JAXBElement.class, required = false)
    })
    @XmlMixed
    protected List<Object> content;
    @XmlAttribute(name = "type")
    protected TNotesFix type;
    @XmlAttribute(name = "n1")
    protected TNotesFix n1;
    @XmlAttribute(name = "n2")
    protected TNotesFix n2;
    @XmlAttribute(name = "n3")
    protected TNotesFix n3;
    @XmlAttribute(name = "n4")
    protected TNotesFix n4;
    @XmlAttribute(name = "n5")
    protected TNotesFix n5;
    @XmlAttribute(name = "ex")
    protected String ex;

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
     * {@link JAXBElement }{@code <}{@link STYLE }{@code >}
     * {@link SUP }
     * {@link JAXBElement }{@code <}{@link XREF }{@code >}
     * {@link JAXBElement }{@code <}{@link GRAM }{@code >}
     * {@link JAXBElement }{@code <}{@link BR }{@code >}
     * {@link JAXBElement }{@code <}{@link XREF }{@code >}
     * {@link JAXBElement }{@code <}{@link STYLE }{@code >}
     * {@link JAXBElement }{@code <}{@link STYLE }{@code >}
     * {@link JAXBElement }{@code <}{@link GRAM }{@code >}
     * {@link JAXBElement }{@code <}{@link GRAM }{@code >}
     * {@link JAXBElement }{@code <}{@link GRAM }{@code >}
     * {@link JAXBElement }{@code <}{@link XREF }{@code >}
     * {@link JAXBElement }{@code <}{@link BR }{@code >}
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
     * Ruft den Wert der type-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TNotesFix }
     *     
     */
    public TNotesFix getType() {
        return type;
    }

    /**
     * Legt den Wert der type-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TNotesFix }
     *     
     */
    public void setType(TNotesFix value) {
        this.type = value;
    }

    /**
     * Ruft den Wert der n1-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TNotesFix }
     *     
     */
    public TNotesFix getN1() {
        return n1;
    }

    /**
     * Legt den Wert der n1-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TNotesFix }
     *     
     */
    public void setN1(TNotesFix value) {
        this.n1 = value;
    }

    /**
     * Ruft den Wert der n2-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TNotesFix }
     *     
     */
    public TNotesFix getN2() {
        return n2;
    }

    /**
     * Legt den Wert der n2-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TNotesFix }
     *     
     */
    public void setN2(TNotesFix value) {
        this.n2 = value;
    }

    /**
     * Ruft den Wert der n3-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TNotesFix }
     *     
     */
    public TNotesFix getN3() {
        return n3;
    }

    /**
     * Legt den Wert der n3-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TNotesFix }
     *     
     */
    public void setN3(TNotesFix value) {
        this.n3 = value;
    }

    /**
     * Ruft den Wert der n4-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TNotesFix }
     *     
     */
    public TNotesFix getN4() {
        return n4;
    }

    /**
     * Legt den Wert der n4-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TNotesFix }
     *     
     */
    public void setN4(TNotesFix value) {
        this.n4 = value;
    }

    /**
     * Ruft den Wert der n5-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TNotesFix }
     *     
     */
    public TNotesFix getN5() {
        return n5;
    }

    /**
     * Legt den Wert der n5-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TNotesFix }
     *     
     */
    public void setN5(TNotesFix value) {
        this.n5 = value;
    }

    /**
     * Ruft den Wert der ex-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEx() {
        return ex;
    }

    /**
     * Legt den Wert der ex-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEx(String value) {
        this.ex = value;
    }

}
