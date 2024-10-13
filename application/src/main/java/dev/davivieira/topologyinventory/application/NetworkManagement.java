package dev.davivieira.topologyinventory.application;

import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.domain.vo.Network;

public interface NetworkManagement {

    Switch addNetworkToSwitch(Network network, Id switchId);

    Switch removeNetworkFromSwitch(String networkName, Id switchId);
}
