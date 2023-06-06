package net.projectuniverse.general.database;

import net.minestom.server.entity.Player;
import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogLevel;

import java.sql.ResultSet;
import java.util.Optional;


public class DBHandler {

    protected static final ILogger logger = DBAccessLayer.logger;
    /**
     * Gets ResultSets from sql request
     * --> Gets Information needed from ResultSet and returns it
     * Works with sql return data(ResultSets)
     * */
    protected DBAccessLayer sql;

    public DBHandler(DBAccessLayer sql) {
        this.sql = sql;
    }


    public boolean playerInDatabase(Player player) {
        try {
            ResultSet result = sql.getPlayer(player);
            if(result == null || !result.next()) return false;
            return true;
        } catch(Exception ex) {
            logger.log(LogLevel.ERROR, "Could not execute Player in Database Query", ex);
            return false;
        }
    }

    public boolean playerHasActivePunishment(Player player) {
        try {
            Optional<Integer> playerIdOpt = getPlayerId(player);
            if(playerIdOpt.isEmpty()) return false;
            int playerId = playerIdOpt.get();
            ResultSet result = sql.getPunishment(playerId);
            if(result == null) return false;
            if(! result.next()) return false;
            return true;
        } catch(Exception ex) {
            logger.log(LogLevel.ERROR, "Could not check for active Player Punishment for Player " + player.getUsername(), ex);
            return false;
        }
    }

    public boolean playerHasActivePunishment(String playerName) {
        try {
            Optional<Integer> playerIdOpt = getPlayerId(playerName);
            if(playerIdOpt.isEmpty()) return false;
            int playerId = playerIdOpt.get();
            ResultSet result = sql.getPunishment(playerId);
            if(result == null) return false;
            if(!result.next()) return false;
            return true;
        } catch(Exception ex) {
            logger.log(LogLevel.ERROR, "Could not check for active Player Punishment for Player " + playerName, ex);
            return false;
        }
    }

    public Optional<String> getPunishmentReason(Player player) {
        try {
            Optional<Integer> playerIdOpt = getPlayerId(player);
            if(playerIdOpt.isEmpty()) return Optional.empty();
            int playerId = playerIdOpt.get();
            ResultSet result = sql.getPunishment(playerId);
            if(result == null) return Optional.empty();
            if(! result.next()) return Optional.empty();
            return Optional.ofNullable(result.getString("Reason"));
        } catch(Exception ex) {
            logger.log(LogLevel.ERROR, "Could not get Punishment Reson for Player " + player.getUsername(), ex);
            return Optional.empty();
        }
    }

    public boolean addPlayerPunishmentReason(Player player, String reason, int duration, char durationId) {
        Optional<Integer> playerIdOpt = getPlayerId(player);
        if(playerIdOpt.isEmpty()) return false;
        int playerId = playerIdOpt.get();
        return sql.addPunishmentReason(playerId, reason, duration, durationId);
    }

    public boolean removePlayerPunishment(String playerName) {
        Optional<Integer> playerIdOpt = getPlayerId(playerName);
        if(playerIdOpt.isEmpty()) return false;
        int playerId = playerIdOpt.get();
        return sql.removePunishmentReason(playerId);
    }

    public Optional<Integer> getPlayerId(Player player) {
        try {
            ResultSet result = sql.getPlayer(player);
            if(result == null) return Optional.empty();
            if(!result.next()) return Optional.empty();
            return Optional.ofNullable(result.getInt("PlayerID"));
        } catch(Exception ex) {
            logger.log(LogLevel.ERROR, "Could not get Id from Player " + player.getUsername(), ex);
            return Optional.empty();
        }
    }

    public Optional<Integer> getPlayerId(String playerName) {
        try {
            ResultSet result = sql.getPlayer(playerName);
            if(result == null) return Optional.empty();
            if(!result.next()) return Optional.empty();
            return Optional.ofNullable(result.getInt("PlayerID"));
        } catch(Exception ex) {
            logger.log(LogLevel.ERROR, "Could not get Id from Player " + playerName, ex);
            return Optional.empty();
        }
    }



}
