package stacko.ste.mvpcleanarch.data.api;

import io.reactivex.Observable;
import stacko.ste.mvpcleanarch.domain.model.TopUsersResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface Api {


    @GET("/2.2/users")
    Observable<TopUsersResponse> getTopUsers(
            @Query("order") String orderBy,
            @Query("sort") String sortBy,
            @Query("site") String siteName,
            @Query("pagesize") int pageSize
    );


}
