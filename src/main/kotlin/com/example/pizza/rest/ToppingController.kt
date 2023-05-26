package com.example.pizza.rest

import com.example.pizza.model.ToppingScore
import com.example.pizza.model.ToppingRequest
import com.example.pizza.service.impl.ToppingServiceImpl
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/toppings")
class ToppingController(private val toppingService: ToppingServiceImpl) {

    @PostMapping
    fun postToppingRequest(@RequestBody @Valid toppingRequest: ToppingRequest) {
        toppingService.processToppingRequest(toppingRequest)
    }

    @GetMapping
    fun getAllToppings(): Iterable<ToppingScore> {
        return toppingService.getAllToppings()
    }

}

