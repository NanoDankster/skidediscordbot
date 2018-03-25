package com.skide.skidediscordbot.util

import net.dv8tion.jda.core.EmbedBuilder
import net.dv8tion.jda.core.MessageBuilder
import net.dv8tion.jda.core.entities.Message

class HelpCommandInfo(val name: String, val smallDesc: String, val longDesc: String) {
    companion object {
        private val helpCommandInfos: ArrayList<HelpCommandInfo> = ArrayList()

        fun addCommand(hci: HelpCommandInfo) {
            helpCommandInfos.add(hci)
        }

        fun getEmbed(): Message {
            val embedBuilder: EmbedBuilder = EmbedBuilder()
                    .setTitle("Available commands")
                    .setDescription("These are the available commands. Type `ìde command <command>` to get more information.")

            helpCommandInfos.forEach {
                embedBuilder.addBlankField(false)
                embedBuilder.addField(it.name, it.smallDesc,false)
            }
            return MessageBuilder().setEmbed(embedBuilder.build()).build()
        }

        fun getCommandInfo(command: String): Message? {
            helpCommandInfos.forEach {
                if (it.name.equals(command)) {
                    val embedBuilder: EmbedBuilder = EmbedBuilder()
                            .setTitle("Information: ${it.name}")
                }
            }
            return null
        }
    }
}