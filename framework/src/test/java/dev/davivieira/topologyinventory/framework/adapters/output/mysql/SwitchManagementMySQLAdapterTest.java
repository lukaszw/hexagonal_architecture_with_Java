package dev.davivieira.topologyinventory.framework.adapters.output.mysql;

import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.vo.IP;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.domain.vo.Model;
import dev.davivieira.topologyinventory.domain.vo.SwitchType;
import dev.davivieira.topologyinventory.domain.vo.Vendor;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.MockitoConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
public class SwitchManagementMySQLAdapterTest {

    @MockitoConfig
    SwitchManagementMySQLAdapter switchManagementMySQLAdapter;

    @Test
    public void testRetrieveSwitch() {
        Switch aSwitch = getSwitch();
        Mockito.when(switchManagementMySQLAdapter.retrieveSwitch(aSwitch.getId())).thenReturn(aSwitch);
        Switch retrievedSwitch = switchManagementMySQLAdapter.retrieveSwitch(aSwitch.getId());
        Assertions.assertSame(aSwitch, retrievedSwitch);

    }

    Switch getSwitch(){
        return Switch.builder()
                .id(Id.withoutId())
                .switchType(SwitchType.LAYER3)
                .switchNetworks(null)
                .ip(IP.fromAddress("10.0.0.1"))
                .model(Model.XYZ0004)
                .vendor(Vendor.CISCO)
                .location(null)
                .build();
    }
}
