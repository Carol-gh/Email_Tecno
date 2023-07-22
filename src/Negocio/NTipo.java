/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Datos.DModo;
import Datos.DTipo;
import Datos.Respuesta;
import Datos.Tipo;
import java.util.ArrayList;

/**
 *
 * @author USUARIO
 */
public class NTipo {
    
    //LISTIPO[NOMBRE] 
    public String listipo(String params) {
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
                DTipo duObj = new DTipo();
                ArrayList<Tipo> actsResult = duObj.listipos();
                if (!actsResult.isEmpty()) {
                    String res 
                        = "<h2> Lista de Tipos </h2>\n"
                        + "<table border=1>\n"
                        + "<tr>"
                        + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">ID</td>"                 
                        + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">NOMBRE</td>"
                        + "</tr>\n";
                    for (Tipo tipo : actsResult) {
                        res += tipo.toLISTIPOtable();
                    }
                    res += "</table>";
                    msg 
                        = "Content-Type:text/html;\r\n<html>"
                        + "<body>\n"
                        + "<h2> COMANDO: LISTIPO[*] </h2>\n"
                        + res
                        + "</body>"
                        + "</html>";
                    
                    return msg;
                } else {
                    msg
                        = "Content-Type:text/html;\r\n<html>"
                        + "<body>\n"
                        + "  <h2> COMANDO: LISTTIPO[*] </h2>\n"
                        + "  <h4>No se encontro registros con los parametros proporcionados</h4>\n"
                        + "</body>"
                        + "</html>";
                }
            } else {
                msg
                    = "Content-Type:text/html;\r\n<html>"
                    + "<body>\n"
                    + "  <h1> EXCEPCION AL SELECCIONAR TIPO </h1>\n"
                    + "  <h3>EXCEPCION: " + msgErr + "</h3>\n"
                    + "  <h2> COMANDO: LISTIPO[*] </h2>\n"
                    + "  <p> debe introducir un nro  '0' o >=1 al nro de registro de tipos </p>\n"
                    + "  <h3>Ejemplos</h3>\n"
                    + "  <ul>\n"
                    + "      <li>LISTIPO[0] retorna todos los tipos</li>\n"
                    + "  </ul>\n"
                    + "</body>"
                    + "</html>";
            }
        } else {
            msg
                = "Content-Type:text/html;\r\n<html>"
                + "<body>\n"
                + "  <h1> EXCEPCION AL SELECCIONAR TIPO </h1>\n"
                + "  <h2> COMANDO: LISTIPO[*] </h2>\n"
                + "  <p> Error en el parametro, debe llenar el parametros,  '0'  o >=1 </p>\n"
                + "</body>"
                + "</html>";
        }
        
        return msg;
    }
    
    //INSTIPO[NOMBRE]
    public String regtipo(String params) {
        String msg
            = "Content-Type:text/html;\r\n<html>"
            + "<body>\n";
        String[] values = params.split(",");
        if (values.length == 1) {                     
            String nombreParam = values[0].trim();
            boolean ok = true;
            String msgErr = "";
          
            if (ok == true) {
                DTipo duObj = new DTipo();
                Respuesta result = duObj.regtipo(nombreParam);
                msg
                    += "<h1> INSTIPO EJECUTADO </h1>\n"
                    +  "<h3>RESPUESTA: " + result.msg + "</h3>\n";
            } else {
                msg
                    += "<h1> EXCEPCION AL REGISTRAR TIPO </h1>\n"
                    +  "<h3>EXCEPCION: " + msgErr + "</h3>\n";
            }
        } else {
            msg
                += " <h1> EXCEPCION AL INSERTAR TIPO </h1>\n"
                + "  <h2> COMANDO: INSTIPO[NOMBRE] </h2>\n"
                + "  <p> Error en parametros, debe llenar todos los parametros</p>\n"
                + "  <h3>Ejemplo</h3>\n"
                + "  <ul>\n"
                + "      <li>INSTIPO[Dificil]</li>\n"
                + "  </ul>\n";
        }
        msg
            += "</body>"
            + "</html>";
        
        return msg;
    }

    //UPDTIPO[ID,NOMBRE]
    public String edittipo(String params) {
        String msg
            = "Content-Type:text/html;\r\n<html>"
            + "<body>\n";
        String[] values = params.split(",");
        if (values.length == 2) { 
            int idParam = Integer.parseInt(values[0].trim());       
            String nombreParam = values[1].trim();
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
            
            if (ok == true) {
                DTipo duObj = new DTipo();
                Respuesta result = duObj.edittipo(idParam, nombreParam);
                msg
                    += "<h1> UPDTIPO EJECUTADO </h1>\n"
                    +  "<h3>RESPUESTA: " + result.msg + "</h3>\n";
            } else {
                msg
                    += "<h1> EXCEPCION AL EDITAR TIPO </h1>\n"
                    +  "<h3>EXCEPCION: " + msgErr + "</h3>\n";
            }
        } else {
            msg
                += " <h1> EXCEPCION AL EDITAR TIPO </h1>\n"
                + "  <h2> COMANDO: UPDTIPO[ID,NOMBRE] </h2>\n"
                + "  <p> Error en parametros, debe llenar todos los parametros</p>\n"
                + "  <h3>Ejemplo</h3>\n"
                + "  <ul>\n"
                + "      <li>UPDTIPO[1, Medio]</li>\n"
                + "  </ul>\n";
        }
        msg
            += "</body>"
            + "</html>";
        
        return msg;
    }
   
    //DELTIPO[ID]
    public String elimtipo(String params) {
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
                DModo duObj = new DModo();
                Respuesta result = duObj.elimModo(idParam);
                msg
                    += "<h1> DELTIPO EJECUTADO </h1>\n"
                    +  "<h3>RESPUESTA: " + result.msg + "</h3>\n";
            } else {
                msg
                    += "<h1> EXCEPCION AL ELIMINAR TIPO </h1>\n"
                    +  "<h3>EXCEPCION: " + msgErr + "</h3>\n";
            }
        } else {
            msg
                += " <h1> EXCEPCION AL ELIMINAR TIPO </h1>\n"
                + "  <h2> COMANDO: DELTIPO[ID] </h2>\n"
                + "  <p> Error en parametros, debe llenar el parametro</p>\n"
                + "  <h3>Ejemplos</h3>\n"
                + "  <ul>\n"
                + "      <li>DELTIPO[1] Elimina el modo con el id=1</li>\n"
                + "      <li>DELTIPO[2] Elimina el modo con el id=2</li>\n"
                + "  </ul>\n";
        }
        msg
            += "</body>"
            + "</html>";
        
        return msg;
    }
}
