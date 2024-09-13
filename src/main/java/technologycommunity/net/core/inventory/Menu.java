package technologycommunity.net.core.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import technologycommunity.net.core.Validator;
import technologycommunity.net.core.constants.CoreConstants;
import technologycommunity.net.core.inventory.structures.MenuStatus;
import technologycommunity.net.core.exception.CoreException;
import technologycommunity.net.core.inventory.buttons.Button;
import technologycommunity.net.core.inventory.structures.Position;
import technologycommunity.net.core.inventory.structures.RegisteredMenu;
import technologycommunity.net.core.plugin.Core;
import technologycommunity.net.core.structures.Artist;

import java.util.*;

public class Menu {
    private static final List<RegisteredMenu> registeredMenuList = new ArrayList<>();

    private final List<Button> buttons = new ArrayList<>();
    private Map<Integer, Inventory> pages = new LinkedHashMap<>();

    private @NotNull String title;
    private @NotNull Integer size;
    private @NotNull Integer page = 1;
    private @NotNull Integer lastPage = 1;
    private Artist viewer = null;

    private MenuStatus status;

    private boolean allowButtonClick = false;
    private boolean allowPlayerInventory = true;

    // Constructor
    protected Menu() {
        this.status = MenuStatus.NOT_ACTIVE;

        this.title = "&8Menu ({current_page}/{maximum_page})";
        this.size = 5;

        if (!registeredMenuList.stream().map(RegisteredMenu::getMenuClass).toList().contains(this.getClass()))
            registeredMenuList.add(RegisteredMenu.of(this.getClass(), this));
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

    public final @NotNull Integer getMaxPage() {
        if (pages.isEmpty() && !this.buttons.isEmpty())
            this.drawPages();

        return pages.size();
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

    public final void setAllowButtonClick(boolean allowButtonClick) {
        this.allowButtonClick = allowButtonClick;
    }

    public final void setAllowPlayerInventory(boolean allowPlayerInventory) {
        this.allowPlayerInventory = allowPlayerInventory;
    }

    public final boolean isAllowButtonClick() {
        return this.allowButtonClick;
    }

    public final boolean isAllowPlayerInventory() {
        return this.allowPlayerInventory;
    }

    public Artist getViewer() {
        return viewer;
    }

    public MenuStatus getStatus() {
        return status;
    }

    public @NotNull Integer getLastPage() {
        return lastPage;
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

    public final void displayTo(Player player) {
        this.viewer = Artist.of(player);
        this.openMenu();
    }

    protected final void openMenu() {
        if (!Validator.valid(this.viewer)) return;

        if (this.getPages().isEmpty())
            this.drawPages();

        final Inventory inventory = this.getInventory(getFirstPage());

        final Player player = this.getViewer().getPlayer();
        final Menu menu = this;

        this.viewer.openInventory(inventory);
        this.status = MenuStatus.ACTIVE;

        setPlayerMenu(this.getViewer().getPlayer(), menu);
    }

    protected final void updateMenu() {
        this.drawPages();
        this.update(this::openMenu);
    }

    public final void restartMenu() {
        this.resetPages();
    }

    public final @NotNull Map<Integer, Inventory> getPages() {
        return this.pages;
    }

    protected final void registerButton(Button button) {
        if (button.getPosition().getPage() > 100 || button.getPosition().getPage() < 1)
            throw new CoreException("Button page is higher than 100 or it's below 1, please set button page between 1-100.");

        for (Button buttonStream : new ArrayList<>(this.buttons)) {
            final Position position = buttonStream.getPosition();

            if (position.getPage().equals(button.getPosition().getPage()) && position.getSlot().equals(button.getPosition().getSlot()))
                this.buttons.remove(buttonStream);
        }

        this.buttons.add(button);
    }

    public final @Nullable Button getButton(Position position) {
        for (Button button : this.buttons)
            if (button.getPosition().getPage().equals(position.getPage()) && button.getPosition().getSlot().equals(position.getSlot()))
                return button;

        return null;
    }

    public static @NotNull List<RegisteredMenu> getRegisteredMenuList() {
        return registeredMenuList;
    }

    public void onEmptySlotClick(final Artist artist, final Position position) {

    }

    public void onMenuPageChange(final Artist artist, final Menu menu, final Integer oldPage, final Integer newPage) {

    }

    public void onMenuChange(final Artist artist, final Menu oldMenu, final Menu newMenu) {

    }

    public void onMenuUpdate(final Artist artist, final Menu menu) {

    }

    public void onMenuOpen(final Artist artist, final Menu menu) {

    }

    public void onMenuClose(final Artist artist, final Menu menu) {

    }

    private void update(final Runnable task) {
        final MenuStatus oldStatus = this.status;

        this.status = MenuStatus.UPDATING; task.run(); this.status = oldStatus;
    }

    public static Menu getPlayerMenu(final @NotNull Player player) {
        final List<MetadataValue> values = player.getMetadata(CoreConstants.NBT.TAG_MENU_CURRENT);

        for (MetadataValue value : values)
            if (value != null && value.value() != null)
                if (value.value() instanceof Menu menu)
                    return menu;

        return null;
    }

    public static void setPlayerMenu(final @NotNull Player player, final @NotNull Menu menu) {
        player.setMetadata(CoreConstants.NBT.TAG_MENU_CURRENT, new FixedMetadataValue(Core.getInstance(), menu));
    }

    public static void rejectPlayerMenu(final @NotNull Player player) {
        player.setMetadata(CoreConstants.NBT.TAG_MENU_CURRENT, new FixedMetadataValue(Core.getInstance(), null));
    }
}
