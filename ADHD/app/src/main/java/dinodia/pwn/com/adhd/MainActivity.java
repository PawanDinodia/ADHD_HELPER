package dinodia.pwn.com.adhd;

import java.util.Calendar;

import android.content.Context;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    int count=0;

    int last_sec=0;
    int last_min=0;
    int last_hour=0;

    int max_sec=0;

    protected PowerManager.WakeLock mWakeLock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "LOCK");
        this.mWakeLock.acquire();

        final TextView counter_tv = (TextView) findViewById(R.id.counterTv);
        Button addCount = (Button) findViewById(R.id.addBtn);

        last_hour = Calendar.getInstance().get(Calendar.HOUR);
        last_min = Calendar.getInstance().get(Calendar.MINUTE);
        last_sec = Calendar.getInstance().get(Calendar.SECOND);

        final TextView last_time = (TextView) findViewById(R.id.last_time);
        final TextView max_time = (TextView) findViewById(R.id.max_time);


        addCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int total_last_sec=last_hour*3600+last_min*60+last_sec;
                int total_new_sec=Calendar.getInstance().get(Calendar.HOUR)*3600+Calendar.getInstance().get(Calendar.MINUTE)*60+Calendar.getInstance().get(Calendar.SECOND)-total_last_sec;

                if(max_sec<total_new_sec){
                    max_sec=total_new_sec;
                    max_time.setText(("Best time:\n").concat(convertTime(max_sec)));
                }
                last_time.setText(("Last time:\n").concat(convertTime(total_new_sec)));

                last_hour = Calendar.getInstance().get(Calendar.HOUR);
                last_min = Calendar.getInstance().get(Calendar.MINUTE);
                last_sec = Calendar.getInstance().get(Calendar.SECOND);
                count++;
                counter_tv.setText(count+"");
            }
        });
    }
        private String convertTime(int sec){
            int hour=(sec-(sec%3600))/3600;
            int min=((sec-hour*3600)-(sec-hour*3600)%60)/60;
            int second=sec-hour*3600-min*60;
            return hour+":"+min+":"+second;
        }
}
