package org.adonai.plugin.publish.fx.initstep;

import java.io.File;
import java.io.IOException;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;
import org.adonai.AdonaiProperties;
import org.adonai.ApplicationEnvironment;
import org.adonai.fx.AbstractController;
import org.adonai.fx.Progress;
import org.adonai.online.FileStore;
import org.adonai.online.FileStoreState;

@Slf4j
public class SynchRemoteDataController extends AbstractController {


  @FXML
  private TextField txtAccessToken;
  @FXML
  private Button btnStart;

  @FXML
  private Button btnSynchronize;

  @FXML
  private ProgressBar pgbProgress;

  @FXML
  private Label lblProgress;

  @FXML
  public void initialize () {

    pgbProgress.setProgress(0);

    btnStart.setOnAction(event -> {
      AdonaiProperties.dispose(); //to dispose
      new AdonaiProperties(); //and reread again

      getStage().close();

    });

    btnSynchronize.setOnAction(event -> {

      if (! txtAccessToken.getText().strip().isEmpty()) {

        ApplicationEnvironment applicationEnvironment = getApplicationEnvironment();
        String [] tokens = txtAccessToken.getText().split("_");
        String tenant = tokens[tokens.length - 1];
        String token = txtAccessToken.getText().substring(0, txtAccessToken.getText().length() - tenant.length() - 1);

        applicationEnvironment.getAdonaiProperties().setDropboxAccessToken(token);
        applicationEnvironment.getAdonaiProperties().setCurrentTenant(tenant);
        File tenantPath = applicationEnvironment.getServices().getModelService().getTenantPath(applicationEnvironment.getCurrentTenant());
        pgbProgress.setVisible(true);
        lblProgress.setVisible(true);

        Task<Integer> downloadTask = new Task<>() {

          @Override protected Integer call() throws Exception {
            FileStore fileStore = new FileStore();
            try {
              FileStoreState remoteState = fileStore.getRemoteState(tenantPath);
              int downloadedFiles = fileStore.download(tenantPath, remoteState, new Progress() {
                @Override public void show(int totalNumer, int workedNumber, String text) {
                  updateMessage(text);
                  updateProgress(workedNumber, totalNumer);
                }
              });
              log.info("Downloaded " + downloadedFiles);
            } catch (IOException e) {
              log.error("Error synchronising remote state: " + e.getLocalizedMessage(), e);
            }
            log.info("Executor finished");
            return null;
          }
        };

        pgbProgress.progressProperty().unbind();
        lblProgress.textProperty().unbind();

        pgbProgress.progressProperty().bind(downloadTask.progressProperty());
        lblProgress.textProperty().bind(downloadTask.messageProperty());

        downloadTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler<WorkerStateEvent>() {
          @Override public void handle(WorkerStateEvent event) {
            AdonaiProperties.dispose(); //to dispose
            new AdonaiProperties(); //and reread again

            getStage().close();
          }
        });

        new Thread(downloadTask).start();

      }
    });

  }

  @Override public void setApplicationEnvironment(ApplicationEnvironment applicationEnvironment) {
    super.setApplicationEnvironment(applicationEnvironment);

    String accessToken = applicationEnvironment.getAdonaiProperties().getDropboxAccessToken();
    String tenant = applicationEnvironment.getAdonaiProperties().getCurrentTenant();
    if (accessToken != null && ! accessToken.isEmpty())
      txtAccessToken.setText(accessToken + "_" + tenant);


  }
}
