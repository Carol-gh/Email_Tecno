/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

/**
 *
 * @author USUARIO
 */
public class Modo {
    public int modo_id = 0;
    public String modo_nombre = "";

    public Modo(int id, String nombre) {
        this.modo_id = id;
        this.modo_nombre = nombre;
    }
  
    public String toLISMODOtable() {
        return "<tr>"
                + "<td>" + this.modo_id + "</td>"
                + "<td>" + this.modo_nombre + "</td>"
                + "</tr>\n";
    }
}
