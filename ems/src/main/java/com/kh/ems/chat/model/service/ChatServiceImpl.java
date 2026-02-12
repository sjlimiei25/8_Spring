package com.kh.ems.chat.model.service;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatModel chatModel;

    public ChatServiceImpl(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public String generateText(String question) {
        // 시스템 메시지 생성
        SystemMessage systemMessage = SystemMessage.builder()
                .text("사용자 질문에 대해 한국어로 답변해줘.")
                .build();

        // 사용자 메시지 생성
        UserMessage userMessage = UserMessage.builder()
                .text(question)
                .build();

        // 대화 옵션 설정
        ChatOptions chatOptions = ChatOptions.builder()
                .model("gpt-4o-mini")
                .temperature(0.3)
                .maxTokens(1000)
                .build();

        // 프롬프트 생성
        Prompt prompt = Prompt.builder()
                .messages(systemMessage, userMessage)
                .chatOptions(chatOptions)
                .build();

        // LLM에게 요청하고 응답받기
        ChatResponse response = chatModel.call(prompt);
        AssistantMessage assistantMessage = response.getResult().getOutput();

        String answer = assistantMessage.getText();

        return answer;
    }
}
