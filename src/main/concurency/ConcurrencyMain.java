package main.concurency;

import org.apache.commons.logging.impl.Log4JLogger;

import main.L2KMain.ProjectExample;
import main.concurency.synchronised.SynchronisedMain;

/**
 * Created by miha.novak on 13/07/2017.
 */
public class ConcurrencyMain {

    private final static String TAG = ConcurrencyMain.class.getName();
    private static Log4JLogger logger = new Log4JLogger(TAG);

    public ConcurrencyMain(ProjectExample projectExample) {
        switch (projectExample){
            case SYNCHRONISED:
                new SynchronisedMain();
                break;
        }
    }
}
