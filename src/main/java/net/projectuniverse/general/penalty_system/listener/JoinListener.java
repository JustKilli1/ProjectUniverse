package net.projectuniverse.general.penalty_system.listener;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.projectuniverse.base.ColorDesign;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.penalty_system.database.DBALPenaltySystem;
import net.projectuniverse.general.penalty_system.database.DBHPenaltySystem;
import net.projectuniverse.general.server.Server;

public class JoinListener {

    private final DBALPenaltySystem sql;
    private final DBHPenaltySystem dbHandler;

    public JoinListener(DBALPenaltySystem sql, DBHPenaltySystem dbHandler) {
        this.sql = sql;
        this.dbHandler = dbHandler;
        onJoin();
    }

    private void onJoin() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
            final Player player = event.getPlayer();
            boolean results = dbHandler.playerHasActivePunishment(player);
            if(!results) return;

            String reason = dbHandler.getPunishmentReason(player).orElse("No Reason");
            TextComponent textComponent = Component.text()
                    .content("You were banned from the Server for")
                    .color(TextColor.color(ColorDesign.getPunishmentColor()))
                    .appendNewline()
                    .append(Component.text().content(reason).color(TextColor.color(ColorDesign.getHighlight())).build())
                    .build();
            player.kick(textComponent);
            Server.SERVER_LOGGER.log(LogLevel.INFO, "Player " + player.getUsername() + " tried to connect while having an Active Punishment.");
        });
    }

}
