package technologycommunity.net.core.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import technologycommunity.net.core.Validator;
import technologycommunity.net.core.event.CoreListener;
import technologycommunity.net.core.exception.CoreException;
import technologycommunity.net.core.inventory.buttons.Button;
import technologycommunity.net.core.inventory.structures.Position;
import technologycommunity.net.core.inventory.structures.RegisteredMenu;
import technologycommunity.net.core.structures.Artist;

import java.util.*;

public class Menu extends CoreListener {
    private static final List<RegisteredMenu> registeredMenuList = new ArrayList<>();

    private final List<Button> buttons = new ArrayList<>();
    private Map<Integer, Inventory> pages = new LinkedHashMap<>();

    private @NotNull String title;
    private @NotNull Integer size;
    private @NotNull Integer page = 1;
    private @NotNull Integer lastPage = 1;
    private @Nullable Artist viewer = null;

    private boolean isUpdating = false;

    private final Menu parent;

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
        this(null);
    }

    protected final void setTitle(final @NotNull String title) {
        this.title = title;
    }

    protected final void setSize(final @NotNull Integer size) {
        this.size = size;
    }

    private void setPage(final @NotNull Integer page) {
        this.lastPage = this.page;
        this.page = page;
    }

    public final @NotNull Integer getPage() {
        return page;
    }

    private void resetPages() {
        this.setPage(getFirstPage());
    }

    public final Integer getFirstPage() {
        return 1;
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
            }
        }

        return null;
    }

    private static boolean isItemsSimilarFromTwoInventories(Map<Integer, ItemStack> firstInventory, Map<Integer, ItemStack> secondInventory) {
        if (firstInventory.size() != secondInventory.size()) return false;

        for (Map.Entry<Integer, ItemStack> firstInventoryItemSlot : firstInventory.entrySet())
            if (secondInventory.get(firstInventoryItemSlot.getKey()) != null && firstInventoryItemSlot.getValue() != null)
                if (!secondInventory.get(firstInventoryItemSlot.getKey()).isSimilar(firstInventoryItemSlot.getValue()))
                    return false;

        return true;
    }

    public final void displayTo(Player player) {
        this.viewer = Artist.of(player);
        this.openMenu();
    }

    protected final void openMenu() {
        if (!Validator.valid(this.viewer)) return;

        if (this.getPages().isEmpty())
            this.drawPages();

        this.viewer.openInventory(this.getInventory(getFirstPage()));
    }

    protected final void updateMenu() {
        if (!Validator.valid(this.viewer)) return;

        this.drawPages();
        this.update(this::openMenu);
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
    public final void onInventoryClickEvent(final InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();

        if (!Validator.valid(this.viewer)) return;
        if (!Validator.valid(this.viewer, player)) return;

        event.setCancelled(true);

        if (event.getCurrentItem() == null)
            this.onEmptySlotClick(this.viewer, Position.of(this.getPage(), event.getSlot()));

        final Button button = this.getButton(Position.of(event.getSlot(), this.getPage()));

        if (button != null)
            button.onButtonClick(this.viewer, button, this);
    }

    @EventHandler
    public final void onInventoryOpenEvent(final InventoryOpenEvent event) {
        final Player player = (Player) event.getPlayer();

        if (!Validator.valid(this.viewer)) return;
        if (!Validator.valid(this.viewer, player)) return;

        this.onMenuOpen(this.viewer, this);
    }

    @EventHandler
    public final void onInventoryCloseEvent(final InventoryCloseEvent event) {
        final Player player = (Player) event.getPlayer();

        if (!Validator.valid(this.viewer)) return;
        if (!Validator.valid(this.viewer, player)) return;

        if (this.isUpdating)
            if (this.getPage().equals(this.lastPage))
                this.onMenuPageChange(this.viewer, this, this.lastPage, this.getPage());
            else this.onMenuUpdate(this.viewer, this);
        else {
            this.resetPages();
            this.onMenuClose(this.viewer, this);
        }
    }

    protected void onEmptySlotClick(final Artist artist, final Position position) {

    }

    protected void onMenuPageChange(final Artist artist, final Menu menu, final Integer oldPage, final Integer newPage) {

    }

    protected void onMenuUpdate(final Artist artist, final Menu menu) {

    }

    protected void onMenuOpen(final Artist artist, final Menu menu) {

    }

    protected void onMenuClose(final Artist artist, final Menu menu) {

    }

    public final Menu getParentMenu() {
        return this.parent;
    }

    private void update(final Runnable task) {
        this.isUpdating = true; task.run(); this.isUpdating = false;
    }
}
