package com.example.vika.Networks;

import com.example.vika.Classes.Message;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface RetrofitInterface {
    @FormUrlEncoded
    @POST("sms/send")
    Observable<Message> getMessage(@Field("api_id") String api_id, @Field("to") Long to, @Field("msg") String msg, @Field("json") int json);
}
