package technologycommunity.net.core.inventory.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

import technologycommunity.net.core.inventory.Menu;
import technologycommunity.net.core.inventory.buttons.Button;
import technologycommunity.net.core.inventory.structures.MenuStatus;
import technologycommunity.net.core.inventory.structures.Position;
import technologycommunity.net.core.listener.internal.CoreListener;

public class MenuListener extends CoreListener {
    @EventHandler
    public final void onMenuClick(final InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        final Menu menu = Menu.getPlayerMenu(player);

        if (event.getClickedInventory() == null)
            return;

        if (menu == null)
            return;

        if (event.getClickedInventory().getType().equals(InventoryType.PLAYER) && menu.isAllowPlayerInventory())
            return;

        // Checks are finished

        final Integer page = menu.getPage();
        final Integer slot = event.getSlot();

        event.setCancelled(true);

        if (event.getCurrentItem() == null)
            menu.onEmptySlotClick(menu.getViewer(), Position.of(page, slot));

        final Button button = menu.getButton(Position.of(slot, page));

        if (button != null)
            button.onButtonClick(menu.getViewer(), button, menu);
    }

    @EventHandler
    public final void onMenuOpen(final InventoryOpenEvent event) {
        final Player player = (Player) event.getPlayer();
        final Menu menu = Menu.getPlayerMenu(player);

        if (menu == null)
            return;

        menu.onMenuOpen(menu.getViewer(), menu);
    }

    @EventHandler
    public final void onMenuClose(final InventoryCloseEvent event) {
        final Player player = (Player) event.getPlayer();
        final Menu menu = Menu.getPlayerMenu(player);

        if (menu == null)
            return;

        if (menu.getStatus().equals(MenuStatus.UPDATING)) {
            if (menu.getPage().equals(menu.getLastPage()))
                menu.onMenuPageChange(menu.getViewer(), menu, menu.getLastPage(), menu.getPage());
            else menu.onMenuUpdate(menu.getViewer(), menu);
        } else {
            Menu.rejectPlayerMenu(player);
            menu.onMenuClose(menu.getViewer(), menu);
            menu.restartMenu();
        }
    }
}
