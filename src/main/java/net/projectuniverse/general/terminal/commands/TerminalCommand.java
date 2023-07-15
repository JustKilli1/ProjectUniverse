package net.projectuniverse.general.terminal.commands;

/**
 * Represents a terminal command.
 */

public record TerminalCommand(String name, String description) {
    /**
     * Returns a string representation of this TerminalCommand object.
     *
     * @return the name of the TerminalCommand
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Compares this TerminalCommand object to the specified object for equality.
     *
     * @param o the object to compare to
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        TerminalCommand that = (TerminalCommand) o;
        return that.name.equals(name);
    }
}
