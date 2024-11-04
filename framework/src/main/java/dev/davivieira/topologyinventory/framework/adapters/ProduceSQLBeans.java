package dev.davivieira.topologyinventory.framework.adapters;

import dev.davivieira.topologyinventory.application.NetworkManagement;
import dev.davivieira.topologyinventory.application.RouterManagement;
import dev.davivieira.topologyinventory.application.SwitchManagement;
import dev.davivieira.topologyinventory.application.adapters.NetworkManagementGenericAdapter;
import dev.davivieira.topologyinventory.application.adapters.RouterManagementGenericAdapter;
import dev.davivieira.topologyinventory.application.adapters.SwitchManagementGenericAdapter;
import dev.davivieira.topologyinventory.application.ports.output.RouterManagementOutputPort;
import dev.davivieira.topologyinventory.application.ports.output.SwitchManagementOutputPort;
import dev.davivieira.topologyinventory.application.usecases.NetworkManagementUseCase;
import dev.davivieira.topologyinventory.application.usecases.RouterManagementUseCase;
import dev.davivieira.topologyinventory.application.usecases.SwitchManagementUseCase;
import dev.davivieira.topologyinventory.framework.adapters.input.rest.NetworkManagementAdapter;
import dev.davivieira.topologyinventory.framework.adapters.input.rest.NetworkManagementAdapterApi;
import dev.davivieira.topologyinventory.framework.adapters.input.rest.RouterManagementAdapter;
import dev.davivieira.topologyinventory.framework.adapters.input.rest.RouterManagementAdapterApi;
import dev.davivieira.topologyinventory.framework.adapters.input.rest.SwitchManagementAdapter;
import dev.davivieira.topologyinventory.framework.adapters.input.rest.SwitchManagementAdapterApi;
import dev.davivieira.topologyinventory.framework.adapters.output.mysql.RouterManagementMySQLAdapter;
import dev.davivieira.topologyinventory.framework.adapters.output.mysql.SwitchManagementMySQLAdapter;
import dev.davivieira.topologyinventory.framework.adapters.output.mysql.repository.RouterManagementRepository;
import dev.davivieira.topologyinventory.framework.adapters.output.mysql.repository.SwitchManagementRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;

@Dependent
public class ProduceSQLBeans {

//    public RouterManagementRepository getRouterManagementRepository() {
//        return new RouterManagementRepository();
//    }
//
//    public SwitchManagementRepository getSwitchManagementRepository() {
//        return new SwitchManagementRepository();
//    }

    @ApplicationScoped
    public RouterManagementMySQLAdapter getRouterManagementMySQLAdapter(RouterManagementRepository routerManagementRepository) {
        return new RouterManagementMySQLAdapter(routerManagementRepository);
    }

    @ApplicationScoped
    public SwitchManagementMySQLAdapter getSwitchManagementMySQLAdapter(SwitchManagementRepository switchManagementRepository) {
        return new SwitchManagementMySQLAdapter(switchManagementRepository);
    }

    /*
    * Logika biznesowa w klasie: dev.davivieira.topologyinventory.framework.adapters.ProduceBeans
    * */

    @ApplicationScoped
    public SwitchManagementAdapterApi getSwitchManagementAdapter(RouterManagementUseCase routerManagementUseCase, SwitchManagementUseCase switchManagementUseCase) {
        return new SwitchManagementAdapter(switchManagementUseCase, routerManagementUseCase);
    }

    @ApplicationScoped
    public RouterManagementAdapterApi getRouterManagementAdapter(RouterManagementUseCase routerManagementUseCase) {
        return new RouterManagementAdapter(routerManagementUseCase);
    }

    @ApplicationScoped
    public NetworkManagementAdapterApi getNetworkManagementAdapter(SwitchManagementUseCase switchManagementInputPort, NetworkManagementUseCase networkManagementUseCase) {
        return new NetworkManagementAdapter(switchManagementInputPort, networkManagementUseCase);
    }

}
