/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.nosqljpa.interfaces;

import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.AlertaDTO;
import java.util.List;

/**
 *
 * @author grupo2
 */
public interface IAlertaLogic {
    
    public AlertaDTO add(AlertaDTO dto);
    
    public AlertaDTO find(String id);
    
    public List<AlertaDTO> findAll();
            
    public List<AlertaDTO> findUbicacion(String ubicacion);
    
}
