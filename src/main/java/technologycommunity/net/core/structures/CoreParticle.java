package technologycommunity.net.core.structures;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

public enum CoreParticle {
    /*
        All working particles from 1.20
     */

    EXPLOSION_NORMAL(Particle.EXPLOSION_NORMAL),
    EXPLOSION_LARGE(Particle.EXPLOSION_LARGE),
    EXPLOSION_HUGE(Particle.EXPLOSION_HUGE),
    FIREWORKS_SPARK(Particle.FIREWORKS_SPARK),
    WATER_BUBBLE(Particle.WATER_BUBBLE),
    WATER_SPLASH(Particle.WATER_SPLASH),
    WATER_WAKE(Particle.WATER_WAKE),
    SUSPENDED(Particle.SUSPENDED),
    SUSPENDED_DEPTH(Particle.SUSPENDED_DEPTH),
    CRIT(Particle.CRIT),
    CRIT_MAGIC(Particle.CRIT_MAGIC),
    SMOKE_NORMAL(Particle.SMOKE_NORMAL),
    SMOKE_LARGE(Particle.SMOKE_LARGE),
    SPELL(Particle.SPELL),
    SPELL_INSTANT(Particle.SPELL_INSTANT),
    SPELL_MOB(Particle.SPELL_MOB),
    SPELL_MOB_AMBIENT(Particle.SPELL_MOB_AMBIENT),
    SPELL_WITCH(Particle.SPELL_WITCH),
    DRIP_WATER(Particle.DRIP_WATER),
    DRIP_LAVA(Particle.DRIP_LAVA),
    VILLAGER_ANGRY(Particle.VILLAGER_ANGRY),
    VILLAGER_HAPPY(Particle.VILLAGER_HAPPY),
    TOWN_AURA(Particle.TOWN_AURA),
    NOTE(Particle.NOTE),
    PORTAL(Particle.PORTAL),
    ENCHANTMENT_TABLE(Particle.ENCHANTMENT_TABLE),
    FLAME(Particle.FLAME),
    LAVA(Particle.LAVA),
    CLOUD(Particle.CLOUD),
    SNOWBALL(Particle.SNOWBALL),
    SNOW_SHOVEL(Particle.SNOW_SHOVEL),
    SLIME(Particle.SLIME),
    HEART(Particle.HEART),
    WATER_DROP(Particle.WATER_DROP),
    MOB_APPEARANCE(Particle.MOB_APPEARANCE),
    DRAGON_BREATH(Particle.DRAGON_BREATH),
    END_ROD(Particle.END_ROD),
    DAMAGE_INDICATOR(Particle.DAMAGE_INDICATOR),
    SWEEP_ATTACK(Particle.SWEEP_ATTACK),
    TOTEM(Particle.TOTEM),
    SPIT(Particle.SPIT),
    SQUID_INK(Particle.SQUID_INK),
    BUBBLE_POP(Particle.BUBBLE_POP),
    CURRENT_DOWN(Particle.CURRENT_DOWN),
    BUBBLE_COLUMN_UP(Particle.BUBBLE_COLUMN_UP),
    NAUTILUS(Particle.NAUTILUS),
    DOLPHIN(Particle.DOLPHIN),
    SNEEZE(Particle.SNEEZE),
    CAMPFIRE_COSY_SMOKE(Particle.CAMPFIRE_COSY_SMOKE),
    CAMPFIRE_SIGNAL_SMOKE(Particle.CAMPFIRE_SIGNAL_SMOKE),
    COMPOSTER(Particle.COMPOSTER),
    FLASH(Particle.FLASH),
    FALLING_LAVA(Particle.FALLING_LAVA),
    LANDING_LAVA(Particle.LANDING_LAVA),
    FALLING_WATER(Particle.FALLING_WATER),
    DRIPPING_HONEY(Particle.DRIPPING_HONEY),
    FALLING_HONEY(Particle.FALLING_HONEY),
    LANDING_HONEY(Particle.LANDING_HONEY),
    FALLING_NECTAR(Particle.FALLING_NECTAR),
    SOUL_FIRE_FLAME(Particle.SOUL_FIRE_FLAME),
    ASH(Particle.ASH),
    CRIMSON_SPORE(Particle.CRIMSON_SPORE),
    WARPED_SPORE(Particle.WARPED_SPORE),
    SOUL(Particle.SOUL),
    DRIPPING_OBSIDIAN_TEAR(Particle.DRIPPING_OBSIDIAN_TEAR),
    FALLING_OBSIDIAN_TEAR(Particle.FALLING_OBSIDIAN_TEAR),
    LANDING_OBSIDIAN_TEAR(Particle.LANDING_OBSIDIAN_TEAR),
    REVERSE_PORTAL(Particle.REVERSE_PORTAL),
    WHITE_ASH(Particle.WHITE_ASH),
    FALLING_SPORE_BLOSSOM(Particle.FALLING_SPORE_BLOSSOM),
    SPORE_BLOSSOM_AIR(Particle.SPORE_BLOSSOM_AIR),
    SMALL_FLAME(Particle.SMALL_FLAME),
    SNOWFLAKE(Particle.SNOWFLAKE),
    DRIPPING_DRIPSTONE_LAVA(Particle.DRIPPING_DRIPSTONE_LAVA),
    FALLING_DRIPSTONE_LAVA(Particle.FALLING_DRIPSTONE_LAVA),
    DRIPPING_DRIPSTONE_WATER(Particle.DRIPPING_DRIPSTONE_WATER),
    FALLING_DRIPSTONE_WATER(Particle.FALLING_DRIPSTONE_WATER),
    GLOW_SQUID_INK(Particle.GLOW_SQUID_INK),
    GLOW(Particle.GLOW),
    WAX_ON(Particle.WAX_ON),
    WAX_OFF(Particle.WAX_OFF),
    ELECTRIC_SPARK(Particle.ELECTRIC_SPARK),
    SCRAPE(Particle.SCRAPE),
    SONIC_BOOM(Particle.SONIC_BOOM),
    SCULK_SOUL(Particle.SCULK_SOUL),
    SCULK_CHARGE_POP(Particle.SCULK_CHARGE_POP),
    CHERRY_LEAVES(Particle.CHERRY_LEAVES),
    EGG_CRACK(Particle.EGG_CRACK);

    private final Particle particle;

    CoreParticle(final Particle particle) {
        this.particle = particle;
    }

    public Particle toBukkit() {
        return particle;
    }

    public void draw(final Location location, final Integer amount) {
        final World world = location.getWorld();

        if (world == null)
            return;

        world.spawnParticle(this.particle, location, amount);
    }
}
