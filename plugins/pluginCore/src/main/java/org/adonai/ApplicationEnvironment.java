package org.adonai;

import java.util.List;
import org.adonai.model.Configuration;
import org.adonai.model.Model;
import org.adonai.model.Session;
import org.adonai.model.Song;
import org.adonai.model.SongBook;
import org.adonai.services.ModelService;
import org.adonai.services.SessionService;
import org.pf4j.PluginManager;

/**
 * wraps environment, which is necessary across the application
 */
public class ApplicationEnvironment {

  /**
   * plugin manager
   */
  private final PluginManager pluginManager;

  private Model model;

  private AdonaiProperties adonaiProperties = new AdonaiProperties();

  private final ServiceRegistry serviceRegistry = new ServiceRegistry(this);

  private Session currentSession = null;


  private Song currentSong = null;

  /**
   * constructor
   * @param pluginManager  the plugin manager
   */
  public ApplicationEnvironment(final PluginManager pluginManager) {
    this.pluginManager = pluginManager;

    ModelService modelService = new ModelService(this);
    model = modelService.load();
  }

  /**
   * getter
   * @return plugin manager
   */
  public PluginManager getPluginManager() {
    return pluginManager;
  }

  /**
   * getter
   *
   * @param type type
   *
   * @return extensions of a certain type
   */
  public <T> List<T> getExtensions (Class<T> type) {
    return pluginManager.getExtensions(type);
  }

  public AdonaiProperties getAdonaiProperties() {
    return adonaiProperties;
  }


  public Model getModel() {
    return model;
  }

  public String getCurrentTenant () {
    return adonaiProperties.getCurrentTenant();
  }



  public Configuration getCurrentConfiguration () {
    return model.getCurrentTenantModel().get();

  }

  public Session getCurrentSession() {
    return currentSession;
  }

  public void setCurrentSession(Session currentSession) {
    this.currentSession = currentSession;
  }

  public Song getCurrentSong() {
    return currentSong;
  }

  public void setCurrentSong(Song currentSong) {
    this.currentSong = currentSong;
  }

  public SongBook getCurrentSongBook() {
    if (getCurrentConfiguration().getSongBooks().isEmpty()) {
      SongBook newSongbook = new SongBook();
      getCurrentConfiguration().getSongBooks().add(newSongbook);
    }
    return getCurrentConfiguration().getSongBooks().get(0);
  }

  public ServiceRegistry getServices() {
    return serviceRegistry;
  }
}
