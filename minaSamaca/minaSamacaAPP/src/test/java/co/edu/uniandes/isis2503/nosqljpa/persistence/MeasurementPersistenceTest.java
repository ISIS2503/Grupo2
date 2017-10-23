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
package co.edu.uniandes.isis2503.nosqljpa.persistence;

import co.edu.uniandes.isis2503.nosqljpa.model.entity.MeasurementEntity;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import co.edu.uniandes.isis2503.nosqljpa.logic.MeasurementLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.MeasurementDTO;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ca.anzola
 */
@RunWith(Arquillian.class)
public class MeasurementPersistenceTest {
    
    MeasurementLogic instanciaLogicaMuestras = new MeasurementLogic();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MeasurementEntity.class.getPackage())
                .addPackage(MeasurementPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml");
    }
    
    //Prueba creación/agregación de DTO
    @Test
    public void pruebaCreacionTest(){   
     MeasurementDTO medida;
     Date fecha = new Date();
     medida = new MeasurementDTO("1","1",1,"1", fecha,"1");
    MeasurementDTO dtoVerificacion = instanciaLogicaMuestras.add(medida);
    assertNotNull(dtoVerificacion);
    }
    
    //Prueba update de datos DTO
    @Test
    public void pruebaModificacionTest(){
    MeasurementDTO medida;
    Date fecha = new Date();
    medida = new MeasurementDTO("1","1",1,"1",fecha,"2"); 
    MeasurementDTO dtoVerificacion = instanciaLogicaMuestras.update(medida);
    assertEquals(dtoVerificacion, medida);
    }
    
    //Prueba de busqueda de Registro por medio de ID
    @Test
    public void pruebaBusquedaTest(){
    MeasurementDTO dtoVerificacion = instanciaLogicaMuestras.find("1");
    assertNotNull(dtoVerificacion);
    }
    
    //Prueba obtencino de todos los datos QUE DICEN LOS IJUEPUTAS
    @Test
    public void pruebaObtencionTest(){
    List<MeasurementDTO> laLista = instanciaLogicaMuestras.all();
    assert(laLista.isEmpty());
    }
    
    public MeasurementPersistenceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSomeMethod() {
        assert(true);
    }
    
}
