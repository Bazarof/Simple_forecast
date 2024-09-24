package com.example.simple_forecast;

import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.RenderEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final int hourOfDay = ZonedDateTime.now(ZoneId.of("America/Mexico_City")).getHour() - 1; // is one hour ahead

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        createHourForecastList();

    }

    private void createHourForecastList() {
        String ampm = hourOfDay >= 12 ? "p.m." : "a.m.";
        int currentHour = hourOfDay % 12;
        int hourIterator = currentHour == 0 ? 12 : currentHour;

        LinearLayout hourTemperatureList = findViewById(R.id.hourTempList);

        int lastHour = hourIterator + 47;
        while(hourIterator <= lastHour)
        {
            hourTemperatureList.addView(
                    createHourTemperatureView(
                            getTextHour(
                                    hourIterator,
                                    currentHour,
                                    getAmPm(hourIterator, ampm)
                            ),
                            R.drawable.icon_sun,
                            hourIterator == lastHour
                    ));

            hourIterator++;
        }

    }



    private LinearLayout createHourTemperatureView(String textHour, int icon, boolean isLastHour)
    {
        return new LinearLayout(getApplicationContext()){{

            setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT){{

                setMargins(getDp(20), 0, isLastHour ? getDp(20) : 0, 0);

            }});

            setOrientation(LinearLayout.VERTICAL);

            setGravity(Gravity.CENTER_HORIZONTAL);

            addView(new TextView(getApplicationContext()){{
                setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT){{
                    setMargins( 0, 0, 0, getDp(10));
                }});
                setText(textHour);
                setTextColor(Color.parseColor("#ffffffff"));
            }});

            addView(new ImageView(getApplicationContext()){{
                setLayoutParams(new LayoutParams(getDp(30), getDp(30)){{
                    setMargins(0, 0, 0, getDp(10));
                }});
                setBackgroundResource(R.drawable.icon_sun);
            }});

            addView(new TextView(getApplicationContext()){{
                setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                setText("32°");
                setTextColor(Color.parseColor("#ffffffff"));
            }});

        }};
    }

    private String getAmPm(int hourIterator, String ampm)
    {
        if(hourIterator % 12 == 0)
        {
            ampm = ampm.equals("p.m.") ? "a.m." : "p.m.";
        }
        return ampm;
    }

    private String getTextHour(int i, int hour, String ampm)
    {
        return i == hour ? "Ahora" : String.valueOf(i <= 12 ? i : getTwelveHourFormat(i)) + " " + ampm;
    }

    private String getTwelveHourFormat(int number)
    {
        int currentHour = number % 12;
        return String.valueOf(currentHour == 0 ? 12 : currentHour);
    }

    private int getDp(int pixels)
    {
        float scale = getBaseContext().getResources().getDisplayMetrics().density;
        return (int)(pixels * scale + 0.5f);
    }

}