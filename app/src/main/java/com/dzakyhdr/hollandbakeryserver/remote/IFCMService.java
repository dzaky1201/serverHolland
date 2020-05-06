package com.dzakyhdr.hollandbakeryserver.remote;
import com.dzakyhdr.hollandbakeryserver.model.FCMRespone;
import com.dzakyhdr.hollandbakeryserver.model.FCMSendData;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;



public interface IFCMService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAdEabe0o:APA91bFCOIJ-zGmkRtuCj5IjvrSHjtd1gyDMqhHfm5Y121DZSE_Iy8QXUE0W_c4Kl3j9hFI9GXEPUd7r-EjGOI6IYg84ZX463hkQ_HmD__6IfRV3aTckINEBP-WhBj_AW-SCBdFLoWE-"
    })
    @POST("fcm/send")
    Observable<FCMRespone> sendNotification(@Body FCMSendData body);
}
