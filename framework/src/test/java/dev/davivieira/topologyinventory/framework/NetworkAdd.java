package dev.davivieira.topologyinventory.framework;

import dev.davivieira.topologyinventory.application.usecases.NetworkManagementUseCase;
import dev.davivieira.topologyinventory.application.usecases.SwitchManagementUseCase;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.service.NetworkService;
import dev.davivieira.topologyinventory.domain.vo.IP;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.domain.vo.Network;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NetworkAdd {

    @Inject
    SwitchManagementUseCase switchManagementUseCase;
    @Inject
    NetworkManagementUseCase networkManagementUseCase;
    Switch networkSwitch;
    Network network;

    @Given("I have a network")
    public void i_have_a_network() {
        network = networkManagementUseCase.createNetwork(
                IP.fromAddress("50.0.0.0"),
                "AddNetwork",
                8
        );
        assertNotNull(network);
    }

    @And("I have a switch to add a network")
    public void i_have_a_switch_to_add_a_network() {
        networkSwitch = switchManagementUseCase.retrieveSwitch(Id.withId("fdd6f769-a4e4-4b5e-b59a-516543775abc"));
        assertNotNull(networkSwitch);
    }

    @Then("I add the network to the switch")
    public void i_add_the_network_to_the_switch() {
        var predicate = Network.getNetworkNamePredicate("AddNetwork");
        var networks = this.networkManagementUseCase
                .addNetworkToSwitch(network, networkSwitch)
                .getSwitchNetworks();
        var network = NetworkService.
                findNetwork(networks, predicate).getNetworkName();
        assertEquals("AddNetwork", network);
    }
}
