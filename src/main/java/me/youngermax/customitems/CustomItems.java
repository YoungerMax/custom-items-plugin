package me.youngermax.customitems;

import me.youngermax.customitems.deathscythe.DeathScytheItem;
import me.youngermax.customitems.deathscythe.ScytheTicker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomItems extends JavaPlugin {
    @Override
    public void onEnable() {
        Bukkit.addRecipe(DeathScytheItem.DEATH_SCYTHE_RECIPE);
        getServer().getPluginManager().registerEvents(new DeathScytheItem(), this);
        getServer().getScheduler().runTaskTimer(this, ScytheTicker::tick, 0, 1);
    }

    @Override
    public void onDisable() {

    }
}
