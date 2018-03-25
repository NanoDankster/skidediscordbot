package com.skide.skidediscordbot

import com.skide.skidediscordbot.discord.GeneralListener
import com.skide.skidediscordbot.gui.controller.MainController
import com.skide.skidediscordbot.util.Config
import com.skide.skidediscordbot.util.HelpCommandInfo
import javafx.application.Application
import javafx.application.Platform
import javafx.concurrent.Task
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import net.dv8tion.jda.core.AccountType
import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.JDABuilder
import java.io.BufferedReader
import java.io.InputStream

class Main : Application() {

    private var api: JDA? = null

    companion object {
        var staticApi: JDA? = null

        fun end() {
            staticApi?.shutdown()
            Platform.exit()
        }
    }

    override fun start(primaryStage: Stage) {
        val stylesheetRes = this::class.java.classLoader.getResource("modena_dark.css").toExternalForm()
        val parentRes = this::class.java.classLoader.getResource("main_ui.fxml")
        val iconRes = this::class.java.classLoader.getResourceAsStream("skide.png")
        if (parentRes != null && stylesheetRes != null && iconRes != null) {
            val parent: Parent = FXMLLoader.load(parentRes)
            val icon = Image(iconRes)
            primaryStage.scene = Scene(parent)
            primaryStage.scene.stylesheets.add(stylesheetRes)
            primaryStage.isResizable = false
            primaryStage.icons.add(icon)
            primaryStage.title = "Sk-IDE Discord Bot Interface"
            primaryStage.show()
            loadBot()
        }
    }


    private fun loadBot() {
        val tkInputStream: InputStream? = this::class.java.classLoader.getResourceAsStream("token.txt")
        if (tkInputStream != null) {
            val tkBuffRead: BufferedReader? = tkInputStream.bufferedReader()
            val tk: String? = tkBuffRead?.readLine()
            val task = object : Task<Void>() {
                public override fun call(): Void? {
                    api = JDABuilder(AccountType.BOT)
                            .setToken(tk)
                            .buildBlocking()
                    api?.addEventListener(GeneralListener(api))
                    return null
                }
            }
            task.setOnSucceeded {
                staticApi = api
                registerHelpCommandInfos()
                api?.getGuildById(Config.GUILD_ID.data)?.textChannels?.forEach {
                    MainController.staticChannelListView.items.add(it.name + " >> " + it.id)
                    MainController.staticChannelListView
                            .scrollTo(MainController.staticChannelListView.items.size -1)
                }
            }
            val thread = Thread(task)
            thread.isDaemon = true
            thread.start()
        }
    }
}

fun main(args: Array<String>) {
    Application.launch(Main::class.java, *args)
}

fun registerHelpCommandInfos() {
    HelpCommandInfo.addCommand(HelpCommandInfo(
            "ide (help|commands)",
            "Sends the current commands list.",
            "Commands are registered manually."))
    HelpCommandInfo.addCommand(HelpCommandInfo(
            "(hey|hello|hi|heya) skide",
            "Who doesn't like to talk to a bot?",
            "A nice message command. Made for fun :)"))
    HelpCommandInfo.addCommand(HelpCommandInfo(
            "ide github [bot]",
            "Sends you the GitHub repository links.",
            "Sends the GitHub links to the channel. The links are set manually."))
}