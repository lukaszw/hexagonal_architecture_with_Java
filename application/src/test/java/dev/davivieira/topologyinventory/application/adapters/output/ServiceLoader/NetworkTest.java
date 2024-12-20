package dev.davivieira.topologyinventory.application.adapters.output.ServiceLoader;

import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.service.NetworkService;
import dev.davivieira.topologyinventory.domain.vo.IP;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.domain.vo.Network;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NetworkTest extends FrameworkTestData {

    public NetworkTest() throws NoSuchFieldException, IllegalAccessException {
        loadPortsAndUseCases();
        loadData();
    }
    @Test
    @Order(1)
    public void addNetworkToSwitch(){
        Id switchId = Id.withId("922dbcd5-d071-41bd-920b-00f83eb4bb46");

        var network  = Network.builder().
                networkAddress(IP.fromAddress("40.0.0.0")).
                networkName("Network").
                networkCidr(8).
                build();

        Switch networkSwitch = networkManagementGenericAdapter.addNetworkToSwitch(network, switchId);
        Predicate<Network> predicate = Network.getNetworkNamePredicate("Network");
        Network actualNetwork = NetworkService.findNetwork(networkSwitch.getSwitchNetworks(), predicate);
        assertEquals(network, actualNetwork);
    }
    @Test
    @Order(2)
    public void removeNetworkFromSwitch(){
        Id switchId = Id.withId("922dbcd5-d071-41bd-920b-00f83eb4bb46");
        var networkName = "TestNetwork";
        Predicate<Network> predicate = Network.getNetworkNamePredicate(networkName);
        Switch networkSwitch = switchManagementGenericAdapter.retrieveSwitch(switchId);
        Network existentNetwork = NetworkService.findNetwork(networkSwitch.getSwitchNetworks(), predicate);
        assertNotNull(existentNetwork);
        networkSwitch = networkManagementGenericAdapter.removeNetworkFromSwitch(networkName, switchId);
        assertEquals(0, networkSwitch.getSwitchNetworks().size());
    }
}
