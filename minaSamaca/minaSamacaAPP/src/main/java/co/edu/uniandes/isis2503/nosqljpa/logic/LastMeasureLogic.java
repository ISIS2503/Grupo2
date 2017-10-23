/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.nosqljpa.logic;

import co.edu.uniandes.isis2503.nosqljpa.interfaces.ILastMeasureLogic;
import static co.edu.uniandes.isis2503.nosqljpa.model.dto.converter.LastMeasureConverter.CONVERTER;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.LastMeasureDTO;
import co.edu.uniandes.isis2503.nosqljpa.persistence.LastMeasurePersistence;
import java.util.List;

/**
 *
 * @author js.prieto10
 */
public class LastMeasureLogic implements ILastMeasureLogic {

    private final LastMeasurePersistence persistence;

    public LastMeasureLogic() {
        this.persistence = new LastMeasurePersistence();
        
    }
    
    
    @Override
    public LastMeasureDTO add(LastMeasureDTO dto) {
        LastMeasureDTO result = CONVERTER.entityToDto(persistence.add(CONVERTER.dtoToEntity(dto)));
        
        return result;
    }

    @Override
    public LastMeasureDTO find(String id) {
        return CONVERTER.entityToDto(persistence.find(id));
    }

    @Override
    public boolean delete(String id) {
        return persistence.delete(id);
    }

    @Override
    public boolean deleteAll() {
        return persistence.deleteAll();
    }

    @Override
    public List<LastMeasureDTO> findAll() {
        return CONVERTER.listEntitiesToListDTOs(persistence.all());
    }

    @Override
    public LastMeasureDTO update(LastMeasureDTO dto) {
            LastMeasureDTO result = CONVERTER.entityToDto(persistence.update(CONVERTER.dtoToEntity(dto)));
        return result;
    }
    
        
}
