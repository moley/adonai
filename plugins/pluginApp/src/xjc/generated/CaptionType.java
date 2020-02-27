
package generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für CaptionType.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="CaptionType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="x-h1"/&gt;
 *     &lt;enumeration value="x-h2"/&gt;
 *     &lt;enumeration value="x-h3"/&gt;
 *     &lt;enumeration value="x-h4"/&gt;
 *     &lt;enumeration value="x-h5"/&gt;
 *     &lt;enumeration value="x-h6"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "CaptionType")
@XmlEnum
public enum CaptionType {

    @XmlEnumValue("x-h1")
    X_H_1("x-h1"),
    @XmlEnumValue("x-h2")
    X_H_2("x-h2"),
    @XmlEnumValue("x-h3")
    X_H_3("x-h3"),
    @XmlEnumValue("x-h4")
    X_H_4("x-h4"),
    @XmlEnumValue("x-h5")
    X_H_5("x-h5"),
    @XmlEnumValue("x-h6")
    X_H_6("x-h6");
    private final String value;

    CaptionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CaptionType fromValue(String v) {
        for (CaptionType c: CaptionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
