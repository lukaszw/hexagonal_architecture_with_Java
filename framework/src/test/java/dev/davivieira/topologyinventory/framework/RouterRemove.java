package dev.davivieira.topologyinventory.framework;

import dev.davivieira.topologyinventory.application.usecases.RouterManagementUseCase;
import dev.davivieira.topologyinventory.domain.entity.CoreRouter;
import dev.davivieira.topologyinventory.domain.entity.EdgeRouter;
import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.domain.vo.Network;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import jakarta.inject.Inject;

import static dev.davivieira.topologyinventory.domain.vo.RouterType.CORE;
import static dev.davivieira.topologyinventory.domain.vo.RouterType.EDGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RouterRemove {

    @Inject
    RouterManagementUseCase routerManagementUseCase;
    CoreRouter coreRouter;
    EdgeRouter edgeRouter;
    Switch networkSwitch;
    CoreRouter coreRouterToBeRemoved;

    //Removing an edge router from a core router
    @Given("The core router has at least one edge router connected to it")
    public void the_core_router_has_at_least_one_edge_router_connected_to_it() {
        var predicate = Router.getRouterTypePredicate(EDGE);
        this.coreRouter = (CoreRouter) routerManagementUseCase.retrieveRouter(Id.withId("b832ef4f-f894-4194-8feb-a99c2cd4be0c"));
        edgeRouter = (EdgeRouter) this.coreRouter.
                getRouters().
                entrySet().
                stream().
                map(routerMap -> routerMap.getValue()).
                filter(predicate).
                findFirst().
                get();
        assertEquals(EDGE, edgeRouter.getRouterType());
    }

    @And("The switch has no networks attached to it")
    public void the_switch_has_no_networks_attached_to_it() {
        networkSwitch = edgeRouter.getSwitches().get(Id.withId("9887cf6d-98df-477d-9750-738aa2615285"));
        var networksSize = networkSwitch.getSwitchNetworks().size();
        assertEquals(1, networksSize);
        Network network = networkSwitch.getSwitchNetworks().get(0);
        networkSwitch.removeNetworkFromSwitch(network);
        networksSize = networkSwitch.getSwitchNetworks().size();
        assertEquals(0, networksSize);
    }

    @And("The edge router has no switches attached to it")
    public void the_edge_router_has_no_switches_attached_to_it() {
        var switchesSize = edgeRouter.getSwitches().size();
        assertEquals(1, switchesSize);
        edgeRouter.removeSwitch(networkSwitch);
        switchesSize = edgeRouter.getSwitches().size();
        assertEquals(0, switchesSize);
    }

    @Then("I remove the edge router from the core router")
    public void edge_router_is_removed_from_core_router() {
        var actualID = edgeRouter.getId();
        var expectedID = this.routerManagementUseCase.
                removeRouterFromCoreRouter(edgeRouter, coreRouter).
                getId();
        assertEquals(expectedID, actualID);
    }

    //Removing a core router from another core router
    @Given("The core router has at least one core router connected to it")
    public void the_core_router_has_at_least_one_core_router_connected_to_it() {
        var predicate = Router.getRouterTypePredicate(CORE);
        coreRouterToBeRemoved = (CoreRouter) this.coreRouter.
                getRouters().
                entrySet().
                stream().
                map(routerMap -> routerMap.getValue()).
                filter(predicate).
                findFirst().
                get();
        assertEquals(CORE, coreRouterToBeRemoved.getRouterType());
    }

    @And("The core router has no other routers connected to it")
    public void the_core_router_no_other_routers_connected_to_it() {
        assertTrue(coreRouterToBeRemoved.getRouters().isEmpty());
    }

    @Then("I remove the core router from another core router")
    public void i_remove_the_core_router_from_another_core_router() {
        var actualId = coreRouterToBeRemoved.getId();
        var expectedId = this.coreRouter.removeRouter(coreRouterToBeRemoved).getId();
        assertEquals(expectedId, actualId);
    }
}
