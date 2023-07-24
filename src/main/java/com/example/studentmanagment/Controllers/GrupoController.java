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

    @PostMapping("/saveGroup")
    public void saveGroup(@RequestBody Grupo grupo){
        String sql = "INSERT Grupo (CodPeriodoAcad, CodAsignatura, NumGrupo, CupoGrupo, Horario) " +
                "VALUES (?,?,?,?,?)";
        Object[] params = {grupo.getCodPeriodoAcad(),grupo.getCodAsignatura(),grupo.getNumGrupo(),
        grupo.getCupoGrupo(),grupo.getHorario()};
        jdbcTemplate.update(sql,params);
    }


    @PostMapping("/updateGroup")
    public void updateGroup(@RequestBody Grupo grupo){
        String sql = "Update Grupo " +
                "SET CodPeriodoAcad = ?," +
                "SET CodAsignatura = ?," +
                "SET CupoGrupo = ?," +
                "SET Horario = ?" +
                "WHERE NumGrupo = ?";

        Object[] params = {grupo.getCodPeriodoAcad(), grupo.getCodAsignatura(),grupo.getCupoGrupo(),
        grupo.getHorario(),grupo.getNumGrupo()};

        jdbcTemplate.update(sql,params);
    }

    @DeleteMapping("/deleteGroup")
    public void deleteGroup(@RequestParam("NumGrupo") String numGrupo){
        String sql = "DELETE Grupo WHERE NumGrupo = ?";
        Object[] params = {numGrupo};
        jdbcTemplate.update(sql,params);
    }
}
