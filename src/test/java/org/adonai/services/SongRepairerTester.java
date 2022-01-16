package org.adonai.services;

import org.adonai.ApplicationEnvironment;
import org.adonai.model.Configuration;
import org.adonai.model.Model;
import org.adonai.model.Song;
import org.adonai.model.SongBook;

public class SongRepairerTester {

  public static void main(String[] args) {

    ApplicationEnvironment applicationEnvironment = new ApplicationEnvironment(null);
    applicationEnvironment.setCreateDefaultExportConfigurations(false);
    ModelService modelService = new ModelService(applicationEnvironment);
    Model model = modelService.load();
    Configuration configuration = model.getCurrentTenantModel().get();
    for (SongBook songBook: configuration.getSongBooks()) {
      for (Song song: songBook.getSongs()) {
        SongRepairer songRepairer = new SongRepairer();
        songRepairer.repairSong(song);
      }
    }
  }
}
