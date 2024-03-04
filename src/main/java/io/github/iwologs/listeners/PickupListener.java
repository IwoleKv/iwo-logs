package io.github.iwologs.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PickupListener implements Listener {

    private static final LegacyComponentSerializer SERIALIZER = LegacyComponentSerializer.legacyAmpersand();
    private static final Pattern ENCHANTMENT_PATTERN = Pattern.compile("Enchantment\\[minecraft:(.*?),.*?]=(\\d+)");

    @EventHandler
    public void onPlayerPickupItem(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        ItemStack item = event.getItem().getItemStack();
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        String name = getName(meta, item);
        String lore = getLore(meta);
        String enchantments = getFormattedEnchantments(item);
        String message = buildMessage(player, name, lore, enchantments, item.getType().name(), meta);

        player.sendMessage(SERIALIZER.deserialize(message));
    }

    private String getName(ItemMeta meta, ItemStack item) {
        return meta.hasDisplayName() ? SERIALIZER.serialize(meta.displayName()) : item.getType().name();
    }

    private String getLore(ItemMeta meta) {
        @Nullable List<Component> loreComponents = meta.lore();
        if (loreComponents == null || loreComponents.isEmpty()) return "";
        return loreComponents.stream()
                .map(SERIALIZER::serialize)
                .collect(Collectors.joining(", "))
                .replaceAll("&.", "");
    }

    private String getFormattedEnchantments(ItemStack item) {
        if (item.getEnchantments().isEmpty()) return "";
        return item.getEnchantments().keySet().stream()
                .map(enchantment -> {
                    String enchantmentName = enchantment.getKey().getKey();
                    Integer level = item.getEnchantments().get(enchantment);
                    return formatEnchantmentName(enchantmentName) + " " + level;
                })
                .collect(Collectors.joining(", "));
    }

    private String formatEnchantmentName(String enchantmentName) {
        return ENCHANTMENT_PATTERN.matcher(enchantmentName).replaceAll(matchResult ->
                matchResult.group(1).substring(0, 1).toUpperCase() + matchResult.group(1).substring(1)
        );
    }

    private String buildMessage(Player player, String name, String lore, String enchantments, String itemTypeName, ItemMeta meta) {
        String messageFormat = "\n&f&8&m--------&x&f&f&9&c&e&e&lIwoLogs&8&m--------\n&f\n"
                + " &8» &x&f&f&9&c&e&eGRACZ: &8[&f%s&8]\n%s%s%s &8» &x&f&f&9&c&e&eITEM: &8[&f%s&8]\n"
                + " &8» &x&f&f&9&c&e&eŚWIAT: &8[&f%s&8]\n &8» &x&f&f&9&c&e&eKORDY: &8[&fx: %d, y: %d, z: %d&8]";

        return String.format(messageFormat, player.getName(),
                !meta.hasDisplayName() ? "" : " &8» &x&f&f&9&c&e&eNAZWA: &8[&f" + name + "&8]\n",
                lore.isEmpty() ? "" : " &8» &x&f&f&9&c&e&eOPIS: &8[&f" + lore + "&8]\n",
                enchantments.isEmpty() ? "" : " &8» &x&f&f&9&c&e&eENCHANT: &8[&f" + enchantments + "&8]\n",
                itemTypeName, player.getWorld().getName(),
                (int) player.getLocation().getX(), (int) player.getLocation().getY(), (int) player.getLocation().getZ());
    }
}
