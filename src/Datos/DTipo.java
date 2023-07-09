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
public class DTipo {
    private ConexionDB connection;

    public DTipo() {
        this.connection = ConexionDB.getInstance();
    }
    
    public ArrayList<Tipo> listTodoTipo() {
        ArrayList<Tipo> lista = new ArrayList<>();
        String sql = "SELECT * FROM tipos";
        
        try {
            PreparedStatement stmnt = connection.conectar().prepareStatement(sql);
            ResultSet result = stmnt.executeQuery();
            connection.desconectar();
            
            while(result.next()) {
                lista.add(new Tipo(
                    result.getInt(1),
                 result.getString(2)
                ));
            }
        } catch(SQLException e) {
            System.out.println("Excepcion al obtener tipos. DTipo: " + e.getMessage());
        }
        
        return lista;
    }
    
    public ArrayList<Tipo> listipos() {
        ArrayList<Tipo> lista = new ArrayList<>();
        String sql = "SELECT * FROM tipos";    
        try {
            PreparedStatement stmnt = connection.conectar().prepareStatement(sql);
            ResultSet result = stmnt.executeQuery();
            connection.desconectar();
            while (result.next()) {
                lista.add(new Tipo(
                    result.getInt(1),
                    result.getString(2)
                ));
            }
        } catch (SQLException e) {
            System.out.println("Excepcion en listipos DTipo: " + e.getMessage());
        }
        
        return lista;
    }
    
    public Respuesta regtipo(String nombreParam) {
        ArrayList<Tipo> existenTipos = listTodoTipo();
        boolean nuevoT = true;
        for (Tipo tipo : existenTipos) {
            if (tipo.tipo_nombre.equals(nombreParam)) {
                nuevoT = false;
                break;
            }
        }
        if (nuevoT == false) {
            return new Respuesta("El nombre de este Tipo ya existe", false);
        }
        String sql = "INSERT INTO tipos(nombre) VALUES (?)";
        try {
            PreparedStatement stmnt = connection.conectar().prepareStatement(sql);
            stmnt.setString(1, nombreParam);
            int result = stmnt.executeUpdate();
            connection.desconectar();
            
            return new Respuesta("Tipo registrado exitosamente", true);
        } catch (SQLException e) {
            System.out.println("Excepcion al regtipo DTipo: " + e);
        }
        return new Respuesta("Excepcion en regtipo DTipo algo salió mal", false);
    }
    
    public Respuesta edittipo(int idTipoParam, String nombreParam) {
        String sql = "";
            
        //todos los parametros vacios no se edita NADA!
        if (nombreParam.equals("0") && idTipoParam == 0){
            return new Respuesta("Los parametros están vacíos", false);
        }
            
        //todos los parametros llenos se edita todos los campos disponibles
        if (!nombreParam.equals("0") && idTipoParam >= 1) {
            sql = "UPDATE tipos SET nombre = ? WHERE id =" + idTipoParam;
            try {
                PreparedStatement stmnt = connection.conectar().prepareStatement(sql);           
                stmnt.setString(1, nombreParam);                  
                int res = stmnt.executeUpdate();
                connection.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DTipo editar: " + e);
            }
        }
                               
        return new Respuesta("Se ha editado el MODO con éxito!", false); 
    }
        
    public Respuesta elimtipo(int idTipoParam) {          
        String sql = "DELETE FROM tipos WHERE id =" + idTipoParam;
        try {
            PreparedStatement stmnt = connection.conectar().prepareStatement(sql);
            int res = stmnt.executeUpdate();
            connection.desconectar();
        } catch (SQLException e) {
            System.out.println("Error DTipo eliminar: " + e);
        }
        
        return new Respuesta("Se ha eliminado el TIPO con exito!", false); //puede que haya que modificar el mensaje de salida o respuesta
    }
}
