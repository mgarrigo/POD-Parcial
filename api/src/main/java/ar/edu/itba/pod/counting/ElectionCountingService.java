package ar.edu.itba.pod.counting;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ar.edu.itba.pod.ElectionParty;

public interface ElectionCountingService extends Remote {

    /**
     * Empieza el conteo de votos de la elección.
     *
     * Luego de esta invocación no se permite que se sumen fiscales para
     * supervisar la elección.
     *
     * Si el conteo ya finalizó, arroja una CountingEndedException.
     */
    void countingStarted() throws RemoteException;

    /**
     * Se contabiliza un voto para el partido electionParty y realiza la
     * notificacion al partido.
     *
     * Si el conteo ya finalizó, arroja una CountingEndedException.
     */
    void countBallot(ElectionParty electionParty) throws RemoteException;

    /**
     * Finalizó el conteo de votos de la elección. Ya no se pueden contabilizar
     * más votos y el conteo no puede abrirse nuevamente.
     *
     * Si el conteo no se inicio, arroja CountingNotStartedException.
     */
    void countingEnded() throws RemoteException;

}