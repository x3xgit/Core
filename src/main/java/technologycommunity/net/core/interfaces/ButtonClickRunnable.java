package technologycommunity.net.core.interfaces;

import technologycommunity.net.core.inventory.Menu;
import technologycommunity.net.core.inventory.buttons.Button;
import technologycommunity.net.core.structures.Artist;

public interface ButtonClickRunnable {
    void onButtonClick(Artist clicker, Button button, Menu menu);
}
