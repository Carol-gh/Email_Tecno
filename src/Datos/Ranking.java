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
public class Ranking {
    public String estudiante = "";
    public String colegio = "";
    public String puntaje = "";
    public String posicion = "";


    public Ranking(String estudiante, String colegio, String puntaje, String posicion) {
        this.estudiante = estudiante;
        this.colegio = colegio;
        this.puntaje = puntaje;
        this.posicion = posicion;
    }
    
    public String LISTRANKINGtable(){
        return "<tr>" + 
                "<td>" + this.estudiante + "</td>" + 
                "<td>" + this.colegio + "</td>" + 
                "<td>" + this.puntaje + "</td>" +
                "<td>" + this.posicion + "</td>" +
                "</tr>\n";
    }   

}
