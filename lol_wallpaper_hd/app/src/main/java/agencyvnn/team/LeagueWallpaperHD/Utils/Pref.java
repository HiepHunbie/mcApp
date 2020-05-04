package agencyvnn.team.LeagueWallpaperHD.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;

import java.util.Locale;
import java.util.Map;

public class Pref {
	public static final String BOOLEAN_IS_ME = "BOOLEAN_IS_ME";
	public static final String NUMBER_IMAGE_CHANGE = "NUMBER_IMAGE_CHANGE";
	private static final String PREF_KEY = "CLOTIFY_PREF_KEY";
	public static final String PREF_KEY_TOKEN  = "token";
	public static final String PREF_KEY_JOB_ID  = "PREF_KEY_JOB_ID";
	public static final String PREF_KEY_VIEW_NEWS_BEFORE  = "PREF_KEY_VIEW_NEWS_BEFORE";
	public static final String PREF_KEY_XEM_TRUOC  = "PREF_KEY_XEM_TRUOC";
	public static final String PREF_KEY_USER_SOKHOP  = "PREF_KEY_USER_SOKHOP";
	public static final String PREF_KEY_STATUS_ID  = "PREF_KEY_STATUS_ID";
	public static final String PREF_KEY_IS_LOGIN_FB  = "PREF_KEY_IS_LOGIN_FB";
	public static final String PREF_KEY_USER_ID  = "PREF_KEY_USER_ID";
	public static final String PREF_KEY_CLOTH_CONFIG  = "PREF_KEY_CLOTH_CONFIG";
	public static final String PREF_KEY_SEARCH_HEIGHT = "PREF_KEY_SEARCH_HEIGHT";
	public static final String PREF_KEY_EMAIL_LOGIN = "PREF_KEY_EMAIL_LOGIN";
	public static final String PREF_KEY_USER_GENDER = "PREF_KEY_USER_GENDER";
	public static final String PREF_KEY_DAY_PREFIX = "PREF_KEY_DAY_PREFIX_";
	public static final String PREF_KEY_IS_SENT_GCM_TOKEN = "PREF_KEY_IS_SENT_GCM_TOKEN";
	public static final String PREF_KEY_USER_AVATAR = "PREF_KEY_USER_AVATAR";
	public static final String PREF_KEY_USERNAME = "PREF_KEY_USERNAME";
	public static final String PREF_KEY_AVATAR= "PREF_KEY_AVATAR";
	public static final String PREF_KEY_COVER= "PREF_KEY_COVER";
	public static final String PREF_KEY_LOGO= "PREF_KEY_LOGO";
	public static final String MY_PREFERENCES = "MY_PREFERENCES";
	public static final String TYPE_USER = "typeUser";
	public static final String USER_ID = "user_id";
	public static final String USER_ID_REAL = "user_id_real";
	public static final String EXPECT_JOB  = "EXPECT_JOB";
	public static final String album_id= "album_id";
	public static final String album_name= "album_name";
	public static final String new_album_name= "new_album_name";
	public static final String upb_id= "upb_id";
	public static final String upb_gender= "upb_gender";
	public static final String upb_dob= "upb_dob";
	public static final String upb_middle_name= "upb_middle_name";
	public static final String upb_first_name= "upb_first_name";
	public static final String upb_last_name= "upb_last_name";
	public static final String upb_full_name= "upb_full_name";
	public static final String upb_status= "upb_status";
	public static final String upb_fate= "upb_fate";
	public static final String upb_appearance= "upb_appearance";
	public static final String upb_phone= "upb_phone";
	public static final String upb_email= "upb_email";
	public static final String upb_address= "upb_address";
	public static final String upb_address_temp= "upb_address_temp";
	public static final String upb_social_networks= "upb_social_networks";
	public static final String upb_height= "upb_height";
	public static final String upb_weight= "upb_weight";
	public static final String upb_measurements= "upb_measurements";
	public static final String upb_hobbies= "upb_hobbies";
	public static final String upb_skills= "upb_skills";
	public static final String upb_speaks= "upb_speaks";
	public static final String upb_soft_skills= "upb_soft_skills";
	public static final String upb_speaks_url= "upb_speaks_url";
	public static final String upb_sings= "upb_sings";
	public static final String upb_sings_url= "upb_sings_url";
	public static final String upb_special_skills= "upb_special_skills";
	public static final String upb_website= "upb_website";
	public static final String upb_marriage_status= "upb_marriage_status";
	public static final String upb_description= "upb_description";
	public static final String upb_image_url= "upb_image_url";
	public static final String upb_video_url= "upb_video_url";
	public static final String upb_created_at= "upb_created_at";
	public static final String upb_updated_at= "upb_updated_at";


	public static final String com_avatar_url= "com_avatar_url";
	public static final String com_cover_url= "com_cover_url";
	public static final String com_id= "com_id";
	public static final String com_user_id= "com_user_id";
	public static final String com_name= "com_name";
	public static final String com_categories= "com_categories";
	public static final String com_address= "com_address";
	public static final String com_size= "com_size";
	public static final String com_employee_count= "com_employee_count";
	public static final String com_website= "com_website";
	public static final String com_phone= "com_phone";
	public static final String com_time_working= "com_time_working";
	public static final String com_type= "com_type";
	public static final String com_email= "com_email";
	public static final String com_social_networks= "com_social_networks";
	public static final String com_introduce= "com_introduce";
	public static final String com_status= "com_status";
	public static final String com_country= "com_country";
	public static final String com_logo_url= "com_logo_url";
	public static final String com_img_url= "com_img_url";
	public static final String com_representation_name= "com_representation_name";
	public static final String com_representation_email= "com_representation_email";
	public static final String com_representation_phone= "com_representation_phone";
	public static final String com_representation_img= "com_representation_img";
	public static final String com_clothes= "com_clothes";
	public static final String com_tax_code= "com_tax_code";
	public static final String com_allowance= "com_allowance";
	public static final String com_rules= "com_rules";
	public static final String com_register_img= "com_register_img";
	public static final String com_contact_name= "com_contact_name";
	public static final String com_contact_phone= "com_contact_phone";
	public static final String com_contact_email= "com_contact_email";
	public static final String com_contact_address= "com_contact_address";
	public static final String com_contact_position= "com_contact_position";
	public static final String com_created_at= "com_created_at";
	public static final String com_updated_at= "com_updated_at";
	public static final String JOB_TAB = "JOB_TAB";
	public static final String TYPE_SEARCH = "TYPE_SEARCH";
	public static final String TYPE_SEARCH_TITLE = "TYPE_SEARCH_TITLE";
	public static final String TYPE_SEARCH_POS = "TYPE_SEARCH_POS";
	public static final String TYPE_SEARCH_TIME_TYPE = "TYPE_SEARCH_TIME_TYPE";
	public static final String TYPE_SEARCH_TIME_TYPE_ID = "TYPE_SEARCH_TIME_TYPE_ID";
	public static final String TYPE_SEARCH_LOCATION = "TYPE_SEARCH_LOCATION";
	public static final String TYPE_SEARCH_SALARY = "TYPE_SEARCH_SALARY";
	public static final String TYPE_SEARCH_POS_ID = "TYPE_SEARCH_POS_ID";
	public static final String TYPE_SEARCH_LOCATION_ID = "TYPE_SEARCH_SALARY_ID";
	public static final String TYPE_SEARCH_EXP = "TYPE_SEARCH_EXP";
	public static final String TYPE_SEARCH_SKILL = "TYPE_SEARCH_SKILL";
	public static final String TYPE_QUANLYTINTUYENDUNG_TAB = "TYPE_QUANLYTINTUYENDUNG_TAB";

	private SharedPreferences mShared;
	public Editor mEditor;
	public static Pref mPrefs;

	public Pref(Context context) {
		mShared = context.getSharedPreferences(MY_PREFERENCES, 0);
		mEditor = mShared.edit();
	}

	public static Pref getInstance(Context context) {
		if (mPrefs == null)
			mPrefs = new Pref(context.getApplicationContext());
		return mPrefs;
	}

	public static void dispose() {
		mPrefs = null;
	}

	public boolean getBoolean(String key, boolean default_value) {
		return mShared.getBoolean(key, default_value);
	}

	public float getFloat(String key, float default_value) {
		return mShared.getFloat(key, default_value);
	}

	public int getInt(String key, int default_value) {
		return mShared.getInt(key, default_value);
	}

	public long getLong(String key, long default_value) {
		return mShared.getLong(key, default_value);
	}

	public String getString(String key, String default_value) {
		return mShared.getString(key, default_value);
	}

	public void putFloat(String key, float value) {
		mEditor.putFloat(key, value);
		commitOrApplyPreferences(mEditor);
	}

	public void putBoolean(String key, boolean value) {
		mEditor.putBoolean(key, value);
		commitOrApplyPreferences(mEditor);
	}


	public void putInt(String key, int value) {
		mEditor.putInt(key, value);
		commitOrApplyPreferences(mEditor);
		if (key.equals(PREF_KEY_USER_ID)) {
			CommonData.getInstance().user_id = value;
		}
	}

	public void putLong(String key, long value) {
		mEditor.putLong(key, value);
		commitOrApplyPreferences(mEditor);
	}

	public void putString(String key, String value) {
		mEditor.putString(key, value);
		commitOrApplyPreferences(mEditor);
		if (key.equals(PREF_KEY_TOKEN)) {
			CommonData.getInstance().access_token = value;
		}
	}

	private void commitOrApplyPreferences(Editor preferencesEditor) {
		try {
			if (Build.VERSION.SDK_INT >= 9)
				preferencesEditor.apply();
			else
				preferencesEditor.commit();
		}
		catch (Throwable t) {
			if (t instanceof OutOfMemoryError) {
				System.gc();
				if (Build.VERSION.SDK_INT >= 9)
					preferencesEditor.commit();
			}
		}
	}

	public void clearAllDatePreferenceBefore(boolean isAll) {
		String today = DateUtils.getTodayText();
		String keyToday = Pref.PREF_KEY_DAY_PREFIX + today;
		Map<String, ?> keys = mShared.getAll();
		for(Map.Entry<String, ?> entry : keys.entrySet()){
			String key = entry.getKey();
			if (key.startsWith(PREF_KEY_DAY_PREFIX) && (isAll || !key.equalsIgnoreCase(keyToday))) {
				mEditor.remove(key);
			}
		}
		commitOrApplyPreferences(mEditor);
	}

	public void logout() {
		putBoolean(Pref.PREF_KEY_IS_SENT_GCM_TOKEN, false);
		clearAllDatePreferenceBefore(true);
		putString(Pref.PREF_KEY_TOKEN, "");
		putBoolean(Pref.PREF_KEY_IS_SENT_GCM_TOKEN, false);
		clearAllDatePreferenceBefore(true);
		putString(Pref.PREF_KEY_USER_AVATAR, null);
		putString(Pref.PREF_KEY_USERNAME, null);
		putString(Pref.PREF_KEY_USER_GENDER, null);
		putInt(Pref.PREF_KEY_USER_ID, 0);
	}

	public String getClothConfig() {
		return getString(PREF_KEY_CLOTH_CONFIG + "_" + Locale.getDefault().getLanguage(), null);
	}

	public void setClothConfig(String config) {
		putString(PREF_KEY_CLOTH_CONFIG + "_" + Locale.getDefault().getLanguage(), config);
	}
}
