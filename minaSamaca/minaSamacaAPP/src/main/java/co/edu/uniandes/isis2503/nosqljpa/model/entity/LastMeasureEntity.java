/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.nosqljpa.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author js.prieto10
 */
@Entity
@Table(name = "LASTMEASURE")
public class LastMeasureEntity implements Serializable {
    
   @Id
   private String id;
   
   @Temporal(TemporalType.DATE)
   private Date time;
   
   public LastMeasureEntity()
   {
   
   }

    public LastMeasureEntity(String id, Date time)
    {
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
