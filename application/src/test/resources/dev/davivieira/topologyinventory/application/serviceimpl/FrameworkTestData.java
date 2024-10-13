package dev.davivieira.topologyinventory.application.serviceimpl;

import dev.davivieira.topologyinventory.application.ports.input.NetworkManagementInputPort;
import dev.davivieira.topologyinventory.application.ports.input.RouterManagementInputPort;
import dev.davivieira.topologyinventory.application.ports.input.SwitchManagementInputPort;
import dev.davivieira.topologyinventory.application.ports.output.RouterManagementOutputPort;
import dev.davivieira.topologyinventory.application.ports.output.SwitchManagementOutputPort;
import dev.davivieira.topologyinventory.application.usecases.NetworkManagementUseCase;
import dev.davivieira.topologyinventory.application.usecases.RouterManagementUseCase;
import dev.davivieira.topologyinventory.application.usecases.SwitchManagementUseCase;
import dev.davivieira.topologyinventory.domain.entity.CoreRouter;
import dev.davivieira.topologyinventory.domain.entity.EdgeRouter;
import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.vo.IP;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.domain.vo.Location;
import dev.davivieira.topologyinventory.domain.vo.Model;
import dev.davivieira.topologyinventory.domain.vo.Network;
import dev.davivieira.topologyinventory.domain.vo.RouterType;
import dev.davivieira.topologyinventory.domain.vo.SwitchType;
import dev.davivieira.topologyinventory.domain.vo.Vendor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrameworkTestData {

    protected RouterManagementUseCase routerManagementUseCase;

    protected SwitchManagementUseCase switchManagementUseCase;

    protected NetworkManagementUseCase networkManagementUseCase;

    protected Router router;

    protected List<Router> routers = new ArrayList<>();

    protected List<Switch> switches = new ArrayList<>();

    protected List<Network> networks = new ArrayList<>();

    protected Map<Id, Router> routersOfCoreRouter = new HashMap<>();

    protected Map<Id, Switch> switchesOfEdgeRouter = new HashMap<>();

    protected Network network;

    protected Switch networkSwitch;

    protected CoreRouter coreRouter;

    protected CoreRouter newCoreRouter;

    protected EdgeRouter edgeRouter;

    protected EdgeRouter newEdgeRouter;

    protected Location locationA;

    protected Location locationB;

    protected RouterManagementGenericAdapter routerManagementGenericAdapter;
    protected SwitchManagementGenericAdapter switchManagementGenericAdapter;
    protected NetworkManagementGenericAdapter networkManagementGenericAdapter;

    protected SwitchManagementOutputPort switchManagementOutputPort;
    protected RouterManagementOutputPort routerManagementOutputPort;

    protected void loadPortsAndUseCases() {
        // Load router implementations
//        ServiceLoader<RouterManagementUseCase> loaderUseCaseRouter = ServiceLoader.load(RouterManagementUseCase.class);
//        RouterManagementUseCase routerManagementUseCase = loaderUseCaseRouter.findFirst().get();
//        ServiceLoader<RouterManagementOutputPort> loaderOutputRouter = ServiceLoader.load(RouterManagementOutputPort.class);
//        RouterManagementOutputPort routerManagementOutputPort = loaderOutputRouter.findFirst().get();

        // Load switch implementations
//        ServiceLoader<SwitchManagementUseCase> loaderUseCaseSwitch = ServiceLoader.load(SwitchManagementUseCase.class);
//        SwitchManagementUseCase switchManagementUseCase = loaderUseCaseSwitch.findFirst().get();
//        ServiceLoader<SwitchManagementOutputPort> loaderOutputSwitch = ServiceLoader.load(SwitchManagementOutputPort.class);
//        SwitchManagementOutputPort switchManagementOutputPort = loaderOutputSwitch.findFirst().get();

        // Load switch implementations
//        ServiceLoader<NetworkManagementUseCase> loaderUseCaseNetwork = ServiceLoader.load(NetworkManagementUseCase.class);
//        NetworkManagementUseCase networkManagementUseCase = loaderUseCaseNetwork.findFirst().get();

//        routerManagementUseCase.setOutputPort(routerManagementOutputPort);
//        switchManagementUseCase.setOutputPort(switchManagementOutputPort);
//        networkManagementUseCase.setOutputPort(routerManagementOutputPort);

        this.routerManagementOutputPort = new RouterManagementOutputPort() {
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
        };

        this.switchManagementOutputPort = new SwitchManagementOutputPort() {
            @Override
            public Switch retrieveSwitch(Id id) {
                return networkSwitch;
            }
        };

        this.routerManagementUseCase = new RouterManagementInputPort(this.routerManagementOutputPort);
        this.switchManagementUseCase = new SwitchManagementInputPort(this.switchManagementOutputPort);
        this.networkManagementUseCase = new NetworkManagementInputPort(this.routerManagementOutputPort);

        this.routerManagementGenericAdapter = new RouterManagementGenericAdapter(this.routerManagementUseCase);
        this.switchManagementGenericAdapter = new SwitchManagementGenericAdapter(this.routerManagementUseCase, this.switchManagementUseCase);
        this.networkManagementGenericAdapter = new NetworkManagementGenericAdapter(this.switchManagementUseCase, this.networkManagementUseCase);

    }

    public void loadData() {
        this.locationA = new Location(
                "Amos Ln",
                "Tully",
                "NY",
                13159,
                "United States",
                42.797310F,
                -76.130750F);
        this.locationB = new Location(
                "Av Republica Argentina 3109",
                "Curitiba",
                "PR",
                80610260,
                "Brazil",
                10F,
                -10F);
        this.network = Network.builder().
                networkAddress(IP.fromAddress("20.0.0.0")).
                networkName("TestNetwork").
                networkCidr(8).
                build();
        this.networks.add(network);
        this.networkSwitch = Switch.builder().
                id(Id.withId("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490")).
                vendor(Vendor.CISCO).
                model(Model.XYZ0004).
                ip(IP.fromAddress("20.0.0.100")).
                location(locationA).
                switchType(SwitchType.LAYER3).
                switchNetworks(networks).
                build();
        this.switchesOfEdgeRouter.put(networkSwitch.getId(), networkSwitch);
        this.edgeRouter = EdgeRouter.builder().
                id(Id.withoutId()).
                vendor(Vendor.CISCO).
                model(Model.XYZ0002).
                ip(IP.fromAddress("20.0.0.1")).
                location(locationA).
                routerType(RouterType.EDGE).
                switches(switchesOfEdgeRouter).
                build();
        this.routersOfCoreRouter.put(edgeRouter.getId(), edgeRouter);

        var routerA = EdgeRouter.builder()
                .id(Id.withId("b832ef4f-f894-4194-8feb-a99c2cd4be0a"))
                .routerType(RouterType.EDGE)
                .vendor(Vendor.JUNIPER)
                .model(Model.XYZ0001)
                .ip(IP.fromAddress("5.0.0.5"))
                .location(locationA)
                .build();
        this.routersOfCoreRouter.put(routerA.getId(), routerA);

        var routerCore = CoreRouter.builder()
                .id(Id.withId("b832ef4f-f894-4194-8feb-a99c2cd4be0c"))
                .routerType(RouterType.CORE)
                .vendor(Vendor.CISCO)
                .model(Model.XYZ0001)
                .ip(IP.fromAddress("1.0.0.1"))
                .location(locationA)
                .routers(this.routersOfCoreRouter)
                .build();
        this.routersOfCoreRouter.put(routerCore.getId(), routerCore);

        var routerJupiter = EdgeRouter.builder()
                .id(Id.withId("b832ef4f-f894-4194-8feb-a99c2cd4be0b"))
                .routerType(RouterType.EDGE)
                .vendor(Vendor.JUNIPER)
                .model(Model.XYZ0001)
                .ip(IP.fromAddress("6.0.0.6"))
                .location(locationA)
                .build();
        this.routersOfCoreRouter.put(routerJupiter.getId(), routerJupiter);

        var switchNet = Switch.builder()
                .id(Id.withId("922dbcd5-d071-41bd-920b-00f83eb4bb46"))
                .routerId(Id.withId("b07f5187-2d82-4975-a14b-bdbad9a8ad46"))
                .switchType(SwitchType.LAYER3)
                .vendor(Vendor.JUNIPER)
                .model(Model.XYZ0004)
                .ip(IP.fromAddress("9.0.0.9"))
                .location(locationA)
                .switchNetworks(new ArrayList<>())
                .build();
//        this.switchesOfEdgeRouter.put(switchNet.getId(), switchNet);

        Map<Id, Switch> switchesMap = new HashMap<>();
        switchesMap.put(switchNet.getId(), switchNet);
        var router = EdgeRouter.builder()
                .id(Id.withId("b07f5187-2d82-4975-a14b-bdbad9a8ad46"))
                .routerType(RouterType.EDGE)
                .vendor(Vendor.HP)
                .model(Model.XYZ0002)
                .ip(IP.fromAddress("2.0.0.2"))
                .location(locationA)
                .switches(switchesMap)
                .build();
        this.routersOfCoreRouter.put(router.getId(), router);

        this.coreRouter = CoreRouter.builder().
                id(Id.withoutId()).
                vendor(Vendor.HP).
                model(Model.XYZ0001).
                ip(IP.fromAddress("10.0.0.1")).
                location(locationA).
                routerType(RouterType.CORE).
                routers(routersOfCoreRouter).
                build();
        this.newCoreRouter = CoreRouter.builder().
                id(Id.withId("81579b05-4b4e-4b9b-91f4-75a5a8561296")).
                vendor(Vendor.HP).
                model(Model.XYZ0001).
                ip(IP.fromAddress("10.1.0.1")).
                location(locationA).
                routerType(RouterType.CORE).
                build();
        this.newEdgeRouter = EdgeRouter.builder().
                id(Id.withId("ca23800e-9b5a-11eb-a8b3-0242ac130003")).
                vendor(Vendor.CISCO).
                model(Model.XYZ0002).
                ip(IP.fromAddress("20.1.0.1")).
                location(locationA).
                routerType(RouterType.EDGE).
                build();

        this.networkSwitch.setRouterId(edgeRouter.getId() );
    }
}
