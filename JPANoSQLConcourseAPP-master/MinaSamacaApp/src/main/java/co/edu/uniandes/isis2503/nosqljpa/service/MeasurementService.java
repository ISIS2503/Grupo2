/*
 * The MIT License
 *
 * Copyright 2017 Universidad De Los Andes - Departamento de Ingeniería de Sistemas.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package co.edu.uniandes.isis2503.nosqljpa.service;

import co.edu.uniandes.isis2503.nosqljpa.interfaces.ILastMeasureLogic;
import co.edu.uniandes.isis2503.nosqljpa.interfaces.IMeasurementLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.LastMeasureLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.MeasurementLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.LastMeasureDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.MeasurementDTO;
import co.edu.uniandes.isis2503.nosqljpa.auth.Secured;
import co.edu.uniandes.isis2503.nosqljpa.auth.AuthorizationFilter.Role;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author js.prieto10
 */
@Path("/measure")
@Secured
@Produces(MediaType.APPLICATION_JSON)
public class MeasurementService {
    
    private final IMeasurementLogic measurementLogic;
    private final ILastMeasureLogic lastMeasureLogic;
    private static double limSup;
    private static double limInf;
    
    public MeasurementService()
    {
        this.measurementLogic = new MeasurementLogic();
        this.lastMeasureLogic = new LastMeasureLogic();
        this.limSup = 0;
        this.limInf = 0;
    }
    
    
    
    @POST
    public MeasurementDTO add(MeasurementDTO dto) {
        dto.changeFecha();
        return measurementLogic.add(dto);
        
    }
    
    @GET
    public List<MeasurementDTO> all() {
        return measurementLogic.all();
    }
    
    @GET
    @Path("/last")
    public MeasurementDTO findLast() {
        return measurementLogic.findLast();
    }
    
    @GET
    @Path("/{id}")
    public MeasurementDTO get(@PathParam("id") String id){
        return measurementLogic.find(id);
    }
    
    @DELETE
    @Path("/delete")
    public Response deleteAll()
    {
        measurementLogic.deleteAll();
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Room was deleted").build();
    }
    
    
    @GET
    @Path("/lastest/{ubicacion}-{variable}")
    public List<MeasurementDTO> getlastest(@PathParam("ubicacion") String pUbicacion, @PathParam("variable") String pVariable)
    {
        return measurementLogic.lastest(pUbicacion, pVariable);
    }
    
    
    @GET
    @Path("/ubicacion/{pUbicacion}")
    public List<MeasurementDTO> getUbicacion(@PathParam("pUbicacion") String ubicacion)
    {
        return measurementLogic.ubicacion(ubicacion);
    }
    
}