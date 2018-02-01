package com.imaginamos.usuariofinal.taxisya.comm;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.imaginamos.usuariofinal.taxisya.activities.HomeActivity;
import com.imaginamos.usuariofinal.taxisya.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MiddleConnect {

	public static void logout(Context context , String user, String password, AsyncHttpResponseHandler responseHandler) {

        RequestParams  params  = new RequestParams();
	    params.put("login", user);
	    params.put("pwd", password);
	    Log.v("USER_SERVICE", "        logout: ");
	    Connect.post(context.getResources().getString(R.string.logout), params, responseHandler);
	}

	public static void getServices(Context context , String user_id, String uuid,String month,AsyncHttpResponseHandler responseHandler) {

        RequestParams params = new RequestParams();
		params.put("user_id", user_id);
		params.put("uuid",uuid);
		params.put("month",month);
	    Log.v("USER_SERVICE", "        getServices: ");
		Connect.post(context.getResources().getString(R.string.services_user), params, responseHandler);
	}

	public static void getServices(Context context , String user_id, String uuid,String year,String month,String day,AsyncHttpResponseHandler responseHandler) {

        RequestParams params = new RequestParams();
		params.put("user_id", user_id);
		params.put("uuid",uuid);
		params.put("year",year);
		params.put("month",month);
		params.put("day",day);
	    Log.v("USER_SERVICE", "        getServices: ");
		Connect.post(context.getResources().getString(R.string.services_user), params, responseHandler);
	}


	public static void cancelServiceBySystem(String url_cancel_current, AsyncHttpResponseHandler responseHandler) {

        RequestParams params = new RequestParams();
		params.put("by_system", "true");
	    Log.v("USER_SERVICE", "        cancelServiceBySystem: ");
		Connect.post(url_cancel_current, params, responseHandler);
	}


	public static void checkStatusService(Context context, String user_id, String service_id, String uuid, String address, Double from_lat, Double from_lng, AsyncHttpResponseHandler responseHandler) throws JSONException {


		RequestParams params = new RequestParams();
        Log.v("checkStatusService","service_id = " + service_id + " user_id = " + user_id);
        if (service_id != null ) {
		    params.put("user_id", user_id);
		    params.put("service_id", service_id);
			params.put("address", address);

	        Log.v("USER_SERVICE", "checkStatusService: " + params.toString());

			Connect.post(context.getResources().getString(R.string.checkstatuservice), params, responseHandler);
        }
        else {
        	JSONObject user = new JSONObject();
            user.put("user_id", user_id);
            Log.e("USER_ID", user.toString());

	        Log.v("USER_SERVICE", "checkStatusService: service_id = null");

   		    Connect.sendJson(context, context.getResources().getString(R.string.checkstatuservice),params, user, responseHandler);
        }
	}

	public static void finishSchedule(Context context , String user_id, String id_schedule,String uuid,String score,AsyncHttpResponseHandler responseHandler) {
		RequestParams params = new RequestParams();

		params.put("user_id", user_id);
		params.put("schedule_id", id_schedule);
		params.put("uuid", uuid);
		params.put("qualification", score);

	    Log.v("USER_SERVICE", "        finishSchedule: ");

		Connect.post(context.getResources().getString(R.string.finish_schedule), params, responseHandler);

	}

	public static void reclamo(Context context,String uuid,String service_id,String description, AsyncHttpResponseHandler responseHandler)
	{
		RequestParams params = new RequestParams();

		params.put("uuid", uuid);
		params.put("service_id", service_id);
		params.put("descript", description);

	    Log.v("USER_SERVICE", "        reclamo:");

		Connect.post(context.getResources().getString(R.string.reclamo), params, responseHandler);
	}


	public static void calificar(Context context,String uuid,String id_user,String service_id,String score,String obs_score, String score_driver, String num_score_driver, String driver_id, AsyncHttpResponseHandler responseHandler) {
		RequestParams params = new RequestParams();

		params.put("user_id", id_user);
		params.put("service_id", service_id);
		params.put("qualification", score);
		params.put("reason_qualification", obs_score);
		params.put("score_driver", score_driver);
		params.put("num_score_driver", num_score_driver);
		params.put("driver_id", driver_id);
		params.put("uuid", uuid);

		Log.v("CALIFICA","user_id="+id_user + " service_id=" + service_id + " qualification=" + score + " uuid=" + uuid);
		Log.v("USER_SERVICE", "        activity_calificar: ");

		Connect.post(context.getResources().getString(R.string.calificar), params, responseHandler);
	}

//	public static void agend(Context context,String user_id, String uuid,String fecha_hora,
//												int motivo,String indice_direccion,String
//													comp1,String comp2,String no
//														,String Barrio ,String obs,String
//															destination,AsyncHttpResponseHandler responseHandler)
	public static void agend(Context context,String user_id, String uuid,String fecha_hora,
							 int motivo, String address,String Barrio ,String obs,String
									 destination,String lat, String lng, AsyncHttpResponseHandler responseHandler) {

		RequestParams params = new RequestParams();

		params.put("user_id", user_id);
		params.put("uuid", uuid);
		params.put("service_date_time", fecha_hora);
		params.put("schedule_type", String.valueOf(motivo));
		params.put("address", address);
		params.put("city_lat", lat);
		params.put("city_lng", lng);
		params.put("barrio", Barrio);
		params.put("obs", obs);

		if (motivo == 2 || motivo == 3)
		{
			params.put("destination", destination);
		}

		Log.v("USER_SERVICE", "        agend: ");
//		Log.v("USER_SERVICE_AGEND", " -d user_id=" + user_id + " -d uuid=" + uuid + " -d service_date_time" + fecha_hora + " -d schedule_type=" + String.valueOf(motivo) + " -d address_index=" + indice_direccion + " -d comp1=" + comp1 + " -d comp2=" + comp2 + " -d no=" + no + " -d barrio=" + Barrio + " -d obs=" + obs);
		Log.v("USER_SERVICE_AGEND", " -d user_id=" + user_id + " -d uuid=" + uuid + " -d service_date_time" + fecha_hora + " -d schedule_type=" + String.valueOf(motivo) + " -d address=" + address  + " -d barrio=" + Barrio + " -d obs=" + obs);

		//Connect.postNode(context.getResources().getString(R.string.agend), params, responseHandler);
		Connect.post(context.getResources().getString(R.string.agend), params, responseHandler);
	}

	public static void myAgend(Context context ,String user_id, String uuid,AsyncHttpResponseHandler responseHandler) {

		RequestParams params = new RequestParams();
		params.put("user_id", user_id);
		params.put("uuid",uuid);
	    Log.i("USER_SERVICE", "        myAgend: "+uuid+" user_id: "+user_id);
		Connect.post(context.getResources().getString(R.string.myanged), params, responseHandler);
	}

	public static void cancelSchedule(Context context ,String user_id,String uuid,String id_schedule,AsyncHttpResponseHandler responseHandler)
	{
		RequestParams params = new RequestParams();

		params.put("user_id", user_id);

		params.put("uuid",uuid);

		params.put("schedule_id", id_schedule);

		Log.v("== cancelSchedule ==", " user_id: "  + user_id + " uuid: " + uuid + " schedule_id: " + id_schedule);

		Connect.postNode(context.getResources().getString(R.string.cancel_schedule), params, responseHandler);
	}



	public static void sendMailReset(Context context,String email,AsyncHttpResponseHandler responseHandler)
	{
		RequestParams params = new RequestParams();

		params.put("email", email);

	    Log.v("USER_SERVICE", "        sendMailReset: ");

		Connect.post(context.getResources().getString(R.string.resetpass), params, responseHandler);
	}

	public static void sendMailResetConfirm(Context context,String email,String token,String password,AsyncHttpResponseHandler responseHandler)
	{
		RequestParams params = new RequestParams();

		params.put("email", email);
		params.put("token", token);
		params.put("password", password);

	    Log.v("USER_SERVICE", "        sendMailResetConfirm: ");
	    Log.v("USER_SERVICE", "        sendMailResetConfirm: " + password);

		Connect.post(context.getResources().getString(R.string.code_pass), params, responseHandler);
	}

	public static void testConnectivityQuality(AsyncHttpResponseHandler responseHandler){
		Log.v("USER_SERVICE", "        ConnectivityQualityTest: ");
		Connect.connectivityQualityTest(responseHandler);
	}

	public static void confirmTicker(Context context,String ticket,AsyncHttpResponseHandler responseHandler) {

		RequestParams params = new RequestParams();
		params.put("ticket", ticket);
		Log.v("USER_SERVICE", "        confirmTicket: ");
		Connect.post(context.getResources().getString(R.string.confirm_ticket), params, responseHandler);
	}

}
