package dev.davivieira.topologyinventory.application;

import dev.davivieira.topologyinventory.domain.entity.EdgeRouter;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.vo.IP;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.domain.vo.Location;
import dev.davivieira.topologyinventory.domain.vo.Model;
import dev.davivieira.topologyinventory.domain.vo.SwitchType;
import dev.davivieira.topologyinventory.domain.vo.Vendor;

public interface SwitchManagement {

    EdgeRouter createAndAddSwitchToEdgeRouter(Vendor vendor, Model model, IP ip, Location location, SwitchType switchType, Id routerId);

    Switch retrieveSwitch(Id switchId);

    EdgeRouter removeSwitchFromEdgeRouter(Id switchId, Id edgeRouterId);
}
