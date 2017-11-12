/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.nosqljpa.service;

import co.edu.uniandes.isis2503.nosqljpa.interfaces.ILastMeasureLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.LastMeasureLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.LastMeasureDTO;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author js.prieto10
 */
@Path("/last")
@Produces(MediaType.APPLICATION_JSON)
public class LastMeasureService {
    
    private final ILastMeasureLogic lastMeasureLogic;

    public LastMeasureService() {
        this.lastMeasureLogic = new LastMeasureLogic();
    }
    
    @POST
    public LastMeasureDTO add(LastMeasureDTO dto)
    {
        System.out.println("=============================================");
        System.out.println(dto.getTime());
        return lastMeasureLogic.add(dto);
    }
    
    @GET
    public List<LastMeasureDTO> findAll()
    {
        return lastMeasureLogic.findAll();
    }
    
    
    @PUT
    public LastMeasureDTO update(LastMeasureDTO dto)
    {
        return lastMeasureLogic.update(dto);
    }
    
    @GET
    @Path("/{id}")
    public LastMeasureDTO find(@PathParam("id") String id)
    {
        return lastMeasureLogic.find(id);
    }
    
    
}
