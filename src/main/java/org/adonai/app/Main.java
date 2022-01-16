package org.adonai.app;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import lombok.extern.slf4j.Slf4j;
import org.adonai.fx.FxApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class Main {


  public static void main(String[] args) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    log.info("Starting application in path " + new File("").getAbsolutePath());
    log.info("Java-Version: " + System.getProperty("java.version"));

    FxApplication.main(new String [0]);

  }
}
