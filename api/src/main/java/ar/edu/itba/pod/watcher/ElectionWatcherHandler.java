package ar.edu.itba.pod.watcher;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ar.edu.itba.pod.ElectionResult;

public interface ElectionWatcherHandler extends Remote {

    /**
     * Notifica que empezó el conteo de votos de la elección y la cantidad total
     * de fiscales que están supervisando el conteo.
     */
    void electionCountingStarted(int electionWatcherCount) throws RemoteException;

    /**
     * Notifica ante un cambio en la cantidad de votos del partido, informando
     * la cantidad de votos hasta el momento para ese partido.
     */
    void newBallotForParty(int count) throws RemoteException;

    /**
     * Luego de finalizado el conteo de votos de la elección, notifica al
     * Handler si su partido ganó o perdió la elección.
     */
    void wonElection(ElectionResult result) throws RemoteException;

}
