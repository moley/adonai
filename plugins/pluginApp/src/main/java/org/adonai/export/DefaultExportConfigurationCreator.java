package org.adonai.export;

import org.adonai.model.Configuration;
import org.reflections.Reflections;

import java.util.Set;

public class DefaultExportConfigurationCreator {

  public void createDefaultExportConfigurations (final Configuration configuration) {

    Reflections reflections = new Reflections("org.adonai");
    Set<Class<? extends AbstractDocumentBuilder>> subTypes = reflections.getSubTypesOf(AbstractDocumentBuilder.class);
    for (Class<?extends AbstractDocumentBuilder> next: subTypes) {

      if (configuration.findDefaultExportConfiguration(next) == null)
      try {
        AbstractDocumentBuilder documentBuilder = next.newInstance();
        ExportConfiguration defaultConfiguration = documentBuilder.getDefaultConfiguration();
        defaultConfiguration.setDocumentBuilderClass(next.getName());
        defaultConfiguration.setDefaultConfiguration(true);
        configuration.getExportConfigurations().add(defaultConfiguration);
      } catch (InstantiationException e) {
        throw new IllegalStateException(e);
      } catch (IllegalAccessException e) {
        throw new IllegalStateException(e);
      }

    }
  }
}
