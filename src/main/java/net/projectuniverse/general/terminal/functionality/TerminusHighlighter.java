package net.projectuniverse.general.terminal.functionality;

import net.minestom.server.MinecraftServer;
import net.projectuniverse.general.terminal.ServerTerminal;
import org.jline.reader.Highlighter;
import org.jline.reader.LineReader;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

import java.util.regex.Pattern;

/**
 * The TerminusHighlighter class implements the Highlighter interface to provide highlighting functionality
 * for the Terminus terminal.
 */

public class TerminusHighlighter implements Highlighter {

    @Override
    public void setErrorPattern(Pattern errorPattern) {
    }

    @Override
    public void setErrorIndex(int errorIndex) {
    }

    /**
     * Highlights the given buffer by applying different styles to each word.
     *
     * @param reader the LineReader object
     * @param buffer the buffer to highlight
     * @return the highlighted buffer as an AttributedString
     */
    @Override
    public AttributedString highlight(LineReader reader, String buffer) {
        AttributedStringBuilder builder = new AttributedStringBuilder();
        String[] words = buffer.split(" ");

        for (int index = 0; index < words.length; index++) {
            AttributedStyle style = AttributedStyle.DEFAULT;
            if (index == 0) {
                if (ServerTerminal.SHELL_COMMANDS.contains(words[0]))
                    style = AttributedStyle.DEFAULT.foreground(AttributedStyle.MAGENTA);
                else if (MinecraftServer.getCommandManager().commandExists(words[0]))
                    style = AttributedStyle.DEFAULT.foreground(AttributedStyle.CYAN);
            }
            builder.append(new AttributedString(words[index] + " ", style));
        }

        return builder.toAttributedString();
    }
}
