package org.adonai.uitests;

import javafx.stage.Stage;
import org.adonai.AbstractAdonaiUiTest;
import org.adonai.uitests.pages.SettingsMaskPage;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SettingsTest extends AbstractAdonaiUiTest {

  private SettingsMaskPage settingsMaskPage;

  private final String ID_PDF_DEFAULTSCHEMA = "tpaExportschemaPDFDefault";
  private final String ID_TXT_DEFAULTSCHEMA = "tpaExportschemaTextfileDefault";


  @BeforeClass
  public static void beforeClass () {
    TestUtil.initialize();
  }

  @Override
  public void start(Stage stage) throws Exception {
    super.start(stage);
    settingsMaskPage = new SettingsMaskPage(stage, this);
  }

  @Test
  public void cloneDefault () throws InterruptedException {
    settingsMaskPage.selectConfigurationType(0);
    int initialNumbers = settingsMaskPage.getExportSchemas().size();

    settingsMaskPage.expandExportSchema(ID_PDF_DEFAULTSCHEMA);
    Assert.assertTrue ("Default configurations must not be removable", settingsMaskPage.isBtnRemoveExportConfigurationDisabled());
    Assert.assertFalse ("Default configurations must be clonable", settingsMaskPage.isBtnCloneExportConfigurationDisabled());

    settingsMaskPage.cloneExportConfiguration();

    int numbersAfterAdded = settingsMaskPage.getExportSchemas().size();
    Assert.assertEquals ("Number of schemas invalid", numbersAfterAdded, initialNumbers + 1);

    settingsMaskPage.expandExportSchema("tpaExportschemaPDFDefault(2)");

    Assert.assertFalse ("Default configurations must removable", settingsMaskPage.isBtnRemoveExportConfigurationDisabled());
    Assert.assertFalse ("Default configurations must be clonable", settingsMaskPage.isBtnCloneExportConfigurationDisabled());

    settingsMaskPage.removeExportConfiguration();

    int numbersAfterRemoved = settingsMaskPage.getExportSchemas().size();
    Assert.assertEquals ("Number of schemas invalid", numbersAfterRemoved, initialNumbers);
  }

}
