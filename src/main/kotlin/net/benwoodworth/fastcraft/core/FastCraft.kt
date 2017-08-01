package net.benwoodworth.fastcraft.core

import net.benwoodworth.fastcraft.dependencies.event.EventPlayerJoin
import net.benwoodworth.fastcraft.dependencies.event.EventPluginDisable
import net.benwoodworth.fastcraft.dependencies.event.EventPluginEnable
import net.benwoodworth.fastcraft.dependencies.event.Listener
import net.benwoodworth.fastcraft.dependencies.gui.Gui
import net.benwoodworth.fastcraft.dependencies.gui.GuiButton
import net.benwoodworth.fastcraft.dependencies.item.Item
import net.benwoodworth.fastcraft.dependencies.server.Task
import net.benwoodworth.fastcraft.dependencies.text.Text
import net.benwoodworth.fastcraft.dependencies.text.TextColorRegistry
import net.benwoodworth.fastcraft.dependencies.text.TextStyle
import javax.inject.Inject
import javax.inject.Provider

/**
 * The core class of FastCraft.
 */
class FastCraft @Inject constructor(
        listenerPluginEnable: Listener<EventPluginEnable>,
        listenerPluginDisable: Listener<EventPluginDisable>,
        listenerPlayerJoin: Listener<EventPlayerJoin>,

        private val textBuilderProvider: Provider<Text.Builder>,
        private val guiBuilderProvider: Provider<Gui.Builder>,
        private val itemBuilderProvider: Provider<Item.Builder>,
        private val textColorRegistry: TextColorRegistry,
        private val taskBuilderProvider: Provider<Task.Builder>
) {

    init {
        listenerPluginEnable += this::onPluginEnable
        listenerPluginDisable += this::onPluginDisable
        listenerPlayerJoin += this::onPlayerJoin
    }

    fun onPluginEnable() {
        //logger.info("FastCraft core enabled")
    }

    fun onPluginDisable() {
        //logger.info("FastCraft core disabled")
    }

    fun onPlayerJoin(event: EventPlayerJoin) {
        event.player.sendMessage(textBuilderProvider.get()
                .text("FastCraft is up and running!")
                .build()
        )

        val guiText = textBuilderProvider.get()
                .text("FastCraft")
                .build()

        val gui = guiBuilderProvider.get()
                .height(6)
                .title(guiText)
                .build()

        val buttonItemText = textBuilderProvider.get()
                .text("This is a demo!")
                .textStyle(TextStyle(textColorRegistry.aqua, true))
                .build()

        val buttonItemLore = textBuilderProvider.get()
                .text("How does it look?")
                .textStyle(TextStyle(textColorRegistry.green, italic = true))
                .build()

        val buttonItem = itemBuilderProvider.get()
                .type("minecraft:diamond")
                .amount(42)
                .displayName(buttonItemText)
                .lore(buttonItemLore)
                .build()

        val button = GuiButton.Impl()
        button.item = buttonItem
        button.clickListener += { clickEvent ->
            val message = textBuilderProvider.get()
                    .text("You clicked a button in the GUI!!")
                    .build()

            clickEvent.player?.sendMessage(message)
        }

        gui.setButton(1, 1, button)
        taskBuilderProvider.get().run {
            gui.open(event.player)
        }
    }
}
