package dev.davivieira.topologyinventory.framework.adapters.input.rest;

import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.framework.adapters.input.rest.request.router.AddRouter;
import dev.davivieira.topologyinventory.framework.adapters.input.rest.request.router.CreateRouter;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/router")
@Tag(name = "Router Operations", description = "Router management operations")
public interface RouterManagementAdapterApi {

    @GET
    @Path("/{id}")
    @Operation(operationId = "retrieveRouter", description = "Retrieve a router from the network inventory")
    Uni<Response> retrieveRouter(@PathParam("id") Id id);

    @DELETE
    @Path("/{id}")
    @Operation(operationId = "removeRouter", description = "Remove a router from the network inventory")
    Uni<Response> removeRouter(@PathParam("id") Id id);

    @POST
    @Path("/")
    @Operation(operationId = "createRouter", description = "Create and persist a new router on the network inventory")
    Uni<Response> createRouter(CreateRouter createRouter);

    @POST
    @Path("/add")
    @Operation(operationId = "addRouterToCoreRouter", description = "Add a router into a core router")
    Uni<Response> addRouterToCoreRouter(AddRouter addRouter);

    @DELETE
    @Path("/{routerId}/from/{coreRouterId}")
    @Operation(operationId = "removeRouterFromCoreRouter", description = "Remove a router from a core router")
    Uni<Response> removeRouterFromCoreRouter(@PathParam("routerId") Id routerId, @PathParam("coreRouterId") Id coreRouterId);

}
