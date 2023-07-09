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
public class DRespuesta {
    private ConexionDB connection;

    public DRespuesta() {
        this.connection = ConexionDB.getInstance();
    }
    
    public ArrayList<RespuestaPreg> listTodoRespuesta() {
        ArrayList<RespuestaPreg> lista = new ArrayList<>();
        String sql = "SELECT r.id, r.texto, r.esCorrecta, r.pregunta_id, p.texto "
                + "FROM respuestas r, preguntas p WHERE r.pregunta_id = p.id";
        
        try {
            PreparedStatement stmnt = connection.conectar().prepareStatement(sql);
            ResultSet result = stmnt.executeQuery();
            connection.desconectar();
            
            while(result.next()) {
                lista.add(new RespuestaPreg(
                   result.getInt(1),
                 result.getString(2),
               result.getBoolean(3),
             result.getInt(4),
               result.getString(5)
                ));
            }
        } catch(SQLException e) {
            System.out.println("Excepcion al obtener preguntas. DRespuesta: " + e.getMessage());
        }
        
        return lista;
    }
    
    public ArrayList<RespuestaPreg> lisrespuesta(int preguntaId) {
        ArrayList<RespuestaPreg> lista = new ArrayList<>();
        String sql;
        if (preguntaId == 0) {//se muestras todas las tuplas de la tabla tarea si el parametro es 0
            sql = "SELECT r.id, r.texto, r.esCorrecta, r.pregunta_id, p.texto "
                + "FROM respuestas r, preguntas p "
                + "WHERE r.pregunta_id = p.id";
        } else{
            sql = "SELECT r.id, r.texto, r.esCorrecta, r.pregunta_id, p.texto "
                + "FROM respuestas r, preguntas p "
                + "WHERE r.pregunta_id = p.id AND r.pregunta_id = " + preguntaId;
        }
        
        try {
            PreparedStatement stmnt = connection.conectar().prepareStatement(sql);
            ResultSet result = stmnt.executeQuery();
            connection.desconectar();
            while (result.next()) {
                lista.add(new RespuestaPreg(
                 result.getInt(1),
               result.getString(2),
             result.getBoolean(3),
           result.getInt(4),
             result.getString(5)
                ));
            }
        } catch (SQLException e) {
            System.out.println("Excepcion en lisrespuesta DRespuesta: " + e.getMessage());
        }
        
        return lista;
    }
    
    public Respuesta regrespuesta(String textoParam, boolean correctaParam, int preguntaParam) {
        ArrayList<RespuestaPreg> existenActs = listTodoRespuesta();
        boolean nuevoT = true;
        for (RespuestaPreg respuesta : existenActs) {
            if (respuesta.respuesta_texto.equals(textoParam)) {
                nuevoT = false;
                break;
            }
        }
        if (nuevoT == false) {
            return new Respuesta("La respuesta ya existe", false);
        }
     
        String sql = "INSERT INTO respuestas(texto, esCorrecta, pregunta_id) VALUES (?, ?)";
        try {
            PreparedStatement stmnt = connection.conectar().prepareStatement(sql);
            stmnt.setString(1, textoParam);
            stmnt.setBoolean(2, correctaParam);
            stmnt.setInt(3, preguntaParam);
            int result = stmnt.executeUpdate();
            connection.desconectar();

            return new Respuesta("Respuesta registrada exitosamente", true);
        } catch (SQLException e) {
            System.out.println("Excepcion al regrespuesta DRespuesta: " + e);
        }
              
        return new Respuesta("Excepcion en regrespuesta DRespuesta algo salió mal", false);
    }
    
    public Respuesta editrespuesta(int idRespuestaParam, String textoParam, boolean correctaParam, int idPreguntaParam) {
        String sql = "";
           
        //todos los parametros vacios no se edita NADA!
        if (textoParam.equals("0") && idPreguntaParam == 0){
            return new Respuesta("Los parametro están vacíos", false);
        }
      
        //todos los parametros llenos se edita todos los campos disponibles
        if (!textoParam.equals("0") && idPreguntaParam != 0 && idRespuestaParam >= 1) {
            sql = "UPDATE respuestas SET texto = ?, esCorrecta = ?, pregunta_id = ? WHERE id =" + idRespuestaParam;
            try {
                PreparedStatement stmnt = connection.conectar().prepareStatement(sql);           
                stmnt.setString(1, textoParam);                  
                stmnt.setBoolean(2, correctaParam);
                stmnt.setInt(3, idPreguntaParam);
                int res = stmnt.executeUpdate();
                connection.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DRespuesta editar: " + e);
            }
        }
        
        //Solo se edita el nombre del parametro segun el idPregunta enviado 
        if (idRespuestaParam >= 1 && !textoParam.equals("0") && idPreguntaParam == 0) {
            sql = "UPDATE respuestas SET texto = ?, esCorrecta = ?, pregunta_id = ? WHERE id =" + idRespuestaParam;
            
            try {
                PreparedStatement stmnt = connection.conectar().prepareStatement(sql);
                stmnt.setString(1, textoParam);
                stmnt.setBoolean(2, correctaParam);
                stmnt.setInt(3, idPreguntaParam);
                int res = stmnt.executeUpdate();
                connection.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DRespuesta editar: " + e);
            }
        }
                                        
        //Solo se edita el puntaje de la Respuesta segun el idPregunta enviado!          
        if (idRespuestaParam >= 1 && textoParam.equals("0") && idPreguntaParam != 0) {
            sql = "UPDATE respuestas SET texto = ?, esCorrecta = ?, pregunta_id = ? WHERE id =" + idRespuestaParam;
            
            try {
                PreparedStatement stmnt = connection.conectar().prepareStatement(sql);
                stmnt.setString(1, textoParam);
                stmnt.setBoolean(2, correctaParam);
                stmnt.setInt(3, idPreguntaParam);
                int res = stmnt.executeUpdate();
                connection.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DRespuesta editar: "+e);
            }
        }          
                               
        return new Respuesta("Se ha editado la RESPUESTA con éxito!", false); 
    }
        
    public Respuesta elimrespuesta(int idRespuestaParam) {          
        String sql = "DELETE FROM respuestas WHERE id =" + idRespuestaParam;
        try {
            PreparedStatement stmnt = connection.conectar().prepareStatement(sql);
            int res = stmnt.executeUpdate();
            connection.desconectar();
        } catch (SQLException e) {
            System.out.println("Error DRespuesta eliminar: " + e);
        }
        
        return new Respuesta("Se ha eliminado la RESPUESTA con exito!", false); //puede que haya que modificar el mensaje de salida o respuesta
    }
}
