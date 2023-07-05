package Negocio;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Generic {

    static public boolean esEntero(String nro) {
        Pattern ptrn = Pattern.compile("^[0-9]$|^[1-9][0-9]*", Pattern.CASE_INSENSITIVE);

        Matcher mtch = ptrn.matcher(nro.trim());
        boolean result = mtch.find();
        if (result) {
            //System.out.println("Nro: " + pal + "  Resultado: SI MATCH!");
            return true;
        } else {
            //System.out.println("Nro: " + pal + "  Resultado: NO MATCH!");
            return false;
        }
    }

    static public boolean esEmailValido(String email) {
        Pattern ptrn = Pattern.compile("^[0-9a-z][0-9a-z._-]+@[a-z0-9]+(\\.[a-z0-9]+)+$", Pattern.CASE_INSENSITIVE);

        Matcher mtch = ptrn.matcher(email.trim());
        boolean result = mtch.find();
        if (result) {
            //System.out.println("Nro: " + pal + "  Resultado: SI MATCH!");
            return true;
        } else {
            //System.out.println("Nro: " + pal + "  Resultado: NO MATCH!");
            return false;
        }
    }
}

