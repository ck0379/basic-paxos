package runtime;

import message.PaxosMessage;
import role.PaxosRole;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;


public class MessageReceiverThread extends Thread {

    private ConnectionProtocol runOnTCPOrUDP = ConnectionProtocol.TCP_CONNECTION;

    private int portNumber;
    private PaxosRole roleInstance;

    public MessageReceiverThread(int portNumber, PaxosRole roleInstance) {

        this.runOnTCPOrUDP = GlobalConfig.INSTANCE.getConnectionProtocol();
        this.portNumber = portNumber;
        this.roleInstance = roleInstance;

        this.start();
    }

    public synchronized void run() {

        switch (this.runOnTCPOrUDP) {
            case TCP_CONNECTION:
                this.runOnTCP();
                break;
            case UDP_CONNECTION:
                this.runOnUDP();
                break;
        }
    }

    public void runOnTCP() {

        ServerSocket serverSocket;
        Socket socket = null;
        ObjectInputStream objectInputStream;

        PaxosMessage message = null;

        try {
            serverSocket = new ServerSocket(this.portNumber);

            while (true) {
                socket = serverSocket.accept();
                objectInputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                Object acceptedObject = objectInputStream.readObject();
                if (acceptedObject != null) {
                    message = (PaxosMessage)acceptedObject;
                }
                synchronized (System.out) {
                    System.out.println(message.toReceiveString());
                    System.out.flush();
                }
                this.roleInstance.handleMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch(Exception ex) {}
        }
    }

    public void runOnUDP() {
        DatagramSocket datagramSocket = null;
        byte[] buffer = new byte[1024];
        PaxosMessage message = null;
        try {
            datagramSocket = new DatagramSocket(this.portNumber);
            DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);
            datagramSocket.receive(receivedPacket);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer);
            ObjectInputStream objectStream = null;
            objectStream = new ObjectInputStream(byteArrayInputStream);
            message = (PaxosMessage) objectStream.readObject();
            synchronized (System.out) {
                System.out.println(message.toReceiveString());
                System.out.flush();
            }
            objectStream.close();
            byteArrayInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.roleInstance.handleMessage(message);
    }
}