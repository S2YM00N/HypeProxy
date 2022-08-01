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
    
    public void onEnable() {
        BungeeCordCommand.register(this);
        this.getProxy().getPluginManager().registerListener((Plugin)this, (Listener)this);
        try {
            if (!this.getDataFolder().exists()) {
                this.getDataFolder().mkdir();
            }
            this.file = new File(this.getDataFolder().getPath(), "settings.yml");
            if (!this.file.exists()) {
                this.file.createNewFile();
            }
            this.config = ConfigurationProvider.getProvider((Class)YamlConfiguration.class).load(this.file);
            if (!this.config.contains("message")) {
                this.config.set("message", (Object)"§cYour account was created join again");
                try {
                    ConfigurationProvider.getProvider((Class)YamlConfiguration.class).save(this.config, this.file);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (IOException ex) {}
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
