package main.concurency;

/**
 * Created by miha.novak on 13/07/2017.
 */
public class L2KMain {

    private final static String TAG = L2KMain.class.getName();

    public enum ProjectExample {
        SYNCHRONISED
    }

    public static void main(String[] args) {
        System.out.println("args[0] = " + args[0]);
        switch (args[0]) {
            case "sync":
                new ConcurrencyMain(ProjectExample.SYNCHRONISED);
                break;
        }
    }
}
