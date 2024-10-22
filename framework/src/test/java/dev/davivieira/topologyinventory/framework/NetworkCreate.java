package dev.davivieira.topologyinventory.framework;

import dev.davivieira.topologyinventory.application.usecases.NetworkManagementUseCase;
import dev.davivieira.topologyinventory.domain.vo.IP;
import dev.davivieira.topologyinventory.domain.vo.Network;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NetworkCreate {

    @Inject
    NetworkManagementUseCase networkManagementUseCase;
    Network network;

    @Given("I provide all required data to create a network")
    public void i_provide_all_required_data_to_create_a_network() {
        network = networkManagementUseCase.createNetwork(
                IP.fromAddress("10.0.0.0"),
                "Finance",
                8
        );
    }

    @Then("A new network is created")
    public void a_new_network_is_created() {
        assertNotNull(network);
        assertEquals(IP.fromAddress("10.0.0.0"), network.getNetworkAddress());
        assertEquals("Finance", network.getNetworkName());
        assertEquals(8, network.getNetworkCidr());
    }
}
