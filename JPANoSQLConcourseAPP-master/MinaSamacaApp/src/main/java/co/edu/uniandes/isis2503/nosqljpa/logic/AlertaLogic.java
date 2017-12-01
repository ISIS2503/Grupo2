/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.nosqljpa.logic;

import co.edu.uniandes.isis2503.nosqljpa.interfaces.IAlertaLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.AlertaDTO;
import co.edu.uniandes.isis2503.nosqljpa.persistence.AlertaPersistence;
import static co.edu.uniandes.isis2503.nosqljpa.model.dto.converter.AlertaConverter.CONVERTER;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author js.prieto10
 */
public class AlertaLogic implements IAlertaLogic{

    private final AlertaPersistence persistence;

    public AlertaLogic() {
        this.persistence = new AlertaPersistence();
    }
    
    
    
    @Override
    public AlertaDTO add(AlertaDTO dto) {
            if (dto.getId() == null) {
            dto.setId(UUID.randomUUID().toString());
        }
        AlertaDTO result = CONVERTER.entityToDto(persistence.add(CONVERTER.dtoToEntity(dto)));
        return result;
    }

    @Override
    public AlertaDTO find(String id) {
        return CONVERTER.entityToDto(persistence.find(id));
    }

    @Override
    public List<AlertaDTO> findAll() {
        return CONVERTER.listEntitesToListDto(persistence.all());
    }

    @Override
    public List<AlertaDTO> findUbicacion(String ubicacion) {
        return CONVERTER.listEntitesToListDto(persistence.findUbicacion(ubicacion));
    }
    
}
