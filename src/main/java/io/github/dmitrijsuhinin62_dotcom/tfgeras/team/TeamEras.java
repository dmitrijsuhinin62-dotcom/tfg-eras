package io.github.dmitrijsuhinin62_dotcom.tfgeras.team;

import com.mojang.logging.LogUtils;
import dev.ftb.mods.ftbteams.api.Team;
import dev.latvian.mods.kubejs.stages.Stages;
import io.github.dmitrijsuhinin62_dotcom.tfgeras.Era;
import io.github.dmitrijsuhinin62_dotcom.tfgeras.TFGEras;
import io.github.dmitrijsuhinin62_dotcom.tfgeras.network.EraNetwork;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;

public final class TeamEras {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String ERA_KEY = TFGEras.MOD_ID + ":era";

    private TeamEras() {}

    public static Era get(Team team) {
        CompoundTag data = team.getExtraData();
        return data.contains(ERA_KEY) ? Era.valueOf(data.getString(ERA_KEY)) : Era.STONE;
    }

    public static void raise(Team team, Era candidate) {
        Era current = get(team);
        if (current.includes(candidate)) {
            return;
        }

        team.getExtraData().putString(ERA_KEY, candidate.name());
        team.markDirty();
        LOGGER.info("Команда {} ({}) перешла из эпохи {} в {}", team.getShortName(), team.getId(), current.id(), candidate.id());

        for (ServerPlayer player : team.getOnlineMembers()) {
            apply(player, candidate);
        }
    }

    public static void apply(ServerPlayer player, Era era) {
        Stages stages = Stages.get(player);
        for (Era stageEra : Era.values()) {
            stages.set(stageEra.stage(), era.includes(stageEra));
        }
        EraNetwork.sendTo(player, era);
    }
}
