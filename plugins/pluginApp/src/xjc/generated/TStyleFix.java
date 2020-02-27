
package generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für TStyleFix.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="TStyleFix"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="acrostic"/&gt;
 *     &lt;enumeration value="bold"/&gt;
 *     &lt;enumeration value="emphasis"/&gt;
 *     &lt;enumeration value="illuminated"/&gt;
 *     &lt;enumeration value="italic"/&gt;
 *     &lt;enumeration value="line-through"/&gt;
 *     &lt;enumeration value="normal"/&gt;
 *     &lt;enumeration value="small-caps"/&gt;
 *     &lt;enumeration value="sub"/&gt;
 *     &lt;enumeration value="super"/&gt;
 *     &lt;enumeration value="underline"/&gt;
 *     &lt;enumeration value="overline"/&gt;
 *     &lt;enumeration value="capitalize"/&gt;
 *     &lt;enumeration value="uppercase"/&gt;
 *     &lt;enumeration value="lowercase"/&gt;
 *     &lt;enumeration value="divineName"/&gt;
 *     &lt;enumeration value="small"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TStyleFix")
@XmlEnum
public enum TStyleFix {

    @XmlEnumValue("acrostic")
    ACROSTIC("acrostic"),
    @XmlEnumValue("bold")
    BOLD("bold"),
    @XmlEnumValue("emphasis")
    EMPHASIS("emphasis"),
    @XmlEnumValue("illuminated")
    ILLUMINATED("illuminated"),
    @XmlEnumValue("italic")
    ITALIC("italic"),
    @XmlEnumValue("line-through")
    LINE_THROUGH("line-through"),
    @XmlEnumValue("normal")
    NORMAL("normal"),
    @XmlEnumValue("small-caps")
    SMALL_CAPS("small-caps"),
    @XmlEnumValue("sub")
    SUB("sub"),
    @XmlEnumValue("super")
    SUPER("super"),
    @XmlEnumValue("underline")
    UNDERLINE("underline"),
    @XmlEnumValue("overline")
    OVERLINE("overline"),
    @XmlEnumValue("capitalize")
    CAPITALIZE("capitalize"),
    @XmlEnumValue("uppercase")
    UPPERCASE("uppercase"),
    @XmlEnumValue("lowercase")
    LOWERCASE("lowercase"),
    @XmlEnumValue("divineName")
    DIVINE_NAME("divineName"),
    @XmlEnumValue("small")
    SMALL("small");
    private final String value;

    TStyleFix(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TStyleFix fromValue(String v) {
        for (TStyleFix c: TStyleFix.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
