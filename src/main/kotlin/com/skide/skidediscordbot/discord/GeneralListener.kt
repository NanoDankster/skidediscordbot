package com.skide.skidediscordbot.discord

import com.skide.skidediscordbot.gui.controller.MainController
import com.skide.skidediscordbot.util.Config
import com.skide.skidediscordbot.util.HelpCommandInfo
import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter
import java.util.*
import kotlin.collections.ArrayList

class GeneralListener(val api: JDA?) : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        val msgClean = event.message.contentDisplay
                .replace("\\s+",
                        " ")
        val msgSplit = msgClean.split(" ")
        val cmd = msgSplit[0]
        val args: Array<String>? = msgSplit.subList(1, msgSplit.lastIndex + 1).toTypedArray()
        if (!event.author.isBot) {
            if (cmd.equals(Config.PREFIX.data, true)) {
                if (args!!.isNotEmpty() && args[0].equals("github", true)) {
                    if (args.size >= 2 && args[1].equals("bot", true)) {
                        event.channel.sendMessage("The bot's GitHub repository: ${Config.BOT_GITHUB_REPO_LINK.data}").queue()
                    } else {
                        event.channel.sendMessage("The IDE's GitHub repository: ${Config.IDE_GITHUB_REPO_LINK.data}").queue()
                    }

                    if (MainController.staticOutputLevelSlider.value >= 1) {
                        MainController.output(MainController.State.INFO,
                                "Command \"${msgClean}\" by ${event.author.name}#${event.author.discriminator}")
                    }
                }
                if (args.isNotEmpty() &&
                        (args[0].equals("help", true) ||
                                args[0].equals("commands", true))) {
                    event.channel.sendMessage(HelpCommandInfo.getEmbed()).queue()

                    if (MainController.staticOutputLevelSlider.value >= 1) {
                        MainController.output(MainController.State.INFO,
                                "Command \"${msgClean}\" by ${event.author.name}#${event.author.discriminator}")
                    }
                }
            }
            if (msgClean.startsWith("hey skide", true) ||
                    msgClean.startsWith("hello skide", true) ||
                    msgClean.startsWith("heya skide", true) ||
                    msgClean.startsWith("hi skide", true)) {

                val msgs = ArrayList<String>()
                msgs.add("Hey ${event.author.asMention}!")
                msgs.add("Hi ${event.author.asMention}!")
                msgs.add("Hello ${event.author.asMention}!")
                msgs.add("Oh, hello ${event.author.asMention}.")
                msgs.add("OMG! Hello ${event.author.asMention}! <3")

                val chosen = msgs[Random().nextInt(msgs.size)]
                event.channel.sendMessage(chosen).queue()

                if (MainController.staticOutputLevelSlider.value >= 2) {
                    MainController.output(MainController.State.INFO,
                            "Message \"${msgClean}\" by ${event.author.name}#${event.author.discriminator}")
                }
            }
            if (msgClean.startsWith("skide", true) ||
                    msgClean.startsWith("sk-ide", true)) {
                val msgs = ArrayList<String>()
                msgs.add("WHAT DO YOU WANT ${event.author.asMention}!?")
                msgs.add("${event.author.asMention}, what do you want?")
                msgs.add("What can I do for you ${event.author.asMention}?")
                msgs.add("Please don't ping me, ${event.author.asMention}.")
                msgs.add("Sk-IDE HATES TO BE PINGED!")

                val chosen = msgs[Random().nextInt(msgs.size)]
                event.channel.sendMessage(chosen).queue()
            }


            if (MainController.staticOutputLevelSlider.value == 3.0) {
                MainController.output(MainController.State.INFO,
                        "${event.author.name}#${event.author.discriminator} @ ${event.channel.name} -> ${msgClean}")
            }
        }
    }
}