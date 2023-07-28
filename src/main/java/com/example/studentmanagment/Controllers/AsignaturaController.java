package com.example.studentmanagment.Controllers;


import com.example.studentmanagment.Entities.Asignatura;
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
@RequestMapping("/asignatura")
public class AsignaturaController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/listAsig")
    public ResponseEntity<List<Asignatura>> asigList(){
        String sql = "SELECT * FROM Asignatura";

        try {
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
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList()); // Devuelve una lista vacía en caso de error
        }
    }

    @PostMapping("/saveAsig")
    public ResponseEntity<String> saveAsig(@RequestBody Asignatura asignatura){
        String sql = "INSERT Asignatura ([CodAsignatura]\n" +
                "           ,[Nombre]\n" +
                "           ,[Creditos]\n" +
                "           ,[HorasTeoricas]\n" +
                "           ,[HorasPracticas])\n" +
                "VALUES (?,?,?,?,?)";
        Object[] params = {asignatura.getCodAsignatura(),asignatura.getNombre(),
                asignatura.getCreditos(),asignatura.getHorasTeoricas(),asignatura.getHorasPracticas()};


        try {
            jdbcTemplate.update(sql,params);
            return ResponseEntity.ok("Se guardo la asignatura correctamente.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar la asignatura: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteAsig")
    public ResponseEntity<String> deleteAsig(@RequestParam("codAsignatura") String codAsignatura){
        String sql = "DELETE FROM Asignatura WHERE CodAsignatura = ?";
        Object[] params = {codAsignatura};


        try {
            jdbcTemplate.update(sql,params);
            return ResponseEntity.ok("Se elimino la asignatura correctamente.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar la asignatura: " + e.getMessage());
        }
    }

    @PostMapping("/updateAsig")
    public ResponseEntity<String> updateStudent(@RequestBody Asignatura asignatura){
        String sql = "UPDATE Asignatura "+
                "SET Nombre = ?," +
                " Creditos = ?," +
                " HorasTeoricas = ?," +
                " HorasPracticas = ? " +
                "WHERE CodAsignatura = ?";
        try {
            jdbcTemplate.update(sql,asignatura.getNombre(), asignatura.getCreditos(), asignatura.getHorasTeoricas(), asignatura.getHorasPracticas(), asignatura.getCodAsignatura());

            return ResponseEntity.ok("Se actualizo la asignatura correctamente.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar la asignatura: " + e.getMessage());
        }
    }
}
