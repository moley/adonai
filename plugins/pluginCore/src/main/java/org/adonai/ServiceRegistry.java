package org.adonai;

import org.adonai.services.SessionService;

/**
 * wraps services
 */
public class ServiceRegistry {


  private SessionService sessionService = new SessionService();

  public SessionService getSessionService() {
    return sessionService;
  }


}
