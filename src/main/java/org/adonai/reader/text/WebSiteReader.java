package org.adonai.reader.text;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.adonai.model.*;
import org.adonai.ui.editor.SongRepairer;
import org.adonai.Chord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

public class WebSiteReader {

  public Song readFromUrl (String link) {
    Collection<String> lines = new ArrayList<String>();
    try {
    URL url = new URL(link);
    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
      String inputLine;
      while ((inputLine = in.readLine()) != null)
        lines.add(inputLine);
      in.close();
      return read(lines);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }



  }

  public Song read(Collection<String> lines) {

    Song song = new Song();


    //chord-pro-disp
    //   chord-pro-line
    //      chord-pro-segment
    //          chord-pro-note
    //          chord-pro-lyric
    //   chord-pro-br

    String html = String.join("\n", lines);
    Document document = Jsoup.parse(html);


    newSongPart(song);
    Elements elementsByClass = document.getElementsByClass("chord-pro-disp");
    if (elementsByClass.size() != 1)
      throw new IllegalStateException("Not one lyrics tag found, but " + elementsByClass.size());

    Element rootTag = elementsByClass.get(0);
    for (Element next: rootTag.children()) {

      if (next.attr("class").equals("chord-pro-br")) {
        System.out.println("NEW PART");
        newSongPart(song);
      }
      if (next.attr("class").equals("chord-pro-line")) {
        newLine(song.getLastSongPart());
      }


      for (Element nextChild: next.children()) {
        if (nextChild.tagName().equals("div")) {
          if (nextChild.attr("class").equals("chord-pro-segment")) {

            for (Element nextSub: nextChild.children()) {
              SongPart lastSongPart = song.getLastSongPart();
              Line lastLine = lastSongPart.getLastLine();
              if (nextSub.attr("class").equals("chord-pro-lyric") && nextSub.childNodeSize() > 0) {
                TextNode textNode = (TextNode) nextSub.childNode(0);
                if (lastSongPart.getSongPartType() == null) {
                  //set type if not yet set for the current part
                  for (SongPartType nextType: SongPartType.values()) {
                    if (textNode.getWholeText().toUpperCase().contains(nextType.name().toUpperCase())) {
                      lastSongPart.setSongPartType(nextType);
                    }
                  }
                }
                else {
                  if (lastLine.getLineParts().size() == 0)
                    newLinePart(lastLine);
                  lastLine.getLastLinePart().setText(textNode.getWholeText());
                  System.out.println ("add text " + textNode.getWholeText());



                }
              }

              if (nextSub.attr("class").equals("chord-pro-note") && nextSub.childNodeSize() > 0) {
                TextNode textNode = (TextNode) nextSub.childNode(0);
                LinePart newLinePart = newLinePart(lastLine);

                String normalized = textNode.getWholeText().replace("|", "");
                normalized = normalized.replace("////", "").replace("///", "").replace("//", "");
                for (String nextChord: normalized.split(" ")) {
                  if (nextChord.trim().isEmpty())
                    continue;

                  System.out.println ("add chord " + nextChord);
                  if (Pattern.matches("[1-9]x", nextChord))
                    continue;
                  newLinePart.setChord(new Chord(nextChord).toString().trim());

                }



              }
            }

          }


        }


      }

    }

    SongRepairer songRepairer = new SongRepairer();
    songRepairer.repairSong(song);

    return song;
  }

  public void newSongPart (final Song song) {
    SongPart newSongPart = new SongPart();
    song.getSongParts().add(newSongPart);
  }

  public void newLine (final SongPart songpart) {
    Line newLine = new Line();
    songpart.getLines().add(newLine);
  }

  public LinePart newLinePart (final Line line) {
    LinePart newLinePart = new LinePart();
    line.getLineParts().add(newLinePart);
    return newLinePart;
  }
}


