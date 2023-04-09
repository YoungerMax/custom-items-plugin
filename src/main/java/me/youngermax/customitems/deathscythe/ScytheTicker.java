package me.youngermax.customitems.deathscythe;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.*;

public class ScytheTicker implements Listener {
    public static final long COOLDOWN_MILLIS = 1500;

    private static final Map<Player, Long> lastSubmissionMap = new HashMap<>();
    private static final List<Scythe> scythes = new ArrayList<>();

    public static void submit(Player player, Scythe scythe) {
        long msSinceLastSubmission = System.currentTimeMillis() - lastSubmissionMap.getOrDefault(player, 0L);

        if (msSinceLastSubmission > COOLDOWN_MILLIS) {
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_SHOOT, 1f, 0.5f);
            lastSubmissionMap.put(player, System.currentTimeMillis());
            scythes.add(scythe);
        } else {
            float cooldownSecsLeft = (COOLDOWN_MILLIS - msSinceLastSubmission) / 1000f;

            BaseComponent[] msg = new ComponentBuilder()
                    .append(Float.toString(cooldownSecsLeft))
                            .color(ChatColor.AQUA)
                    .append("s")
                            .color(ChatColor.AQUA)
                    .append(" cooldown")
                            .color(ChatColor.RESET)
                    .create();

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, msg);
        }
    }

    public static void tick() {
        Iterator<Scythe> scytheIterator = scythes.iterator();

        while (scytheIterator.hasNext()) {
            Scythe scythe = scytheIterator.next();
            scythe.tick();

            if (scythe.age > Scythe.MAX_AGE) scytheIterator.remove();
        }
    }
}
