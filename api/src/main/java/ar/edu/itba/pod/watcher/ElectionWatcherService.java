package ar.edu.itba.pod.watcher;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ar.edu.itba.pod.ElectionParty;
import ar.edu.itba.pod.counting.CountingAlreadyStartedException;
import ar.edu.itba.pod.counting.CountingEndedException;

public interface ElectionWatcherService extends Remote {

    /**
     * Agrega un fiscal del partido dado al sistema de notificaciones del conteo
     * e la elección
     * 
     * Como solos se pueden agregar fiscales antes del conteo:
     * 
     * Si el conteo está en curso, arroja una CountingAlreadyStartedException
     * 
     * Si el conteo ya finalizó, arroja una CountingEndedException
     */
    void watchElection(ElectionParty party, ElectionWatcherHandler electionWatcherHandler) throws RemoteException;

}