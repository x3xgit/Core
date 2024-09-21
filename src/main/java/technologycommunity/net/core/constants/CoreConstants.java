package technologycommunity.net.core.constants;

import technologycommunity.net.core.color.Corelor;
import technologycommunity.net.core.plugin.Core;

public class CoreConstants {
    public static final class NBT {

        public static final String TAG_MENU_CURRENT = Core.getNamed() + "_Menu";
    }

    public static final class SKULLS {
        public static final String skullNextArrowAction = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19";
        public static final String skullPreviousArrowAction = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==";
    }

    public static final class COLORS {
        public static final Corelor defaultColor = Corelor.GRAY;
    }
}
