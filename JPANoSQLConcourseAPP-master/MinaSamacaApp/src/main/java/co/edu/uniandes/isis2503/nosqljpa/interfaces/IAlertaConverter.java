/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.nosqljpa.interfaces;

import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.AlertaDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.entity.AlertaEntity;
import java.util.List;

/**
 *
 * @author js.prieto10
 */
public interface IAlertaConverter {
    
    
    public AlertaDTO entityToDto(AlertaEntity entity);
    
    public AlertaEntity dtoToEntity(AlertaDTO dto);
    
    public List<AlertaDTO> listEntitesToListDto(List<AlertaEntity> entities);
    
    public List<AlertaEntity> listDtosToListEntities(List<AlertaDTO> dto);
    
}
