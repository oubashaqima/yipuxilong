package com.zhl.yipuxilong.service.impl;

import com.theokanning.openai.service.OpenAiService;
import com.zhl.yipuxilong.service.OpenAIService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class OpenAIServiceImpl implements OpenAIService {


    @Value("${key}")
    private String key;

    @Override
    public OpenAiService getChatService() {
        return new OpenAiService(key, Duration.ofSeconds(100));
    }

    @Override
    public long getLastActivateTime() {
        return System.currentTimeMillis();
    }
}
