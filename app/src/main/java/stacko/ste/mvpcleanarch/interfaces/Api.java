package stacko.ste.mvpcleanarch.interfaces;

import stacko.ste.mvpcleanarch.model.TopUsersResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface Api {


    @GET("/2.2/users")
    Call<TopUsersResponse> getTopUsers(
            @Query("order") String orderBy,
            @Query("sort") String sortBy,
            @Query("site") String siteName,
            @Query("pagesize") int pageSize
    );


}