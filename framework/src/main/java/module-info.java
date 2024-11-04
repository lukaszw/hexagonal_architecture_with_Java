module framework {
    requires domain;
    requires application;
    requires static lombok;
    requires org.eclipse.persistence.core;
    requires java.sql;
    requires jakarta.persistence;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires jakarta.cdi;
    requires jakarta.inject;
    requires jakarta.ws.rs;
    requires io.smallrye.mutiny;
    requires microprofile.openapi.api;
    requires quarkus.hibernate.reactive.panache;
    requires quarkus.hibernate.reactive.panache.common;
    requires io.vertx.core;
    requires jakarta.xml.bind;
    requires io.smallrye.common.annotation;
    requires com.fasterxml.jackson.annotation;
    requires jakarta.transaction;
    requires quarkus.vertx;
    requires microprofile.context.propagation.api;
    requires io.smallrye.mutiny.vertx.core;
    requires org.slf4j;
    requires hibernate.reactive.core;

    exports dev.davivieira.topologyinventory.framework.adapters.output.h2.data;
    opens dev.davivieira.topologyinventory.framework.adapters.output.h2.data;

    exports dev.davivieira.topologyinventory.framework.adapters.output.mysql.data;
    opens dev.davivieira.topologyinventory.framework.adapters.output.mysql.data;

//    provides dev.davivieira.topologyinventory.application.ports.output.RouterManagementOutputPort
//            with dev.davivieira.topologyinventory.framework.adapters.output.h2.RouterManagementH2Adapter;
//    provides dev.davivieira.topologyinventory.application.ports.output.SwitchManagementOutputPort
//            with dev.davivieira.topologyinventory.framework.adapters.output.h2.SwitchManagementH2Adapter;

//    provides dev.davivieira.topologyinventory.application.ports.output.RouterManagementOutputPort
//            with dev.davivieira.topologyinventory.framework.adapters.output.mysql.RouterManagementMySQLAdapter;
//    provides dev.davivieira.topologyinventory.application.ports.output.SwitchManagementOutputPort
//            with dev.davivieira.topologyinventory.framework.adapters.output.mysql.SwitchManagementMySQLAdapter;

    uses dev.davivieira.topologyinventory.application.usecases.RouterManagementUseCase;
    uses dev.davivieira.topologyinventory.application.usecases.SwitchManagementUseCase;
    uses dev.davivieira.topologyinventory.application.usecases.NetworkManagementUseCase;
    uses dev.davivieira.topologyinventory.application.ports.output.RouterManagementOutputPort;
    uses dev.davivieira.topologyinventory.application.ports.output.SwitchManagementOutputPort;
}