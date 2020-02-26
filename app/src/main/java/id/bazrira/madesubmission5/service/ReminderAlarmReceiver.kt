package id.bazrira.madesubmission5.service

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import id.bazrira.madesubmission5.R
import id.bazrira.madesubmission5.api.ApiHelper
import id.bazrira.madesubmission5.api.data.movie.MovieDAO
import id.bazrira.madesubmission5.api.response.MovieResponse
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ReminderAlarmReceiver : BroadcastReceiver() {

    private val API_KEY = "b59545fa485ebb68339cb053f3164aac"

    companion object {
        const val TYPE_DAILY = "Daily Reminder"
        const val TYPE_RELEASE = "Release Reminder"
        const val EXTRA_MESSAGE = "message"
        const val EXTRA_TYPE = "type"
        // Siapkan 2 id untuk 2 macam alarm, onetime dan repeating
        private const val ID_DAILY = 100
        private const val ID_RELEASE = 101
    }

    override fun onReceive(context: Context, intent: Intent) {
        val type = intent.getStringExtra(EXTRA_TYPE)
        val message = intent.getStringExtra(EXTRA_MESSAGE)
        val title = if (type.equals(TYPE_DAILY, ignoreCase = true)) TYPE_DAILY else TYPE_RELEASE
        val notifId = if (type.equals(TYPE_DAILY, ignoreCase = true)) ID_DAILY else ID_RELEASE

        if (notifId == ID_RELEASE)
            getData(context, message, notifId)
        else
            showDailyNotification(context, title, message, notifId)
    }

    fun setDailyReminder(context: Context, type: String, message: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, ReminderAlarmReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_TYPE, type)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 7)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        Toast.makeText(context, "Daily Reminder set up", Toast.LENGTH_SHORT).show()
    }

    fun setReleaseReminder(context: Context, type: String, message: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, ReminderAlarmReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_TYPE, type)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 8)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        Toast.makeText(context, "Release Reminder set up", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("SimpleDateFormat")
    fun getData(context: Context, message: String, notifId: Int) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val dateNow = dateFormat.format(Date())
        val url =
            "discover/movie?api_key=${API_KEY}&primary_release_date.gte=${dateNow}&primary_release_date.lte=${dateNow}"

        var id = notifId

        ApiHelper.apiService.getMovie(url)
            .enqueue(object : retrofit2.Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    try {
                        if (response.isSuccessful || response.code() == 200) {
                            response.body()?.results?.let {
                                for (m in it) {
                                    val poster =
                                        if (m.posterPath != null) "https://image.tmdb.org/t/p/w185/${m.posterPath}" else ""

                                    val backDrop =
                                        if (m.backdropPath != null) "https://image.tmdb.org/t/p/w500/${m.backdropPath}" else ""

                                    val sdfDate =
                                        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                    sdfDate.timeZone = TimeZone.getTimeZone("UTC")

                                    val formatDate = try {
                                        val parseDate = sdfDate.parse(m.releaseDate)
                                        SimpleDateFormat("dd-MM-yyyy").format(parseDate)
                                    } catch (e: Exception) {
                                        ""
                                    }

                                    val movieItems =
                                        MovieDAO(
                                            m.id,
                                            m.title,
                                            m.voteAverage,
                                            formatDate,
                                            poster,
                                            backDrop,
                                            m.overview
                                        )

                                    showReleaseNotification(context, movieItems, message, id++)
                                    SystemClock.sleep(500)
                                }
                            }
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.message.toString())
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                }
            })
    }

    private fun showReleaseNotification(
        context: Context,
        movie: MovieDAO,
        message: String,
        notifId: Int
    ) {
        val CHANNEL_ID = "Channel_1"
        val CHANNEL_NAME = "AlarmManager channel"
        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_access_time_black_36dp)
            .setContentTitle(movie.title)
            .setContentText("${movie.title} $message")
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(CHANNEL_ID)
            notificationManagerCompat.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManagerCompat.notify(notifId, notification)
    }

    private fun showDailyNotification(
        context: Context,
        title: String,
        message: String,
        notifId: Int
    ) {
        val CHANNEL_ID = "Channel_1"
        val CHANNEL_NAME = "AlarmManager channel"
        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_access_time_black_36dp)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(CHANNEL_ID)
            notificationManagerCompat.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManagerCompat.notify(notifId, notification)
    }

    fun cancelAlarm(context: Context, type: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderAlarmReceiver::class.java)
        val requestCode = if (type.equals(TYPE_DAILY, ignoreCase = true)) ID_DAILY else ID_RELEASE
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, "$type dibatalkan", Toast.LENGTH_SHORT).show()
    }
}