package es.cice.bindservicetest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Random;

import es.cice.bindservicetest.services.MyBindService;

public class MainActivity extends AppCompatActivity {

    private Messenger messenger;
    public static final String TAG="MainActivity";
    private Button connectionBtn;
    private Button sendMessageBtn;

    public class MyServiceConnection implements ServiceConnection{


        //el ibinder que envía el servicio
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //envolvemos el ibinder en un messenger pq este tiene métodos
            //esto funciona si sabemos que el ibinder está asociado a un messenger
            messenger=new Messenger(service);
            connectionBtn.setEnabled(false);
            sendMessageBtn.setEnabled(true);
            Log.d(TAG, "recibido el IBinder del servicio conectado....");

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            messenger=null;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectionBtn =(Button)findViewById(R.id.btn1);
        sendMessageBtn=(Button)findViewById(R.id.btn2);
        sendMessageBtn.setEnabled(false);

    }
    public void bindToService(View v){
        bindService(new Intent(this, MyBindService.class), new MyServiceConnection(), Context.BIND_AUTO_CREATE);
    }

    public void sendMessage(View v){
        Random rnd=new Random();
        int msgType=rnd.nextInt(3);
        Message msg=Message.obtain();
        msg.what=msgType+1;
        Log.d(TAG, "Enviando mensaje de tipo " + msg.what);
        try {
            messenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
