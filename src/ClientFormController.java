import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class ClientFormController {


    final int PORT = 5000;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;


    String message = "";

    public TextArea textArea;
    public TextField textMessage;

    public void initialize() {

        new Thread(() -> {
            try {
                socket = new Socket("localhost", PORT);

                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());
                while (!message.equals("exit")) {

                    message = dataInputStream.readUTF();
                    textArea.appendText("\n Server : " + message);

                }

                } catch (IOException e) {
                    e.printStackTrace();
                }

        }).start();
    }

        public void sendOnAction (ActionEvent event) throws IOException {
        dataOutputStream.writeUTF(textMessage.getText().trim());
        dataOutputStream.flush();

        }
    }

