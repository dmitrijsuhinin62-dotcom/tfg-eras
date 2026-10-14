package io.github.dmitrijsuhinin62_dotcom.tfgeras.item;

import io.github.dmitrijsuhinin62_dotcom.tfgeras.Era;
import io.github.dmitrijsuhinin62_dotcom.tfgeras.TFGEras;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class ItemEras {

    private static final Map<Era, TagKey<Item>> ERA_TAGS = createEraTags();

    private ItemEras() {}

    public static Optional<Era> of(ItemStack stack) {
        Era[] eras = Era.values();
        for (int i = eras.length - 1; i >= 0; i--) {
            if (stack.is(ERA_TAGS.get(eras[i]))) {
                return Optional.of(eras[i]);
            }
        }
        return Optional.empty();
    }

    public static boolean isLocked(Player player, ItemStack stack, Era teamEra) {
        return !player.isCreative()
                && !player.isSpectator()
                && of(stack).filter(itemEra -> !teamEra.includes(itemEra)).isPresent();
    }

    private static Map<Era, TagKey<Item>> createEraTags() {
        Map<Era, TagKey<Item>> tags = new EnumMap<>(Era.class);
        for (Era era : Era.values()) {
            tags.put(era, TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(TFGEras.MOD_ID, "era/" + era.id())));
        }
        return tags;
    }
}
