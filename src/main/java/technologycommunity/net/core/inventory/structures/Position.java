package technologycommunity.net.core.inventory.structures;

public class Position {
    private final Integer slot;
    private final Integer page;

    private Position(Integer slot, Integer page) {
        this.slot = slot;
        this.page = page;
    }

    public static Position of(Integer slot, Integer page) {
        return new Position(slot, page);
    }

    public Integer getSlot() {
        return slot;
    }

    public Integer getPage() {
        return page;
    }
}
