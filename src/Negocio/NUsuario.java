
package Negocio;

import Datos.DUsuario;
import Datos.Usuario;
import Datos.Message;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NUsuario {

    public boolean checkEmail(String email) {
        DUsuario dusuObj = new DUsuario();
        boolean existe = dusuObj.checkEmail(email);
        return existe;
    }
    
     //LISTARUSU[EMAIL, CI, FULLNAME]
    public String listarusu(String params) {
        String msg = "";
        String[] values = params.split(",");
        if (values.length == 2) {
            String emailParam = values[1].trim();
            String fullnameParam = values[0].trim();
            String msgErr = "";
            boolean ok = true;
            if (emailParam.length() <= 0 || emailParam.length() > 320) {
                ok = false;
                msgErr = "Email invalido";
            }
            if (ok == true) {
                DUsuario duObj = new DUsuario();
                ArrayList<Usuario> usrsResult = duObj.listarTodos();
                if (!usrsResult.isEmpty()) {
                    String res = "<h2> Lista de Usuarios </h2>\n"
                            + "<table border=1>\n"
                            + "<tr>"
                            + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">ID</td>"
                            + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">NOMBRE</td>"
                            + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">TELEFONO</td>"
                            + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">EMAIL</td>"
                            + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">TIPO</td>"
                            + "</tr>\n";
                    for (Usuario usr : usrsResult) {
                        res += usr.LISTUSUtable();
                    }
                    res += "</table>";
                    msg
                            = "Content-Type:text/html;\r\n<html>"
                            + "<body>\n"
                            + "  <h2> COMANDO: LISTARUSU[EMAIL,FULLNAME] </h2>\n"
                            + res
                            + "</body>"
                            + "</html>";
                    return msg;
                } else {
                    msg
                            = "Content-Type:text/html;\r\n<html>"
                            + "<body>\n"
                            + "  <h2> COMANDO: LISTARUSU[EMAIL,FULLNAME] </h2>\n"
                            + "  <h4>No se encontro registros con los parametros proporcionados</h4>\n"
                            + "</body>"
                            + "</html>";
                }
            } else {//AQUI
                msg   
                        = "Content-Type:text/html;\r\n<html>"
                        + "<body>\n"
                        + "  <h1> EXCEPCION AL SELECCIONAR PERSONAS </h1>\n"
                        + "  <h3>EXCEPCION: " + msgErr + "</h3>\n"
                        + "  <h2> COMANDO: LISTARUSU[EMAIL,FULLNAME] </h2>\n"
                        + "  <p>Si desea obviar alguno introduzca '0' </p>\n"
                        + "  <h3>Ejemplos</h3>\n"
                        + "  <ul>\n"
                        + "      <li>LISTARUSU[0, 0] retorna todos los usuarios</li>\n"
                        + "      <li>LISTARUSU[0, juan] retorna usuarios con nombre 'juan'</li>\n"
                        + "      <li>LISTARUSU[@outlook, pedro] retorna usuarios con correo '@outlook' y nombre 'pedro'</li>\n"
                        + "  </ul>\n"
                        + "</body>"
                        + "</html>";
            }
        } else {
            msg
                    = "Content-Type:text/html;\r\n<html>"
                    + "<body>\n"
                    + "  <h1> EXCEPCION AL SELECCIONAR USUARIOS </h1>\n"
                    + "  <h2> COMANDO: LISTARUSU[EMAIL, CI, FULLNAME] </h2>\n"
                    + "  <p> Error en parametros, debe llenar todos los parametros, si desea obviar alguno introduzca '0' </p>\n"
                    + "  <h3>Ejemplos</h3>\n"
                    + "  <ul>\n"
                    + "      <li>LISTARUSU[0, 0] retorna todos los usuarios</li>\n"
                    + "      <li>LISTARUSU[0, juan] retorna usuarios con nombre 'juan'</li>\n"
                    + "      <li>LISTARUSU[@outlook, pedro] retorna usuarios con correo '@outlook' y nombre 'pedro'</li>\n"
                    + "  </ul>\n"
                    + "</body>"
                    + "</html>";
        }
        return msg;
    }

    //REGUSU[NOMBRE,EMAIL,TELEFONO,PASSWORD,TIPO]
    public String registrar(String params) {
        String msg
                = "Content-Type:text/html;\r\n<html>"
                + "<body>\n";
        String[] values = params.split(",");
        if (values.length == 5) {
            String nombreParam = values[0].trim();
            String emailParam = values[1].trim();
            String telefonoParam = values[2].trim();
            String passwordParam = values[3].trim();
            String tipoParam = values[4].trim();
            boolean ok = true;
            String msgErr = "";
            if (tipoParam.length() <= 0 || Generic.esEntero(tipoParam) == false) {
                msgErr = "Tipo no valido";
                ok = false;
            }
            if (emailParam.length() <= 0 || Generic.esEmailValido(emailParam) == false) {
                msgErr = "Email no valido";
                ok = false;
            }
            if (passwordParam.length() < 5 || passwordParam.length() > 30) {
                msgErr = "Password no valida (muy larga o corta, almenos 5 caracteres maximo 30)";
                ok = false;
            }
            if (telefonoParam.length() > 8 ) {
                msgErr = "telefono no valido";
                ok = false;
            }
            if (nombreParam.length() < 5 || nombreParam.length() > 100 ) {
                msgErr = "Nombre completo invalido (almenos 5 caracteres maximo 100)";
                ok = false;
            }
            if (ok == true) {
                DUsuario duObj = new DUsuario();
                Message result = duObj.registrar(nombreParam, emailParam, telefonoParam, passwordParam, tipoParam);
                msg
                        += "<h1> REGUSU EJECUTADO </h1>\n"
                        +  "<h3>RESPUESTA: " + result.msg + "</h3>\n";
            } else {
                msg
                        += "<h1> EXCEPCION AL REGISTRAR USUARIO </h1>\n"
                        +  "<h3>EXCEPCION: " + msgErr + "</h3>\n";
            }
        } else {
            msg
                    += " <h1> EXCEPCION AL INSERTAR USUARIO </h1>\n"
                    + "  <h2> COMANDO: REGUSU[ROL,EMAIL,PASSWORD,CI,FULLNAME] </h2>\n"
                    + "  <p> Error en parametros, debe llenar todos los parametros</p>\n"
                    + "  <h3>Ejemplos</h3>\n"
                    + "  <ul>\n"
                    + "      <li>REGUSU[1, pedro@gmail.com, pedro123, 443433, pedro camacho hernandez]</li>\n"
                    + "      <li>REGUSU[2, sara@onmicrosoft.com, saraTheGoat, 989918,sara gutierrez anillas]</li>\n"
                    + "  </ul>\n";
        }
        msg
                += "</body>"
                + "</html>";
        return msg;
    }
}

    
        

