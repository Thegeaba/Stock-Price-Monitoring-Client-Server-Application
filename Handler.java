package tema;

import java.io.*;
import java.net.*;
import java.util.*;

class Handler implements Runnable {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private List<String> monitoredSymbols;

    public Handler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.monitoredSymbols = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String message;
            while ((message = in.readLine()) != null) {
                String[] parts = message.split(" ");
                if (parts[0].equalsIgnoreCase("ADD") && parts.length == 2) {
                    String symbol = parts[1];
                    if (monitoredSymbols.size() < 5) {
                        monitoredSymbols.add(symbol);
                        Server.addSymbol(symbol);
                        Server.addClient(this, monitoredSymbols);
                        out.println("Added " + symbol);
                    } else {
                        out.println("Cannot monitor more than 5 symbols.");
                    }
                } else if (parts[0].equalsIgnoreCase("DEL") && parts.length == 2) {
                    String symbol = parts[1];
                    monitoredSymbols.remove(symbol);
                    out.println("Removed " + symbol);
                } else if (parts[0].equalsIgnoreCase("QUIT")) {
                    break;
                } else {
                    out.println("Invalid command.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                Server.removeClient(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }
}