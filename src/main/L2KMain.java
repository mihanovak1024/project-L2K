package main;

import org.apache.commons.logging.impl.Log4JLogger;

import main.concurency.ConcurrencyMain;

/**
 * Created by miha.novak on 13/07/2017.
 */
public class L2KMain {

    private final static String TAG = L2KMain.class.getName();
    private static Log4JLogger logger = new Log4JLogger(TAG);

    public enum ProjectExample {
        SYNCHRONISED
    }

    public static void main(String[] args) {
        logger.debug("args[0] = " + args[0]);
        switch (args[0]) {
            case "sync":
                new ConcurrencyMain(ProjectExample.SYNCHRONISED);
                break;
        }
    }
}
