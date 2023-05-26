package com.example.pizza

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.stereotype.Component

@Component
@Profile("!test")
class StartupValidator(
    private val redisConnectionFactory: RedisConnectionFactory
) : CommandLineRunner {

    companion object {
        private val logger = LoggerFactory.getLogger(StartupValidator::class.java)
    }

    @Throws(Exception::class)
    override fun run(vararg args: String) {
        redisConnectionFactory.connection.ping()
        logger.info("Successfully connected to Redis!")
    }
}
