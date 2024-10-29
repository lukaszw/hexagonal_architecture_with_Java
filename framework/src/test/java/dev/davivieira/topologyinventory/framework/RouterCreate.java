package dev.davivieira.topologyinventory.framework;

import dev.davivieira.topologyinventory.application.usecases.RouterManagementUseCase;
import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.vo.IP;
import dev.davivieira.topologyinventory.domain.vo.Location;
import dev.davivieira.topologyinventory.domain.vo.Model;
import dev.davivieira.topologyinventory.domain.vo.Vendor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import jakarta.inject.Inject;

import static dev.davivieira.topologyinventory.domain.vo.RouterType.CORE;
import static dev.davivieira.topologyinventory.domain.vo.RouterType.EDGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RouterCreate {

    @Inject
    RouterManagementUseCase routerManagementUseCase;
    Router router;
    Location locationA = new Location(
            "Av Republica Argentina 3109",
            "Curitiba",
            "PR",
            80610260,
            "Brazil",
            10F,
            -10F
    );

    //Creating a new core router
    @Given("I provide all required data to create a core router")
    public void create_core_router() {
        router = this.routerManagementUseCase.createRouter(
                null,
                Vendor.CISCO,
                Model.XYZ0001,
                IP.fromAddress("20.0.0.1"),
                locationA,
                CORE
        );
    }

    @Then("A new core router is created")
    public void a_new_core_router_is_created() {
        assertNotNull(router);
        assertEquals(CORE, router.getRouterType());
    }

    //Creating a new edge router
    @Given("I provide all required data to create an edge router")
    public void create_edge_router() {
        router = this.routerManagementUseCase.createRouter(
                null,
                Vendor.HP,
                Model.XYZ0004,
                IP.fromAddress("30.0.0.1"),
                locationA,
                EDGE
        );
    }

    @Then("A new edge router is created")
    public void a_new_edge_router_is_created() {
        assertNotNull(router);
        assertEquals(EDGE, router.getRouterType());
    }
}