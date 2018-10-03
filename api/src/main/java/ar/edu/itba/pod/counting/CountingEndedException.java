package ar.edu.itba.pod.counting;

import java.rmi.RemoteException;

public class CountingEndedException extends RemoteException {

    private static final long serialVersionUID = -4000039342502124844L;
    private static final String MESSAGE = "Counting ended";

    public CountingEndedException() {
        super(MESSAGE);
    }

}
