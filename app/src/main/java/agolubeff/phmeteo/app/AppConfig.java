package agolubeff.phmeteo.app;

/**
 * Created by andre on 23.02.2018.
 */

public class AppConfig
{
    private static String HOST = "http://phmeteo.pas-wd.ru/android/";
    //private static String HOST = "http://192.168.1.129/PH_meteo/";
    //private static String HOST = "http://172.21.130.33/PH_meteo/";

    // Server user login url
    public static String URL_LOGIN = HOST + "login.php";

    // Server user register url
    public static String URL_REGISTER = HOST + "register.php";

    public static String URL_GET_METEOSTATION_LIST = HOST + "get_meteostation_list.php";

    public static String URL_REGISTER_STATION = HOST + "set_user_station_link.php";
}
