
package generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für BreakType.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="BreakType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="x-nl"/&gt;
 *     &lt;enumeration value="x-p"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "BreakType")
@XmlEnum
public enum BreakType {


    /**
     * Neue Zeile (new line)
     * 
     */
    @XmlEnumValue("x-nl")
    X_NL("x-nl"),

    /**
     * Neuer Absatz (new paragraph)
     * 
     */
    @XmlEnumValue("x-p")
    X_P("x-p");
    private final String value;

    BreakType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BreakType fromValue(String v) {
        for (BreakType c: BreakType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
