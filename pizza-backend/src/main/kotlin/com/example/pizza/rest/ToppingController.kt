package com.example.pizza.rest

import com.example.pizza.model.ToppingScore
import com.example.pizza.model.ToppingRequest
import com.example.pizza.service.ToppingService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/toppings")
@CrossOrigin
class ToppingController(private val toppingService: ToppingService) {

    @PostMapping
    fun postToppingRequest(@RequestBody @Valid toppingRequest: ToppingRequest): ResponseEntity<Void> {
        toppingService.processToppingRequest(toppingRequest)
        return ResponseEntity.ok().build()
    }

    @GetMapping
    fun getAllToppings(): ResponseEntity<Iterable<ToppingScore>> {
        return ResponseEntity.ok(toppingService.getAllToppings())
    }

}

