package com.daviddevelops.pickaxelevels.EventManager;

import com.daviddevelops.pickaxelevels.Enchants.CustomEnchant;
import com.daviddevelops.pickaxelevels.Enchants.EnchantManager;
import com.daviddevelops.pickaxelevels.Pickaxe;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class EventManager implements Listener {
    ArrayList<Material> PK = new ArrayList<Material>();
    public EventManager(){
        PK.add(Material.DIAMOND_PICKAXE);
        PK.add(Material.IRON_PICKAXE);
        PK.add(Material.GOLDEN_PICKAXE);
        PK.add(Material.NETHERITE_PICKAXE);
    }

    @EventHandler
    public void onPickaxeBlockBreak(BlockBreakEvent event){
        if(isPickaxe(event.getPlayer())){
            //Get NBT Tag, Check if  PickaxeManager contains this pickaxe. If not create a new one and add it with the NBTs Data.
            //If it does contain one, Update PickaxeManagers data and items NBT Data.
            ItemStack pickaxeI = event.getPlayer().getItemInHand();
            net.minecraft.world.item.ItemStack pickaxeN = CraftItemStack.asNMSCopy(pickaxeI);
            CompoundTag pickaxeC = (pickaxeN.hasTag()) ? pickaxeN.getTag() : new CompoundTag();

            //Trigger enchantments
            for (CustomEnchant C : EnchantManager.getInstance().getEnchantments(event.getEventName())) {
                C.perform(event.getPlayer(), event.getBlock());
            }
        }
    }


    public boolean isPickaxe(Player p){
        if(PK.contains(p.getItemInHand().getType())){

            //Creating our pickaxe variables.

            ItemStack pickaxeI = p.getItemInHand();
            net.minecraft.world.item.ItemStack pickaxeN = CraftItemStack.asNMSCopy(pickaxeI);
            CompoundTag pickaxeC = (pickaxeN.hasTag()) ? pickaxeN.getTag() : new CompoundTag();

            if(pickaxeC.contains("BlocksBroken")){
                p.setItemInHand(updateBlocksBroken(pickaxeI, pickaxeN, pickaxeC));
                return true;
            }
            p.setItemInHand(setBlocksBroken(pickaxeI, pickaxeN, pickaxeC));
        }
        return false;
    }

    //Move all of this to the 'pickaxe' class. Which is filled in with data when dealing with calculations before being nulled.

    private ItemStack updateBlocksBroken(ItemStack pickaxeI, net.minecraft.world.item.ItemStack pickaxeN, CompoundTag pickaxeC) {
        Tag tag = pickaxeC.get("BlocksBroken");
        int blocksBroken = Integer.parseInt(tag.getAsString());
        blocksBroken++;

        //Increase XP
        int XP =+ Integer.parseInt(pickaxeC.get("PickaxeExperience").getAsString());
        XP++;

        //Check for levelup
        int Lvl =+ Integer.parseInt(pickaxeC.get("PickaxeLevel").getAsString());
        if(XP >= 10){
            Lvl++;
            System.out.println(Lvl);
            XP = 0;
        }
        pickaxeC.putString("BlocksBroken", String.valueOf(blocksBroken));
        pickaxeC.putString("PickaxeExperience", String.valueOf(XP));
        pickaxeC.putString("PickaxeLevel", String.valueOf(Lvl));
        pickaxeN.setTag(pickaxeC);
        pickaxeI = CraftItemStack.asBukkitCopy(pickaxeN);
        return pickaxeI;
    }

    private ItemStack setBlocksBroken(ItemStack pickaxeI, net.minecraft.world.item.ItemStack pickaxeN, CompoundTag pickaxeC){
        pickaxeC.putString("BlocksBroken", "1");
        pickaxeC.putString("PickaxeExperience", "1");
        pickaxeC.putString("PickaxeLevel", "1");
        pickaxeN.setTag(pickaxeC);
        pickaxeI = CraftItemStack.asBukkitCopy(pickaxeN);
        return pickaxeI;
    }


}
