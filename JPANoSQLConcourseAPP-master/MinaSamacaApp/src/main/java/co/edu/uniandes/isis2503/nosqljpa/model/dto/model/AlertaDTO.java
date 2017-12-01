/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.nosqljpa.model.dto.model;

import java.text.ParseException;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author js.prieto10
 */
@XmlRootElement
public class AlertaDTO {
    private String id;
    private String descripcion;
    private String ubicacion;
    private String fecha;
    
    
    public AlertaDTO()
    {
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String f) {
        this.fecha = f;
    }
    
    public void changeFecha()
    {
        Long lo = Long.parseLong(fecha);
        Date da = new Date(lo);
        this.fecha = da.toString();
    }
    
    
    
    
}
