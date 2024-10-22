package dev.davivieira.topologyinventory.framework;

import dev.davivieira.topologyinventory.application.usecases.RouterManagementUseCase;
import dev.davivieira.topologyinventory.application.usecases.SwitchManagementUseCase;
import dev.davivieira.topologyinventory.domain.entity.EdgeRouter;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.domain.vo.Network;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SwitchRemove {

    @Inject
    RouterManagementUseCase routerManagementUseCase;
    @Inject
    SwitchManagementUseCase switchManagementUseCase;
    Id id;
    Switch switchToBeRemoved;
    EdgeRouter edgeRouter;
    Network network;

    @Given("I know the switch I want to remove")
    public void i_know_the_switch_i_want_to_remove() {
        id = Id.withId("922dbcd5-d071-41bd-920b-00f83eb4bb47");
        edgeRouter = (EdgeRouter) routerManagementUseCase.retrieveRouter(Id.withId("b07f5187-2d82-4975-a14b-bdbad9a8ad46"));
        switchToBeRemoved = edgeRouter.getSwitches().get(id);
    }

    @And("The switch has no networks")
    public void the_switch_has_no_networks() {
        switchToBeRemoved.removeNetworkFromSwitch(network);
        assertTrue(switchToBeRemoved.getSwitchNetworks().isEmpty());
    }

    @Then("I remove the switch from the edge router")
    public void i_remove_the_switch_from_the_edge_router() {
        assertNotNull(edgeRouter);
        edgeRouter = this.switchManagementUseCase.
                removeSwitchFromEdgeRouter(switchToBeRemoved, edgeRouter);
        assertNull(edgeRouter.getSwitches().get(id));
    }
}
