package net.benwoodworth.fastcraft.implementations.bukkit.config

import net.benwoodworth.fastcraft.dependencies.config.FcConfig
import net.benwoodworth.fastcraft.dependencies.config.FcConfigManager
import org.bukkit.configuration.file.YamlConfiguration
import java.nio.file.Path

/**
 * Bukkit implementation of [FcConfigManager].
 */
class BukkitFcConfigManager : FcConfigManager {

    override fun getEmptyConfig(): FcConfig {
        return BukkitFcConfig(YamlConfiguration())
    }

    override fun loadConfig(path: Path): FcConfig {
        return BukkitFcConfig(YamlConfiguration.loadConfiguration(path.toFile()))
    }

    override fun saveConfig(config: FcConfig, path: Path) {
        (config as BukkitFcConfig).base.save(path.toFile())
    }

    override fun getFileExtension() = ".yml"
}
