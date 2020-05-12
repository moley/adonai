package org.adonai.plugin.publish.fx.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.adonai.ApplicationEnvironment;
import org.adonai.api.MainAction;
import org.adonai.model.User;
import org.adonai.online.DropboxAdapter;
import org.adonai.online.MailSender;
import org.adonai.online.ZipManager;
import org.controlsfx.control.Notifications;
import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Extension(ordinal=1)
public class UploadAction implements MainAction {

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

        try {
          Collection<String> ids = new ArrayList<>();
          DropboxAdapter dropboxAdapter = new DropboxAdapter();

          ZipManager zipManager = new ZipManager(applicationEnvironment);
          File zippedBackupFile = zipManager.zip();
          dropboxAdapter.upload(zippedBackupFile, "");

          File exportPath = applicationEnvironment.getCurrentConfiguration().getExportPathAsFile();
          log.info("Using export path " + exportPath.getAbsolutePath());
          File songbookExport = new File(exportPath, "songbook");
          if (songbookExport.exists()) {
            if (songbookExport.listFiles() == null || songbookExport.listFiles().length == 0)
              throw new IllegalStateException("Export path " + songbookExport.getAbsolutePath() + " is empty");
            for (File nextExportFile : songbookExport.listFiles()) {
              log.info("Check file " + nextExportFile.getAbsolutePath());
              if (nextExportFile.getName().endsWith(".pdf")) {
                ids.add(dropboxAdapter.upload(nextExportFile, "export/songbook/"));
              }
            }
          }

          ArrayList<String> users = new ArrayList<>();
          for (User next : applicationEnvironment.getCurrentConfiguration().getUsers()) {
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
          }
        } catch (Exception e) {
          Notifications.create().title("Upload").text("Error uploading content").showError();
          log.error(e.getLocalizedMessage(), e);
        }


      }
    };
  }
}
