package com.acash.fitmate.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.acash.fitmate.R
import com.acash.fitmate.activities.*
import com.acash.fitmate.models.Inbox
import com.acash.fitmate.models.Request
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class NotificationWorker(private val context: Context, params: WorkerParameters) :
    Worker(context, params) {
    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    private val rtDb by lazy {
        FirebaseDatabase.getInstance("https://fitmate-f33d2-default-rtdb.asia-southeast1.firebasedatabase.app/")
    }

    override fun doWork(): Result {
        var isSuccessful = false

        val taskNewMsg = rtDb.reference.child("inbox/${auth.uid.toString()}").get()

        Tasks.await(taskNewMsg)

        val inboxListSnapshot = taskNewMsg.result

        if(inboxListSnapshot?.exists()==true) {
            for ((index, inboxSnapshot) in inboxListSnapshot.children.withIndex()) {
                val friendInInbox = inboxSnapshot.getValue(Inbox::class.java)
                friendInInbox?.apply {
                    if (count > 0) {
                        val title =
                            if (count == 1) "$count new message from $name"
                            else "$count new messages from $name"

                        val intent = Intent(context, ChatActivity::class.java)

                        intent.putExtra(NAME, name)
                        intent.putExtra(UID, from)
                        intent.putExtra(THUMBIMG, image)

                        val pi = PendingIntent.getActivity(
                            context,
                            index,
                            intent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                        )

                        showNotification(index+10000, title, msg, image, pi)
                    }
                }
            }

            isSuccessful = true
        }

        if (isSuccessful) {

            isSuccessful = false

            val taskNewRequests = rtDb.reference.child("requests/${auth.uid.toString()}").get()

            Tasks.await(taskNewRequests)

            val requestListSnapshot = taskNewRequests.result

            if(requestListSnapshot?.exists()==true){
                for ((index, requestSnapshot) in requestListSnapshot.children.withIndex()) {
                    val request = requestSnapshot.getValue(Request::class.java)
                    request?.apply {
                        val title = "New invitation"
                        val msg = "$name sent you a Partner Request"
                        val intent = Intent(context, MainActivity::class.java)

                        val pi = PendingIntent.getActivity(
                            context,
                            index,
                            intent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                        )

                        showNotification(index+20000, title, msg, downloadUrlDp, pi)
                    }
                }

                isSuccessful = true
            }
        }

        return if (isSuccessful)
            Result.success()
        else Result.retry()
    }

    private fun showNotification(id: Int, title:String, msg:String, dpUrl:String, pi:PendingIntent) {
        val nm: NotificationManager =
            context.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nm.createNotificationChannel(
                NotificationChannel(
                    "Notifications",
                    "New message/request",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    enableLights(true)
                    enableVibration(true)
                }
            )
        }

        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder(context, "Notifications")
        } else {
            NotificationCompat.Builder(context)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_LIGHTS or NotificationCompat.DEFAULT_VIBRATE)
        }

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        var largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.defaultavatar)

        if (dpUrl != "") {
            runBlocking(Dispatchers.IO) {
                try {
                    val url = URL(dpUrl)
                    val httpUrlConnection = url.openConnection() as HttpURLConnection
                    val inputStream = httpUrlConnection.inputStream
                    largeIcon = BitmapFactory.decodeStream(inputStream)
                } catch (e: MalformedURLException) {
                    Log.i("Notification img", e.message.toString())
                } catch (e: IOException) {
                    Log.i("Notification img", e.message.toString())
                }
            }
        }

        val notification = builder
            .setSmallIcon(R.mipmap.app_icon)
            .setLargeIcon(largeIcon)
            .setContentTitle(title)
            .setContentText(msg)
            .setContentIntent(pi)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(defaultSoundUri)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText(msg))
            .build()

        nm.notify(id, notification)
    }
}