package sangu.com.calendertest;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.spans.EventDecorator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    MaterialCalendarView materialCalendarView;
    TextView week,month;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //Toast.makeText(MainActivity.this,msg.obj.toString()+msg.arg1+"月",Toast.LENGTH_SHORT).show();

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    private void initEvent() {
        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialCalendarView.state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).commit();
            }
        });
        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialCalendarView.state().edit().setCalendarDisplayMode(CalendarMode.MONTHS).commit();

            }
        });
    }

    /**
     * 将字符串改变成时间戳
     * */
    public static long str2long(String content,String spf_Value){
        long l=0;
        SimpleDateFormat spf=new SimpleDateFormat(spf_Value);
        Date date;
        try {
            date=spf.parse(content);
            l=date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return l;

    }
    private void initView() {
        week= (TextView) findViewById(R.id.tv_week);
        month= (TextView) findViewById(R.id.tv_month);
        materialCalendarView= (MaterialCalendarView) findViewById(R.id.calendarView);
        materialCalendarView.setSelectedDate(new Date());
        materialCalendarView.setDateSelected(new Date(str2long("2017-8-4","yyyy-MM-dd")),true);
        materialCalendarView.setDateSelected(new Date(str2long("2017-8-5","yyyy-MM-dd")),true);
        materialCalendarView.setDateSelected(new Date(str2long("2017-8-7","yyyy-MM-dd")),true);
        materialCalendarView.setDateSelected(new Date(str2long("2017-8-8","yyyy-MM-dd")),true);
        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
                Message message=Message.obtain();
                message.obj=date.toString();
                message.arg1=date.getMonth()+1;
                handler.sendMessage(message);
            }
        });
        materialCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                Log.e("DDZTAG", "onMonthChanged:" + date.getMonth());
            }
        });
        Collection<CalendarDay> dates=new ArrayList<>();
        dates.add(new CalendarDay(new Date(str2long("2017-8-4","yyyy-MM-dd"))));
        dates.add(new CalendarDay(new Date(str2long("2017-8-5","yyyy-MM-dd"))));
        dates.add(new CalendarDay(new Date(str2long("2017-8-7","yyyy-MM-dd"))));
        dates.add(new CalendarDay(new Date(str2long("2017-8-8","yyyy-MM-dd"))));
        dates.add(new CalendarDay(new Date(str2long("2017-8-20","yyyy-MM-dd"))));
        materialCalendarView.addDecorator(new EventDecorator(Color.RED,dates));



    }
}
