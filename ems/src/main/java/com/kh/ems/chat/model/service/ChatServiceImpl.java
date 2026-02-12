package com.kh.ems.chat.model.service;

import org.springframework.stereotype.Service;
import com.kh.ems.chat.model.vo.ChatMessage;

@Service
public class ChatServiceImpl implements ChatService {

    @Override
    public ChatMessage getResponse(String message) {
        // AI 관련 부분은 일단 비워둠 (추후 구현 예정)
        // 현재는 통신 확인을 위한 단순 응답만 반환
        String aiResponse = "AI 응답 기능은 현재 준비 중입니다. 입력하신 메시지: " + message;

        return new ChatMessage("assistant", aiResponse);
    }
}
