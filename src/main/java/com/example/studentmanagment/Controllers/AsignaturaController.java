package com.example.studentmanagment.Controllers;


import com.example.studentmanagment.Entities.Asignatura;
import com.example.studentmanagment.Entities.PeriodoAcad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asignatura")
public class AsignaturaController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/listAsig")
    public ResponseEntity<List<Asignatura>> asigList(){
        String sql = "SELECT * FROM Asignatura";

        List<Asignatura> listaAsignatura = jdbcTemplate.query(sql, (rs, rowNum)->{
            Asignatura asignatura = new Asignatura();
            asignatura.setCodAsignatura(rs.getString("CodAsignatura"));
            asignatura.setNombre(rs.getString("Nombre"));
            asignatura.setCreditos(rs.getInt("Creditos"));
            asignatura.setHorasTeoricas(rs.getInt("HorasTeoricas"));
            asignatura.setHorasPracticas(rs.getInt("HorasPracticas"));

            return asignatura;
        });
        return ResponseEntity.ok(listaAsignatura);
    }

    @PostMapping("/saveAsig")
    public void saveAsig(@RequestBody Asignatura asignatura){
        String sql = "INSERT Asignatura ([CodAsignatura]\n" +
                "           ,[Nombre]\n" +
                "           ,[Creditos]\n" +
                "           ,[HorasTeoricas]\n" +
                "           ,[HorasPracticas]\n" +
                "VALUES (?,?,?,?,?)";
        Object[] params = {asignatura.getCodAsignatura(),asignatura.getNombre(),
                asignatura.getCreditos(),asignatura.getHorasTeoricas(),asignatura.getHorasPracticas()};

        jdbcTemplate.update(sql,params);
    }

    @DeleteMapping("/deleteAsig")
    public void deleteAsig(@RequestParam("codAsignatura") String codAsignatura){
        String sql = "DELETE FROM Asignatura WHERE CodAsignatura = ?";
        Object[] params = {codAsignatura};

        jdbcTemplate.update(sql,params);
    }

    @PostMapping("/updateAsig")
    public void updateStudent(@RequestBody Asignatura asignatura){
        String sql = "UPDATE Asignatura "+
                "SET Nombre = ?," +
                "SET Creditos = ?," +
                "SET HorasTeoricas = ?," +
                "SET HorasPracticas =?," +
                "WHERE CodAsignatura = ?";
        jdbcTemplate.update(sql,asignatura.getNombre(), asignatura.getCreditos(), asignatura.getHorasTeoricas(), asignatura.getHorasPracticas(), asignatura.getCodAsignatura());
    }
}
