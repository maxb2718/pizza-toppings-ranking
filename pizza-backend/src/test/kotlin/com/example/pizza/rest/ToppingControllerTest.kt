package com.example.pizza.rest

import com.example.pizza.BasePizzaApplicationTest
import com.example.pizza.model.ToppingRequest
import com.example.pizza.model.ToppingScore
import com.example.pizza.service.impl.ToppingServiceImpl
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content

@AutoConfigureMockMvc
class ToppingControllerTest : BasePizzaApplicationTest() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockkBean
    private lateinit var toppingService: ToppingServiceImpl

    @Test
    fun `Should return all toppings`() {
        val expectedToppings = listOf(
            ToppingScore("Pepperoni", 2.0),
            ToppingScore("Cheese", 1.0)
        )
        every { toppingService.getAllToppings() } returns expectedToppings

        mockMvc.perform(get("/toppings"))
            .andExpect(status().isOk)
            .andExpect(content().json(objectMapper.writeValueAsString(expectedToppings)))
    }

    @Test
    fun `Should post topping request`() {
        val toppingRequest = ToppingRequest("user@example.com", "Pepperoni, Cheese")
        every { toppingService.processToppingRequest(toppingRequest) } answers { Unit }

        val toppingRequestJson = objectMapper.writeValueAsString(toppingRequest)

        mockMvc.perform(post("/toppings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toppingRequestJson))
            .andExpect(status().isOk)
        // Add more expectations here
    }

    @Test
    fun `Should return bad request for invalid email in topping request`() {
        val badToppingRequest = ToppingRequest("bad_email", "Pepperoni, Cheese")
        val badToppingRequestJson = objectMapper.writeValueAsString(badToppingRequest)

        mockMvc.perform(post("/toppings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(badToppingRequestJson))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `Should return bad request for too long toppingsContent in topping request`() {
        val badToppingRequest = ToppingRequest("user@example.com", "P".repeat(121)) // exceeding 120 character limit
        val badToppingRequestJson = objectMapper.writeValueAsString(badToppingRequest)

        mockMvc.perform(post("/toppings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(badToppingRequestJson))
            .andExpect(status().isBadRequest)
    }
}
