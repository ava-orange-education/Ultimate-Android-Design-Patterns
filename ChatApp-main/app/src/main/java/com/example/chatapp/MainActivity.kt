package com.example.chatapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.chat.model.ChatRepository
import com.example.chatapp.navigation.AppNavHost
import com.example.chatapp.ui.LoadingScreen
import com.example.chatapp.ui.MyBottomAppBar
import com.example.chatapp.ui.MyTopAppBar
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.contacts.model.ContactRepository
import com.example.core.db.ChatDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.chat.intent.ChatListReducer
import com.example.chat.intent.ChatReducer
import com.example.contacts.intent.ContactListReducer

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : ComponentActivity() {

    @Inject lateinit var chatRepository: ChatRepository
    @Inject lateinit var contactRepository: ContactRepository

    private val activity = this
    private val CHANNEL_ID = "chat_app_channel"
    private var isPermissionGranted = false
    private var notificationId = 1

    // Register for the permission result
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            isPermissionGranted = isGranted
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Request the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        enableEdgeToEdge()
        setContent {
            ChatAppTheme {
                val navController = rememberNavController()
                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                val isSettingUpDatabase = remember { mutableStateOf(false) }

                val chatListReducer = remember { ChatListReducer(chatRepository) }
                val chatListState by chatListReducer.state.collectAsStateWithLifecycle()

                val chatReducer = remember { ChatReducer(chatRepository) }
                val chatState by chatReducer.state.collectAsStateWithLifecycle()

                val contactListReducer = remember { ContactListReducer(contactRepository) }
                val contactListState by contactListReducer.state.collectAsStateWithLifecycle()


                LaunchedEffect(Unit) {
                    if (chatRepository.fetchConversations().isEmpty()) {
                        isSettingUpDatabase.value = true
                        withContext(Dispatchers.IO) {
                            val delayJob = launch {
                                delay(3000)
                            }
                            ChatDatabase.prepopulateDatabase(applicationContext)
                            delayJob.join()
                        }
                        isSettingUpDatabase.value = false
                    }
                }

                if (isSettingUpDatabase.value) {
                    LoadingScreen("Setting up database...")
                    return@ChatAppTheme
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        MyTopAppBar(
                            navController = navController,
                            currentDestination = currentBackStackEntry?.destination?.route,
                            chatState = chatState
                        )
                    },
                    bottomBar = {
                        MyBottomAppBar(
                            navController = navController,
                            currentDestination = currentBackStackEntry?.destination?.route
                        )
                    }
                ) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding).padding(8.dp)) {
                        AppNavHost(
                            navController = navController,
                            chatListReducer = chatListReducer,
                            chatListState = chatListState,
                            chatReducer = chatReducer,
                            chatState = chatState,
                            contactListReducer = contactListReducer,
                            contactListState = contactListState,
                            onSimulateMessageReceiving = { title, message ->
                                showNotification(title, message)
                            }
                        )
                    }
                }
            }
        }
    }

    fun createNotificationChannel(context: Context) {
        val channelName = "Incoming messages"
        val channelDescription = "Channel for incoming messages"
        val importance = NotificationManager.IMPORTANCE_HIGH

        val channel = NotificationChannel(CHANNEL_ID, channelName, importance).apply {
            description = channelDescription
        }

        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun showNotification(title: String, message: String) {
        val intent = Intent(activity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(
            activity,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(activity, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(activity)) {
            if (ActivityCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(notificationId++, builder.build())
        }
    }

}