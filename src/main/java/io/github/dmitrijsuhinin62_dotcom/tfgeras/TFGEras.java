package io.github.dmitrijsuhinin62_dotcom.tfgeras;

import io.github.dmitrijsuhinin62_dotcom.tfgeras.network.EraNetwork;
import io.github.dmitrijsuhinin62_dotcom.tfgeras.team.IngotEraTracker;
import io.github.dmitrijsuhinin62_dotcom.tfgeras.team.TeamEraEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(TFGEras.MOD_ID)
public final class TFGEras {

    public static final String MOD_ID = "tfgeras";

    public TFGEras() {
        EraNetwork.register();
        TeamEraEvents.register();
        MinecraftForge.EVENT_BUS.addListener(IngotEraTracker::onPlayerTick);
    }
}
