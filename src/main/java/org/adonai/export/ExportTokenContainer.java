package org.adonai.export;

import java.util.ArrayList;
import java.util.List;

public class ExportTokenContainer {

  private List<ExportToken> exportTokenList = new ArrayList<ExportToken>();

  public void addToken (final ExportToken token) {
    exportTokenList.add(token);
  }

  public List<ExportToken> getExportTokenList () {
    return exportTokenList;
  }

  public boolean hasTokens () {
    return ! exportTokenList.isEmpty();
  }

  public Double getMaxY () {
    Double max = new Double(0);
    for (ExportToken next: exportTokenList) {
      if (next.getAreaInfo() != null)
        max = Double.max(max, next.getAreaInfo().getY() + next.getAreaInfo().getWidth());
    }
    return max;

  }

  public Double getMaxX () {
    Double max = new Double(0);
    for (ExportToken next: exportTokenList) {
      if (next.getAreaInfo() != null)
        max = Double.max(max, next.getAreaInfo().getX());
    }
    return max;

  }
}
