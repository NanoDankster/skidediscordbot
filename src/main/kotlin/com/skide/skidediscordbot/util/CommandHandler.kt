package com.skide.skidediscordbot.util

import com.skide.skidediscordbot.Main
import com.skide.skidediscordbot.gui.controller.MainController.Companion.staticChannelListView

class CommandHandler {
    companion object {
        fun handle(inp: ArrayList<String>, allChannels: Boolean) {
            if (inp.isNotEmpty() && inp[0].startsWith("send", true)) {
                if (allChannels) {
                    if (inp.size >= 2 && inp[1].startsWith("\"")) {
                        Main.staticApi?.getGuildById(Config.GUILD_ID.data)?.textChannels?.forEach {
                            it.sendMessage(inp[1].replace("\"", "")).queue()
                        }
                    }
                } else {
                    if (inp.size >= 2 && inp[1].startsWith("\"")) {
                        val channel = staticChannelListView.focusModel.focusedItem.split(" >> ")[1]
                        Main.staticApi?.getTextChannelById(channel)
                                ?.sendMessage(inp[1].replace("\"", ""))?.queue()
                    }
                }
            }
        }
    }
}