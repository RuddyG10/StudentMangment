package com.example.studentmanagment.Entities;

public class Grupo extends Horario {

    private String codPeriodoAcad;
    private String codAsignatura;
    private String numGrupo;
    private int cupoGrupo;
    private String horario;

    public Grupo() {
    }

    public Grupo(String codPeriodoAcad, String codAsignatura, String numGrupo, int cupoGrupo, String horario) {
        this.codPeriodoAcad = codPeriodoAcad;
        this.codAsignatura = codAsignatura;
        this.numGrupo = numGrupo;
        this.cupoGrupo = cupoGrupo;
        this.horario = horario;
    }

    public String getCodPeriodoAcad() {
        return codPeriodoAcad;
    }

    public void setCodPeriodoAcad(String codPeriodoAcad) {
        this.codPeriodoAcad = codPeriodoAcad;
    }

    public String getCodAsignatura() {
        return codAsignatura;
    }

    public void setCodAsignatura(String codAsignatura) {
        this.codAsignatura = codAsignatura;
    }

    public String getNumGrupo() {
        return numGrupo;
    }

    public void setNumGrupo(String numGrupo) {
        this.numGrupo = numGrupo;
    }

    public int getCupoGrupo() {
        return cupoGrupo;
    }

    public void setCupoGrupo(int cupoGrupo) {
        this.cupoGrupo = cupoGrupo;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
}
