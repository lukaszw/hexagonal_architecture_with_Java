package dev.davivieira.topologyinventory.application.adapters.out;

import dev.davivieira.topologyinventory.application.ports.output.RouterManagementOutputPort;
import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.vo.Id;

import java.util.HashMap;
import java.util.Map;

public class RouterManagementMapRepository implements RouterManagementOutputPort {

    private Map<Id, Router> routersOfCoreRouter;

    public RouterManagementMapRepository() {
        this.routersOfCoreRouter = new HashMap<>();
    }

    public RouterManagementMapRepository(Map<Id, Router> routersOfCoreRouter) {
        this.routersOfCoreRouter = routersOfCoreRouter;
    }

    @Override
    public Router retrieveRouter(Id id) {
        return routersOfCoreRouter.get(id);
    }

    @Override
    public Router removeRouter(Id id) {
        var removed = routersOfCoreRouter.remove(id);
        return removed == null ? removed : null;
    }

    @Override
    public Router persistRouter(Router router) {
        var previous = routersOfCoreRouter.put(router.getId(), router);
        return router;
    }

    public void setRoutersOfCoreRouter(Map<Id, Router> routersOfCoreRouter) {
        this.routersOfCoreRouter = routersOfCoreRouter;
    }

}
