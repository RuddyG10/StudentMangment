package com.example.studentmanagment.Controllers;

import com.example.studentmanagment.Entities.Estudiante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student/")
@CrossOrigin("*")
public class StudentController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/studentList")
    public ResponseEntity<List<Estudiante>> studenList(){
        String sql = "SELECT * FROM Estudiante";
        List<Estudiante> estudiantes = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Estudiante estudiante = new Estudiante();
            estudiante.setMatricula(rs.getString("Matricula"));
            estudiante.setPrimerNom(rs.getString("PrimerNombre"));
            estudiante.setSegNom(rs.getString("SegundoNombre"));
            estudiante.setPrimerApe(rs.getString("PrimerApellido"));
            estudiante.setSegApe(rs.getString("SegundoApellido"));
            estudiante.setNacionalidad(rs.getString("Nacionalidad"));
            estudiante.setDireccion(rs.getString("Direccion"));
            estudiante.setCatPago(rs.getString("CatPago"));
            estudiante.setCarrera(rs.getString("Carrera"));
            return estudiante;
        });

        return ResponseEntity.ok(estudiantes);
    }

    @PostMapping("/saveStudent")
    public void saveStudent(@RequestBody Estudiante estudiante){
        String sql = "INSERT INTO Estudiante (Matricula, PrimerNombre, SegundoNombre, PrimerApellido, SegundoApellido, Direccion, Nacionalidad, Carrera, CatPago) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, estudiante.getMatricula(), estudiante.getPrimerNom(), estudiante.getSegNom(), estudiante.getPrimerApe(), estudiante.getSegApe(), estudiante.getDireccion(), estudiante.getNacionalidad(), estudiante.getCarrera(), estudiante.getCatPago());
    }

    @DeleteMapping("/deleteStudent")
    public void deleteStudent(@RequestParam("matricula") String matricula){
        String sqlQuery = "DELETE FROM Estudiante WHERE matricula = ?";
        Object[] params = {matricula};
        jdbcTemplate.update(sqlQuery,matricula);
    }
}
