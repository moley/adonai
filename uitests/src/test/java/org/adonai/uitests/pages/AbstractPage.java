package org.adonai.uitests.pages;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.adonai.ApplicationEnvironment;
import org.adonai.model.Configuration;
import org.adonai.model.Model;
import org.adonai.services.ModelService;
import org.adonai.uitests.MyNodeMatchers;
import org.hamcrest.Matcher;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

public class AbstractPage<T> {

  protected ApplicationTest applicationTest;

  public AbstractPage (ApplicationTest applicationTest) {
    this.applicationTest = applicationTest;
  }


  protected Matcher<Node> nodeWithId (final String id) {
    return MyNodeMatchers.withId(id);
  }

  protected Matcher<Node> nodeWithUserData (final String userdata) {
    return MyNodeMatchers.withUserData(userdata);
  }

  public void pressAndRelease (KeyCode keyCode) {
    applicationTest.press(keyCode).release(keyCode);
  }

  public ApplicationTest getApplicationTest () {
    return applicationTest;
  }

  public Configuration getCurrentConfiguration () {
    ApplicationEnvironment applicationEnvironment = new ApplicationEnvironment(null);
    ModelService modelService = new ModelService(applicationEnvironment);
    Model model = modelService.load();
    return model.getCurrentTenantModel().get();
  }

  public void waitUntilWindowsVisible (final int numberOfVisible)  {
    try {
      WaitForAsyncUtils.waitFor(10, TimeUnit.SECONDS, new Callable<Boolean>() {
        @Override
        public Boolean call() throws Exception {
          long numberOfvisiblewindows = Stage.getWindows().stream().filter(Window::isShowing).count();
          System.out.println ("Wait until windows visible (" + numberOfvisiblewindows + "<->" + numberOfVisible);
          return numberOfVisible == numberOfvisiblewindows;
        }
      });
    } catch (TimeoutException e) {
      throw new IllegalStateException(e);
    }
  }






}
