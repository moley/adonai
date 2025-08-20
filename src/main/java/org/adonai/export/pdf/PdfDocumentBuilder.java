package org.adonai.export.pdf;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.adonai.SizeInfo;
import org.adonai.export.AbstractDocumentBuilder;
import org.adonai.export.ExportConfiguration;
import org.adonai.export.ExportToken;
import org.adonai.export.ExportTokenContainer;
import org.adonai.export.ExportTokenNewPage;
import org.adonai.export.ExportTokenType;
import org.adonai.model.SongPartDescriptorStrategy;

public class PdfDocumentBuilder extends AbstractDocumentBuilder {

  private final Rectangle pagesizeA4;
  private final Document document;

  BaseFont bf;
  BaseFont bfBold;

  BaseFont bfItalic;


  public PdfDocumentBuilder () {
    pagesizeA4 = PageSize.A4;
    document = new Document(pagesizeA4);

    try {
      bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED); //centralize fonthandling
      bfBold = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.EMBEDDED); //centralize fonthandling
      bfItalic = BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE,  BaseFont.CP1252, BaseFont.EMBEDDED); //centralize fonthandling
    } catch (IOException | DocumentException e) {
      throw new IllegalStateException(e);
    }

  }


  public SizeInfo getPageSize () {
    return new SizeInfo(Double.valueOf(document.getPageSize().getWidth()), Double.valueOf(document.getPageSize().getHeight()));
  }



  @Override
  public SizeInfo getSize(String text, final ExportTokenType exportTokenType) {
    int fontsize = getFontsize(exportTokenType);
    BaseFont baseFont = getBaseFont(exportTokenType);
    Chunk chunk = new Chunk(text, new Font(baseFont, fontsize));
    float ascent = baseFont.getAscentPoint(text, fontsize);
    float descent = baseFont.getDescentPoint(text, fontsize);
    float width = chunk.getWidthPoint();
    float height = ascent - descent;
    return new SizeInfo(Double.valueOf(width), Double.valueOf(height));
  }

  private BaseFont getBaseFont (final ExportTokenType exportTokenType) {
    if (exportTokenType.isBold())
      return bfBold;
    else if (exportTokenType.isItalic())
      return bfItalic;
    else
      return bf;
  }

  private int getFontsize (final ExportTokenType exportTokenType) {
    if (exportTokenType.equals(ExportTokenType.CHORD))
      return 10;
    else if (exportTokenType.equals(ExportTokenType.TEXT))
      return 12;
    else if (exportTokenType.equals(ExportTokenType.TITLE))
      return 14;
    else if (exportTokenType.equals(ExportTokenType.STRUCTURE))
      return 8;
    else if (exportTokenType.equals(ExportTokenType.REMARKS))
      return 8;
    else
      throw new IllegalStateException("ExportTokenType " + exportTokenType.name() + " not yet supported");
  }

  @Override
  public void write(File outputfile) {
    PdfWriter writer;
    try {
      writer = PdfWriter.getInstance(document, new FileOutputStream(outputfile));

      ExportTokenContainer exportTokenContainer = getExportTokenContainer();

      document.open();
      Double heightFirstLine = null;
      for (ExportToken nextToken: exportTokenContainer.getExportTokenList()) {
        if (nextToken instanceof ExportTokenNewPage) {
          document.newPage();
          heightFirstLine = null;
        }
        else {
          if (heightFirstLine == null)
            heightFirstLine = nextToken.getAreaInfo().getHeight();
          PdfContentByte cb = writer.getDirectContent();


          cb.saveState();
          cb.beginText();
          double realY = pagesizeA4.getHeight() - nextToken.getAreaInfo().getY() - heightFirstLine;
          cb.moveText(nextToken.getAreaInfo().getX().floatValue(), (float) realY);

          BaseFont baseFont = getBaseFont(nextToken.getExportTokenType());
          cb.setFontAndSize(baseFont, getFontsize(nextToken.getExportTokenType()));
          cb.showText(nextToken.getText());
          cb.endText();

        }


      }

      document.close();

    } catch (IOException | DocumentException e) {
      throw new IllegalStateException(e);
    }

  }

  @Override
  public ExportConfiguration getDefaultConfiguration() {
    ExportConfiguration exportConfiguration =  new ExportConfiguration();
    exportConfiguration.initializeValues();
    exportConfiguration.setWithId(true);
    exportConfiguration.setWithTitle(true);
    exportConfiguration.setWithLead(true);
    exportConfiguration.setWithContentPage(true);
    exportConfiguration.setInterLineDistance(5.0);
    exportConfiguration.setChordTextDistance(4.0);
    exportConfiguration.setInterPartDistance(15.0);
    exportConfiguration.setStructureDistance(5.0);
    exportConfiguration.setTitleSongDistance(5.0);
    exportConfiguration.setSongPartDescriptorType(SongPartDescriptorStrategy.LONG);
    exportConfiguration.setLeftBorder(30.0);
    exportConfiguration.setUpperBorder(20.0);
    exportConfiguration.setLowerBorder(20.0);
    exportConfiguration.setOpenPreview(true);
    exportConfiguration.setMinimalChordDistance(5.0);
    exportConfiguration.setRemarksStructureDistance(5.0);
    exportConfiguration.setRemarksRight(true);
    exportConfiguration.setDocumentBuilderClass(getClass().getName());
    exportConfiguration.setName("Styleschema PDF Default");


    return exportConfiguration;
  }

  @Override
  public void openPreview(File file) {
    if (Desktop.isDesktopSupported()) {
      try {
        Desktop.getDesktop().open(file);
      } catch (IOException ex) {
        // no application registered for PDFs
      }
    }

  }
}
