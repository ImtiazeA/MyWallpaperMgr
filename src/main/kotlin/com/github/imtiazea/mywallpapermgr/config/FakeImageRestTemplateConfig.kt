package com.github.imtiazea.mywallpapermgr.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.web.client.RestTemplate

@Configuration
@Import(FakeImageHeaderModifierInterceptor::class)
class FakeImageRestTemplateConfig {

    @Bean
    public fun fakeImageRestTemplate(fakeImageHeaderModifierInterceptor: FakeImageHeaderModifierInterceptor) : RestTemplate {

        val restTemplate = RestTemplate()
        val interceptors = restTemplate.interceptors
        interceptors.add(fakeImageHeaderModifierInterceptor)

        return restTemplate
    }

}