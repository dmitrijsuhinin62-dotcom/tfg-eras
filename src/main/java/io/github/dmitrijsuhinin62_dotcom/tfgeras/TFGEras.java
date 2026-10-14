package io.github.dmitrijsuhinin62_dotcom.tfgeras;

import com.gregtechceu.gtceu.api.recipe.condition.RecipeConditionType;
import io.github.dmitrijsuhinin62_dotcom.tfgeras.client.UnidentifiedTooltip;
import io.github.dmitrijsuhinin62_dotcom.tfgeras.gt.EraCondition;
import io.github.dmitrijsuhinin62_dotcom.tfgeras.item.EquipmentLock;
import io.github.dmitrijsuhinin62_dotcom.tfgeras.network.EraNetwork;
import io.github.dmitrijsuhinin62_dotcom.tfgeras.team.IngotEraTracker;
import io.github.dmitrijsuhinin62_dotcom.tfgeras.team.TeamEraEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TFGEras.MOD_ID)
public final class TFGEras {

    public static final String MOD_ID = "tfgeras";

    public TFGEras(FMLJavaModLoadingContext context) {
        context.getModEventBus().addGenericListener(RecipeConditionType.class, EraCondition::register);

        EraNetwork.register();
        TeamEraEvents.register();
        MinecraftForge.EVENT_BUS.addListener(IngotEraTracker::onPlayerTick);
        MinecraftForge.EVENT_BUS.addListener(EquipmentLock::onPlayerTick);
        MinecraftForge.EVENT_BUS.addListener(UnidentifiedTooltip::onTooltip);
    }
}
