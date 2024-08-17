package io.github.brov3r.chatterbox.handlers;

import com.avrix.events.OnChatMessageProcessedEvent;
import discord4j.core.spec.WebhookExecuteSpec;
import io.github.brov3r.chatterbox.Main;
import zombie.chat.ChatBase;
import zombie.chat.ChatMessage;
import zombie.network.chat.ChatType;

/**
 * Triggered when a player sends a chat message.
 */
public class GameChatMessageHandler extends OnChatMessageProcessedEvent {
    /**
     * Called Event Handling Method
     *
     * @param chatBase    Target chat data.
     * @param chatMessage Sent message details.
     */
    @Override
    public void handleEvent(ChatBase chatBase, ChatMessage chatMessage) {
        String webhook = Main.getConfig().getString("chatWebHookURL");

        String avatarULR = "https://api.dicebear.com/9.x/bottts/png?seed=" + chatMessage.getAuthor().trim().toLowerCase();

        if (webhook.isEmpty() || webhook.equals("...")) return;

        if (chatBase.getType() != ChatType.general) return;

        String message = chatMessage.getText().trim().replace("@", "");
        String urlRegex = "(https?://\\S+|www\\.\\S+)";
        message = message.replaceAll(urlRegex, "***");

        Main.getDiscordAPI().sendWebhook(webhook, WebhookExecuteSpec.builder()
                .avatarUrl(avatarULR)
                .username(chatMessage.getAuthor())
                .content(message).build());
    }
}