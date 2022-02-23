package com.jnu.youownme
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.core.content.res.ComplexColorCompat.inflate
import androidx.core.graphics.drawable.DrawableCompat.inflate
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import android.content.Context

class CalendarDate(context: Context): CalendarView.OnCalendarSelectListener, View.OnClickListener,
AdapterView.OnItemClickListener{

    private var calendarView: CalendarView? = null
    private var dayText: TextView? = null
    private var yearText: TextView? = null
    private var lunarText: TextView? = null
    private var positiveButton: Button? = null
    private var negativeButton: Button? = null
    private var yearLayout: LinearLayout? = null
    private var yearList: ListView? = null
    private var onDateSetListener: CalendarDate.OnDateSetListener? = null

    interface OnDateSetListener {
        fun onDateSet(year: Int, month: Int, day: Int)
    }
    fun setOnDateSetListener(listener: CalendarDate.OnDateSetListener): CalendarDate {
        onDateSetListener = listener
        return this
    }
    fun getOnDateSetListener(): CalendarDate.OnDateSetListener? {
        return onDateSetListener
    }


    private var calendar: Calendar? = null

    init {
        val view =  LayoutInflater.from(context).inflate(R.layout.fragment_calendar, null)

        calendarView = view.findViewById(R.id.calendar_view)
        dayText = view.findViewById(R.id.day_text)
        yearText = view.findViewById(R.id.year_text)
//        lunarText = view.findViewById(R.id.lunar_text)
        //positiveButton = view.findViewById(R.id.positive_button)
        //negativeButton = view.findViewById(R.id.negative_button)
        yearLayout = view.findViewById(R.id.year_layout)
        yearList = view.findViewById(R.id.year_list)

        calendarView?.setOnCalendarSelectListener(this)
        dayText?.setOnClickListener(this)
        positiveButton?.setOnClickListener(this)
        negativeButton?.setOnClickListener(this)
        yearLayout?.setOnClickListener(this)

        val list: MutableList<String> = mutableListOf()
        for (i in 1970 until 2100) {
            list.add(i.toString())
        }
        val adapter = ArrayAdapter(context, R.layout.item_year_list, list)
        yearList?.adapter = adapter
        yearList?.onItemClickListener = this

        scrollToCurrent()
    }

    private fun freshDate(calendar: Calendar?) {
        val year: Int = calendar?.year ?: 2021
        val month: Int = calendar?.month ?: 1
        val day: Int = calendar?.day ?: 8

        val string = "${month}月${day}日"
        this.dayText?.text = string
        yearText?.text = year.toString()
    }

    override fun onCalendarOutOfRange(calendar: Calendar?) {
        TODO("Not yet implemented")
    }

    override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {
        this.calendar = calendar
        freshDate(this.calendar)
    }

    override fun onClick(v: View?) {
        when (v) {
            dayText -> {
                scrollToCurrent()
            }
            yearLayout -> {
                yearList?.setSelection((calendar?.year ?: 1970) - 1970)
                yearList?.setItemChecked((calendar?.year ?: 1970) - 1970, true)
                yearList?.visibility = View.VISIBLE
                calendarView?.visibility = View.GONE
            }
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        calendar?.year = position + 1970
        calendarView?.scrollToCalendar(calendar?.year ?: 0,
                calendar?.month ?: 0, calendar?.day ?: 0)
        yearList?.visibility = View.GONE
        calendarView?.visibility = View.VISIBLE
        yearText?.text = (position + 1970).toString()
    }

    private fun scrollToCurrent() {
        val curCalendar: java.util.Calendar = java.util.Calendar.getInstance()
        calendar = Calendar()
        calendar?.year = curCalendar.get(java.util.Calendar.YEAR)
        calendar?.month = curCalendar.get(java.util.Calendar.MONTH) + 1
        calendar?.day = curCalendar.get(java.util.Calendar.DAY_OF_MONTH)
        calendarView?.scrollToCalendar(calendar?.year ?: 0,
                calendar?.month ?: 0, calendar?.day ?: 0)
    }
    fun create() : CalendarDate? {
        val Context = null
        return Context?.let { CalendarDate(context = it) };
    }

}
