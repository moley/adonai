
package generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für TNotesFix.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="TNotesFix"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="x-studynote"/&gt;
 *     &lt;enumeration value="n-studynote"/&gt;
 *     &lt;enumeration value="added"/&gt;
 *     &lt;enumeration value="allusion"/&gt;
 *     &lt;enumeration value="alternative"/&gt;
 *     &lt;enumeration value="background"/&gt;
 *     &lt;enumeration value="citation"/&gt;
 *     &lt;enumeration value="crossReference"/&gt;
 *     &lt;enumeration value="devotional"/&gt;
 *     &lt;enumeration value="encoder"/&gt;
 *     &lt;enumeration value="exegesis"/&gt;
 *     &lt;enumeration value="explanation"/&gt;
 *     &lt;enumeration value="liturgical"/&gt;
 *     &lt;enumeration value="speaker"/&gt;
 *     &lt;enumeration value="study"/&gt;
 *     &lt;enumeration value="translation"/&gt;
 *     &lt;enumeration value="variant"/&gt;
 *     &lt;enumeration value="amplified"/&gt;
 *     &lt;enumeration value="changed"/&gt;
 *     &lt;enumeration value="deleted"/&gt;
 *     &lt;enumeration value="implied"/&gt;
 *     &lt;enumeration value="moved"/&gt;
 *     &lt;enumeration value="tenseChange"/&gt;
 *     &lt;enumeration value="commentary"/&gt;
 *     &lt;enumeration value="exposition"/&gt;
 *     &lt;enumeration value="meditation"/&gt;
 *     &lt;enumeration value="outline"/&gt;
 *     &lt;enumeration value="rebuttal"/&gt;
 *     &lt;enumeration value="sermon"/&gt;
 *     &lt;enumeration value="studyGuide"/&gt;
 *     &lt;enumeration value="translation"/&gt;
 *     &lt;enumeration value="transChange"/&gt;
 *     &lt;enumeration value="tr"/&gt;
 *     &lt;enumeration value="trao"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TNotesFix")
@XmlEnum
public enum TNotesFix {


    /**
     * Do not use
     * 
     */
    @XmlEnumValue("x-studynote")
    X_STUDYNOTE("x-studynote"),

    /**
     * Do not use
     * 
     */
    @XmlEnumValue("n-studynote")
    N_STUDYNOTE("n-studynote"),
    @XmlEnumValue("added")
    ADDED("added"),
    @XmlEnumValue("allusion")
    ALLUSION("allusion"),
    @XmlEnumValue("alternative")
    ALTERNATIVE("alternative"),
    @XmlEnumValue("background")
    BACKGROUND("background"),
    @XmlEnumValue("citation")
    CITATION("citation"),
    @XmlEnumValue("crossReference")
    CROSS_REFERENCE("crossReference"),
    @XmlEnumValue("devotional")
    DEVOTIONAL("devotional"),
    @XmlEnumValue("encoder")
    ENCODER("encoder"),
    @XmlEnumValue("exegesis")
    EXEGESIS("exegesis"),
    @XmlEnumValue("explanation")
    EXPLANATION("explanation"),
    @XmlEnumValue("liturgical")
    LITURGICAL("liturgical"),
    @XmlEnumValue("speaker")
    SPEAKER("speaker"),
    @XmlEnumValue("study")
    STUDY("study"),
    @XmlEnumValue("translation")
    TRANSLATION("translation"),
    @XmlEnumValue("variant")
    VARIANT("variant"),
    @XmlEnumValue("amplified")
    AMPLIFIED("amplified"),
    @XmlEnumValue("changed")
    CHANGED("changed"),
    @XmlEnumValue("deleted")
    DELETED("deleted"),
    @XmlEnumValue("implied")
    IMPLIED("implied"),
    @XmlEnumValue("moved")
    MOVED("moved"),
    @XmlEnumValue("tenseChange")
    TENSE_CHANGE("tenseChange"),
    @XmlEnumValue("commentary")
    COMMENTARY("commentary"),
    @XmlEnumValue("exposition")
    EXPOSITION("exposition"),
    @XmlEnumValue("meditation")
    MEDITATION("meditation"),
    @XmlEnumValue("outline")
    OUTLINE("outline"),
    @XmlEnumValue("rebuttal")
    REBUTTAL("rebuttal"),
    @XmlEnumValue("sermon")
    SERMON("sermon"),
    @XmlEnumValue("studyGuide")
    STUDY_GUIDE("studyGuide"),
    @XmlEnumValue("transChange")
    TRANS_CHANGE("transChange"),

    /**
     * Textus Receptus
     * 
     */
    @XmlEnumValue("tr")
    TR("tr"),

    /**
     * Textus Receptus and other
     * 
     */
    @XmlEnumValue("trao")
    TRAO("trao");
    private final String value;

    TNotesFix(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TNotesFix fromValue(String v) {
        for (TNotesFix c: TNotesFix.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
