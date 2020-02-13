package org.adonai.export.presentation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.geometry.Bounds;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.adonai.SizeInfo;
import org.adonai.export.AbstractDocumentBuilder;
import org.adonai.export.ExportConfiguration;
import org.adonai.export.ExportToken;
import org.adonai.export.ExportTokenType;
import org.adonai.export.NewPageStrategy;
import org.adonai.model.SongPartDescriptorStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PresentationDocumentBuilder extends AbstractDocumentBuilder {

  private static final Logger LOGGER = LoggerFactory.getLogger(PresentationDocumentBuilder.class);


  private SizeInfo sizeInfo;
  private HashMap<ExportTokenType, Font> savedFonts = new HashMap<ExportTokenType, Font>();

  private List<Page> pages = new ArrayList<>();




  @Override public SizeInfo getSize(String text, ExportTokenType exportTokenType) {
    Text txtAsText = new Text(text);

    Font font = getBaseFont(exportTokenType);
    txtAsText.setFont(font);

    Bounds layoutBounds = txtAsText.getLayoutBounds();
    return new SizeInfo(layoutBounds.getWidth(), layoutBounds.getHeight());
  }

  private Font getBaseFont (final ExportTokenType exportTokenType) {
    Font fromSaved = savedFonts.get(exportTokenType);
    if (fromSaved != null)
      return fromSaved;

    if (exportTokenType.isBold()) {
      savedFonts.put(exportTokenType, Font.font("arial", FontWeight.BOLD, getFontsize(exportTokenType)));
    }
    else {
      savedFonts.put(exportTokenType, Font.font("arial", FontWeight.NORMAL, getFontsize(exportTokenType)));
    }

    return savedFonts.get(exportTokenType);
  }

  private int getFontsize (final ExportTokenType exportTokenType) {
    if (exportTokenType.equals(ExportTokenType.CHORD))
      return 16;
    else if (exportTokenType.equals(ExportTokenType.TEXT))
      return 18;
    else if (exportTokenType.equals(ExportTokenType.TITLE))
      return 20;
    else if (exportTokenType.equals(ExportTokenType.STRUCTURE))
      return 12;
    else
      throw new IllegalStateException("ExportTokenType " + exportTokenType.name() + " not yet supported");
  }

  private Page createPane () {
    Page currentPane = new Page();
    currentPane.getPane().setBackground(new Background(new BackgroundFill(Color.rgb(230, 230, 230), CornerRadii.EMPTY, null)));
    currentPane.getPane().setVisible(false);
    return currentPane;
  }

  @Override public void write(File outputfile) {

    Page currentPage = createPane();
    pages.add(currentPage);

    for (ExportToken nextToken: getExportTokenContainer().getExportTokenList()) {
      if (nextToken.getExportTokenType().equals(ExportTokenType.NEW_PAGE)) {
        currentPage = createPane();
        currentPage.setSong(nextToken.getSong());
        pages.add(currentPage);
      }
      else {
        Text text = new Text();
        text.setFont(getBaseFont(nextToken.getExportTokenType()));
        text.setText(nextToken.getText());
        text.relocate(nextToken.getAreaInfo().getX(), nextToken.getAreaInfo().getY());
        currentPage.getPane().getChildren().add(text);
        currentPage.setSong(nextToken.getSong());
      }

    }

    //noimpl

  }

  @Override public ExportConfiguration getDefaultConfiguration() {
    ExportConfiguration exportConfiguration =  new ExportConfiguration();
    exportConfiguration.initializeValues();
    exportConfiguration.setWithTitle(true);
    exportConfiguration.setWithContentPage(false);
    exportConfiguration.setWithChords(Boolean.TRUE);
    exportConfiguration.setNewPageStrategy(NewPageStrategy.PER_SONG);
    exportConfiguration.setTitleSongDistance(new Double(5));
    exportConfiguration.setInterLineDistance(new Double(5));
    exportConfiguration.setChordTextDistance(new Double(4));
    exportConfiguration.setInterPartDistance(new Double(10));
    exportConfiguration.setStructureDistance(new Double(15));
    exportConfiguration.setSongPartDescriptorType(SongPartDescriptorStrategy.LONG);
    exportConfiguration.setLeftBorder(new Double(5));
    exportConfiguration.setUpperBorder(new Double(5));
    exportConfiguration.setLowerBorder(new Double(5));
    exportConfiguration.setOpenPreview(false);
    exportConfiguration.setMinimalChordDistance(new Double(5));
    exportConfiguration.setDocumentBuilderClass(getClass().getName());
    exportConfiguration.setName("Exportschema Preview Default");

    return exportConfiguration;
  }

  @Override public SizeInfo getPageSize() {
    return sizeInfo;
  }

  public void setSizeInfo(SizeInfo sizeInfo) {
    this.sizeInfo = sizeInfo;
  }

  public List<Page> getPages() {
    return pages;
  }


}
