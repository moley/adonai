package org.adonai.plugin.publish.fx.action;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.adonai.ApplicationEnvironment;
import org.adonai.api.MainAction;
import org.adonai.fx.Consts;
import org.adonai.online.DropboxAdapter;
import org.controlsfx.control.Notifications;
import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Extension(ordinal=1)
public class DownloadAction implements MainAction {

  private Logger log = LoggerFactory.getLogger(DownloadAction.class);

  @Override public String getIconname() {
    return "fas-cloud-download-alt";
  }

  @Override public String getDisplayName() {
    return "Download data from dropbox";
  }

  @Override public EventHandler<ActionEvent> getEventHandler(ApplicationEnvironment applicationEnvironment) {
    return new EventHandler<ActionEvent>() {
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
    };
  }
}
