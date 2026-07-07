package com.nyan.everybagel.blocks.entities.actions;

import com.nyan.everybagel.blocks.MillstoneBlock;
import com.nyan.everybagel.blocks.entities.MillstoneBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class MillstoneActions {
    public static void debug(MillstoneBlockEntity be, Player player) {
        player.displayClientMessage(Component.literal(be.toString()), false);
    }


}
