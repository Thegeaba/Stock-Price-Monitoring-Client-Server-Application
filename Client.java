package tema;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Connected to server. Type commands:");
            Scanner scanner = new Scanner(System.in);

            Thread listener = new Thread(() -> {
                try {
                    String response;
                    while ((response = in.readLine()) != null) {
                        System.out.println("Update: " + response);
                    }
                } catch (IOException e) {
                    System.out.println("Connection closed.");
                }
            });
            listener.start();

            while (true) {
                String command = scanner.nextLine();
                out.println(command);
                if (command.equalsIgnoreCase("QUIT")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}