package org.adonai.export.pdf;

import org.adonai.export.Exporter;
import org.adonai.export.ExportConfiguration;
import org.adonai.export.ExportEngine;
import org.adonai.export.ExportException;
import org.adonai.model.Song;

import java.io.File;
import java.util.Collection;

public class PdfExporter implements Exporter {

  private PdfDocumentBuilder pdfDocumentBuilder = new PdfDocumentBuilder();
  @Override
  public void export(Collection<Song> songs, File exportFile, ExportConfiguration config) throws ExportException {
    ExportEngine exportEngine = new ExportEngine();
    exportEngine.exportSongs(songs, config, exportFile, pdfDocumentBuilder);
  }

  @Override
  public String getSuffix() {
    return ".pdf";
  }

  public PdfDocumentBuilder getPdfDocumentBuilder () {
    return pdfDocumentBuilder;
  }


}
