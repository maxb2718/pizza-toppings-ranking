package com.example.pizza.model

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("topping_requests")
data class ToppingRequest(
    @Id
    @field:NotBlank(message = "Email must not be blank.")
    @field:Email(message = "Email should be valid.")
    val email: String,

    @field:NotBlank(message = "Toppings content must not be blank.")
    @field:Size(max = 120, message = "Toppings content must be no longer than 120 characters.")
    val toppingsContent: String
) {

    fun getToppings(): Set<String> {
        return toppingsContent.split(",")
            .map { it.trim() }
            .toSet()
    }

}
