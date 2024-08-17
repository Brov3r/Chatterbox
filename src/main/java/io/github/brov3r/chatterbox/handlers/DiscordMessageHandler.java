package io.github.brov3r.chatterbox.handlers;

import com.avrix.utils.ChatUtils;
import com.brov3r.discordapi.events.OnDiscordMessage;
import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.message.MessageCreateEvent;
import io.github.brov3r.chatterbox.Main;

/**
 * Triggered when a chat message arrives
 */
public class DiscordMessageHandler extends OnDiscordMessage {
    /**
     * Called when a message arrives in a discord chat (any one).
     *
     * @param messageEvent The {@link MessageCreateEvent} message event.
     */
    @Override
    public void handleEvent(MessageCreateEvent messageEvent) {
        String template = Main.getConfig().getString("gameChatTemplate");
        String chatID = Main.getConfig().getString("chatID");

        if (chatID.isEmpty() || chatID.equals("...")) return;

        if (!messageEvent.getMessage().getChannelId().equals(Snowflake.of(chatID))) return;

        if (messageEvent.getMember().get().isBot()) return;

        String author = messageEvent.getMember().get().getDisplayName();
        String message = messageEvent.getMessage().getContent().replaceAll("<[^>]*>", "***");

        if (message.startsWith("!")) return;

        System.out.printf("[#] Discord user '%s' sent a message: %s%n", author, message);

        try {
            ChatUtils.sendMessageToAll(template.replace("<NICKNAME>", author)
                    .replace("<MESSAGE>", message)
                    .replace("<SPACE>", ChatUtils.SPACE_SYMBOL));
        } catch (Exception ignore) {
        }
    }
}