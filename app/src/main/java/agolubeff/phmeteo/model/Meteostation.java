package agolubeff.phmeteo.model;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Meteostation
{
    private String name;
    private int temperature;
    private long time;
    private int humidity;
    private int atmosphere_pressure;

    public Meteostation(String name, int temperature, long time, int humidity, int atmosphere_pressure)
    {
        this.name = name;
        this.temperature = temperature;
        this.time = time;
        this.humidity = humidity;
        this.atmosphere_pressure = atmosphere_pressure;
    }

    public String GetName()
    {
        return name;
    }

    public int GetTemperature()
    {
        return temperature;
    }

    public String GetTime()
    {
        return GetDateFromSeconds(time);
    }

    public int GetHumidity()
    {
        return humidity;
    }

    public int GetAtmospherePressure()
    {
        return atmosphere_pressure;
    }

    private String GetDateFromSeconds(long seconds)
    {
        long millis = seconds * 1000;
        Date date = new Date(millis);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }
}
