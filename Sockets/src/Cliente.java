import java.io.*;
import java.net.*;

public class Cliente {

    public static void main(String[] args) {
        String serverAddress = "144.22.201.166";
        int port = 9000;

        try (Socket socket = new Socket(serverAddress, port)) {
            System.out.println("Conectado ao servidor");

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            String ra = "12345";
            String senha = "senha123";
            String regUserMessage = "REG USER " + ra + " " + senha;
            outputStream.write((regUserMessage + "\n").getBytes());
            System.out.println("Enviado: " + regUserMessage);

            String response = readResponse(inputStream);
            System.out.println("Resposta do servidor: " + response);

            String askSecretMessage = "ASK SECRET " + ra + " " + senha;
            outputStream.write((askSecretMessage + "\n").getBytes());
            System.out.println("Enviado: " + askSecretMessage);

            response = readResponse(inputStream);
            System.out.println("Resposta do servidor: " + response);

            if (response != null && response.startsWith("RESPONSE 200")) {
                String secretWord = response.split(" ")[1];
                System.out.println("Palavra secreta recebida: " + secretWord);
            }

        } catch (IOException e) {
            System.err.println("Erro de conexão: " + e.getMessage());
        }
    }

    private static String readResponse(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);

        return new String(buffer, 0, bytesRead);
    }
}
