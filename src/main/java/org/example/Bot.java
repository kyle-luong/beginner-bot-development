package org.example;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.example.commands.CommandManager;
import org.example.listeners.EventListener;

import javax.security.auth.login.LoginException;

public class Bot {

    private final Dotenv config;
    private ShardManager shardManager;

    /**
     * Loads environment variables and builds the bot shard manager.
     * @throws LoginException occurs when the bot token is invalid.
     */
    public Bot() throws LoginException {
        // Load environment variables
        config = Dotenv.configure().load();
        String token = config.get("TOKEN");

        // Build shard manager
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("you win money"));
        builder.enableIntents(
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_PRESENCES,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_MESSAGE_REACTIONS,
                GatewayIntent.MESSAGE_CONTENT);
        // builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        // builder.setChunkingFilter(ChunkingFilter.ALL);
        // builder.enableCache(CacheFlag.ONLINE_STATUS, CacheFlag.ACTIVITY);
        shardManager = builder.build();

        // Register listeners
        shardManager.addEventListener(new EventListener(), new CommandManager());
    }

    /**
     * Retrieves the bot environment variables.
     * @return the DotEnv instance for the bot.
     */
    public Dotenv getConfig() {
        return config;
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public static void main(String[] args) {
        try {
            Bot bot = new Bot();
        } catch (LoginException e) {
            System.out.println("ERROR: Provided bot token is invalid.");
        }
    }
}