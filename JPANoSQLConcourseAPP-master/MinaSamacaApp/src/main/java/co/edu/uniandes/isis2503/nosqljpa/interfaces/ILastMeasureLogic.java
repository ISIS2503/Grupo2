/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.nosqljpa.interfaces;

import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.LastMeasureDTO;
import java.util.List;

/**
 *
 * @author grupo2
 */
public interface ILastMeasureLogic {
    public LastMeasureDTO add(LastMeasureDTO dto);
    public LastMeasureDTO find(String id);
    public boolean delete(String id);
    public boolean deleteAll();
    public List<LastMeasureDTO> findAll();
    public LastMeasureDTO update(LastMeasureDTO dto);
    
}
