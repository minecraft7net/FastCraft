package net.benwoodworth.fastcraft.implementations.sponge.event

import net.benwoodworth.fastcraft.dependencies.event.FcEventPluginDisable
import net.benwoodworth.fastcraft.util.Adapter
import org.spongepowered.api.event.game.state.GameStoppingEvent

/**
 * Sponge implementation of [FcEventPluginDisable].
 */
class SpongeFcEventPluginDisable(
        override val base: GameStoppingEvent
) : FcEventPluginDisable, Adapter<GameStoppingEvent>()
