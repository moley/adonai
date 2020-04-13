package org.adonai.ui.imports;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.StackPane;
import org.adonai.ui.imports.pages.WizardPage;

import java.util.Stack;

/**
 * basic wizard infrastructure class
 */
public class Wizard extends StackPane {
  private static final int UNDEFINED = -1;
  private ObservableList<WizardPage> pages = FXCollections.observableArrayList();
  private Stack<Integer> history = new Stack<>();
  private int curPageIdx = UNDEFINED;

  Wizard(WizardPage... nodes) {
    pages.addAll(nodes);
    navTo(0);
    setStyle("-fx-padding: 10;");
  }

  public void nextPage() {
    if (hasNextPage()) {
      navTo(curPageIdx + 1);
    }
  }

  public void priorPage() {
    if (hasPriorPage()) {
      navTo(history.pop(), false);
    }
  }

  public boolean hasNextPage() {
    return (curPageIdx < pages.size() - 1);
  }

  public boolean hasPriorPage() {
    return !history.isEmpty();
  }

  public void navTo(int nextPageIdx, boolean pushHistory) {
    if (nextPageIdx < 0 || nextPageIdx >= pages.size()) return;
    if (curPageIdx != UNDEFINED) {
      if (pushHistory) {
        history.push(curPageIdx);
      }
    }

    WizardPage nextPage = pages.get(nextPageIdx);
    curPageIdx = nextPageIdx;
    getChildren().clear();
    getChildren().add(nextPage);
    nextPage.manageButtons();
  }

  public void navTo(int nextPageIdx) {
    navTo(nextPageIdx, true);
  }

  public void navTo(String id) {
    if (id == null) {
      return;
    }

    pages.stream()
      .filter(page -> id.equals(page.getId()))
      .findFirst()
      .ifPresent(page ->
        navTo(pages.indexOf(page))
      );
  }

  public void finish() {
  }

  public void cancel() {
  }
}