package Presentacion;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


/**
 * An InputStreamReader is a bridge from byte streams to character streams: It
 * reads bytes and decodes them into characters using a specified {@link
 * java.nio.charset.Charset charset}.  The charset that it uses
 * may be specified by name or may be given explicitly, or the platform's
 * {@link Charset#defaultCharset() default charset} may be accepted.
 *
 * <p> Each invocation of one of an InputStreamReader's read() methods may
 * cause one or more bytes to be read from the underlying byte-input stream.
 * To enable the efficient conversion of bytes to characters, more bytes may
 * be read ahead from the underlying stream than are necessary to satisfy the
 * current read operation.
 *
 * <p> For top efficiency, consider wrapping an InputStreamReader within a
 * BufferedReader.  For example:
 *
 * <pre>
 * BufferedReader in
 *   = new BufferedReader(new InputStreamReader(anInputStream));
 * </pre>
 *
 * @see BufferedReader
 * @see InputStream
 * @see java.nio.charset.Charset
 *
 * @author      Mark Reinhold
 * @since       1.1
 */

public class SMTPService {

    private Socket sckt;
    private final String HOST = "mail.tecnoweb.org.bo";
    private final int PORT = 25;
    private final String EMISOR = "grupo06sc@tecnoweb.org.bo";
    private BufferedReader entrada;
    private DataOutputStream salida;

    public SMTPService() throws IOException {
        try {
            sckt = new Socket(HOST, PORT);
            entrada = new BufferedReader(new InputStreamReader(sckt.getInputStream()));
            salida = new DataOutputStream(sckt.getOutputStream());
        } catch (Exception e) {
            System.out.println("Excepcion: " + e);
        }
    }

    public void sendMessage(String sbjct, String receptor, String message) {
        try {
            System.out.println(entrada.readLine());
            salida.writeBytes("HELO mail.tecnoweb.org.bo\r\n");
            //entrada.readLine();
            getMultiline(entrada);
            salida.writeBytes("MAIL FROM: <" + EMISOR + "> \r\n");
            entrada.readLine();
            salida.writeBytes("RCPT TO: <" + receptor + "> \r\n");
            entrada.readLine();
            salida.writeBytes("DATA\r\n");
            entrada.readLine();
            String comando = "Subject:" + sbjct + "\r\n" + message + "\n" + ".\r\n";
            salida.writeBytes(comando);
            entrada.readLine();
            System.out.println("Respuesta de "+ EMISOR + " enviada a " + receptor);
        } catch (Exception e) {
            System.out.println("Excepcion al enviar respuesta: " + e);
        }
    }

    public List<String> getMultiline(BufferedReader in) {
        List<String> lines = new ArrayList<String>();
        try {
            while (true) {
                String line = in.readLine();
                if (line == null) {
                }
                if (line.charAt(3) == ' ') {
                    lines.add(line);
                    break;
                }
                lines.add(line);
            }
        } catch (Exception e) {
            System.out.println("Excepcion en getMultiline SMTPService: " + e);
        }
        return lines;
    }

    public void close() {
        try {
            salida.writeBytes("QUIT\r\n");
            entrada.readLine();
            entrada.close();
            salida.close();
            sckt.close();
        } catch (Exception e) {
            System.out.println("Excepcion al cerrar SMTPService: " + e);
        }
    }
}