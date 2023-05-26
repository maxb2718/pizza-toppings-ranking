package com.example.pizza.service.impl

import com.example.pizza.model.ToppingRequest
import com.example.pizza.model.ToppingScore
import com.example.pizza.repository.ToppingRequestRepository
import com.example.pizza.service.ToppingService
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class ToppingServiceImpl(
    private val redisTemplate: RedisTemplate<String, String>,
    private val toppingRequestRepository: ToppingRequestRepository
) : ToppingService {

    companion object {
        const val TOPPING_SCORES_ZSET = "topping_scores"
    }

    override fun getAllToppings(): Iterable<ToppingScore> =
        redisTemplate.opsForZSet().rangeWithScores(TOPPING_SCORES_ZSET, 0, -1)!!
            .map { ToppingScore(it.value!!, it.score!!) }
            .sortedByDescending { it.score }

    override fun processToppingRequest(toppingRequest: ToppingRequest) {
        val previousRequest = toppingRequestRepository.findById(toppingRequest.email)
        toppingRequestRepository.save(toppingRequest)
        val requestedToppings = toppingRequest.getToppings()

        if (previousRequest.isEmpty) {
            incrementToppingScores(requestedToppings, 1.0)
            return
        }

        val previousToppings = previousRequest.get().getToppings()
        val newToppings = requestedToppings.minus(previousToppings)
        val toppingsDontWantAnymore = previousToppings.minus(requestedToppings)
        incrementToppingScores(newToppings, 1.0)
        incrementToppingScores(toppingsDontWantAnymore, -1.0)
    }

    private fun incrementToppingScores(toppings: Iterable<String>, delta: Double) {
        toppings.forEach {
            redisTemplate.opsForZSet().incrementScore(TOPPING_SCORES_ZSET, it, delta)
        }
    }

}
