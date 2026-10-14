package io.github.dmitrijsuhinin62_dotcom.tfgeras.network;

import io.github.dmitrijsuhinin62_dotcom.tfgeras.Era;
import io.github.dmitrijsuhinin62_dotcom.tfgeras.client.ClientEra;
import io.github.dmitrijsuhinin62_dotcom.tfgeras.client.emi.TFGErasEmiPlugin;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public record EraSyncPacket(Era era) {

    public static EraSyncPacket decode(FriendlyByteBuf buf) {
        return new EraSyncPacket(buf.readEnum(Era.class));
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeEnum(era);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        if (ClientEra.set(era)) {
            TFGErasEmiPlugin.reload();
        }
    }
}
