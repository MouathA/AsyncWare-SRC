package com.nquantum.api.discord;

import net.arikia.dev.drpc.callbacks.*;
import net.arikia.dev.drpc.*;

public class ReadyEvent implements ReadyCallback
{
    public void apply(final DiscordUser discordUser) {
        System.out.println("manager");
    }
}
