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
public class Usuario {
    public int id = 0;
    public String nombre = "";
    public String email = "";
    public String telefono = "";
    public String pass = "";
    public String tipo = "";


    public Usuario(int id, String nombre, String email, String telefono, String pass, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.pass = pass;
        this.tipo = tipo;
    }
    
    public String LISTUSUtable(){
        return "<tr>" + 
                "<td>" + this.id + "</td>" +
                "<td>" + this.nombre + "</td>" + 
                "<td>" + this.email + "</td>" +
                "<td>" + this.telefono + "</td>" +
                "<td>" + this.tipo + "</td>" +
                "</tr>\n";
    }
}
