package org.intelehealth.swasthyasamparktelemedicine.syncModule;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import org.intelehealth.swasthyasamparktelemedicine.R;
import org.intelehealth.swasthyasamparktelemedicine.appointment.dao.AppointmentDAO;
import org.intelehealth.swasthyasamparktelemedicine.appointment.model.AppointmentInfo;
import org.intelehealth.swasthyasamparktelemedicine.utilities.Logger;
import org.intelehealth.swasthyasamparktelemedicine.utilities.SessionManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SyncWorkManager extends Worker {

    private SessionManager sessionManager = null;
    private String TAG = SyncWorkManager.class.getSimpleName();

    public SyncWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        sessionManager = new SessionManager(context);
    }


    @NonNull
    @Override
    public Result doWork() {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            Logger.logE(TAG, "Exception in doWork method", e);
        }
        Logger.logD(TAG, "doWork");
        //Logger.logD(TAG, "result job");

        SyncUtils syncUtils = new SyncUtils();
        syncUtils.syncBackground();

        //displayNotification("My Worker", "Hey I finished my work");
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    AppointmentDAO appointmentDAO = new AppointmentDAO();
                    List<AppointmentInfo> appointmentInfoList = appointmentDAO.getAppointments();
                    if (appointmentInfoList != null) {
                        for (AppointmentInfo appointmentInfo : appointmentInfoList) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault());
                            String currentDateTime = dateFormat.format(new Date());
                            String slottime = appointmentInfo.getSlotDate() + " " + appointmentInfo.getSlotTime();

                            long diff = dateFormat.parse(slottime).getTime() - dateFormat.parse(currentDateTime).getTime();
                            long second = diff / 1000;
                            long minutes = second / 60;
                            if (minutes > 0 && minutes <= 45) {
                                displayNotification("Appointment!", "Today at" + appointmentInfo.getSlotTime() + " " + appointmentInfo.getPatientName() + " has an appointment with " + appointmentInfo.getDrName());
                            }
                            if (minutes <= 0) {
                                appointmentDAO.deleteAppointmentByVisitId(appointmentInfo.getVisitUuid());
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 15000);


        return Result.success();
    }

    private void displayNotification(String title, String task) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("swasthyasamparktelemedicine", "swasthyasamparktelemedicine", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "swasthyasamparktelemedicine")
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(task))
                //.setContentText(task)
                .setSmallIcon(R.mipmap.ic_launcher);

        notificationManager.notify(1, notification.build());
    }
}
