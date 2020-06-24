package com.dts.mpossocket;

import androidx.appcompat.app.AppCompatActivity;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dts.classes.clsCommit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Calendar;

public class Main extends AppCompatActivity {

    private TextView lblIP,lblMsg;
    private EditText txt1;

    private ServerSocket serverSocket;
    private Thread Thread1 = null;

    private clsCommit commit;

    private PrintWriter output;
    private BufferedReader input;

    private String tdata;
    private String command,answer;

    private String SERVER_IP;
    private static final int SERVER_PORT = 8080;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        lblIP = findViewById(R.id.textView2);lblIP.setText("");
        lblMsg = findViewById(R.id.textView);lblIP.setText("");
        txt1 = findViewById(R.id.editText1);

        try {
            SERVER_IP = getLocalIpAddress();
        } catch (UnknownHostException e) {
            SERVER_IP = "";
        }

        lblIP.setText("IP : "+SERVER_IP);

        Thread1 = new Thread(new SocketHandler());
        Thread1.start();

    }

    //region Events

    public void doAnswer(View view) {
        answer = txt1.getText().toString().trim();
        if (!answer.isEmpty()) {
            new Thread(new Sender(answer)).start();
        }
    }

    //endregion

    //region Handlers

    private class SocketHandler implements Runnable {

        @Override
        public void run() {
            Socket socket;

            try {

                serverSocket = new ServerSocket(SERVER_PORT);

                command="";

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        lblIP.setText("IP : "+SERVER_IP+" ");
                    }
                });

                try {

                    socket = serverSocket.accept();
                    output = new PrintWriter(socket.getOutputStream());
                    input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lblIP.setText("IP : " + SERVER_IP+" Conectado");
                        }
                    });

                    new Thread(new Receiver()).start();

                } catch (Exception e) {
                    showerr(e.getMessage());
                }

            } catch (Exception e) {
                showerr(e.getMessage());
            }
        }
    }

    private class Receiver implements Runnable {
        @Override
        public void run() {

            while (true) {
                try {

                    final String message = input.readLine();

                    if (message!=null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!message.isEmpty()) {
                                    command+=message ;
                                } else {
                                    //enter;
                                    lblMsg.setText("Received:" + command );
                                    command="INSERT INTO TEST VALUES('"+getCorelTimeStr()+"',0)";
                                    processCommand();
                                }
                            }
                        });
                    } else {
                        Thread1 = new Thread(new SocketHandler());
                        Thread1.start();
                        return;
                    }
                } catch (Exception e) {
                    showerr(e.getMessage());
                }
            }

        }
    }

    private class Sender implements Runnable {
        private String message;

        Sender(String message) {
            this.message = message;
        }

        @Override
        public void run() {

            try {
                output.write(message+ "\n\r");
                output.flush();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txt1.setText("");
                    }
                });
            } catch (Exception e) {
                showerr(e.getMessage());
            }

        }
    }

    //endregion

    //region Main

    private void processCommand() {
        try {
            commit=new clsCommit(this);
            commit.commit(command);
            commit.dispose();
        } catch (Exception e) {
            toast(new Object(){}.getClass().getEnclosingMethod().getName()+" . "+e.getMessage());
        }
    }

    //endregion

    //region Aux

    private String getLocalIpAddress() throws UnknownHostException {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        assert wifiManager != null;
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipInt = wifiInfo.getIpAddress();
        return InetAddress.getByAddress(
                ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(ipInt).array())
                .getHostAddress();
    }

    private void showerr(String errmsg) {
        final String err=errmsg;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lblMsg.setText(err);
            }
        });
    }

    public void toast(String msg) {
        Toast toast= Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public String getCorelTimeStr() {
        int cyear,cmonth,cday,ch,cm,cs,vd,vh;
        long f;

        Calendar c = Calendar.getInstance();

        cyear = c.get(Calendar.YEAR);cyear=cyear % 10;
        cmonth = c.get(Calendar.MONTH)+1;
        cday = c.get(Calendar.DAY_OF_MONTH);
        ch=c.get(Calendar.HOUR_OF_DAY);
        cm=c.get(Calendar.MINUTE);
        cs=c.get(Calendar.SECOND);

        vd=cyear*384+cmonth*32+cday;
        vh=ch*3600+cm*60+cs;

        f=vd*100000+vh;

        return ""+f;
    }

    //endregion

    //region Dialogs


    //endregion

    //region Activity Events


    //endregion

}