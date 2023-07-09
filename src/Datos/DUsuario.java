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
        String query = "SELECT * FROM Usuario ";
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
    
    public void delete(String email) throws SQLException{
        String query = "DELETE FROM usuarios WHERE email=?";
         PreparedStatement ps = con.conectar().prepareStatement(query);
         ps.setString(1, email);
        if(ps.executeUpdate() == 0){
            System.err.println("Class DUsuario.java dice:" 
                    + "Ocurrio un error al eliminar un usuario delete()");
            throw new SQLException();
        }
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
