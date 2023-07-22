/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Datos.DModo;
import Datos.Modo;
import Datos.Respuesta;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author USUARIO
 */
public class NModo {
    
    //LISMODO[NOMBRE] 
    public String lismodo(String params) {
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
                DModo duObj = new DModo();
                ArrayList<Modo> actsResult = duObj.lismodos();
                if (!actsResult.isEmpty()) {
                    String res 
                        = "<h2> Lista de Modos </h2>\n"
                        + "<table border=1>\n"
                        + "<tr>"
                        + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">ID</td>"                 
                        + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">NOMBRE</td>"
                        + "</tr>\n";
                    for (Modo modo : actsResult) {
                        res += modo.toLISMODOtable();
                    }
                    res += "</table>";
                    msg 
                        = "Content-Type:text/html;\r\n<html>"
                        + "<body>\n"
                        + "<h2> COMANDO: LISMODO[*] </h2>\n"
                        + res
                        + "</body>"
                        + "</html>";
                    
                    return msg;
                } else {
                    msg
                        = "Content-Type:text/html;\r\n<html>"
                        + "<body>\n"
                        + "  <h2> COMANDO: LISMODO[*] </h2>\n"
                        + "  <h4>No se encontro registros con los parametros proporcionados</h4>\n"
                        + "</body>"
                        + "</html>";
                }
            } else {
                msg
                    = "Content-Type:text/html;\r\n<html>"
                    + "<body>\n"
                    + "  <h1> EXCEPCION AL SELECCIONAR MODO </h1>\n"
                    + "  <h3>EXCEPCION: " + msgErr + "</h3>\n"
                    + "  <h2> COMANDO: LISMODO[*] </h2>\n"
                    + "  <p> debe introducir un nro  '0' o >=1 al nro de registro de modos </p>\n"
                    + "  <h3>Ejemplos</h3>\n"
                    + "  <ul>\n"
                    + "      <li>LISMODO[0] retorna todos los modos</li>\n"
                    + "  </ul>\n"
                    + "</body>"
                    + "</html>";
            }
        } else {
            msg
                = "Content-Type:text/html;\r\n<html>"
                + "<body>\n"
                + "  <h1> EXCEPCION AL SELECCIONAR MODO </h1>\n"
                + "  <h2> COMANDO: LISMODO[*] </h2>\n"
                + "  <p> Error en el parametro, debe llenar el parametros,  '0'  o >=1 </p>\n"

                + "</body>"
                + "</html>";
        }
        
        return msg;
    }
    
    //INSMODO[NOMBRE]
    public String regmodo(String params) {
        String msg
            = "Content-Type:text/html;\r\n<html>"
            + "<body>\n";
        String[] values = params.split(",");
        if (values.length == 1) {                     
            String nombreParam = values[0].trim();
            boolean ok = true;
            String msgErr = "";
          
            if (ok == true) {
                DModo duObj = new DModo();
                Respuesta result = duObj.regmodo(nombreParam);
                msg
                    += "<h1> INSMODO EJECUTADO </h1>\n"
                    +  "<h3>RESPUESTA: " + result.msg + "</h3>\n";
            } else {
                msg
                    += "<h1> EXCEPCION AL REGISTRAR MODO </h1>\n"
                    +  "<h3>EXCEPCION: " + msgErr + "</h3>\n";
            }
        } else {
            msg
                += " <h1> EXCEPCION AL INSERTAR MODO </h1>\n"
                + "  <h2> COMANDO: INSMODO[NOMBRE] </h2>\n"
                + "  <p> Error en parametros, debe llenar todos los parametros</p>\n"
                + "  <h3>Ejemplo</h3>\n"
                + "  <ul>\n"
                + "      <li>INSMODO[Duelo]</li>\n"
                + "  </ul>\n";
        }
        msg
            += "</body>"
            + "</html>";
        
        return msg;
    }

    //UPDAREA[ID,NOMBRE]
    public String editmodo(String params) {
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
                DModo duObj = new DModo();
                Respuesta result = duObj.editmodo(idParam, nombreParam);
                msg
                    += "<h1> UPDMODO EJECUTADO </h1>\n"
                    +  "<h3>RESPUESTA: " + result.msg + "</h3>\n";
            } else {
                msg
                    += "<h1> EXCEPCION AL EDITAR MODO </h1>\n"
                    +  "<h3>EXCEPCION: " + msgErr + "</h3>\n";
            }
        } else {
            msg
                += " <h1> EXCEPCION AL EDITAR MODO </h1>\n"
                + "  <h2> COMANDO: UPDMODO[ID,NOMBRE] </h2>\n"
                + "  <p> Error en parametros, debe llenar todos los parametros</p>\n"
                + "  <h3>Ejemplo</h3>\n"
                + "  <ul>\n"
                + "      <li>EDITMODO[1, Duelo]</li>\n"
                + "  </ul>\n";
        }
        msg
            += "</body>"
            + "</html>";
        
        return msg;
    }
   
    //DELMODO[ID]
    public String elimmodo(String params) {
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
                    += "<h1> DELMODO EJECUTADO </h1>\n"
                    +  "<h3>RESPUESTA: " + result.msg + "</h3>\n";
            } else {
                msg
                    += "<h1> EXCEPCION AL ELIMINAR MODO </h1>\n"
                    +  "<h3>EXCEPCION: " + msgErr + "</h3>\n";
            }
        } else {
            msg
                += " <h1> EXCEPCION AL ELIMINAR MODO </h1>\n"
                + "  <h2> COMANDO: DELAREA[ID] </h2>\n"
                + "  <p> Error en parametros, debe llenar el parametro</p>\n"
                + "  <h3>Ejemplos</h3>\n"
                + "  <ul>\n"
                + "      <li>DELMODO[1] Elimina el modo con el id=1</li>\n"
                + "      <li>DELMODO[2] Elimina el modo con el id=2</li>\n"
                + "  </ul>\n";
        }
        msg
            += "</body>"
            + "</html>";
        
        return msg;
    }
}
