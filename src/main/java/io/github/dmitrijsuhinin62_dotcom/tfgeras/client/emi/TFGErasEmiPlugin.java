package io.github.dmitrijsuhinin62_dotcom.tfgeras.client.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.runtime.EmiReloadManager;
import io.github.dmitrijsuhinin62_dotcom.tfgeras.Era;
import io.github.dmitrijsuhinin62_dotcom.tfgeras.client.ClientEra;
import io.github.dmitrijsuhinin62_dotcom.tfgeras.item.ItemEras;

@EmiEntrypoint
public final class TFGErasEmiPlugin implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        Era era = ClientEra.get();
        registry.removeEmiStacks(stack -> ItemEras.isAbove(stack.getItemStack(), era));
    }

    /**
     * EmiReloadManager — внутренний класс EMI (пакет runtime, не api): в EMI 1.1.24 нет публичного способа пересобрать индекс.
     * Выбрано осознанно, чтобы новая эпоха появлялась в EMI сразу, а не после перезахода или F3+T. При обновлении EMI проверить первым делом.
     */
    public static void reload() {
        EmiReloadManager.reload();
    }
}
