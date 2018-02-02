package com.imaginamos.usuariofinal.taxisya.io;

import com.imaginamos.usuariofinal.taxisya.Model.AddAddressResponse;
import com.imaginamos.usuariofinal.taxisya.Model.CheckServiceResponse;
import com.imaginamos.usuariofinal.taxisya.Model.DirectionsResponse;
import com.imaginamos.usuariofinal.taxisya.Model.FeesResponse;
import com.imaginamos.usuariofinal.taxisya.Model.InterruptResponse;
import com.imaginamos.usuariofinal.taxisya.Model.LoginResponse;
import com.imaginamos.usuariofinal.taxisya.Model.PlacesResponse;
import com.imaginamos.usuariofinal.taxisya.Model.RegisterResponse;
import com.imaginamos.usuariofinal.taxisya.Model.RequestServiceAddressResponse;
import com.imaginamos.usuariofinal.taxisya.Model.ReverseGeocodingResponse;
import com.imaginamos.usuariofinal.taxisya.Model.SimpleResponse;
import com.imaginamos.usuariofinal.taxisya.Model.UpdateResponse;
import com.imaginamos.usuariofinal.taxisya.Model.UpdateServiceResponse;
import com.imaginamos.usuariofinal.taxisya.models.CancelServiceResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.Call;
import retrofit2.http.Query;

/**
 * Created by leo on 8/31/15.
 */
public interface ApiService {

    // login
//    params.put("type", HomeActivity.TYPE_USER);
    @FormUrlEncoded
    @POST(ApiConstants.USER_LOGIN)
    Call<LoginResponse> login(@Field("type") String type, @Field("login") String login,
                              @Field("pwd") String pwd, @Field("uuid") String uuid);

    @FormUrlEncoded
    @POST(ApiConstants.USER_REGISTER)
    Call<RegisterResponse> register(@Field("type") String type, @Field("name") String name,
                                    @Field("lastname") String lastname, @Field("email") String email,
                                    @Field("login") String login, @Field("pwd") String pwd,
                                    @Field("token") String token, @Field("cellphone") String cellphone,
                                    @Field("uuid") String uuid);

    @GET(ApiConstants.DIRECTIONS_GOOGLE)
    Call<DirectionsResponse> directions(@Query("origin") String Origin, @Query("destination") String Destination);

    @GET(ApiConstants.PLACES_GOOGLE)
    Call<PlacesResponse> places(@Query("keyword") String keyword);

   @GET("geocode/json?" + ApiConstants.GOOGLE_KEY)
    Call<ReverseGeocodingResponse> reverse_geocoding(@Query("latlng") String latlng);

    @FormUrlEncoded
    @POST(ApiConstants.USER_UPDATE)
    Call<UpdateResponse> update(@Field("type") String type,
                                @Field("name") String name,
                                @Field("lastname") String lastname,
                                @Field("email") String email,
                                @Field("login") String login,
                                @Field("pwd") String pwd,
                                @Field("token") String token,
                                @Field("cellphone") String cellphone,
                                @Field("uuid") String uuid);
    @FormUrlEncoded
    @POST(ApiConstants.USER_UPDATE)
    Call<CheckServiceResponse> checkService(@Field("user_id") String user_id,
                                            @Field("service_id") String service_id,
                                            @Field("uuid") String uuid,
                                            @Field("address") String address,
                                            @Field("from_lat") Double from_lat,
                                            @Field("from_lng") Double from_lng
    );

    @GET(ApiConstants.SERVICE_CHAT)
    Call<SimpleResponse> sendNotification(@Query("id_conductor") String id_conductor);

    @FormUrlEncoded
    @POST(ApiConstants.SERVICE_INTERRUPT)
    Call<InterruptResponse> interrupt(@Field("id_vehiculo") int id_vehiculo,
                                      @Field("id_conductor") int id_conductor,
                                      @Field("latitude") double latitude,
                                      @Field("longitude") double longitude,
                                      @Field("to_lati") double to_lati,
                                      @Field("to_lng") double to_lng,
                                      @Field("direccion") String direccion,
                                      @Field("obs") String obs,
                                      @Field("barrio") String barrio,
                                      @Field("destino") String destino,
                                      @Field("km_recorrido") double km_recorrido,
                                      @Field("tiempo_recorido") String tiempo_recorido,
                                      @Field("valor_app") double valor_app,
                                      @Field("carrera_anterior") int carrera_anterior,
                                      @Field("n_pasajeros") int n_pasajeros,
                                      @Field("charge1") int charge1,
                                      @Field("charge2") int charge2,
                                      @Field("charge3") int charge3,
                                      @Field("charge4") int charge4);
 @FormUrlEncoded
    @POST(ApiConstants.SERVICE_UPDATE)
    Call<UpdateServiceResponse> service_update (@Field("idServicio") int idServicio,
                                                @Field("to_lat") double to_lat,
                                                @Field("to_lng") double to_lng,
                                                @Field("valor") double valor,
                                                @Field("distancia") double distancia,
                                                @Field("tiempo") String tiempo);

    @FormUrlEncoded
    @POST(ApiConstants.REQUEST_SERVICE_ADDRESS)
    Call<RequestServiceAddressResponse> requestServiceAddress(
            @Field("usuario") String usuario,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("to_lati") String to_lati,
            @Field("to_lng") String to_lng,
            @Field("obs") String obs,
            @Field("barrio") String barrio,
            @Field("aeropuerto") int aeropuerto,
            @Field("nocturno") int nocturno,
            @Field("mensajeria") int mensajeria,
            @Field("pp") int pp,
            @Field("pay_type") String pay_type,
            @Field("destino") String destino,
            @Field("address") String address,
            @Field("km_recorrido") int km_recorrido,
            @Field("tiempo_recorido") int tiempo_recorido,
            @Field("valor_app") int valor_app,
            @Field("nVale") String nVale,
            @Field("motivoVale") String motivoVale

    );


    @GET(ApiConstants.REQUEST_FEES)
    Call<FeesResponse> requestFees(
            @Query("lat") String lat,
            @Query("lng") String lng,
            @Query("time") String time

    );


    // cancelar servicio
    @POST(ApiConstants.CANCEL_SERVICE)
    Call<CancelServiceResponse> cancelService(@Path("user_id") String user_id);

    @FormUrlEncoded
    @POST(ApiConstants.USER_IS_LOGUED)
    void isLogued(@Field("login") String login, @Field("uuid") String uuid, retrofit.Callback<ServiceResponse> callback);

    @FormUrlEncoded
    @POST(ApiConstants.USER_LOGOUT)
    void logout(@Field("login") String login, @Field("pwd") String pwd, retrofit.Callback<ServiceResponse> callback);

    @FormUrlEncoded
    @POST(ApiConstants.GET_SERVICES)
    void getServicesMonth(@Field("user_id") String user_id, @Field("month") String month,
                          @Field("uuid") String uuid, retrofit.Callback<ServiceResponse> callback);

    @FormUrlEncoded
    @POST(ApiConstants.GET_SERVICES)
    void getServicesDayOfMonth(@Field("user_id") String user_id, @Field("month") String month,
                               @Field("day") String day, @Field("uuid") String uuid, retrofit.Callback<ServiceResponse> callback);

    @FormUrlEncoded
    @POST(ApiConstants.GET_ADDRESS)
    Call<AddressServiceResponse>  getAddress(@Field("user_id") String user_id, @Field("uuid") String uuid);

    @FormUrlEncoded
    @POST(ApiConstants.DEL_ADDRESS)
    void delAddress(@Field("address_id") String address_id, retrofit.Callback<ServiceResponse> callback);


    @FormUrlEncoded
    @POST(ApiConstants.ADD_ADDRESS)
    Call<AddAddressResponse> addAddress(
            @Field("address") String address,
            @Field("barrio") String barrio,
            @Field("obs") String obs,
            @Field("user_id") String user,
            @Field("user_pref_order") String order,
            @Field("lat") String lat,
            @Field("lng") String lng,
            @Field("nombre") String nombre);

    // cancelar servicio
    @POST(ApiConstants.CANCEL_SERVICE_SYSTEM)
    void cancelServiceBySystem(@Path("user_id") String user_id, @Field("by_system") String value, retrofit.Callback<RequestServiceResponse> callback);

    @FormUrlEncoded
    @POST(ApiConstants.SERVICE_SCORE)
    void scoreService(@Field("user_id") String user_id,
                      @Field("service_id") String service_id,
                      @Field("qualification") String qualification,
                      retrofit.Callback<ServiceResponse> callback);

    // agendamiento
    @FormUrlEncoded
    @POST(ApiConstants.SCHEDULE_FINISH)
    void finishSchedule(@Field("user_id") String user_id,
                        @Field("schedule_id") String schedule_id,
                        @Field("qualification") String qualification,
                        @Field("uuid") String uuid,
                        retrofit.Callback<ScheduleResponse> callback);


    @FormUrlEncoded
    @POST(ApiConstants.SCHEDULE_USER)
    void getSchedules(@Field("user_id") String user_id, @Field("uuid") String uuid, retrofit.Callback<ScheduleResponse> callback);

    @FormUrlEncoded
    @POST(ApiConstants.SCHEDULE_REQUEST)
    void requestSchedule(@Field("user_id") String user_id,
                         @Field("service_date_time") String service_date_time,
                         @Field("schedule_type") String schedule_type,
                         @Field("address_index") String address_index,
                         @Field("comp1") String comp1,
                         @Field("comp2") String comp2,
                         @Field("no") String no,
                         @Field("barrio") String barrio,
                         @Field("obs") String obs,
                         @Field("description") String description,
                         @Field("uuid") String uuid, retrofit.Callback<ScheduleResponse> callback);

    @FormUrlEncoded
    @POST(ApiConstants.USER_CONFIRM)
    void sendMailResetConfirm(@Field("email") String email,
                              @Field("token") String token,
                              @Field("password") String password,
                              retrofit.Callback<ScheduleResponse> callback);

    @FormUrlEncoded
    @POST(ApiConstants.USER_FORGOTTEN)
    void sendMailReset(@Field("email") String email, retrofit.Callback<UserResponse> callback);

    @FormUrlEncoded
    @POST(ApiConstants.SERVICE_COMPLAIN)
    void complain(@Field("service_id") String service_id,
                  @Field("descript") String descript,
                  @Field("uuid") String uuid, retrofit.Callback<ServiceResponse> callback);

    @FormUrlEncoded
    @POST(ApiConstants.APP_VERSION)
    void appVersion(@Field("descript") String descript, retrofit.Callback<AppResponse> callback);

}
