package net.benwoodworth.fastcraft.core.dependencies.server

/**
 * Dagger module for server dependencies.
 */
interface ModuleServer {

    fun taskScheduler(): TaskScheduler
}