package me.youngermax.customitems.deathscythe;

import me.youngermax.customitems.CustomItems;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.recipe.CraftingBookCategory;
import org.bukkit.plugin.java.JavaPlugin;

public class DeathScytheItem implements Listener {
    public static final NamespacedKey DEATH_SCYTHE_KEY = new NamespacedKey(JavaPlugin.getPlugin(CustomItems.class), "death_scythe");
    public static final ItemStack DEATH_SCYTHE = new ItemStack(Material.NETHERITE_HOE);
    public static final ShapedRecipe DEATH_SCYTHE_RECIPE;

    static {
        ItemMeta itemMeta = DEATH_SCYTHE.getItemMeta();
        itemMeta.setCustomModelData(45232); // Some random ID
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Death Scythe");
        DEATH_SCYTHE.setItemMeta(itemMeta);

        // Initialize recipe
        DEATH_SCYTHE_RECIPE = new ShapedRecipe(DEATH_SCYTHE_KEY, DEATH_SCYTHE)
                .shape( "12",
                        "A3",
                        "A4")
                .setIngredient('1', Material.GOLDEN_APPLE)
                .setIngredient('2', Material.GOLD_BLOCK)
                .setIngredient('3', Material.NETHERITE_HOE)
                .setIngredient('4', Material.DIAMOND)
                .setIngredient('A', Material.AIR);

        DEATH_SCYTHE_RECIPE.setCategory(CraftingBookCategory.EQUIPMENT);
    }

    @EventHandler
    public void onUseItem(PlayerAnimationEvent event) {
        if (event.getAnimationType() == PlayerAnimationType.ARM_SWING && isDeathScythe(event.getPlayer().getInventory().getItemInMainHand())) {
            useDeathScythe(event.getPlayer());
        }
    }

    public void useDeathScythe(Player player) {
        ScytheTicker.submit(player, new Scythe(
                player,
                2,
                player.getLocation().getYaw(),
                player.getLocation().getPitch(),
                player.getLocation().add(0, Scythe.BASE_RADIUS, 0)
        ));
    }

    public boolean isDeathScythe(ItemStack stack) {
        if (stack == null || stack.getItemMeta() == null || !stack.getItemMeta().hasCustomModelData()) return false;

        return stack.getItemMeta().getCustomModelData() == DEATH_SCYTHE.getItemMeta().getCustomModelData() &&
                stack.getType() == DEATH_SCYTHE.getType();
    }
}
