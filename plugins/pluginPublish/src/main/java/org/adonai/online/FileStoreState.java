package org.adonai.online;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class FileStoreState {

  private List<FileStoreStateItem> items = new ArrayList<>();

  public void addItem (final FileStoreStateItem item) {
    this.items.add(item);
  }

  public FileStoreStateItem findOrCreateItem(String path) {
    for (FileStoreStateItem next: items) {
      if (next.getLocalPath() != null && next.getLocalPath().equals(path))
        return next;
    }

    FileStoreStateItem fileStoreStateItem = new FileStoreStateItem();
    fileStoreStateItem.setRemotePath(path);
    addItem(fileStoreStateItem);
    return fileStoreStateItem;

  }
}
