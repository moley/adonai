package org.adonai;

import org.adonai.model.SongPart;
import org.adonai.model.SongPartType;

public class SongStructItem {
  private SongPartType type;

  /**
   * E.g. the second refrain is shown as 2
   */
  private Integer index = new Integer(1);

  /**
   * the times, this part is shown after itself
   */
  private Integer cumulation = 1;

  /**
   * if the current part was shown before (but not directly before), the content of the current part
   * can be skipped, and only the struct-tag is shown, e.g.
   * ->REFRAIN1 ....
   * VERS ....
   * ->REFRAIN1
   * .........
   */
  private boolean isContentShown = true;

  /**
   * if the current part was shown directory before
   * VERS
   * REFRAIN 1
   * REFRAIN 1
   *
   * gets to VERS
   *         REFRAIN 2x
   *         (REFRAIN not visible)
   */
  private boolean isVisible = true;

  private SongPart part;

  public boolean isContentShown () {
    return isContentShown;
  }

  public void setContentShown (boolean isContentShown) {
    this.isContentShown = isContentShown;
  }

  public Integer getIndex () {
    return index;
  }

  public void setIndex (Integer index) {
    this.index = index;
  }

  /**
   * returns the tag-label to be shown for the current structitem
   * @return label
   */
  public String getLabel () {
    if (! isVisible())
      return null;

    StringBuilder builder = new StringBuilder();
    builder.append(getType().name());
    if (getIndex() != null)
      builder.append(getIndex());

    if (getCumulation() != null && getCumulation().intValue() > 1) {
      builder.append (" " + getCumulation() + "x");
    }

    return builder.toString() + " ";
  }

  public SongPartType getType () {
    return type;
  }

  public void setType (SongPartType type) {
    this.type = type;
  }

  public SongPart getPart () {
    return part;
  }

  public void setPart (SongPart part) {
    this.part = part;
  }

  public void increaseCumulation () {
    cumulation ++;

  }

  public Integer getCumulation () {
    return cumulation;
  }

  public boolean isVisible () {
    return isVisible;
  }

  public void setVisible (boolean isVisible) {
    this.isVisible = isVisible;
  }
}
