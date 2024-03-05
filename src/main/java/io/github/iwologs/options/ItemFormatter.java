package io.github.iwologs.options;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ItemFormatter {

    private static final LegacyComponentSerializer SERIALIZER = LegacyComponentSerializer.legacyAmpersand();
    private static final Pattern ENCHANTMENT_PATTERN = Pattern.compile("Enchantment\\[minecraft:(.*?),.*?]=(\\d+)");

    public static String getNamePlain(ItemMeta meta, ItemStack item) {
        return meta.hasDisplayName() ? SERIALIZER.serialize(meta.displayName())
                .replaceAll("&.", "") : item.getType().name();
    }

    public static String getLorePlain(ItemMeta meta) {
        @Nullable List<Component> loreComponents = meta.lore();
        if (loreComponents == null || loreComponents.isEmpty()) return "";
        return loreComponents.stream()
                .map(SERIALIZER::serialize)
                .collect(Collectors.joining(", "))
                .replaceAll("&.", "");
    }

    public static String getFormattedEnchantments(ItemStack item) {
        if (item.getEnchantments().isEmpty()) return "";
        return item.getEnchantments().keySet().stream()
                .map(enchantment -> {
                    String enchantmentName = enchantment.getKey().getKey();
                    int enchantmentLevel = item.getEnchantments().get(enchantment);
                    return formatEnchantmentName(enchantmentName) + " " + enchantmentLevel;
                })
                .collect(Collectors.joining(", "));
    }

    public static String formatEnchantmentName(String enchantmentName) {
        return ENCHANTMENT_PATTERN.matcher(enchantmentName).replaceAll(matchResult ->
                matchResult.group(1).substring(0, 1).toUpperCase() + matchResult.group(1).substring(1)
        );
    }
    public static Integer getAmount(ItemStack item) {
        return item.getAmount();
    }
}