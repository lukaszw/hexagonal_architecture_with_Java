package dev.davivieira.topologyinventory.framework.adapters.input.rest;

import dev.davivieira.topologyinventory.application.usecases.RouterManagementUseCase;
import dev.davivieira.topologyinventory.application.usecases.SwitchManagementUseCase;
import dev.davivieira.topologyinventory.domain.entity.EdgeRouter;
import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.vo.IP;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.domain.vo.RouterType;
import dev.davivieira.topologyinventory.framework.adapters.input.rest.request.aswitch.CreateSwitch;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;

public class SwitchManagementAdapter implements SwitchManagementAdapterApi {

    private final SwitchManagementUseCase switchManagementUseCase;
    private final RouterManagementUseCase routerManagementUseCase;

    public SwitchManagementAdapter(SwitchManagementUseCase switchManagementUseCase, RouterManagementUseCase routerManagementUseCase) {
        this.switchManagementUseCase = switchManagementUseCase;
        this.routerManagementUseCase = routerManagementUseCase;
    }

    public Uni<Response> retrieveSwitch(Id switchId) {
        return Uni.createFrom()
                .item(switchManagementUseCase.retrieveSwitch(switchId))
                .onItem()
                .transform(f -> f != null ? Response.ok(f) : Response.ok(null))
                .onItem()
                .transform(Response.ResponseBuilder::build);
    }

    public Uni<Response> createAndAddSwitchToEdgeRouter(CreateSwitch createSwitch, Id edgeRouterId) {
        Switch newSwitch = switchManagementUseCase.createSwitch(
                createSwitch.getVendor(),
                createSwitch.getModel(),
                IP.fromAddress(createSwitch.getIp()),
                createSwitch.getLocation(),
                createSwitch.getSwitchType()
        );
        Router edgeRouter = routerManagementUseCase.retrieveRouter(edgeRouterId);
        if (!edgeRouter.getRouterType().equals(RouterType.EDGE))
            throw new UnsupportedOperationException("Please inform the id of an edge router to add a switch");
        Router router = switchManagementUseCase.addSwitchToEdgeRouter(newSwitch, (EdgeRouter) edgeRouter);

        return Uni.createFrom()
                .item((EdgeRouter) routerManagementUseCase.persistRouter(router))
                .onItem()
                .transform(f -> f != null ? Response.ok(f) : Response.ok(null))
                .onItem()
                .transform(Response.ResponseBuilder::build);
    }

    public Uni<Response> removeSwitchFromEdgeRouter(Id switchId, Id edgeRouterId) {
        EdgeRouter edgeRouter = (EdgeRouter) routerManagementUseCase.retrieveRouter(edgeRouterId);
        Switch networkSwitch = edgeRouter.getSwitches().get(switchId);
        Router router = switchManagementUseCase.removeSwitchFromEdgeRouter(networkSwitch, edgeRouter);

        return Uni.createFrom()
                .item((EdgeRouter) routerManagementUseCase.persistRouter(router))
                .onItem()
                .transform(f -> f != null ? Response.ok(f) : Response.ok(null))
                .onItem()
                .transform(Response.ResponseBuilder::build);
    }
}
