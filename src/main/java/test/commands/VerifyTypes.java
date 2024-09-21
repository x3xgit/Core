package test.commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import technologycommunity.net.core.command.CoreCommand;
import technologycommunity.net.core.menu.model.ItemCore;
import technologycommunity.net.core.menu.model.data.DataTypeCreator;
import technologycommunity.net.core.menu.model.data.Validator;

import test.types.SecretTestType;

public class VerifyTypes extends CoreCommand {
    public VerifyTypes() {
        super("verify");

        this.setPermission("permission.qualify.first");
        this.setPermissionMessage("&cCan't really when yo ain't got that damn permission.");
    }

    @Override
    protected boolean onCommand(@NotNull Player executor, @NotNull String label, @NotNull String[] arguments) {
        if (executor.getInventory().getItemInMainHand().getType().isAir()) {
            final ItemStack item = ItemCore.of(Material.DEAD_BUSH)
                    .data(new DataTypeCreator<>(), new SecretTestType())
                    .create();

            executor.getInventory().addItem(item);
        } else {
            final ItemStack item = executor.getInventory().getItemInMainHand();
            final SecretTestType type = new Validator(item).get(new DataTypeCreator<>());

            executor.sendMessage("Verified: " + (type == null ? null : type.getUUID()));
        }

        return false;
    }

    @Override
    protected double getCooldown(@NotNull Player player, @NotNull String[] arguments) {
        if (player.hasPermission("bypass.cooldowns"))
            return 0;

        return 3;
    }
}
