package dev.davivieira.topologyinventory.application.serviceimpl;

import dev.davivieira.topologyinventory.domain.entity.CoreRouter;
import dev.davivieira.topologyinventory.domain.vo.IP;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.domain.vo.Model;
import dev.davivieira.topologyinventory.domain.vo.RouterType;
import dev.davivieira.topologyinventory.domain.vo.Vendor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RouterTest extends FrameworkTestData {

    public RouterTest() {
        loadPortsAndUseCases();
        loadData();
    }

    @Test
    public void  retrieveRouter() {
        var id = Id.withId("b832ef4f-f894-4194-8feb-a99c2cd4be0c");
        var actualId = routerManagementGenericAdapter.retrieveRouter(id)
                .getId();
        assertEquals(id, actualId);
    }

    @Test
    public void createRouter() {
        var ipAddress = "40.0.0.1";
        var routerId = this.
                routerManagementGenericAdapter.createRouter(
                        Vendor.DLINK,
                        Model.XYZ0001,
                        IP.fromAddress(ipAddress),
                        locationA,
                        RouterType.EDGE).getId();
        var router = this.routerManagementGenericAdapter.retrieveRouter(routerId);
        assertEquals(routerId, router.getId());
        assertEquals(Vendor.DLINK, router.getVendor());
        assertEquals(Model.XYZ0001, router.getModel());
        assertEquals(ipAddress, router.getIp().getIpAddress());
        assertEquals(locationA, router.getLocation());
        assertEquals(RouterType.EDGE, router.getRouterType());
    }

    @Test
    public void addRouterToCoreRouter() {
        var routerId = Id.withId("b832ef4f-f894-4194-8feb-a99c2cd4be0b");
        var coreRouterId = Id.withId("b832ef4f-f894-4194-8feb-a99c2cd4be0c");
        var actualRouter = (CoreRouter) this.routerManagementGenericAdapter.
                addRouterToCoreRouter(routerId, coreRouterId);
        assertEquals(routerId, actualRouter.getRouters().get(routerId).getId());
    }

    @Test
    public void removeRouterFromCoreRouter() {
        var routerId = Id.withId("b832ef4f-f894-4194-8feb-a99c2cd4be0a");
        var coreRouterId = Id.withId("b832ef4f-f894-4194-8feb-a99c2cd4be0c");
        var removedRouter = this.routerManagementGenericAdapter.
                removeRouterFromCoreRouter(routerId, coreRouterId);
        var coreRouter = (CoreRouter) this.routerManagementGenericAdapter
                .retrieveRouter(coreRouterId);
        assertEquals(routerId, removedRouter.getId());
        assertFalse(coreRouter.getRouters().containsKey(routerId));
    }

    @Test
    public void removeRouter() {
        var routerId = Id.withId("b832ef4f-f894-4194-8feb-a99c2cd4be0b");
        var router = this.routerManagementGenericAdapter.removeRouter(routerId);
        assertEquals(null, router);
    }
}
