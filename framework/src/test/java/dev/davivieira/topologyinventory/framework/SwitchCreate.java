package dev.davivieira.topologyinventory.framework;

import dev.davivieira.topologyinventory.application.usecases.SwitchManagementUseCase;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.vo.IP;
import dev.davivieira.topologyinventory.domain.vo.Location;
import dev.davivieira.topologyinventory.domain.vo.Model;
import dev.davivieira.topologyinventory.domain.vo.SwitchType;
import dev.davivieira.topologyinventory.domain.vo.Vendor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SwitchCreate {

    @Inject
    protected SwitchManagementUseCase switchManagementUseCase;
    Switch networkSwitch;
    Location locationA;

    @Given("I provide all required data to create a switch")
    public void i_provide_all_required_data_to_create_a_switch() {
        networkSwitch = this.switchManagementUseCase.createSwitch(
                Vendor.CISCO,
                Model.XYZ0001,
                IP.fromAddress("20.0.0.100"),
                locationA,
                SwitchType.LAYER3
        );
    }

    @Then("A new switch is created")
    public void a_new_switch_is_created() {
        assertNotNull(networkSwitch);
        assertEquals(Vendor.CISCO, networkSwitch.getVendor());
        assertEquals(Model.XYZ0001, networkSwitch.getModel());
        assertEquals(IP.fromAddress("20.0.0.100"), networkSwitch.getIp());
        assertEquals(locationA, networkSwitch.getLocation());
        assertEquals(SwitchType.LAYER3, networkSwitch.getSwitchType());
    }
}
