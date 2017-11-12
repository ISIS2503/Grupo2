/*
 * The MIT License
 *
 * Copyright 2017 Universidad De Los Andes - Departamento de Ingeniería de Sistemas.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package co.edu.uniandes.isis2503.nosqljpa.logic;

import co.edu.uniandes.isis2503.nosqljpa.interfaces.IMeasurementLogic;
import static co.edu.uniandes.isis2503.nosqljpa.model.dto.converter.MeasurementConverter.CONVERTER;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.MeasurementDTO;
import co.edu.uniandes.isis2503.nosqljpa.persistence.MeasurementPersistence;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author ca.mendoza968
 */
public class MeasurementLogic implements IMeasurementLogic {

    private final MeasurementPersistence persistence;

    public MeasurementLogic() {
        this.persistence = new MeasurementPersistence();
    }

    //Abrega un elemento de medida DTO a la base de datos
    @Override
    public MeasurementDTO add(MeasurementDTO dto) {
        if (dto.getId() == null) {
            dto.setId(UUID.randomUUID().toString());
        }
        MeasurementDTO result = CONVERTER.entityToDto(persistence.add(CONVERTER.dtoToEntity(dto)));
        return result;
    }

    @Override
    public MeasurementDTO update(MeasurementDTO dto) {
        MeasurementDTO result = CONVERTER.entityToDto(persistence.update(CONVERTER.dtoToEntity(dto)));
        return result;
    }

    @Override
    public MeasurementDTO find(String id) {
        return CONVERTER.entityToDto(persistence.find(id));
    }

    @Override
    public List<MeasurementDTO> all() {
        return CONVERTER.listEntitiesToListDTOs(persistence.all());
    }

    @Override
    public Boolean delete(String id) {
        return persistence.delete(id);
    }
    
    @Override
    public MeasurementDTO findLast(){
       return CONVERTER.entityToDto(persistence.findLast());
    }

    @Override
    public Boolean deleteAll() {
        return persistence.deleteAll();
    }
    
    @Override
    public List<MeasurementDTO> lastest(String pUbicacion, String pVariable)
    {
        return CONVERTER.listEntitiesToListDTOs(persistence.lastest(pUbicacion, pVariable));
    }

    @Override
    public Boolean fueraRango(String pUbicacion, String pVariable, double pLimInferior, double pLimSuperior) {
        List<MeasurementDTO> lista = lastest(pUbicacion, pVariable);
        if (lista.size() < 10)
            return false;
        double suma = 0;
        double resultado = 0;
        for (MeasurementDTO m : lista)
        {
            suma+=m.getValor();
        }
        resultado=suma/10;
        System.out.println(resultado+ " inferior: "+pLimInferior+" superior: "+pLimSuperior);
        if (pLimInferior <= resultado && resultado <= pLimSuperior)
            return false;
        else
            return true;
    }
    
}
