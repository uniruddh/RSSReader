package `in`.uniruddh.rssreader.utils

import android.content.Context
import android.widget.Toast

/**
 * @Author: Aniruddh Bhilvare
 * @Date:  30/September/2021
 * @Email: uniruddh@gmail.com
 */

fun Context.toastShort(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.toastLong(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()