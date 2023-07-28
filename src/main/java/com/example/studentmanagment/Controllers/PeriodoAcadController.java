package com.example.studentmanagment.Controllers;

import com.example.studentmanagment.Entities.Estudiante;
import com.example.studentmanagment.Entities.PeriodoAcad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/periodo")
public class PeriodoAcadController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @GetMapping("/listaPeriodos")
    public ResponseEntity<List<PeriodoAcad>> periodList(){
        String sql = "SELECT * FROM PeriodoAcademico";

        try {
            List<PeriodoAcad> listaPeriodo = jdbcTemplate.query(sql, (rs,rowNum)->{
                PeriodoAcad periodoAcad = new PeriodoAcad();
                periodoAcad.setCodPeriodoAcad(rs.getString("CodPeriodoAcad"));
                periodoAcad.setDescripcion(rs.getString("Descripcion"));
                periodoAcad.setFechaInicio(rs.getDate("FechaInicio"));
                periodoAcad.setFechaFin(rs.getDate("FechaFin"));
                periodoAcad.setFechaInicioClases(rs.getDate("FechaInicioClases"));
                periodoAcad.setFechaFinClases(rs.getDate("FechaFinClases"));
                periodoAcad.setFechaLimitePago(rs.getDate("FechaLimitePago"));
                periodoAcad.setFechaLimitePrematricula(rs.getDate("FechaLimitePrematricula"));
                periodoAcad.setFechaLimiteRetiro(rs.getDate("FechaLimiteRetiro"));
                periodoAcad.setFechaLimitePublicacion(rs.getDate("FechaLimitePublicacion"));
                return periodoAcad;
            });
            return ResponseEntity.ok(listaPeriodo);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList()); // Devuelve una lista vacía en caso de error
        }

    }

    @PostMapping("/savePeriodo")
    public ResponseEntity<String> savePeriodo(@RequestBody PeriodoAcad periodoAcad){
        String sql = "INSERT PeriodoAcademico ([CodPeriodoAcad]\n" +
                "           ,[Descripcion]\n" +
                "           ,[FechaInicio]\n" +
                "           ,[FechaFin]\n" +
                "           ,[FechaInicioClases]\n" +
                "           ,[FechaFinClases]\n" +
                "           ,[FechaLimitePago]\n" +
                "           ,[FechaLimitePrematricula]\n" +
                "           ,[FechaLimiteRetiro]\n" +
                "           ,[FechaLimitePublicacion])" +
                "VALUES (?,?,?,?,?,?,?,?,?,?)";
        Object[] params = {periodoAcad.getCodPeriodoAcad(),periodoAcad.getDescripcion(),
        periodoAcad.getFechaInicio(),periodoAcad.getFechaFin(),periodoAcad.getFechaInicioClases(),periodoAcad.getFechaFinClases(),
        periodoAcad.getFechaLimitePago(),periodoAcad.getFechaLimitePrematricula(),periodoAcad.getFechaLimiteRetiro(),
        periodoAcad.getFechaLimitePublicacion()};

        try {
            jdbcTemplate.update(sql,params);

            return ResponseEntity.ok("Se guardo el periodo correctamente.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el periodo: " + e.getMessage());
        }
    }

    @DeleteMapping("/deletePeriod")
    public ResponseEntity<String> deletePeriod(@RequestParam("codPeriodoAcad") String codPeriodo){
        String sql = "DELETE FROM PeriodoAcademico WHERE CodPeriodoAcad = ?";
        Object[] params = {codPeriodo};


        try {
            jdbcTemplate.update(sql,params);

            return ResponseEntity.ok("Se elimino el periodo correctamente.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el periodo: " + e.getMessage());
        }
    }

    @PostMapping("/updatePeriodo")
    public ResponseEntity<String> updateStudent(@RequestBody PeriodoAcad periodoAcad){
        String sql = "UPDATE PeriodoAcademico "+
                "SET Descripcion = ?, " +
                " FechaInicio = ?, " +
                " FechaFin = ?, " +
                " FechaInicioClases =?, " +
                " FechaFinClases = ?, " +
                " FechaLimitePago = ?, " +
                " FechaLimitePrematricula = ?, " +
                " FechaLimiteRetiro = ?, " +
                " FechaLimitePublicacion = ? "+
                "WHERE CodPeriodoAcad = ?";
        try {
            jdbcTemplate.update(sql,periodoAcad.getDescripcion(), periodoAcad.getFechaInicio(), periodoAcad.getFechaFin(), periodoAcad.getFechaInicioClases(), periodoAcad.getFechaFinClases(), periodoAcad.getFechaLimitePago(), periodoAcad.getFechaLimitePrematricula(), periodoAcad.getFechaLimiteRetiro(), periodoAcad.getFechaLimitePublicacion(),periodoAcad.getCodPeriodoAcad());


            return ResponseEntity.ok("Se Actualizo el periodo correctamente.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el periodo: " + e.getMessage());
        }
    }

}
