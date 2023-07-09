/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Datos.Area;
import Datos.DArea;
import Datos.Respuesta;
import java.util.ArrayList;

/**
 *
 * @author USUARIO
 */
public class NArea {
    
    //LISAREA[NOMBRE, PUNTAJE] 
    public String lisarea(String params) {
        String msg = "";
        String[] values = params.split(",");
        if (values.length == 1) {
            String msgErr = "";
            boolean ok = true;
            if (values[0].trim().length() <= 0) {
                ok = false;
                msgErr = "";
            }          
            if (ok == true) {
                DArea duObj = new DArea();
                ArrayList<Area> actsResult = duObj.lisarea();
                if (!actsResult.isEmpty()) {
                    String res 
                        = "<h2> Lista de Áreas </h2>\n"
                        + "<table border=1>\n"
                        + "<tr>"
                        + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">ID</td>"                 
                        + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">NOMBRE</td>"
                        + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">PUNTAJE</td>"
                        + "</tr>\n";
                    for (Area area : actsResult) {
                        res += area.toLISAREAtable();
                    }
                    res += "</table>";
                    msg 
                        = "Content-Type:text/html;\r\n<html>"
                        + "<body>\n"
                        + "<h2> COMANDO: LISAREA[*] </h2>\n"
                        + res
                        + "</body>"
                        + "</html>";
                    
                    return msg;
                } else {
                    msg
                        = "Content-Type:text/html;\r\n<html>"
                        + "<body>\n"
                        + "  <h2> COMANDO: LISTAREA[*] </h2>\n"
                        + "  <h4>No se encontro registros con los parametros proporcionados</h4>\n"
                        + "</body>"
                        + "</html>";
                }
            } else {
                msg
                    = "Content-Type:text/html;\r\n<html>"
                    + "<body>\n"
                    + "  <h1> EXCEPCION AL SELECCIONAR AREA </h1>\n"
                    + "  <h3>EXCEPCION: " + msgErr + "</h3>\n"
                    + "  <h2> COMANDO: LISAREA[*] </h2>\n"
                    + "  <p> debe introducir un nro  '0' o >=1 al nro de registro de areas </p>\n"
                    + "  <h3>Ejemplos</h3>\n"
                    + "  <ul>\n"
                    + "      <li>LISAREA[0] retorna todos las areas</li>\n"
                    + "  </ul>\n"
                    + "</body>"
                    + "</html>";
            }
        } else {
            msg
                = "Content-Type:text/html;\r\n<html>"
                + "<body>\n"
                + "  <h1> EXCEPCION AL SELECCIONAR AREA </h1>\n"
                + "  <h2> COMANDO: LISAREA[*] </h2>\n"
                + "  <p> Error en el parametro, debe llenar el parametros,  '0'  o >=1 </p>\n"

                + "</body>"
                + "</html>";
        }
        return msg;
    }
    
    //INSAREA[NOMBRE,PUNTAJE]
    public String regarea(String params) {
        String msg
            = "Content-Type:text/html;\r\n<html>"
            + "<body>\n";
        String[] values = params.split(",");
        if (values.length == 2) {                     
            String nombreParam = values[0].trim();
            int puntajeParam = Integer.parseInt(values[1].trim());
            boolean ok = true;
            String msgErr = "";
          
            if (ok == true) {
                DArea duObj = new DArea();
                Respuesta result = duObj.regarea(nombreParam, puntajeParam);
                msg
                    += "<h1> INSAREA EJECUTADO </h1>\n"
                    +  "<h3>RESPUESTA: " + result.msg + "</h3>\n";
            } else {
                msg
                    += "<h1> EXCEPCION AL REGISTRAR AREA </h1>\n"
                    +  "<h3>EXCEPCION: " + msgErr + "</h3>\n";
            }
        } else {
            msg
                += " <h1> EXCEPCION AL INSERTAR AREA </h1>\n"
                + "  <h2> COMANDO: INSAREA[NOMBRE,PUNTAJE] </h2>\n"
                + "  <p> Error en parametros, debe llenar todos los parametros</p>\n"
                + "  <h3>Ejemplo</h3>\n"
                + "  <ul>\n"
                + "      <li>INSAREA[Historia,25]</li>\n"
                + "  </ul>\n";
        }
        msg
            += "</body>"
            + "</html>";
        
        return msg;
    }

    //UPDAREA[ID,NOMBRE,PUNTAJE]
    public String editarea(String params) {
        String msg
            = "Content-Type:text/html;\r\n<html>"
            + "<body>\n";
        String[] values = params.split(",");
        if (values.length == 3) { 
            int idParam = Integer.parseInt(values[0].trim());       
            String nombreParam = values[1].trim();
            int puntajeParam = Integer.parseInt(values[2].trim());         
            boolean ok = true;
            String msgErr = "";
            
            if (values[0].trim().length() <= 0 || idParam<=0) {
                msgErr = "Id no valido debe ser id >= 1";
                ok = false;
            }
          
            if (values[1].trim().length() <= 0) {
                msgErr = "Nombre no valido, esta vacio!";
                ok = false;
            }
            if (values[2].trim().length() <=0) {
                msgErr = "Puntaje no valido, esta vacio!";
                ok = false;
            }
            
            if (ok == true) {
                DArea duObj = new DArea();
                Respuesta result = duObj.editarea(idParam, nombreParam, puntajeParam);
                msg
                    += "<h1> UPDAREA EJECUTADO </h1>\n"
                    +  "<h3>RESPUESTA: " + result.msg + "</h3>\n";
            } else {
                msg
                    += "<h1> EXCEPCION AL EDITAR AREA </h1>\n"
                    +  "<h3>EXCEPCION: " + msgErr + "</h3>\n";
            }
        } else {
            msg
                += " <h1> EXCEPCION AL EDITAR AREA </h1>\n"
                + "  <h2> COMANDO: UPDAREA[ID,NOMBRE,PUNTAJE] </h2>\n"
                + "  <p> Error en parametros, debe llenar todos los parametros</p>\n"
                + "  <h3>Ejemplo</h3>\n"
                + "  <ul>\n"
                + "      <li>EDITPER[1, Psicologia, 30]</li>\n"
                + "  </ul>\n";
        }
        msg
            += "</body>"
            + "</html>";
        
        return msg;
    }
   
    //DELAREA[ID]
    public String elimarea(String params) {
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
                DArea duObj = new DArea();
                Respuesta result = duObj.elimArea(idParam);
                msg
                    += "<h1> DELAREA EJECUTADO </h1>\n"
                    +  "<h3>RESPUESTA: " + result.msg + "</h3>\n";
            } else {
                msg
                    += "<h1> EXCEPCION AL ELIMINAR AREA </h1>\n"
                    +  "<h3>EXCEPCION: " + msgErr + "</h3>\n";
            }
        } else {
            msg
                += " <h1> EXCEPCION AL ELIMINAR AREA </h1>\n"
                + "  <h2> COMANDO: DELAREA[ID] </h2>\n"
                + "  <p> Error en parametros, debe llenar el parametro</p>\n"
                + "  <h3>Ejemplos</h3>\n"
                + "  <ul>\n"
                + "      <li>DELAREA[1] Elimina el area con el id=1</li>\n"
                + "      <li>DELAREA[2] Elimina el area con el id=2</li>\n"
                + "  </ul>\n";
        }
        msg
            += "</body>"
            + "</html>";
        
        return msg;
    }
}
