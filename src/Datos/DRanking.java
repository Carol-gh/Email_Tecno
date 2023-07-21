/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Conexion.ConexionDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class DRanking {
    private ConexionDB con;
    
    public DRanking(){
        this.con = ConexionDB.getInstance();
    }
    public ArrayList<Ranking> listrankingcol(String colegio) {
        ArrayList<Ranking> lista = new ArrayList<>();
        String sql= "SELECT u.nombre AS nombre_ganador,e_creador.colegio AS colegio_ganador,SUM(j.resultado_final) AS resultado_final,RANK() OVER (ORDER BY SUM(j.resultado_final) DESC) AS posicion FROM public.juegos j JOIN public.estudiantes e_creador ON e_creador.id = j.estudiante_creador_id JOIN public.estudiantes e_jugador ON e_jugador.id = j.estudiante_jugador_id JOIN public.usuarios u ON u.id = CAST(j.ganador AS INTEGER) WHERE e_creador.colegio = e_jugador.colegio GROUP BY nombre_ganador,e_creador.colegio;";
        System.out.println("Consulta SQL: " + sql); // Imprimir la consulta SQL para verificar la sintaxis
        try {
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);
            ResultSet result = stmnt.executeQuery();
            con.desconectar();
            while (result.next()) {
                lista.add(new Ranking(result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4)
                ));
            }
        } catch (Exception e) {
            System.out.println("Excepcion al obtener los ranking: " + e.getMessage());
        }
        return lista;
    }
    
}
