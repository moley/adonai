package org.adonai.model;

import javafx.beans.property.SimpleStringProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SongStructItem {

  private final static Logger LOGGER = LoggerFactory.getLogger(SongStructItem.class);

  /**
   * text to be shown in structure (e.g. VERS 1)
   */
  private String text;

  /**
   * shorttext to be shown (e.g. V1)
   */
  private String shorttext;

  /**
   * id of the part
   */
  private String partId;

  /**
   * flag if this is the first occurence of this part or
   * a following one
   */
  private boolean firstOccurence;

  private SimpleStringProperty quantity = new SimpleStringProperty();
  private SimpleStringProperty remarks = new SimpleStringProperty();

  public SongStructItem () {

  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getPartId() {
    return partId;
  }

  public void setPartId(String partId) {
    this.partId = partId;
  }

  public String getRemarks() {
    return remarks.getValue();
  }

  public void setRemarks(String remarks) {
    this.remarks.set(remarks);
  }

  public String getQuantity() {
    return quantity.getValue();
  }

  public void setQuantity(String quantity) {
    this.quantity.set(quantity);
  }

  public SimpleStringProperty quantityProperty () {
    return quantity;
  }

  public SimpleStringProperty remarksProperty () {
    return remarks;
  }

  public boolean isFirstOccurence() {
    return firstOccurence;
  }

  public void setFirstOccurence(boolean firstOccurence) {
    this.firstOccurence = firstOccurence;
  }

  @Override public String toString() {
    return "SongStructItem{" + "text='" + text + '\'' + ", shorttext='" + shorttext + '\'' + ",partId='" + partId + '\'' + ", original=" + firstOccurence + ", quantity=" + quantity + ", remarks=" + remarks + "}\n";
  }

  public String getShorttext() {
    return shorttext;
  }

  public void setShorttext(String shorttext) {
    this.shorttext = shorttext;
  }
}
