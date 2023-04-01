package net.projectuniverse.general.listener;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.adventure.audience.Audiences;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.instance.InstanceContainer;
import net.projectuniverse.base.ColorDesign;
import net.projectuniverse.general.config.configs.MessagesConfig;
import net.projectuniverse.general.config.configs.MessagesParams;
import net.projectuniverse.general.database.DBAccessLayer;
import net.projectuniverse.general.database.DBHandler;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.messenger.MessageDesign;
import net.projectuniverse.general.messenger.Messenger;
import net.projectuniverse.general.server.Server;

public class JoinListener {

    private DBAccessLayer sql;
    private DBHandler dbHandler;
    private InstanceContainer spawningInstance;

    public JoinListener(DBAccessLayer sql, DBHandler dbHandler, InstanceContainer spawningInstance) {
        this.sql = sql;
        this.dbHandler = dbHandler;
        this.spawningInstance = spawningInstance;
        onJoin();
    }

    private void onJoin() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(spawningInstance);
            player.setRespawnPoint(new Pos(0, 42, 0));

            //Punishment System check
            if(dbHandler.playerHasActivePunishment(player)) {
                String reason = dbHandler.getPunishmentReason(player).orElse("No Reason");
                TextComponent textComponent = Component.text()
                        .content("You were banned from the Server for")
                        .color(TextColor.color(ColorDesign.getPunishmentColor()))
                        .appendNewline()
                        .append(Component.text().content(reason.toString()).color(TextColor.color(ColorDesign.getHighlight())).build())
                        .build();
                player.kick(textComponent);
                Server.SERVER_LOGGER.log(LogLevel.INFO, "Player " + player.getUsername() + " tried to connect while having an Active Punishment.");
                return;
            }

            Server.SERVER_LOGGER.log(LogLevel.INFO, "Player " + player.getUsername() + " connected.");
            if(!dbHandler.playerInDatabase(player)) sql.addPlayer(player);
            Audience allOnlinePlayer = Audiences.all();
            Messenger.sendAudienceMessage(allOnlinePlayer, MessageDesign.SERVER_MESSAGE, MessagesConfig.PLAYER_JOINED
                    .clone()
                    .setConfigParamValue(MessagesParams.PLAYER.clone().setValue(player.getUsername()))
                    .getValue()
            );
        });
    }


}
