/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

/**
 *
 * @author USUARIO
 */
public class Tipo {
    public int tipo_id = 0;
    public String tipo_nombre = "";

    public Tipo(int id, String nombre) {
        this.tipo_id = id;
        this.tipo_nombre = nombre;
    }
  
    public String toLISTIPOtable() {
        return "<tr>"
                + "<td>" + this.tipo_id + "</td>"
                + "<td>" + this.tipo_nombre + "</td>"
                + "</tr>\n";
    }
}
