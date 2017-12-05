/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.nosqljpa.model.dto.converter;

import co.edu.uniandes.isis2503.nosqljpa.interfaces.IAlertaConverter;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.AlertaDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.entity.AlertaEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author js.prieto10
 *
 * Funciona como wrapper para convertir de DTO a Entity
 */
public class AlertaConverter implements IAlertaConverter{

     public static IAlertaConverter CONVERTER = new AlertaConverter();


    public AlertaConverter() {
    }


    @Override
    public AlertaDTO entityToDto(AlertaEntity entity) {
        AlertaDTO dto = new AlertaDTO();
        dto.setId(entity.getId());
        dto.setDescripcion(entity.getDescripcion());
        dto.setFecha(entity.getFecha());
        dto.setUbicacion(entity.getUbicacion());
        return dto;
    }

    @Override
    public AlertaEntity dtoToEntity(AlertaDTO dto) {
        AlertaEntity entity = new AlertaEntity();
        entity.setId(dto.getId());
        entity.setDescripcion(dto.getDescripcion());
        entity.setFecha((dto.getFecha()));
        entity.setUbicacion(dto.getUbicacion());
        return entity;
    }

    @Override
    public List<AlertaDTO> listEntitesToListDto(List<AlertaEntity> entities) {
        ArrayList<AlertaDTO> dtos = new ArrayList<>();
        for (AlertaEntity entity: entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }

    @Override
    public List<AlertaEntity> listDtosToListEntities(List<AlertaDTO> dtos) {
        ArrayList<AlertaEntity> entities = new ArrayList<>();
        for (AlertaDTO dto: dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }

}
