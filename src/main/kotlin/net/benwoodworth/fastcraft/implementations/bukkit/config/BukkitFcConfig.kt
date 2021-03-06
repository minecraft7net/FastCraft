package net.benwoodworth.fastcraft.implementations.bukkit.config

import net.benwoodworth.fastcraft.dependencies.config.FcConfig
import net.benwoodworth.fastcraft.dependencies.config.FcConfigSection
import net.benwoodworth.fastcraft.util.Adapter
import org.bukkit.configuration.file.YamlConfiguration

/**
 * Bukkit implementation of [FcConfig].
 */
class BukkitFcConfig(override val base: YamlConfiguration) :
        FcConfigSection by BukkitFcConfigSection(base),
        Adapter<YamlConfiguration>(),
        FcConfig {

    override var header: List<String>
        get() {
            var lines = base.options().header()?.split('\n') ?: emptyList()

            if (lines.takeIf { it.isNotEmpty() }?.last() == "") {
                lines = lines.take(lines.size - 1)
            }

            return lines.map {
                when {
                    it.isNotEmpty() && it[0] == ' ' -> it.substring(1)
                    else -> it
                }
            }
        }
        set(value) {
            base.options().header(value
                    .takeUnless { it.isEmpty() }
                    ?.map { " $it" }
                    ?.joinToString("\n")
            )
        }
}
