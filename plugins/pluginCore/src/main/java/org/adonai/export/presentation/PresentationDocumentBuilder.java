package org.adonai.export.presentation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.adonai.ApplicationEnvironment;
import org.adonai.SizeInfo;
import org.adonai.export.AbstractDocumentBuilder;
import org.adonai.export.ExportConfiguration;
import org.adonai.export.ExportToken;
import org.adonai.export.ExportTokenType;
import org.adonai.export.NewPageStrategy;
import org.adonai.fx.Mask;
import org.adonai.fx.MaskLoader;
import org.adonai.fx.ScreenManager;
import org.adonai.fx.UiUtils;
import org.adonai.fx.editcontent.EditContentController;
import org.adonai.fx.songdetails.SongDetailsController;
import org.adonai.fx.songstructure.SongStructureController;
import org.adonai.model.SongPartDescriptorStrategy;
import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Extension(ordinal=1)
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
        currentPage.setSongStructItem(nextToken.getSongStructItem());
        pages.add(currentPage);
      }
      else {
        Text text = new Text();
        text.setUserData(nextToken);
        text.setOnMouseClicked(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent event) {
            Text text = (Text) event.getSource();
            LOGGER.info("On mouse clicked on " + text.getText() + "-" + event.getClickCount() + "-" + event.getButton());

            ExportToken exportToken = (ExportToken) text.getUserData();



            LOGGER.info("with type " + exportToken.getExportTokenType());
            if (exportToken != null) {
              if (exportToken.getExportTokenType().equals(ExportTokenType.TITLE)) {
                MaskLoader<SongDetailsController> songdetailsMaskLoader = new MaskLoader<SongDetailsController>();
                Mask<SongDetailsController> songdetailsMask = songdetailsMaskLoader.load("songdetails");
                Stage stage = songdetailsMask.getStage();
                SongDetailsController songDetailsController = songdetailsMask.getController();
                songDetailsController.setStage(stage);
                songDetailsController.setConfiguration(applicationEnvironment.getCurrentConfiguration());
                songDetailsController.setCurrentSong(applicationEnvironment.getCurrentSong());
                songDetailsController.setOnSongContentChange(onSongContentChange);
                songDetailsController.loadData();

                Bounds sceneBounds = text.localToScene(text.getBoundsInLocal());
                stage.setX(sceneBounds.getMinX() + 10);
                stage.setY(sceneBounds.getMinY() + 50);
                stage.setMinWidth(800);
                stage.setMinHeight(600);

                stage.showAndWait();

              }
              else if (exportToken.getExportTokenType().equals(ExportTokenType.STRUCTURE) && exportToken.getSongStructItem() != null) {
                MaskLoader<SongStructureController> songstructureMaskLoader = new MaskLoader<SongStructureController>();
                Mask<SongStructureController> songstructureMask = songstructureMaskLoader.load("songstructure");
                Stage stage = songstructureMask.getStage();
                SongStructureController songStructureController = songstructureMask.getController();
                songStructureController.setSong(getApplicationEnvironment().getCurrentSong());
                songStructureController.loadData();

                Bounds sceneBounds = text.localToScene(text.getBoundsInLocal());
                stage.setX(sceneBounds.getMinX() + 100);
                stage.setY(sceneBounds.getMinY());
                stage.setMinWidth(600);
                stage.setMinHeight(400);
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                  @Override public void handle(WindowEvent event) {
                    onSongContentChange.handle(new ActionEvent());
                  }
                });

                stage.showAndWait();

              }
              else if (exportToken.getSongStructItem() != null) {
                MaskLoader<EditContentController> maskLoader = new MaskLoader<EditContentController>();
                Mask<EditContentController> editContentMask = maskLoader.load("editContent");
                Stage stage = editContentMask.getStage();
                EditContentController editContentController = editContentMask.getController();
                editContentController.setOnSongContentChange(onSongContentChange);
                editContentController.setStage(stage);
                editContentController.setExportToken(exportToken);

                ScreenManager screenManager = new ScreenManager();
                screenManager.layoutOnScreen(stage, 200);

                stage.showAndWait();
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
    exportConfiguration.setName("Styleschema Editor Default");

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
