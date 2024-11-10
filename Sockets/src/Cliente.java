import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String serverAddress = "144.22.201.166";
        int port = 9000;

        try (Socket socket = new Socket(serverAddress, port)) {
            System.out.println("Conectado ao servidor");

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            System.out.print("Digite a sua mensagem:");
            var mensagem = sc.nextLine();
            outputStream.write((mensagem + "\n").getBytes());
            System.out.println("Enviado: " + mensagem);

            String resposta = readResponse(inputStream);
            System.out.println("Resposta do servidor: " + resposta);

            System.out.print("Digite a sua mensagem:");
            mensagem = sc.nextLine();
            outputStream.write((mensagem + "\n").getBytes());
            System.out.println("Enviado: " + mensagem);

            resposta = readResponse(inputStream);
            System.out.println("Resposta do servidor: " + resposta);

            String secretWord = resposta.split(" ")[1];
            System.out.println("Palavra secreta recebida: " + secretWord);

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
