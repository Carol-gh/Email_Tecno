/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

/**
 *
 * @author USUARIO
 */
public class RespuestaPreg {
    public int respuesta_id = 0;
    public String respuesta_texto = "";
    public boolean respuesta_correcta = false;
    public int respuesta_preguntaId = 0;
    public String respuesta_pregunta = "";

    public RespuestaPreg(int id, String texto, boolean correcta, int preguntaId, String pregunta) {
        this.respuesta_id = id;
        this.respuesta_texto = texto;
        this.respuesta_correcta = correcta;
        this.respuesta_preguntaId = preguntaId;
        this.respuesta_pregunta = pregunta;
    }
  
    public String toLISRESPUESTAtable() {
        return "<tr>"
                + "<td>" + this.respuesta_id + "</td>"
                + "<td>" + this.respuesta_texto + "</td>"
                + "<td>" + this.respuesta_correcta + "</td>"
                + "<td>" + this.respuesta_preguntaId + "</td>"
                + "<td>" + this.respuesta_pregunta + "</td>"
                + "</tr>\n";
    }
}
