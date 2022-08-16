package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientFormController extends Thread{


    public TextArea textArea;
    public TextField textMessage;
    public Label lblName;
    public JFXButton btnEmoji;
    public TextFlow emojiList;

    BufferedReader reader;
    PrintWriter writer;
    Socket socket;

    public void initialize(){

        for(Node text : emojiList.getChildren()){
            text.setOnMouseClicked(event -> {
                textMessage.setText(textMessage.getText()+" "+((Text)text).getText());
                emojiList.setVisible(false);
            });
        }

        lblName.setText(LoginFormController.name);

        try {
            socket = new Socket("localhost", 5000);
            System.out.println("Socket is connected with server!");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            while (true) {
                String msg = reader.readLine();
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];
                System.out.println(cmd);
                StringBuilder fullMsg = new StringBuilder();
                for (int i = 1; i < tokens.length; i++) {
                    fullMsg.append(tokens[i]);
                }
                System.out.println(fullMsg);
                System.out.println("cmd=" + cmd + "-----" + "UserName" + lblName.getText());
                if (!cmd.equalsIgnoreCase(lblName.getText() + ":")) {
                    textArea.appendText(msg + "\n");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendOnAction(ActionEvent event) throws IOException {
        String msg = textMessage.getText();
        writer.println(lblName.getText() + ": " + textMessage.getText());
        textArea.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        textArea.appendText("Me: " + msg + "\n");
        textMessage.clear();
        if (msg.equalsIgnoreCase("bye") || (msg.equalsIgnoreCase("logout"))) {
            Stage stage = (Stage) textMessage.getScene().getWindow();
            stage.close();
        }
    }

    public void btnEmojiOnAction(ActionEvent actionEvent) {
        if(emojiList.isVisible()){

            emojiList.setVisible(false);
        }else {
            emojiList.setVisible(true);
        }
    }
}

