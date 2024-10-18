package dev.davivieira.topologyinventory.application;

import dev.davivieira.topologyinventory.application.adapters.NetworkManagementGenericAdapter;
import dev.davivieira.topologyinventory.application.adapters.RouterManagementGenericAdapter;
import dev.davivieira.topologyinventory.application.adapters.SwitchManagementGenericAdapter;
import dev.davivieira.topologyinventory.application.ports.output.memory.RouterManagementMapRepository;
import dev.davivieira.topologyinventory.application.ports.output.memory.SwitchManagementMapRepository;
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
import java.util.ServiceLoader;

public class FrameworkTestData {
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

    protected void loadPortsAndUseCases() {
        // Load router implementations
        ServiceLoader<RouterManagementUseCase> loaderUseCaseRouter = ServiceLoader.load(RouterManagementUseCase.class);
        RouterManagementUseCase routerManagementUseCase = loaderUseCaseRouter.findFirst().get();
        ServiceLoader<RouterManagementOutputPort> loaderOutputRouter = ServiceLoader.load(RouterManagementOutputPort.class);
        RouterManagementOutputPort routerManagementOutputPort = loaderOutputRouter.findFirst().get();

        // Load switch implementations
        ServiceLoader<SwitchManagementUseCase> loaderUseCaseSwitch = ServiceLoader.load(SwitchManagementUseCase.class);
        SwitchManagementUseCase switchManagementUseCase = loaderUseCaseSwitch.findFirst().get();
        ServiceLoader<SwitchManagementOutputPort> loaderOutputSwitch = ServiceLoader.load(SwitchManagementOutputPort.class);
        SwitchManagementOutputPort switchManagementOutputPort = loaderOutputSwitch.findFirst().get();

        //FIXME: hm,.,.,.,... ServiceLoader,.,.,...
        ((SwitchManagementMapRepository)switchManagementOutputPort).setSwitchesOfEdgeRouter(switchesOfEdgeRouter);
        ((RouterManagementMapRepository)routerManagementOutputPort).setRoutersOfCoreRouter(routersOfCoreRouter);
        //FIXME: ,.,.,.

        // Load switch implementations
        ServiceLoader<NetworkManagementUseCase> loaderUseCaseNetwork = ServiceLoader.load(NetworkManagementUseCase.class);
        NetworkManagementUseCase networkManagementUseCase = loaderUseCaseNetwork.findFirst().get();

        routerManagementUseCase.setOutputPort(routerManagementOutputPort);
        switchManagementUseCase.setOutputPort(switchManagementOutputPort);
        networkManagementUseCase.setOutputPort(routerManagementOutputPort);

        this.routerManagementGenericAdapter = new RouterManagementGenericAdapter(routerManagementUseCase);
        this.switchManagementGenericAdapter = new SwitchManagementGenericAdapter(routerManagementUseCase, switchManagementUseCase);
        this.networkManagementGenericAdapter = new NetworkManagementGenericAdapter(switchManagementUseCase, networkManagementUseCase);
    }

    public void loadData(){

        this.locationA = new Location(
                "Amos Ln",
                "Tully",
                "NY",
                13159,
                "United States",
                42.797310F,
                -76.130750F);

        this.networks.add(
                Network.builder().
                        networkAddress(IP.fromAddress("20.0.0.0")).
                        networkName("TestNetwork").
                        networkCidr(8).
                        build()
        );

        var switch46 = Switch.builder()
                .id(Id.withId("922dbcd5-d071-41bd-920b-00f83eb4bb46"))
                .routerId(Id.withId("b07f5187-2d82-4975-a14b-bdbad9a8ad46"))
                .switchType(SwitchType.LAYER3)
                .vendor(Vendor.JUNIPER)
                .model(Model.XYZ0004)
                .ip(IP.fromAddress("9.0.0.9"))
                .location(locationA)
                .switchNetworks(networks)
                .build();
        this.switchesOfEdgeRouter.put(switch46.getId(), switch46);

        var switch47 = Switch.builder()
                .id(Id.withId("922dbcd5-d071-41bd-920b-00f83eb4bb47"))
                .routerId(Id.withId("b07f5187-2d82-4975-a14b-bdbad9a8ad46"))
                .switchType(SwitchType.LAYER3)
                .vendor(Vendor.CISCO)
                .model(Model.XYZ0002)
                .ip(IP.fromAddress("10.0.0.10"))
                .location(locationA)
                .switchNetworks(new ArrayList<>())
                .build();
        this.switchesOfEdgeRouter.put(switch47.getId(), switch47);

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

        var routerA = EdgeRouter.builder()
                .id(Id.withId("b832ef4f-f894-4194-8feb-a99c2cd4be0a"))
                .routerType(RouterType.EDGE)
                .vendor(Vendor.JUNIPER)
                .model(Model.XYZ0001)
                .ip(IP.fromAddress("5.0.0.5"))
                .location(locationA)
                .build();
        this.routersOfCoreRouter.put(routerA.getId(), routerA);

        var routerB = EdgeRouter.builder()
                .id(Id.withId("b832ef4f-f894-4194-8feb-a99c2cd4be0b"))
                .routerType(RouterType.EDGE)
                .vendor(Vendor.JUNIPER)
                .model(Model.XYZ0001)
                .ip(IP.fromAddress("6.0.0.6"))
                .location(locationA)
                .switches(switchesOfEdgeRouter)
                .build();
        this.routersOfCoreRouter.put(routerB.getId(), routerB);

        var router46 = EdgeRouter.builder()
                .id(Id.withId("b07f5187-2d82-4975-a14b-bdbad9a8ad46"))
                .routerType(RouterType.EDGE)
                .vendor(Vendor.HP)
                .model(Model.XYZ0002)
                .ip(IP.fromAddress("2.0.0.2"))
                .location(locationA)
                .switches(switchesOfEdgeRouter)
                .build();
        this.routersOfCoreRouter.put(router46.getId(), router46);

    }
}
