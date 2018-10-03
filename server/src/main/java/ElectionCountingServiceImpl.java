import ar.edu.itba.pod.ElectionParty;
import ar.edu.itba.pod.ElectionResult;
import ar.edu.itba.pod.counting.CountingEndedException;
import ar.edu.itba.pod.counting.CountingNotStartedException;
import ar.edu.itba.pod.counting.ElectionCountingService;
import ar.edu.itba.pod.watcher.ElectionWatcherHandler;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ElectionCountingServiceImpl implements ElectionCountingService {

	private Map<ElectionParty, Integer> votes;

	private Map<ElectionParty, List<ElectionWatcherHandler>> watchers;
	private Boolean countingInProgress;

	public ElectionCountingServiceImpl(Map<ElectionParty, List<ElectionWatcherHandler>> watchers,
									   Boolean countingInProgress) throws RemoteException {
		UnicastRemoteObject.exportObject(this, 0);
		votes = new HashMap<>();
		this.watchers = watchers;
		this.countingInProgress = countingInProgress;
	}

	@Override
	public void countingStarted() throws RemoteException {
		synchronized (Server.countingLock) {
			if (countingInProgress == false) throw new CountingEndedException();
			countingInProgress = true;
		}

		int count = 0;
		for (List<ElectionWatcherHandler> electionWatcherHandlers : watchers.values()) {
			count+= electionWatcherHandlers.size();
		}

		for (List<ElectionWatcherHandler> electionWatcherHandlers : watchers.values()) {
			for (ElectionWatcherHandler electionWatcherHandler: electionWatcherHandlers) {
				electionWatcherHandler.electionCountingStarted(count);
			}
		}
	}

	@Override
	public void countBallot(ElectionParty electionParty) throws RemoteException {
		int currentCount;
		synchronized (Server.countingLock) {
			if (countingInProgress == null) throw new CountingNotStartedException();
			if (countingInProgress == false) throw new CountingEndedException();
			if (!votes.containsKey(electionParty)) votes.put(electionParty, 0);
			currentCount = votes.get(electionParty) + 1;
			votes.put(electionParty, currentCount);
		}
		if (watchers.containsKey(electionParty))
			watchers.get(electionParty).forEach(w -> {
				try {
					w.newBallotForParty(currentCount);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			});
	}

	@Override
	public void countingEnded() throws RemoteException {
		synchronized (Server.countingLock) {
			if (countingInProgress == null) throw new CountingNotStartedException();
			countingInProgress = false;
		}

		Integer max = votes.values().stream().max(Integer::compareTo).get();
		boolean hasManyWinners = votes.values().stream().filter(vote -> vote >= max).count() > 1;

		for (ElectionParty electionParty : watchers.keySet()) {

			ElectionResult electionResult;
			if (votes.get(electionParty) == max) {
				if (hasManyWinners) {
					electionResult = ElectionResult.DRAW;
				} else {
					electionResult = ElectionResult.WIN;
				}
			} else {
				electionResult = ElectionResult.LOOSE;
			}

			List<ElectionWatcherHandler> electionWatcherHandlers = watchers.get(electionParty);
			for (ElectionWatcherHandler electionWatcherHandler: electionWatcherHandlers) {

				electionWatcherHandler.wonElection(electionResult);
			}
		}
	}
}
