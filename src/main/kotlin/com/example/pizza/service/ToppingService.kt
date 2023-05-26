package com.example.pizza.service

import com.example.pizza.model.ToppingRequest
import com.example.pizza.model.ToppingScore

interface ToppingService {

    fun getAllToppings(): Iterable<ToppingScore>
    fun processToppingRequest(toppingRequest: ToppingRequest)

}