package org.adonai;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.adonai.model.*;
import org.adonai.ui.Consts;
import org.adonai.ui.editor.SongEditor;

import java.io.IOException;

/**
 * Created by OleyMa on 22.11.16.
 */
public class SongEditorStarter extends Application {

    public static void main(String[] args) {
      launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {


      Line line1 = new Line();
      line1.getLineParts().add(new LinePart("Strength will rise as we ", "G"));
      line1.getLineParts().add(new LinePart("wait upon the ", "Gsus"));
      line1.getLineParts().add(new LinePart("Lord ", "G"));

      Line line2 = new Line();
      line2.getLineParts().add(new LinePart("we will "));
      line2.getLineParts().add(new LinePart("wait upon the ", "Gsus"));
      line2.getLineParts().add(new LinePart("Lord, we will ", "G"));
      line2.getLineParts().add(new LinePart("wait upon the ", "Gsus"));
      line2.getLineParts().add(new LinePart("Lord ", "G"));

      Line line3 = new Line();
      line3.getLineParts().add(new LinePart("Our ", "G/H"));
      line3.getLineParts().add(new LinePart("God, ", "C"));
      line3.getLineParts().add(new LinePart("you ", "G/H"));
      line3.getLineParts().add(new LinePart("reign ", "G"));
      line3.getLineParts().add(new LinePart("for ", "D"));
      line3.getLineParts().add(new LinePart("e ", "e"));
      line3.getLineParts().add(new LinePart("ver", "D"));

      Line line4 = new Line();
      line4.getLineParts().add(new LinePart("Our ", "G/H"));
      line4.getLineParts().add(new LinePart("hope, ", "C"));
      line4.getLineParts().add(new LinePart("our ", "G/H"));
      line4.getLineParts().add(new LinePart("strong ", "G"));
      line4.getLineParts().add(new LinePart("De", "D"));
      line4.getLineParts().add(new LinePart("liv'", "e"));
      line4.getLineParts().add(new LinePart("rer", "D"));


      SongPart part = new SongPart();
      part.setSongPartType(SongPartType.VERS);
      part.getLines().add(line1);
      part.getLines().add(line2);

      SongPart part2 = new SongPart();
      part2.setSongPartType(SongPartType.ZWISCHENSPIEL);
      part2.getLines().add(line3);
      part2.getLines().add(line4);


      Song song = new Song();
      song.getSongParts().add(part);
      song.getSongParts().add(part2);

      SongEditor songEditor = new SongEditor(song);

      Parent editor = songEditor.getPanel();



      Scene scene = new Scene(editor, Consts.DEFAULT_WIDTH, Consts.DEFAULT_HEIGHT, false);

      scene.getStylesheets().add("/adonai.css");

      primaryStage.setScene(scene);

      primaryStage.show();


    }

}
