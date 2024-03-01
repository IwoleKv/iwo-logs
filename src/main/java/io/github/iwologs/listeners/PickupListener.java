package io.github.iwologs.listeners;

import io.github.iwologs.options.Strings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.ChatColor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PickupListener implements Listener {

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();
        ItemMeta meta = item.getItemMeta();
        Player player = event.getPlayer();

        String Prefix = Strings.PREFIX;
        String world = player.getWorld().getName();
        int x = (int) player.getLocation().getX();
        int y = (int) player.getLocation().getY();
        int z = (int) player.getLocation().getZ();

        if (meta != null) {
            String name = meta.getDisplayName();
            String nametype = meta.getDisplayName();

            if (name == null || name.isEmpty()) {
                name = item.getType().name();
            }

            String enchantments = item.getEnchantments().isEmpty() ? "" : item.getEnchantments().toString();
            Pattern pattern = Pattern.compile("Enchantment\\[minecraft:(.*?),.*?\\]=(\\d+)");
            Matcher matcher = pattern.matcher(enchantments);

            StringBuilder formattedEnchantments = new StringBuilder();
            String itemTypeName = item.getType().name();

            while (matcher.find()) {
                String enchantmentName = matcher.group(1);
                String enchantmentLevel = matcher.group(2);
                String formattedEnchantment = enchantmentName.substring(0, 1).toUpperCase() + enchantmentName.substring(1) + " " + enchantmentLevel;
                formattedEnchantments.append(formattedEnchantment).append(", ");
            }

            if (formattedEnchantments.length() > 0) {
                formattedEnchantments.setLength(formattedEnchantments.length() - 2);
            }

            String lore = meta.hasLore() ? String.join(", ", meta.getLore()) : "";
            lore = lore.replaceAll("§.", "");
            name = name.replaceAll("§.", "");

            String formattedEnchantmentsString = formattedEnchantments.toString();
            formattedEnchantmentsString = formattedEnchantmentsString.replace("§", "");

            String message = "\n&f" +
                    "&8&m--------" + "&x&f&f&9&c&e&e&lIwoLogs" + "&8&m--------" + "\n&f" +
                    "\n &8» &x&f&f&9&c&e&eGRACZ: &8[&f" + player.getName() + "&8]\n" +
                    (nametype == null || nametype.isEmpty() ? "" : " &8» &x&f&f&9&c&e&eNAZWA: &8[&f" + name + "&8]\n") +
                    (!meta.hasLore() ? "" : " &8» &x&f&f&9&c&e&eOPIS: &8[&f" + lore + "&8]\n") +
                    (item.getEnchantments().isEmpty() ? "" : " &8» &x&f&f&9&c&e&eENCHANT: &8[&f" + formattedEnchantmentsString + "&8]\n") +
                    " &8» &x&f&f&9&c&e&eITEM: &8[&f" + itemTypeName + "&8]\n" +
                    " &8» &x&f&f&9&c&e&eŚWIAT: &8[&f" + world + "&8]\n" +
                    " &8» &x&f&f&9&c&e&eKORDY: &8[&fx: " + x + ", y: " + y + ", z: " + z + "&8]";

            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
    }
}
