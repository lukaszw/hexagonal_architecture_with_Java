package dev.davivieira.topologyinventory.application;

import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.vo.IP;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.domain.vo.Location;
import dev.davivieira.topologyinventory.domain.vo.Model;
import dev.davivieira.topologyinventory.domain.vo.RouterType;
import dev.davivieira.topologyinventory.domain.vo.Vendor;

public interface RouterManagement {

    Router retrieveRouter(Id id);

    Router removeRouter(Id id);

    Router createRouter(Vendor vendor,
                        Model model,
                        IP ip,
                        Location location,
                        RouterType routerType);

    Router addRouterToCoreRouter(Id routerId, Id coreRouterId);

    Router removeRouterFromCoreRouter(Id routerId, Id coreRouterId);
}
