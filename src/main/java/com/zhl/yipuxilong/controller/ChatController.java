package com.zhl.yipuxilong.controller;

import com.theokanning.openai.service.OpenAiService;
import com.zhl.yipuxilong.service.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/openai")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ChatController {

    @Autowired
    private OpenAIService openAIService;

    /**
     * 测试连接是否成功
     * @return String
     */
    @GetMapping("/connect")
    public String testConnect() {
        return "connect is ok!!!";
    }

    @PostMapping("/chat")
    public String getResponse(@RequestBody String prompt) {
        OpenAiService chatService = openAIService.getChatService();
        return "";
    }

}
