
package generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für SupType.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="SupType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="x-sup"/&gt;
 *     &lt;enumeration value="x-sub"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "SupType")
@XmlEnum
public enum SupType {


    /**
     * Höherstellung
     * 
     */
    @XmlEnumValue("x-sup")
    X_SUP("x-sup"),

    /**
     * Tieferstellung
     * 
     */
    @XmlEnumValue("x-sub")
    X_SUB("x-sub");
    private final String value;

    SupType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SupType fromValue(String v) {
        for (SupType c: SupType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
