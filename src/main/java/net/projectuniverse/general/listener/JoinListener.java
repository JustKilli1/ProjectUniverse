package net.projectuniverse.general.listener;

import net.kyori.adventure.audience.Audience;
import net.minestom.server.MinecraftServer;
import net.minestom.server.adventure.audience.Audiences;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.instance.InstanceContainer;
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
            Server.SERVER_LOGGER.log(LogLevel.INFO, "Player " + player.getUsername() + " connected.");
            if(!dbHandler.playerInDatabase(player)) sql.addPlayer(player);
            Audience allOnlinePlayer = Audiences.all();
            Messenger.sendAudienceMessage(allOnlinePlayer, MessageDesign.PLAYER_MESSAGE, MessagesConfig.PLAYER_JOINED
                    .clone()
                    .setConfigParamValue(MessagesParams.PLAYER.clone().setValue(player.getUsername()))
                    .getValue()
            );
        });
    }


}
