package org.adonai.plugin.publish.fx.initstep;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.slf4j.Slf4j;
import org.adonai.ApplicationEnvironment;
import org.adonai.api.InitStep;
import org.adonai.fx.Mask;
import org.adonai.fx.MaskLoader;
import org.adonai.fx.ScreenManager;
import org.adonai.online.FileStore;


@Slf4j
public class SynchRemoteDataInitStep implements InitStep {

  private final MaskLoader<SynchRemoteDataController> maskLoader = new MaskLoader<>();

  public void execute (ApplicationEnvironment applicationEnvironment) {
    Stage initStepStage = new Stage();
    Mask<SynchRemoteDataController> mask = maskLoader.loadWithStage("synchRemoteData", getClass().getClassLoader());
    SynchRemoteDataController controller = mask.getController();
    controller.setStage(initStepStage);
    controller.setApplicationEnvironment(applicationEnvironment);
    initStepStage.setScene(mask.getScene());
    ScreenManager screenManager = new ScreenManager();
    initStepStage.initStyle(StageStyle.UNDECORATED);
    screenManager.layoutOnScreen(initStepStage, 200, screenManager.getPrimary());
    initStepStage.toFront();
    initStepStage.showAndWait();
  }

  @Override public boolean isExecuted(ApplicationEnvironment applicationEnvironment) {
    FileStore fileStore = new FileStore();
    return false;
//    if (true) { //TODO
//      try {
//        File tenantPath = applicationEnvironment.getServices().getModelService().getTenantPath(applicationEnvironment.getCurrentTenant());
//        FileStoreState remoteState = fileStore.getRemoteState(tenantPath);
//        log.error("The following remote items after newer: " + remoteState.getItemsRemoteNewer());
//        return !remoteState.getItemsRemoteNewer().isEmpty();
//
//      } catch (IOException e) {
//        log.error("Error when reading remote state: " + e.getLocalizedMessage(), e);
//        return true;
//      }
//    }
//    else
//      return false;

  }
}
