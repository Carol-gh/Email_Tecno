/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Datos.DPregunta;
import Datos.DRespuesta;
import Datos.Pregunta;
import Datos.Respuesta;
import Datos.RespuestaPreg;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author USUARIO
 */
public class NRespuesta {
    
    //LISRESP[IDPREGUNTA] 
    public String lisrespuesta(String params) {
        String msg = "";
        String[] values = params.split(",");
        if (values.length == 1) {
            String msgErr = "";
            int idPregunta = Integer.parseInt(values[0].trim());
            boolean ok = true;
            
            if (values[0].trim().length() <= 0) {
                ok = false;
                msgErr = "";
            } 
            
            if (ok == true) {
                DRespuesta duObj = new DRespuesta();
                ArrayList<RespuestaPreg> actsResult = duObj.lisrespuesta(idPregunta);
                if (!actsResult.isEmpty()) {
                    String res 
                        = "<h2> Lista de Respuestas </h2>\n"
                        + "<table border=1>\n"
                        + "<tr>"
                        + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">ID</td>"                 
                        + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">RESPUESTA</td>"
                        + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">CORRECTA</td>"
                        + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">PREGUNTA ID</td>"
                        + "</tr>\n";
                    for (RespuestaPreg respuesta : actsResult) {
                        res += respuesta.toLISRESPUESTAtable();
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
                        + "  <h2> COMANDO: LISRESP[PREGUNTA] </h2>\n"
                        + "  <h4>No se encontro registros con los parametros proporcionados</h4>\n"
                        + "</body>"
                        + "</html>";
                }
            } else {
                msg
                    = "Content-Type:text/html;\r\n<html>"
                    + "<body>\n"
                    + "  <h1> EXCEPCION AL SELECCIONAR RESPUESTA </h1>\n"
                    + "  <h3>EXCEPCION: " + msgErr + "</h3>\n"
                    + "  <h2> COMANDO: LISRESP[PREGUNTA] </h2>\n"
                    + "  <p> debe introducir un nro  '0' o >= 1 al nro de registro de respuestas </p>\n"
                    + "  <h3>Ejemplos</h3>\n"
                    + "  <ul>\n"
                    + "      <li>LISRESP[0] retorna todos las respuestas</li>\n"
                    + "      <li>LISRESP[1] retorna todos las respuestas de la pregunta 1</li>\n"
                    + "  </ul>\n"
                    + "</body>"
                    + "</html>";
            }
        } else {
            msg
                = "Content-Type:text/html;\r\n<html>"
                + "<body>\n"
                + "  <h1> EXCEPCION AL SELECCIONAR RESPUESTA </h1>\n"
                + "  <h2> COMANDO: LISRESP[PREGUNTA] </h2>\n"
                + "  <p> Error en el parametro, debe llenar el parametros,  '0'  o >=1 </p>\n"

                + "</body>"
                + "</html>";
        }
        return msg;
    }
    
    //INSRESP[TEXTO,CORRECTA,IDPREGUNTA]
    public String regrespuesta(String params) {
        String msg
            = "Content-Type:text/html;\r\n<html>"
            + "<body>\n";
        String[] values = params.split(",");
        if (values.length == 2) {                     
            String textoParam = values[0].trim();
            boolean correctaParam = Boolean.parseBoolean(values[1].trim());
            int idPreguntaParam = Integer.parseInt(values[2].trim());
            boolean ok = true;
            String msgErr = "";
          
            if (ok == true) {
                DRespuesta duObj = new DRespuesta();
                Respuesta result = duObj.regrespuesta(textoParam, correctaParam, idPreguntaParam);
                msg
                    += "<h1> INSRESP EJECUTADO </h1>\n"
                    +  "<h3>RESPUESTA: " + result.msg + "</h3>\n";
            } else {
                msg
                    += "<h1> EXCEPCION AL REGISTRAR RESPUESTA </h1>\n"
                    +  "<h3>EXCEPCION: " + msgErr + "</h3>\n";
            }
        } else {
            msg
                += " <h1> EXCEPCION AL INSERTAR RESPUES </h1>\n"
                + "  <h2> COMANDO: INSRESP[TEXTO,CORRECTA,IDPREGUNTA] </h2>\n"
                + "  <p> Error en parametros, debe llenar todos los parametros</p>\n"
                + "  <h3>Ejemplo</h3>\n"
                + "  <ul>\n"
                + "      <li>INSRESP[Respuesta 1,false,1]</li>\n"
                + "  </ul>\n";
        }
        msg
            += "</body>"
            + "</html>";
        
        return msg;
    }

    //UPDRESP[ID,TEXTO,CORRECTA,IDPREGUNTA]
    public String editrespuesta(String params) throws SQLException {
        String msg
            = "Content-Type:text/html;\r\n<html>"
            + "<body>\n";
        String[] values = params.split(",");
        if (values.length == 3) { 
            int idParam = Integer.parseInt(values[0].trim());       
            String nombreParam = values[1].trim();
            boolean correctaParam = Boolean.parseBoolean(values[2].trim());  
            int preguntaParam = Integer.parseInt(values[3].trim());
            boolean ok = true;
            String msgErr = "";
            
            if (values[0].trim().length() <= 0 || idParam<=0) {
                msgErr = "Id no valido debe ser id >= 1";
                ok = false;
            }
          
            if (values[1].trim().length() <= 0) {
                msgErr = "La respuesta no es valido, esta vacio!";
                ok = false;
            }
            
            if (values[3].trim().length() <=0) {
                msgErr = "idPregunta no valida, esta vacio!";
                ok = false;
            }
            
            if (ok == true) {
                DRespuesta duObj = new DRespuesta();
                Respuesta result = duObj.editrespuesta(idParam, nombreParam, correctaParam, preguntaParam);
                msg
                    += "<h1> UPDRESP EJECUTADO </h1>\n"
                    +  "<h3>RESPUESTA: " + result.msg + "</h3>\n";
            } else {
                msg
                    += "<h1> EXCEPCION AL EDITAR RESPUESTA </h1>\n"
                    +  "<h3>EXCEPCION: " + msgErr + "</h3>\n";
            }
        } else {
            msg
                += " <h1> EXCEPCION AL EDITAR RESPUESTA </h1>\n"
                + "  <h2> COMANDO: UPDRESP[ID,RESPUESTA,CORRECTA,IDPREGUNTA] </h2>\n"
                + "  <p> Error en parametros, debe llenar todos los parametros</p>\n"
                + "  <h3>Ejemplo</h3>\n"
                + "  <ul>\n"
                + "      <li>UPDRESP[1, Respuesta editada, false, 20]</li>\n"
                + "  </ul>\n";
        }
        msg
            += "</body>"
            + "</html>";
        
        return msg;
    }
   
    //DELRESP[ID]
    public String elimrespuesta(String params) {
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
                DRespuesta duObj = new DRespuesta();
                Respuesta result = duObj.elimrespuesta(idParam);
                msg
                    += "<h1> DELRESP EJECUTADO </h1>\n"
                    +  "<h3>RESPUESTA: " + result.msg + "</h3>\n";
            } else {
                msg
                    += "<h1> EXCEPCION AL ELIMINAR RESPUESTA </h1>\n"
                    +  "<h3>EXCEPCION: " + msgErr + "</h3>\n";
            }
        } else {
            msg
                += " <h1> EXCEPCION AL ELIMINAR RESPUESTA </h1>\n"
                + "  <h2> COMANDO: DELRESP[ID] </h2>\n"
                + "  <p> Error en parametros, debe llenar el parametro</p>\n"
                + "  <h3>Ejemplos</h3>\n"
                + "  <ul>\n"
                + "      <li>DELRESP[1] Elimina la respuesta con el id=1</li>\n"
                + "      <li>DELRESP[2] Elimina la respuesta con el id=2</li>\n"
                + "  </ul>\n";
        }
        msg
            += "</body>"
            + "</html>";
        
        return msg;
    }  
}
