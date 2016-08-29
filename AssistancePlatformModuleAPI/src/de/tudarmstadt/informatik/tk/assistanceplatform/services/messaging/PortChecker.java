package de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging;

import java.net.InetSocketAddress;
import java.net.Socket;

public class PortChecker {
  public static boolean portIsOpen(String ip, int port, int timeout) {
    try {
      Socket socket = new Socket();
      socket.connect(new InetSocketAddress(ip, port), timeout);
      socket.close();
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  public static boolean portIsOpen(String hostString, int timeout) {
    String withoutPrefix = hostString.replace("tcp://", "");
    String[] split = withoutPrefix.split(":");

    String ip = split[0];
    int port = Integer.parseInt(split[1]);

    return portIsOpen(ip, port, timeout);
  }
}
