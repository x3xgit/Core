package technologycommunity.net.core.interfaces;

import technologycommunity.net.core.inventory.Menu;
import technologycommunity.net.core.inventory.buttons.Button;
import technologycommunity.net.core.inventory.structures.Viewer;

public interface ButtonClickRunnable {
    void onButtonClick(Viewer clicker, Button button, Menu menu);
}
