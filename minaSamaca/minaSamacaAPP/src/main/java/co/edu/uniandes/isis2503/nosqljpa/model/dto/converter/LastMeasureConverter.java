/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.nosqljpa.model.dto.converter;

import co.edu.uniandes.isis2503.nosqljpa.interfaces.ILastMeasureConverter;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.LastMeasureDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.entity.LastMeasureEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author js.prieto10
 */
public class LastMeasureConverter implements ILastMeasureConverter {

    public static ILastMeasureConverter CONVERTER = new LastMeasureConverter();
    
    @Override
    public LastMeasureDTO entityToDto(LastMeasureEntity entity) {
        LastMeasureDTO dto = new LastMeasureDTO();
        dto.setId(entity.getId());
        dto.setTime(entity.getTime());
        
        return dto;
    }

    @Override
    public LastMeasureEntity dtoToEntity(LastMeasureDTO dto) {
        LastMeasureEntity entity = new LastMeasureEntity();
        entity.setId(dto.getId());
        entity.setTime(dto.getTime());
        
        return entity;
    }

    @Override
    public List<LastMeasureDTO> listEntitiesToListDTOs(List<LastMeasureEntity> entities) {
              ArrayList<LastMeasureDTO> dtos = new ArrayList<>();
        for (LastMeasureEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;  
    }

    @Override
    public List<LastMeasureEntity> listDTOsToListEntities(List<LastMeasureDTO> dtos) {
                ArrayList<LastMeasureEntity> entities = new ArrayList<>();
        for (LastMeasureDTO dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }
}
