package agencyvnn.team.beautiful_girlshd.Api.Utils;


import agencyvnn.team.beautiful_girlshd.Api.RetrofitClient;
import agencyvnn.team.beautiful_girlshd.Api.Service.SOService;
import agencyvnn.team.beautiful_girlshd.Contants.AppContants;

/**
 * Created by hiepnt on 05/01/2018.
 */

public class ApiUtils {
    public static final String BASE_URL = AppContants.BaseUrl;

    public static SOService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}
