package dev.davivieira.topologyinventory.framework;

import dev.davivieira.topologyinventory.application.usecases.SwitchManagementUseCase;
import dev.davivieira.topologyinventory.domain.entity.EdgeRouter;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.vo.IP;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.domain.vo.Location;
import dev.davivieira.topologyinventory.domain.vo.Model;
import dev.davivieira.topologyinventory.domain.vo.RouterType;
import dev.davivieira.topologyinventory.domain.vo.SwitchType;
import dev.davivieira.topologyinventory.domain.vo.Vendor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SwitchAdd {

    @Inject
    protected SwitchManagementUseCase switchManagementUseCase;
    Switch networkSwitch;
    Map<Id, Switch> switchesOfEdgeRouter = new HashMap<>();
    Location locationA = new Location(
            "Av Republica Argentina 3109",
            "Curitiba",
            "PR",
            80610260,
            "Brazil",
            10F,
            -10F
    );

    EdgeRouter edgeRouter = EdgeRouter.builder().
            id(Id.withoutId()).
            vendor(Vendor.CISCO).
            model(Model.XYZ0002).
            ip(IP.fromAddress("20.0.0.1")).
            location(new Location(
                    "Av Republica Argentina 3109",
                    "Curitiba",
                    "PR",
                    80610260,
                    "Brazil",
                    10F,
                    -10F)).
            routerType(RouterType.EDGE).
            switches(switchesOfEdgeRouter).
            build();

    @Given("I provide a switch")
    public void i_provide_a_switch() {
        networkSwitch = Switch.builder().
                id(Id.withId("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490")).
                vendor(Vendor.CISCO).
                model(Model.XYZ0004).
                ip(IP.fromAddress("20.0.0.100")).
                location(locationA).
                switchType(SwitchType.LAYER3).
                build();
        assertNotNull(networkSwitch);
    }

    @Then("I add the switch to the edge router")
    public void i_add_the_switch_to_the_edge_router() {
        assertNotNull(edgeRouter);
        edgeRouter = this.switchManagementUseCase.
                addSwitchToEdgeRouter(networkSwitch, edgeRouter);
        var actualId = networkSwitch.getId();
        var expectedId = edgeRouter.
                getSwitches().
                get(Id.withId("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490")).
                getId();
        assertEquals(expectedId, actualId);
    }
}
