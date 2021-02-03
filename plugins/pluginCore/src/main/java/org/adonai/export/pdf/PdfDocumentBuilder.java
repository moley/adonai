package org.adonai.export.pdf;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import org.adonai.export.*;
import org.adonai.SizeInfo;
import org.adonai.model.SongPartDescriptorStrategy;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.pf4j.Extension;

@Extension(ordinal=1)
public class PdfDocumentBuilder extends AbstractDocumentBuilder {

  private Rectangle pagesizeA4;
  private Document document;

  BaseFont bf;
  BaseFont bfBold;


  public PdfDocumentBuilder () {
    pagesizeA4 = PageSize.A4;
    document = new Document(pagesizeA4);

    try {
      bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED); //centralize fonthandling
      bfBold = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.EMBEDDED); //centralize fonthandling
    } catch (IOException e) {
      throw new IllegalStateException(e);
    } catch (DocumentException e) {
      throw new IllegalStateException(e);
    }


  }


  public SizeInfo getPageSize () {
    return new SizeInfo(new Double (document.getPageSize().getWidth()), new Double(document.getPageSize().getHeight()));
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
    return new SizeInfo(new Double(width), new Double(height));
  }

  private BaseFont getBaseFont (final ExportTokenType exportTokenType) {
    if (exportTokenType.isBold())
      return bfBold;
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
          Double realY = pagesizeA4.getHeight() - nextToken.getAreaInfo().getY() - heightFirstLine;
          cb.moveText(nextToken.getAreaInfo().getX().floatValue(), realY.floatValue());
          cb.setFontAndSize(nextToken.getExportTokenType().isBold() ? bfBold : bf, getFontsize(nextToken.getExportTokenType()));
          cb.showText(nextToken.getText());
          cb.endText();

        }


      }

      document.close();

    } catch (IOException e) {
      throw new IllegalStateException(e);
    } catch (DocumentException e) {
      throw new IllegalStateException(e);
    }

  }

  @Override
  public ExportConfiguration getDefaultConfiguration() {
    ExportConfiguration exportConfiguration =  new ExportConfiguration();
    exportConfiguration.initializeValues();
    exportConfiguration.setWithId(true);
    exportConfiguration.setWithTitle(true);
    exportConfiguration.setWithContentPage(true);
    exportConfiguration.setTitleSongDistance(new Double(5));
    exportConfiguration.setInterLineDistance(new Double(5));
    exportConfiguration.setChordTextDistance(new Double(4));
    exportConfiguration.setInterPartDistance(new Double(15));
    exportConfiguration.setStructureDistance(new Double(5));
    exportConfiguration.setSongPartDescriptorType(SongPartDescriptorStrategy.LONG);
    exportConfiguration.setLeftBorder(new Double(30));
    exportConfiguration.setUpperBorder(new Double(20));
    exportConfiguration.setLowerBorder(new Double(20));
    exportConfiguration.setOpenPreview(true);
    exportConfiguration.setMinimalChordDistance(new Double(5));
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
