package br.com.ggsoftware.avisoperiodico;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ReceberAlarmeReceiber extends BroadcastReceiver {
    public ReceberAlarmeReceiber() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Calendar calendar = Calendar.getInstance();

        Calendar horario;
        SimpleDateFormat sdfCompleto = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("pt-BR"));

        LembreteDAO oLembreteDAO = new LembreteDAO(context);


        List<LembreteVO> lLembreteVO = oLembreteDAO.listar();


        for (LembreteVO oLembreteVO : lLembreteVO) {
            horario = Calendar.getInstance();

            try {
                horario.setTime(sdfCompleto.parse(oLembreteVO.getDataHora()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (calendar.get(Calendar.HOUR_OF_DAY) == horario.get(Calendar.HOUR_OF_DAY) && calendar.get(Calendar.MINUTE) == horario.get(Calendar.MINUTE) && calendar.get(Calendar.DAY_OF_MONTH) == horario.get(Calendar.DAY_OF_MONTH) && calendar.get(Calendar.MONTH) == horario.get(Calendar.MONTH)
                    && calendar.get(Calendar.YEAR) == horario.get(Calendar.YEAR)) {

                notificar(context, oLembreteVO.getLembrete());
                break;
            }

        }

    }

    public void notificar(Context context, String mensagem) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Lembrete")
                        .setContentText(mensagem);

        Intent resultIntent = new Intent(context, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        stackBuilder.addParentStack(MainActivity.class);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, mBuilder.build());
    }
}

