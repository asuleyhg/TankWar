package net;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerFrame extends Frame {

    public static final ServerFrame INSTANCE = new ServerFrame();
    private Server server = new Server();
    private TextArea serverText;
    private TextArea clientText;

    private ServerFrame(){
        this.setTitle("tank Server");
        this.setSize(600, 400);
        this.setLocation(100, 100);
        Panel p = new Panel(new GridLayout(1, 2));
        this.serverText = new TextArea();
        this.clientText = new TextArea();
        p.add(serverText);
        p.add(clientText);
        this.add(p);

        this.updateServerMsg("Server");
        this.updateClientMsg("Client");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });



    }

    public void updateClientMsg(String msg) {
        this.clientText.setText(clientText.getText() + msg + System.getProperty("line.separator"));
    }

    public void updateServerMsg(String msg) {
        this.serverText.setText(serverText.getText() + msg + System.getProperty("line.separator"));
    }

    public static void main(String[] args) {
        ServerFrame.INSTANCE.setVisible(true);
        ServerFrame.INSTANCE.server.serverStart();
    }

}
