package com.fsep.sova.network;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Document;
import com.fsep.sova.models.Photo;
import com.fsep.sova.models.Video;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface SOVAStorageRest {

  /**
   * Файловое хранилище принимает файл на вход, проводит верификацию по расширению. Далее, хранилище
   * определеяет, действительно ли тип файла соответствует содержимому
   */

  @Multipart @POST("uploads/files") Call<BaseResponseModel<Document>> uploadFile(
      @Header("ticket") String ticket, @Part MultipartBody.Part file);

  @Multipart @POST("uploads/files") Call<BaseResponseModel<Photo>> uploadImageFile(
      @Header("ticket") String ticket, @Part MultipartBody.Part file);

  @Multipart @POST("uploads/files") Call<BaseResponseModel<Video>> uploadVideoFile(
      @Header("ticket") String ticket, @Part MultipartBody.Part file);
}
