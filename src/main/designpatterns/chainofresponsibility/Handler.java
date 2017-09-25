package main.designpatterns.chainofresponsibility;

/**
 * Created by miha.novak on 25/09/2017.
 */
public interface Handler {

    void setNextHandler(Handler nextHandler);

    void doSomethingOnCarPart(String mechanicAction, String carPart);
}
