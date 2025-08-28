package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RedeCliente {
    private static final String SERVER_ADDRESS = "192.168.3.60";
    private static final int SERVER_PORT = 80;

    public static String enviarMensagem(String mensagem) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println(mensagem);
            String respostaDoServidor = in.readLine();
            if (respostaDoServidor != null) {
                return respostaDoServidor.replace("&NL&", System.lineSeparator());
            }
            return "";
        } catch (IOException e) {
            return "Erro de conexão com o servidor: " + e.getMessage();
        }
    }
}