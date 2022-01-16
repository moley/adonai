package org.adonai.model;

import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by OleyMa on 01.09.16.
 */
public class Line {

  private List<LinePart> lineParts = new ArrayList<LinePart>();

  public Line () {

  }

  public int getIndex (final LinePart after) {
    return lineParts.indexOf(after);
  }

  public void addFirst (final LinePart newPart) {
    lineParts.add(0, newPart);
  }

  public void addAfter (final LinePart after, final LinePart newPart) {
    lineParts.add(getIndex(after) + 1, newPart);
  }

  public Line (final String text) {
    setText(text);
  }


  @XmlTransient
  public String getText() {
    String completeText = "";
    for (LinePart next: lineParts) {
      completeText += next.getText();
    }
    return completeText;
  }

  @XmlTransient
  public String getFirstChord () {
    for (LinePart nextLinePart: lineParts){
      if (nextLinePart.getChord() != null)
        return nextLinePart.getChord();
    }

    return null;

  }

  @XmlTransient
  public String getPreview () {
    int PREVIEW_LENGTH = 20;
    return getText().length() > PREVIEW_LENGTH ? getText().substring(0, 20) + "..." : getText();
  }

  public LinePart getPreviousLinePart (LinePart linePart) {
    int index = getIndex(linePart);
    return index > 0 ? getLineParts().get(index - 1): null;
  }

  public LinePart getNextLinePart (LinePart linePart) {
    int index = getIndex(linePart);
    return (index < getLineParts().size() - 1)? getLineParts().get(index + 1): null;
  }

  public LinePart getFirstLinePart () {
    return lineParts.get(0);
  }

  public LinePart getLastLinePart () {
    return lineParts.get(lineParts.size() - 1);
  }

  public List<LinePart> getLineParts () {
    return lineParts;
  }

  public void setLineParts (List<LinePart> partList) {
    lineParts = partList;
  }

  public void setText(String text) {
    LinePart linePart = new LinePart(text);
    linePart.setText(text);
    lineParts.add(linePart);
  }

  public String toString () {
    String completeString = "";
    for (LinePart linePart: lineParts) {
      completeString += linePart.toString();
    }

    return completeString;
  }
}
