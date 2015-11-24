package script.systems.storyteller;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.combat;
import script.library.create;
import script.library.storyteller;
import script.library.sui;
import script.library.trial;
import script.library.utils;

public class npc_controller extends script.base_script
{
    public npc_controller()
    {
    }
    public static final float MIN_NPC_DISTANCE = 6.0f;
    public static final String INVENTORY_SCRIPT = "systems.storyteller.npc_inventory_controller";
    public static final int OPEN_NPC_MENU = menu_info_types.SERVER_MENU1;
    public static final int SET_NPC_LEVEL_MENU = menu_info_types.SERVER_MENU2;
    public static final int DESTROY_NPC_MENU = menu_info_types.SERVER_MENU3;
    public static final int SET_NPC_BEHAVIOR_MENU = menu_info_types.SERVER_MENU4;
    public static final int SET_NPC_TO_FLAVOR_MENU = menu_info_types.SERVER_MENU5;
    public static final int REMOVE_EFFECT_MENU = menu_info_types.SERVER_MENU6;
    public static final int NPC_OPTIONS_ROOT_MENU = menu_info_types.SERVER_MENU7;
    public static final int EQUIP_UNEQUIP_WEAPON_MENU = menu_info_types.SERVER_MENU8;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "storytellerCreationTime", getGameTime());
        utils.setScriptVar(self, "storytellerOnAttachFired", true);
        checkBonusCleanupTime(self);
        npcSetup(self);
        setNpcCleanup(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleStorytellerPropInitialize", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int handleStorytellerPropInitialize(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "eventTeamCleaupOverride") && !utils.hasScriptVar(self, "storytellerOnAttachFired"))
        {
            if (hasObjVar(self, "storytellerCreationTime"))
            {
                int storytellerCreationTime = getIntObjVar(self, "storytellerCreationTime");
                if (getGameTime() >= storytellerCreationTime + getStandardCleanupTime(self))
                {
                    messageTo(self, "cleanupProp", null, 1, false);
                }
                else 
                {
                    npcSetup(self);
                    messageTo(self, "prepCleanupProp", null, getStandardCleanupTime(self) - (getGameTime() - storytellerCreationTime), false);
                }
            }
            else 
            {
                setObjVar(self, "storytellerCreationTime", getGameTime());
                npcSetup(self);
                setNpcCleanup(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id myStorytellerId = getObjIdObjVar(self, "storytellerid");
        if ((myStorytellerId == player || myStorytellerId == utils.getObjIdScriptVar(player, "storytellerAssistant") || isGod(player)) && !isIncapacitated(self))
        {
            int menuDestroyNpc = mi.addRootMenu(DESTROY_NPC_MENU, new string_id("storyteller", "destroy_npc"));
            int menuNpcOptions = mi.addRootMenu(NPC_OPTIONS_ROOT_MENU, new string_id("storyteller", "npc_options"));
            if (myStorytellerId == player || myStorytellerId == utils.getObjIdScriptVar(player, "storytellerAssistant"))
            {
                if (!isInvulnerable(self))
                {
                    mi.addSubMenu(menuNpcOptions, OPEN_NPC_MENU, new string_id("storyteller", "open_npc"));
                    mi.addSubMenu(menuNpcOptions, SET_NPC_LEVEL_MENU, new string_id("storyteller", "set_npc_level"));
                    mi.addSubMenu(menuNpcOptions, SET_NPC_TO_FLAVOR_MENU, new string_id("storyteller", "set_npc_flavor_npc"));
                }
                String defaultBehavaviorMenu = "set_npc_sentinel";
                int defaultBehavior = ai_lib.getDefaultCalmBehavior(self);
                if (defaultBehavior == ai_lib.BEHAVIOR_SENTINEL)
                {
                    defaultBehavaviorMenu = "set_npc_loiter";
                }
                mi.addSubMenu(menuNpcOptions, SET_NPC_BEHAVIOR_MENU, new string_id("storyteller", defaultBehavaviorMenu));
                if (aiHasPrimaryWeapon(self) || aiHasSecondaryWeapon(self))
                {
                    String defaultEquipWeaponMenu = "set_npc_weapon_equip";
                    if (aiUsingPrimaryWeapon(self) || aiUsingSecondaryWeapon(self))
                    {
                        defaultEquipWeaponMenu = "set_npc_weapon_unequip";
                    }
                    mi.addSubMenu(menuNpcOptions, EQUIP_UNEQUIP_WEAPON_MENU, new string_id("storyteller", defaultEquipWeaponMenu));
                }
            }
            if (hasObjVar(self, storyteller.EFFECT_ACTIVE_OBJVAR))
            {
                mi.addSubMenu(menuNpcOptions, REMOVE_EFFECT_MENU, new string_id("storyteller", "remove_persisted_effect"));
            }
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        sendDirtyObjectMenuNotification(self);
        obj_id myStorytellerId = getObjIdObjVar(self, "storytellerid");
        if ((myStorytellerId == player || myStorytellerId == utils.getObjIdScriptVar(player, "storytellerAssistant") || isGod(player)) && !isIncapacitated(self))
        {
            if (item == NPC_OPTIONS_ROOT_MENU)
            {
                sendSystemMessage(player, new string_id("storyteller", "npc_options_prompt"));
            }
            if (item == OPEN_NPC_MENU)
            {
                master_storyteller_npc_inventory(player, self);
            }
            else if (item == SET_NPC_LEVEL_MENU)
            {
                String prompt = utils.packStringId(new string_id("storyteller", "npc_combat_level_prompt"));
                sui.inputbox(self, player, prompt, "handleStorytellerNpcLevelSelect");
            }
            else if (item == DESTROY_NPC_MENU)
            {
                trial.cleanupObject(self);
            }
            else if (item == SET_NPC_BEHAVIOR_MENU)
            {
                int targetBehavoir = ai_lib.BEHAVIOR_SENTINEL;
                int defaultBehavior = ai_lib.getDefaultCalmBehavior(self);
                if (defaultBehavior == ai_lib.BEHAVIOR_SENTINEL)
                {
                    targetBehavoir = ai_lib.BEHAVIOR_LOITER;
                }
                ai_lib.setDefaultCalmBehavior(self, targetBehavoir);
            }
            else if (item == SET_NPC_TO_FLAVOR_MENU)
            {
                if (combat.isInCombat(self))
                {
                    sendSystemMessage(player, new string_id("storyteller", "npc_combat_in_combat"));
                }
                else 
                {
                    setInvulnerable(self, true);
                }
            }
            else if (item == EQUIP_UNEQUIP_WEAPON_MENU)
            {
                if (combat.isInCombat(self))
                {
                    sendSystemMessage(player, new string_id("storyteller", "npc_combat_in_combat"));
                }
                else 
                {
                    if (aiUsingPrimaryWeapon(self) || aiUsingSecondaryWeapon(self))
                    {
                        aiUnEquipWeapons(self);
                    }
                    else 
                    {
                        if (!equipWeapon(self))
                        {
                            sendSystemMessage(player, new string_id("storyteller", "npc_unable_to_equip"));
                        }
                    }
                }
            }
            else if (item == REMOVE_EFFECT_MENU)
            {
                storyteller.removeStorytellerPersistedEffect(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRemoveStorytellerPersistedEffect(obj_id self, dictionary params) throws InterruptedException
    {
        storyteller.removeStorytellerPersistedEffect(self);
        return SCRIPT_CONTINUE;
    }
    public int handleStorytellerNpcLevelSelect(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        String text = sui.getInputBoxText(params);
        int level = utils.stringToInt(text);
        if (level < 1 || level > 90)
        {
            sendSystemMessage(player, new string_id("storyteller", "npc_combat_level_invalid"));
        }
        else 
        {
            String creatureName = getCreatureName(self);
            dictionary creatureDict = utils.dataTableGetRow(create.CREATURE_TABLE, creatureName);
            if (creatureDict != null)
            {
                create.initializeCreature(self, creatureName, creatureDict, level);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void master_storyteller_npc_inventory(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id inventory = utils.getInventoryContainer(npc);
        if (isIdValid(inventory))
        {
            if (queueCommand(player, (1880585606), inventory, "", COMMAND_PRIORITY_DEFAULT))
            {
                dictionary params = new dictionary();
                params.put("npc", npc);
                messageTo(player, "handleEditingNpcInventory", params, 15, false);
            }
        }
    }
    public int handleEditingNpcInventory(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id npc = params.getObjId("npc");
        if (isIdValid(npc))
        {
            if (getDistance(self, npc) > MIN_NPC_DISTANCE)
            {
                obj_id npcInventory = utils.getInventoryContainer(npc);
                if (isIdValid(npcInventory))
                {
                    queueCommand(self, (822776054), npcInventory, "", COMMAND_PRIORITY_IMMEDIATE);
                }
            }
            else 
            {
                messageTo(self, "handleEditingNpcInventory", params, 15, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void npcSetup(obj_id self) throws InterruptedException
    {
        if (aiHasPrimaryWeapon(self))
        {
            aiEquipPrimaryWeapon(self);
        }
        else if (aiHasSecondaryWeapon(self))
        {
            aiEquipSecondaryWeapon(self);
        }
        clearNpcInventoryOwnership(self);
        return;
    }
    public void clearNpcInventoryOwnership(obj_id self) throws InterruptedException
    {
        obj_id owner = getOwner(self);
        if (isIdValid(owner))
        {
            obj_id inventory = utils.getInventoryContainer(self);
            if (isIdValid(inventory))
            {
                setOwner(inventory, obj_id.NULL_ID);
                setObjVar(inventory, "players_can_access_container", true);
                if (!hasScript(inventory, INVENTORY_SCRIPT))
                {
                    attachScript(inventory, INVENTORY_SCRIPT);
                }
            }
        }
        return;
    }
    public boolean equipWeapon(obj_id self) throws InterruptedException
    {
        if (aiHasPrimaryWeapon(self))
        {
            aiEquipPrimaryWeapon(self);
        }
        else if (aiHasSecondaryWeapon(self))
        {
            aiEquipSecondaryWeapon(self);
        }
        if (aiUsingPrimaryWeapon(self) || aiUsingSecondaryWeapon(self))
        {
            return true;
        }
        return false;
    }
    public void setNpcCleanup(obj_id self) throws InterruptedException
    {
        int cleanup_time = getStandardCleanupTime(self);
        messageTo(self, "prepCleanupProp", null, cleanup_time, false);
        return;
    }
    public int getStandardCleanupTime(obj_id prop_controller) throws InterruptedException
    {
        int cleanup_time = storyteller.DEFAULT_NPC_CLEANUP_TIME;
        String myCreatureName = getCreatureName(prop_controller);
        if (myCreatureName.equals("storyteller_vendor"))
        {
            cleanup_time = 1 * 60 * 60;
            return cleanup_time;
        }
        if (hasObjVar(prop_controller, "storytellerCleanUpTime"))
        {
            int cleanUpTime = getIntObjVar(prop_controller, "storytellerCleanUpTime");
            return cleanUpTime;
        }
        return cleanup_time;
    }
    public int cleanupProp(obj_id self, dictionary params) throws InterruptedException
    {
        trial.cleanupObject(self);
        return SCRIPT_CONTINUE;
    }
    public int prepCleanupProp(obj_id self, dictionary params) throws InterruptedException
    {
        storyteller.confirmCleanuptime(self);
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id myStorytellerId = getObjIdObjVar(self, "storytellerid");
        if ((myStorytellerId == player || myStorytellerId == utils.getObjIdScriptVar(player, "storytellerAssistant") || isGod(player)))
        {
            if (hasObjVar(self, "storytellerCreationTime"))
            {
                int storytellerCreationTime = getIntObjVar(self, "storytellerCreationTime");
                int timeRemaining = storytellerCreationTime + getStandardCleanupTime(self) - getGameTime();
                names[idx] = "storyteller_time_remaining";
                attribs[idx] = utils.formatTimeVerbose(timeRemaining);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (hasObjVar(self, "storytellerName"))
        {
            String storytellerName = getStringObjVar(self, "storytellerName");
            if (storytellerName != null && storytellerName.length() > 0)
            {
                names[idx] = "storyteller_npc_owner";
                attribs[idx] = storytellerName;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleBlueprintElevation(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        storyteller.handleBlueprintObjectElevation(self, params);
        return SCRIPT_CONTINUE;
    }
    public int aiCorpsePrepared(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "readyToLoot", true);
        return SCRIPT_CONTINUE;
    }
    public void checkBonusCleanupTime(obj_id prop_controller) throws InterruptedException
    {
        storyteller.calculateNpcBonusExistTime(prop_controller);
    }
    public int st_receivedCityResponse(obj_id self, dictionary params) throws InterruptedException
    {
        storyteller.setBonusExistTime(self);
        return SCRIPT_CONTINUE;
    }
}
