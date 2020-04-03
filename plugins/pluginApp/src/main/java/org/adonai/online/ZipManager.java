package org.adonai.online;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import org.adonai.ApplicationEnvironment;
import org.adonai.model.Model;
import org.adonai.model.TenantModel;
import org.adonai.services.ModelService;
import org.adonai.ui.Consts;
import org.apache.commons.io.FileUtils;

public class ZipManager {

  private ApplicationEnvironment applicationEnvironment;

  public ZipManager (final ApplicationEnvironment applicationEnvironment) {
    this.applicationEnvironment = applicationEnvironment;
  }

  public File getZipFile () {
    return new File (Consts.getAdonaiHome().getParent(), "adonai.zip");
  }

  public String getRelativeFileName (final File homePath, final File file) {
    String local = file.getAbsolutePath().substring(homePath.getAbsolutePath().length() + 1);
    return local;
  }

  public File zip () {
    Collection<File> files = new ArrayList<File>();

    File adonaiHomePath = Consts.getAdonaiHome();

    ModelService modelService = new ModelService(applicationEnvironment);
    Model model = modelService.load();
    for (TenantModel tenantModel : model.getTenantModels()) {
      files.add(tenantModel.getConfigFile());
    }

    File zipfile = getZipFile();

    try {
      ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream(getZipFile().toPath())));
      for (File next: files) {
        zos.putNextEntry(new ZipEntry(getRelativeFileName(adonaiHomePath, next)));
        zos.write(FileUtils.readFileToByteArray(next));
        zos.closeEntry();
      }

      zos.close();

    } catch (IOException e) {
      throw new IllegalStateException(e);
    }

    return zipfile;

  }

  public void unzip (final String pathname) {

    File adonaiHomePath = Consts.getAdonaiHome();

    Path outputPath = new File (adonaiHomePath.getParentFile(), pathname).toPath();
    File outputDir = outputPath.toFile();

    try (var zf = new ZipFile(getZipFile())) {

      // Delete if exists, then create a fresh empty directory to put the zip archive contents
      if (outputDir.exists())
        FileUtils.deleteDirectory(outputDir);
      outputDir.mkdirs();

      Enumeration<? extends ZipEntry> zipEntries = zf.entries();
      zipEntries.asIterator().forEachRemaining(entry -> {
        try {
          if (entry.isDirectory()) {
            Path dirToCreate = outputPath.resolve(entry.getName());
            Files.createDirectories(dirToCreate);
          } else {
            Path fileToCreate = outputPath.resolve(entry.getName());
            Files.createDirectories(fileToCreate.getParent());
            Files.copy(zf.getInputStream(entry), fileToCreate);
          }
        } catch(IOException ei) {
          throw new IllegalStateException(ei);
        }
      });
    } catch(IOException e) {
      throw new IllegalStateException(e);
    }

  }


}
