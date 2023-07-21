/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Datos.DRanking;
import Datos.Ranking;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class NRanking {
        
    //LISTARANKINGCOLEGIO[*]
    public String listrankingcol(String params) {
        String msg = "";
        String[] values = params.split(",");
        if (values.length == 1) {
            String colegioParam = values[0].trim();
            String msgErr = "";
            boolean ok = true;
            if (colegioParam.length() <= 0 || colegioParam.length() > 255) {
                ok = false;
                msgErr = "colegio invalido";
            }
            if (ok == true) {
                DRanking duObj = new DRanking();
                ArrayList<Ranking> usrsResult = duObj.listrankingcol(colegioParam);
                if (!usrsResult.isEmpty()) {
                    String res = "<h2> Lista de Ranking por colegio </h2>\n"
                            + "<table border=1>\n"
                            + "<tr>"
                            + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">ESTUDIANTE</td>"
                            + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">COLEGIO</td>"
                            + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">PUNTAJE</td>"
                            + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">POSICION</td>"
                            + "</tr>\n";
                    for (Ranking usr : usrsResult) {
                        res += usr.LISTRANKINGtable();
                    }
                    res += "</table>";
                    msg
                            = "Content-Type:text/html;\r\n<html>"
                            + "<body>\n"
                            + "  <h2> COMANDO: LISRANKCOL[*] </h2>\n"
                            + res
                            + "</body>"
                            + "</html>";
                    return msg;
                } else {
                    msg
                            = "Content-Type:text/html;\r\n<html>"
                            + "<body>\n"
                            + "  <h2> COMANDO: LISRANKCOL[*] </h2>\n"
                            + "  <h4>No se encontro registros con los parametros proporcionados</h4>\n"
                            + "</body>"
                            + "</html>";
                }
            } else {//AQUI
                msg   
                        = "Content-Type:text/html;\r\n<html>"
                        + "<body>\n"
                        + "  <h1> EXCEPCION AL SELECCIONAR COLEGIOS RANKING </h1>\n"
                        + "  <h3>EXCEPCION: " + msgErr + "</h3>\n"
                        + "  <h2> COMANDO: LISRANKCOL[*] </h2>\n"
                        + "  <p>Si desea obviar introduzca '0' </p>\n"
                        + "  <h3>Ejemplos</h3>\n"
                        + "  <ul>\n"
                        + "      <li>LISRANKCOL[*] retorna ranking por colegio</li>\n"
                        + "  </ul>\n"
                        + "</body>"
                        + "</html>";
            }
        } else {
            msg
                    = "Content-Type:text/html;\r\n<html>"
                    + "<body>\n"
                    + "  <h1> EXCEPCION AL SELECCIONAR COLEGIOS </h1>\n"
                    + "  <h2> COMANDO: LISRANKCOL[*] </h2>\n"
                    + "  <p> Error al listar todos, si desea obviarlo introduzca '0' </p>\n"
                    + "  <h3>Ejemplos</h3>\n"
                    + "  <ul>\n"
                    + "      <li>LISRANKCOL[*] retorna ranking por colegio</li>\n"
                    + "  </ul>\n"
                    + "</body>"
                    + "</html>";
        }
        return msg;
    }
}
