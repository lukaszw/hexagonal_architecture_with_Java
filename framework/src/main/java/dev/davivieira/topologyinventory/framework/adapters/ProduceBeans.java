package dev.davivieira.topologyinventory.framework.adapters;

import dev.davivieira.topologyinventory.application.NetworkManagement;
import dev.davivieira.topologyinventory.application.RouterManagement;
import dev.davivieira.topologyinventory.application.SwitchManagement;
import dev.davivieira.topologyinventory.application.adapters.NetworkManagementGenericAdapter;
import dev.davivieira.topologyinventory.application.adapters.RouterManagementGenericAdapter;
import dev.davivieira.topologyinventory.application.adapters.SwitchManagementGenericAdapter;
import dev.davivieira.topologyinventory.application.ports.input.NetworkManagementInputPort;
import dev.davivieira.topologyinventory.application.ports.input.RouterManagementInputPort;
import dev.davivieira.topologyinventory.application.ports.input.SwitchManagementInputPort;
import dev.davivieira.topologyinventory.application.ports.output.RouterManagementOutputPort;
import dev.davivieira.topologyinventory.application.ports.output.SwitchManagementOutputPort;
import dev.davivieira.topologyinventory.application.usecases.NetworkManagementUseCase;
import dev.davivieira.topologyinventory.application.usecases.RouterManagementUseCase;
import dev.davivieira.topologyinventory.application.usecases.SwitchManagementUseCase;
import dev.davivieira.topologyinventory.framework.adapters.output.h2.RouterManagementH2Adapter;
import dev.davivieira.topologyinventory.framework.adapters.output.h2.SwitchManagementH2Adapter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;

/*
* Chapter 11
* */

@Dependent
public class ProduceBeans {

//    @ApplicationScoped
    public RouterManagementOutputPort getRouterManagementOutputPort() {
        return new RouterManagementH2Adapter();
    }

//    @ApplicationScoped
    public SwitchManagementOutputPort getSwitchManagementOutputPort() {
        return new SwitchManagementH2Adapter();
    }

    @ApplicationScoped
    public RouterManagementUseCase getRouterManagementInputPort(RouterManagementOutputPort routerManagementOutputPort) {
        return new RouterManagementInputPort(routerManagementOutputPort);
    }

    @ApplicationScoped
    public SwitchManagementUseCase getSwitchManagementInputPort(SwitchManagementOutputPort switchManagementOutputPort) {
        return new SwitchManagementInputPort(switchManagementOutputPort);
    }

    @ApplicationScoped
    public NetworkManagementUseCase getNetworkManagementInputPort(RouterManagementOutputPort routerManagementOutputPort) {
        return new NetworkManagementInputPort(routerManagementOutputPort);
    }

//    @ApplicationScoped
    public SwitchManagement getSwitchManagementGenericAdapter(RouterManagementUseCase routerManagementUseCase, SwitchManagementUseCase switchManagementUseCase) {
        return new SwitchManagementGenericAdapter(routerManagementUseCase, switchManagementUseCase);
    }

//    @ApplicationScoped
    public RouterManagement getRouterManagementGenericAdapter(RouterManagementUseCase routerManagementUseCase) {
        return new RouterManagementGenericAdapter(routerManagementUseCase);
    }

//    @ApplicationScoped
    public NetworkManagement getNetworkManagementGenericAdapter(SwitchManagementUseCase switchManagementInputPort, NetworkManagementUseCase networkManagementUseCase) {
        return new NetworkManagementGenericAdapter(switchManagementInputPort, networkManagementUseCase);
    }

}
