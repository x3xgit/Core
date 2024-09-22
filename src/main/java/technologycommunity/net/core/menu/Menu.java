package technologycommunity.net.core.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import technologycommunity.net.core.Validator;
import technologycommunity.net.core.constants.CoreConstants;
import technologycommunity.net.core.menu.model.ItemCreator;
import technologycommunity.net.core.exception.CoreException;
import technologycommunity.net.core.menu.model.meta.TextureType;
import technologycommunity.net.core.menu.structures.ButtonPosition;
import technologycommunity.net.core.menu.structures.RegisteredMenu;
import technologycommunity.net.core.plugin.Core;
import technologycommunity.net.core.structures.Artist;

import java.util.*;

public abstract class Menu {
    private static final List<RegisteredMenu> registeredMenuList = new ArrayList<>();

    private final List<Button> buttons = new ArrayList<>();
    private Map<Integer, Inventory> pages = new LinkedHashMap<>();

    private @NotNull String title;
    private @NotNull Integer size;
    private @NotNull Integer page = this.getFirstPage();
    private @NotNull Integer lastPage = this.getFirstPage();
    private @Nullable Artist viewer = null;

    private boolean updating = false;

    private boolean allowPlayerInventory = true;

    private boolean isPageButtonsAdded = false;

    // Constructor
    protected Menu() {
        this.title = "&8Menu ({current_page}/{maximum_page})";
        this.size = 5;

        if (!registeredMenuList.stream().map(RegisteredMenu::getMenuClass).toList().contains(this.getClass()))
            registeredMenuList.add(RegisteredMenu.of(this.getClass(), this));
    }

    protected final void setTitle(final @NotNull String title) {
        this.title = title;
    }

    protected final void setSize(final int size) {
        this.size = size;
    }

    private void setPage(final int page) {
        this.lastPage = this.page;
        this.page = page;
    }

    protected final int getPage() {
        return page;
    }

    private int getMaxPageFromButtons() {
        return this.buttons.stream()
                .map(b -> b.getPosition().getPage())
                .max(Integer::compare)
            .orElse(this.getFirstPage());
    }

    protected final @NotNull Map<Integer, Inventory> getPages() {
        return this.pages;
    }

    public final int getFirstPage() {
        return 1;
    }

    protected final boolean canGo(final int to) {
        return to < this.getMaxPageFromButtons();
    }

    protected final boolean canBack(final int to) {
        return to > this.getFirstPage();
    }

    protected final boolean canGo(final int to, final int pages) {
        return to < pages;
    }

    protected final boolean canBack(final int to, final int pages) {
        return to > this.getFirstPage();
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

    private int getFreestButtonSlotFromEnd(final @NotNull Inventory inventory) {
        for (int slot = inventory.getSize() - 1; slot >= 0; slot --) {
            final ItemStack item = inventory.getItem(slot);

            if (item == null || item.getType().isAir())
                return slot;
        }

        return -1;
    }

    private int getFreestButtonSlotFromStart(final @NotNull Inventory inventory) {
        for (int slot = 0; slot < inventory.getSize(); slot ++) {
            final ItemStack item = inventory.getItem(slot);

            if (item == null || item.getType().isAir())
                return slot;
        }

        return -1;
    }

    protected boolean isSlotFree(int page, int slot) {
        final Inventory pageInventory = this.pages.get(page);

        if (pageInventory == null)
            return false;

        final ItemStack item = pageInventory.getItem(slot);

        return item == null || item.getType().isAir();
    }

    protected final void setAllowPlayerInventory(final boolean allowPlayerInventory) {
        this.allowPlayerInventory = allowPlayerInventory;
    }

    protected final boolean isAllowPlayerInventory() {
        return this.allowPlayerInventory;
    }

    protected final @Nullable Artist getViewer() {
        return viewer;
    }

    protected final boolean isUpdating() {
        return updating;
    }

    protected final int getLastPage() {
        return lastPage;
    }

    private void drawPages() {
        if (this.shouldAddPageButtons())
            this.addPageButtons();

        this.pages = Drawer.from(this.buttons, this.title, this.size)
            .draw();
    }

    private @NotNull Inventory getInventory(final int page) {
        if (this.pages.isEmpty())
            this.drawPages();

        final Inventory pageInv = this.pages.get(page);

        if (this.pages.isEmpty())
            throw new CoreException("Can't draw pages because they are empty.");

        if (pageInv == null)
            throw new CoreException("Can't return page because it's not found.");

        return pageInv;
    }

    private void openMenu(final @NotNull Menu menu) {
        if (!Validator.checkNotNull(this.viewer))
            return;

        final Inventory inventory = this.getInventory(getPage());

        final Player player = this.getViewer().getPlayer();
        final Artist artist = Artist.of(player);

        this.viewer.openInventory(inventory);
        Menu.setPlayerMenu(this.getViewer().getPlayer(), menu);
    }

    protected void openMenu() {
        this.openMenu(this);
    }

    private void update(final Runnable task) {
        this.updating = true;
        task.run();
        this.updating = false;
    }

    protected final void updateMenu() {
        this.drawPages();
        this.update(this::openMenu);
    }

    protected final void restartMenu() {
        this.setPage(getFirstPage());
    }

    public final void displayTo(final @NotNull Player player) {
        final Menu lastMenu = getPlayerMenu(player);
        final Menu menu = this;

        this.viewer = Artist.of(player);
        this.openMenu(menu);

        if (lastMenu != null)
            this.onMenuChange(this.viewer, lastMenu, menu);

        this.onMenuOpen(this.viewer, menu);
    }

    protected final void registerButton(final Button button) {
        if (button.getPosition().getPage() > 100 || button.getPosition().getPage() < 1)
            throw new CoreException("Button page is higher than 100 or it's below 1, please set button page between 1-100.");

        for (Button buttonStream : new ArrayList<>(this.buttons)) {
            final ButtonPosition position = buttonStream.getPosition();

            if (position.getPage().equals(button.getPosition().getPage()) && position.getSlot().equals(button.getPosition().getSlot()))
                this.buttons.remove(buttonStream);
        }

        this.buttons.add(button);
    }

    protected final @Nullable Button getButton(final ButtonPosition position) {
        for (Button button : this.buttons)
            if (button.getPosition().getPage().equals(position.getPage()) && button.getPosition().getSlot().equals(position.getSlot()))
                return button;

        return null;
    }

    public static @NotNull List<RegisteredMenu> getRegisteredMenuList() {
        return registeredMenuList;
    }

    protected void onEmptySlotClick(final @NotNull Artist artist, final @NotNull ButtonPosition position) {

    }

    protected void onMenuPageChange(final @NotNull Artist artist, final @NotNull Menu menu, final int oldPage, final int newPage) {

    }

    protected void onMenuChange(final @NotNull Artist artist, final @NotNull Menu oldMenu, final @NotNull Menu newMenu) {

    }

    protected void onMenuUpdate(final @NotNull Artist artist, final @NotNull Menu menu) {

    }

    protected void onMenuOpen(final @NotNull Artist artist, final @NotNull Menu menu) {

    }

    protected void onMenuClose(final @NotNull Artist artist, final @NotNull Menu menu) {

    }

    protected boolean shouldAddPageButtons() {
        return false;
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

    private void addPageButtons() {
        for (int page = 1; page <= this.getMaxPageFromButtons(); page++) {
            final int currentPage = page;

            this.buttons.add(
                    new Button() {
                        @Override
                        public void onButtonClick(final @NotNull Artist clicker, final @NotNull Menu menu) {
                            boolean nextPage = nextPage();

                            if (nextPage)
                                clicker.tell("&aSuccessfully changed page to: " + getPage() + ".");
                            else clicker.tell("&cCouldn't change page.");
                        }

                        @Override
                        public @NotNull ButtonPosition getPosition() {
                            return ButtonPosition.of(size * 9 - 4, currentPage);
                        }

                        @Override
                        public @NotNull ItemStack getItem() {
                            return ItemCreator.of(Material.PLAYER_HEAD)
                                    .name((canGo(currentPage) ? "&a" : "&c") + "Next")
                                    .skull(TextureType.BASE64, CoreConstants.SKULLS.skullNextArrowAction)
                                .create();
                        }
                    }
            );

            this.buttons.add(
                    new Button() {
                        @Override
                        public void onButtonClick(final @NotNull Artist clicker, final @NotNull Menu menu) {
                            boolean previousPage = previousPage();

                            if (previousPage)
                                clicker.tell("&aSuccessfully changed page to: " + getPage() + ".");
                            else clicker.tell("&cCouldn't change page.");
                        }

                        @Override
                        public @NotNull ButtonPosition getPosition() {
                            return ButtonPosition.of(size * 9 - 6, currentPage);
                        }

                        @Override
                        public @NotNull ItemStack getItem() {
                            return ItemCreator.of(Material.PLAYER_HEAD)
                                    .name((canBack(currentPage) ? "&a" : "&c") + "Previous")
                                    .skull(TextureType.BASE64, CoreConstants.SKULLS.skullPreviousArrowAction)
                                    .create();
                        }
                    }
            );
        }

        this.isPageButtonsAdded = true;
    }
}
