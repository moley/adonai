package org.adonai.fx;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelpController extends AbstractController {

  private Logger log = LoggerFactory.getLogger(HelpController.class);


  public void render() {
    String id = getMainController().getCenter().getId();
    if (id != null) {
      InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("help/" + id + ".md");
      if (resourceAsStream != null) {
        Parser parser = Parser.builder().build();
        String asString = new BufferedReader(
            new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8))
            .lines()
            .collect(Collectors.joining("\n"));
        Node document = parser.parse(asString);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String html = renderer.render(document);

      }
    }
  }
}
