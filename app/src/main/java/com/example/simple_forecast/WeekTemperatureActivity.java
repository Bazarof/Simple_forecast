package com.example.simple_forecast;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.Random;

public class WeekTemperatureActivity extends AppCompatActivity {

    private final int today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

    private final int[] iconMorningList = {
            R.drawable.icon_sun,
            R.drawable.icon_cloudy,
            R.drawable.icon_rainy,
            R.drawable.icon_wind,
            R.drawable.icon_thunderstorm
    };

    Button botonReset, botonHoy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_week_temperature);

        createDayForecastList();

        botonReset = findViewById(R.id.botonReset);
        botonHoy = findViewById(R.id.botonHoy);

        botonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), Launcher.class){{
                    this.putExtra("text_reset","Reiniciando...");
                }});
                finish();
            }
        });

        botonHoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void createDayForecastList()
    {
        TableLayout dayTempList = findViewById(R.id.dayTempList);

        int lastDay = today + 10;
        for(int i = today; i < lastDay; i++)
        {
            dayTempList.addView(createMargin());
            dayTempList.addView(createDayRow(getDayName(i)));
            dayTempList.addView(createMargin());

            if(i != lastDay-1)
                dayTempList.addView(createDividerRow());
        }
    }

    private String getDayName(int day)
    {
        String name = "";
        int sevenDayFormat = day > 7 ? getSevenDayFormat(day) : day;

        switch(sevenDayFormat)
        {
            case Calendar.SUNDAY: name = "Dom";break;
            case Calendar.MONDAY: name = "Lun";break;
            case Calendar.TUESDAY: name = "Mar";break;
            case Calendar.WEDNESDAY: name = "Mie";break;
            case Calendar.THURSDAY: name = "Jue";break;
            case Calendar.FRIDAY: name = "Vie";break;
            case Calendar.SATURDAY: name = "Sab";break;
        }
        return name;
    }

    private int getSevenDayFormat(int day)
    {
        int dayNumber = day % 7;
        return dayNumber == 0 ? 7 : dayNumber;
    }

    private TableRow createDayRow(String dayName)
    {
        return new TableRow(getBaseContext()){{
                setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT) {{
                    setMargins(
                            0, //ScreenScale.getDp(getContext(), 20),
                            ScreenScale.getDp(getContext(), 10),
                            0, //ScreenScale.getDp(getContext(), 20),
                            ScreenScale.getDp(getContext(), 10)
                    );
                    setGravity(Gravity.CENTER_VERTICAL);
                }});

                addView(new TextView(getContext()) {{
                    setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
                    setText(dayName);
                    setTextColor(Color.parseColor("#ffffffff"));
                    setTypeface(null, Typeface.BOLD);
                }});

                addView(new View(getContext()){{

                    setLayoutParams(new LayoutParams(0, ScreenScale.getDp(getContext(), 30), 1));

                    addView(new ImageView(getContext()) {{
                        setLayoutParams(new LayoutParams(ScreenScale.getDp(getContext(), 30), ScreenScale.getDp(getContext(), 30)));
                        setBackgroundResource(getIcon());
                    }});
                }});

                addView(new TextView(getContext()){{
                    setLayoutParams(new LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                    setText(getMinTemp());
                    setTextColor(Color.parseColor("#99ffffff"));
                    setTypeface(null, Typeface.BOLD);
                }});

                addView(new TextView(getContext()){{
                    setLayoutParams(new LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                    setText(getMaxTemp());
                    setTextColor(Color.parseColor("#ffffffff"));
                    setTypeface(null, Typeface.BOLD);
                }});
        }};
    }

    private int getIcon()
    {
        return iconMorningList[new Random().nextInt(5)];
    }

    private String getMinTemp()
    {
        return String.valueOf(new Random().nextInt(27 - 24) +24) + "°";
    }

    private String getMaxTemp()
    {
        return String.valueOf(new Random().nextInt(34 - 28) + 28) + "°";
    }

    private TableRow createDividerRow()
    {
        return new TableRow(getBaseContext()){{
            setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            addView(new View(getContext()){{
                setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, ScreenScale.getDp(getContext(), 1)));
            }});

            setBackgroundColor(Color.parseColor("#99ffffff"));
        }};
    }

    private View createMargin()
    {
        return new View(getBaseContext()){{
            setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenScale.getDp(getContext(), 10)));
        }};
    }
}