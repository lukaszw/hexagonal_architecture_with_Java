package dev.davivieira.topologyinventory.framework.adapters.input.rest;

import dev.davivieira.topologyinventory.application.usecases.RouterManagementUseCase;
import dev.davivieira.topologyinventory.domain.entity.CoreRouter;
import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.vo.IP;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.framework.adapters.input.rest.request.router.AddRouter;
import dev.davivieira.topologyinventory.framework.adapters.input.rest.request.router.CreateRouter;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class RouterManagementAdapter implements RouterManagementAdapterApi {

    private final RouterManagementUseCase routerManagementUseCase;

    @Inject
    public RouterManagementAdapter(RouterManagementUseCase routerManagementUseCase) {
        this.routerManagementUseCase = routerManagementUseCase;
    }

    public Uni<Response> retrieveRouter(Id id) {
        return Uni.createFrom()
                .item(routerManagementUseCase.retrieveRouter(id))
                .onItem()
                .transform(f -> f != null ? Response.ok(f) : Response.ok(null))
                .onItem()
                .transform(Response.ResponseBuilder::build);
    }

    public Uni<Response> removeRouter(Id id) {
        return Uni.createFrom()
                .item(routerManagementUseCase.removeRouter(id))
                .onItem()
                .transform(f -> f != null ? Response.ok(f) : Response.ok(null))
                .onItem()
                .transform(Response.ResponseBuilder::build);
    }

    public Uni<Response> createRouter(CreateRouter createRouter) {
        var router = routerManagementUseCase.createRouter(
                null,
                createRouter.getVendor(),
                createRouter.getModel(),
                IP.fromAddress(createRouter.getIp()),
                createRouter.getLocation(),
                createRouter.getRouterType()
        );
        return Uni.createFrom()
                .item(routerManagementUseCase.persistRouter(router))
                .onItem()
                .transform(f -> f != null ? Response.ok(f) : Response.ok(null))
                .onItem()
                .transform(Response.ResponseBuilder::build);
    }

    public Uni<Response> addRouterToCoreRouter(AddRouter addRouter) {
        Router router = routerManagementUseCase.retrieveRouter(Id.withId(addRouter.getRouterId()));
        CoreRouter coreRouter = (CoreRouter) routerManagementUseCase.retrieveRouter(Id.withId(addRouter.getCoreRouterId()));
        return Uni.createFrom()
                .item(routerManagementUseCase.addRouterToCoreRouter(router, coreRouter))
                .onItem()
                .transform(f -> f != null ? Response.ok(f) : Response.ok(null))
                .onItem()
                .transform(Response.ResponseBuilder::build);
    }

    public Uni<Response> removeRouterFromCoreRouter(Id routerId, Id coreRouterId) {
        Router router = routerManagementUseCase.retrieveRouter(routerId);
        CoreRouter coreRouter = (CoreRouter) routerManagementUseCase.retrieveRouter(coreRouterId);
        return Uni.createFrom().item(routerManagementUseCase.removeRouterFromCoreRouter(router, coreRouter))
                .onItem()
                .transform(f -> f != null ? Response.ok(f) : Response.ok(null))
                .onItem()
                .transform(Response.ResponseBuilder::build);
    }
}
