package io.github.dmitrijsuhinin62_dotcom.tfgeras.mixin;

import io.github.dmitrijsuhinin62_dotcom.tfgeras.item.ItemEras;
import io.github.dmitrijsuhinin62_dotcom.tfgeras.team.TeamEras;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(CraftingMenu.class)
public abstract class CraftingMenuMixin {

    /** Подменяем результат до result.setItem: пустой стек уходит и в слот результата, и клиенту пакетом. Метод общий для верстака и инвентаря 2×2. */
    @ModifyVariable(
            method = "slotChangedCraftingGrid",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/ResultContainer;setItem(ILnet/minecraft/world/item/ItemStack;)V"),
            ordinal = 0)
    private static ItemStack tfgeras$hideUnidentifiedResult(ItemStack result, AbstractContainerMenu menu, Level level, Player player) {
        if (player instanceof ServerPlayer serverPlayer
                && ItemEras.isLocked(serverPlayer, result, TeamEras.get(TeamEras.teamOf(serverPlayer)))) {
            return ItemStack.EMPTY;
        }
        return result;
    }
}
