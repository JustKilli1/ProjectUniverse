package net.projectuniverse.base;

import net.minestom.server.color.Color;

public class ColorDesign {

    private static Color punishmentColor = new Color(250, 20, 30);
    private static Color playerName = new Color(0, 204, 204);
    private static Color highlight = new Color(255, 51, 153);
    private static Color baseText = new Color(240, 240, 240);
    private static Color chatSeparator = new Color(204, 204, 204);

    public static Color getPunishmentColor() {
        return punishmentColor;
    }

    public static void setPunishmentColor(Color punishmentColor) {
        ColorDesign.punishmentColor = punishmentColor;
    }

    public static Color getPlayerName() {
        return playerName;
    }

    public static void setPlayerName(Color playerName) {
        ColorDesign.playerName = playerName;
    }

    public static Color getHighlight() {
        return highlight;
    }

    public static void setHighlight(Color highlight) {
        ColorDesign.highlight = highlight;
    }

    public static Color getBaseText() {
        return baseText;
    }

    public static void setBaseText(Color baseText) {
        ColorDesign.baseText = baseText;
    }

    public static Color getChatSeparator() {
        return chatSeparator;
    }

    public static void setChatSeparator(Color chatSeparator) {
        ColorDesign.chatSeparator = chatSeparator;
    }
}
