# Stock-Price-Monitoring-Client-Server-Application
This project is a Java-based client-server application designed for monitoring stock prices


## Features

- **Real-Time Stock Price Updates**:
  - The server generates random price changes for stocks every 5 seconds.
  - Clients receive updates for the stock symbols they monitor.

- **Client Commands**:
  - `ADD <symbol>`: Subscribe to updates for a specific stock symbol.
  - `DEL <symbol>`: Unsubscribe from updates for a specific stock symbol.
  - `QUIT`: Disconnect from the server.

- **Server Notifications**:
  - Includes the updated stock price, absolute change, and percentage change.
  - Example: `AAPL 102.50 +2.50(2.50%)`.

- **Concurrent Connections**:
  - Supports multiple clients, each maintaining their own monitoring list.

---

## Architecture

1. **Server**:
   - Listens on port `12345` for client connections.
   - Maintains:
     - A map of stock symbols and their current prices.
     - A list of clients and their monitored stock symbols.
   - Notifies relevant clients when stock prices change.

2. **Client**:
   - Sends commands to the server.
   - Receives notifications about price updates and command responses.

3. **Handler**:
   - A dedicated thread for each connected client.
   - Manages client-specific actions, such as adding or removing stock symbols.

---

## Protocol

### 1. Messages from Client to Server

- **Format**: `<COMMAND> [ARGUMENT]`
- **Commands**:
  - `ADD <symbol>`: Subscribe to updates for a stock symbol.
  - `DEL <symbol>`: Unsubscribe from updates for a stock symbol.
  - `QUIT`: Disconnect from the server.

- **Examples**:
  - `ADD AAPL`
  - `DEL GOOGL`
  - `QUIT`

### 2. Messages from Server to Client

- **Types of Responses**:
  - Confirmations for commands:
    - Example: `Added AAPL`, `Removed GOOGL`
  - Price update notifications:
    - Example: `AAPL 102.50 +2.50(2.50%)`
  - Error messages for invalid commands:
    - Example: `Invalid command.`

---

## How to Run

1. **Server**:
   - Compile and run the server:
     ```bash
     javac Server.java
     java Server
     ```

2. **Client**:
   - Compile and run the client:
     ```bash
     javac Client.java
     java Client
     ```

3. **Interaction**:
   - Use the following commands in the client terminal:
     - `ADD <symbol>`: Add a stock symbol to monitor.
     - `DEL <symbol>`: Remove a stock symbol from monitoring.
     - `QUIT`: Exit the client.

---

## Example Interaction

1. Client sends: `ADD AAPL`
2. Server responds: `Added AAPL`
3. Server sends price update: `AAPL 102.50 +2.50(2.50%)`
4. Client sends: `DEL AAPL`
5. Server responds: `Removed AAPL`
6. Client sends: `QUIT`
7. Server closes the connection.






