package org.adonai.additionals;

import org.adonai.StringUtils;
import org.adonai.actions.openAdditionals.OpenAdditionalHandler;
import org.adonai.model.AdditionalType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdditionalsActionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(AdditionalsActionHandler.class);

  private Package openAdditionalsPackageName = OpenAdditionalHandler.class.getPackage();


  public OpenAdditionalHandler createOpenAdditionalHandler (final AdditionalType additionalType) {

    String classname = openAdditionalsPackageName.getName() + ".Open" + StringUtils.getFirstUpper(additionalType.name()) + "Action";
    LOGGER.info("Using actionhandler " + classname + " for additional type " + additionalType.name());

    try {
      Class<OpenAdditionalHandler> openAdditionalHandlerClass = (Class<OpenAdditionalHandler>) getClass().getClassLoader().loadClass(classname);
      return openAdditionalHandlerClass.newInstance();
    } catch (ClassNotFoundException e) {
      LOGGER.error(e.getLocalizedMessage(), e);
      throw new IllegalStateException(e);
    } catch (Exception e) {
      LOGGER.error(e.getLocalizedMessage(), e);
      throw new IllegalStateException(e);
    }
  }

}
