package agencyvnn.team.game_wallpaper_hd.Api.Utils;


import agencyvnn.team.game_wallpaper_hd.Api.RetrofitClient;
import agencyvnn.team.game_wallpaper_hd.Api.Service.SOService;
import agencyvnn.team.game_wallpaper_hd.Contanst.AppContants;

/**
 * Created by hiepnt on 05/01/2018.
 */

public class ApiUtils {
    public static final String BASE_URL = AppContants.BaseUrl;

    public static SOService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}
