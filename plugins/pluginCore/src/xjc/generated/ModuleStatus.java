
package generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für ModuleStatus.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="ModuleStatus"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="v"/&gt;
 *     &lt;enumeration value="w"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ModuleStatus")
@XmlEnum
public enum ModuleStatus {


    /**
     * Modul is valid again the schema
     * 
     */
    @XmlEnumValue("v")
    V("v"),

    /**
     * Modul is wellformed xml
     * 
     */
    @XmlEnumValue("w")
    W("w");
    private final String value;

    ModuleStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ModuleStatus fromValue(String v) {
        for (ModuleStatus c: ModuleStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
