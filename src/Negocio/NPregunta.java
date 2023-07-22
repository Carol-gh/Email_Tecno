/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Datos.DPregunta;
import Datos.Pregunta;
import Datos.Respuesta;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author USUARIO
 */
public class NPregunta {
    //LISPREG[TEXTO, AREA] 
    public String lispregunta(String params) {
        String msg = "";
        String[] values = params.split(",");
        if (values.length == 1) {
            String msgErr = "";
            String areaNombre = values[0].trim();
            boolean ok = true;
            
            if (values[0].trim().length() <= 0) {
                ok = false;
                msgErr = "";
            } 
            
            if (ok == true) {
                DPregunta duObj = new DPregunta();
                ArrayList<Pregunta> actsResult = duObj.lispregunta(areaNombre);
                if (!actsResult.isEmpty()) {
                    String res 
                        = "<h2> Lista de Preguntas por Áreas </h2>\n"
                        + "<table border=1>\n"
                        + "<tr>"
                        + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">ID</td>"                 
                        + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">PREGUNTA</td>"
                        + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">AREA</td>"
                        + "</tr>\n";
                    for (Pregunta pregunta : actsResult) {
                        res += pregunta.toLISPREGUNTAtable();
                    }
                    res += "</table>";
                    msg 
                        = "Content-Type:text/html;\r\n<html>"
                        + "<body>\n"
                        + "<h2> COMANDO: LISPREG[AREA] </h2>\n"
                        + res
                        + "</body>"
                        + "</html>";
                    
                    return msg;
                } else {
                    msg
                        = "Content-Type:text/html;\r\n<html>"
                        + "<body>\n"
                        + "  <h2> COMANDO: LISPREG[AREA] </h2>\n"
                        + "  <h4>No se encontro registros con los parametros proporcionados</h4>\n"
                        + "</body>"
                        + "</html>";
                }
            } else {
                msg
                    = "Content-Type:text/html;\r\n<html>"
                    + "<body>\n"
                    + "  <h1> EXCEPCION AL SELECCIONAR PREGUNTA </h1>\n"
                    + "  <h3>EXCEPCION: " + msgErr + "</h3>\n"
                    + "  <h2> COMANDO: LISPREG[AREA] </h2>\n"
                    + "  <p> debe introducir un nro  '0' o >= 1 al nro de registro de preguntas </p>\n"
                    + "  <h3>Ejemplos</h3>\n"
                    + "  <ul>\n"
                    + "      <li>LISAREA[0] retorna todos las preguntas</li>\n"
                    + "      <li>LISAREA[Historia] retorna todos las preguntas del area HISTORIA</li>\n"
                    + "  </ul>\n"
                    + "</body>"
                    + "</html>";
            }
        } else {
            msg
                = "Content-Type:text/html;\r\n<html>"
                + "<body>\n"
                + "  <h1> EXCEPCION AL SELECCIONAR PREGUNTA </h1>\n"
                + "  <h2> COMANDO: LISPREG[AREA] </h2>\n"
                + "  <p> Error en el parametro, debe llenar el parametros,  '0'  o >=1 </p>\n"

                + "</body>"
                + "</html>";
        }
        return msg;
    }
    
    //INSPREG[TEXTO,IDAREA]
    public String regpreg(String params) throws SQLException {
        String msg
            = "Content-Type:text/html;\r\n<html>"
            + "<body>\n";
        String[] values = params.split(",");
        if (values.length == 2) {                     
            String nombreParam = values[0].trim();
            String nombreAreaParam = values[1].trim();
            boolean ok = true;
            String msgErr = "";
          
            if (ok == true) {
                DPregunta duObj = new DPregunta();
                Respuesta result = duObj.regpregunta(nombreParam, nombreAreaParam);
                msg
                    += "<h1> INSPREG EJECUTADO </h1>\n"
                    +  "<h3>RESPUESTA: " + result.msg + "</h3>\n";
            } else {
                msg
                    += "<h1> EXCEPCION AL REGISTRAR PREGUNTA </h1>\n"
                    +  "<h3>EXCEPCION: " + msgErr + "</h3>\n";
            }
        } else {
            msg
                += " <h1> EXCEPCION AL INSERTAR PREGUNTA </h1>\n"
                + "  <h2> COMANDO: INSPREG[PREGUNTA,AREA] </h2>\n"
                + "  <p> Error en parametros, debe llenar todos los parametros</p>\n"
                + "  <h3>Ejemplo</h3>\n"
                + "  <ul>\n"
                + "      <li>INSPREG[Esto es una pregunta,Historia]</li>\n"
                + "  </ul>\n";
        }
        msg
            += "</body>"
            + "</html>";
        
        return msg;
    }

    //UPDPREG[ID,TEXTO,AREA]
    public String editpregunta(String params) throws SQLException {
        String msg
            = "Content-Type:text/html;\r\n<html>"
            + "<body>\n";
        String[] values = params.split(",");
        if (values.length == 3) { 
            int idParam = Integer.parseInt(values[0].trim());       
            String nombreParam = values[1].trim();
            String areaParam = values[2].trim();         
            boolean ok = true;
            String msgErr = "";
            
            if (values[0].trim().length() <= 0 || idParam<=0) {
                msgErr = "Id no valido debe ser id >= 1";
                ok = false;
            }
          
            if (values[1].trim().length() <= 0) {
                msgErr = "La pregunta no es valido, esta vacio!";
                ok = false;
            }
            
            if (values[2].trim().length() <=0) {
                msgErr = "Area no valida, esta vacio!";
                ok = false;
            }
            
            if (ok == true) {
                DPregunta duObj = new DPregunta();
                Respuesta result = duObj.editpregunta(idParam, nombreParam, areaParam);
                msg
                    += "<h1> UPDPREG EJECUTADO </h1>\n"
                    +  "<h3>RESPUESTA: " + result.msg + "</h3>\n";
            } else {
                msg
                    += "<h1> EXCEPCION AL EDITAR PREGUNTA </h1>\n"
                    +  "<h3>EXCEPCION: " + msgErr + "</h3>\n";
            }
        } else {
            msg
                += " <h1> EXCEPCION AL EDITAR PREGUNTA </h1>\n"
                + "  <h2> COMANDO: UPDPREG[ID,PREGUNTA,AREA] </h2>\n"
                + "  <p> Error en parametros, debe llenar todos los parametros</p>\n"
                + "  <h3>Ejemplo</h3>\n"
                + "  <ul>\n"
                + "      <li>UPDPREG[1, ¿Rsta es una pregunta editada?, LITERATURA]</li>\n"
                + "  </ul>\n";
        }
        msg
            += "</body>"
            + "</html>";
        
        return msg;
    }
   
    //DELPREG[ID]
    public String elimpreg(String params) {
        String msg
            = "Content-Type:text/html;\r\n<html>"
            + "<body>\n";
        String[] values = params.split(",");
        if (values.length == 1) {         
            int idParam = Integer.parseInt(values[0].trim());
            boolean ok = true;
            String msgErr = "";
            
            if (values[0].trim().length() <= 0) {
                msgErr = "id no valido esta vacio!";
                ok = false;
            }
            
            if (ok == true) {
                DPregunta duObj = new DPregunta();
                Respuesta result = duObj.elimPregunta(idParam);
                msg
                    += "<h1> DELPREG EJECUTADO </h1>\n"
                    +  "<h3>RESPUESTA: " + result.msg + "</h3>\n";
            } else {
                msg
                    += "<h1> EXCEPCION AL ELIMINAR PREGUNTA </h1>\n"
                    +  "<h3>EXCEPCION: " + msgErr + "</h3>\n";
            }
        } else {
            msg
                += " <h1> EXCEPCION AL ELIMINAR PREGUNTA </h1>\n"
                + "  <h2> COMANDO: DELPREG[ID] </h2>\n"
                + "  <p> Error en parametros, debe llenar el parametro</p>\n"
                + "  <h3>Ejemplos</h3>\n"
                + "  <ul>\n"
                + "      <li>DELPREG[1] Elimina la pregunta con el id=1</li>\n"
                + "      <li>DELPREG[2] Elimina la pregunta con el id=2</li>\n"
                + "  </ul>\n";
        }
        msg
            += "</body>"
            + "</html>";
        
        return msg;
    }  
}
