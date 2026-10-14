package io.github.dmitrijsuhinin62_dotcom.tfgeras.team;

import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.Team;
import io.github.dmitrijsuhinin62_dotcom.tfgeras.Era;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.TickEvent;

public final class IngotEraTracker {

    private static final int CHECK_INTERVAL_TICKS = 20;
    private static final Map<Era, List<TagKey<Item>>> INGOT_TAGS = createIngotTags();

    private IngotEraTracker() {}

    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END
                || !(event.player instanceof ServerPlayer player)
                || player.tickCount % CHECK_INTERVAL_TICKS != 0) {
            return;
        }

        Team team = FTBTeamsAPI.api().getManager().getTeamForPlayer(player)
                .orElseThrow(() -> new IllegalStateException("У игрока " + player.getGameProfile().getName() + " нет команды FTB Teams"));

        TeamEras.get(team).next()
                .filter(next -> hasAnyIngot(player.getInventory(), next))
                .ifPresent(next -> TeamEras.raise(team, next));
    }

    private static boolean hasAnyIngot(Inventory inventory, Era era) {
        return INGOT_TAGS.get(era).stream().anyMatch(inventory::contains);
    }

    private static Map<Era, List<TagKey<Item>>> createIngotTags() {
        Map<Era, List<TagKey<Item>>> tags = new EnumMap<>(Era.class);
        for (Era era : Era.values()) {
            tags.put(era, era.ingotMetals().stream()
                    .map(metal -> TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "ingots/" + metal)))
                    .toList());
        }
        return tags;
    }
}
