package api

import model.BreakingBadModelItem
import retrofit2.http.GET

interface ApiService {
    @GET("characters")
    suspend fun getCharacters(): List<BreakingBadModelItem>
}