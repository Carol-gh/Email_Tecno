/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Conexion.ConexionDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class DUsuario {
    private ConexionDB con;
    
    public DUsuario(){
        this.con = ConexionDB.getInstance();
    }
    
    public boolean checkEmail(String email) {
        String sql = "SELECT * FROM usuarios WHERE email = ? ;";
        try {
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);
            stmnt.setString(1, email);
            ResultSet result = stmnt.executeQuery();
            con.desconectar();
            while (result.next()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Excepcion al validar email DUsuario: " + e);
        }
        return false;
    }
    
    public ArrayList<Usuario> listarTodos() {
        ArrayList<Usuario> lista = new ArrayList<>();
        String query = "SELECT * FROM usuarios ";
        try {
            PreparedStatement ps = con.conectar().prepareStatement(query);
            ResultSet set = ps.executeQuery();
            con.desconectar();
            while (set.next()) {
                lista.add(new Usuario(set.getInt(1),
                        set.getString(2),
                        set.getString(3),
                        set.getString(4),
                        set.getString(5),
                        set.getString(6)));
            }
        } catch (Exception e) {
            System.out.println("Excepcion al obtener usuarios DUsuario: " + e.getMessage());
        }
        return lista;
    }
    
    public ArrayList<Usuario> listarusu(String name, String email) {
        ArrayList<Usuario> lista = new ArrayList<>();
        String sql;
        if (email.equals("0")  && name.equals("0")) {
            sql = "SELECT * FROM usuarios WHERE 1=1";
        } else {
            sql = "SELECT * FROM usuarios WHERE 1=1 ";
            if (!name.equals("0")) {
                sql += "AND nombre like \'%" + name + "%\' ";
            }
            if (!email.equals("0")) {
                sql += "AND email like \'%" + email + "%\' ";
            }
        }
        System.out.println("Consulta SQL: " + sql); // Imprimir la consulta SQL para verificar la sintaxis
        try {
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);
            ResultSet result = stmnt.executeQuery();
            con.desconectar();
            while (result.next()) {
                lista.add(new Usuario(result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        result.getString(6)));
            }
        } catch (Exception e) {
            System.out.println("Excepcion al obtener usuarios DUsuario: " + e.getMessage());
        }
        return lista;
    }

    
    public Message registrar(String nombre, String email, String telefono, String password,String tipo) {
        ArrayList<Usuario> existentUsers = listarTodos();
        boolean nuevoUsr = true;
        for (Usuario usr : existentUsers) {
            if (usr.email.equals(email)) {
                nuevoUsr = false;
                break;
            }
        }
        if (nuevoUsr == false) {
            return new Message("Usuario ya existe", false);
        }
        String query = "INSERT INTO usuarios(nombre, email, telefono, password,tipo)" + "values(?,?,?,?,?)";
        try {
        PreparedStatement ps = con.conectar().prepareStatement(query);
        ps.setString(1, nombre);
        ps.setString(2, email);
        ps.setString(3, telefono);
        ps.setString(4, password);
        ps.setString(5, tipo);
        int result = ps.executeUpdate();
            con.desconectar();
            return new Message("Usuario registrado exitosamente", true);
        } catch (Exception e) {
          System.out.println("Excepcion al regusu DUsuario: " + e);      
        }
         return new Message("Excepcion en regusu DUsuario algo salio mal", false);
    }
    
    public Message registrarEst(String nombre, String email, String telefono, String password,String tipo, String colegio, String carrerainteres, String grado) {
        ArrayList<Usuario> existentUsers = listarTodos();
        boolean nuevoUsr = true;
        for (Usuario usr : existentUsers) {
            if (usr.email.equals(email)) {
                nuevoUsr = false;
                break;
            }
        }
        if (nuevoUsr == false) {
            return new Message("Estudiante ya existe", false);
        }
        String query = "INSERT INTO usuarios(nombre, email, telefono, password,tipo)" + "values(?,?,?,?,?)";
        try {
        PreparedStatement ps = con.conectar().prepareStatement(query);
        ps.setString(1, nombre);
        ps.setString(2, email);
        ps.setString(3, telefono);
        ps.setString(4, password);
        ps.setString(5, tipo);
        int result = ps.executeUpdate();
        ArrayList<Usuario> UsersId = listarTodos();
        int IdUsr=0;
        for (Usuario usr : UsersId) {
            if (usr.email.equals(email)) {
                IdUsr = usr.id;
            }
        }
        String qry = "INSERT INTO estudiantes(colegio, carrerainteres, grado, usuario_id) VALUES (?, ?, ?, ?)";
        PreparedStatement ps2 = con.conectar().prepareStatement(qry);
        ps2.setString(1, colegio);
        ps2.setString(2, carrerainteres);
        ps2.setString(3, grado);
        ps2.setInt(4, IdUsr);
        int result2 = ps2.executeUpdate();
            con.desconectar();
            return new Message("Estudiante registrado exitosamente", true);
        } catch (Exception e) {
          System.out.println("Excepcion al regest DUsuario: " + e);      
        }
         return new Message("Excepcion en regest DUsuario algo salio mal", false);
    }
    
    public Message registrarDoc(String nombre, String email, String telefono, String password,String tipo, String especialidad) {
        ArrayList<Usuario> existentUsers = listarTodos();
        boolean nuevoUsr = true;
        for (Usuario usr : existentUsers) {
            if (usr.email.equals(email)) {
                nuevoUsr = false;
                break;
            }
        }
        if (nuevoUsr == false) {
            return new Message("Docente ya existe", false);
        }
        String query = "INSERT INTO usuarios(nombre, email, telefono, password,tipo)" + "values(?,?,?,?,?)";
        try {
        PreparedStatement ps = con.conectar().prepareStatement(query);
        ps.setString(1, nombre);
        ps.setString(2, email);
        ps.setString(3, telefono);
        ps.setString(4, password);
        ps.setString(5, tipo);
        int result = ps.executeUpdate();
        con.desconectar();
        ArrayList<Usuario> UsersId = listarTodos();
        int IdUsr=0;
        for (Usuario usr : UsersId) {
            if (usr.email.equals(email)) {
                IdUsr = usr.id;
            }
        }
        String qry = "INSERT INTO docentes(especialidad, usuario_id) VALUES (?, ?)";
        PreparedStatement ps2 = con.conectar().prepareStatement(qry);
        ps2.setString(1, especialidad);
        ps2.setInt(2, IdUsr);
        int result2 = ps2.executeUpdate();
            con.desconectar();
            return new Message("Docente registrado exitosamente", true);
        } catch (Exception e) {
          System.out.println("Excepcion al regdoc DUsuario: " + e);      
        }
         return new Message("Excepcion en regdoc DUsuario algo salio mal", false);
    }
    
    public Message registrarAdm(String nombre, String email, String telefono, String password,String tipo, String cargo) {
        ArrayList<Usuario> existentUsers = listarTodos();
        boolean nuevoUsr = true;
        for (Usuario usr : existentUsers) {
            if (usr.email.equals(email)) {
                nuevoUsr = false;
                break;
            }
        }
        if (nuevoUsr == false) {
            return new Message("Docente ya existe", false);
        }
        String query = "INSERT INTO usuarios(nombre, email, telefono, password,tipo)" + "values(?,?,?,?,?)";
        try {
        PreparedStatement ps = con.conectar().prepareStatement(query);
        ps.setString(1, nombre);
        ps.setString(2, email);
        ps.setString(3, telefono);
        ps.setString(4, password);
        ps.setString(5, tipo);
        int result = ps.executeUpdate();
        ArrayList<Usuario> UsersId = listarTodos();
        int IdUsr=0;
        for (Usuario usr : UsersId) {
            if (usr.email.equals(email)) {
                IdUsr = usr.id;
            }
        }
        String qry = "INSERT INTO administrativos(cargo, usuario_id) VALUES (?, ?)";
        PreparedStatement ps2 = con.conectar().prepareStatement(qry);
        ps2.setString(1, cargo);
        ps2.setInt(2, IdUsr);
        int result2 = ps2.executeUpdate();
            con.desconectar();
            return new Message("Administrativo registrado exitosamente", true);
        } catch (Exception e) {
          System.out.println("Excepcion al regadm DUsuario: " + e);      
        }
         return new Message("Excepcion en regadm DUsuario algo salio mal", false);
    }
    
    public void update(int id, String nombre, String email, String telefono, String password,String tipo) throws SQLException{
        String query = "UPDATE usuarios SET nombre=?, email=?, telefono=?, password=?,tipo=?"
                + "WHERE id=?";
        PreparedStatement ps = con.conectar().prepareStatement(query);
        ps.setString(1, nombre);
        ps.setString(2, email);
        ps.setString(3, telefono);
        ps.setString(4, password);
        ps.setString(5, tipo);
        ps.setInt(6, id);
        
        if(ps.executeUpdate() == 0){
            System.err.println("Class DUsuario.java dice:" 
                    + "Ocurrio un error al modificar un usuario update()");
            throw new SQLException();
        }
    }
    
    public Respuesta editest(int idParam, String nombreParam,String emailParam,String telefonoParam,String passwordParam,String tipoParam,String colegioParam, String carrerainteresParam,String gradoParam ) {
        String sql = "";
        String query = "";
            
        //todos los parametros vacios no se edita NADA!
        if (nombreParam.equals("0") && emailParam.equals("0") && idParam == 0 && telefonoParam.equals("0") && passwordParam.equals("0") && tipoParam.equals("0") && colegioParam.equals("0") && carrerainteresParam.equals("0") && gradoParam.equals("0")){
            return new Respuesta("Los parametro están vacíos", false);
        }
            
        //todos los parametros llenos se edita todos los campos disponibles
        if (!nombreParam.equals("0") && !emailParam.equals("0") && idParam >= 1 && !telefonoParam.equals("0") && !passwordParam.equals("0") && !tipoParam.equals("0") && !colegioParam.equals("0") && !carrerainteresParam.equals("0") && !gradoParam.equals("0")) {
            sql = "UPDATE usuarios SET nombre = ?, email = ?, telefono = ?, password = ?, tipo = ? WHERE id =" + idParam;
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);           
                stmnt.setString(1, nombreParam);                  
                stmnt.setString(2, emailParam);
                stmnt.setString(3, telefonoParam);
                stmnt.setString(4, passwordParam);
                stmnt.setString(5, tipoParam);
                int res = stmnt.executeUpdate();
            query = "UPDATE estudiantes SET colegio = ?, carrerainteres = ?, grado = ? WHERE usuario_id =" + idParam;
            PreparedStatement st = con.conectar().prepareStatement(query);           
                st.setString(1, colegioParam);                  
                st.setString(2, carrerainteresParam);
                st.setString(3, gradoParam);
                int result = st.executeUpdate();
                con.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DUsuario Estudiante editar: " + e);
            }
        }
        
        //Solo se edita el nombre del parametro segun el idArea enviado 
        if (idParam >= 1 && !nombreParam.equals("0") && emailParam.equals("0") && telefonoParam.equals("0") && passwordParam.equals("0") && tipoParam.equals("0") && colegioParam.equals("0") && carrerainteresParam.equals("0") && gradoParam.equals("0")) {
            sql = "UPDATE usuarios SET nombre = ? WHERE id =" + idParam;
            
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, nombreParam);
                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DUsuario estudiante editar: " + e);
            }
        }
                                        
        //Solo se edita el email del Estudiante segun el usuario_id enviado!          
        if (idParam >= 1 && nombreParam.equals("0") && !emailParam.equals("0") && telefonoParam.equals("0") && passwordParam.equals("0") && tipoParam.equals("0") && colegioParam.equals("0") && carrerainteresParam.equals("0") && gradoParam.equals("0")) {
            sql = "UPDATE usuarios SET email = ? WHERE id =" + idParam;
            
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, emailParam);
                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DEstudiante editar: "+e);
            }
        }          
        
        if (idParam >= 1 && nombreParam.equals("0") && emailParam.equals("0") && !telefonoParam.equals("0") && passwordParam.equals("0") && tipoParam.equals("0") && colegioParam.equals("0") && carrerainteresParam.equals("0") && gradoParam.equals("0")) {
            sql = "UPDATE usuarios SET telefono = ? WHERE id =" + idParam;
            
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, telefonoParam);
                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DUsuario editar: "+e);
            }
        }
        if (idParam >= 1 && nombreParam.equals("0") && emailParam.equals("0") && telefonoParam.equals("0") && !passwordParam.equals("0") && tipoParam.equals("0") && colegioParam.equals("0") && carrerainteresParam.equals("0") && gradoParam.equals("0")) {
            sql = "UPDATE usuarios SET password = ? WHERE id =" + idParam;
            
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, passwordParam);
                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DUsuario editar: "+e);
            }
        }
        if (idParam >= 1 && nombreParam.equals("0") && emailParam.equals("0") && telefonoParam.equals("0") && passwordParam.equals("0") && tipoParam.equals("0") && !colegioParam.equals("0") && carrerainteresParam.equals("0") && gradoParam.equals("0")) {
            sql = "UPDATE estudiantes SET colegio = ? WHERE usuario_id =" + idParam;
            
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, colegioParam);
                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DUsuario Estudiante editar: "+e);
            }
        }
        if (idParam >= 1 && nombreParam.equals("0") && emailParam.equals("0") && telefonoParam.equals("0") && passwordParam.equals("0") && tipoParam.equals("0") && !colegioParam.equals("0") && carrerainteresParam.equals("0") && gradoParam.equals("0")) {
            sql = "UPDATE estudiantes SET colegio = ? WHERE usuario_id =" + idParam;
            
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, colegioParam);
                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DUsuario Estudiante editar: "+e);
            }
        }
        if (idParam >= 1 && nombreParam.equals("0") && emailParam.equals("0") && telefonoParam.equals("0") && passwordParam.equals("0") && tipoParam.equals("0") && colegioParam.equals("0") && !carrerainteresParam.equals("0") && gradoParam.equals("0")) {
            sql = "UPDATE estudiantes SET carrerainteres = ? WHERE usuario_id =" + idParam;
            
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, carrerainteresParam);
                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DUsuario Estudiante editar: "+e);
            }
        }
        if (idParam >= 1 && nombreParam.equals("0") && emailParam.equals("0") && telefonoParam.equals("0") && passwordParam.equals("0") && tipoParam.equals("0") && colegioParam.equals("0") && carrerainteresParam.equals("0") && !gradoParam.equals("0")) {
            sql = "UPDATE estudiantes SET grado = ? WHERE usuario_id =" + idParam;
            
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, gradoParam);
                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DUsuario Estudiante editar: "+e);
            }
        }
        return new Respuesta("Se ha editado al Estudiante con éxito!", false); 
    }
    
    public Respuesta editdoc(int idParam, String nombreParam,String emailParam,String telefonoParam,String passwordParam,String tipoParam,String especialidadParam ) {
        String sql = "";
        String query = "";
            
        //todos los parametros vacios no se edita NADA!
        if (nombreParam.equals("0") && emailParam.equals("0") && idParam == 0 && telefonoParam.equals("0") && passwordParam.equals("0") && tipoParam.equals("0") && especialidadParam.equals("0")){
            return new Respuesta("Los parametro están vacíos", false);
        }
            
        //todos los parametros llenos se edita todos los campos disponibles
        if (!nombreParam.equals("0") && !emailParam.equals("0") && idParam >= 1 && !telefonoParam.equals("0") && !passwordParam.equals("0") && !tipoParam.equals("0") && !especialidadParam.equals("0")) {
            sql = "UPDATE usuarios SET nombre = ?, email = ?, telefono = ?, password = ?, tipo = ? WHERE id =" + idParam;
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);           
                stmnt.setString(1, nombreParam);                  
                stmnt.setString(2, emailParam);
                stmnt.setString(3, telefonoParam);
                stmnt.setString(4, passwordParam);
                stmnt.setString(5, tipoParam);
                int res = stmnt.executeUpdate();
            query = "UPDATE docentes SET especialidad = ?, carrerainteres = ?, grado = ? WHERE usuario_id =" + idParam;
            PreparedStatement st = con.conectar().prepareStatement(query);           
                st.setString(1, especialidadParam);                  
                int result = st.executeUpdate();
                con.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DUsuario Docente editar: " + e);
            }
        }
        
        //Solo se edita el nombre del parametro segun el idArea enviado 
        if (idParam >= 1 && !nombreParam.equals("0") && emailParam.equals("0") && telefonoParam.equals("0") && passwordParam.equals("0") && tipoParam.equals("0") && especialidadParam.equals("0")) {
            sql = "UPDATE usuarios SET nombre = ? WHERE id =" + idParam;
            
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, nombreParam);
                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DUsuario docente editar: " + e);
            }
        }
                                        
        //Solo se edita el email del Estudiante segun el usuario_id enviado!          
        if (idParam >= 1 && nombreParam.equals("0") && !emailParam.equals("0") && telefonoParam.equals("0") && passwordParam.equals("0") && tipoParam.equals("0") && especialidadParam.equals("0")) {
            sql = "UPDATE usuarios SET email = ? WHERE id =" + idParam;
            
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, emailParam);
                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DUsuario Docente editar: "+e);
            }
        }          
        
        if (idParam >= 1 && nombreParam.equals("0") && emailParam.equals("0") && !telefonoParam.equals("0") && passwordParam.equals("0") && tipoParam.equals("0") && especialidadParam.equals("0")) {
            sql = "UPDATE usuarios SET telefono = ? WHERE id =" + idParam;
            
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, telefonoParam);
                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DUsuario editar: "+e);
            }
        }
        if (idParam >= 1 && nombreParam.equals("0") && emailParam.equals("0") && telefonoParam.equals("0") && !passwordParam.equals("0") && tipoParam.equals("0") && especialidadParam.equals("0")) {
            sql = "UPDATE usuarios SET password = ? WHERE id =" + idParam;
            
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, passwordParam);
                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DUsuario editar: "+e);
            }
        }
        if (idParam >= 1 && nombreParam.equals("0") && emailParam.equals("0") && telefonoParam.equals("0") && passwordParam.equals("0") && tipoParam.equals("0") && !especialidadParam.equals("0")) {
            sql = "UPDATE docentes SET especialidad = ? WHERE usuario_id =" + idParam;
            
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, especialidadParam);
                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DUsuario Docente editar: "+e);
            }
        }
        return new Respuesta("Se ha editado al Docente con éxito!", false); 
    }
    public Respuesta delete(Integer id) throws SQLException{
        String query = "DELETE FROM usuarios WHERE id=?";
         try {
            PreparedStatement stmnt = con.conectar().prepareStatement(query);
            int res = stmnt.executeUpdate();
            con.desconectar();
        } catch (SQLException e) {
            System.out.println("Error DUsuario eliminar: " + e);
        }
        return new Respuesta("Se ha eliminado al Usuario con exito!", false);
    }
    
     public Respuesta editadm(int idParam, String nombreParam,String emailParam,String telefonoParam,String passwordParam,String tipoParam,String cargoParam ) {
        String sql = "";
        String query = "";
            
        //todos los parametros vacios no se edita NADA!
        if (nombreParam.equals("0") && emailParam.equals("0") && idParam == 0 && telefonoParam.equals("0") && passwordParam.equals("0") && tipoParam.equals("0") && cargoParam.equals("0")){
            return new Respuesta("Los parametro están vacíos", false);
        }
            
        //todos los parametros llenos se edita todos los campos disponibles
        if (!nombreParam.equals("0") && !emailParam.equals("0") && idParam >= 1 && !telefonoParam.equals("0") && !passwordParam.equals("0") && !tipoParam.equals("0") && !cargoParam.equals("0")) {
            sql = "UPDATE usuarios SET nombre = ?, email = ?, telefono = ?, password = ?, tipo = ? WHERE id =" + idParam;
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);           
                stmnt.setString(1, nombreParam);                  
                stmnt.setString(2, emailParam);
                stmnt.setString(3, telefonoParam);
                stmnt.setString(4, passwordParam);
                stmnt.setString(5, tipoParam);
                int res = stmnt.executeUpdate();
            query = "UPDATE administrativos SET cargo = ? WHERE usuario_id =" + idParam;
            PreparedStatement st = con.conectar().prepareStatement(query);           
                st.setString(1, cargoParam);                  
                int result = st.executeUpdate();
                con.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DUsuario Administrativo editar: " + e);
            }
        }
        
        //Solo se edita el nombre del parametro segun el idArea enviado 
        if (idParam >= 1 && !nombreParam.equals("0") && emailParam.equals("0") && telefonoParam.equals("0") && passwordParam.equals("0") && tipoParam.equals("0") && cargoParam.equals("0")) {
            sql = "UPDATE usuarios SET nombre = ? WHERE id =" + idParam;
            
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, nombreParam);
                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DUsuario Administrativo editar: " + e);
            }
        }
                                        
        //Solo se edita el email del Estudiante segun el usuario_id enviado!          
        if (idParam >= 1 && nombreParam.equals("0") && !emailParam.equals("0") && telefonoParam.equals("0") && passwordParam.equals("0") && tipoParam.equals("0") && cargoParam.equals("0")) {
            sql = "UPDATE usuarios SET email = ? WHERE id =" + idParam;
            
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, emailParam);
                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DUsuario Administrativo editar: "+e);
            }
        }          
        
        if (idParam >= 1 && nombreParam.equals("0") && emailParam.equals("0") && !telefonoParam.equals("0") && passwordParam.equals("0") && tipoParam.equals("0") && cargoParam.equals("0")) {
            sql = "UPDATE usuarios SET telefono = ? WHERE id =" + idParam;
            
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, telefonoParam);
                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DUsuario editar: "+e);
            }
        }
        if (idParam >= 1 && nombreParam.equals("0") && emailParam.equals("0") && telefonoParam.equals("0") && !passwordParam.equals("0") && tipoParam.equals("0") && cargoParam.equals("0")) {
            sql = "UPDATE usuarios SET password = ? WHERE id =" + idParam;
            
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, passwordParam);
                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DUsuario editar: "+e);
            }
        }
        if (idParam >= 1 && nombreParam.equals("0") && emailParam.equals("0") && telefonoParam.equals("0") && passwordParam.equals("0") && tipoParam.equals("0") && !cargoParam.equals("0")) {
            sql = "UPDATE administrativos SET cargo = ? WHERE usuario_id =" + idParam;
            
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, cargoParam);
                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (SQLException e) {
                System.out.println("Error DUsuario Administrativo editar: "+e);
            }
        }
        return new Respuesta("Se ha editado al Docente con éxito!", false); 
    }
     
    public Respuesta delete(int id) {
        String query = "DELETE FROM usuarios WHERE id=?";
         try {
            PreparedStatement stmnt = con.conectar().prepareStatement(query);
            int res = stmnt.executeUpdate();
            con.desconectar();
        } catch (SQLException e) {
            System.out.println("Error DUsuario eliminar: " + e);
        }
        return new Respuesta("Se ha eliminado al Usuario con exito!", false);
    }
    
    public ArrayList<Usuario> ver(String email) throws SQLException{
        ArrayList<Usuario> usuario = new ArrayList<>();
        String query = "SELECT * FROM Usuario WHERE email=?";
        try {
        PreparedStatement ps = con.conectar().prepareStatement(query);
        ps.setString(1, email);
        ResultSet set = ps.executeQuery();
            while (set.next()){
                usuario.add(new Usuario(set.getInt(1),
                set.getString(2),
                set.getString(3),
                set.getString(4),
                set.getString(5),
                set.getString(6))
                );
            }
        } catch (Exception e) {
            System.out.println("Excepcion en listarusu DUsuario: ");
        }
        return usuario;
    }
}
