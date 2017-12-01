/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.nosqljpa.service;

import co.edu.uniandes.isis2503.nosqljpa.auth.Secured;
import co.edu.uniandes.isis2503.nosqljpa.interfaces.IAlertaLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.AlertaLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.AlertaDTO;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author js.prieto10
 */
@Path("/alerta")
@Produces(MediaType.APPLICATION_JSON)
public class AlertaService {
    
    private final IAlertaLogic AlertaLogic;
    
    
    

    public AlertaService() {
        this.AlertaLogic = new AlertaLogic();
    }
    
    
    @POST
    public AlertaDTO add(AlertaDTO dto)
    {
        dto.changeFecha();
        return AlertaLogic.add(dto);
    }
    
    
    @GET
    public List<AlertaDTO> findAll()
    {
        return AlertaLogic.findAll();
    }
    
    @GET
    @Path("/{id}")
    public AlertaDTO find(@PathParam("id") String id)
    {
        return AlertaLogic.find(id);
    }
    
    
    @GET
    @Path("/ubicacion/{pUbicacion}")
    public List<AlertaDTO> getUbicacion(@PathParam("pUbicacion") String ubicacion)
    {
        return AlertaLogic.findUbicacion(ubicacion);
    }
    
}
