package org.mifos.mobile.cn.ui.mifos.beneficiaries

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_beneficiaries.*
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.models.beneficiaries.Beneficiary
import org.mifos.mobile.cn.ui.adapter.BeneficiariesAdapter

class BeneficiariesActivity : AppCompatActivity() {

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beneficiaries)

        val name: String = getString(R.string.name)
        val description: String = getString(R.string.description)
        val price: String = getString(R.string.dummy_price)

        beneficiariesRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val items = ArrayList<Beneficiary>()
        items.add(Beneficiary(name, description, price))
        items.add(Beneficiary(name, description, price))
        items.add(Beneficiary(name, description, price))
        items.add(Beneficiary(name, description, price))
        items.add(Beneficiary(name, description, price))
        items.add(Beneficiary(name, description, price))
        items.add(Beneficiary(name, description, price))
        items.add(Beneficiary(name, description, price))
        items.add(Beneficiary(name, description, price))
        items.add(Beneficiary(name, description, price))

        val adapter = BeneficiariesAdapter(items)
        beneficiariesRecyclerView.adapter = adapter

        btnListBeneficiary.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.white))
        btnAddBeneficiary.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.violet))
        btnAddBeneficiary.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.white))
        btnListBeneficiary.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.violet))

        btnAddBeneficiary.setOnClickListener {
            mBtnAddFocused.visibility = LinearLayout.VISIBLE
            mBtnListFocused.visibility = LinearLayout.INVISIBLE
            btnListBeneficiary.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.violet))
            btnAddBeneficiary.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.white))
            btnListBeneficiary.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.white))
            btnAddBeneficiary.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.violet))
        }

        btnListBeneficiary.setOnClickListener {
            mBtnAddFocused.visibility = LinearLayout.INVISIBLE
            mBtnListFocused.visibility = LinearLayout.VISIBLE
            btnAddBeneficiary.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.violet))
            btnListBeneficiary.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.white))
            btnListBeneficiary.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.violet))
            btnAddBeneficiary.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.white))
        }
    }
}