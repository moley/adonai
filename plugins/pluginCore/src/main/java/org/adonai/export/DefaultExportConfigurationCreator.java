package org.adonai.export;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.adonai.ApplicationEnvironment;
import org.adonai.api.ExportBuilder;
import org.adonai.model.Configuration;

public class DefaultExportConfigurationCreator {

  public void createDefaultExportConfigurations (final ApplicationEnvironment applicationEnvironment,
                                                 final Configuration configuration) {

    List<ExportBuilder> exportBuilderList = applicationEnvironment.getExtensions(ExportBuilder.class);

    if (exportBuilderList.isEmpty())
      throw new IllegalStateException("No export builders registered");

    for (ExportBuilder next: exportBuilderList) {
      AbstractDocumentBuilder abstractDocumentBuilder = (AbstractDocumentBuilder) next;

      if (configuration.findDefaultExportConfiguration(abstractDocumentBuilder.getClass()) == null)
      try {
        AbstractDocumentBuilder documentBuilder = abstractDocumentBuilder.getClass().getDeclaredConstructor().newInstance();
        ExportConfiguration defaultConfiguration = documentBuilder.getDefaultConfiguration();
        defaultConfiguration.setDocumentBuilderClass(abstractDocumentBuilder.getClass().getName());
        defaultConfiguration.setDefaultConfiguration(true);
        configuration.getExportConfigurations().add(defaultConfiguration);
      } catch (InstantiationException e) {
        throw new IllegalStateException(e);
      } catch (IllegalAccessException e) {
        throw new IllegalStateException(e);
      } catch (NoSuchMethodException e) {
        throw new IllegalStateException(e);
      } catch (InvocationTargetException e) {
        throw new IllegalStateException(e);
      }

    }
  }
}
