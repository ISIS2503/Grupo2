/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.nosqljpa.interfaces;

import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.LastMeasureDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.entity.LastMeasureEntity;
import java.util.List;

/**
 *
 * @author Grupo 2
 */
public interface ILastMeasureConverter {
    
    public LastMeasureDTO entityToDto(LastMeasureEntity entity);
    
    public LastMeasureEntity dtoToEntity(LastMeasureDTO dto);
    
    public List<LastMeasureDTO> listEntitiesToListDTOs(List<LastMeasureEntity> entities);
    
    public List<LastMeasureEntity> listDTOsToListEntities(List<LastMeasureDTO> dtos); 
    
    
}
