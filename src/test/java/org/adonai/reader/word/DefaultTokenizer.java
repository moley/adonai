package org.adonai.reader.word;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public class DefaultTokenizer implements ITokenizer {

  private Collection<String> lines = new ArrayList<String>();

  public DefaultTokenizer add (final String line) {
    lines.add(line);
    return this;
  }

  public Collection<String> getLines() {
    return lines;
  }

  public void setLines(Collection<String> lines) {
    this.lines = lines;
  }

  @Override
  public Collection<String> getTokens(InputStream inputStream) {
    return lines;
  }
}
