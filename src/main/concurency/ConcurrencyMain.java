package main.concurency;

import main.concurency.L2KMain.ProjectExample;
import main.concurency.synchronised.SynchronisedMain;

/**
 * Created by miha.novak on 13/07/2017.
 */
public class ConcurrencyMain {

    private final static String TAG = ConcurrencyMain.class.getName();

    public ConcurrencyMain(ProjectExample projectExample) {
        switch (projectExample) {
            case SYNCHRONISED:
                new SynchronisedMain();
                break;
        }
    }
}
