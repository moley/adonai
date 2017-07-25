package org.adonai.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by OleyMa on 03.09.16.
 */
@XmlRootElement
public class Configuration {


  private List<String> screensAdmin = new ArrayList<String>();

  private List<String> screensPresentation = new ArrayList<String>();

  private List<Session> sessions = new ArrayList<Session>();

  private List<SongBook> songBooks = new ArrayList<>();

  private List<String> extensionPaths = new ArrayList<>();

  private String exportPath = "export";

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
    if (extensionPaths.isEmpty()) {
      extensionPaths.add(new File("/Users/OleyMa/Music/iTunes/iTunes Media/Music").getAbsolutePath()); //TODO
    }
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
    return new File (exportPath);
  }

}
