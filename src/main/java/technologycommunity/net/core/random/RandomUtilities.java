package technologycommunity.net.core.random;

import java.util.List;
import java.util.Random;

public class RandomUtilities {
    public static <T> T randomFromList(List<T> listOfObjects) {
        return listOfObjects.get(randomNumber(listOfObjects.size() - 1));
    }

    @SafeVarargs
    public static <T> T random(T... objects) {
        return objects[randomNumber(objects.length - 1)];
    }

    private static int randomNumber(Integer maxIndex) {
        return new Random().nextInt(maxIndex);
    }
}
