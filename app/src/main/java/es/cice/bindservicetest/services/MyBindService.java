package es.cice.bindservicetest.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

import es.cice.bindservicetest.R;

public class MyBindService extends Service {
    private Messenger messenger;
    public final static  int MSG1=1;
    public final static  int MSG2=2;
    public final static  int MSG3=3;

    public MyBindService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
    //ibinder interfaz de conexión remota
        return messenger.getBinder();
    }
    public class MyHandler extends Handler {

        //por aquí recibe los mensajes
        @Override
        public void handleMessage(Message msg) {
            Notification.Builder builder;
            Notification n;
            switch (msg.what){
                case MSG1:
                    builder=new Notification.Builder(getApplicationContext());
                    builder
                            .setContentText("Mensaje recibido")
                            .setSmallIcon(R.drawable.ic_msg1)
                            .setContentTitle(" MSG1");
                    n=builder.build();
                    ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(1,n);
                    break;
                case MSG2:
                    builder=new Notification.Builder(getApplicationContext());
                    builder
                            .setContentText("Mensaje recibido")
                            .setSmallIcon(R.drawable.ic_msg2)
                            .setContentTitle(" MSG2");
                    n=builder.build();
                    ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(1,n);
                    break;
                case MSG3:
                    builder=new Notification.Builder(getApplicationContext());
                    builder
                            .setContentText("Mensaje recibido")
                            .setSmallIcon(R.drawable.ic_msg3)
                            .setContentTitle(" MSG3");
                    n=builder.build();
                    ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(1,n);
                    break;

            }

        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MyHandler handler=new MyHandler();
        //construimos el messenger vinculado al handler
        messenger=new Messenger(handler);
    }
}
