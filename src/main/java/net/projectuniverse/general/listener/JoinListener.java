package net.projectuniverse.general.listener;

import net.kyori.adventure.audience.Audience;
import net.minestom.server.MinecraftServer;
import net.minestom.server.adventure.audience.Audiences;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
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

/**
 * The JoinListener class listens for player join events and performs certain actions when a player joins the server.
 */

public class JoinListener {

    private DBAccessLayer sql;
    private DBHandler dbHandler;
    private InstanceContainer spawningInstance;

    /**
     * Initializes a JoinListener with the given parameters.
     * When initialized, it automatically calls the onJoin() method.
     *
     * @param sql               the DBAccessLayer object used for database access
     * @param dbHandler         the DBHandler object used for database handling
     * @param spawningInstance  the InstanceContainer object representing the spawning instance
     */
    public JoinListener(DBAccessLayer sql, DBHandler dbHandler, InstanceContainer spawningInstance) {
        this.sql = sql;
        this.dbHandler = dbHandler;
        this.spawningInstance = spawningInstance;
        onJoin();
    }

    /**
     * Performs actions when a player joins the server.
     * This method is called automatically when a JoinListener is initialized.
     * It handles player login events, sets the spawning instance, respawn point,
     * logs connection message, adds the player to the database if not already present,
     * broadcasts a player joined message, and sets the player's game mode to creative.
     */
    private void onJoin() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(spawningInstance);
            player.setRespawnPoint(new Pos(4, 71, 19));

            Server.SERVER_LOGGER.log(LogLevel.INFO, "Player " + player.getUsername() + " connected.");
            if(!dbHandler.playerInDatabase(player)) sql.addPlayer(player);
            Audience allOnlinePlayer = Audiences.all();
            Messenger.sendAudienceMessage(allOnlinePlayer, MessageDesign.SERVER_MESSAGE, MessagesConfig.PLAYER_JOINED
                    .clone()
                    .setConfigParamValue(MessagesParams.PLAYER.clone().setValue(player.getUsername()))
                    .getValue()
            );
            player.setGameMode(GameMode.CREATIVE);
        });
    }


}
