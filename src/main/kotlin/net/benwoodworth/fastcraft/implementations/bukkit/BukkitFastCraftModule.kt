package net.benwoodworth.fastcraft.implementations.bukkit

import dagger.Module
import dagger.Provides
import net.benwoodworth.fastcraft.dependencies.api.Listener
import net.benwoodworth.fastcraft.dependencies.api.gui.GuiFactory
import net.benwoodworth.fastcraft.dependencies.api.item.ItemBuilder
import net.benwoodworth.fastcraft.dependencies.api.item.ItemTypeFactory
import net.benwoodworth.fastcraft.dependencies.api.player.PlayerProvider
import net.benwoodworth.fastcraft.dependencies.api.text.TextBuilder
import net.benwoodworth.fastcraft.dependencies.api.text.TextColorRegistry
import net.benwoodworth.fastcraft.dependencies.config.ConfigManager
import net.benwoodworth.fastcraft.dependencies.event.EventPlayerJoin
import net.benwoodworth.fastcraft.dependencies.event.EventPluginDisable
import net.benwoodworth.fastcraft.dependencies.event.EventPluginEnable
import net.benwoodworth.fastcraft.dependencies.recipe.RecipeProvider
import net.benwoodworth.fastcraft.dependencies.server.PluginRegistry
import net.benwoodworth.fastcraft.dependencies.server.TaskBuilder
import net.benwoodworth.fastcraft.implementations.bukkit.api.gui.BukkitGuiFactory
import net.benwoodworth.fastcraft.implementations.bukkit.api.item.BukkitItemBuilder
import net.benwoodworth.fastcraft.implementations.bukkit.api.item.BukkitItemTypeFactory
import net.benwoodworth.fastcraft.implementations.bukkit.api.player.BukkitPlayerProvider
import net.benwoodworth.fastcraft.implementations.bukkit.api.text.BukkitTextBuilder
import net.benwoodworth.fastcraft.implementations.bukkit.api.text.BukkitTextColorRegistry
import net.benwoodworth.fastcraft.implementations.bukkit.config.BukkitConfigManager
import net.benwoodworth.fastcraft.implementations.bukkit.event.BukkitEventPlayerJoin
import net.benwoodworth.fastcraft.implementations.bukkit.event.BukkitEventPluginDisable
import net.benwoodworth.fastcraft.implementations.bukkit.event.BukkitEventPluginEnable
import net.benwoodworth.fastcraft.implementations.bukkit.recipe.BukkitRecipeProvider
import net.benwoodworth.fastcraft.implementations.bukkit.server.BukkitPluginRegistry
import net.benwoodworth.fastcraft.implementations.bukkit.server.BukkitTaskBuilder
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.server.PluginDisableEvent
import org.bukkit.event.server.PluginEnableEvent
import java.util.logging.Level
import javax.inject.Singleton

@Module
class BukkitFastCraftModule(
        private val plugin: BukkitFastCraft
) {

    @Provides
    @Singleton
    fun plugin(): BukkitFastCraft = plugin

    @Provides
    @Singleton
    fun bukkitApiVersion(): BukkitApiVersion {
        val versionStr = Bukkit.getBukkitVersion()
        return BukkitApiVersion.parse(versionStr) ?: run {
            plugin.logger.log(Level.SEVERE, "Unsupported Bukkit API version: $versionStr. Assuming latest API.")
            BukkitApiVersion(1000)
        }
    }

    @Provides
    @Singleton
    fun configManager(dep: BukkitConfigManager): ConfigManager = dep

    @Provides
    @Singleton
    fun listenerPlayerJoin(): Listener<EventPlayerJoin> {
        return Listener<EventPlayerJoin>().also { listener ->
            Bukkit.getPluginManager().registerEvents(
                    object : org.bukkit.event.Listener {
                        @EventHandler
                        @Suppress("UNUSED")
                        fun onPlayerJoin(event: PlayerJoinEvent) {
                            listener.notifyHandlers(BukkitEventPlayerJoin(event))
                        }
                    },
                    plugin
            )
        }
    }

    @Provides
    @Singleton
    fun listenerPluginDisable(): Listener<EventPluginDisable> {
        return Listener<EventPluginDisable>().also { listener ->
            Bukkit.getPluginManager().registerEvents(
                    object : org.bukkit.event.Listener {
                        @EventHandler
                        @Suppress("UNUSED")
                        fun onPluginDisable(event: PluginDisableEvent) {
                            listener.notifyHandlers(BukkitEventPluginDisable(event))
                        }
                    },
                    plugin
            )
        }
    }

    @Provides
    @Singleton
    fun listenerPluginEnable(): Listener<EventPluginEnable> {
        return Listener<EventPluginEnable>().also { listener ->
            Bukkit.getPluginManager().registerEvents(
                    object : org.bukkit.event.Listener {
                        @EventHandler
                        @Suppress("UNUSED")
                        fun onPluginEnable(event: PluginEnableEvent) {
                            listener.notifyHandlers(BukkitEventPluginEnable(event))
                        }
                    },
                    plugin
            )
        }
    }

    @Provides
    fun guiBuilder(dep: BukkitGuiFactory): GuiFactory = dep

    @Provides
    fun itemBuilder(dep: BukkitItemBuilder): ItemBuilder = dep

    @Provides
    fun itemTypeFactory(dep: BukkitItemTypeFactory): ItemTypeFactory = dep

    @Provides
    @Singleton
    fun recipeProvider(dep: BukkitRecipeProvider): RecipeProvider = dep

    @Provides
    @Singleton
    fun playerProvider(dep: BukkitPlayerProvider): PlayerProvider = dep

    @Provides
    fun taskBuilder(dep: BukkitTaskBuilder): TaskBuilder = dep

    @Provides
    @Singleton
    fun pluginProvider(dep: BukkitPluginRegistry): PluginRegistry = dep

    @Provides
    fun textBuilder(dep: BukkitTextBuilder): TextBuilder = dep

    @Provides
    @Singleton
    fun textColorRegistry(dep: BukkitTextColorRegistry): TextColorRegistry = dep
}