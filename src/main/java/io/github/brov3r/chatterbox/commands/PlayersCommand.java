package io.github.brov3r.chatterbox.commands;

import com.avrix.utils.PlayerUtils;
import com.brov3r.discordapi.commands.Command;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import io.github.brov3r.chatterbox.Main;
import zombie.characters.IsoPlayer;
import zombie.commands.PlayerType;
import zombie.core.raknet.UdpConnection;
import zombie.network.GameServer;
import zombie.network.ServerOptions;

/**
 * Command to display the list of players on the server.
 * The team displays a list of players with numbering.
 */
public class PlayersCommand extends Command {
    /**
     * Creates a "players" command with a description.
     */
    public PlayersCommand() {
        super("players", Main.getConfig().getString("commands.players.description"));
    }

    /**
     * Executes a command by displaying a numbered list of players.
     *
     * @param event   The event that generates the message that triggers the execution of the command.
     * @param strings Command arguments.
     * @return {@code true} if the command succeeds, {@code false} otherwise.
     */
    @Override
    public boolean execute(MessageCreateEvent event, String[] strings) {
        StringBuilder players = new StringBuilder();
        int playerNumber = 1;

        if (GameServer.udpEngine != null) {
            for (IsoPlayer player : GameServer.getPlayers()) {
                UdpConnection connection = PlayerUtils.getUdpConnectionByPlayer(player);

                players.append(playerNumber).append(". ").append(player.getDisplayName());
                if (connection != null) {
                    String access = PlayerType.toString(connection.accessLevel);
                    players.append(" - ");
                    players.append(access.isEmpty() ? "Player" : capitalize(access));
                }
                players.append("\n");
                playerNumber++;
            }

            if (!players.isEmpty()) {
                players.setLength(players.length() - 1);
            }
        }

        EmbedCreateSpec embedSpec = EmbedCreateSpec.builder()
                .title(Main.getConfig().getString("commands.players.title")
                        .replace("<MAX_PLAYERS>", String.valueOf(ServerOptions.getInstance().getMaxPlayers()))
                        .replace("<CURRENT_PLAYERS>", String.valueOf(GameServer.udpEngine == null ? 0 : GameServer.getPlayers().size())))
                .description(players.isEmpty() ? Main.getConfig().getString("commands.players.noPlayers") : players.toString())
                .color(Color.LIGHT_SEA_GREEN)
                .build();

        event.getMessage().getChannel()
                .flatMap(channel -> channel.createMessage(embedSpec).withMessageReference(event.getMessage().getId()))
                .subscribe();

        return true;
    }

    /**
     * Converts the first letter of the string to a capital letter, leaving the rest of the letters as they were.
     *
     * @param text The text to be converted.
     * @return Text with a capital first letter.
     */
    public static String capitalize(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}