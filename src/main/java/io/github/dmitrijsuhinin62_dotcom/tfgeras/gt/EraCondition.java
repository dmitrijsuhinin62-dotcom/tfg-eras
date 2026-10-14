package io.github.dmitrijsuhinin62_dotcom.tfgeras.gt;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeCondition;
import com.gregtechceu.gtceu.api.recipe.condition.RecipeConditionType;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import io.github.dmitrijsuhinin62_dotcom.tfgeras.Era;
import io.github.dmitrijsuhinin62_dotcom.tfgeras.team.TeamEras;
import java.util.Arrays;
import java.util.UUID;
import net.minecraft.network.chat.Component;

public class EraCondition extends RecipeCondition<EraCondition> {

    public static final String TYPE_ID = "tfgeras_era";

    private static final Codec<Era> ERA_CODEC = Codec.STRING.comapFlatMap(EraCondition::parseEra, Era::id);

    public static final Codec<EraCondition> CODEC = RecordCodecBuilder.create(instance -> RecipeCondition.isReverse(instance)
            .and(ERA_CODEC.fieldOf("era").forGetter(condition -> condition.era))
            .apply(instance, EraCondition::new));

    public static final RecipeConditionType<EraCondition> TYPE = new RecipeConditionType<>(EraCondition::new, CODEC);

    private Era era;

    public EraCondition() {
        this(Era.STONE);
    }

    public EraCondition(Era era) {
        this(false, era);
    }

    public EraCondition(boolean isReverse, Era era) {
        super(isReverse);
        this.era = era;
    }

    public static void register(GTCEuAPI.RegisterEvent<String, RecipeConditionType<?>> event) {
        event.register(TYPE_ID, TYPE);
    }

    @Override
    public RecipeConditionType<EraCondition> getType() {
        return TYPE;
    }

    @Override
    public Component getTooltips() {
        return Component.translatable("tfgeras.recipe.condition.era", Component.translatable(era.translationKey()));
    }

    /** Машина без владельца (поставлена не игроком) рецепты с эпохой не запускает. */
    @Override
    protected boolean testCondition(GTRecipe recipe, RecipeLogic recipeLogic) {
        UUID ownerId = recipeLogic.machine.self().getOwnerUUID();
        return ownerId != null && FTBTeamsAPI.api().getManager().getTeamForPlayerID(ownerId)
                .map(team -> TeamEras.get(team).includes(era))
                .orElse(false);
    }

    @Override
    public EraCondition createTemplate() {
        return new EraCondition();
    }

    private static DataResult<Era> parseEra(String id) {
        return Arrays.stream(Era.values())
                .filter(era -> era.id().equals(id))
                .findFirst()
                .map(DataResult::success)
                .orElseGet(() -> DataResult.error(() -> "Неизвестная эпоха: " + id));
    }
}
