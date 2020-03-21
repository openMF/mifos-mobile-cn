package org.mifos.mobile.cn.ui.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.core.view.ViewCompat
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.mifos.mobile.passcode.BasePassCodeActivity
import org.mifos.mobile.cn.MifosApplication
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.injection.component.ActivityComponent
import org.mifos.mobile.cn.injection.component.ConfigPersistentComponent
import org.mifos.mobile.cn.injection.component.DaggerConfigPersistentComponent
import org.mifos.mobile.cn.injection.module.ActivityModule
import org.mifos.mobile.cn.ui.mifos.passcode.PasscodeActivity
import timber.log.Timber
import org.mifos.mobile.cn.ui.utils.ProgressBarHandler
import java.lang.Exception
import java.util.*
import java.util.concurrent.atomic.AtomicLong


/**
 * @author Rajan Maurya
 * On 22/01/18.
 */
@SuppressLint("Registered")
open class MifosBaseActivity : BaseActivityCallback, BasePassCodeActivity() {

    companion object {
        @JvmStatic
        private val KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID"
        @JvmStatic
        private val NEXT_ID = AtomicLong(0)
        @SuppressLint("UseSparseArrays")
        @JvmStatic
        private val componentsMap = HashMap<Long, ConfigPersistentComponent>()
    }

    private var activityId: Long = 0
    lateinit var activityComponent: ActivityComponent
    private lateinit var progressBarHandler: ProgressBarHandler
    private lateinit var toolbar: Toolbar

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        try {
            toolbar = findViewById(R.id.toolbar)
            setSupportActionBar(toolbar)
        } catch (e: Exception) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressBarHandler = ProgressBarHandler(this)

        // Create the ActivityComponent and reuses cached ConfigPersistentComponent if this is
        // being called after a configuration change.
        activityId = savedInstanceState?.getLong(KEY_ACTIVITY_ID) ?: NEXT_ID.getAndIncrement()

        if (componentsMap[activityId] != null)
            Timber.i("Reusing ConfigPersistentComponent id=%d", activityId)

        val configPersistentComponent = componentsMap.getOrPut(activityId, {
            Timber.i("Creating new ConfigPersistentComponent id=%d", activityId)

            val component = (applicationContext as MifosApplication).applicationComponent
            DaggerConfigPersistentComponent.builder()
                    .applicationComponent(component)
                    .build()
        })

        activityComponent = configPersistentComponent.activityComponent(ActivityModule(this))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(KEY_ACTIVITY_ID, activityId)
    }

    fun hideKeyBoard(view: View) {
        val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager
                .RESULT_UNCHANGED_SHOWN)
    }

    fun showBackButton() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun showProgressbar() {
        progressBarHandler.show()
    }

    fun hideProgressbar() {
        progressBarHandler.hide()
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun getToolbar(): Toolbar {
        return toolbar
    }

    override fun showJusticeProgressDialog(message: String) {

    }

    override fun showTabLayout(show: Boolean) {

    }

    override fun setToolbarTitle(toolbarTitle: String) {
        title = toolbarTitle
    }

    override fun hideJusticeProgressDialog() {

    }

    override fun logout() {

    }

    override fun getPassCodeClass(): Class<*> {
        return PasscodeActivity::class.java
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Used for setting toolbar elevation
     */
    fun setToolbarElevation() {
        ViewCompat.setElevation(toolbar, 8f)
    }

    /**
     * Used for removing elevation from toolbar
     */
    fun hideToolbarElevation() {
        ViewCompat.setElevation(toolbar, 0f)
    }

    /**
     * Replace Fragment in FrameLayout Container.
     *
     * @param fragment Fragment
     * @param addToBackStack Add to BackStack
     * @param containerId Container Id
     */
    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean, containerId: Int) {
        invalidateOptionsMenu()
        val backStateName = fragment.javaClass.name
        val fragmentPopped = supportFragmentManager.popBackStackImmediate(backStateName, 0)
        if (!fragmentPopped && supportFragmentManager.findFragmentByTag(backStateName) == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(containerId, fragment, backStateName)
            if (addToBackStack) {
                transaction.addToBackStack(backStateName)
            }
            transaction.commit()
        }
    }

    fun clearFragmentBackStack() {
        val fm = supportFragmentManager
        val backStackCount = supportFragmentManager.backStackEntryCount
        for (i in 0 until backStackCount) {
            val backStackId = supportFragmentManager.getBackStackEntryAt(i).id
            fm.popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    fun stackCount(): Int {
        return supportFragmentManager.backStackEntryCount
    }

    override fun onDestroy() {
        if (!isChangingConfigurations) {
            Timber.i("Clearing ConfigPersistentComponent id=%d", activityId)
            componentsMap.remove(activityId)
        }
        super.onDestroy()
    }
}
