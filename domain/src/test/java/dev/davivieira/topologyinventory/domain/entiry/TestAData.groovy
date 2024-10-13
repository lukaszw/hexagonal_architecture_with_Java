package dev.davivieira.topologyinventory.domain.entiry

import dev.davivieira.topologyinventory.domain.entity.CoreRouter
import dev.davivieira.topologyinventory.domain.entity.EdgeRouter
import dev.davivieira.topologyinventory.domain.entity.Router
import dev.davivieira.topologyinventory.domain.entity.Switch
import dev.davivieira.topologyinventory.domain.vo.*

class TestAData {

    static Network createTestNetwork(String address, int CIDR) {
        return Network.builder().
                networkAddress(IP.fromAddress(address)).
                networkName("NewNetwork").
                networkCidr(CIDR).
                build();
    }

    static Location createLocation(String country) {
        return new Location(
                "Test street",
                "Test City",
                "Test State",
                00000,
                country,
                10F,
                -10F
        );
    }

    static List<Network> createNetworks(Network network) {
        List<Network> networks = new ArrayList<>();
        networks.add(network);
        return networks;
    }

    static Switch createSwitch(String address, int cidr, Location location) {
        var newNetwork = createTestNetwork(address, cidr);
        var networks = createNetworks(newNetwork);
        var networkSwitch = createNetworkSwitch(location, networks);
        return networkSwitch;
    }

    static Switch createNetworkSwitch(Location location, List<Network> networks) {
        return Switch.builder().
                id(Id.withId("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490")).
                vendor(Vendor.CISCO).
                model(Model.XYZ0004).
                ip(IP.fromAddress("20.0.0.100")).
                location(location).
                switchType(SwitchType.LAYER3).
                switchNetworks(networks).
                build();
    }

    static EdgeRouter createEdgeRouter(Location location, String address) {
        Map<Id, Switch> switchesOfEdgeRouter = new HashMap<>();
        return EdgeRouter.builder().
                id(Id.withoutId()).
                vendor(Vendor.CISCO).
                model(Model.XYZ0002).
                ip(IP.fromAddress(address)).
                location(location).
                routerType(RouterType.EDGE).
                switches(switchesOfEdgeRouter).
                build();
    }

    static CoreRouter createCoreRouter(Location location, String address) {
        Map<Id, Router> routersOfCoreRouter = new HashMap<>();
        return CoreRouter.builder().
                id(Id.withoutId()).
                vendor(Vendor.HP).
                model(Model.XYZ0001).
                ip(IP.fromAddress(address)).
                location(location).
                routerType(RouterType.CORE).
                routers(routersOfCoreRouter).
                build();
    }

}
