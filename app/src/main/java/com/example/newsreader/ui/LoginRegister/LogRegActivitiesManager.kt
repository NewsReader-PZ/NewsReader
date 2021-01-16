package com.example.newsreader.ui.LoginRegister
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

object LogRegActivitiesManager
{
    lateinit var callingActivity : AppCompatActivity
    var activityPassed = false
    fun passActivity(activity :AppCompatActivity)
    {

        if(!activityPassed) {
            callingActivity = activity
            activityPassed = true
        }
    }
    fun moveToLogin()
    {
        if(activityPassed)
        {
            val intent = Intent( callingActivity, LoginActivity::class.java )
            callingActivity.startActivity(intent)
        }
    }
}
