package com.example.pizza.repository

import com.example.pizza.model.ToppingRequest
import org.springframework.data.repository.CrudRepository

interface ToppingRequestRepository : CrudRepository<ToppingRequest, String>
