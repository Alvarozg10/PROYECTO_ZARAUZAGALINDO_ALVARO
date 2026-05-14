package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.Test;

import com.luisdbb.tarea3AD2024base.modelo.Estado;
import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;

/**
 * Clase de pruebas unitarias para las FCT.
 * 
 * Comprueba:
 * <ul>
 *     <li>Fechas válidas</li>
 *     <li>Estados</li>
 *     <li>Datos obligatorios</li>
 * </ul>
 */
class FormacionEmpresaTest {

    /**
     * Comprueba que la fecha de fin
     * es posterior a la fecha de inicio.
     */
    @Test
    void fechasCorrectas() {

        FormacionEmpresa fct = new FormacionEmpresa();

        Date inicio = Date.valueOf("2026-05-01");
        Date fin = Date.valueOf("2026-08-01");

        fct.setFechaInicio(inicio);
        fct.setFechaFin(fin);

        assertTrue(
            fct.getFechaFin().after(
                fct.getFechaInicio()
            )
        );
    }

    /**
     * Comprueba que el estado se asigna correctamente.
     */
    @Test
    void estadoInicialCorrecto() {

        FormacionEmpresa fct = new FormacionEmpresa();

        fct.setEstado(Estado.EN_CURSO);

        assertEquals(
            Estado.EN_CURSO,
            fct.getEstado()
        );
    }

    /**
     * Verifica que la empresa no sea nula.
     */
    @Test
    void empresaNoDebeSerNull() {

        FormacionEmpresa fct = new FormacionEmpresa();

        fct.setEmpresa("Mecalux");

        assertNotNull(fct.getEmpresa());
    }
}