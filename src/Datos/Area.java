/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

/**
 *
 * @author USUARIO
 */
public class Area {
    public int area_id = 0;
    public String area_nombre = "";
    public int area_puntaje = 0;

    public Area(int id, String nombre, int puntaje) {
        this.area_id = id;
        this.area_nombre = nombre;
        this.area_puntaje = puntaje;
    }
  
    public String toLISAREAtable() {
        return "<tr>"
                + "<td>" + this.area_id + "</td>"
                + "<td>" + this.area_nombre + "</td>"
                + "<td>" + this.area_puntaje + "</td>"
                + "</tr>\n";
    }
}
