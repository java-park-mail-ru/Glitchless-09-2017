package ru.glitchless.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpSession
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.glitchless.data.models.UserModel
import ru.glitchless.data.stores.UserDao
import ru.glitchless.repositories.auth.UserService


@SpringBootTest
@RunWith(SpringRunner::class)
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
open class UserControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var userDao: UserDao
    @Autowired
    private lateinit var userService: UserService
    private lateinit var existingUser: UserModel
    private var session: MockHttpSession? = null;
    val basePath: String = "/api"

    @Before
    fun before() {
        userDao.clearAllTable()
        existingUser = UserModel("lionzxy", "123456789")
        existingUser.setEmail("nikita@kulikof.ru")
        userService.registerUser(existingUser)
    }

    @Test
    fun testRegistration() {
        mockMvc.perform(post(basePath + "/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"login\": \"Testreg\", \"email\": \"Test@test.ru\", \"password\": \"123456789\"}"))
                .andExpect(status().isOk)
    }

    @Test
    fun testUnsucRegistration() {
        mockMvc.perform(post(basePath + "/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(existingUser)))
                .andExpect(status().isForbidden)
    }


    @Test
    fun testUnsucLogin() {
        mockMvc.perform(post(basePath + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"login\": \"BLABLA\", \"email\": \"Test@test.ru\", \"password\": \"123456789\"}"))
                .andExpect(status().isNotFound)
    }

    @Test
    fun testUserChange() {
        mockMvc.perform(post(basePath + "/user/change")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"login\": \"${existingUser.login}\", \"password\": \"${existingUser.password}\", \"email\": \"newemail@newemail.ru\"}"))
                .andExpect(status().isOk)
    }

    @Test
    fun testLogin() {
        mockMvc.perform(post(basePath + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"login\": \"${existingUser.login}\", \"password\": \"${existingUser.password}\"}"))
                .andExpect(status().isOk)
                .andDo { session = it.request.getSession(false) as MockHttpSession }
    }

    @Test
    fun testCurrentUser() {
        testLogin()
        mockMvc.perform(get(basePath + "/user")
                .session(session!!))
                .andExpect(status().isOk)
                .andExpect(jsonPath("message.login").value(existingUser.login))
    }

    @Test
    fun testLogout() {
        testCurrentUser()
        mockMvc.perform(post(basePath + "/logout")
                .session(session!!))
                .andExpect(status().isOk)
        mockMvc.perform(get(basePath + "/user")
                .session(session!!))
                .andExpect(status().isUnauthorized)
    }


}
