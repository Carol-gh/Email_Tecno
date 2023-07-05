/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

/**
 *
 * @author Usuario
 */
public class Tecnoweb_P1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Guia guiaObj = new Guia();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    guiaObj.listen();
                    try{
                        Thread.sleep(3000);
                    }catch(Exception e){
                        System.out.println("Excepcion: " + e.getMessage());
                    }
                }
            }
        }).start();
    }
    
}
