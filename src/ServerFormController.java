import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFormController {

    @FXML
    public TextArea textArea;
    @FXML
     public TextField textMessage;

    final int PORT = 5000;
    ServerSocket serverSocket;
    Socket accept;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    String message = "";

    public void initialize(){

        new Thread(() -> {

                try {
                    serverSocket = new ServerSocket(PORT);
                    textArea.appendText("Server Started..");

                    accept = serverSocket.accept();
                    textArea.appendText("Client Connected..");

                    dataOutputStream = new DataOutputStream(accept.getOutputStream());
                    dataInputStream = new DataInputStream(accept.getInputStream());
                    while (!message.equals("exit")) {

                        message = dataInputStream.readUTF();
                        textArea.appendText("\n Client : " + message);


                    }

                    String message = dataInputStream.readUTF();
                    textArea.appendText(message);



                } catch (IOException e) {
                    e.printStackTrace();
                }

        }).start();


    }

    public void sendOnAction(ActionEvent actionEvent) throws IOException {
        dataOutputStream.writeUTF(textMessage.getText().trim());
        dataOutputStream.flush();
    }
}
