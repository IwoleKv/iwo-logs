package io.github.iwologs.loggers;

import io.github.iwologs.options.*;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class PickupLogger implements Listener {

    private static final LegacyComponentSerializer SERIALIZER = LegacyComponentSerializer.legacyAmpersand();

    @EventHandler
    public void onPlayerPickupItem(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        ItemStack item = event.getItem().getItemStack();
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        String name = ItemFormatter.getNamePlain(meta, item);
        String lore = ItemFormatter.getLorePlain(meta);
        String enchantments = ItemFormatter.getFormattedEnchantments(item);
        String message = buildMessage(player, name, lore, enchantments, item.getType().name(), meta, item);

        player.sendMessage(SERIALIZER.deserialize(message));
    }
    private String buildMessage(Player player, String name, String lore, String enchantments, String itemTypeName, ItemMeta meta, ItemStack item) {
        String messageFormat = """

                &f&8&m--------&x&f&f&9&c&e&e&lIwoLogs&8&m--------
                &f
                 &8» &x&f&f&9&c&e&eGRACZ: &8[&f%s&8]
                %s%s%s &8» &x&f&f&9&c&e&eITEM: &8[&f%s&8]
                %s &8» &x&f&f&9&c&e&eŚWIAT: &8[&f%s&8]
                 &8» &x&f&f&9&c&e&eKORDY: &8[&fx: %d, y: %d, z: %d&8]""";

        return String.format(messageFormat, player.getName(),
                !meta.hasDisplayName() ? "" : " &8» &x&f&f&9&c&e&eNAZWA: &8[&f" + name + "&8]\n",
                lore.isEmpty() ? "" : " &8» &x&f&f&9&c&e&eOPIS: &8[&f" + lore + "&8]\n",
                enchantments.isEmpty() ? "" : " &8» &x&f&f&9&c&e&eENCHANT: &8[&f" + enchantments + "&8]\n",
                itemTypeName, ItemFormatter.getAmount(item) <= 1 ? "" : " &8» &x&f&f&9&c&e&eILOŚĆ: &8[&f" + ItemFormatter.getAmount(item) + "&8]\n", player.getWorld().getName(),
                (int) player.getLocation().getX(), (int) player.getLocation().getY(), (int) player.getLocation().getZ());
    }
}
