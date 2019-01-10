package org.adonai.additionals;

import org.adonai.StringUtils;
import org.adonai.actions.openAdditionals.OpenAdditionalHandler;
import org.adonai.model.AdditionalType;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AdditionalsActionHandler {

  private static final Logger LOGGER = Logger.getLogger(AdditionalsActionHandler.class.getName());

  private Package openAdditionalsPackageName = OpenAdditionalHandler.class.getPackage();


  public OpenAdditionalHandler createOpenAdditionalHandler (final AdditionalType additionalType) {

    String classname = openAdditionalsPackageName.getName() + ".Open" + StringUtils.getFirstUpper(additionalType.name()) + "Action";
    LOGGER.info("Using actionhandler " + classname + " for additional type " + additionalType.name());

    try {
      Class<OpenAdditionalHandler> openAdditionalHandlerClass = (Class<OpenAdditionalHandler>) getClass().getClassLoader().loadClass(classname);
      return openAdditionalHandlerClass.newInstance();
    } catch (ClassNotFoundException e) {
      LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
      throw new IllegalStateException(e);
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
      throw new IllegalStateException(e);
    }
  }

}
