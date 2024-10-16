import dev.davivieira.topologyinventory.application.adapters.output.memory.RouterManagementMapRepository;
import dev.davivieira.topologyinventory.application.adapters.output.memory.SwitchManagementMapRepository;

module application {
    exports dev.davivieira.topologyinventory.application.usecases;
    exports dev.davivieira.topologyinventory.application.ports.output;
    exports dev.davivieira.topologyinventory.application.ports.input;
    exports dev.davivieira.topologyinventory.application;
    exports dev.davivieira.topologyinventory.application.adapters;
    requires domain;
    requires static lombok;

    exports dev.davivieira.topologyinventory.application.adapters.output.memory;
    opens dev.davivieira.topologyinventory.application.adapters.output.memory;

    provides dev.davivieira.topologyinventory.application.usecases.RouterManagementUseCase
            with dev.davivieira.topologyinventory.application.ports.input.RouterManagementInputPort;
    provides dev.davivieira.topologyinventory.application.usecases.SwitchManagementUseCase
            with dev.davivieira.topologyinventory.application.ports.input.SwitchManagementInputPort;
    provides dev.davivieira.topologyinventory.application.usecases.NetworkManagementUseCase
            with dev.davivieira.topologyinventory.application.ports.input.NetworkManagementInputPort;
    provides dev.davivieira.topologyinventory.application.ports.output.RouterManagementOutputPort
            with RouterManagementMapRepository;
    provides dev.davivieira.topologyinventory.application.ports.output.SwitchManagementOutputPort
            with SwitchManagementMapRepository;

    uses dev.davivieira.topologyinventory.application.usecases.RouterManagementUseCase;
    uses dev.davivieira.topologyinventory.application.usecases.SwitchManagementUseCase;
    uses dev.davivieira.topologyinventory.application.usecases.NetworkManagementUseCase;
    uses dev.davivieira.topologyinventory.application.ports.output.RouterManagementOutputPort;
    uses dev.davivieira.topologyinventory.application.ports.output.SwitchManagementOutputPort;
}
