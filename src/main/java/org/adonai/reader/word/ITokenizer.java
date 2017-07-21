package org.adonai.reader.word;

import java.io.InputStream;
import java.util.Collection;

public interface ITokenizer {

  public Collection<String> getTokens (InputStream inputStream);
}
