/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

import Datos.DUsuario;
import Datos.Usuario;
import Negocio.NUsuario;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class Guia {
     private int bandejaLength = 0;
    private POPServide guiaPopObj;
    private SMTPService guiaSmtpObj;
    private String guiaCmdo = "";
    private String guiaReceptor = "";
    private String guiaParams = "";

    public Guia() {
        guiaPopObj = new POPServide();
        bandejaLength = guiaPopObj.getBandejaLength();
    }

    public void listen() {
        guiaPopObj = new POPServide();
        boolean correoValido;
        boolean subjectValido;
        if (guiaPopObj.getBandejaLength() > bandejaLength) {
            bandejaLength++;
            System.out.println("se detecto nuevo correo en la bandeja de entrada!");
            List<String> msg = guiaPopObj.getEmail(bandejaLength);
            correoValido = checkEmisor(msg);
            subjectValido = checkSubject(msg);
            if (correoValido == true && subjectValido == true) {
                System.out.println("Comando: " + this.guiaCmdo);
                if (!this.guiaParams.equals("")) {
                    System.out.println("Parametros: " + this.guiaParams);
                }
                executeCmdo(this.guiaCmdo);
            } else {
                if (correoValido == false) {
                    System.out.println("Emisor no valido...cerrando conx");
                } else {
                    System.out.println("Subject no valido...cerrando conx");
                }
            }
        }
        guiaPopObj.close();
    }

    public boolean checkSubject(List<String> emailMsg) {
        for (String line : emailMsg) {
            if (line.contains("Subject:")) {
                String cmdo = line.split(":")[1];
                cmdo = cmdo.substring(1);
                if ("HELP".equals(cmdo)) {
                    this.guiaCmdo = cmdo;
                    return true;
                } else {
                    String cmdoVerbo = cmdo.split("\\[")[0];
                    boolean cmdoValido = checkCmd(cmdoVerbo);
                    if (cmdoValido == true) {
                        this.guiaCmdo = cmdoVerbo;
                        String parametros = cmdo.split("\\[")[1];
                        parametros = parametros.split("\\]")[0];
                        this.guiaParams = parametros;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkCmd(String cmdo) {
        switch (cmdo) {
    /*Listar todos los usuarios*/
            case "LISRUSU":
                return true;
    /*INSERTAR estudiantes, docentes, administrativos*/
            case "INSEST":
                return true;
            case "INSDOC":
                return true;
            case "INSADM":
                return true;
    /*UPDATE estudiantes, docentes, administrativos*/
            case "UPDEST":
                return true;
            case "UPDDOC":
                return true;
            case "UPDADM":
                return true;
    /*DELETE USUARIO*/         
            case "DELUSU":
                return true;
            case "VERUSU":
                return true;
            default:
                return false;
        }
    }

    public boolean checkParams(String params) {
        return false;
    }

    public boolean checkEmisor(List<String> emailMsg) {
        for (String line : emailMsg) {
            if (line.contains("Return-Path:")) {
                //guardar correo emisor
                int ini = line.indexOf("<");
                int fin = line.indexOf(">");
                String emisor = line.substring(ini + 1, fin);
                System.out.println("Emisor: " + emisor);
                this.guiaReceptor = emisor;
                NUsuario nusuObj = new NUsuario();
                return nusuObj.checkEmail(emisor);
            }
        }

        return true;
    }

    public void executeCmdo(String cmdo) {
        switch (cmdo) {
            case "HELP":
                executeHELP();
                break;
            case "LISUSU":
                executeLISUSU(this.guiaParams);
                break;
            case "INSEST":
                executeINSEST(this.guiaParams);
                break;
            case "INSDOC":
                executeINSDOC(this.guiaParams);
                break;
            case "INSADM":
                executeINSADM(this.guiaParams);
                break;
            case "UPDEST":
                executeUPDEST(this.guiaParams);
                break;
            case "UPDDOC":
                executeUPDDOC(this.guiaParams);
                break;
            case "UPDADM":
                executeUPDADM(this.guiaParams);
                break;
            case "DELUSU":
                executeDELUSU(this.guiaParams);
                break;
            case "VERUSU":
                executeVERUSU(this.guiaParams);
                break;
           //---------------------------------------//     
            default:
                break;
        }
    }

    public void executeHELP() {
        String msg
                = "Content-Type:text/html;\r\n<html>"
                + "<body>\n"
                + "  <h1> BIENVENIDO AL MENU DE COMANDOS PARA EL PROYECTO PREENTRENAMIENTO UNIVERSITARIO FCEE</h1>\n"
                + "  <h3> AYUDA: HELP </h3>\n"
                + "  <h3> Gestionar Usuario </h3>\n"
                + "  <ul>\n"
                + "      <li>LISUSU[NOMBRE,EMAIL,TELEFONO, TIPO]</li>\n" 
                + "      <li>INSEST[NOMBRE,EMAIL,PASSWORD,TELEFONO,TIPO]</li>\n"
                + "      <li>INSDOC[NOMBRE,EMAIL,PASSWORD,TELEFONO,TIPO]</li>\n"
                + "      <li>INSADM[NOMBRE,EMAIL,PASSWORD,TELEFONO,TIPO]</li>\n"
                + "      <li>UPDEST[NOMBRE,EMAIL,PASSWORD,TELEFONO,TIPO]</li>\n"
                + "      <li>UPDDOC[NOMBRE,EMAIL,PASSWORD,TELEFONO,TIPO]</li>\n"
                + "      <li>UPDADM[NOMBRE,EMAIL,PASSWORD,TELEFONO,TIPO]</li>\n"
                + "      <li>DELUSU[EMAIL]</li>\n"
                + "      <li>VERUSU[EMAIL]</li>\n"
                + "  </ul>\n"
                + "</body>"
                + "</html>";
        sendResponseEmail("Estos son los comandos disponibles", msg);
    }
    
    public void sendResponseEmail(String sbjct, String msg){
        this.guiaSmtpObj = new SMTPService();
        this.guiaSmtpObj.sendMessage(sbjct, this.guiaReceptor, msg);
        this.guiaSmtpObj.close();
    }

    /*==============================
    ===== CU1 GESTIONAR USUARIO ====
    ==============================*/
    public void executeLISUSU() {
        NUsuario nuObj = new NUsuario();
        String msg = nuObj.listarTodos();
        sendResponseEmail("RESPUESTA A PETICION LISUSU", msg);
    }

    public void executeINSEST(String params){
        NUsuario nuObj = new NUsuario();
        String msg = nuObj.registrar(params);
        sendResponseEmail("RESPUESTA A PETICION INSEST", msg);
    }
    
    public void executeINSDOC(String params){
        NUsuario nuObj = new NUsuario();
        String msg = nuObj.registrar(params);
        sendResponseEmail("RESPUESTA A PETICION INSEST", msg);
    }  
    
    public void executeINSADM(String params){
        NUsuario nuObj = new NUsuario();
        String msg = nuObj.insertar(params);
        sendResponseEmail("RESPUESTA A PETICION REGISTRAR USUARIO", msg);
    }
    /*==============================
    ===== FIN GESTIONAR USUARIO ====
    ==============================*/
}
