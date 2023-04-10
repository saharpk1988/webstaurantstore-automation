package utils;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class CustomLogger {

    private static final Logger LOGGER = Logger.getLogger(CustomLogger.class.getName());
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    static {
        LOGGER.setLevel(Level.ALL);

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        consoleHandler.setFormatter(new CustomFormatter());

        LOGGER.addHandler(consoleHandler);
    }

    public static Logger getLogger() {
        return LOGGER;
    }


    private static class CustomFormatter extends java.util.logging.Formatter {

        @Override
        public String format(LogRecord record) {
            String color = "";
            if (record.getLevel() == Level.SEVERE) {
                color = ANSI_RED;
            } else if (record.getLevel() == Level.WARNING) {
                color = ANSI_YELLOW;
            } else if (record.getLevel() == Level.INFO) {
                color = ANSI_GREEN;
            } else if (record.getLevel() == Level.CONFIG) {
                color = ANSI_CYAN;
            } else if (record.getLevel() == Level.FINE) {
                color = ANSI_BLUE;
            } else if (record.getLevel() == Level.FINER || record.getLevel() == Level.FINEST) {
                color = ANSI_BLACK;
            }
            return color + record.getMessage() + ANSI_RESET + "\n";
        }
    }

}
