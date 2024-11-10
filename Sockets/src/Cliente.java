import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String endereco = "144.22.201.166";
        int porta = 9000;

        try (Socket socket = new Socket(endereco, porta)) {
            System.out.println("Conectado ao servidor");

            OutputStream entrada = socket.getOutputStream();
            InputStream saida = socket.getInputStream();

            System.out.print("Digite a sua mensagem:");
            var mensagem = sc.nextLine();
            entrada.write((mensagem + "\n").getBytes());
            System.out.println("Enviado: " + mensagem);

            String resposta = readResponse(saida);
            System.out.println("Resposta do servidor: " + resposta);

            System.out.print("Digite a sua mensagem:");
            mensagem = sc.nextLine();
            entrada.write((mensagem + "\n").getBytes());
            System.out.println("Enviado: " + mensagem);

            resposta = readResponse(saida);
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
