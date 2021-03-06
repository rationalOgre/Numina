package net.machinemuse.numina.basemod

import cpw.mods.fml.common.{FMLCommonHandler, Mod}
import cpw.mods.fml.common.network.NetworkMod
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.common.event.{FMLServerStartingEvent, FMLPostInitializationEvent, FMLInitializationEvent, FMLPreInitializationEvent}
import net.machinemuse.numina.general.MuseLogger
import net.machinemuse.numina.command.Commander
import net.machinemuse.numina.network.{NuminaPackets, MusePacketHandler}
import net.machinemuse.numina.recipe.JSONRecipeList

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 6:06 AM, 6/18/13
 */
@Mod(modid = "numina", modLanguage = "scala")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, tinyPacketHandler = classOf[MusePacketHandler])
object Numina {
  //@SidedProxy(clientSide = "net.machinemuse.anima.ClientProxy", serverSide = "net.machinemuse.anima.ServerProxy")
  var proxy: NuminaProxy = if (FMLCommonHandler.instance().getSide == Side.CLIENT) NuminaProxyClient else NuminaProxyServer
  var configDir: java.io.File = null

  @Mod.EventHandler def preinit(e: FMLPreInitializationEvent) {
    NuminaConfig.init(e)
    MuseLogger.logDebug("test")
    configDir = e.getModConfigurationDirectory
    //MinecraftForge.EVENT_BUS.register(PlayerTickHandler)
    //    MinecraftForge.EVENT_BUS.register(DeathEventHandler)
    //    NetworkRegistry.instance.registerGuiHandler(Numina, NuminaGuiHandler)
    proxy.PreInit()
  }

  @Mod.EventHandler def init(e: FMLInitializationEvent) {
    proxy.Init()
    NuminaPackets.init()
  }

  @Mod.EventHandler def postinit(e: FMLPostInitializationEvent) {
    proxy.PostInit()
  }

  @Mod.EventHandler def serverstart(e: FMLServerStartingEvent) {
    Commander.init()
  }
}
