package pl.hypeproxy.commands;

import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.api.chat.*;
import pl.hypeproxy.*;
import java.util.*;
import net.md_5.bungee.api.*;

public class BungeeCordCommand extends Command
{
    public BungeeCordCommand() {
        super("hypeproxy", (String)null, new String[0]);
    }
    
    public static void register(final Plugin owningPlugin) {
        ProxyServer.getInstance().getPluginManager().registerCommand(owningPlugin, (Command)new BungeeCordCommand());
    }
    
    public void execute(final CommandSender sender, final String[] arguments) {
    	sender.sendMessage(TextComponent.fromLegacyText("§8» §7This server is running §bHypeProxy Lite §8(§7v2.6.1§8) §7Download HypeProxy on discord §3§nhttps://discord.gg/VthqwHPsqC"));
        }
    }
