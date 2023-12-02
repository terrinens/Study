spring security 3.1.5 버전 방식입니다. <br>
기존에 implement 하지 않고 클래스를 @configuration 해서 <br> 
구성 파일로 인식하게 만들고 해당 메서드를 @bean 을 주입시켜 사용하는 방식입니다. <br> 
처음 참조할 부분은 여기를 참조 하시면 됩니다. <br>
[처음 설정 방법 :: 3.1.5](https://www.inflearn.com/questions/1062450/2-00-%EC%97%90%EC%84%9C-%EC%A0%80%EC%B2%98%EB%9F%BC-%EB%B2%84%EC%A0%84-%EC%95%88-%EB%A7%9E%EC%B6%B0%EC%84%9C-%ED%95%B4%EC%84%9C-%ED%97%A4%EB%A7%A4%EB%8A%94-%EB%B6%84%EB%93%A4-%EC%9D%B4%EA%B1%B8%EB%A1%9C-%ED%95%B4%EB%B3%B4%EC%84%B8%EC%9A%94) <br> 
그리고 설정 방식을 유지 시켜 수업 내용과 일치 시키는 방법입니다. <br>

### 방법 1 <br>
1. 메서드 수정 <br>
```azure
@Bean
public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)

                .exceptionHandling((handling) ->
                        handling.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                )

                .headers((header) ->
                        header.frameOptions(
                                HeadersConfigurer.FrameOptionsConfig::sameOrigin
                        )
                )

                .authorizeHttpRequests((registry) ->
                        registry.requestMatchers("/api/hello").permitAll()
                                .requestMatchers("/api/authentication").permitAll()
                                .requestMatchers("/api/signup").permitAll()
                                .anyRequest().authenticated()
                );
    return httpSecurity.build();
}

```

### 방법2 <br>
1. JwtSecurityConfig에 메서드를 하나 추가 한다. <br>

```azure
public HttpSecurity configureAndReturn(HttpSecurity httpSecurity) {
    httpSecurity.addFilterBefore(
            new JwtFilter(tokenProvider),
            UsernamePasswordAuthenticationFilter.class
    );
    return httpSecurity;
}
```

2. SecutiryConfig 메서드를 수정한다. <br>

```azure
@Bean
public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    return new JwtSecurityConfig(tokenProvider).configureAndReturn(
            httpSecurity
                    .csrf(AbstractHttpConfigurer::disable)

                    .exceptionHandling((handling) ->
                            handling.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                    .accessDeniedHandler(jwtAccessDeniedHandler)
                    )
                        .headers((header) ->
                            header.frameOptions(
                                    HeadersConfigurer.FrameOptionsConfig::sameOrigin
                            )
                    )

                    .authorizeHttpRequests((registry) ->
                            registry.requestMatchers("/api/hello").permitAll()
                                    .requestMatchers("/api/authentication").permitAll()
                                    .requestMatchers("/api/signup").permitAll()
                                    .anyRequest().authenticated()
                    )
    ).build();
}
```

개인적으로는 1 방식이 더 깔끔하다고 생각합니다.
