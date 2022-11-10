package org.adonai.export.presentation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.adonai.ApplicationEnvironment;
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

  private EventHandler<ActionEvent> onSongContentChange;

  private ApplicationEnvironment applicationEnvironment;


  @Override public SizeInfo getSize(String text, ExportTokenType exportTokenType) {
    Text txtAsText = new Text(text);

    Font font = getBaseFont(exportTokenType);
    txtAsText.setFont(font);

    Bounds layoutBounds = txtAsText.getLayoutBounds();
    return new SizeInfo(layoutBounds.getWidth(), layoutBounds.getHeight());
  }

  private Font getBaseFont(final ExportTokenType exportTokenType) {
    Font fromSaved = savedFonts.get(exportTokenType);
    if (fromSaved != null)
      return fromSaved;

    if (exportTokenType.isItalic()) {
      savedFonts.put(exportTokenType, Font.font("arial", FontPosture.ITALIC, getFontsize(exportTokenType)));
    }
    else if (exportTokenType.isBold()) {
      savedFonts.put(exportTokenType, Font.font("arial", FontWeight.BOLD, getFontsize(exportTokenType)));
    } else {
      savedFonts.put(exportTokenType, Font.font("arial", FontWeight.NORMAL, getFontsize(exportTokenType)));
    }

    return savedFonts.get(exportTokenType);
  }

  private int getFontsize(final ExportTokenType exportTokenType) {
    if (exportTokenType.equals(ExportTokenType.CHORD))
      return 16;
    else if (exportTokenType.equals(ExportTokenType.TEXT))
      return 18;
    else if (exportTokenType.equals(ExportTokenType.TITLE))
      return 20;
    else if (exportTokenType.equals(ExportTokenType.STRUCTURE))
      return 12;
    else if (exportTokenType.equals(ExportTokenType.REMARKS))
      return 12;
    else
      throw new IllegalStateException("ExportTokenType " + exportTokenType.name() + " not yet supported");
  }

  private Page createPane() {
    Page currentPane = new Page();
    currentPane.getPane()
        .setBackground(new Background(new BackgroundFill(Color.rgb(230, 230, 230), CornerRadii.EMPTY, null)));
    currentPane.getPane().setVisible(false);
    return currentPane;
  }

  @Override public void write(File outputfile) {

    Page currentPage = createPane();
    pages.add(currentPage);

    for (ExportToken nextToken : getExportTokenContainer().getExportTokenList()) {
      if (nextToken.getExportTokenType().equals(ExportTokenType.NEW_PAGE)) {
        currentPage = createPane();
        currentPage.setSong(nextToken.getSong());
        currentPage.setSongStructItem(nextToken.getSongStructItem());
        pages.add(currentPage);
      } else {
        Text text = new Text();
        text.setUserData(nextToken);
        text.setOnMouseClicked(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent event) {
            Text text = (Text) event.getSource();
            event.consume();
            if (event.getClickCount() > 1)
              return;

            LOGGER.info("On mouse clicked on " + text.getText() + "-" + event.getClickCount() + "-" + event.getButton());

            ExportToken exportToken = (ExportToken) text.getUserData();

            LOGGER.info("with type " + exportToken.getExportTokenType());
            if (exportToken != null) {
              if (exportToken.getExportTokenType().equals(ExportTokenType.TITLE)) {
                /**MaskLoader<SongDetailsController> songdetailsMaskLoader = new MaskLoader<SongDetailsController>();
                Mask<SongDetailsController> songdetailsMask = songdetailsMaskLoader.load("songdetails");
                Stage stage = songdetailsMask.getStage();
                ScreenManager screenManager = new ScreenManager();
                screenManager.layoutOnScreen(stage, 300, getApplicationEnvironment().getAdminScreen());

                SongDetailsController songDetailsController = songdetailsMask.getController();
                songDetailsController.setStage(stage);
                songDetailsController.setApplicationEnvironment(getApplicationEnvironment());
                songDetailsController.setConfiguration(applicationEnvironment.getCurrentConfiguration());
                songDetailsController.setCurrentSong(applicationEnvironment.getCurrentSong());
                songDetailsController.loadData();
                stage.setOnCloseRequest(event12 -> {
                  songDetailsController.save();
                  onSongContentChange.handle(new ActionEvent());
                });

                stage.showAndWait();**/

              } else if (exportToken.getExportTokenType().equals(ExportTokenType.STRUCTURE) && exportToken
                  .getSongStructItem() != null || (exportToken.getSongStructItem() != null)) {
                /**TODOMaskLoader<EditContentController> maskLoader = new MaskLoader<EditContentController>();
                Mask<EditContentController> editContentMask = maskLoader.load("editContent");
                Stage stage = editContentMask.getStage();

                EditContentController editContentController = editContentMask.getController();
                editContentController.setStage(stage);
                editContentController.setExportToken(exportToken);

                stage.setOnCloseRequest(event1 -> {
                  editContentController.serializeCurrentSongPart();
                  onSongContentChange.handle(new ActionEvent());
                });

                Bounds bounds = UiUtils.getBounds(applicationEnvironment.getSongEditor());
                stage.setX(bounds.getMinX());
                stage.setY(bounds.getMinY());
                stage.setWidth(bounds.getWidth());
                stage.setHeight(bounds.getHeight());
                stage.showAndWait();**/
              }
            }
          }
        });
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
    ExportConfiguration exportConfiguration = new ExportConfiguration();
    exportConfiguration.initializeValues();
    exportConfiguration.setWithTitle(true);
    exportConfiguration.setWithContentPage(false);
    exportConfiguration.setWithChords(Boolean.TRUE);
    exportConfiguration.setNewPageStrategy(NewPageStrategy.PER_SONG);
    exportConfiguration.setTitleSongDistance(Double.valueOf(5));
    exportConfiguration.setInterLineDistance(Double.valueOf(5));
    exportConfiguration.setChordTextDistance(Double.valueOf(4));
    exportConfiguration.setInterPartDistance(Double.valueOf(10));
    exportConfiguration.setStructureDistance(Double.valueOf(15));
    exportConfiguration.setSongPartDescriptorType(SongPartDescriptorStrategy.LONG);
    exportConfiguration.setLeftBorder(Double.valueOf(5));
    exportConfiguration.setUpperBorder(Double.valueOf(5));
    exportConfiguration.setLowerBorder(Double.valueOf(5));
    exportConfiguration.setOpenPreview(false);
    exportConfiguration.setMinimalChordDistance(Double.valueOf(5));
    exportConfiguration.setRemarksStructureDistance(Double.valueOf(5));
    exportConfiguration.setDocumentBuilderClass(getClass().getName());
    exportConfiguration.setName("Styleschema Editor Default");
    exportConfiguration.setWithRemarks(Boolean.TRUE);

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

  public EventHandler<ActionEvent> getOnSongContentChange() {
    return onSongContentChange;
  }

  public void setOnSongContentChange(EventHandler<ActionEvent> onSongContentChange) {
    this.onSongContentChange = onSongContentChange;
  }

  public ApplicationEnvironment getApplicationEnvironment() {
    return applicationEnvironment;
  }

  public void setApplicationEnvironment(ApplicationEnvironment applicationEnvironment) {
    this.applicationEnvironment = applicationEnvironment;
  }
}
