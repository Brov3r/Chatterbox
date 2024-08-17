package io.github.brov3r.chatterbox.handlers;

import com.avrix.events.OnPlayerKickEvent;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.WebhookExecuteSpec;
import discord4j.rest.util.Color;
import io.github.brov3r.chatterbox.Main;
import zombie.core.raknet.UdpConnection;

/**
 * Triggered when the player kick command is called.
 */
public class KickHandler extends OnPlayerKickEvent {
    /**
     * Called Event Handling Method
     *
     * @param connection Connection of the player who was kicked.
     * @param adminName  Nickname of the administrator who kicked the player
     * @param reason     Reason for player kick.
     */
    @Override
    public void handleEvent(UdpConnection connection, String adminName, String reason) {
        String webhook = Main.getConfig().getString("chatWebHookURL");

        if (webhook.isEmpty() || webhook.equals("...")) return;

        Main.getDiscordAPI().sendWebhook(webhook, WebhookExecuteSpec.builder()
                .addEmbed(EmbedCreateSpec.builder()
                        .color(Color.RED)
                        .description(Main.getConfig().getString("messages.playerKick")
                                .replace("<ADMIN>", adminName)
                                .replace("<REASON>", reason)
                                .replace("<PLAYER>", connection.username)).build()).build());
    }
}
