import ar.edu.itba.pod.ElectionResult;
import ar.edu.itba.pod.watcher.ElectionWatcherHandler;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ElectionWatcherHandlerImpl implements ElectionWatcherHandler, Serializable {

	public ElectionWatcherHandlerImpl() throws RemoteException {
		UnicastRemoteObject.exportObject(this, 0);
	}

	@Override
	public void electionCountingStarted(int electionWatcherCount) throws RemoteException {
		System.out.println("The Election has started! - Number of Watchers: " + electionWatcherCount);
	}

	@Override
	public void newBallotForParty(int count) throws RemoteException {
		System.out.println("New Ballot for Party! - Current vote count: " + count);
	}

	@Override
	public void wonElection(ElectionResult result) throws RemoteException {
		System.out.println("The Election has ended! - Result is: " + result);

	}
}
