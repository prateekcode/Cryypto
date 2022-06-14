package com.prateekcode.cryypto.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.prateekcode.cryypto.R

/**
 * Kotlin Extensions for simpler, easier and fun way
 * of launching of Activities
 */

inline fun <reified T : Any> Activity.launchActivity(
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivityForResult(intent, requestCode, options)
}

inline fun <reified T : Any> Context.launchActivity(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivity(intent, options)
}

inline fun <reified T : Any> Context.launchActivity(
    activity: FragmentActivity,
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    activity.startActivityForResult(intent, requestCode, options)
}

inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)

/**
 * The `fragment` is added to the container view with id `frameId`. The operation is
 * performed by the `fragmentManager`.
 */
fun AppCompatActivity.replaceFragmentInActivity(
    fragment: Fragment, @IdRes frameId: Int,
    addBackStack: String? = null
) {
    supportFragmentManager.beginTransaction().apply {
        if (addBackStack != null) {
            addToBackStack(addBackStack)
        }
        setCustomAnimations(
            R.anim.slide_in,
            R.anim.slide_out,
            R.anim.fade_in,
            R.anim.slide_out
        )
        replace(frameId, fragment, addBackStack)
        commitAllowingStateLoss()
    }
}

fun AppCompatActivity.removeFragmentFromContainer(@IdRes containerId: Int) {
    val fragment = supportFragmentManager.findFragmentById(containerId)
    if (fragment != null)
        supportFragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss()
}

