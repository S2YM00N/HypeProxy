package pl.hypeproxy;

import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.config.*;
import java.io.*;
import java.util.*;
import net.md_5.bungee.event.*;
import pl.hypeproxy.commands.*;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.connection.*;

public class Main extends Plugin implements Listener
{
    Configuration config;
    File file;
    
    @Override
    public void onEnable() {
        getLogger().info("HypeProxy-lite plugin enabled.");
        BungeeCordCommand.register(this);
        getProxy().getPluginManager().registerListener(this, this);
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdir();
            }
            file = new File(getDataFolder(), "settings.yml");
            if (!file.exists()) {
                file.createNewFile();
            }
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            if (!config.contains("message")) {
                config.set("message", "&cYour account was created, please join again");
                try {
                    ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @EventHandler
    public void onPing(final ProxyPingEvent event) {
        final String ip = event.getConnection().getAddress().getAddress().getHostAddress();
        final List<String> h = (List<String>)this.config.getStringList("verified");
        if (!h.contains(ip)) {
            h.add(ip);
            this.config.set("verified", (Object)h);
            try {
                ConfigurationProvider.getProvider((Class)YamlConfiguration.class).save(this.config, this.file);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    @EventHandler
    public void onConnect(final ServerConnectEvent event) {
        final ProxiedPlayer player = event.getPlayer();
        final String ip = player.getAddress().getAddress().getHostAddress();
        final List<String> h = (List<String>)this.config.getStringList("verified");
        if (!h.contains(ip)) {
            event.setCancelled(true);
            player.disconnect((BaseComponent)new TextComponent(this.config.getString("message").replaceAll("&", "§")));
        }
    }
}
