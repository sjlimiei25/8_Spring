package com.kh.ems.chat.model.service;

import com.kh.ems.chat.model.vo.ChatMessage;

public interface ChatService {
    ChatMessage getResponse(String message);
}
