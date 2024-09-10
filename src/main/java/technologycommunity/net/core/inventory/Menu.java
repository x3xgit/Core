package technologycommunity.net.core.inventory;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import technologycommunity.net.core.event.CoreListener;
import technologycommunity.net.core.exception.CoreException;
import technologycommunity.net.core.inventory.buttons.Button;
import technologycommunity.net.core.inventory.structures.Position;
import technologycommunity.net.core.inventory.structures.RegisteredMenu;
import technologycommunity.net.core.inventory.structures.Viewer;
import technologycommunity.net.core.plugin.Core;

import java.util.*;

public class Menu extends CoreListener {
    private static final List<RegisteredMenu> registeredMenuList = new ArrayList<>();

    private final List<Button> buttons = new ArrayList<>();
    private Map<Integer, Inventory> pages = new LinkedHashMap<>();

    private @NotNull String title;
    private @NotNull Integer size;
    private @NotNull Integer page = 1;
    private @NotNull Integer lastPage = 1;
    private @Nullable Viewer viewer = null;

    private Menu parent = null;

    // Constructor
    protected Menu(Menu parent) {
        super(true);

        this.parent = parent;

        this.title = "&8Menu ({current_page}/{maximum_page})";
        this.size = 5;

        if (!registeredMenuList.stream().map(RegisteredMenu::getMenuClass).toList().contains(this.getClass()))
            registeredMenuList.add(RegisteredMenu.of(this.getClass(), this));
    }

    protected Menu() {
        new Menu(null);
    }

    protected final void setTitle(@NotNull String title) {
        this.title = title;
    }

    protected final void setSize(@NotNull Integer size) {
        this.size = size;
    }

    private void setPage(@NotNull Integer page) {
        this.lastPage = this.page;
        this.page = page;
    }

    public final @NotNull Integer getPage() {
        return page;
    }

    private void resetPages() {
        this.setPage(this.getFirstPage());
    }

    public final Integer getFirstPage() {
        return 1;
    }

    private void updateMenu() {
        this.drawPages();

        if (this.viewer != null)
            this.viewer.reOpenMenu();
        else throw new CoreException("Can't update menu because viewer is not found.");
    }

    protected final boolean canGo(Integer page) {
        return page < this.pages.size();
    }

    protected final boolean canBack(Integer page) {
        return page > 1;
    }

    protected final boolean nextPage() {
        if (this.canGo(this.page)) {
            this.setPage(this.page + 1);
            this.updateMenu();

            return true;
        }

        return false;
    }

    protected final boolean previousPage() {
        if (this.canBack(this.page)) {
            this.setPage(this.page - 1);
            this.updateMenu();

            return true;
        }

        return false;
    }

    public final void drawPages() {
        this.pages = InventoryDrawer.create(this.buttons, this.title, this.size)
            .draw();
    }

    public final @NotNull Inventory getInventory(Integer page) {
        if (this.pages.isEmpty())
            this.drawPages();

        if (this.pages.isEmpty())
            throw new CoreException("Can't draw pages because they are empty.");

        if (this.pages.get(page) == null)
            throw new CoreException("Can't return page because it's not found.");

        return this.pages.get(page);
    }

    public static @Nullable Menu getMenu(Inventory inventory) {
        for (RegisteredMenu registeredMenu : getRegisteredMenuList())
        {
            Menu menu = registeredMenu.getMenuObject();

            for (Map.Entry<Integer, Inventory> pageEntry : menu.getPages().entrySet())
            {
                Inventory pageInventory = pageEntry.getValue();

                Map<Integer, ItemStack> itemsFromGivenInventory = new HashMap<>();
                Map<Integer, ItemStack> itemsFromPageInventory = new HashMap<>();

                for (int slot = 0; slot < inventory.getSize(); slot ++) {
                    ItemStack item = inventory.getItem(slot);

                    if (item != null)
                        itemsFromGivenInventory.put(slot, item);
                }

                for (int slot = 0; slot < pageInventory.getSize(); slot ++) {
                    ItemStack item = pageInventory.getItem(slot);

                    if (item != null)
                        itemsFromPageInventory.put(slot, item);
                }

                if (isItemsSimilarFromTwoInventories(itemsFromGivenInventory, itemsFromPageInventory))
                    return registeredMenu.getMenuObject();
                else Core.getInstance().getCoreLogger().information("Inventories are not similar (getMenu());");
            }
        }

        return null;
    }

    private static boolean isItemsSimilarFromTwoInventories(Map<Integer, ItemStack> firstInventory, Map<Integer, ItemStack> secondInventory) {
        if (firstInventory.size() != secondInventory.size()) return false;

        for (Map.Entry<Integer, ItemStack> firstInventoryItemSlot : firstInventory.entrySet())
            if (secondInventory.get(firstInventoryItemSlot.getKey()) != null && firstInventoryItemSlot.getValue() != null)
                if (!secondInventory.get(firstInventoryItemSlot.getKey()).isSimilar(firstInventoryItemSlot.getValue())) {
                    Core.getInstance().getCoreLogger().information("Item is not similar (isItemsSimilarFromTwoInventories());");
                    return false;
                }

        return true;
    }

    public final void displayTo(Player player) {
        this.viewer = new Viewer(player, this);

        this.drawPages();

        this.viewer.openMenu();
    }

    public final @NotNull Map<Integer, Inventory> getPages() {
        return this.pages;
    }

    protected final void registerButton(Button button) {
        if (button.getPosition().getPage() > 100 || button.getPosition().getPage() < 1)
            throw new CoreException("Button page is higher than 100 or it's below 1, please set button page between 1-100.");

        this.buttons.add(button);
    }

    protected final @Nullable Button getButton(Position position) {
        for (Button button : this.buttons)
            if (button.getPosition().getPage().equals(position.getPage()) && button.getPosition().getSlot().equals(position.getSlot()))
                return button;

        return null;
    }

    public static @NotNull List<RegisteredMenu> getRegisteredMenuList() {
        return registeredMenuList;
    }

    @EventHandler
    public final void onInventoryClickEvent(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        final Menu menu = getMenu(event.getClickedInventory());

        if (!(menu != null && this.viewer != null && this.viewer.isSimilar(player)))
            return;

        event.setCancelled(true);

        if (event.getCurrentItem() == null)
            this.onEmptySlotClick(this.viewer, Position.of(this.getPage(), event.getSlot()));

        final Button button = this.getButton(Position.of(event.getSlot(), this.getPage()));

        if (button != null)
            button.onButtonClick(this.viewer, button, menu);
    }

    @EventHandler
    public final void onInventoryOpenEvent(InventoryOpenEvent event) {
        final Player player = (Player) event.getPlayer();
        final Menu menu = getMenu(event.getInventory());

        if (!(menu != null && this.viewer != null && this.viewer.isSimilar(player)))
            return;

        this.onMenuOpen(this.viewer, menu);
    }

    @EventHandler
    public final void onInventoryCloseEvent(InventoryCloseEvent event) {
        final Player player = (Player) event.getPlayer();
        final Menu menu = getMenu(event.getInventory());

        if (!(menu != null && this.viewer != null && this.viewer.isSimilar(player)))
            return;

        if (this.viewer.isUpdating())
            if (menu.getPage().equals(this.lastPage))
                menu.onMenuPageChange(this.viewer, menu, menu.lastPage, menu.getPage());
            else menu.onMenuUpdate(this.viewer, menu);
        else {
            menu.resetPages();
            menu.onMenuClose(this.viewer, menu);
        }
    }

    protected void onEmptySlotClick(Viewer clicker, Position position) {

    }

    protected void onMenuPageChange(Viewer viewer, Menu menu, Integer oldPage, Integer newPage) {

    }

    protected void onMenuUpdate(Viewer viewer, Menu menu) {

    }

    protected void onMenuOpen(Viewer viewer, Menu menu) {

    }

    protected void onMenuClose(Viewer viewer, Menu menu) {

    }

    public final Menu getParentMenu() {
        return this.parent;
    }
}
