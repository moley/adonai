package org.adonai;

import org.adonai.services.ModelService;
import org.adonai.services.SessionService;

/**
 * wraps services
 */
public class ServiceRegistry {


  private SessionService sessionService;

  private ApplicationEnvironment applicationEnvironment;

  private ModelService modelService;

  public SessionService getSessionService() {
    if (sessionService == null)
      sessionService = new SessionService();
    return sessionService;
  }

  public ServiceRegistry(final ApplicationEnvironment applicationEnvironment) {
    this.applicationEnvironment = applicationEnvironment;
  }

  public ModelService getModelService () {
    if (modelService == null)
      modelService = new ModelService(applicationEnvironment);

    return modelService;
  }


}
