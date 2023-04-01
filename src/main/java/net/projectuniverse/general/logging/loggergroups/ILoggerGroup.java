package net.projectuniverse.general.logging.loggergroups;


import net.projectuniverse.general.logging.ILogger;

import java.util.List;

/**
 * Calls Multiple Loggers
 * @deprecated Combined to: {@link net.projectuniverse.general.logging.loggers.BaseLogger}
 * @since 0.4.7-dev
 * */
public interface ILoggerGroup extends ILogger {

    /**
     * @return All Loggers present in this instance
     * */
    List<ILogger> getLogger();

}
