package tema;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Server {
    private static final int PORT = 12345;
    private static final Map<String, Double> stockPrices = new ConcurrentHashMap<>();
    private static final Map<Handler, List<String>> clientMonitors = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port " + PORT);
            new Thread(() -> updateStockPrices()).start();
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());
                Handler clientHandler = new Handler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void updateStockPrices() {
        Random random = new Random();
        while (true) {
            for (String symbol : stockPrices.keySet()) {
                double oldPrice = stockPrices.get(symbol);
                double newPrice = oldPrice + (random.nextDouble() * 2 - 1);
                stockPrices.put(symbol, newPrice);
                notifyClients(symbol, oldPrice, newPrice);
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void notifyClients(String symbol, double oldPrice, double newPrice) {
    	for (Map.Entry<Handler, List<String>> entry : clientMonitors.entrySet()) {
    	    Handler client = entry.getKey();
    	    List<String> monitoredSymbols = entry.getValue();
    	    if (monitoredSymbols.contains(symbol)) {
    	        double change = newPrice - oldPrice;
    	        double percentChange = (change / oldPrice) * 100;
    	        client.sendMessage(String.format("%s %.2f %.2f(%.2f%%)", symbol, newPrice, change, percentChange));
    	    }
    	}
    }

    public static void addClient(Handler client, List<String> symbols) {
        clientMonitors.put(client, symbols);
    }

    public static void removeClient(Handler client) {
        clientMonitors.remove(client);
    }

    public static void addSymbol(String symbol) {
        stockPrices.putIfAbsent(symbol, 100.0);
    }
}
