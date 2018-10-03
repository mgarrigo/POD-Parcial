import ar.edu.itba.pod.ElectionParty;
import ar.edu.itba.pod.counting.CountingAlreadyStartedException;
import ar.edu.itba.pod.counting.CountingEndedException;
import ar.edu.itba.pod.watcher.ElectionWatcherHandler;
import ar.edu.itba.pod.watcher.ElectionWatcherService;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ElectionWatcherServiceImpl implements ElectionWatcherService, Serializable {

	private Map<ElectionParty, List<ElectionWatcherHandler>> watchers;
	private Boolean countingInProgress;

	public ElectionWatcherServiceImpl(Map<ElectionParty, List<ElectionWatcherHandler>> watchers, Boolean countingInProgress) throws RemoteException {
		this.watchers = watchers;
		this.countingInProgress = countingInProgress;
	}

	@Override
	public void watchElection(ElectionParty party, ElectionWatcherHandler electionWatcherHandler) throws RemoteException {
		synchronized (Server.countingLock) {
			if (countingInProgress == null) {
				if (!watchers.containsKey(party)) watchers.put(party, new LinkedList<>());
				watchers.get(party).add(electionWatcherHandler);
			} else if (countingInProgress == true) {
				throw new CountingAlreadyStartedException();
			} else if (countingInProgress == false) {
				throw new CountingEndedException();
			}
		}
	}
}
