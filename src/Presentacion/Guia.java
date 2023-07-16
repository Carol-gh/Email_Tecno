/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

import Datos.DUsuario;
import Datos.Usuario;
import Negocio.NUsuario;
import Negocio.NArea;
import Negocio.NModo;
import Negocio.NPregunta;
import Negocio.NRespuesta;
import Negocio.NTipo;
import java.io.IOException;
import java.sql.SQLException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class Guia {
     private int bandejaLength = 0;
    private POPService guiaPopObj;
    private SMTPService guiaSmtpObj;
    private String guiaCmdo = "";
    private String guiaReceptor = "";
    private String guiaParams = "";

    public Guia() {
        guiaPopObj = new POPService();
        bandejaLength = guiaPopObj.getBandejaLength();
    }

    public void listen() {
        guiaPopObj = new POPService();
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
            case "LISUSUTODOS":
                return true;
            case "LISUSU":
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
            /*case "VERUSU":
                return true; funciona con el ver todos */
            // CU2. GESTIONAR PREGUNTA
            case "LISAREA" :
                return true;
            case "INSAREA":
                return true;
            case "UPDAREA":
                return true;
            case "DELAREA":
                return true;
            case "LISPREG":
                 return true;
            case "INSPREG":
                 return true;
            case "UPDPREG":
                 return true;
            case "DELPREG":
                 return true;
            case "LISRESP":
                 return true;
            case "INSRESP":
                 return true;
            case "UPDRESP":
                return true;
            case "DELRESP":
                return true;
            // CU5. GESTIONAR JUEGOS
            case "LISMODO":
                return true;
            case "INSMODO":
                return true;
            case "UPDMODO":
                return true;
            case "DELMODO":
                return true;
            // CU6. GESTIONAR DESAFIOS
            case "LISTIPO":
                return true;
            case "INSTIPO":
                return true;
            case "UPDTIPO":
                return true;
            case "DELTIPO":
               return true;
            default :
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
            case "LISUSUTODOS":
                executeLISUSUTODOS(this.guiaParams);
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
            //------CU2. GESTIONAR PREGUNTAS------//
            case "LISAREA":
                executeLISAREA(this.guiaParams);
                break;
            case "INSAREA":
                executeINSAREA(this.guiaParams);
                 break;
            case "UPDAREA":
                executeUPDAREA(this.guiaParams);
                 break;
            case "DELAREA":
                executeDELAREA(this.guiaParams);
                 break;
            case "LISPREG":
                executeLISPREG(this.guiaParams);
                 break;
            case "INSPREG":
                executeINSPREG(this.guiaParams);
                 break;
            case "UPDPREG":
                executeUPDPREG(this.guiaParams);
                 break;
            case "DELPREG":
                executeDELPREG(this.guiaParams);
                 break;
            case "LISRESP":
                executeLISRESP(this.guiaParams);
                 break;
            case "INSRESP":
                executeINSRESP(this.guiaParams);
                 break;
            case "UPDRESP":
                executeUPDRESP(this.guiaParams);
                 break;
            case "DELRESP":
                executeDELRESP(this.guiaParams);
                 break;
            //------CU5. GESTIONAR JUEGOS------//
            case "LISMODO":
                executeLISMODO(this.guiaParams);
                 break;
            case "INSMODO":
                executeINSMODO(this.guiaParams);
                 break;
            case "UPDMODO":
                executeUPDMODO(this.guiaParams);
                 break;
            case "DELMODO":
                executeDELMODO(this.guiaParams);
                 break;
            //------CU6. GESTIONAR DESAFIOS------//
            case "LISTIPO":
                executeLISTIPO(this.guiaParams);
                 break;
            case "INSTIPO":
                executeINSTIPO(this.guiaParams);
                 break;
            case "UPDTIPO":
                executeUPDTIPO(this.guiaParams);
                 break;
            case "DELTIPO":
                executeDELTIPO(this.guiaParams);
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
                + "      <li>LISUSUTODOS[*]</li>\n" 
                + "      <li>LISUSU[NOMBRE,EMAIL]</li>\n" 
                + "      <li>INSEST[NOMBRE,EMAIL,TELEFONO,PASSWORD,TIPO,COLEGIO,CARRERAINTERES,GRADO]</li>\n"
                + "      <li>INSDOC[NOMBRE,EMAIL,TELEFONO,PASSWORD,TIPO,ESPECIALIDAD]</li>\n"
                + "      <li>INSADM[NOMBRE,EMAIL,TELEFONO,PASSWORD,TIPO, CARGO]</li>\n"
                + "      <li>UPDEST[NOMBRE,EMAIL,TELEFONO,PASSWORD,TIPO]</li>\n"
                + "      <li>UPDDOC[NOMBRE,EMAIL,TELEFONO,PASSWORD,TIPO]</li>\n"
                + "      <li>UPDADM[NOMBRE,EMAIL,TELEFONO,PASSWORD,TIPO]</li>\n"
                + "      <li>DELUSU[ID]</li>\n"
                + "  </ul>\n"
                + "  <h3> Gestionar Preguntas </h3>\n"
                + "  <ul>\n"
                + "      <li>LISAREA[*]</li>\n"
                + "      <li>INSAREA[NOMBRE,PUNTAJE]</li>\n"
                + "      <li>UPDAREA[IDAREA,NOMBRE,PUNTAJE]</li>\n"
                + "      <li>DELAREA[IDAREA]</li>\n"
                + "  </ul>\n"
                + "  <ul>\n"
                + "      <li>LISPREG[AREA]</li>\n"
                + "      <li>REGPREG[PREGUNTA,AREA]</li>\n"
                + "      <li>UPDPREG[IDPREGUNTA,PREGUNTA,AREA]</li>\n"
                + "      <li>ELIMPREG[IDPREGUNTA]</li>\n"
                + "  </ul>\n"
                + "  <ul>\n"
                + "      <li>LISRESP[PREGUNTA]</li>\n"
                + "      <li>REGRESP[RESPUESTA,CORRECTA,IDPREGUNTA]</li>\n"
                + "      <li>UPDRESP[IDRESPUESTA,RESPUESTA,CORRECTA,IDPREGUNTA]</li>\n"
                + "      <li>ELIMRESP[IDRESPUESTA]</li>\n"
                + "  </ul>\n"
                + "  <h3> Gestionar Juegos </h3>\n"
                + "  <ul>\n"
                + "      <li>LISMODO[NOMBRE]</li>\n"
                + "      <li>INSMODO[NOMBRE]</li>\n"
                + "      <li>UPDMODO[NOMBRE]</li>\n"
                + "      <li>DELMODO[IDMODO]</li>\n"
                + "  </ul>\n"
                + "  <h3> Gestionar DesafÃ­os </h3>\n"
                + "  <ul>\n"
                + "      <li>LISTIPO[NOMBRE]</li>\n"
                + "      <li>INSTIPO[NOMBRE]</li>\n"
                + "      <li>UPDTIPO[NOMBRE]</li>\n"
                + "      <li>DELTIPO[IDMODO]</li>\n"
                + "  </ul>\n"
                + "</body>"
                + "</html>";
        sendResponseEmail("Estos son los comandos disponibles", msg);
    }
    
    public void sendResponseEmail(String sbjct, String msg){
         try {
             this.guiaSmtpObj = new SMTPService();
         } catch (IOException ex) {
             Logger.getLogger(Guia.class.getName()).log(Level.SEVERE, null, ex);
         }
        this.guiaSmtpObj.sendMessage(sbjct, this.guiaReceptor, msg);
        this.guiaSmtpObj.close();
    }

    /*==============================
    ===== CU1 GESTIONAR USUARIO ====
    ==============================*/
    public void executeLISUSUTODOS(String params) {
        NUsuario nuObj = new NUsuario();
        String msg = nuObj.listarusutodos(params);
        sendResponseEmail("RESPUESTA A PETICION LISUSUTODOS", msg);
    }

    public void executeLISUSU(String params) {
        NUsuario nuObj = new NUsuario();
        String msg = nuObj.listarusu(params);
        sendResponseEmail("RESPUESTA A PETICION LISUSU", msg);
    }

    public void executeINSEST(String params){
        NUsuario nuObj = new NUsuario();
        String msg = nuObj.registrarEst(params);
        sendResponseEmail("RESPUESTA A PETICION INSEST", msg);
    }
    
    public void executeINSDOC(String params){
        NUsuario nuObj = new NUsuario();
        String msg = nuObj.registrarDoc(params);
        sendResponseEmail("RESPUESTA A PETICION INSEST", msg);
    }  
    
    public void executeINSADM(String params){
        NUsuario nuObj = new NUsuario();
        String msg = nuObj.registrarAdm(params);
        sendResponseEmail("RESPUESTA A PETICION REGISTRAR USUARIO", msg);
    }
    
    public void executeUPDEST(String params) {
        NUsuario nuObj = new NUsuario();
        String msg = nuObj.editest(params);
        sendResponseEmail("RESPUESTA A PETICION UPDEST", msg);
    }
    
    public void executeUPDDOC(String params) {
        NUsuario nuObj = new NUsuario();
        String msg = nuObj.editdoc(params);
        sendResponseEmail("RESPUESTA A PETICION UPDOC", msg);
    }
    
    public void executeUPDADM(String params) {
        NUsuario nuObj = new NUsuario();
        String msg = nuObj.editadm(params);
        sendResponseEmail("RESPUESTA A PETICION UPDADM", msg);
    }
    public void executeDELUSU(String params) {
        NUsuario nuObj = new NUsuario();
        String msg = nuObj.elimusu(params);
        sendResponseEmail("RESPUESTA A PETICION DELUSU", msg);
    }
    /*==============================
    ===== FIN GESTIONAR USUARIO ====
    ==============================*/
    /*==============================
    ===== CU2 GESTIONAR PREGUNTAS ====
    ==============================*/
    public void executeLISAREA(String params) {
        NArea nuObj = new NArea();
        String msg = nuObj.lisarea(params);
        sendResponseEmail("RESPUESTA A PETICION LISAREA", msg);
    }
     
    public void executeINSAREA(String params) {
        NArea nuObj = new NArea();
        String msg = nuObj.regarea(params);
        sendResponseEmail("RESPUESTA A PETICION INSAREA", msg);
    }
      
    public void executeUPDAREA(String params) {
        NArea nuObj = new NArea();
        String msg = nuObj.editarea(params);
        sendResponseEmail("RESPUESTA A PETICION UPDAREA", msg);
    }
      
    public void executeDELAREA(String params) {
        NArea nuObj = new NArea();
        String msg = nuObj.elimarea(params);
        sendResponseEmail("RESPUESTA A PETICION DELAREA", msg);
    } 

    public void executeLISPREG(String params) {
        NPregunta nuObj = new NPregunta();
        String msg = nuObj.lispregunta(params);
        sendResponseEmail("RESPUESTA A PETICION LISPREG", msg);
    }
     
    public void executeINSPREG(String params) {
        NPregunta nuObj = new NPregunta();
        String msg = null;
        try {
            msg = nuObj.regpreg(params);
        } catch (SQLException ex) {
            Logger.getLogger(Guia.class.getName()).log(Level.SEVERE, null, ex);
        }
        sendResponseEmail("RESPUESTA A PETICION INSPREG", msg);
    }
      
    public void executeUPDPREG(String params) {
        NPregunta nuObj = new NPregunta();
        String msg = null;
        try {
            msg = nuObj.editpregunta(params);
        } catch (SQLException ex) {
            Logger.getLogger(Guia.class.getName()).log(Level.SEVERE, null, ex);
        }
        sendResponseEmail("RESPUESTA A PETICION UPDPREG", msg);
    }
      
    public void executeDELPREG(String params) {
        NPregunta nuObj = new NPregunta();
        String msg = nuObj.elimpreg(params);
        sendResponseEmail("RESPUESTA A PETICION DELPREG", msg);
    }    
       
    public void executeLISRESP(String params) {
        NRespuesta nuObj = new NRespuesta();
        String msg = nuObj.lisrespuesta(params);
        sendResponseEmail("RESPUESTA A PETICION LISRESP", msg);
    }
     
    public void executeINSRESP(String params) {
        NRespuesta nuObj = new NRespuesta();
        String msg = nuObj.regrespuesta(params);
        sendResponseEmail("RESPUESTA A PETICION INSRESP", msg);
    }
      
    public void executeUPDRESP(String params) {
        NRespuesta nuObj = new NRespuesta();
        String msg = null;
        try {
            msg = nuObj.editrespuesta(params);
        } catch (SQLException ex) {
            Logger.getLogger(Guia.class.getName()).log(Level.SEVERE, null, ex);
        }
        sendResponseEmail("RESPUESTA A PETICION UPDRESP", msg);
    }
      
    public void executeDELRESP(String params) {
        NRespuesta nuObj = new NRespuesta();
        String msg = nuObj.elimrespuesta(params);
        sendResponseEmail("RESPUESTA A PETICION DELRESP", msg);
    }
    
    /*==============================
    ===== CU5 GESTIONAR JUEGOS ====
    ==============================*/
    public void executeLISMODO(String params) {
        NModo nuObj = new NModo();
        String msg = nuObj.lismodo(params);
        sendResponseEmail("RESPUESTA A PETICION LISMODO", msg);
    }
     
    public void executeINSMODO(String params) {
        NModo nuObj = new NModo();
        String msg = nuObj.regmodo(params);
        sendResponseEmail("RESPUESTA A PETICION INSMODO", msg);
    }
      
    public void executeUPDMODO(String params) {
        NModo nuObj = new NModo();
        String msg = nuObj.editmodo(params);
        sendResponseEmail("RESPUESTA A PETICION UPDMODO", msg);
    }
    
    public void executeDELMODO(String params) {
        NModo nuObj = new NModo();
        String msg = nuObj.elimmodo(params);
        sendResponseEmail("RESPUESTA A PETICION DELMODO", msg);
    }     

    /*==============================
    ===== CU5 GESTIONAR DESAFIOS ====
    ==============================*/
    public void executeLISTIPO(String params) {
        NTipo nuObj = new NTipo();
        String msg = nuObj.listipo(params);
        sendResponseEmail("RESPUESTA A PETICION LISTIPO", msg);
    }
     
    public void executeINSTIPO(String params) {
        NTipo nuObj = new NTipo();
        String msg = nuObj.regtipo(params);
        sendResponseEmail("RESPUESTA A PETICION INSTIPO", msg);
    }
      
    public void executeUPDTIPO(String params) {
        NTipo nuObj = new NTipo();
        String msg = nuObj.edittipo(params);
        sendResponseEmail("RESPUESTA A PETICION UPDTIPO", msg);
    }
    
    public void executeDELTIPO(String params) {
        NTipo nuObj = new NTipo();
        String msg = nuObj.elimtipo(params);
        sendResponseEmail("RESPUESTA A PETICION DELTIPO", msg);
    } 
}
