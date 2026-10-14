package io.github.dmitrijsuhinin62_dotcom.tfgeras.network;

import io.github.dmitrijsuhinin62_dotcom.tfgeras.Era;
import io.github.dmitrijsuhinin62_dotcom.tfgeras.TFGEras;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public final class EraNetwork {

    private static final String PROTOCOL_VERSION = "1";
    private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(TFGEras.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals);

    private EraNetwork() {}

    public static void register() {
        CHANNEL.messageBuilder(EraSyncPacket.class, 0, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(EraSyncPacket::encode)
                .decoder(EraSyncPacket::decode)
                .consumerMainThread(EraSyncPacket::handle)
                .add();
    }

    public static void sendTo(ServerPlayer player, Era era) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new EraSyncPacket(era));
    }
}
