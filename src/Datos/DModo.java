/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

import Conexion.ConexionDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author USUARIO
 */
public class DModo {

    private ConexionDB connection;

    public DModo() {
        this.connection = ConexionDB.getInstance();
    }
    
    public ArrayList<Modo> listTodoModo() {
        ArrayList<Modo> lista = new ArrayList<>();
        String sql = "SELECT * FROM modos";
        
        try {
            PreparedStatement stmnt = connection.conectar().prepareStatement(sql);
            ResultSet result = stmnt.executeQuery();
            connection.desconectar();
            
            while(result.next()) {
                lista.add(new Modo(
                    result.getInt(1),
                 result.getString(2)
                ));
            }
        } catch(SQLException e) {
            System.out.println("Excepcion al obtener modos. DModo: " + e.getMessage());
        }
        
        return lista;
    }
    
    public ArrayList<Modo> lismodos() {
        ArrayList<Modo> lista = new ArrayList<>();
        String sql = "SELECT * FROM modos";    
        try {
            PreparedStatement stmnt = connection.conectar().prepareStatement(sql);
            ResultSet result = stmnt.executeQuery();
            connection.desconectar();
            while (result.next()) {
                lista.add(new Modo(
                    result.getInt(1),
                    result.getString(2)
                ));
            }
        } catch (SQLException e) {
            System.out.println("Excepcion en lismodos DModo: " + e.getMessage());
        }
        
        return lista;
    }
    
    public Respuesta regmodo(String nombreParam) {
        ArrayList<Modo> existenModos = listTodoModo();
        boolean nuevoT = true;
        for (Modo modo : existenModos) {
            if (modo.modo_nombre.equals(nombreParam)) {
                nuevoT = false;
                break;
            }
        }
        if (nuevoT == false) {
            return new Respuesta("El nombre de este Modo ya existe", false);
        }
        String sql = "INSERT INTO modos(nombre) VALUES (?)";
        try {
            PreparedStatement stmnt = connection.conectar().prepareStatement(sql);
            stmnt.setString(1, nombreParam);
            int result = stmnt.executeUpdate();
            connection.desconectar();
            
            return new Respuesta("Modo registrado exitosamente", true);
        } catch (SQLException e) {
            System.out.println("Excepcion al regmodo DModo: " + e);
        }
        return new Respuesta("Excepcion en regmodo DModo algo salió mal", false);
    }
    
    public Respuesta editmodo(int idModoParam, String nombreParam) {
        String sql = "";
            
        //todos los parametros vacios no se edita NADA!
        if (nombreParam.equals("0") && idModoParam == 0){
            return new Respuesta("Los parametros están vacíos", false);
        }
            
        //todos los parametros llenos se edita todos los campos disponibles
        if (!nombreParam.equals("0") && idModoParam >= 1) {
            sql = "UPDATE modos SET nombre = ? WHERE id =" + idModoParam;
            try {
                PreparedStatement stmnt = connection.conectar().prepareStatement(sql);           
                stmnt.setString(1, nombreParam);                  
                int res = stmnt.executeUpdate();
                connection.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DModo editar: " + e);
            }
        }
                               
        return new Respuesta("Se ha editado el MODO con éxito!", false); 
    }
        
    public Respuesta elimModo(int idModoParam) {          
        String sql = "DELETE FROM modos WHERE id =" + idModoParam;
        try {
            PreparedStatement stmnt = connection.conectar().prepareStatement(sql);
            int res = stmnt.executeUpdate();
            connection.desconectar();
        } catch (SQLException e) {
            System.out.println("Error DModo eliminar: " + e);
        }
        
        return new Respuesta("Se ha eliminado el MODO con exito!", false); //puede que haya que modificar el mensaje de salida o respuesta
    }
}
