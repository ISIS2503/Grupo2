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
    
    public void tipoVariable(String pVariable)
    {
        if (pVariable.equalsIgnoreCase("temperatura"))
        {
            this.limInf = 21.5;
            this.limSup = 27.0;
        }
        else if (pVariable.equalsIgnoreCase("ruido"))
        {
            this.limInf = 80;
            this.limSup = 85;
        }
        else if (pVariable.equalsIgnoreCase("monoxido"))
        {
            this.limInf = 0;
            this.limSup = 350;
        }
        else if (pVariable.equalsIgnoreCase("iluminacion"))
        {
            this.limInf = 100;
            this.limSup = 500;
        }
    }
    
    
    @POST
    public MeasurementDTO add(MeasurementDTO dto) {
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        String ubicacion = dto.getUbicacion();
        String variable = dto.getVariable();
        LastMeasureDTO anterior = lastMeasureLogic.find(ubicacion);
        long ant = anterior.getTime().getTime()+ 300000;
        System.out.println("Fecha anterior: "+ant+" Fecha nueva: "+dto.getFecha().getTime());
        if (ant < dto.getFecha().getTime())
        {
            System.out.println("Alerta");
        }
        LastMeasureDTO last = new LastMeasureDTO(dto.getUbicacion(), dto.getFecha());
        lastMeasureLogic.update(last);
        
        tipoVariable(variable);
        if (measurementLogic.fueraRango(ubicacion, variable, limInf, limSup))
            System.out.println("Alerta en: "+ubicacion+ " sensor de "+variable+" fuera de rango");
        
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
    
}