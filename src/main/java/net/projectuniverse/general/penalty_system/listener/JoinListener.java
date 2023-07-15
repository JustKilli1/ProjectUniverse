package net.projectuniverse.general.penalty_system.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.projectuniverse.base.ColorDesign;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.penalty_system.database.DBHPenaltySystem;
import net.projectuniverse.general.server.Server;

/**
 * JoinListener class handles player join events and checks if the player has any active punishments.
 * It utilizes the provided SQL and DBHandler instances for database operations and penalty handling.
 */

public class JoinListener {

    private final DBHPenaltySystem dbHandler;

    /**
     * Constructs a JoinListener object with the specified SQL and DBHandler instances.
     * This class is responsible for handling player join events and checking if the player has any active punishments.
     *
     * @param sql the DBALPenaltySystem instance used for SQL operations
     * @param dbHandler the DBHPenaltySystem instance used for handling penalties
     */
    public JoinListener(DBHPenaltySystem dbHandler) {
        this.dbHandler = dbHandler;
        onJoin();
    }

    /**
     * This method is called when a player joins the server. It checks if the player has any active punishments
     * and kicks them if they do.
     */
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
