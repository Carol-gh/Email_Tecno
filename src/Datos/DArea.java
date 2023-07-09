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
public class DArea {
    private ConexionDB connection;

    public DArea() {
        this.connection = ConexionDB.getInstance();
    }
    
    public ArrayList<Area> listTodoArea() {
        ArrayList<Area> lista = new ArrayList<>();
        String sql = "SELECT * FROM areas";
        
        try {
            PreparedStatement stmnt = connection.conectar().prepareStatement(sql);
            ResultSet result = stmnt.executeQuery();
            connection.desconectar();
            
            while(result.next()) {
                lista.add(new Area(
                    result.getInt(1),
                 result.getString(2),
                 result.getInt(3)
                ));
            }
        } catch(SQLException e) {
            System.out.println("Excepcion al obtener áreas. DArea: " + e.getMessage());
        }
        
        return lista;
    }
    
    public ArrayList<Area> lisarea() {
        ArrayList<Area> lista = new ArrayList<>();
        String sql = "SELECT * FROM areas";    
        try {
            PreparedStatement stmnt = connection.conectar().prepareStatement(sql);
            ResultSet result = stmnt.executeQuery();
            connection.desconectar();
            while (result.next()) {
                lista.add(new Area(
                    result.getInt(1),
                    result.getString(2),
                    result.getInt(3)
                ));
            }
        } catch (SQLException e) {
            System.out.println("Excepcion en lisarea DArea: " + e.getMessage());
        }
        
        return lista;
    }
    
    public Respuesta regarea(String nombreParam, int puntajeParam) {
        ArrayList<Area> existenActs = listTodoArea();
        boolean nuevoT = true;
        for (Area area : existenActs) {
            if (area.area_nombre.equals(nombreParam)) {
                nuevoT = false;
                break;
            }
        }
        if (nuevoT == false) {
            return new Respuesta("El nombre de esta Area ya existe", false);
        }
        String sql = "INSERT INTO areas(nombre, puntaje) VALUES (?, ?)";
        try {
            PreparedStatement stmnt = connection.conectar().prepareStatement(sql);
            stmnt.setString(1, nombreParam);
            stmnt.setInt(2, puntajeParam);
            int result = stmnt.executeUpdate();
            connection.desconectar();
            
            return new Respuesta("Area registrada exitosamente", true);
        } catch (SQLException e) {
            System.out.println("Excepcion al regarea DArea: " + e);
        }
        return new Respuesta("Excepcion en regarea DArea algo salió mal", false);
    }
    
    public Respuesta editarea(int idAreaParam, String nombreParam, int puntajeParam) {
        String sql = "";
            
        //todos los parametros vacios no se edita NADA!
        if (nombreParam.equals("0") && puntajeParam == 0 && idAreaParam == 0){
            return new Respuesta("Los parametro están vacíos", false);
        }
            
        //todos los parametros llenos se edita todos los campos disponibles
        if (!nombreParam.equals("0") && puntajeParam != 0 && idAreaParam >= 1) {
            sql = "UPDATE areas SET nombre = ?, puntaje = ? WHERE id =" + idAreaParam;
            try {
                PreparedStatement stmnt = connection.conectar().prepareStatement(sql);           
                stmnt.setString(1, nombreParam);                  
                stmnt.setInt(2, puntajeParam);
                int res = stmnt.executeUpdate();
                connection.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DArea editar: " + e);
            }
        }
        
        //Solo se edita el nombre del parametro segun el idArea enviado 
        if (idAreaParam >= 1 && !nombreParam.equals("0") && puntajeParam == 0) {
            sql = "UPDATE areas SET nombre = ? WHERE id =" + idAreaParam;
            
            try {
                PreparedStatement stmnt = connection.conectar().prepareStatement(sql);
                stmnt.setString(1, nombreParam);
                int res = stmnt.executeUpdate();
                connection.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DArea editar: " + e);
            }
        }
                                        
        //Solo se edita el puntaje del Area segun el idArea enviado!          
        if (idAreaParam >= 1 && nombreParam.equals("0") && puntajeParam != 0) {
            sql = "UPDATE areas SET puntaje = ? WHERE id =" + idAreaParam;
            
            try {
                PreparedStatement stmnt = connection.conectar().prepareStatement(sql);
                stmnt.setInt(1, puntajeParam);
                int res = stmnt.executeUpdate();
                connection.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DArea editar: "+e);
            }
        }          
                               
        return new Respuesta("Se ha editado el AREA con éxito!", false); 
    }
        
    public Respuesta elimArea(int idAreaParam) {          
        String sql = "DELETE FROM areas WHERE id =" + idAreaParam;
        try {
            PreparedStatement stmnt = connection.conectar().prepareStatement(sql);
            int res = stmnt.executeUpdate();
            connection.desconectar();
        } catch (SQLException e) {
            System.out.println("Error DArea eliminar: " + e);
        }
        
        return new Respuesta("Se ha eliminado el AREA con exito!", false); //puede que haya que modificar el mensaje de salida o respuesta
    }
}
