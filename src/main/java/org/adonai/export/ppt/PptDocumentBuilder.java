package org.adonai.export.ppt;

import org.adonai.export.ReferenceStrategy;
import org.apache.poi.xslf.usermodel.*;
import org.adonai.SizeInfo;
import org.adonai.export.AbstractDocumentBuilder;
import org.adonai.export.ExportConfiguration;
import org.adonai.export.ExportTokenType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PptDocumentBuilder extends AbstractDocumentBuilder {

  XMLSlideShow ppt = new XMLSlideShow();
  XSLFSlideLayout layout;
  XSLFTextShape currentBody;

  @Override
  public ExportConfiguration getDefaultConfiguration() {
    ExportConfiguration exportConfiguration =  new ExportConfiguration();
    exportConfiguration.setDocumentBuilderClass(getClass().getName());
    exportConfiguration.setReferenceStrategy(ReferenceStrategy.HIDE);
    exportConfiguration.setName("Exportschema Powerpoint Default");

    return exportConfiguration;
  }

  @Override
  public SizeInfo getPageSize() {
    return null;
  }


  @Override
  public SizeInfo getSize (final String text, final ExportTokenType exportTokenType) {
    return null;
  }


  @Override
  public void write(File outputfile) {
    /**
     * XSLFTextParagraph textparagraph = currentBody.addNewTextParagraph();
     textparagraph.setBullet(false);
     XSLFTextRun textRun = textparagraph.addNewTextRun();
     textRun.setFontColor(Color.white);
     textRun.setFontSize(new Double(32));
     textRun.setText(text);
     */

    /**
     * new page:
     * layout = getLayout(ppt, SlideLayout.TITLE_AND_CONTENT);
     layout.getBackground().setFillColor(Color.BLACK);

     XSLFSlide currentSlide = ppt.createSlide(layout);
     XSLFTextShape title = currentSlide.getPlaceholder(0);
     title.clearText();

     currentBody = currentSlide.getPlaceholder(1);
     currentBody.clearText();

     return null; //TODO
     */

    try {
      FileOutputStream out = new FileOutputStream(outputfile);
      ppt.write(out);
      out.close();
    } catch (FileNotFoundException e) {
      throw new IllegalStateException(e);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  public final XSLFSlideLayout getLayout(XMLSlideShow ppt, SlideLayout layout) {
    XSLFSlideLayout selectedLayout = null;

    String listOfLayouts = "";

    for (XSLFSlideMaster master : ppt.getSlideMasters()) {

      //getting the list of the layouts in each slide master
      for (XSLFSlideLayout findlayout : master.getSlideLayouts()) {
        //getting the list of available slides
        if (findlayout.getType().equals(layout))
          selectedLayout = findlayout;

        listOfLayouts += " " + findlayout.getType().name();
      }
    }

    if (selectedLayout == null)
      throw new IllegalStateException("No layout found of type " + layout + "(available " + listOfLayouts.trim() + ")");

    return selectedLayout;
  }


}
