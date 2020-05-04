package agencyvnn.team.LeagueWallpaperHD.Api.Utils;


import agencyvnn.team.LeagueWallpaperHD.Api.RetrofitClient;
import agencyvnn.team.LeagueWallpaperHD.Api.Service.SOService;
import agencyvnn.team.LeagueWallpaperHD.Contanst.AppContants;

/**
 * Created by hiepnt on 05/01/2018.
 */

public class ApiUtils {
    public static final String BASE_URL = AppContants.BaseUrl;

    public static SOService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}
