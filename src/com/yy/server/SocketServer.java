package com.yy.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yy on 17-6-12.
 */
public class SocketServer {
    public static void main(String[] args) {
        SocketServer socketServer = new SocketServer();
        socketServer.startServer();
    }

    public void startServer() {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(9898);
            System.out.println("server started...");
            while (true) {
                socket = serverSocket.accept();//程序阻塞，等待客户端的接入
                managerConnection(socket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 管理服务器与客户端的连接
     *
     * @param socket
     */
    public void managerConnection(final Socket socket) {

        //每一个客户端连接都开启一个子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader reader = null;
                BufferedWriter writer = null;
                System.out.println("client " + socket.hashCode() + " connedted");  //表示是哪个客户端连接

                try {
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    //客户端发送过来的消息
                    String receiveDMsg;
                    //从reader读取一行数据打印出来
                    while ((receiveDMsg = reader.readLine()) != null) {
                        System.out.println("client " + socket.hashCode() + ":" + receiveDMsg);

                        writer.write("server reply:" + receiveDMsg + "\n");
                        writer.flush();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        reader.close();
                        writer.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
