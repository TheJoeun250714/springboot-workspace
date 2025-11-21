package edu.thejoeun.common.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * WebSocket 을 통한 알림 컨트롤러
 * STOMP 프로토콜을 사용한 실시간 양방향 통신
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/notify")
    @SendTo("/topic/notifications")
    public Map<String, Object> sendNotification(Map<String, Object> msg) {
        log.info("알림 메세지 수신 및 브로드캐스트 : {}", msg);
        return msg;
    }

    public void sendToUser(String username, Map<String, Object> notification) {
        simpMessagingTemplate.convertAndSendToUser(username,
                "/queue/notifications",
                notification);
        log.info("사용자 {}에게 개인 알림 전송 : {}", username, notification);
    }

    public void broadcastNotification(Map<String, Object> notification) {
        simpMessagingTemplate.convertAndSend("/topic/notifications", notification);
        log.info("모든 사용자에게 알림 브로드캐스트 : {}", notification);
    }
}










