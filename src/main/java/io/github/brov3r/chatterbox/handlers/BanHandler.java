package io.github.brov3r.chatterbox.handlers;

import com.avrix.events.OnPlayerBanEvent;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.WebhookExecuteSpec;
import discord4j.rest.util.Color;
import io.github.brov3r.chatterbox.Main;
import zombie.core.raknet.UdpConnection;

/**
 * Triggered when the player ban command is called.
 */
public class BanHandler extends OnPlayerBanEvent {
    /**
     * Called Event Handling Method
     *
     * @param connection Connecting a player who has been banned.
     * @param adminName  Nickname of the administrator who banned the player
     * @param reason     Reason for blocking the player.
     */
    @Override
    public void handleEvent(UdpConnection connection, String adminName, String reason) {
        String webhook = Main.getConfig().getString("chatWebHookURL");

        if (webhook.isEmpty() || webhook.equals("...")) return;

        Main.getDiscordAPI().sendWebhook(webhook, WebhookExecuteSpec.builder()
                .addEmbed(EmbedCreateSpec.builder()
                        .color(Color.RED)
                        .description(Main.getConfig().getString("messages.playerBan")
                                .replace("<ADMIN>", adminName)
                                .replace("<REASON>", reason)
                                .replace("<PLAYER>", connection.username)).build()).build());
    }
}
