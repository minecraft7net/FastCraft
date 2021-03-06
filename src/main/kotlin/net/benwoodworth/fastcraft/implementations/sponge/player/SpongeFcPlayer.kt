package net.benwoodworth.fastcraft.implementations.sponge.player

import net.benwoodworth.fastcraft.dependencies.player.FcPlayer
import net.benwoodworth.fastcraft.dependencies.text.FcText
import net.benwoodworth.fastcraft.implementations.sponge.text.SpongeFcText
import net.benwoodworth.fastcraft.util.Adapter
import org.spongepowered.api.data.manipulator.mutable.DisplayNameData
import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.text.Text
import java.util.*

/**
 * Sponge implementation of [FcPlayer].
 */
class SpongeFcPlayer(
        override val base: Player
) : FcPlayer, Adapter<Player>() {

    override val username: String
        get() = base.name

    override var customName: String?
        get() = base.displayNameData.displayName().get().toPlain()
        set(value) {
            if (value == null) {
                base.remove(DisplayNameData::class.java)
            } else {
                base.displayNameData.displayName().set(Text.of(value))
            }
        }

    override val uuid: UUID
        get() = base.uniqueId

    override fun sendMessage(message: FcText) {
        base.sendMessage((message as SpongeFcText).base)
    }

    override fun hasPermission(permission: String): Boolean {
        return base.hasPermission(permission)
    }
}
