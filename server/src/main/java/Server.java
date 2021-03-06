import ar.edu.itba.pod.ElectionParty;
import ar.edu.itba.pod.counting.ElectionCountingService;
import ar.edu.itba.pod.watcher.ElectionWatcherHandler;
import ar.edu.itba.pod.watcher.ElectionWatcherService;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Server {

	public static Object countingLock = new Integer(2);
	public static Boolean countingInProgress = null;

	public static void main(String[] args) throws RemoteException {

		// Primero tiene que obtener el rmi registry
		Registry registry = LocateRegistry.getRegistry("localhost", 1099);

		//Instancia los servicios, que se exportan en el contstructor
		Map<ElectionParty, List<ElectionWatcherHandler>> watchers = new HashMap<>();

		ElectionCountingService electionCountingService = new ElectionCountingServiceImpl(watchers);
		ElectionWatcherService electionWatcherService = new ElectionWatcherServiceImpl(watchers);

		registry.rebind("electionCountingService", electionCountingService);
		registry.rebind("electionWatcherService", electionWatcherService);

	}
}
