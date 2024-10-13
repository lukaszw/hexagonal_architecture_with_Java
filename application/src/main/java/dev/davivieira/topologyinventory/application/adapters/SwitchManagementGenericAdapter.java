package dev.davivieira.topologyinventory.application.adapters;

import dev.davivieira.topologyinventory.application.SwitchManagement;
import dev.davivieira.topologyinventory.application.usecases.RouterManagementUseCase;
import dev.davivieira.topologyinventory.application.usecases.SwitchManagementUseCase;
import dev.davivieira.topologyinventory.domain.entity.EdgeRouter;
import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.vo.IP;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.domain.vo.Location;
import dev.davivieira.topologyinventory.domain.vo.Model;
import dev.davivieira.topologyinventory.domain.vo.RouterType;
import dev.davivieira.topologyinventory.domain.vo.SwitchType;
import dev.davivieira.topologyinventory.domain.vo.Vendor;

public class SwitchManagementGenericAdapter implements SwitchManagement {

    private final SwitchManagementUseCase switchManagementUseCase;
    private final RouterManagementUseCase routerManagementUseCase;

    public SwitchManagementGenericAdapter(RouterManagementUseCase routerManagementUseCase, SwitchManagementUseCase switchManagementUseCase) {
        this.switchManagementUseCase = switchManagementUseCase;
        this.routerManagementUseCase = routerManagementUseCase;
//        setPorts();
    }

//    private void setPorts() {
//        this.routerManagementUseCase = new RouterManagementInputPort(
//                RouterManagementH2Adapter.getInstance()
//        );
//        this.switchManagementUseCase = new SwitchManagementInputPort(
//                SwitchManagementH2Adapter.getInstance()
//        );
//    }

    /**
     * GET /switch/retrieve/{id}
     */
    public Switch retrieveSwitch(Id switchId) {
        return switchManagementUseCase.retrieveSwitch(switchId);
    }

    /**
     * POST /switch/create
     */
    public EdgeRouter createAndAddSwitchToEdgeRouter(
            Vendor vendor,
            Model model,
            IP ip,
            Location location,
            SwitchType switchType,
            Id routerId
    ) {
        Switch newSwitch = switchManagementUseCase.createSwitch(vendor, model, ip, location, switchType);
        Router edgeRouter = routerManagementUseCase.retrieveRouter(routerId);
        if (!edgeRouter.getRouterType().equals(RouterType.EDGE))
            throw new UnsupportedOperationException("Please inform the id of an edge router to add a switch");
        Router router = switchManagementUseCase.addSwitchToEdgeRouter(newSwitch, (EdgeRouter) edgeRouter);
        return (EdgeRouter) routerManagementUseCase.persistRouter(router);
    }

    /**
     * POST /switch/remove
     */
    public EdgeRouter removeSwitchFromEdgeRouter(Id switchId, Id edgeRouterId) {
        EdgeRouter edgeRouter = (EdgeRouter) routerManagementUseCase
                .retrieveRouter(edgeRouterId);
        Switch networkSwitch = edgeRouter.getSwitches().get(switchId);
        Router router = switchManagementUseCase
                .removeSwitchFromEdgeRouter(networkSwitch, edgeRouter);
        return (EdgeRouter) routerManagementUseCase.persistRouter(router);
    }
}
