package com.fabianolira.appmiguelasnews.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.fabianolira.appmiguelasnews.R;
import com.fabianolira.appmiguelasnews.activity.MainActivity;
import com.fabianolira.appmiguelasnews.activity.NoticiasDetalhesActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage notificacao) {

        String titulo = notificacao.getNotification().getTitle();
        String corpo = notificacao.getNotification().getBody();

        enviarNotificacao(titulo, corpo);
        //Log.i("notificacao", "Notificação recebida");
    }

    private void enviarNotificacao(String titulo, String corpo){

        String canal = getString(R.string.default_notification_channel_id);
        Uri uriSom = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        // Criar notificacao
        NotificationCompat.Builder notificacao = new NotificationCompat.Builder(this, canal)
                .setContentTitle(titulo)
                .setContentText(corpo)
                .setSmallIcon(R.drawable.ic_sentiment_satisfied_black_24dp)
                .setSound(uriSom)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        // Recuperar notificationManager
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Verifica versao do android a partir do oreo para configurar canal de notificacao
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(canal, "canal", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);

        }

        // Envia notificacao
        notificationManager.notify(0, notificacao.build());

    }


    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }
}


