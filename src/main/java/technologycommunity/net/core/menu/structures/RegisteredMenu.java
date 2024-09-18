package technologycommunity.net.core.menu.structures;

import technologycommunity.net.core.menu.Menu;

public class RegisteredMenu {
    private final Class<? extends Menu> menuClass;
    private final Menu menuObject;

    private RegisteredMenu(Class<? extends Menu> menuClass, Menu menuObject) {
        this.menuClass = menuClass;
        this.menuObject = menuObject;
    }

    public static RegisteredMenu of(Class<? extends Menu> menuClass, Menu menuObject) {
        return new RegisteredMenu(menuClass, menuObject);
    }

    public Class<? extends Menu> getMenuClass() {
        return menuClass;
    }

    public Menu getMenuObject() {
        return menuObject;
    }
}
