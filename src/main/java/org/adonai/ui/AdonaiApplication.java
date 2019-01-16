package org.adonai.ui;

/**
 * This indirection is a workaround for https://github.com/javafxports/openjdk-jfx/issues/236
 */
public class AdonaiApplication {
  public static void main(String[] args) {
    JavaFxApplication.main(args);
  }

}
