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
public class DPregunta {
    private ConexionDB connection;

    public DPregunta() {
        this.connection = ConexionDB.getInstance();
    }
    
    public ArrayList<Pregunta> listTodoPregunta() {
        ArrayList<Pregunta> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.texto, p.area_id, a.nombre FROM preguntas p, areas a WHERE p.area_id = a.id";
        
        try {
            PreparedStatement stmnt = connection.conectar().prepareStatement(sql);
            ResultSet result = stmnt.executeQuery();
            connection.desconectar();
            
            while(result.next()) {
                lista.add(new Pregunta(
                    result.getInt(1),
                 result.getString(2),
                 result.getInt(3),
              result.getString(4)
                ));
            }
        } catch(SQLException e) {
            System.out.println("Excepcion al obtener preguntas. DPregunta: " + e.getMessage());
        }
        
        return lista;
    }
    
    public ArrayList<Pregunta> lispregunta(String area) {
        ArrayList<Pregunta> lista = new ArrayList<>();
        String sql;
        if (area.equals("0")) {//se muestras todas las tuplas de la tabla tarea si el parametro es 0
            sql = "SELECT p.id, p.texto, p.area_id, a.nombre "
                    + "FROM preguntas p, areas a "
                    + "WHERE p.area_id = a.id";
        } else{
            sql = "SELECT p.id, p.texto, p.area_id, a.nombre "
                    + "FROM preguntas p, areas a "
                    + "WHERE p.area_id = a.id AND a.nombre LIKE \'%" + area + "%'";
        }
        
        try {
            PreparedStatement stmnt = connection.conectar().prepareStatement(sql);
            ResultSet result = stmnt.executeQuery();
            connection.desconectar();
            while (result.next()) {
                lista.add(new Pregunta(
                    result.getInt(1),
                 result.getString(2),
                 result.getInt(3),
              result.getString(4)
                ));
            }
        } catch (SQLException e) {
            System.out.println("Excepcion en lispreguntas DPregunta: " + e.getMessage());
        }
        
        return lista;
    }
    
    public Respuesta regpregunta(String textoParam, String areaParam) throws SQLException {
        ArrayList<Pregunta> existenActs = listTodoPregunta();
        boolean nuevoT = true;
        for (Pregunta pregunta : existenActs) {
            if (pregunta.pregunta_texto.equals(textoParam)) {
                nuevoT = false;
                break;
            }
        }
        if (nuevoT == false) {
            return new Respuesta("La pregunta ya existe", false);
        }
        
        String getArea = "SELECT id FROM areas WHERE nombre =" + areaParam;
        PreparedStatement stmt = connection.conectar().prepareStatement(getArea);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            int idArea = rs.getInt("id");
            System.out.println("ID del área: " + idArea);
            String sql = "INSERT INTO preguntas(texto, area_id) VALUES (?, ?)";
            try {
                PreparedStatement stmnt = connection.conectar().prepareStatement(sql);
                stmnt.setString(1, textoParam);
                stmnt.setInt(2, idArea);
                int result = stmnt.executeUpdate();
                connection.desconectar();

                return new Respuesta("Pregunta registrada exitosamente", true);
            } catch (SQLException e) {
                System.out.println("Excepcion al regpregunta DPregunta: " + e);
            }
        } else {
            System.out.println("La pregunta no fue encontrada.");
        }
        
        return new Respuesta("Excepcion en regpregunta DPregunta algo salió mal", false);
    }
    
    public Respuesta editpregunta(int idPreguntaParam, String textoParam, String areaParam) throws SQLException {
        String sql = "";
        
        String getArea = "SELECT id FROM areas WHERE nombre =" + areaParam;
        PreparedStatement stmt = connection.conectar().prepareStatement(getArea);
        ResultSet rs = stmt.executeQuery();
        int idArea = 0;
        
        if (rs.next()) {
            idArea = rs.getInt("id");
            System.out.println("ID del área: " + idArea);
        } else {
            System.out.println("El área no fue encontrada.");
        }
           
        //todos los parametros vacios no se edita NADA!
        if (textoParam.equals("0") && idArea == 0){
            return new Respuesta("Los parametro están vacíos", false);
        }
      
        //todos los parametros llenos se edita todos los campos disponibles
        if (!textoParam.equals("0") && idArea != 0 && idPreguntaParam >= 1) {
            sql = "UPDATE preguntas SET texto = ?, area_id = ? WHERE id =" + idPreguntaParam;
            try {
                PreparedStatement stmnt = connection.conectar().prepareStatement(sql);           
                stmnt.setString(1, textoParam);                  
                stmnt.setInt(2, idArea);
                int res = stmnt.executeUpdate();
                connection.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DPregunta editar: " + e);
            }
        }
        
        //Solo se edita el nombre del parametro segun el idArea enviado 
        if (idPreguntaParam >= 1 && !textoParam.equals("0") && idArea == 0) {
            sql = "UPDATE preguntas SET texto = ?, area_id WHERE id =" + idPreguntaParam;
            
            try {
                PreparedStatement stmnt = connection.conectar().prepareStatement(sql);
                stmnt.setString(1, textoParam);
                stmnt.setInt(2, idArea);
                int res = stmnt.executeUpdate();
                connection.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DPregunta editar: " + e);
            }
        }
                                        
        //Solo se edita el puntaje del Area segun el idArea enviado!          
        if (idPreguntaParam >= 1 && textoParam.equals("0") && idArea != 0) {
            sql = "UPDATE preguntas SET texto = ?, area_id = ? WHERE id =" + idPreguntaParam;
            
            try {
                PreparedStatement stmnt = connection.conectar().prepareStatement(sql);
                stmnt.setString(1, textoParam);
                stmnt.setInt(2, idArea);
                int res = stmnt.executeUpdate();
                connection.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DPregunta editar: "+e);
            }
        }          
                               
        return new Respuesta("Se ha editado la PREGUNTA con éxito!", false); 
    }
        
    public Respuesta elimPregunta(int idPreguntaParam) {          
        String sql = "DELETE FROM preguntas WHERE id =" + idPreguntaParam;
        try {
            PreparedStatement stmnt = connection.conectar().prepareStatement(sql);
            int res = stmnt.executeUpdate();
            connection.desconectar();
        } catch (SQLException e) {
            System.out.println("Error DPregunta eliminar: " + e);
        }
        
        return new Respuesta("Se ha eliminado el PREGUNTA con exito!", false); //puede que haya que modificar el mensaje de salida o respuesta
    }
}
