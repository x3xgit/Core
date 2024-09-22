package technologycommunity.net.core;

import org.jetbrains.annotations.Nullable;

import technologycommunity.net.core.exception.CoreException;

public class Validator {
    public static boolean checkNotNull(final @Nullable Object object, final boolean ex) {
        if (object == null)
            if (ex) throw new CoreException("Generated CoreException, checked object is null.");
            else return false;

        return true;
    }

    public static boolean checkNotNull(final @Nullable Object object) {
        return checkNotNull(object, false);
    }

    public static boolean checkNull(final @Nullable Object object) {
        return (object == null);
    }
}
