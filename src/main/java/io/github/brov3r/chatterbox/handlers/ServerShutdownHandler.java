package io.github.brov3r.chatterbox.handlers;

import com.avrix.events.OnServerShutdownEvent;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.WebhookExecuteSpec;
import discord4j.rest.util.Color;
import io.github.brov3r.chatterbox.Main;

/**
 * Triggered when the server shuts down
 */
public class ServerShutdownHandler extends OnServerShutdownEvent {
    /**
     * Called Event Handling Method
     */
    @Override
    public void handleEvent() {
        String webhook = Main.getConfig().getString("chatWebHookURL");

        if (webhook.isEmpty() || webhook.equals("...")) return;

        Main.getDiscordAPI().sendWebhook(webhook, WebhookExecuteSpec.builder()
                .addEmbed(EmbedCreateSpec.builder()
                        .color(Color.RED)
                        .description(Main.getConfig().getString("messages.serverShutdown")).build()).build());
    }
}