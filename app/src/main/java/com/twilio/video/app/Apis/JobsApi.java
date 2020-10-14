package com.twilio.video.app.Apis;

import com.twilio.video.app.ApiModals.MakeClassResponse;
import com.twilio.video.app.JobsResponse.FindJobsRespose;
import com.twilio.video.app.PostedJobResponse.PostedJobsList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface JobsApi {

    @GET("jobs")
    Call<FindJobsRespose> getJobs(
            @Query("token") String token
    );

    @GET
    Call<MakeClassResponse> applyJob(
            @Url String url,
            @Query("token") String token

    );

    @GET("job_posted")
    Call<PostedJobsList> postedJob(
            @Query("token") String token
    );


    @FormUrlEncoded
    @POST("job/new")
    Call<MakeClassResponse> makeNewJob(
            @Query("token") String token,
            @Field("title") String title,
            @Field("description") String description,
            @Field("location") String location,
            @Field("company") String company,
            @Field("required_exp") String required_exp,
            @Field("min_qualification") String min_qualification,
            @Field("salary") String salary,
            @Field("last_date") String last_date

    );

    @FormUrlEncoded
    @POST("job/edit")
    Call<MakeClassResponse> editJob(
            @Query("token") String token,
            @Query("id") String id,
            @Field("title") String title,
            @Field("company") String company,
            @Field("description") String description,
            @Field("location") String location,
            @Field("required_exp") String required_exp,
            @Field("min_qualification") String min_qualification,
            @Field("salary") String salary,
            @Field("last_date") String last_date

    );

    @GET
    Call<MakeClassResponse> deleteJob(
            @Url String root,
            @Query("token") String token
    );

}
