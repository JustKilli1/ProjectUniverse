package net.projectuniverse.general.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.projectuniverse.general.commands.command_executor.DefaultExecutor;
import net.projectuniverse.general.config.configs.MessagesConfig;
import net.projectuniverse.general.config.configs.MessagesParams;
import net.projectuniverse.general.messenger.MessageDesign;
import net.projectuniverse.general.messenger.Messenger;

public class CmdGameMode extends UniverseCommand {
    /**
     * Creates a new instance of the CmdGameMode class.
     * This command is used to change the game mode of a player.
     * The command format is "/gamemode [ModeId]" or "/gm [ModeId]".
     */
    public CmdGameMode() {
        super("gamemode", "Changes the Gamemode of the Player", "gamemode [Mode-Id]", "gm");

        setDefaultExecutor(new DefaultExecutor("/gamemode [ModeId]"));

        var modeIdArg = ArgumentType.Integer("mode-id");

        addSyntax((sender, context) -> {
            if(!(sender instanceof Player)) return;
            Player player = (Player) sender;
            int modeId = context.get(modeIdArg);
            if(modeId < 0 || modeId > GameMode.values().length) {
                Messenger.sendPlayerMessage(player, MessageDesign.SERVER_MESSAGE, MessagesConfig.GAME_MODE_NOT_FOUND
                        .clone()
                        .setConfigParamValue(MessagesParams.GAME_MODE.clone().setValue(String.valueOf(modeId)))
                        .getValue()
                );
                return;
            }
            GameMode gameMode = GameMode.fromId(modeId);
            player.setGameMode(gameMode);
            Messenger.sendPlayerMessage(player, MessageDesign.SERVER_MESSAGE, MessagesConfig.GAME_MODE_CHANGED
                    .clone()
                    .setConfigParamValue(MessagesParams.GAME_MODE.clone().setValue(gameMode.name()))
                    .getValue()
            );
        }, modeIdArg);
    }
}