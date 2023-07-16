
package Negocio;

import Datos.DUsuario;
import Datos.Usuario;
import Datos.Message;
import Datos.Respuesta;
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
    
    //LISTARUSU[NAME, EMAIL]
    public String listarusutodos(String params) {
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
                            + "  <h2> COMANDO: LISUSU[*] </h2>\n"
                            + res
                            + "</body>"
                            + "</html>";
                    return msg;
                } else {
                    msg
                            = "Content-Type:text/html;\r\n<html>"
                            + "<body>\n"
                            + "  <h2> COMANDO: LISUSUTODOS[*] </h2>\n"
                            + "  <h4>No se encontro registros con los parametros proporcionados</h4>\n"
                            + "</body>"
                            + "</html>";
                }
            } else {//AQUI
                msg   
                        = "Content-Type:text/html;\r\n<html>"
                        + "<body>\n"
                        + "  <h1> EXCEPCION AL SELECCIONAR USUARIOS </h1>\n"
                        + "  <h3>EXCEPCION: " + msgErr + "</h3>\n"
                        + "  <h2> COMANDO: LISUSUTODOS[*] </h2>\n"
                        + "  <p>Si desea obviar alguno introduzca '0' </p>\n"
                        + "  <h3>Ejemplos</h3>\n"
                        + "  <ul>\n"
                        + "      <li>LISUSU[0] retorna todos los usuarios</li>\n"
                        + "  </ul>\n"
                        + "</body>"
                        + "</html>";
            }
        } else {
            msg
                    = "Content-Type:text/html;\r\n<html>"
                    + "<body>\n"
                    + "  <h1> EXCEPCION AL SELECCIONAR USUARIOS </h1>\n"
                    + "  <h2> COMANDO: LISUSUTODOS[*] </h2>\n"
                    + "  <p> Error en parametros, debe llenar todos los parametros, si desea obviar alguno introduzca '0' </p>\n"
                    + "  <h3>Ejemplos</h3>\n"
                    + "  <ul>\n"
                    + "      <li>LISUSU[0] retorna todos los usuarios</li>\n"
                    + "  </ul>\n"
                    + "</body>"
                    + "</html>";
        }
        return msg;
    }
    
     //LISTARUSU[NAME, EMAIL]
    public String listarusu(String params) {
        String msg = "";
        String[] values = params.split(",");
        if (values.length == 2) {
            String nameParam = values[0].trim();
            String emailParam = values[1].trim();
            String msgErr = "";
            boolean ok = true;
            if (emailParam.length() <= 0 || emailParam.length() > 320) {
                ok = false;
                msgErr = "Email invalido";
            }
            if (ok == true) {
                DUsuario duObj = new DUsuario();
                ArrayList<Usuario> usrsResult = duObj.listarusu(nameParam,emailParam);
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
                            + "  <h2> COMANDO: LISUSU[NAME, EMAIL] </h2>\n"
                            + res
                            + "</body>"
                            + "</html>";
                    return msg;
                } else {
                    msg
                            = "Content-Type:text/html;\r\n<html>"
                            + "<body>\n"
                            + "  <h2> COMANDO: LISUSU[NAME, EMAIL] </h2>\n"
                            + "  <h4>No se encontro registros con los parametros proporcionados</h4>\n"
                            + "</body>"
                            + "</html>";
                }
            } else {//AQUI
                msg   
                        = "Content-Type:text/html;\r\n<html>"
                        + "<body>\n"
                        + "  <h1> EXCEPCION AL SELECCIONAR USUARIOS </h1>\n"
                        + "  <h3>EXCEPCION: " + msgErr + "</h3>\n"
                        + "  <h2> COMANDO: LISUSU[NAME,EMAIL] </h2>\n"
                        + "  <p>Si desea obviar alguno introduzca '0' </p>\n"
                        + "  <h3>Ejemplos</h3>\n"
                        + "  <ul>\n"
                        + "      <li>LISUSU[0, 0] retorna todos los usuarios</li>\n"
                        + "      <li>LISUSU[juan, 0] retorna usuarios con nombre 'juan'</li>\n"
                        + "      <li>LISUSU[pedro, @outlook] retorna usuarios con correo '@outlook' y nombre 'pedro'</li>\n"
                        + "  </ul>\n"
                        + "</body>"
                        + "</html>";
            }
        } else {
            msg
                    = "Content-Type:text/html;\r\n<html>"
                    + "<body>\n"
                    + "  <h1> EXCEPCION AL SELECCIONAR USUARIOS </h1>\n"
                    + "  <h2> COMANDO: LISTARUSU[NAME, EMAIL] </h2>\n"
                    + "  <p> Error en parametros, debe llenar todos los parametros, si desea obviar alguno introduzca '0' </p>\n"
                    + "  <h3>Ejemplos</h3>\n"
                    + "  <ul>\n"
                    + "      <li>LISUSU[0, 0] retorna todos los usuarios</li>\n"
                    + "      <li>LISUSU[juan, 0] retorna usuarios con nombre 'juan'</li>\n"
                    + "      <li>LISUSU[pedro, @outlook] retorna usuarios con correo '@outlook' y nombre 'pedro'</li>\n"
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
                    + "  <h2> COMANDO: REGUSU[NAME,EMAIL,TELEFONO,PASSWORD,TIPO] </h2>\n"
                    + "  <p> Error en parametros, debe llenar todos los parametros</p>\n"
                    + "  <h3>Ejemplos</h3>\n"
                    + "  <ul>\n"
                    + "      <li>REGUSU[1, pedro gutierrez vargas, pedro@gmail.com, 74944397, 12345678, E]</li>\n"
                    + "      <li>REGUSU[2, sara leon deliz, sara@onmicrosoft.com, 68931912, 989918, E]</li>\n"
                    + "  </ul>\n";
        }
        msg
                += "</body>"
                + "</html>";
        return msg;
    }
    
     //REGDOC[NOMBRE,EMAIL,TELEFONO,PASSWORD,TIPO,ESPECIALIDAD]
    public String registrarDoc(String params) {
        String msg
                = "Content-Type:text/html;\r\n<html>"
                + "<body>\n";
        String[] values = params.split(",");
        if (values.length == 6) {
            String nombreParam = values[0].trim();
            String emailParam = values[1].trim();
            String telefonoParam = values[2].trim();
            String passwordParam = values[3].trim();
            String tipoParam = values[4].trim();
            String especialidadParam = values[5].trim();
            boolean ok = true;
            String msgErr = "";
            if (tipoParam.length() <= 0 ) {
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
            if (especialidadParam.length() < 5 || especialidadParam.length() > 100 ) {
                msgErr = "Nombre colegio invalido (almenos 5 caracteres maximo 100)";
                ok = false;
            }
            if (ok == true) {
                DUsuario duObj = new DUsuario();
                Message result = duObj.registrarDoc(nombreParam, emailParam, telefonoParam, passwordParam, tipoParam, especialidadParam );
                msg
                        += "<h1> INSDOC EJECUTADO </h1>\n"
                        +  "<h3>RESPUESTA: " + result.msg + "</h3>\n";
            } else {
                msg
                        += "<h1> EXCEPCION AL REGISTRAR DOCENTE </h1>\n"
                        +  "<h3>EXCEPCION: " + msgErr + "</h3>\n";
            }
        } else {
            msg
                    += " <h1> EXCEPCION AL INSERTAR DOCENTE </h1>\n"
                    + "  <h2> COMANDO: INSDOC[NAME,EMAIL,TELEFONO,PASSWORD,TIPO,ESPECIALIDAD] </h2>\n"
                    + "  <p> Error en parametros, debe llenar todos los parametros</p>\n"
                    + "  <h3>Ejemplos</h3>\n"
                    + "  <ul>\n"
                    + "      <li>INSDOC[pedro gutierrez vargas, pedro@gmail.com, 74944397, 12345678, D, Matematicas]</li>\n"
                    + "      <li>INSDOC[sara leon deliz, sara@onmicrosoft.com, 68931912, 989918, D, Literatura]</li>\n"
                    + "  </ul>\n";
        }
        msg
                += "</body>"
                + "</html>";
        return msg;
    }
    
    //REGADM[NOMBRE,EMAIL,TELEFONO,PASSWORD,TIPO,CARGO]
    public String registrarAdm(String params) {
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
            String cargoParam = values[5].trim();
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
            if (cargoParam.length() < 5 || cargoParam.length() > 100 ) {
                msgErr = "Cargo invalido (almenos 5 caracteres maximo 100)";
                ok = false;
            }
            if (ok == true) {
                DUsuario duObj = new DUsuario();
                Message result = duObj.registrarAdm(nombreParam, emailParam, telefonoParam, passwordParam, tipoParam, cargoParam );
                msg
                        += "<h1>INSADM EJECUTADO </h1>\n"
                        +  "<h3>RESPUESTA: " + result.msg + "</h3>\n";
            } else {
                msg
                        += "<h1> EXCEPCION AL REGISTRAR ADMINISTRATIVO </h1>\n"
                        +  "<h3>EXCEPCION: " + msgErr + "</h3>\n";
            }
        } else {
            msg
                    += " <h1> EXCEPCION AL INSERTAR ADMINISTRATIVOS </h1>\n"
                    + "  <h2> COMANDO: INSADM[NAME,EMAIL,TELEFONO,PASSWORD,TIPO,ESPECIALIDAD] </h2>\n"
                    + "  <p> Error en parametros, debe llenar todos los parametros</p>\n"
                    + "  <h3>Ejemplos</h3>\n"
                    + "  <ul>\n"
                    + "      <li>INSADM[pedro gutierrez vargas, pedro@gmail.com, 74944397, 12345678, D, Jefe Sistemas]</li>\n"
                    + "      <li>INSADM[sara leon deliz, sara@onmicrosoft.com, 68931912, 989918, D, Secretario]</li>\n"
                    + "  </ul>\n";
        }
        msg
                += "</body>"
                + "</html>";
        return msg;
    }
    
    //UPDEST[ID,NOMBRE,EMAIL,TELEFONO,PASSWORD, TIPO,COLEGIO,CARRERAINTERES,GRADO]
    public String editest(String params) {
        String msg
            = "Content-Type:text/html;\r\n<html>"
            + "<body>\n";
        String[] values = params.split(",");
        if (values.length == 3) { 
            int idParam = Integer.parseInt(values[0].trim());       
            String nombreParam = values[1].trim();
            String emailParam = (values[2].trim());       
            String telefonoParam = (values[3].trim());  
            String passwordParam = (values[4].trim());  
            String tipoParam = (values[5].trim());  
            String colegioParam = (values[6].trim()); 
            String carrerainteresParam = (values[7].trim()); 
            String gradoParam = (values[8].trim()); 
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
                msgErr = "Email no valido, esta vacio!";
                ok = false;
            }
            if (values[3].trim().length() <=0) {
                msgErr = "Telefono no valido, esta vacio!";
                ok = false;
            }
            if (values[4].trim().length() <=0) {
                msgErr = "Password no valido, esta vacio!";
                ok = false;
            }
            if (values[5].trim().length() <=0) {
                msgErr = "Tipo no valido, esta vacio!";
                ok = false;
            }
            if (values[6].trim().length() <=0) {
                msgErr = "Colegio no valido, esta vacio!";
                ok = false;
            }
            if (values[7].trim().length() <=0) {
                msgErr = "CarreraInteres no valido, esta vacio!";
                ok = false;
            }
            if (values[8].trim().length() <=0) {
                msgErr = "Grado no valido, esta vacio!";
                ok = false;
            }
            if (ok == true) {
                DUsuario duObj = new DUsuario();
                Respuesta result = duObj.editest(idParam, nombreParam,emailParam,telefonoParam,passwordParam,tipoParam,colegioParam,carrerainteresParam,gradoParam );
                msg
                    += "<h1> UPDEST EJECUTADO </h1>\n"
                    +  "<h3>RESPUESTA: " + result.msg + "</h3>\n";
            } else {
                msg
                    += "<h1> EXCEPCION AL EDITAR ESTUDIANTE </h1>\n"
                    +  "<h3>EXCEPCION: " + msgErr + "</h3>\n";
            }
        } else {
            msg
                += " <h1> EXCEPCION AL EDITAR ESTUDIANTE </h1>\n"
                + "  <h2> COMANDO: UPDEST[ID,NOMBRE,EMAIL,TELEFONO,PASSWORD, TIPO,COLEGIO,CARRERAINTERES,GRADO] </h2>\n"
                + "  <p> Error en parametros, debe llenar todos los parametros</p>\n"
                + "  <h3>Ejemplo</h3>\n"
                + "  <ul>\n"
                + "      <li>UPDEST[1, Juana, juana@gmail.com, 74944397, 12345678,E, Don Bosco, Economia, Quinto]</li>\n"
                + "  </ul>\n";
        }
        msg
            += "</body>"
            + "</html>";
        
        return msg;
    }
    
    //UPDDOC[ID,NOMBRE,EMAIL,TELEFONO,PASSWORD, TIPO,ESPECIALIDAD]
    public String editdoc(String params) {
        String msg
            = "Content-Type:text/html;\r\n<html>"
            + "<body>\n";
        String[] values = params.split(",");
        if (values.length == 6) { 
            int idParam = Integer.parseInt(values[0].trim());       
            String nombreParam = values[1].trim();
            String emailParam = (values[2].trim());       
            String telefonoParam = (values[3].trim());  
            String passwordParam = (values[4].trim());  
            String tipoParam = (values[5].trim());  
            String especialidadParam = (values[6].trim());  
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
                msgErr = "Email no valido, esta vacio!";
                ok = false;
            }
            if (values[3].trim().length() <=0) {
                msgErr = "Telefono no valido, esta vacio!";
                ok = false;
            }
            if (values[4].trim().length() <=0) {
                msgErr = "Password no valido, esta vacio!";
                ok = false;
            }
            if (values[5].trim().length() <=0) {
                msgErr = "Tipo no valido, esta vacio!";
                ok = false;
            }
            if (values[6].trim().length() <=0) {
                msgErr = "Especialidad no valido, esta vacio!";
                ok = false;
            }
            if (ok == true) {
                DUsuario duObj = new DUsuario();
                Respuesta result = duObj.editdoc(idParam, nombreParam,emailParam,telefonoParam,passwordParam,tipoParam,especialidadParam);
                msg
                    += "<h1> UPDDOC EJECUTADO </h1>\n"
                    +  "<h3>RESPUESTA: " + result.msg + "</h3>\n";
            } else {
                msg
                    += "<h1> EXCEPCION AL EDITAR DOCENTE </h1>\n"
                    +  "<h3>EXCEPCION: " + msgErr + "</h3>\n";
            }
        } else {
            msg
                += " <h1> EXCEPCION AL EDITAR DOCENTE </h1>\n"
                + "  <h2> COMANDO: UPDEST[ID,NOMBRE,EMAIL,TELEFONO,PASSWORD, TIPO,ESPECIALIDAD] </h2>\n"
                + "  <p> Error en parametros, debe llenar todos los parametros</p>\n"
                + "  <h3>Ejemplo</h3>\n"
                + "  <ul>\n"
                + "      <li>UPDEST[1, Juana, juana@gmail.com, 74944397, 12345678,E, Literatura]</li>\n"
                + "  </ul>\n";
        }
        msg
            += "</body>"
            + "</html>";
        
        return msg;
    }

    //UPDEST[ID,NOMBRE,EMAIL,TELEFONO,PASSWORD, TIPO,COLEGIO,CARRERAINTERES,GRADO]
    public String editadm(String params) {
        String msg
            = "Content-Type:text/html;\r\n<html>"
            + "<body>\n";
        String[] values = params.split(",");
        if (values.length == 3) { 
            int idParam = Integer.parseInt(values[0].trim());       
            String nombreParam = values[1].trim();
            String emailParam = (values[2].trim());       
            String telefonoParam = (values[3].trim());  
            String passwordParam = (values[4].trim());  
            String tipoParam = (values[5].trim());  
            String cargoParam = (values[6].trim()); 
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
                msgErr = "Email no valido, esta vacio!";
                ok = false;
            }
            if (values[3].trim().length() <=0) {
                msgErr = "Telefono no valido, esta vacio!";
                ok = false;
            }
            if (values[4].trim().length() <=0) {
                msgErr = "Password no valido, esta vacio!";
                ok = false;
            }
            if (values[5].trim().length() <=0) {
                msgErr = "Tipo no valido, esta vacio!";
                ok = false;
            }
            if (values[6].trim().length() <=0) {
                msgErr = "Cargo no valido, esta vacio!";
                ok = false;
            }
            if (ok == true) {
                DUsuario duObj = new DUsuario();
                Respuesta result = duObj.editadm(idParam, nombreParam,emailParam,telefonoParam,passwordParam,tipoParam,cargoParam );
                msg
                    += "<h1> UPDADM EJECUTADO </h1>\n"
                    +  "<h3>RESPUESTA: " + result.msg + "</h3>\n";
            } else {
                msg
                    += "<h1> EXCEPCION AL EDITAR ADMINISTRATIVO </h1>\n"
                    +  "<h3>EXCEPCION: " + msgErr + "</h3>\n";
            }
        } else {
            msg
                += " <h1> EXCEPCION AL EDITAR ADMINISTRATIVO </h1>\n"
                + "  <h2> COMANDO: UPDEST[ID,NOMBRE,EMAIL,TELEFONO,PASSWORD, TIPO,CARGO] </h2>\n"
                + "  <p> Error en parametros, debe llenar todos los parametros</p>\n"
                + "  <h3>Ejemplo</h3>\n"
                + "  <ul>\n"
                + "      <li>UPDEST[1, Juana, juana@gmail.com, 74944397, 12345678,E, Don Bosco, Jefe de carrera]</li>\n"
                + "  </ul>\n";
        }
        msg
            += "</body>"
            + "</html>";
        
        return msg;
    }
    
    //DELUSU[ID]
    public String elimusu (String params){
        String msg
            = "Content-Type:text/html;\r\n<html>"
            + "<body>\n";
        String[] values = params.split(",");
        if (values.length == 1){
            int idParam = Integer.parseInt(values[0].trim());
            boolean ok = true;
            String msgErr = "";
            
            if (values[0].trim().length() <= 0) {
                msgErr = "id no valido esta vacio!";
                ok = false;
            }
            
            if (ok == true) {
                DUsuario duObj = new DUsuario();
                Respuesta result = duObj.delete(idParam);
                msg
                    += "<h1> DELMODO EJECUTADO </h1>\n"
                    +  "<h3>RESPUESTA: " + result.msg + "</h3>\n";
            } else {
                msg
                    += "<h1> EXCEPCION AL ELIMINAR MODO </h1>\n"
                    +  "<h3>EXCEPCION: " + msgErr + "</h3>\n";
            } 
        }
        return msg;  //arreglar
    }

    //REGEST[NOMBRE,EMAIL,TELEFONO,PASSWORD,TIPO,COLEGIO, CARRERAINTERES, GRADO]
    public String registrarEst(String params) {
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
            String colegioParam = values[5].trim();
            String carrerainteresParam = values[6].trim();
            String gradoParam = values[7].trim();
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
            if (colegioParam.length() < 5 || colegioParam.length() > 100 ) {
                msgErr = "Nombre colegio invalido (almenos 5 caracteres maximo 100)";
                ok = false;
            }
            if (carrerainteresParam.length() < 5 || carrerainteresParam.length() > 100 ) {
                msgErr = "Nombre de carrera invalido (almenos 5 caracteres maximo 100)";
                ok = false;
            }
            if (gradoParam.length() < 0 || gradoParam.length() > 10 ) {
                msgErr = "Grado invalido (almenos 1 caracteres maximo 10)";
                ok = false;
            }
            if (ok == true) {
                DUsuario duObj = new DUsuario();
                Message result = duObj.registrarEst(nombreParam, emailParam, telefonoParam, passwordParam, tipoParam,colegioParam,carrerainteresParam, gradoParam );
                msg
                        += "<h1> INSEST EJECUTADO </h1>\n"
                        +  "<h3>RESPUESTA: " + result.msg + "</h3>\n";
            } else {
                msg
                        += "<h1> EXCEPCION AL REGISTRAR ESTUDIANTE </h1>\n"
                        +  "<h3>EXCEPCION: " + msgErr + "</h3>\n";
            }
        } else {
            msg
                    += " <h1> EXCEPCION AL INSERTAR ESTUDIANTE </h1>\n"
                    + "  <h2> COMANDO: INSEST[NAME,EMAIL,TELEFONO,PASSWORD,TIPO,COLEGIO, CARRERAINTERES,GRADO] </h2>\n"
                    + "  <p> Error en parametros, debe llenar todos los parametros</p>\n"
                    + "  <h3>Ejemplos</h3>\n"
                    + "  <ul>\n"
                    + "      <li>INSEST[pedro gutierrez vargas, pedro@gmail.com, 74944397, 12345678, E, Don Bosco, Economía, Quinto]</li>\n"
                    + "      <li>INSEST[sara leon deliz, sara@onmicrosoft.com, 68931912, 989918, E, Don Bosco, Finanzas, Sexto]</li>\n"
                    + "  </ul>\n";
        }
        msg
                += "</body>"
                + "</html>";
        return msg;
    }
        
}

    
        

