package io.github.dmitrijsuhinin62_dotcom.tfgeras.team;

import dev.ftb.mods.ftbteams.api.event.PlayerJoinedPartyTeamEvent;
import dev.ftb.mods.ftbteams.api.event.PlayerLeftPartyTeamEvent;
import dev.ftb.mods.ftbteams.api.event.PlayerLoggedInAfterTeamEvent;
import dev.ftb.mods.ftbteams.api.event.TeamEvent;
import net.minecraft.server.level.ServerPlayer;

public final class TeamEraEvents {

    private TeamEraEvents() {}

    public static void register() {
        TeamEvent.PLAYER_LOGGED_IN.register(TeamEraEvents::onLoggedIn);
        TeamEvent.PLAYER_JOINED_PARTY.register(TeamEraEvents::onJoinedParty);
        TeamEvent.PLAYER_LEFT_PARTY.register(TeamEraEvents::onLeftParty);
    }

    private static void onLoggedIn(PlayerLoggedInAfterTeamEvent event) {
        TeamEras.apply(event.getPlayer(), TeamEras.get(event.getTeam()));
    }

    private static void onJoinedParty(PlayerJoinedPartyTeamEvent event) {
        TeamEras.raise(event.getTeam(), TeamEras.get(event.getPreviousTeam()));
        TeamEras.apply(event.getPlayer(), TeamEras.get(event.getTeam()));
    }

    /** getPlayer() == null, когда из группы кикнули игрока, который сейчас офлайн: этапы он получит при входе. */
    private static void onLeftParty(PlayerLeftPartyTeamEvent event) {
        TeamEras.raise(event.getPlayerTeam(), TeamEras.get(event.getTeam()));
        ServerPlayer player = event.getPlayer();
        if (player != null) {
            TeamEras.apply(player, TeamEras.get(event.getPlayerTeam()));
        }
    }
}
