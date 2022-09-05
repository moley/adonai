package org.adonai.plugin.publish.fx.action;

import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.adonai.ApplicationEnvironment;
import org.adonai.api.MainAction;
import org.adonai.online.FileStore;
import org.adonai.online.FileStoreState;
import org.controlsfx.control.Notifications;


@Slf4j
public class DownloadAction extends AbstractRemoteAction implements MainAction {

  @Override public String getIconname() {
    return "fas-cloud-download-alt";
  }

  @Override public String getDisplayName() {
    return "Download data from dropbox";
  }

  @Override public EventHandler<ActionEvent> getEventHandler(ApplicationEnvironment applicationEnvironment) {
    return event -> {


      File tenantPath = applicationEnvironment.getModel().getCurrentTenantModel().getTenantPath();


      FileStore fileStore = new FileStore();
      try {
        FileStoreState remoteState = fileStore.getRemoteState(tenantPath);
        int numberOfUploaded = fileStore.download(tenantPath, remoteState, null); //TODO
        if (numberOfUploaded > 0) {
          Notifications.create().title("Download").text("No data for download found").show();
        }
        else Notifications.create().title("Download").text(numberOfUploaded + " files downloaded").show();
      } catch (IOException e) {
        log.error("Error downloading " + tenantPath.getAbsolutePath() + ":" + e.getLocalizedMessage(), e);
      }


    };
  }

  @Override public boolean isVisible() {
    return false;
  }
}
