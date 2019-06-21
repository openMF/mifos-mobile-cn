package org.mifos.mobile.cn.ui.mifos

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.google.android.material.navigation.NavigationView
import androidx.fragment.app.FragmentManager
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.local.PreferencesHelper
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.mifos.dashboard.DashboardFragment
import org.mifos.mobile.cn.ui.mifos.login.LoginActivity
import org.mifos.mobile.cn.ui.utils.MaterialDialog
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_main.*
import org.mifos.mobile.cn.enums.AccountType
import org.mifos.mobile.cn.ui.mifos.customerAccounts.CustomerAccountFragment
import org.mifos.mobile.cn.ui.mifos.products.ProductFragment
import org.mifos.mobile.cn.ui.utils.CircularImageView
import org.mifos.mobile.cn.ui.utils.Toaster

class DashboardActivity : MifosBaseActivity(), View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    @Inject
    internal lateinit var preferencesHelper: PreferencesHelper

    private lateinit var tvUsername: TextView
    private lateinit var ivCircularUserProfilePicture: CircularImageView
    private lateinit var ivTextDrawableUserProfilePicture: ImageView

    private var menuItem = -1
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbarTitle(getString(R.string.home))
        activityComponent.inject(this)
        setupNavigationBar()
        setToolbarElevation()

        replaceFragment(DashboardFragment.newInstance(), false, R.id.container)

    }


    /**
     * Asks users to confirm whether he want to logout or not
     */
    private fun showLogoutDialog() {
        MaterialDialog.Builder().init(this)
                .setMessage(R.string.dialog_logout)
                .setPositiveButton(getString(R.string.logout),
                        DialogInterface.OnClickListener { dialog, which ->
                            preferencesHelper.clear()
                            val intent = Intent(this, LoginActivity::class.java)
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(intent)
                            finish()
                        })
                .setNegativeButton(getString(R.string.cancel),
                        DialogInterface.OnClickListener { dialog, which -> setNavigationViewSelectedItem(R.id.item_home) })
                .createMaterialDialog()
                .show()
    }


    private fun setNavigationViewSelectedItem(id: Int) {
        navigationView.setCheckedItem(id)
    }

    /**
     * This method is used to set up the navigation drawer for
     * self-service application
     */
    private fun setupNavigationBar() {

        navigationView.setNavigationItemSelectedListener(this)

        val actionBarDrawerToggle = object : ActionBarDrawerToggle(this,
                drawerLayout, getToolbar(), R.string.open_drawer, R.string.close_drawer) {

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                hideKeyboard(drawerView)
            }
        }

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        setupHeaderView(navigationView.getHeaderView(0))
        setUpBackStackListener()
    }

    fun hideKeyboard(view: View) {
        val inputManager = this
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager
                .RESULT_UNCHANGED_SHOWN)
    }


    /**
     * Used for initializing values for HeaderView of NavigationView
     *
     * @param headerView Header view of NavigationView
     */
    private fun setupHeaderView(headerView: View) {
        tvUsername = headerView.findViewById(R.id.tv_user_name)
        ivCircularUserProfilePicture = headerView
                .findViewById(R.id.iv_circular_user_image)
        ivTextDrawableUserProfilePicture = headerView.findViewById(R.id.iv_user_image)

        ivTextDrawableUserProfilePicture.setOnClickListener(this)
        ivCircularUserProfilePicture.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        // Click Header to view full profile of User
    }

    private fun setUpBackStackListener() {
        supportFragmentManager.addOnBackStackChangedListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.container)
            setToolbarElevation()
            if (fragment is DashboardFragment) {
                setNavigationViewSelectedItem(R.id.item_home)
            } else if(fragment is CustomerAccountFragment) {
                setNavigationViewSelectedItem(R.id.item_accounts)
            }
        }


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // select which item to open
        clearFragmentBackStack()
        setToolbarElevation()
        menuItem = item.itemId
        when (item.itemId) {
            R.id.item_home -> {
                hideToolbarElevation()
                replaceFragment(DashboardFragment.newInstance(), true, R.id.container)
            }
            R.id.item_accounts -> {
                replaceFragment(CustomerAccountFragment.newInstance(AccountType.LOAN), true,
                        R.id.container)
            }

            R.id.item_logout -> {
                showLogoutDialog()
            }
            R.id.item_product -> {
                replaceFragment(ProductFragment.Companion.newInstance(), true,
                        R.id.container)
            }
        }

        // close the drawer
        drawerLayout.closeDrawer(GravityCompat.START)
        setNavigationViewSelectedItem(R.id.item_home)
        return true
    }

    /**
     * Handling back press
     */
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
            return
        }

        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        if (fragment is DashboardFragment) {
            if (doubleBackToExitPressedOnce && stackCount() == 0) {
                this.finish()
                return
            }
            this.doubleBackToExitPressedOnce = true
            Toaster.show(findViewById(android.R.id.content), getString(R.string.exit_message))
            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        }

        if (stackCount() != 0) {
            super.onBackPressed()
        }
    }

}
