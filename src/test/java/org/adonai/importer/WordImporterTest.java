package org.adonai.importer;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import org.adonai.model.Configuration;
import org.adonai.model.ConfigurationService;
import org.adonai.model.Song;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

public class WordImporterTest {

    @Test
    public void importFromWordFile () throws IOException, InvalidFormatException {
        WordImporter wordImporter = new WordImporter();

        ConfigurationService configurationService = new ConfigurationService();
        Configuration configuration = configurationService.get();

        Collection<Song> importedSongs = wordImporter.importFromWordFile(configuration, new File(System.getProperty("user.home") + "/privat/worship/worshipmappe.docx"));

        for (Song next: importedSongs) {
            System.out.println (next);
        }

        configuration.getSongBooks().get(0).getSongs().addAll(importedSongs);

        //configurationService.set(configuration);

        System.out.println (configuration.getSongBooks().get(0));

    }
}
