/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.nosqljpa.model.dto.model;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author js.prieto10
 */
@XmlRootElement
public class LastMeasureDTO {
    
    private String id;
    
    private Date time;
    
    public LastMeasureDTO()
    {
        
    }
    
    public LastMeasureDTO(String id, Date time) {
        this.id = id;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    
    @Override
    public String toString()
    {
        return "LastMeasure";
    }
    
    
    
    
    
    
    
    
}
