package com.zhl.yipuxilong.controller;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import com.zhl.yipuxilong.service.OpenAIService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yipuxilong
 */
@RestController
@RequestMapping("/openai")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ChatController {

    private static final Logger logger = LogManager.getLogger(ChatController.class);

    @Autowired
    private OpenAIService openAIService;

    private static final long MAX_INACTIVE_TIME = 300000;

    private long lastActivateTime;

    private List<ChatMessage> messageList;

    @PostConstruct
    public void initLastActivateTime() {
        lastActivateTime = openAIService.getLastActivateTime();
    }

    /**
     * 测试连接是否成功
     * @return String
     */
    @GetMapping("/connect")
    public String testConnect() {
        logger.info("test log");
        openAIService.getChatService();
        return "connect is ok!!!";
    }

    @PostMapping("/chat")
    public String getResponse(@RequestBody String prompt) {

        logger.info("user input:{}", prompt);
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastActivateTime > MAX_INACTIVE_TIME) {
            messageList = new ArrayList<>();
        }
        OpenAiService chatService = openAIService.getChatService();
        messageList.add(new ChatMessage("user", prompt));
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(messageList)
                .build();
        List<ChatCompletionChoice> chatCompletionChoiceList = chatService.createChatCompletion(chatCompletionRequest).getChoices();
        lastActivateTime = currentTime;
        return chatCompletionChoiceList.get(0).getMessage().getContent();
    }
}
