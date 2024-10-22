package dev.davivieira.topologyinventory.framework;

import dev.davivieira.topologyinventory.application.usecases.NetworkManagementUseCase;
import dev.davivieira.topologyinventory.application.usecases.RouterManagementUseCase;
import dev.davivieira.topologyinventory.application.usecases.SwitchManagementUseCase;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.service.NetworkService;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.domain.vo.Network;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import jakarta.inject.Inject;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class NetworkRemove {

    @Inject
    RouterManagementUseCase routerManagementUseCase;
    @Inject
    SwitchManagementUseCase switchManagementUseCase;
    @Inject
    NetworkManagementUseCase networkManagementUseCase;
    Predicate<Network> predicate;
    Switch networkSwitch;
    Network network;

    @Given("I know the network I want to remove")
    public void i_know_the_network_i_want_to_remove() {
        networkSwitch = switchManagementUseCase.retrieveSwitch(Id.withId("9887cf6d-98df-477d-9750-738aa2615285"));
        predicate = Network.getNetworkNamePredicate("TestNetwork");
        network = NetworkService.findNetwork(networkSwitch.getSwitchNetworks(), predicate);
        assertEquals("TestNetwork", network.getNetworkName());
    }

    @And("I have a switch to remove a network")
    public void i_have_a_switch_to_remove_a_network() {
        assertNotNull(networkSwitch);
    }

    @Then("I remove the network from the switch")
    public void i_remove_the_network_from_the_switch() {
        var networks = this.networkManagementUseCase.removeNetworkFromSwitch("TestNetwork", networkSwitch).getSwitchNetworks();
        network = NetworkService.findNetwork(networks, predicate);
        assertNull(network);
    }
}
