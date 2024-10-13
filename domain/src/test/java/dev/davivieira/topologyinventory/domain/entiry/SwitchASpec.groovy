package dev.davivieira.topologyinventory.domain.entiry

import spock.lang.Specification

import static dev.davivieira.topologyinventory.domain.entiry.TestAData.createLocation
import static dev.davivieira.topologyinventory.domain.entiry.TestAData.createSwitch
import static dev.davivieira.topologyinventory.domain.entiry.TestAData.createTestNetwork

class SwitchASpec extends Specification {

    def "AddNetworkToSwitch"() {

        given:
        var location = createLocation("US");
        var newNetwork = createTestNetwork("30.0.0.1", 8);
        var networkSwitch = createSwitch("30.0.0.0", 8, location);
        expect:
        networkSwitch.addNetworkToSwitch(newNetwork)

    }

}
