package com.example.pizza.service

import com.example.pizza.model.ToppingRequest
import com.example.pizza.repository.ToppingRequestRepository
import com.example.pizza.service.impl.ToppingServiceImpl
import com.example.pizza.service.impl.ToppingServiceImpl.Companion.TOPPING_SCORES_ZSET
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ZSetOperations
import java.util.*

class ToppingServiceImplTest {

    private lateinit var toppingService: ToppingServiceImpl
    private val redisTemplate: RedisTemplate<String, String> = mock()
    private val toppingRequestRepository: ToppingRequestRepository = mock()

    @BeforeEach
    fun setUp() {
        toppingService = ToppingServiceImpl(redisTemplate, toppingRequestRepository)
    }

    @Test
    fun `test getAllToppings`() {
        // Given
        val typedTuple: ZSetOperations.TypedTuple<String> = mock {
            on { value } doReturn "Cheese"
            on { score } doReturn 1.0
        }
        val zsetOps: ZSetOperations<String, String> = mock {
            on { rangeWithScores(TOPPING_SCORES_ZSET, 0, -1) } doReturn setOf(typedTuple)
        }
        whenever(redisTemplate.opsForZSet()).thenReturn(zsetOps)

        // When
        val result = toppingService.getAllToppings()

        // Then
        verify(zsetOps).rangeWithScores(TOPPING_SCORES_ZSET, 0, -1)
        assertThat(result).hasSize(1)
        assertThat(result.first().topping).isEqualTo("Cheese")
        assertThat(result.first().score).isEqualTo(1.0)
    }

    @Test
    fun `test processToppingRequest`() {
        // Given
        val email = "test@example.com"
        val toppingsContent = "Cheese,Pepperoni"
        val toppingRequest = ToppingRequest(email, toppingsContent)

        val previousRequest: Optional<ToppingRequest> = Optional.empty()
        whenever(toppingRequestRepository.findById(email)).thenReturn(previousRequest)

        val zsetOps: ZSetOperations<String, String> = mock()
        whenever(redisTemplate.opsForZSet()).thenReturn(zsetOps)

        // When
        toppingService.processToppingRequest(toppingRequest)

        // Then
        verify(toppingRequestRepository).save(toppingRequest)
        verify(zsetOps, times(2)).incrementScore(eq(TOPPING_SCORES_ZSET), any(), any())
    }
}
