package ru.glitchless.newserver.retrofit

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.http.HttpStatus.OK
import org.springframework.http.HttpStatus.UNAUTHORIZED
import org.springframework.test.context.junit4.SpringRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.glitchless.newserver.data.model.UserModel
import ru.glitchless.newserver.data.stores.UserDao

@SpringBootTest(webEnvironment = RANDOM_PORT)
@RunWith(SpringRunner::class)
class RetrofitTest {
    @LocalServerPort
    private var localPort: Int = 0;
    @Autowired
    private lateinit var userDao: UserDao
    private lateinit var api: IServerApi
    private lateinit var user: UserModel

    @Before
    fun setup() {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://localhost:$localPort/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        api = retrofit.create(IServerApi::class.java)
        user = UserModel("LionZXY", "123456789")
        user.setEmail("nikita@kulikof.ru")
        userDao.clearAllTable()
        registration()
    }

    @Test
    fun testMeRequiresLogin() {
        val response = api.me().execute()
        assertEquals(UNAUTHORIZED.value(), response.code())
    }

    @Test
    fun testMe() {
        val session = login()

        val response = api.me(session).execute()
        assertTrue(response.isSuccessful)
        assertEquals(response.body()!!.message!!.login, user.login)
        assertEquals(response.body()!!.message!!.email, user.email)
    }

    @Test
    fun testLogin() {
        login()
    }

    @Test
    fun testLogout() {
        val session = login()

        val resposeOnFirstMe = api.me(session).execute()
        assertEquals(OK.value(), resposeOnFirstMe.code())
        assertEquals(resposeOnFirstMe.body()!!.message!!.login, user.login)
        assertEquals(resposeOnFirstMe.body()!!.message!!.email, user.email)

        val responseLogout = api.logout(session).execute()
        assertTrue(responseLogout.isSuccessful)

        val responseOnSecondMe = api.me(session).execute()
        assertEquals(UNAUTHORIZED.value(), responseOnSecondMe.code())

    }

    @Test
    fun testUpdate() {
        val responseLogin = api.login(user).execute()
        assertTrue(responseLogin.isSuccessful)
        assertEquals(responseLogin.body()!!.message!!.login, user.login)
        assertEquals(responseLogin.body()!!.message!!.email, user.email)

        val newUser = UserModel(user.login, user.password)
        newUser.email = "some.email@email.com"

        val updateExecute = api.update(newUser).execute()
        assertTrue(updateExecute.isSuccessful)

        val responseSecondLogin = api.login(user).execute()
        assertTrue(responseSecondLogin.isSuccessful)
        assertEquals(responseSecondLogin.body()!!.message!!.login, user.login)
        assertNotEquals(responseSecondLogin.body()!!.message!!.email, user.email)
        assertEquals(responseSecondLogin.body()!!.message!!.email, newUser.email)

        api.update(user).execute()
    }

    private fun registration() {
        val response = api.signup(user).execute()
        assertTrue(response.isSuccessful)
        assertEquals(response.body()!!.message!!.login, user.login)
        assertEquals(response.body()!!.message!!.email, user.email)
    }

    private fun login(): String {
        val response = api.login(user).execute()
        assertTrue(response.isSuccessful)
        assertEquals(response.body()!!.message!!.login, user.login)

        return response.headers()["Set-Cookie"]!!
    }
}