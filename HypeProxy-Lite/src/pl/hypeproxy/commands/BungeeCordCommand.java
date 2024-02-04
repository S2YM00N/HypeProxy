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
    	sender.sendMessage(TextComponent.fromLegacyText("§cHypeProxy §7» §fThis server is running §cHypeProxy-Lite§7(§f2.6.4-BULLETPROOF§7) §fdownload hypeproxy at: https://github.com/S2YM00N/HypeProxy"));
        }
    }
