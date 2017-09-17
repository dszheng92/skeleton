package controllers;

//import api.CreateReceiptRequest;
import api.CreateTagRequest;
import api.ReceiptResponse;
import dao.ReceiptDao;
import dao.TaoDao;
import generated.tables.Tags;
import generated.tables.records.ReceiptsRecord;
import generated.tables.records.TagsRecord;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.Endpoint;
import java.util.List;


import static java.util.stream.Collectors.toList;

@Path("/tags")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TaoController {
    final TaoDao tags;

    public TaoController(TaoDao tags) {
        this.tags = tags;
    }


    @PUT
    @Path("/{tag}")
    public void toggleTag(@PathParam("tag") String tagname, Integer id) {
         tags.modify(tagname, id);
    }

    @GET
    @Path("/{tag}")
    public List<ReceiptResponse> getTags(@PathParam("tag") String tagname) {
        List<ReceiptsRecord> receiptsRecord = tags.jointable(tagname);
        return receiptsRecord.stream().map(ReceiptResponse::new).collect(toList());

    }


}
