
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

    public String ver(String email) {
        String msg = "";

        if (email != null && !email.isEmpty()) {
            if (email.length() <= 0 || email.length() > 320) {
                msg = "Content-Type:text/html;\r\n<html>"
                        + "<body>\n"
                        + "  <h1>EXCEPCIÓN AL SELECCIONAR USUARIOS</h1>\n"
                        + "  <h2>COMANDO: LISTARUSU[EMAIL]</h2>\n"
                        + "  <p>Error en el parámetro 'email'. Debe tener entre 1 y 320 caracteres.</p>\n"
                        + "</body>"
                        + "</html>";
            } else {
                try {
                    DUsuario duObj = new DUsuario();
                    ArrayList<Usuario> usrsResult = duObj.ver(email);
                    if (!usrsResult.isEmpty()) {
                        StringBuilder res = new StringBuilder("<h2>Lista de Usuarios</h2>\n"
                                + "<table border=1>\n"
                                + "<tr>"
                                + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">ID</td>"
                                + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">ROL</td>"
                                + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">EMAIL</td>"
                                + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">CI</td>"
                                + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">NOMBRE COMP</td>"
                                + "</tr>\n");
                        
                        for (Usuario usr : usrsResult) {
                            res.append(usr.LISTUSUtable());
                        }
                        
                        res.append("</table>");
                        msg = "Content-Type:text/html;\r\n<html>"
                                + "<body>\n"
                                + "  <h2>COMANDO: LISTARUSU[EMAIL]</h2>\n"
                                + res.toString()
                                + "</body>"
                                + "</html>";
                    } else {
                        msg = "Content-Type:text/html;\r\n<html>"
                                + "<body>\n"
                                + "  <h2>COMANDO: LISTARUSU[EMAIL]</h2>\n"
                                + "  <h4>No se encontraron registros con el email proporcionado</h4>\n"
                                + "</body>"
                                + "</html>";
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(NUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            msg = "Content-Type:text/html;\r\n<html>"
                    + "<body>\n"
                    + "  <h1>EXCEPCIÓN AL SELECCIONAR USUARIOS</h1>\n"
                    + "  <h2>COMANDO: LISTARUSU[EMAIL]</h2>\n"
                    + "  <p>Error en el parámetro 'email'. Debe proporcionar un valor válido.</p>\n"
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

    
        

