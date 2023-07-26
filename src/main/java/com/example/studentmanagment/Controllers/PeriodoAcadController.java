package com.example.studentmanagment.Controllers;

import com.example.studentmanagment.Entities.Estudiante;
import com.example.studentmanagment.Entities.PeriodoAcad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/periodo")
public class PeriodoAcadController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @GetMapping("/listaPeriodos")
    public ResponseEntity<List<PeriodoAcad>> periodList(){
        String sql = "SELECT * FROM PeriodoAcademico";

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
    }

    @PostMapping("/savePeriodo")
    public void savePeriodo(@RequestBody PeriodoAcad periodoAcad){
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

        jdbcTemplate.update(sql,params);
    }

    @DeleteMapping("/deletePeriod")
    public void deletePeriod(@RequestParam("codPeriodoAcad") String codPeriodo){
        String sql = "DELETE FROM PeriodoAcademico WHERE CodPeriodoAcad = ?";
        Object[] params = {codPeriodo};

        jdbcTemplate.update(sql,params);
    }

    @PostMapping("/updatePeriodo")
    public void updateStudent(@RequestBody PeriodoAcad periodoAcad){
        String sql = "UPDATE PeriodoAcademico "+
                "SET Descripcion = ?," +
                "SET FechaInicio = ?," +
                "SET FechaFin = ?," +
                "SET FechaInicioClases =?," +
                "SET FechaFinClases = ?," +
                "SET FechaLimitePago = ?," +
                "SET FechaLimitePrematricula = ?," +
                "SET FechaLimiteRetiro = ?" +
                "SET FechaLimitePublicacion = ?"+
                "WHERE CodPeriodoAcad = ?";
        jdbcTemplate.update(sql,periodoAcad.getDescripcion(), periodoAcad.getFechaInicio(), periodoAcad.getFechaFin(), periodoAcad.getFechaInicioClases(), periodoAcad.getFechaFinClases(), periodoAcad.getFechaLimitePago(), periodoAcad.getFechaLimitePrematricula(), periodoAcad.getFechaLimiteRetiro(), periodoAcad.getFechaLimitePublicacion(),periodoAcad.getCodPeriodoAcad());
    }
}
