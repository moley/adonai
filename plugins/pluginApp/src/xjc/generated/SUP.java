
package generated;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;
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
 *         &lt;element ref="{}STYLE"/&gt;
 *         &lt;element ref="{}GRAM"/&gt;
 *       &lt;/choice&gt;
 *       &lt;attribute name="art" use="required" type="{}SupType" /&gt;
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
@XmlRootElement(name = "SUP")
public class SUP {

    @XmlElementRefs({
        @XmlElementRef(name = "STYLE", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "GRAM", type = JAXBElement.class, required = false)
    })
    @XmlMixed
    protected List<Serializable> content;
    @XmlAttribute(name = "art", required = true)
    protected SupType art;

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
     * {@link JAXBElement }{@code <}{@link STYLE }{@code >}
     * {@link JAXBElement }{@code <}{@link STYLE }{@code >}
     * {@link JAXBElement }{@code <}{@link GRAM }{@code >}
     * {@link JAXBElement }{@code <}{@link GRAM }{@code >}
     * {@link JAXBElement }{@code <}{@link STYLE }{@code >}
     * {@link JAXBElement }{@code <}{@link GRAM }{@code >}
     * {@link String }
     * {@link JAXBElement }{@code <}{@link STYLE }{@code >}
     * {@link JAXBElement }{@code <}{@link GRAM }{@code >}
     * 
     * 
     */
    public List<Serializable> getContent() {
        if (content == null) {
            content = new ArrayList<Serializable>();
        }
        return this.content;
    }

    /**
     * Ruft den Wert der art-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link SupType }
     *     
     */
    public SupType getArt() {
        return art;
    }

    /**
     * Legt den Wert der art-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link SupType }
     *     
     */
    public void setArt(SupType value) {
        this.art = value;
    }

}
