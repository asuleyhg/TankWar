package nettyChat;

import java.awt.*;

public class ClientFrame  extends Frame{
    private TextArea textArea = new TextArea();
    private TextField textField = new TextField();
    private Client c = null;

    public ClientFrame(){
        this.setSize(300, 400);
        this.setLocation(300, 100);
        this.setTitle("chat");
        this.textArea.setEditable(false);
        this.add(textArea, BorderLayout.CENTER);
        this.add(textField, BorderLayout.SOUTH);
        textField.addActionListener(e -> {
            if(!"".equals(textField.getText().trim()) && textField != null) {
                textArea.setText(textArea.getText() + textField.getText() + "\r\n");
                textField.setText("");
            }else{
                
            }
        });
        c = new Client();
        c.connect();


    }
    public static void main(String[] args) {
        ClientFrame clientFrame = new ClientFrame();
        clientFrame.setVisible(true);
    }
}
