package org.adonai.plugin.publish.fx.initstep;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.adonai.AdonaiProperties;
import org.adonai.ApplicationEnvironment;
import org.adonai.fx.AbstractController;

public class SynchRemoteDataController extends AbstractController {

  @FXML
  private TextField txtAccessToken;
  @FXML
  private Button btnStart;

  @FXML
  public void initialize () {

    btnStart.setOnAction(event -> {



      if (! txtAccessToken.getText().strip().isEmpty()) {

        String [] tokens = txtAccessToken.getText().split("_");
        String tenant = tokens[tokens.length - 1];
        String token = txtAccessToken.getText().substring(0, txtAccessToken.getText().length() - tenant.length() - 1);

        getApplicationEnvironment().getAdonaiProperties().setDropboxAccessToken(token);
        getApplicationEnvironment().getAdonaiProperties().setCurrentTenant(tenant);

        System.out.println (tenant + " - " + token);
        System.out.println ("");


      }
      AdonaiProperties.dispose(); //to dispose
      new AdonaiProperties(); //and reread again

      getStage().close();

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
