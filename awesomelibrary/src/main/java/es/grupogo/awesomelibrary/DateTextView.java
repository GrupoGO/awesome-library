package es.grupogo.awesomelibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import es.grupogo.awesomelibrary.utils.DateUtils;


/**
 * Created by Carlos Olmedo on 18/5/16.
 */
public class DateTextView extends TextView {

    private static final int MODE_NEW = 0;
    private static final int MODE_TASK = 1;
    private static final int MODE_CHAT = 2;

    int dateMode = MODE_NEW;

    public DateTextView(Context context) {
        super(context);
        init(context, null);
    }

    public DateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DateTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {


        TypedArray attrsArray = context.obtainStyledAttributes(attrs, R.styleable.dateTextView);
        dateMode = attrsArray.getInteger(R.styleable.dateTextView_dateMode, MODE_NEW);
        attrsArray.recycle();
    }

    public void setDate(Date date) {

        if(date==null){
            setText(" ");
            return;
        }

        SimpleDateFormat sdfUTC = new SimpleDateFormat();
        sdfUTC.setTimeZone(new SimpleTimeZone(SimpleTimeZone.UTC_TIME, "UTC"));

        SimpleDateFormat sdf = new SimpleDateFormat();

        Date utcDate = new Date();
        try {
            utcDate = sdf.parse(sdfUTC.format(utcDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = utcDate.getTime() - date.getTime();

        switch (dateMode) {
            case MODE_NEW:
                formatNewDate(diff);
                break;
            case MODE_TASK:
                formatTaskDate(date);
                break;
            case MODE_CHAT:
                formatChatDate(date);
                break;
        }

    }


    public void formatNewDate(long diff) {

        int daysElapsed = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        if (daysElapsed == 1) {
            setText(String.format(getContext().getString(R.string.day_ago), daysElapsed));
            return;
        } else if (daysElapsed > 1) {
            setText(String.format(getContext().getString(R.string.days_ago), daysElapsed));
            return;
        }

        int hoursElapsed = (int) TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
        if (hoursElapsed == 1) {
            setText(String.format(getContext().getString(R.string.hour_ago), hoursElapsed));
            return;
        } else if (hoursElapsed > 1) {
            setText(String.format(getContext().getString(R.string.hours_ago), hoursElapsed));
            return;
        }

        int minutesElapsed = (int) TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);
        if (minutesElapsed == 1) {
            setText(String.format(getContext().getString(R.string.minute_ago), minutesElapsed));
        } else if (minutesElapsed > 1) {
            setText(String.format(getContext().getString(R.string.minutes_ago), minutesElapsed));
        } else {
            setText(R.string.less_than_minute);
        }

    }

    public void formatTaskDate(Date date) {

        SimpleDateFormat sdfUTC = new SimpleDateFormat();

        SimpleDateFormat sdf = new SimpleDateFormat();
        sdfUTC.setTimeZone(new SimpleTimeZone(SimpleTimeZone.UTC_TIME, "UTC"));

        Date utcDate = new Date();
        try {
            utcDate = sdf.parse(sdfUTC.format(utcDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //caso de task sin fecha
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if(date.before(dateFormat.parse("1000-00-00"))){
                setText(R.string.no_date);
                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = date.getTime() - utcDate.getTime();
        int daysElapsed = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        switch (daysElapsed) {
            case 0:
                setText(R.string.today);
                setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextLight));
                break;
            case 1:
                setText(R.string.tomorrow);
                setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextLight));
                break;
            case -1:
                setText(R.string.yesterday);
                setTextColor(ContextCompat.getColor(getContext(), R.color.md_red_700));
                break;
            default:
                if (daysElapsed>0) {
                    setText(DateUtils.formatDate(new Date(date.getTime()), "yyyy-MM-dd"));
                    setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextLight));
                } else {
                    setText(String.format(getContext().getString(R.string.days_later), Math.abs(daysElapsed)));
                    setTextColor(ContextCompat.getColor(getContext(), R.color.md_red_500));
                }
        }

    }

     public void formatChatDate(Date date){

         SimpleDateFormat sdfUTC = new SimpleDateFormat();
         sdfUTC.setTimeZone(new SimpleTimeZone(SimpleTimeZone.UTC_TIME, "UTC"));

         SimpleDateFormat sdf = new SimpleDateFormat();


         Date utcDate = new Date();
         try {
             utcDate = sdf.parse(sdfUTC.format(utcDate));
         } catch (ParseException e) {
             e.printStackTrace();
         }

        long diff = date.getTime() - utcDate.getTime();
        int daysElapsed = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        if(daysElapsed == 0){
            long currentTime = System.currentTimeMillis();
            int edtOffset = TimeZone.getTimeZone("UTC").getOffset(currentTime);
            int gmtOffset = TimeZone.getDefault().getOffset(currentTime);
            int hourDifference = (gmtOffset - edtOffset) / (1000 * 60 * 60);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.HOUR_OF_DAY, hourDifference);
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            setText(format.format(cal.getTime()));

        } else if (daysElapsed == -1) {
            setText(getContext().getString(R.string.yesterday));
        } else {
            long currentTime = System.currentTimeMillis();
            int edtOffset = TimeZone.getTimeZone("UTC").getOffset(currentTime);
            int gmtOffset = TimeZone.getDefault().getOffset(currentTime);
            int hourDifference = (gmtOffset - edtOffset) / (1000 * 60 * 60);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.HOUR_OF_DAY, hourDifference);
            SimpleDateFormat format = new SimpleDateFormat("HH:mm dd-MM-yyyy");
            setText(format.format(cal.getTime()));
        }
    }
}
