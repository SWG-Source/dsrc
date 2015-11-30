package script.systems.pet_tradein;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.callable;
import script.library.pet_lib;
import script.library.utils;
import script.library.chat;
import script.library.create;

public class pet_tradein extends script.base_script
{
    public pet_tradein()
    {
    }
    public static final String CREATURE_TRADEIN = "creature_tradein";
    public static final String TRADE_DATA = "datatables/dispenser/creature_trade_in.iff";
    public static final String CREATURE_TABLE = "datatables/mob/creatures.iff";
    public static final String CREATURE_NAME_FILE = "mob/creature_names";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnGiveItem(obj_id self, obj_id pet, obj_id player) throws InterruptedException
    {
        String oldPet = getStringObjVar(pet, "pet.creatureName");
        if (callable.hasAnyCallable(player))
        {
            string_id sysmessage = new string_id(CREATURE_TRADEIN, "store");
            sendSystemMessage(player, sysmessage);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            if (oldPet == null)
            {
                string_id sysmessage = new string_id(CREATURE_TRADEIN, "notacreature");
                sendSystemMessage(player, sysmessage);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                if (hasSkill(player, "outdoors_creaturehandler_novice") == true)
                {
                    string_id sysmessage = new string_id(CREATURE_TRADEIN, "cant");
                    sendSystemMessage(player, sysmessage);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    if (dataTableGetString(TRADE_DATA, oldPet, "trade") != null)
                    {
                        boolean isMount = false;
                        if (getIntObjVar(pet, "ai.pet.trainedMount") == 1)
                        {
                            isMount = true;
                        }
                        tradePet(pet, player, isMount);
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        string_id message = new string_id(CREATURE_TRADEIN, "invalid");
                        chat.chat(self, message);
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
    }
    public void tradePet(obj_id trade, obj_id player, boolean isMount) throws InterruptedException
    {
        String oldPet = getStringObjVar(trade, "pet.creatureName");
        String newPet = dataTableGetString(TRADE_DATA, oldPet, "trade");
        location spawnLoc = getLocation(player);
        spawnLoc.x += 2;
        spawnLoc.z += 2;
        String oldName = getName(trade);
        string_id oldRealName = new string_id(CREATURE_NAME_FILE, oldPet);
        string_id newRealName = new string_id(CREATURE_NAME_FILE, newPet);
        obj_id pet = create.createCreature(newPet, spawnLoc, true);
        pet_lib.makePet(pet, player);
        copyObjVar(trade, pet, "ai.pet.commands");
        destroyObject(trade);
        dictionary params = new dictionary();
        params.put("pet", pet);
        params.put("master", player);
        params.put("isMount", isMount);
        messageTo(pet, "handlePetTradeInStore", params, 3, false);
        if (oldName.equals(getString(oldRealName)) || oldName.equals(getString(oldRealName) + "(baby)") || oldName.equals(getString(oldRealName) + " (baby)"))
        {
            setName(pet, newRealName);
        }
        else 
        {
            setName(pet, oldName);
        }
    }
}
