package io.github.dmitrijsuhinin62_dotcom.tfgeras.item;

import io.github.dmitrijsuhinin62_dotcom.tfgeras.Era;
import io.github.dmitrijsuhinin62_dotcom.tfgeras.team.TeamEras;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;

public final class EquipmentLock {

    private EquipmentLock() {}

    /** Фаза START: Player.tick засчитывает надетое (detectEquipmentUpdates → атрибуты) уже после неё, так что атрибуты неопознанного предмета не применятся ни на тик. */
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START || !(event.player instanceof ServerPlayer player)) {
            return;
        }

        Era teamEra = TeamEras.get(TeamEras.teamOf(player));
        boolean dropped = false;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = player.getItemBySlot(slot);
            if (ItemEras.isLocked(player, stack, teamEra)) {
                player.setItemSlot(slot, ItemStack.EMPTY);
                player.drop(stack, true);
                dropped = true;
            }
        }

        if (dropped) {
            player.displayClientMessage(Component.translatable("tfgeras.message.unidentified_item"), true);
        }
    }
}
