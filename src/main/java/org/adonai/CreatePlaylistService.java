package org.adonai;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.adonai.model.Additional;
import org.adonai.model.AdditionalType;
import org.adonai.model.Song;
import org.apache.commons.io.FileUtils;

public class CreatePlaylistService {

  public void create (final File file, final Collection<Song> songs) throws IOException {

    List<String> lines = new ArrayList<String>();
    lines.add("#EXTM3U");
    for (Song next: songs) {

      for (Additional nextAdditional: next.getAdditionals()) {
        if (nextAdditional.getAdditionalType().equals(AdditionalType.AUDIO)) {
          String length = "100";
          lines.add("#EXTINF:" + length + "," + next.getName());
          lines.add(nextAdditional.getLink());
        }
      }
    }

    FileUtils.write(file,  String.join("\r", lines), Charset.forName("UTF-8"));


  }


}
