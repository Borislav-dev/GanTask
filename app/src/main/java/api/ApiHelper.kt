package api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getChars() = apiService.getCharacters()
}