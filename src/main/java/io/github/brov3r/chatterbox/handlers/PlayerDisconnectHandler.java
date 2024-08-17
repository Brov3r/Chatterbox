package io.github.brov3r.chatterbox.handlers;

import com.avrix.events.OnPlayerDisconnectEvent;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.WebhookExecuteSpec;
import discord4j.rest.util.Color;
import io.github.brov3r.chatterbox.Main;
import zombie.characters.IsoPlayer;
import zombie.core.raknet.UdpConnection;
import zombie.network.GameServer;
import zombie.network.ServerOptions;

/**
 * Triggered when the server decided to disconnect from the player.
 */
public class PlayerDisconnectHandler extends OnPlayerDisconnectEvent {
    /**
     * Called Event Handling Method
     *
     * @param player           Object of the player who left.
     * @param playerConnection Connection of a player who has left the server.
     */
    @Override
    public void handleEvent(IsoPlayer player, UdpConnection playerConnection) {
        String webhook = Main.getConfig().getString("chatWebHookURL");

        if (webhook.isEmpty() || webhook.equals("...")) return;

        Main.getDiscordAPI().sendWebhook(webhook, WebhookExecuteSpec.builder()
                .addEmbed(EmbedCreateSpec.builder()
                        .color(Color.RUBY)
                        .description(Main.getConfig().getString("messages.playerLeave")
                                .replace("<MAX_PLAYERS>", String.valueOf(ServerOptions.getInstance().getMaxPlayers()))
                                .replace("<CURRENT_PLAYERS>", String.valueOf(GameServer.getPlayers().size()))
                                .replace("<PLAYER>", playerConnection.username)).build()).build());
    }
}
