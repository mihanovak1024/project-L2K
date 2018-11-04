package main.java.designpatterns.chainofresponsibility;

/**
 * Created by miha.novak on 25/09/2017.
 */
public class Client {

    public static void main(String[] args) {

        Handler chainAction1 = new Engine();
        Handler chainAction2 = new Tyre();
        Handler chainAction3 = new Interior();

        chainAction1.setNextHandler(chainAction2);
        chainAction2.setNextHandler(chainAction3);

        System.out.println("Example 1:");
        String carPart = "Interior";
        String mechanicAction = "CheckCeiling";
        chainAction1.doSomethingOnCarPart(mechanicAction, carPart);

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        System.out.println("\nExample 2:");
        carPart = "Tyre";
        mechanicAction = "MeasurePressure";
        chainAction1.doSomethingOnCarPart(mechanicAction, carPart);

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        System.out.println("\nExample 3:");
        carPart = "Tyre";
        mechanicAction = "CheckPressure";
        chainAction1.doSomethingOnCarPart(mechanicAction, carPart);

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        System.out.println("\nExample 4:");
        carPart = "Engine";
        mechanicAction = "ImprovePower";
        chainAction1.doSomethingOnCarPart(mechanicAction, carPart);
    }
}
