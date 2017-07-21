package org.adonai.export.pdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import org.adonai.export.*;
import org.adonai.SizeInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfDocumentBuilder extends AbstractDocumentBuilder {

  private Rectangle pagesizeA4;
  private Document document;

  BaseFont bf;
  BaseFont bfBold;
  BaseFont bfOblique;


  public PdfDocumentBuilder () {
    pagesizeA4 = PageSize.A4;
    document = new Document(pagesizeA4);

    try {
      bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED); //centralize fonthandling
      bfBold = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.EMBEDDED); //centralize fonthandling
      bfOblique = BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, BaseFont.CP1252, BaseFont.EMBEDDED); //centralize fonthandling
    } catch (IOException e) {
      throw new IllegalStateException(e);
    } catch (DocumentException e) {
      throw new IllegalStateException(e);
    }


  }




  @Override
  public SizeInfo getSize(String text, final ExportTokenType exportTokenType) {
    int fontsize = getFontsize(exportTokenType);
    Chunk chunk = new Chunk(text, new Font(bfBold, fontsize));
    float ascent = bfBold.getAscentPoint(text, fontsize);
    float descent = bfBold.getDescentPoint(text, fontsize);
    float width = chunk.getWidthPoint();
    float height = ascent - descent;
    return new SizeInfo(new Double(width), new Double(height));
  }

  private int getFontsize (final ExportTokenType exportTokenType) {
    if (exportTokenType.equals(ExportTokenType.CHORD))
      return 10;
    else if (exportTokenType.equals(ExportTokenType.TEXT))
      return 12;
    else if (exportTokenType.equals(ExportTokenType.TITLE))
      return 18;
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
        if (heightFirstLine == null)
          heightFirstLine = nextToken.getAreaInfo().getHeight();

        if (nextToken instanceof ExportTokenNewPage) {
          document.newPage();
          heightFirstLine = null;
        }
        else {
          PdfContentByte cb = writer.getDirectContent();


          cb.saveState();
          cb.beginText();
          Double realY = pagesizeA4.getHeight() - nextToken.getAreaInfo().getY() - heightFirstLine;
          cb.moveText(nextToken.getAreaInfo().getX().floatValue(), realY.floatValue());
          cb.setFontAndSize(nextToken.getExportTokenType().equals(ExportTokenType.TEXT) ? bf : bfBold, getFontsize(nextToken.getExportTokenType()));
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
    exportConfiguration.setInterLineDistance(new Double(5));
    exportConfiguration.setChordTextDistance(new Double(4));
    exportConfiguration.setInterPartDistance(new Double(15));
    exportConfiguration.setLeftBorder(new Double(40));
    exportConfiguration.setUpperBorder(new Double(40));

    return exportConfiguration;
  }
}
