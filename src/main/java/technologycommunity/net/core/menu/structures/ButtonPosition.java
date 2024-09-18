package technologycommunity.net.core.menu.structures;

public class ButtonPosition {
    private final Integer slot;
    private final Integer page;

    private ButtonPosition(Integer slot, Integer page) {
        this.slot = slot;
        this.page = page;
    }

    public static ButtonPosition of(Integer slot, Integer page) {
        return new ButtonPosition(slot, page);
    }

    public Integer getSlot() {
        return slot;
    }

    public Integer getPage() {
        return page;
    }
}
