package org.adonai.plugin.publish.fx.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.adonai.ApplicationEnvironment;
import org.adonai.api.MainAction;
import org.adonai.online.FileStore;
import org.adonai.online.FileStoreState;
import org.controlsfx.control.Notifications;
import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Extension(ordinal=1)
public class UploadAction extends AbstractRemoteAction implements MainAction {

  private Logger log = LoggerFactory.getLogger(UploadAction.class);


  @Override public String getIconname() {
    return "fas-cloud-upload-alt";
  }

  @Override public String getDisplayName() {
    return "Upload data to dropbox";
  }

  @Override public EventHandler<ActionEvent> getEventHandler(ApplicationEnvironment applicationEnvironment) {
    return new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {

        File tenantPath = applicationEnvironment.getModel().getCurrentTenantModel().getTenantPath();

        try {
          Collection<String> ids = new ArrayList<>();

          FileStore fileStore = new FileStore();
          FileStoreState remoteState = fileStore.getRemoteState(tenantPath);
          int numberOfUploaded = fileStore.upload(remoteState, false);
          if (numberOfUploaded > 0) {
            Notifications.create().title("Upload").text("No data for upload found").show();
          }
          else Notifications.create().title("Upload").text(numberOfUploaded + " files uploaded").show();


          ArrayList<String> users = new ArrayList<>();
          /**for (User next : applicationEnvironment.getCurrentConfiguration().getUsers()) {
            if (next.getMail() != null && !next.getMail().trim().isEmpty())
              users.add(next.getMail());
          }

          if (ids.isEmpty()) {
            Notifications.create().title("Upload").text("Upload finished, no mails sent because no export data available").show();
            log.info("Upload finished, no mail sent because no export data available");
          }
          else {

            MailSender mailSender = new MailSender();
            mailSender.sendExportMail(users, ids);

            Notifications.create().title("Upload").text("Upload finished, mail sent to " + users + " with links " + ids)
                .show();
            log.info("Upload finished, mail sent to " + users + " with links " + ids);
          }**/
        } catch (Exception e) {
          Notifications.create().title("Upload").text("Error uploading content").showError();
          log.error(e.getLocalizedMessage(), e);
        }


      }
    };
  }
}
