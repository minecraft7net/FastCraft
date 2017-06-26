package net.benwoodworth.fastcraft.core.dependencies.event

import net.benwoodworth.fastcraft.core.util.EventListener

/**
 * Dagger module for event dependencies.
 */
interface ModuleEvent {

    fun listenerItemCraft(): EventListener<EventItemCraft>

    fun listenerPlayerJoin(): EventListener<EventPlayerJoin>

    fun listenerPluginDisable(): EventListener<EventPluginDisable>

    fun listenerPluginEnable(): EventListener<EventPluginEnable>
}
