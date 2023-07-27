package org.jash.homepage

import kotlinx.coroutines.runBlocking
import org.jash.homepage.model.VideoModel
import org.jash.homepage.net.HomeService
import org.jash.network.model.User
import org.jash.network.retrofit
import org.jash.network.user
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
//    @Test
    fun testBulletScreen() {
        user = User(0,"","",0, false, "", "", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhZG1pbiJ9.Jwb2uY2Sb0SPsQavnpC3irfmNUCt6OjdmLZSRiD_8Bs","")
        val service = retrofit.create(HomeService::class.java)
        runBlocking {
            val comments = service.getCommentByItemId(0, "6968386858111730183").data
            comments.forEach(::println)

        }
    }
}