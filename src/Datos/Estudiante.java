/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

/**
 *
 * @author Usuario
 */
public class Estudiante {
    public int id = 0;
    public String nombre = "";
    public String email = "";
    public String telefono = "";
    public String pass = "";
    public String colegio = "";
    public String grado = "";
    public String carreraInteres = "";
    public int idUsuario = 0;


    public Estudiante(int id, String nombre, String email, String telefono, String colegio, String grado, String carreraInteres, Integer idUsuario) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.colegio = colegio;
        this.grado = grado;
        this.carreraInteres = carreraInteres;
        this.idUsuario = idUsuario;
    }
    
    public String LISTESTtable(){
        return "<tr>" + 
                "<td>" + this.id + "</td>" +
                "<td>" + this.nombre + "</td>" + 
                "<td>" + this.email + "</td>" +
                "<td>" + this.telefono + "</td>" +
                "<td>" + this.colegio + "</td>" +
                "<td>" + this.grado + "</td>" +
                "<td>" + this.colegio + "</td>" +
                "<td>" + this.idUsuario + "</td>" +
                "</tr>\n";
    }
}
