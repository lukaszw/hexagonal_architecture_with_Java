package dev.davivieira.topologyinventory.application.adapters.output.memory;

import dev.davivieira.topologyinventory.application.ports.output.SwitchManagementOutputPort;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.vo.Id;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class SwitchManagementMapRepository implements SwitchManagementOutputPort {

    @Setter
    private Map<Id, Switch> switchesOfEdgeRouter;

    public SwitchManagementMapRepository() {
        this.switchesOfEdgeRouter = new HashMap<>();
    }

    public SwitchManagementMapRepository(Map<Id, Switch> switchesOfEdgeRouter) {
        this.switchesOfEdgeRouter = switchesOfEdgeRouter;
    }

    @Override
    public Switch retrieveSwitch(Id id) {
        return switchesOfEdgeRouter.get(id);
    }

}
