package io.github.brov3r.chatterbox;

import com.avrix.events.EventManager;
import com.avrix.plugin.Metadata;
import com.avrix.plugin.Plugin;
import com.avrix.plugin.ServiceManager;
import com.avrix.utils.YamlFile;
import com.brov3r.discordapi.commands.CommandsManager;
import com.brov3r.discordapi.services.DiscordAPI;
import io.github.brov3r.chatterbox.commands.PlayersCommand;
import io.github.brov3r.chatterbox.handlers.*;

/**
 * Main entry point of the example plugin
 */
public class Main extends Plugin {
    private static Main instance;
    private static DiscordAPI discordAPI;

    /**
     * Constructs a new {@link Plugin} with the specified metadata.
     * Metadata is transferred when the plugin is loaded into the game context.
     *
     * @param metadata The {@link Metadata} associated with this plugin.
     */
    public Main(Metadata metadata) {
        super(metadata);
    }

    /**
     * Called when the plugin is initialized.
     * <p>
     * Implementing classes should override this method to provide the initialization logic.
     */
    @Override
    public void onInitialize() {
        instance = this;
        loadDefaultConfig();

        discordAPI = ServiceManager.getService(DiscordAPI.class);

        EventManager.addListener(new DiscordMessageHandler());
        EventManager.addListener(new GameChatMessageHandler());
        EventManager.addListener(new ServerInitHandler());
        EventManager.addListener(new ServerShutdownHandler());
        EventManager.addListener(new PlayerDisconnectHandler());
        EventManager.addListener(new PlayerJoinHandler());
        EventManager.addListener(new BanHandler());
        EventManager.addListener(new KickHandler());
        EventManager.addListener(new PlayerDeathHandler());

        CommandsManager.addCommand(new PlayersCommand());
    }

    /**
     * Getting the instance of the Main class
     *
     * @return instance of the Main class
     */
    public static Main getInstance() {
        return instance;
    }

    /**
     * Getting the instance of the DiscordAPI class
     *
     * @return instance of the DiscordAPI class
     */
    public static DiscordAPI getDiscordAPI() {
        return discordAPI;
    }

    /**
     * Getting the default config
     *
     * @return default config
     */
    public static YamlFile getConfig() {
        return getInstance().getDefaultConfig();
    }
}