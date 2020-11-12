package repository

import api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getChars() = apiHelper.getChars()
}