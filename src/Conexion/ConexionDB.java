/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Usuario
 */
public class ConexionDB {
    private final String HOST = "127.0.0.1";    /*"mail.tecnoweb.org.bo";*/
    private final String PUERTO = "5432";
    private final String DB = "db_grupo06sc"; 
    private final String USER = "postgres";  /* "grupo06sc"*/
    private final String PASSWORD = "toor"; /* "grup06grup06"*/
    
    private static ConexionDB instancia;
    private Connection con;
    
    public ConexionDB(){
        this.con = null;
    }
    
    public Connection conectar(){
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://" + HOST + ":" + PUERTO + "/" + DB;
            this.con = DriverManager.getConnection(url, USER, PASSWORD);
            System.out.println("Conexion exitosa");
        } catch (Exception e) {
            System.out.println("Excepcion al conectar a postgresql DbConn: " + e);
        }
        return this.con;
    }
    
    public void desconectar(){
        try{
            this.con.close();
        }catch(SQLException e){
            System.out.println("Excepcion al deconectar DbConn: " + e.getMessage());
        }
    }
    
    public static ConexionDB getInstance(){
        if(instancia == null){
            instancia = new ConexionDB();
            return instancia;
        }
        return instancia;
    }
    
}
