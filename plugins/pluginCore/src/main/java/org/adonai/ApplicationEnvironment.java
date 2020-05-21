package org.adonai;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.adonai.additionals.AdditionalsImporter;
import org.adonai.fx.main.ScopeItem;
import org.adonai.fx.main.ScopeItemProvider;
import org.adonai.model.Additional;
import org.adonai.model.AdditionalType;
import org.adonai.model.Configuration;
import org.adonai.model.Model;
import org.adonai.model.Session;
import org.adonai.model.Song;
import org.adonai.model.SongBook;
import org.adonai.services.ModelService;
import org.adonai.services.SessionService;
import org.pf4j.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * wraps environment, which is necessary across the application
 */
public class ApplicationEnvironment {

  private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationEnvironment.class);



  private static boolean initialized;
  /**
   * plugin manager
   */
  private final PluginManager pluginManager;

  private Model model;

  private AdonaiProperties adonaiProperties = new AdonaiProperties();

  private final ServiceRegistry serviceRegistry = new ServiceRegistry(this);

  private Session currentSession = null;

  private Song currentSong = null;

  private ScopeItem currentScopeItem = null;

  private List<Song> songsOfCurrentScope = new ArrayList<>();

  private boolean showOriginalKey = false;

  private ScopeItemProvider scopeItemProvider = new ScopeItemProvider();


  /**
   * constructor
   * @param pluginManager  the plugin manager
   */
  public ApplicationEnvironment(final PluginManager pluginManager) {
    if (initialized)
      LOGGER.error("ApplicationEnvironment must be initialized only once in the application", new IllegalStateException());
    else
      initialized = true;

    this.pluginManager = pluginManager;

    ModelService modelService = new ModelService(this);
    model = modelService.load();
  }

  /**
   * gets all songs of the currently selected scope item
   *
   * @return songs of scope item
   */
  public List<Song> getSongsOfCurrentScope () {
    return songsOfCurrentScope;
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

  public Collection<String> getTenants () {
    return getServices().getModelService().getTenants();
  }



  public Configuration getCurrentConfiguration () {
    return model.getCurrentTenantModel().get();

  }

  public File getMp3FileOfCurrentSong() {
    Song currentSong = getCurrentSong();
    if (currentSong == null) {
      LOGGER.info("getMp3FileOfCurrentSong returns null (currentSong=null)");
      return null;
    }
    if (currentSong.getAdditionals() == null) {
      LOGGER.info("getMp3FileOfCurrentSong returns null (getAdditionals=null)");
      return null;
    }
    AdditionalsImporter importer = new AdditionalsImporter();

    for (Additional nextAdditional : currentSong.getAdditionals()) {
      if (nextAdditional.getAdditionalType().equals(AdditionalType.AUDIO) && nextAdditional.getLink() != null) {
        File additionalFile = importer.getAdditionalFile(currentSong, nextAdditional);
        LOGGER.info("getMp3FileOfCurrentSong returns " + additionalFile);
        return additionalFile;
      }
    }

    LOGGER.info("getMp3FileOfCurrentSong returns null (not found) ");

    return null;
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

  public ScopeItem getCurrentScopeItem() {
    return currentScopeItem;
  }

  public void setCurrentScopeItem(ScopeItem currentScopeItem) {
    if (currentScopeItem == null)
      return;

    this.currentScopeItem = currentScopeItem;
    this.songsOfCurrentScope.clear();

    SongBook currentSongBook = getCurrentSongBook();
    for (Integer integer: currentScopeItem.getSongs()) {
      Song song = currentSongBook.findSong(integer);
      this.songsOfCurrentScope.add(song);
    }

    //if id of last shown song is not available in the current scope, then start at the beginning
    if (currentSong == null || ! currentScopeItem.getSongs().contains(currentSong.getId())) {
      this.setCurrentSong(songsOfCurrentScope.isEmpty() ? null: songsOfCurrentScope.get(0));
    }

  }

  public boolean isShowOriginalKey() {
    return showOriginalKey;
  }

  public void setShowOriginalKey(boolean showOriginalKey) {
    this.showOriginalKey = showOriginalKey;
  }

  public ScopeItem getScopeItem(Session newSession) {
    for (ScopeItem next: getAllScopeItems()) {
      if (next.getSession() != null && next.getSession().equals(newSession))
        return next;
    }

    throw new IllegalStateException("Session " + newSession.getName() + " not found");


  }

  public ScopeItem getScopeItem(SongBook songbook) {
    for (ScopeItem next: getAllScopeItems()) {
      if (next.getSongBook() != null && next.getSongBook().equals(songbook))
        return next;
    }

    throw new IllegalStateException("Songbook " + songbook + " not found");

  }

  public List<ScopeItem> getAllScopeItems () {
    return scopeItemProvider.getScopeItems(getCurrentConfiguration());
  }
}
