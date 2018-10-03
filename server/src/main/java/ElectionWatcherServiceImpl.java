import ar.edu.itba.pod.ElectionParty;
import ar.edu.itba.pod.counting.CountingAlreadyStartedException;
import ar.edu.itba.pod.counting.CountingEndedException;
import ar.edu.itba.pod.watcher.ElectionWatcherHandler;
import ar.edu.itba.pod.watcher.ElectionWatcherService;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ElectionWatcherServiceImpl implements ElectionWatcherService, Serializable {

	private Map<ElectionParty, List<ElectionWatcherHandler>> watchers;

	public ElectionWatcherServiceImpl(Map<ElectionParty, List<ElectionWatcherHandler>> watchers) throws RemoteException {
		UnicastRemoteObject.exportObject(this, 0);
		this.watchers = watchers;
	}

	@Override
	public void watchElection(ElectionParty party, ElectionWatcherHandler electionWatcherHandler) throws RemoteException {
		synchronized (Server.countingLock) {
			if (Server.countingInProgress == null) {
				if (!watchers.containsKey(party)) watchers.put(party, new LinkedList<>());
				watchers.get(party).add(electionWatcherHandler);
			} else if (Server.countingInProgress == true) {
				throw new CountingAlreadyStartedException();
			} else if (Server.countingInProgress == false) {
				throw new CountingEndedException();
			}
		}
	}
}
