package ar.edu.itba.pod.counting;

import java.rmi.RemoteException;

public class CountingAlreadyStartedException extends RemoteException {

    private static final long serialVersionUID = -7565098189609254760L;
    private static final String MESSAGE = "Counting already started.";

    public CountingAlreadyStartedException() {
        super(MESSAGE);
    }
}
