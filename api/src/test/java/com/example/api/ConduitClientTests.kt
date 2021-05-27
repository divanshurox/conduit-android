package com.example.api

import com.example.api.models.entities.UserCreds
import com.example.api.models.requests.LoginRequest
import com.example.api.models.requests.RegisterRequest
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.random.Random

class ConduitClientTests {
    private val conduitClient = ConduitClient()

    fun generateRandomString(length: Int): String{
        val possibleVals = ('A'..'Z') + ('0'..'9')
        return (1..length).map{ possibleVals.random() }.joinToString("")
    }

    @Test
    fun `GET articles`(){
        runBlocking {
            val articles = conduitClient.publicApi.getArticles()
            Assert.assertNotNull(articles.body()?.articles)
        }
    }

    @Test
    fun `GET articles by author`(){
        runBlocking {
            val articlesByAuthor = conduitClient.publicApi.getArticles(author = "jane")
            Assert.assertNotNull(articlesByAuthor.body()?.articles)
        }
    }

    @Test
    fun `POST register user`(){
        val userCreds = UserCreds(
            email = "testmail${Random.nextInt(1,9)}@test.com",
            password = generateRandomString(10),
            username = generateRandomString(10)
        )
        runBlocking {
            val user = conduitClient.publicApi.registerUser(RegisterRequest(userCreds))
            assertEquals(userCreds.username,user.body()?.user?.username)
        }
    }

    @Test
    fun `POST login user`(){
        val userCreds = UserCreds(
            email = "testmail3@test.com",
            password = "1234567890"
        )
        runBlocking {
            val user = conduitClient.publicApi.loginUser(LoginRequest(userCreds))
            assertEquals(userCreds.email,user.body()?.user?.email)
        }
    }

}