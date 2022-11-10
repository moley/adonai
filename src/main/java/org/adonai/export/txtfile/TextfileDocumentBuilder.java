package org.adonai.export.txtfile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.adonai.SizeInfo;
import org.adonai.StringUtils;
import org.adonai.export.AbstractDocumentBuilder;
import org.adonai.export.ExportConfiguration;
import org.adonai.export.ExportToken;
import org.adonai.export.ExportTokenContainer;
import org.adonai.export.ExportTokenType;
import org.adonai.export.NewPageStrategy;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextfileDocumentBuilder extends AbstractDocumentBuilder {

  private static final Logger LOGGER = LoggerFactory.getLogger(TextfileDocumentBuilder.class);



  @Override
  public SizeInfo getSize(String text, ExportTokenType exportTokenType) {
    return new SizeInfo(text.length(), 1);
  }

  @Override
  public void write(File outputfile) {
    try {

      List<StringBuilder> lines = new ArrayList<StringBuilder>();
      ExportTokenContainer exportTokenContainer = getExportTokenContainer();

      int maxWidth = exportTokenContainer.getMaxX().intValue();
      for (int i = 0; i <= exportTokenContainer.getMaxY().intValue(); i++) {
        lines.add(new StringBuilder(StringUtils.spaces(maxWidth + 1)));
      }

      for (ExportToken next : exportTokenContainer.getExportTokenList()) {
        if (LOGGER.isDebugEnabled())
          LOGGER.debug("Adding next export token " + next);
        if (next.getAreaInfo() != null) {
          int x = next.getAreaInfo().getX().intValue();
          int y = next.getAreaInfo().getY().intValue();
          StringBuilder line = lines.get(y);
          for (int i = line.length(); i < x; i++)
            line.append(" ");
          line.insert(x, next.getText());
          lines.set(y, line);
        }

      }

      FileUtils.writeLines(outputfile, lines);

    } catch (FileNotFoundException e) {
      throw new IllegalStateException(e);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }

  }

  @Override
  public ExportConfiguration getDefaultConfiguration() {
    ExportConfiguration exportConfiguration = new ExportConfiguration();
    exportConfiguration.initializeValues();
    exportConfiguration.setWithChords(false);
    exportConfiguration.setNewPageStrategy(NewPageStrategy.NEVER);
    exportConfiguration.setInterPartDistance(Double.valueOf(1));
    exportConfiguration.setInterSongDistance(Double.valueOf(2));
    exportConfiguration.setMinimalChordDistance(Double.valueOf(0));
    exportConfiguration.setRemarksStructureDistance(Double.valueOf(0));

    exportConfiguration.setDocumentBuilderClass(getClass().getName());
    exportConfiguration.setName("Styleschema Textfile Default");
    return exportConfiguration;
  }

  @Override
  public SizeInfo getPageSize() {
    return null;
  }

}
