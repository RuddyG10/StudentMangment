package com.example.studentmanagment.Controllers;

import com.example.studentmanagment.Entities.Estudiante;
import com.example.studentmanagment.Entities.Grupo;
import com.example.studentmanagment.Entities.Horario;
import com.example.studentmanagment.Entities.HorarioClases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/student/")
@CrossOrigin("*")
public class StudentController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/studentList")
    public ResponseEntity<List<Estudiante>> studenList(){
        try {
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
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList()); // Devuelve una lista vacía en caso de error
        }

    }

    //Lista de grupos inscritos
    @GetMapping("/listGrup")
    public ResponseEntity<List<Grupo>> grupList(@RequestParam("matricula")String matricula){

        try {
            Object[] params = {matricula};
            String sql = "SELECT g.CodPeriodoAcad, g.CodAsignatura, g.NumGrupo, g.CupoGrupo, g.Horario FROM Grupo g, Inscripcion i " +
                    "WHERE g.CodAsignatura = i.CodAsignatura AND " +
                    "g.CodPeriodoAcad = i.CodPeriodAcad AND " +
                    "i.NumGrupo = g.NumGrupo AND " +
                    "i.Matricula = ?";
            List<Grupo> grupos = jdbcTemplate.query(sql, params, (rs, rowNum) -> {
                Grupo grupo = new Grupo();
                grupo.setCodPeriodoAcad(rs.getString("CodPeriodoAcad"));
                grupo.setCodAsignatura(rs.getString("CodAsignatura"));
                grupo.setNumGrupo(rs.getString("NumGrupo"));
                grupo.setCupoGrupo(rs.getInt("CupoGrupo"));
                grupo.setHorario(rs.getString("Horario"));

                return grupo;
            });
            return ResponseEntity.ok(grupos);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList()); // Devuelve una lista vacía en caso de error
        }
    }

    //Ver horario de clases
    @GetMapping("/classSchedule")
    public ResponseEntity<List<HorarioClases>> getSchedule(@RequestParam("periodo") String periodo,
                                                           @RequestParam("matricula") String matricula){
        try {
            Object[] params = {matricula,periodo};
            String sql = "EXEC dbo.sp_GetStudentSchedule @Matricula = ?, @CodPeriodoAcad = ?";
            List<HorarioClases> horarios = jdbcTemplate.query(sql, params, (rs, rowNum) -> {
                HorarioClases horarioClases = new HorarioClases();
                horarioClases.setHoraClase(rs.getString("col1"));
                horarioClases.setLunes(rs.getString("col2"));
                horarioClases.setMartes(rs.getString("col3"));
                horarioClases.setMiercoles(rs.getString("col4"));
                horarioClases.setJueves(rs.getString("col5"));
                horarioClases.setViernes(rs.getString("col6"));
                horarioClases.setSabado(rs.getString("col7"));

                return horarioClases;
            });
            return ResponseEntity.ok(horarios);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList()); // Devuelve una lista vacía en caso de error
        }

    }
    @PostMapping("/saveStudent")
    public ResponseEntity<String> saveStudent(@RequestBody Estudiante estudiante){
        String sql = "INSERT INTO Estudiante (Matricula, PrimerNombre, SegundoNombre, PrimerApellido, SegundoApellido, Direccion, Nacionalidad, Carrera, CatPago) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            jdbcTemplate.update(sql, estudiante.getMatricula(), estudiante.getPrimerNom(), estudiante.getSegNom(),
                    estudiante.getPrimerApe(), estudiante.getSegApe(), estudiante.getDireccion(), estudiante.getNacionalidad(),
                    estudiante.getCarrera(), estudiante.getCatPago());
            return ResponseEntity.ok("Se guardo el estudiante correctamente.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar estudiante: " + e.getMessage());
        }
    }

    //Inscribir estudiante
    @PostMapping("/inscribSt")
    public ResponseEntity<String> inscript(@RequestParam("codPeriod") String codPeriod,
                         @RequestParam("codAsig") String codAsig,
                         @RequestParam("matricula") String matricula,
                         @RequestParam("numGrup") String numGrup){
        Object[] params = {codPeriod,codAsig,matricula,numGrup};
        String sqlQuery = "INSERT Inscripcion (CodPeriodAcad,CodAsignatura,Matricula,NumGrupo) " +
                "VALUES (?,?,?,?)";
        try {
            jdbcTemplate.update(sqlQuery, params);
            return ResponseEntity.ok("Inscripción realizada correctamente");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en la inscripción: " + e.getMessage());
        }
    }
    @DeleteMapping("/deleteStudent")
    public ResponseEntity<String> deleteStudent(@RequestParam("matricula") String matricula){
        String sqlQuery = "DELETE FROM Estudiante WHERE matricula = ?";
        Object[] params = {matricula};

        try {
            jdbcTemplate.update(sqlQuery,params);
            return ResponseEntity.ok("Estudiante eliminado con exito.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en la eliminacion: " + e.getMessage());
        }

    }

    @PostMapping("/updateStudent")
    public ResponseEntity<String> updateStudent(@RequestBody Estudiante estudiante){
        String sql = "UPDATE Estudiante "+
                        "SET PrimerNombre = ?," +
                        " SegundoNombre = ?," +
                        " PrimerApellido = ?," +
                        " SegundoApellido =?," +
                        " Direccion = ?," +
                        " Nacionalidad = ?," +
                        " Carrera = ?," +
                        " CatPago = ? " +
                    "WHERE Matricula = ?";

        try {
            jdbcTemplate.update(sql, estudiante.getPrimerNom(),
                    estudiante.getSegNom(), estudiante.getPrimerApe(),
                    estudiante.getSegApe(), estudiante.getDireccion(),
                    estudiante.getNacionalidad(), estudiante.getCarrera(),
                    estudiante.getCatPago(), estudiante.getMatricula());
            return ResponseEntity.ok("Actualizacion lograda con exito.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en la actualizacion: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteInscrip")
    public ResponseEntity<String> deleteInscript(@RequestParam("codPeriod") String codPeriod,
                                                 @RequestParam("codAsig") String codAsig,
                                                 @RequestParam("matricula") String matricula,
                                                 @RequestParam("numGrup") String numGrup){
        Object[] params = {codPeriod,codAsig,matricula,numGrup};
        String sqlQuery = "DELETE FROM Inscripcion WHERE CodPeriodAcad = ? AND CodAsignatura = ? AND Matricula = ? AND NumGrupo = ?";
        try {
            jdbcTemplate.update(sqlQuery, params);
            return ResponseEntity.ok("Inscripción eliminada correctamente");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en la aliminacion: " + e.getMessage());
        }
    }
}
