package io.github.brov3r.chatterbox.handlers;

import com.avrix.events.OnCharacterDeathEvent;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.WebhookExecuteSpec;
import discord4j.rest.util.Color;
import io.github.brov3r.chatterbox.Main;
import zombie.characters.IsoGameCharacter;
import zombie.characters.IsoPlayer;

/**
 * Triggered when a character dies.
 */
public class PlayerDeathHandler extends OnCharacterDeathEvent {
    /**
     * Called Event Handling Method
     *
     * @param character The character who's about to die.
     */
    @Override
    public void handleEvent(IsoGameCharacter character) {
        String webhook = Main.getConfig().getString("chatWebHookURL");
        String message;

        if (webhook.isEmpty() || webhook.equals("...")) return;

        if (!(character instanceof IsoPlayer player)) return;

        if (character.getAttackedBy() instanceof IsoPlayer killer) {
            message = Main.getConfig().getString("messages.playerDeathBattle").replace("<PLAYER>", player.getDisplayName())
                    .replace("<ENEMY>", killer.getDisplayName());
        } else {
            message = Main.getConfig().getString("messages.playerDeath").replace("<PLAYER>", player.getDisplayName());
        }

        Main.getDiscordAPI().sendWebhook(webhook, WebhookExecuteSpec.builder()
                .addEmbed(EmbedCreateSpec.builder()
                        .color(Color.ORANGE)
                        .description(message).build()).build());

    }
}