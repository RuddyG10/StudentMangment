package com.example.studentmanagment.Entities;

public class Estudiante {

    private String matricula;
    private String PrimerNom;
    private String SegNom;
    private String PrimerApe;
    private String SegApe;
    private String direccion;
    private String catPago;
    private String carrera;
    private String nacionalidad;

    public Estudiante() {
    }

    public Estudiante(String matricula, String primerNom, String segNom, String primerApe, String segApe, String direccion, String catPago, String carrera, String nacionalidad) {
        this.matricula = matricula;
        PrimerNom = primerNom;
        SegNom = segNom;
        PrimerApe = primerApe;
        SegApe = segApe;
        this.direccion = direccion;
        this.catPago = catPago;
        this.carrera = carrera;
        this.nacionalidad = nacionalidad;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getPrimerNom() {
        return PrimerNom;
    }

    public void setPrimerNom(String primerNom) {
        PrimerNom = primerNom;
    }

    public String getSegNom() {
        return SegNom;
    }

    public void setSegNom(String segNom) {
        SegNom = segNom;
    }

    public String getPrimerApe() {
        return PrimerApe;
    }

    public void setPrimerApe(String primerApe) {
        PrimerApe = primerApe;
    }

    public String getSegApe() {
        return SegApe;
    }

    public void setSegApe(String segApe) {
        SegApe = segApe;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCatPago() {
        return catPago;
    }

    public void setCatPago(String catPago) {
        this.catPago = catPago;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
}
