package org.adonai.fx.firstStart;

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.adonai.AdonaiProperties;
import org.adonai.fx.AbstractController;
import org.adonai.fx.Consts;
import org.adonai.online.DropboxAdapter;
import org.controlsfx.control.Notifications;
import org.pf4j.util.Unzip;

public class FirstStartController extends AbstractController {

  @FXML
  private TextField txtAccessToken;
  @FXML
  private Button btnStart;

  @FXML
  public void initialize () {
    btnStart.setOnAction(event -> {

      if (! txtAccessToken.getText().strip().isEmpty()) {
        DropboxAdapter dropboxAdapter = new DropboxAdapter();
        File homPath = Consts.getAdonaiHome();
        File downloadFile = dropboxAdapter.download(homPath.getParentFile(), txtAccessToken.getText().strip());
        Unzip unzip = new Unzip();
        unzip.setDestination(Consts.getAdonaiHome());
        unzip.setSource(downloadFile);
        try {
          unzip.extract();
          downloadFile.delete();
        } catch (IOException e) {
          Notifications.create().title("Download team data").text("Error downloading team data").showError();
        }

      }
      AdonaiProperties.dispose(); //to dispose
      new AdonaiProperties(); //and reread again

      getStage().close();

    });

  }
}
