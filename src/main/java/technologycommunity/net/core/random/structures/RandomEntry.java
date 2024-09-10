package technologycommunity.net.core.random.structures;

public class RandomEntry<T> {
    final T entry;
    final double percent;

    @Deprecated(forRemoval = true)
    public RandomEntry(T entry, double percent) {
        this.entry = entry;
        this.percent = percent;
    }

    public T getEntry() {
        return entry;
    }

    public double getPercent() {
        return percent;
    }
}
