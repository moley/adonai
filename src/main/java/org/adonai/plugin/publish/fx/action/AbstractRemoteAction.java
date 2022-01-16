package org.adonai.plugin.publish.fx.action;

import org.adonai.ApplicationEnvironment;
import org.adonai.api.MainAction;

public abstract class AbstractRemoteAction implements MainAction {

  public String getCompleteToken (ApplicationEnvironment applicationEnvironment) {
    String currentTenant = applicationEnvironment.getAdonaiProperties().getCurrentTenant();
    String accessToken  = applicationEnvironment.getAdonaiProperties().getDropboxAccessToken();

    return currentTenant + "_" + accessToken;

  }
}
