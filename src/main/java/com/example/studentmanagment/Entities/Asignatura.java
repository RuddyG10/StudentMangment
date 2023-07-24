package com.example.studentmanagment.Entities;

public class Asignatura {

    private String codAsignatura;
    private String nombre;
    private int creditos;
    private int horasTeoricas;
    private int horasPracticas;

    public Asignatura() {
    }

    public Asignatura(String codAsignatura, String nombre, int creditos, int horasTeoricas, int horasPracticas) {
        this.codAsignatura = codAsignatura;
        this.nombre = nombre;
        this.creditos = creditos;
        this.horasTeoricas = horasTeoricas;
        this.horasPracticas = horasPracticas;
    }

    public String getCodAsignatura() {
        return codAsignatura;
    }

    public void setCodAsignatura(String codAsignatura) {
        this.codAsignatura = codAsignatura;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public int getHorasTeoricas() {
        return horasTeoricas;
    }

    public void setHorasTeoricas(int horasTeoricas) {
        this.horasTeoricas = horasTeoricas;
    }

    public int getHorasPracticas() {
        return horasPracticas;
    }

    public void setHorasPracticas(int horasPracticas) {
        this.horasPracticas = horasPracticas;
    }
}
