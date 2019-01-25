package org.adonai.model;

import javafx.beans.property.SimpleStringProperty;
import org.adonai.export.DocumentBuilder;
import org.adonai.export.ExportConfiguration;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by OleyMa on 03.09.16.
 */
@XmlRootElement
public class Configuration {

  private List<User> users = new ArrayList<User>();

  private List<ExportConfiguration> exportConfigurations = new ArrayList<>();

  private List<String> screensAdmin = new ArrayList<String>();

  private List<String> screensPresentation = new ArrayList<String>();

  private List<Session> sessions = new ArrayList<Session>();

  private List<SongBook> songBooks = new ArrayList<>();

  private List<String> extensionPaths = new ArrayList<>(Arrays.asList());

  private SimpleStringProperty exportPath = new SimpleStringProperty("export");

  public List<Session> getSessions() {
    return sessions;
  }

  public void setSessions(List<Session> sessions) {
    this.sessions = sessions;
  }

  public List<SongBook> getSongBooks() {
    return songBooks;
  }

  public void setSongBooks(List<SongBook> songBooks) {
    this.songBooks = songBooks;
  }

  public List<String> getExtensionPaths() {
    return extensionPaths;
  }

  public void setExtensionPaths(List<String> extensionPaths) {
    this.extensionPaths = extensionPaths;
  }

  public List<String> getScreensPresentation() {
    return screensPresentation;
  }

  public void setScreensPresentation(List<String> screensPresentation) {
    this.screensPresentation = screensPresentation;
  }

  public List<String> getScreensAdmin() {
    return screensAdmin;
  }

  public void setScreensAdmin(List<String> screensAdmin) {
    this.screensAdmin = screensAdmin;
  }

  @XmlTransient
  public File getExportPathAsFile () {
    return new File (exportPath.get());
  }

  public String getExportPath () {
    return exportPath.get();
  }

  public void setExportPath (final String newExportPath) {
    this.exportPath.set(newExportPath);
  }

  public SimpleStringProperty exportPathProperty () {
    return exportPath;
  }

  public List<ExportConfiguration> getExportConfigurations() {
    return exportConfigurations;
  }

  public void setExportConfigurations(List<ExportConfiguration> exportConfigurations) {
    this.exportConfigurations = exportConfigurations;
  }

  public ExportConfiguration findDefaultExportConfiguration (final Class<? extends DocumentBuilder> clazz) {
    for (ExportConfiguration next: exportConfigurations) {
      if (next.getDocumentBuilderClass().equals(clazz.getName()) && next.isDefaultConfiguration())
        return next;
    }

    return null;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }
}
