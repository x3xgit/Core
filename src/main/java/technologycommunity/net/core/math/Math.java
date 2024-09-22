package technologycommunity.net.core.math;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.HashSet;
import java.util.Set;

public class Math {
    public static Set<Block> getBlocksBetween(final Location firstLocation, final Location secondLocation) {
        final Set<Block> blocks = new HashSet<>();

        final World world = firstLocation.getWorld();

        if (world == null) return blocks;

        final int minX = java.lang.Math.min(firstLocation.getBlockX(), secondLocation.getBlockX());
        final int maxX = java.lang.Math.max(firstLocation.getBlockX(), secondLocation.getBlockX());

        final int minY = java.lang.Math.min(firstLocation.getBlockY(), secondLocation.getBlockY());
        final int maxY = java.lang.Math.max(firstLocation.getBlockY(), secondLocation.getBlockY());

        final int minZ = java.lang.Math.min(firstLocation.getBlockZ(), secondLocation.getBlockZ());
        final int maxZ = java.lang.Math.max(firstLocation.getBlockZ(), secondLocation.getBlockZ());

        for (int x = minX; x <= maxX; x++)
            for (int y = minY; y <= maxY; y++)
                for (int z = minZ; z <= maxZ; z++)
                    blocks.add(world.getBlockAt(x, y, z));

        return blocks;
    }
}
