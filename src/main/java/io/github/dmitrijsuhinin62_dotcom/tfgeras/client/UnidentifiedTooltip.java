package io.github.dmitrijsuhinin62_dotcom.tfgeras.client;

import io.github.dmitrijsuhinin62_dotcom.tfgeras.item.ItemEras;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public final class UnidentifiedTooltip {

    private UnidentifiedTooltip() {}

    /** getEntity() == null, когда Forge на старте строит поисковые деревья тултипов без игрока. */
    public static void onTooltip(ItemTooltipEvent event) {
        Player player = event.getEntity();
        if (player == null || !ItemEras.isLocked(player, event.getItemStack(), ClientEra.get())) {
            return;
        }

        List<Component> tooltip = event.getToolTip();
        tooltip.clear();
        tooltip.add(Component.translatable("tfgeras.tooltip.unidentified_item"));
    }
}
