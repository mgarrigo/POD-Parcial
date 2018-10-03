import ar.edu.itba.pod.ElectionParty;
import ar.edu.itba.pod.counting.ElectionCountingService;
import ar.edu.itba.pod.watcher.ElectionWatcherHandler;
import ar.edu.itba.pod.watcher.ElectionWatcherService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class Client {

	public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {

		ElectionWatcherService electionWatcherService = (ElectionWatcherService) Naming.lookup("//localhost:1099/electionWatcherService");
		ElectionCountingService electionCountingService = (ElectionCountingService) Naming.lookup("//localhost:1099/electionCountingService");

		ElectionWatcherHandler electionWatcherHandler = new ElectionWatcherHandlerImpl();

		electionWatcherService.watchElection(ElectionParty.RIGHT_PARTY, electionWatcherHandler);

		electionCountingService.countingStarted();

		electionCountingService.countBallot(ElectionParty.RIGHT_PARTY);
		electionCountingService.countBallot(ElectionParty.RIGHT_PARTY);

		electionCountingService.countingEnded();
	}
}
