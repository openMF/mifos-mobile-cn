package org.mifos.mobile.cn.ui.mifos

import android.os.Bundle
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.enums.AccountType
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.utils.ConstantKeys
import android.view.MenuItem as MenuItem1


class Main : MifosBaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private var bottomNavigationView: BottomNavigationView? = null
    private var menuItem = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        setupNavigationBar()
        bottomNavigationView?.run { setSelectedItemId(R.id.action_home)
        }


    }

    override fun onNavigationItemSelected(item: MenuItem1): Boolean {
        clearFragmentBackStack()
        setToolbarElevation()
        menuItem = item.itemId
navigateFragment(item.itemId,false)
     return true
    }
    private fun setupNavigationBar() {

        bottomNavigationView?.setOnNavigationItemSelectedListener { item ->
            navigateFragment(item.itemId, false)
            true
        }

    }
    override fun onBackPressed() {
        val fragment: Fragment? = supportFragmentManager
                .findFragmentById(R.id.bottom_navigation_fragment_container)
        if (fragment != null && fragment !is Home && fragment.isVisible()) {
            navigateFragment(R.id.action_home, true)
            return
        }
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
            //additional code
        } else {
            supportFragmentManager.popBackStack()
        }
    }
    private fun navigateFragment(id: Int, shouldSelect: Boolean) {
        if (shouldSelect) {
            bottomNavigationView?.setSelectedItemId(id)
        } else {
            when (id) {
                R.id.action_home -> replaceFragment(Home.newInstance(), false,
                        R.id.bottom_navigation_fragment_container)
                R.id.action_acounts -> replaceFragment(Account.newInstance(AccountType.DEPOSIT), false,
                        R.id.bottom_navigation_fragment_container)
                R.id.action_transfer -> replaceFragment(Transfer.newInstance(), false,
                        R.id.bottom_navigation_fragment_container)
                R.id.action_profile -> replaceFragment(Profile.newInstance(), false,
                        R.id.bottom_navigation_fragment_container)
            }
        }
    }
}
