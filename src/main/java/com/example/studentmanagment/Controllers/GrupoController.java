package com.example.studentmanagment.Controllers;

import com.example.studentmanagment.Entities.Grupo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GrupoController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/groupList")
    public ResponseEntity<List<Grupo>> groupList(){
        String sql = "SELECT * FROM Grupo";
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
        if (grupoHorario != null){
            return ResponseEntity.ok(grupoHorario);

        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/saveGroup")
    public void saveGroup(@RequestBody Grupo grupo){
        String sql = "INSERT Grupo (CodPeriodoAcad, CodAsignatura, NumGrupo, CupoGrupo, Horario) " +
                "VALUES (?,?,?,?,?)";
        Object[] params = {grupo.getCodPeriodoAcad(),grupo.getCodAsignatura(),grupo.getNumGrupo(),
        grupo.getCupoGrupo(),grupo.getHorario()};
        jdbcTemplate.update(sql,params);
    }

    @PostMapping("/saveSchedule")
    public void saveSchedule(@RequestBody Grupo grupo){
        String sql = "INSERT GrupoHorario (CodPeriodoAcad, CodAsignatura, NumGrupo,Dia,FechaInicial,FechaFinal,HoraInicio,HoraFin) " +
                "VALUES (?,?,?,?,?,?,?, ?)";

        Object[] params = {grupo.getCodPeriodoAcad(),grupo.getCodAsignatura(),grupo.getNumGrupo(),
                grupo.getDia(),grupo.getFechaInicio(),grupo.getFechaFin(),grupo.getHoraInicio(),grupo.getHoraFin()};
        jdbcTemplate.update(sql,params);
    }

    @PostMapping("/updateGroup")
    public void updateGroup(@RequestBody Grupo grupo){
        Object[] params = {grupo.getCupoGrupo(), grupo.getHorario(),grupo.getNumGrupo(),
                grupo.getCodPeriodoAcad(), grupo.getCodAsignatura()};
        //Se actualiza primer el grupo como tal
        String sql = "Update Grupo " +
                "SET CupoGrupo = ?, "+
                " Horario = ? " +
                "WHERE NumGrupo = ? AND CodPeriodoAcad = ? AND CodAsignatura = ?";
        jdbcTemplate.update(sql,params);

//        //Revisar si el grupo tiene un horario para que sea actualizado
//        List<Grupo> grupoHorario = groupSchedule(grupo.getCodPeriodoAcad(),grupo.getCodAsignatura(),grupo.getNumGrupo()).getBody();
//        if (!grupoHorario.isEmpty()){
//            updateSchedule(grupo);
//        }
    }

    @PostMapping("/updateSchedule")
    public void updateSchedule(@RequestBody Grupo grupo){
        String sql = "Update GrupoHorario " +
                " SET Dia = ?, " +
                " FechaInicial = ?, " +
                " FechaFinal = ?, " +
                " HoraInicio = ?, " +
                "HoraFin= ? " +
                "WHERE NumGrupo = ? AND CodPeriodoAcad = ? AND CodAsignatura = ?";

        Object[] params = {grupo.getDia(),grupo.getFechaInicio(),grupo.getFechaFin(),grupo.getHoraInicio(),grupo.getHoraFin(),grupo.getNumGrupo(),
                grupo.getCodPeriodoAcad(), grupo.getCodAsignatura()};

        jdbcTemplate.update(sql,params);
    }
    @DeleteMapping("/deleteGroup")
    public void deleteGroup(@RequestParam("NumGrupo") String numGrupo,@RequestParam("CodPeriodoAcad") String codPeriodoAcad,
                            @RequestParam("CodAsignatura") String codAsignatura){

        //Primero eliminar el horario
        String sql = "DELETE GrupoHorario WHERE NumGrupo = ? AND CodPeriodoAcad = ? AND CodAsignatura = ?";
        Object[] params = {numGrupo,codPeriodoAcad,codAsignatura};
        jdbcTemplate.update(sql,params);

        //Ahora se elimina el grupo
        String sqlGrupo = "DELETE Grupo WHERE NumGrupo = ? AND CodPeriodoAcad = ? AND CodAsignatura = ?";

        jdbcTemplate.update(sqlGrupo,params);
    }

    @DeleteMapping("/deleteSchedule")
    public void deleteSchedule(@RequestParam("NumGrupo") String numGrupo,@RequestParam("CodPeriodoAcad") String codPeriodoAcad,
                            @RequestParam("CodAsignatura") String codAsignatura){

        //Primero eliminar el horario
        String sql = "DELETE GrupoHorario WHERE NumGrupo = ? AND CodPeriodoAcad = ? AND CodAsignatura = ?";
        Object[] params = {numGrupo,codPeriodoAcad,codAsignatura};
        jdbcTemplate.update(sql,params);

    }

}
