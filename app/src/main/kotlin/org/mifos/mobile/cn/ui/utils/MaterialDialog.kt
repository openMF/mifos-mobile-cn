package org.mifos.mobile.cn.ui.utils

import android.content.Context
import android.content.DialogInterface
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import android.view.View
import org.mifos.mobile.cn.R


/**
 * This Class is the Material Dialog Builder Class
 * Created by Rajan Maurya on 03/08/16.
 */
class MaterialDialog {

    class Builder {

        private var materialDialogBuilder: AlertDialog.Builder? = null

        //This is the Default Builder Initialization with Material Style
        fun init(context: Context): Builder {
            materialDialogBuilder = AlertDialog.Builder(context, R.style.MaterialAlertDialogStyle)
            return this
        }

        //This method set the custom Material Style
        fun init(context: Context, theme: Int): Builder {
            materialDialogBuilder = AlertDialog.Builder(context, theme)
            return this
        }

        //This method set the String Title
        fun setTitle(title: String): Builder {
            materialDialogBuilder!!.setTitle(title)
            return this
        }

        //This Method set the String Resources to Title
        fun setTitle(@StringRes title: Int): Builder {
            materialDialogBuilder!!.setTitle(title)
            return this
        }

        //This Method set the String Message
        fun setMessage(message: String): Builder {
            materialDialogBuilder!!.setMessage(message)
            return this
        }

        //This Method set the String Resources message
        fun setMessage(@StringRes message: Int): Builder {
            materialDialogBuilder!!.setMessage(message)
            return this
        }

        //This Method set String Test to the Positive Button and set the Onclick null
        fun setPositiveButton(positiveText: String): Builder {
            materialDialogBuilder!!.setPositiveButton(positiveText, null)
            return this
        }

        //This Method Set the String Resources Text To Positive Button
        fun setPositiveButton(@StringRes positiveText: Int): Builder {
            materialDialogBuilder!!.setPositiveButton(positiveText, null)
            return this
        }

        //This Method set the String Text to Positive Button and set the OnClick Event to it
        fun setPositiveButton(positiveText: String,
                              listener: DialogInterface.OnClickListener): Builder {
            materialDialogBuilder!!.setPositiveButton(positiveText, listener)
            return this
        }

        //This method set the String Resources text To Positive button and set the Onclick Event
        fun setPositiveButton(@StringRes positiveText: Int,
                              listener: DialogInterface.OnClickListener): Builder {
            materialDialogBuilder!!.setPositiveButton(positiveText, listener)
            return this
        }

        //This Method the String Text to Negative Button and Set the onclick event to null
        fun setNegativeButton(negativeText: String): Builder {
            materialDialogBuilder!!.setNegativeButton(negativeText, null)
            return this
        }

        //This Method set the String Resources Text to Negative button
        // and set the onclick event to null
        fun setNegativeButton(@StringRes negativeText: Int): Builder {
            materialDialogBuilder!!.setNegativeButton(negativeText, null)
            return this
        }

        //This Method set String Text to Negative Button and
        //Set the Onclick event
        fun setNegativeButton(negativeText: String,
                              listener: DialogInterface.OnClickListener): Builder {
            materialDialogBuilder!!.setNegativeButton(negativeText, listener)
            return this
        }

        //This method set String Resources Text to Negative Button and set Onclick Event
        fun setNegativeButton(@StringRes negativeText: Int,
                              listener: DialogInterface.OnClickListener): Builder {
            materialDialogBuilder!!.setNegativeButton(negativeText, listener)
            return this
        }

        //This Method the String Text to Neutral Button and Set the onclick event to null
        fun setNeutralButton(neutralText: String): Builder {
            materialDialogBuilder!!.setNeutralButton(neutralText, null)
            return this
        }

        fun addView(view: View): Builder {
            materialDialogBuilder?.setView(view)
            return this
        }

        //This Method set the String Resources Text to Neutral button
        // and set the onclick event to null
        fun setNeutralButton(@StringRes neutralText: Int): Builder {
            materialDialogBuilder!!.setNeutralButton(neutralText, null)
            return this
        }

        //This Method set String Text to Neutral Button and
        //Set the Onclick event
        fun setNeutralButton(neutralText: String,
                             listener: DialogInterface.OnClickListener): Builder {
            materialDialogBuilder!!.setNeutralButton(neutralText, listener)
            return this
        }

        //This method set String Resources Text to Neutral Button and set Onclick Event
        fun setNeutralButton(@StringRes neutralText: Int,
                             listener: DialogInterface.OnClickListener): Builder {
            materialDialogBuilder!!.setNeutralButton(neutralText, listener)
            return this
        }

        fun setCancelable(cancelable: Boolean?): Builder {
            materialDialogBuilder!!.setCancelable(cancelable!!)
            return this
        }

        fun setItems(items: Int, listener: DialogInterface.OnClickListener): Builder {
            materialDialogBuilder!!.setItems(items, listener)
            return this
        }

        fun setItems(items: Array<CharSequence>,
                     listener: DialogInterface.OnClickListener): Builder {
            materialDialogBuilder!!.setItems(items, listener)
            return this
        }

        //This Method Create the Final Material Dialog
        fun createMaterialDialog(): Builder {
            materialDialogBuilder!!.create()
            return this
        }

        //This Method Show the Dialog
        fun show(): Builder {
            materialDialogBuilder!!.show()
            return this
        }
    }
}
