package uz.karsoft.baxt.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.formatPhoneNumber() {
    this.addTextChangedListener(object : TextWatcher {
        private var isFormatting: Boolean = false
        private var cursorPosition: Int = 0
        private var lastTextLength: Int = 0

        override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
            if (!isFormatting) {
                cursorPosition = this@formatPhoneNumber.selectionStart
                lastTextLength = charSequence?.length ?: 0
            }
        }

        override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(editable: Editable?) {
            if (isFormatting || editable == null) return

            isFormatting = true

            // Remove all non-digit characters from the input
            val cleanText = editable.toString().replace(Regex("[^\\d]"), "")

            // Format the text based on its length
            val formattedText = formatAsPhoneNumber(cleanText)

            // Set the formatted text and manage cursor position
            this@formatPhoneNumber.setText(formattedText)
            this@formatPhoneNumber.setSelection(calculateCursorPosition(formattedText))

            isFormatting = false
        }

        private fun formatAsPhoneNumber(text: String): String {
            val stringBuilder = StringBuilder("+998 ")

            if (text.length > 3) {
                stringBuilder.append(text.substring(3, Math.min(text.length, 5)))
            }
            if (text.length > 5) {
                stringBuilder.append(" ")
                stringBuilder.append(text.substring(5, Math.min(text.length, 8)))
            }
            if (text.length > 8) {
                stringBuilder.append(" ")
                stringBuilder.append(text.substring(8))
            }

            return stringBuilder.toString()
        }

        private fun calculateCursorPosition(formattedText: String): Int {
            val currentTextLength = formattedText.length
            val selectionIndex = if (currentTextLength > lastTextLength) cursorPosition + (currentTextLength - lastTextLength) else cursorPosition
            return if (selectionIndex <= currentTextLength) selectionIndex else currentTextLength
        }
    })
}


fun snackBarWithAction(msg: String, v: View, text: String, loadData: () -> Unit) {
    Snackbar.make(v, msg, Snackbar.LENGTH_LONG).setAction(
        text
    ) {
        loadData()
    }.show()
}

fun showSnackBar(msg: String, v: View) {
    Snackbar.make(v, msg, Snackbar.LENGTH_LONG).show()
}


fun Fragment.openOnBrowser(url: String) {
    val urlIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(url)
    )
    startActivity(urlIntent)
}

fun View.visibility(visibility: Boolean): View {
    if (visibility) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
    return this
}

fun View.enabled(isEnabled: Boolean): View {
    this.isEnabled = isEnabled
    return this
}

fun Fragment.showMessage(msg: String?) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}

fun Activity.showMessage(msg: String?) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun Fragment.showTopMessage(msg: String?) {
    val toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.TOP, 0, 0)
    toast.show()
}

fun ViewGroup.inflate(@LayoutRes id: Int): View =
    LayoutInflater.from(context).inflate(id, this, false)

fun RecyclerView.addVertDivider(context: Context?) {
    this.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
}

fun RecyclerView.addHorizDivider(context: Context?) {
    this.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
}

fun View.onClick(onClick: (View) -> Unit) {
    this.setOnClickListener(onClick)
}

// Setting Html string to TextView and making links clickable
fun TextView.setTextViewHtml(html: String, onLinkClick: (link: String) -> Unit) {
    val sequence: CharSequence = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
    val strBuilder = SpannableStringBuilder(sequence)
    val urls = strBuilder.getSpans(0, sequence.length, URLSpan::class.java)
    for (span in urls) {
        makeLinkClickable(strBuilder, span, onLinkClick)
    }
    this.text = strBuilder
    this.movementMethod = LinkMovementMethod.getInstance()
}

private fun makeLinkClickable(
    strBuilder: SpannableStringBuilder,
    span: URLSpan,
    onLinkClick: (link: String) -> Unit
) {
    val start = strBuilder.getSpanStart(span)
    val end = strBuilder.getSpanEnd(span)
    val flags = strBuilder.getSpanFlags(span)
    val clickable = object : ClickableSpan() {
        override fun onClick(widget: View) {
            val fullText = (widget as TextView).text.toString()
            val link = fullText.substring(start, end)
            onLinkClick.invoke(link)
        }
    }
    strBuilder.setSpan(clickable, start, end, flags)
    strBuilder.removeSpan(span)
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Float.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Int.dpToFloat: Float
    get() = (this / Resources.getSystem().displayMetrics.density)

val String.ifContainsLatin: Boolean
    get() {
        this.forEach {
            if (it.toInt() in 65..90 || it.toInt() in 97..122) {
                return true
            }
        }
        return false
    }

val String.textFormat: String
    get() {
        var text = this.reversed()
        text = text.subSequence(0, text.length)
            .chunked(3) // group every 3 chars
            .joinToString(" ")
        return text.reversed()
    }

val String.convertDate: String
    get() {
        var y = ""
        var m = ""
        var d = ""

        this.forEach { c ->
            if (c.isDigit()) {
                when {
                    y.length < 4 -> y += c
                    m.length < 2 -> m += c
                    d.length < 2 -> d += c
                }
            }
        }
        return "$d.$m.$y"
    }

val String.toPhoneNumber: String
    get() {
        val arr = this.toCharArray()
        var phone = "+998 ("
        arr.forEachIndexed { index, c ->
            phone += c
            if (index == 1) {
                phone += ") "
            }
            if (index == 4 || index == 6) {
                phone += " "
            }
        }
        return phone
    }

fun EditText.showSoftKeyboard() {
    (this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        .showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun EditText.hideSoftKeyboard() {
    (this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

fun <T : ViewBinding> T.scope(block: T.() -> Unit) {
    block(this)
}



