
package generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für ModuleType.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="ModuleType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="x-bible"/&gt;
 *     &lt;enumeration value="x-quran"/&gt;
 *     &lt;enumeration value="x-mormon"/&gt;
 *     &lt;enumeration value="x-other"/&gt;
 *     &lt;enumeration value="x-cult"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ModuleType")
@XmlEnum
public enum ModuleType {


    /**
     * christian bible text
     * 
     */
    @XmlEnumValue("x-bible")
    X_BIBLE("x-bible"),

    /**
     * text is a quran
     * 
     */
    @XmlEnumValue("x-quran")
    X_QURAN("x-quran"),

    /**
     * Mormon bible
     * 
     */
    @XmlEnumValue("x-mormon")
    X_MORMON("x-mormon"),

    /**
     * type is not assignable
     * 
     */
    @XmlEnumValue("x-other")
    X_OTHER("x-other"),

    /**
     * text is from a cult like Jehovah's Witnesses
     * 
     */
    @XmlEnumValue("x-cult")
    X_CULT("x-cult");
    private final String value;

    ModuleType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ModuleType fromValue(String v) {
        for (ModuleType c: ModuleType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
