package dev.davivieira.topologyinventory.application.adapters;

import dev.davivieira.topologyinventory.application.NetworkManagement;
import dev.davivieira.topologyinventory.application.usecases.NetworkManagementUseCase;
import dev.davivieira.topologyinventory.application.usecases.SwitchManagementUseCase;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.domain.vo.Network;

public class NetworkManagementGenericAdapter implements NetworkManagement {

    private final SwitchManagementUseCase switchManagementUseCase;
    private final NetworkManagementUseCase networkManagementUseCase;

    public NetworkManagementGenericAdapter(SwitchManagementUseCase switchManagementUseCase, NetworkManagementUseCase networkManagementUseCase) {
        this.switchManagementUseCase = switchManagementUseCase;
        this.networkManagementUseCase = networkManagementUseCase;
    }

    /**
     * POST /network/add
     */
    public Switch addNetworkToSwitch(Network network, Id switchId) {
        Switch networkSwitch = switchManagementUseCase.retrieveSwitch(switchId);
        return networkManagementUseCase.addNetworkToSwitch(network, networkSwitch);
    }

    /**
     * POST /network/remove
     */
    public Switch removeNetworkFromSwitch(String networkName, Id switchId) {
        Switch networkSwitch = switchManagementUseCase.retrieveSwitch(switchId);
        return networkManagementUseCase.removeNetworkFromSwitch(networkName, networkSwitch);
    }
}
