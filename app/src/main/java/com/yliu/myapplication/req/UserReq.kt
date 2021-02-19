import io.reactivex.Observable
import req.Result
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserReq {

    @POST("/yliu/user/register")
    fun reqgister(@Body user: User): Observable<Result<User>>

    @POST("/yliu/user/login")
    fun login(@Body user: User): Observable<Result<User>>

}
