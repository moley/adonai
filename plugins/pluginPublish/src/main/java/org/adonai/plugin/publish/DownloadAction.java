package org.adonai.plugin.publish;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import org.adonai.ApplicationEnvironment;
import org.adonai.api.MainAction;
import org.adonai.online.DropboxAdapter;
import org.adonai.ui.Consts;
import org.controlsfx.control.Notifications;
import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Extension(ordinal=1)
public class DownloadAction implements MainAction {

  private Logger log = LoggerFactory.getLogger(DownloadAction.class);

  public Button createButton (final ApplicationEnvironment applicationEnvironment) {
    javafx.scene.control.Button btnFromCloud = new javafx.scene.control.Button();
    btnFromCloud.setTooltip(new Tooltip("Download data from dropbox"));
    btnFromCloud.setGraphic(Consts.createIcon("fa-cloud-download", Consts.ICON_SIZE_TOOLBAR));

    btnFromCloud.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        try {
          DropboxAdapter dropboxAdapter = new DropboxAdapter();
          File homPath = Consts.getAdonaiHome();
          File downloadFile = dropboxAdapter.download(new File (homPath.getParentFile(), ".adonai_backup"));

          Notifications.create().title("Download").text("Downloaded backup to " + downloadFile.getAbsolutePath() + ". Unzip manually to ~/.adonai to overwrite").show();
          log.info("Download finished");
        } catch (Exception e) {
          Notifications.create().title("Download").text("Error downloading content").showError();
          log.error(e.getLocalizedMessage(), e);
        }
      }
    });

    return btnFromCloud;

  }
}
