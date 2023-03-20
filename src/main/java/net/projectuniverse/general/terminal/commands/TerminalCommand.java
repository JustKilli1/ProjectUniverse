package net.projectuniverse.general.terminal.commands;

import java.util.Objects;

public record TerminalCommand(String name, String description) {
    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        TerminalCommand that = (TerminalCommand) o;
        return that.name.equals(name);
    }
}
