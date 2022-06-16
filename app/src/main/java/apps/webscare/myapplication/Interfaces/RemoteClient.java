package apps.webscare.myapplication.Interfaces;

import apps.webscare.myapplication.Model.ProductModel;
import apps.webscare.myapplication.Model.RegisterUser;
import apps.webscare.myapplication.Model.resetPass.ResetPassModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RemoteClient {
    @POST("registration")
    @FormUrlEncoded
    Call<RegisterUser> registerUser(@Field("fullname") String name, @Field("email") String email, @Field("phone") String  phoneNumber, @Field("address") String address,@Field("password") String password);


    @POST("login")
    @FormUrlEncoded
    Call<RegisterUser> loginUser(@Field("email") String email,@Field("password") String password);

    @GET("category")
    Call<ProductModel> getProducts(@Header("Authorization")String authHeader);

    @POST("update")
    @FormUrlEncoded
    Call<ResetPassModel> resetPass(@Header("Authorization")String authHeader, @Field("current_password")String old, @Field("new_password")String passNew, @Field("renew_password")String ConfirmNew);

}
