package com.example.studentmanagment.Controllers;

import com.example.studentmanagment.Entities.Estudiante;
import com.example.studentmanagment.Entities.Grupo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/group")
public class GrupoController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/groupList")
    public ResponseEntity<List<Grupo>> groupList(){
        String sql = "SELECT * FROM Grupo";

        try {
            List<Grupo> listaGrupo = jdbcTemplate.query(sql,(rs, rowNum) -> {
                Grupo grupo = new Grupo();
                grupo.setCodPeriodoAcad(rs.getString("CodPeriodoAcad"));
                grupo.setCodAsignatura(rs.getString("CodAsignatura"));
                grupo.setNumGrupo(rs.getString("NumGrupo"));
                grupo.setCupoGrupo(rs.getInt("CupoGrupo"));
                grupo.setHorario(rs.getString("Horario"));

                return grupo;
            });

            return ResponseEntity.ok(listaGrupo);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList()); // Devuelve una lista vacía en caso de error
        }
    }


    @GetMapping("/groupSchedule")
    public ResponseEntity<List<Grupo>> groupSchedule(@RequestParam("codPeriodoAcad") String codPeriodoAcad,
                                               @RequestParam("codAsignatura") String codAsignatura,
                                               @RequestParam("numGrupo") String numGrupo){
        String sql = "SELECT * FROM GrupoHorario " +
                "WHERE CodPeriodoAcad = ? AND " +
                "CodAsignatura = ? AND " +
                "NumGrupo = ?";
        Object[] params = {codPeriodoAcad,codAsignatura,numGrupo};

        try {
            List<Grupo> grupoHorario = jdbcTemplate.query(sql,params,(rs, rowNum) -> {
                Grupo grupo1 = new Grupo();
                grupo1.setCodPeriodoAcad(codPeriodoAcad);
                grupo1.setCodAsignatura(codAsignatura);
                grupo1.setNumGrupo(numGrupo);
                grupo1.setDia(rs.getInt("Dia"));
                grupo1.setFechaInicio(rs.getDate("FechaInicial"));
                grupo1.setFechaFin(rs.getDate("FechaFinal"));
                grupo1.setHoraInicio(rs.getString("HoraInicio"));
                grupo1.setHoraFin(rs.getString("HoraFin"));

                return grupo1;
            });
            return ResponseEntity.ok(grupoHorario);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList()); // Devuelve una lista vacía en caso de error
        }
    }

    @PostMapping("/saveGroup")
    public ResponseEntity<String> saveGroup(@RequestBody Grupo grupo){
        String sql = "INSERT Grupo (CodPeriodoAcad, CodAsignatura, NumGrupo, CupoGrupo, Horario) " +
                "VALUES (?,?,?,?,?)";
        Object[] params = {grupo.getCodPeriodoAcad(),grupo.getCodAsignatura(),grupo.getNumGrupo(),
        grupo.getCupoGrupo(),grupo.getHorario()};

        try {
            jdbcTemplate.update(sql,params);

            return ResponseEntity.ok("Se guardo el grupo correctamente.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el grupo: " + e.getMessage());
        }
    }

    @PostMapping("/saveSchedule")
    public ResponseEntity<String> saveSchedule(@RequestBody Grupo grupo){
        String sql = "INSERT GrupoHorario (CodPeriodoAcad, CodAsignatura, NumGrupo,Dia,FechaInicial,FechaFinal,HoraInicio,HoraFin) " +
                "VALUES (?,?,?,?,?,?,?, ?)";

        Object[] params = {grupo.getCodPeriodoAcad(),grupo.getCodAsignatura(),grupo.getNumGrupo(),
                grupo.getDia(),grupo.getFechaInicio(),grupo.getFechaFin(),grupo.getHoraInicio(),grupo.getHoraFin()};

        try {
            jdbcTemplate.update(sql,params);

            return ResponseEntity.ok("Se guardo el horario correctamente.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el horario: " + e.getMessage());
        }
    }

    @PostMapping("/updateGroup")
    public ResponseEntity<String> updateGroup(@RequestBody Grupo grupo){
        Object[] params = {grupo.getCupoGrupo(), grupo.getHorario(),grupo.getNumGrupo(),
                grupo.getCodPeriodoAcad(), grupo.getCodAsignatura()};
        //Se actualiza primer el grupo como tal
        String sql = "Update Grupo " +
                "SET CupoGrupo = ?, "+
                " Horario = ? " +
                "WHERE NumGrupo = ? AND CodPeriodoAcad = ? AND CodAsignatura = ?";


        try {
            jdbcTemplate.update(sql,params);

            return ResponseEntity.ok("Se Actualizo el grupo correctamente.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el grupo: " + e.getMessage());
        }
    }

    @PostMapping("/updateSchedule")
    public ResponseEntity<String> updateSchedule(@RequestBody Grupo grupo){
        String sql = "Update GrupoHorario " +
                " SET Dia = ?, " +
                " FechaInicial = ?, " +
                " FechaFinal = ?, " +
                " HoraInicio = ?, " +
                "HoraFin= ? " +
                "WHERE NumGrupo = ? AND CodPeriodoAcad = ? AND CodAsignatura = ?";

        Object[] params = {grupo.getDia(),grupo.getFechaInicio(),grupo.getFechaFin(),grupo.getHoraInicio(),grupo.getHoraFin(),grupo.getNumGrupo(),
                grupo.getCodPeriodoAcad(), grupo.getCodAsignatura()};

        try {
            jdbcTemplate.update(sql,params);

            return ResponseEntity.ok("Se actualizo el horario correctamente.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el horario: " + e.getMessage());
        }
    }
    @DeleteMapping("/deleteGroup")
    public ResponseEntity<String> deleteGroup(@RequestParam("NumGrupo") String numGrupo, @RequestParam("CodPeriodoAcad") String codPeriodoAcad,
                                              @RequestParam("CodAsignatura") String codAsignatura){
        String sqlInscrip = "DELETE Inscripcion WHERE NumGrupo = ? AND CodPeriodAcad = ? AND CodAsignatura = ?";
        Object[] params = {numGrupo,codPeriodoAcad,codAsignatura};
        //Primero eliminar el horario
        String sql = "DELETE GrupoHorario WHERE NumGrupo = ? AND CodPeriodoAcad = ? AND CodAsignatura = ?";
        //Ahora se elimina el grupo
        String sqlGrupo = "DELETE Grupo WHERE NumGrupo = ? AND CodPeriodoAcad = ? AND CodAsignatura = ?";


        try {
            jdbcTemplate.update(sqlInscrip,params);
            jdbcTemplate.update(sql,params);
            jdbcTemplate.update(sqlGrupo,params);

            return ResponseEntity.ok("Se elimino el grupo correctamente.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el grupo: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteSchedule")
    public ResponseEntity<String> deleteSchedule(@RequestParam("NumGrupo") String numGrupo,@RequestParam("CodPeriodoAcad") String codPeriodoAcad,
                            @RequestParam("CodAsignatura") String codAsignatura){

        //Primero eliminar el horario
        String sql = "DELETE GrupoHorario WHERE NumGrupo = ? AND CodPeriodoAcad = ? AND CodAsignatura = ?";
        Object[] params = {numGrupo,codPeriodoAcad,codAsignatura};

        try {
            jdbcTemplate.update(sql,params);

            return ResponseEntity.ok("Se Elimino el horario correctamente.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el horario: " + e.getMessage());
        }

    }

}
