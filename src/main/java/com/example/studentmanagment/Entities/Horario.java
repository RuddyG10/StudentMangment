package com.example.studentmanagment.Entities;

import java.util.Date;

public class Horario {

    private int dia;
    private Date fechaInicio;
    private Date fechaFin;

    public Horario() {
    }

    public Horario(int dia, Date fechaInicio, Date fechaFin) {
        this.dia = dia;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
}
