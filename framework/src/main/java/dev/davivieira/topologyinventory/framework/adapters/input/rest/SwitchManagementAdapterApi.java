package dev.davivieira.topologyinventory.framework.adapters.input.rest;

import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.framework.adapters.input.rest.request.aswitch.CreateSwitch;
import io.smallrye.mutiny.Uni;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/switch")
@Tag(name = "Switch Operations", description = "Operations for switch management")
public interface SwitchManagementAdapterApi {

    @Transactional
    @GET
    @Path("/{id}")
    @Operation(operationId = "retrieveSwitch", description = "Retrieve a switch from an edge router")
    Uni<Response> retrieveSwitch(@PathParam("id") Id switchId);

    @Transactional
    @POST
    @Path("/create/{edgeRouterId}")
    @Operation(operationId = "createAndAddSwitchToEdgeRouter", description = "Create switch and add to an edge router")
    Uni<Response> createAndAddSwitchToEdgeRouter(CreateSwitch createSwitch, @PathParam("edgeRouterId") Id edgeRouterId);

    @Transactional
    @DELETE
    @Path("/{switchId}/from/{edgeRouterId}")
    @Operation(operationId = "removeSwitch", description = "Retrieve a router from the network inventory")
    Uni<Response> removeSwitchFromEdgeRouter(@PathParam("switchId") Id switchId, @PathParam("edgeRouterId") Id edgeRouterId);

}
