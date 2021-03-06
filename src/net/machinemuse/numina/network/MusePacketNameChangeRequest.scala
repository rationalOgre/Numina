package net.machinemuse.numina.network

import java.io.DataInputStream
import cpw.mods.fml.common.network.Player
import net.minecraft.client.entity.EntityClientPlayerMP
import net.machinemuse.numina.command.NicknameMap
import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.minecraft.entity.player.EntityPlayer
import net.machinemuse.numina.scala.OptionCast

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 8:50 PM, 9/6/13
 */
object MusePacketNameChangeRequest extends MusePackager {
  def read(d: DataInputStream, p: Player) = {
    val username = readString(d)
    val newnick = readString(d)
    new MusePacketNameChangeRequest(p, username, newnick, 0)
  }
}

class MusePacketNameChangeRequest(player: Player, username: String, newnick: String, entityID: Int) extends MusePacket(player) {
  override val packager = MusePacketNameChangeRequest

  override def write() {
    writeString(username)
    writeString(newnick)
  }

  @SideOnly(Side.CLIENT)
  override def handleClient(player: EntityClientPlayerMP) {
    NicknameMap.removeName(username)
    NicknameMap.putName(username, newnick)

    OptionCast[EntityPlayer](player.worldObj.getEntityByID(entityID)) map {
      t => t.refreshDisplayName()
    }
  }
}
