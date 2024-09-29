package com.example.simple_forecast;

import android.content.Intent;
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
import android.widget.Button;
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

    private final int hourOfDay = ZonedDateTime.now(ZoneId.of("America/Mexico_City")).getHour() - 1;// is one hour ahead
    Random numberGenerator = new Random();

    Button boton1;

    //6 am to 6 pm
    private final int[] iconMorningList = {
            R.drawable.icon_sun,
            R.drawable.icon_cloudy,
            R.drawable.icon_rainy,
            R.drawable.icon_wind,
            R.drawable.icon_thunderstorm
    };

    //6 pm to 6 am
    private final int[] iconNightList = {
            R.drawable.icon_cloudy_moon,
            R.drawable.icon_moon,
            R.drawable.icon_thunderstorm,
            R.drawable.icon_wind
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        boton1 = findViewById(R.id.boton1);
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(view.getContext(), WeekTemperatureActivity.class)
                );
            }
        });

        createHourForecastList();
    }

    private void createHourForecastList() {
        String ampm = hourOfDay >= 12  ? "p.m." : "a.m.";
        int hourTwelveFormat = getTwelveHourFormat(hourOfDay);
        int currentHour = hourTwelveFormat == 0 ? 12 : hourTwelveFormat;

        int hourIterator = currentHour;

        LinearLayout hourTemperatureList = findViewById(R.id.hourTempList);

        int lastHour = hourIterator + 47;
        while(hourIterator <= lastHour)
        {
            if(hourIterator % 12 == 0)
            {
                ampm = ampm.equals("p.m.") ? "a.m." : "p.m.";
            }

            hourTemperatureList.addView(
                    createHourTemperatureView(
                            getTextHour(
                                    hourIterator,
                                    currentHour,
                                    ampm
                            ),
                            getIcon(getTwelveHourFormat(hourIterator), ampm),
                            hourIterator == lastHour
                    ));

            hourIterator++;
        }
    }

    private int getIcon(int hourTwelveFormat, String ampm)
    {
        int hour = hourTwelveFormat == 0 ? 12 : hourTwelveFormat;

        boolean isMorning = hour >= 6 && hour < 12 && ampm.equals("a.m.");
        boolean isNoon = hour == 12 && ampm.equals("p.m.");
        boolean isAfternoon = hour <= 6 && ampm.equals("p.m.");

        return isMorning || isNoon || isAfternoon ?
                iconMorningList[numberGenerator.nextInt(5)]
                :
                iconNightList[numberGenerator.nextInt(4)];
    }

    private LinearLayout createHourTemperatureView(String textHour, int icon, boolean isLastHour)
    {
        return new LinearLayout(getApplicationContext()){{

            setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT){{

                setMargins(ScreenScale.getDp(getContext(), 20), 0, isLastHour ? ScreenScale.getDp(getContext(), 20) : 0, 0);

            }});

            setOrientation(LinearLayout.VERTICAL);

            setGravity(Gravity.CENTER_HORIZONTAL);

            addView(new TextView(getApplicationContext()){{
                setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT){{
                    setMargins( 0, 0, 0, ScreenScale.getDp(getContext(), 10));
                }});
                setText(textHour);
                setTextColor(Color.parseColor("#ffffffff"));
            }});

            addView(new ImageView(getApplicationContext()){{
                setLayoutParams(new LayoutParams(ScreenScale.getDp(getContext(), 30), ScreenScale.getDp(getContext(), 30)){{
                    setMargins(0, 0, 0, ScreenScale.getDp(getContext(), 10));
                }});
                setBackgroundResource(icon);
            }});

            addView(new TextView(getApplicationContext()){{
                setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                setText("32°");
                setTextColor(Color.parseColor("#ffffffff"));
            }});

        }};
    }

    private String getTextHour(int i, int hour, String ampm)
    {
        return i == hour ? "Ahora" : String.valueOf(i <= 12 ? i : getTwelveHourFormat(i)) + " " + ampm;
    }

    private int getTwelveHourFormat(int number)
    {
        int twelveFormat = number % 12;
        return twelveFormat == 0 ? 12 : twelveFormat;
    }
}