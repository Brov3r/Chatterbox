package io.github.brov3r.chatterbox.handlers;

import com.avrix.events.OnPlayerFullyConnectedEvent;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.WebhookExecuteSpec;
import discord4j.rest.util.Color;
import io.github.brov3r.chatterbox.Main;
import zombie.core.raknet.UdpConnection;
import zombie.network.GameServer;
import zombie.network.ServerOptions;

import java.nio.ByteBuffer;

/**
 * Triggered when the player is fully connected to the server.
 */
public class PlayerJoinHandler extends OnPlayerFullyConnectedEvent {
    /**
     * Called Event Handling Method
     *
     * @param data             event-related data represented as a ByteBuffer
     * @param playerConnection active player connection
     * @param username         user name
     */
    @Override
    public void handleEvent(ByteBuffer data, UdpConnection playerConnection, String username) {
        String webhook = Main.getConfig().getString("chatWebHookURL");

        if (webhook.isEmpty() || webhook.equals("...")) return;

        Main.getDiscordAPI().sendWebhook(webhook, WebhookExecuteSpec.builder()
                .addEmbed(EmbedCreateSpec.builder()
                        .color(Color.LIGHT_SEA_GREEN)
                        .description(Main.getConfig().getString("messages.playerJoin")
                                .replace("<MAX_PLAYERS>", String.valueOf(ServerOptions.getInstance().getMaxPlayers()))
                                .replace("<CURRENT_PLAYERS>", String.valueOf(GameServer.getPlayers().size()))
                                .replace("<PLAYER>", playerConnection.username)).build()).build());
    }
}
