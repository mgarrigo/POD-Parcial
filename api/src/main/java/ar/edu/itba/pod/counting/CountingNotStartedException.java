package ar.edu.itba.pod.counting;

import java.rmi.RemoteException;

public class CountingNotStartedException extends RemoteException {

    private static final long serialVersionUID = 9219304969914760507L;
    private static final String MESSAGE = "Counting not started";

    public CountingNotStartedException() {
        super(MESSAGE);
    }

}
