# Stock-Price-Monitoring-Client-Server-Application
This project is a Java-based client-server application designed for monitoring stock prices


Features

  1. Stock Price Updates using random numbers:
    - The server generates random stock price fluctuations every 5 seconds.
    - Clients are notified of changes for the stock symbols they are monitoring.

  2. Client Commands:
    - ADD <symbol>: Subscribe to a stock symbol.
    - DEL <symbol>: Unsubscribe from a stock symbol.
    - QUIT: Disconnect from the server.

  3. Server Notifications:
    - Updates include the new stock price, absolute change, and percentage change.
    - Example: AAPL 102.50 +2.50(2.50%).

  4. Concurrency:
    - Supports multiple clients simultaneously, each with an independent monitoring list.


Architecture

  1. Server:
    - Listens for client connections on port 12345.
    - Maintains a shared map of stock symbols and prices, along with client subscriptions.
    - Sends price updates only to clients monitoring affected symbols.

  2. Client:
    - Sends commands to the server.
    - Listens for server responses, including price updates and command confirmations.

  3. Handler:
    - A dedicated thread for each client connection.
    - Manages client-specific monitoring lists and communication.


Protocol

  1. Client to Server:
    - Commands are structured as <COMMAND> [ARGUMENT].
    - Examples:
      ADD AAPL – Start monitoring AAPL.
      DEL AAPL – Stop monitoring AAPL.
      QUIT – Close the connection.

  2. Server to Client:
    - Notifications and confirmations.
    - Examples:
      Added AAPL
      Removed AAPL
      AAPL 102.50 +2.50(2.50%)


How to Run

  1. Compile and run the server (Server.java).
  2. Start one or more clients (Client.java).
  3. Use client commands to interact with the server.

This application showcases basic client-server communication and threading in Java, making it a good foundation for understanding distributed systems.






