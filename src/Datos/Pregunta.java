/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

/**
 *
 * @author USUARIO
 */
public class Pregunta {
    public int pregunta_id = 0;
    public String pregunta_texto = "";
    public int pregunta_areaId = 0;
    public String pregunta_areaNombre = "";

    public Pregunta(int id, String nombre, int areaId, String areaNombre) {
        this.pregunta_id = id;
        this.pregunta_texto = nombre;
        this.pregunta_areaId = areaId;
        this.pregunta_areaNombre = areaNombre;
    }
  
    public String toLISPREGUNTAtable() {
        return "<tr>"
                + "<td>" + this.pregunta_id + "</td>"
                + "<td>" + this.pregunta_texto + "</td>"
                + "<td>" + this.pregunta_areaId + "</td>"
                + "<td>" + this.pregunta_areaNombre + "</td>"
                + "</tr>\n";
    }
}
