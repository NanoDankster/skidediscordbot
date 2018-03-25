package com.skide.skidediscordbot.gui.controller

import com.skide.skidediscordbot.Main
import com.skide.skidediscordbot.util.CommandHandler
import com.skide.skidediscordbot.util.Config
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import java.net.URL
import java.util.*
import java.util.regex.Pattern
import kotlin.properties.Delegates


class MainController : Initializable {
    @FXML private lateinit var inputArea: TextArea
    @FXML private lateinit var channelTargetGroup: ToggleGroup
    @FXML private lateinit var chosenChannelRadio: RadioButton
    @FXML private lateinit var outputArea: TextArea
    @FXML private lateinit var outputLevelSlider: Slider
    /*
     * 1: Commands
     * 2: Commands and messages
     * 3: Commands and ALL messages
     */
    @FXML private lateinit var channelListView: ListView<String>
    @FXML private lateinit var allChannelsRadio: RadioButton

    companion object {
        var staticOutputArea: TextArea by Delegates.notNull()
        var staticInputArea: TextArea by Delegates.notNull()
        var staticOutputLevelSlider: Slider by Delegates.notNull()
        var staticChannelListView: ListView<String> by Delegates.notNull()

        fun output(logLevel: State, text: String) {
            if (logLevel != State.NONE) {
                staticOutputArea.appendText(logLevel.data + " " + text + "\n")
                return
            }
            staticOutputArea.appendText(text + "\n")
        }
    }


    @FXML fun clearOutputAction(event: ActionEvent) {
        outputArea.clear()
    }

    @FXML fun sendCommandAction(event: ActionEvent) {
        val cmdClean = inputArea.getText().replace("\\s+", " ")
        val cmdSplit = ArrayList<String>()
        val m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(cmdClean)
        while (m.find())
            cmdSplit.add(m.group(1))

        output(State.INFO, "Input command: ${cmdClean}")

        CommandHandler.handle(cmdSplit, allChannelsRadio.isSelected)

        inputArea.clear()
    }

    @FXML fun fileCloseAction(event: ActionEvent) {
        Main.end()
    }

    @FXML fun helpAboutAction(event: ActionEvent) {
        // TODO help -> about action
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        staticOutputArea = outputArea
        staticInputArea = inputArea
        staticOutputLevelSlider = outputLevelSlider
        staticChannelListView = channelListView
    }

    enum class State(val data: String?) {
        ERROR("[Error]"),
        WARNING("[Warning]"),
        INFO("[Info]"),
        NONE(null)
    }
}
