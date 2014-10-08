package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.openstack;

import java.util.List;

import org.openstack4j.api.OSClient;
import org.openstack4j.openstack.OSFactory;

public class OpenStack_Help {
	public OSClient authenticate() {
		OSClient os = OSFactory.builder()
                .endpoint("http://127.0.0.1:5000/v2.0")
                .credentials("admin","sample")
                .tenantName("admin")
                .authenticate();
		
		return os;
	}
	
	public void SomeQueries() {
		// Find all Users
//		List<? extends User> users = os.identity().users().list();
//
//		// List all Tenants
//		List<? extends Tenant> tenants = os.identity().tenants().list();
//
//		// Find all Compute Flavors
//		List<? extends Flavor> flavors = os.compute().flavors().list();
//
//		// Find all running Servers
//		List<? extends Server> servers = os.compute().servers().list();
//
//		// Suspend a Server
//		os.compute().servers().action("serverId", Action.SUSPEND);
//
//		// List all Networks
//		List<? extends Network> networks = os.networking().network().list();
//
//		// List all Subnets
//		List<? extends Subnet> subnets = os.networking().subnet().list();
//
//		// List all Routers
//		List<? extends Router> routers = os.networking().router().list();
//
//		// List all Images (Glance)
//		List<? extends Image> images = os.images().list();
//
//		// Download the Image Data
//		InputStream is = os.images().getAsStream("imageId");
	}
}
