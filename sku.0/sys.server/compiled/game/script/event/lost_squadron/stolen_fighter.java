package script.event.lost_squadron;

import script.ai.ai_combat;
import script.*;
import script.library.chat;
import script.library.colors;
import script.library.create;
import script.library.utils;

public class stolen_fighter extends script.base_script
{
    public stolen_fighter()
    {
    }
    public static final float LIFESPAN = 60 * 60 * 3;
    public static final float FIFTEEN_MINUTES = 60 * 15;
    public static final String[] THUG = 
    {
        "borvos_thug",
        "jabba_thug",
        "valarian_thug"
    };
    public static final String STF_FILE = "event/lost_squadron";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "event.lost_squadron.core_removed"))
        {
            setObjVar(self, "event.lost_squadron.core_removed", 0);
        }
        messageTo(self, "setCoreTemplate", null, 5, false);
        int coreSetTries = 0;
        utils.setScriptVar(self, "coreSetTries", coreSetTries);
        messageTo(self, "checkTimeLimit", null, 30, false);
        int coreRemoved = getIntObjVar(self, "event.lost_squadron.core_removed");
        utils.setScriptVar(self, "core_removed", coreRemoved);
        createTriggerVolume("fighterTriggerVolume", 30, true);
        return SCRIPT_CONTINUE;
    }
    public int setCoreTemplate(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "event.lost_squadron.type"))
        {
            int coreSetTries = utils.getIntScriptVar(self, "coreSetTries");
            if (coreSetTries > 4)
            {
                messageTo(self, "goDie", null, 1, false);
                CustomerServiceLog("EventPerk", "[Lost Squadron Event] WARNING: Spawner did not set core template in for at least two minutes. Spawn " + self);
            }
            messageTo(self, "setCoreTemplate", null, 15, false);
            coreSetTries++;
            utils.setScriptVar(self, "coreSetTries", coreSetTries);
            return SCRIPT_CONTINUE;
        }
        String spawntype = getStringObjVar(self, "event.lost_squadron.type");
        String coreTemplate = "";
        if (spawntype.equals("dath_north"))
        {
            coreTemplate = "object/tangible/loot/quest/lost_squadron_core_1.iff";
        }
        if (spawntype.equals("dath_south"))
        {
            coreTemplate = "object/tangible/loot/quest/lost_squadron_core_2.iff";
        }
        if (spawntype.equals("endor_north"))
        {
            coreTemplate = "object/tangible/loot/quest/lost_squadron_core_3.iff";
        }
        if (spawntype.equals("endor_south"))
        {
            coreTemplate = "object/tangible/loot/quest/lost_squadron_core_4.iff";
        }
        if (spawntype.equals("yavin_north"))
        {
            coreTemplate = "object/tangible/loot/quest/lost_squadron_core_5.iff";
        }
        if (spawntype.equals("yavin_south"))
        {
            coreTemplate = "object/tangible/loot/quest/lost_squadron_core_6.iff";
        }
        if (coreTemplate.equals("") || coreTemplate == null)
        {
            CustomerServiceLog("EventPerk", "[Lost Squadron Event] WARNING: Template for Nav Core invalid on fighter " + spawntype);
            setObjVar(self, "event.lost_squadron.core_removed", 1);
            utils.setScriptVar(self, "core_removed", 1);
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "event.lost_squadron.core_template", coreTemplate);
        return SCRIPT_CONTINUE;
    }
    public int checkTimeLimit(obj_id self, dictionary params) throws InterruptedException
    {
        float timeStamp = getFloatObjVar(self, "event.lost_squadron.timeStamp");
        float rightNow = getGameTime();
        float delta = rightNow - timeStamp;
        obj_id mom = getObjIdObjVar(self, "event.lost_squadron.mom");
        int coreRemoved = getIntObjVar(self, "event.lost_squadron.core_removed");
        String type = getStringObjVar(self, "event.lost_squadron.type");
        String style = getStringObjVar(self, "event.lost_squadron.style");
        params.put("fighter", self);
        if (delta > LIFESPAN)
        {
            CustomerServiceLog("EventPerk", "[Lost Squadron Event] Attempting to delete an expired fighter (" + style + "). Located at: " + type + ". Core removed status " + coreRemoved);
            messageTo(mom, "fighterRequestsDeletion", params, 1, false);
        }
        else 
        {
            messageTo(mom, "fighterStillAlive", params, 1, false);
        }
        messageTo(self, "checkTimeLimit", null, FIFTEEN_MINUTES, false);
        return SCRIPT_CONTINUE;
    }
    public int goDie(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id mom = getObjIdObjVar(self, "event.lost_squadron.mom");
        params.put("fighter", self);
        messageTo(mom, "fighterDeletedTimeOut", params, 30, false);
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int resetAmbushTimerTrigger(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "recently_triggered"))
        {
            utils.removeScriptVar(self, "recently_triggered");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        CustomerServiceLog("EventPerk", "[Lost Squadron Event] An expired fighter (" + self + ") has been deleted.");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int coreRemoved = utils.getIntScriptVar(self, "core_removed");
        int menu2 = mi.addRootMenu(menu_info_types.SERVER_MENU2, new string_id("event/lost_squadron", "mnu_inspect_vessel"));
        if (coreRemoved == 1 || utils.hasScriptVar(self, "core_being_removed"))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            int menu1 = mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id("event/lost_squadron", "mnu_remove_core"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        int coreRemoved = utils.getIntScriptVar(self, "core_removed");
        String coreTemplate = getStringObjVar(self, "event.lost_squadron.core_template");
        location here = getLocation(self);
        String type = getStringObjVar(self, "event.lost_squadron.type");
        if (item == menu_info_types.SERVER_MENU1)
        {
            if (coreRemoved == 1 || utils.hasScriptVar(self, "core_being_removed"))
            {
                sendSystemMessage(player, new string_id("event/lost_squadron", "sys_msg_no_core"));
                return SCRIPT_CONTINUE;
            }
            utils.setScriptVar(self, "core_being_removed", 1);
            obj_id playerInventory = utils.getInventoryContainer(player);
            obj_id coreLoot = createObject(coreTemplate, playerInventory, "");
            if (isIdValid(coreLoot) && exists(coreLoot))
            {
                String playerName = getName(player);
                utils.setScriptVar(self, "core_removed", 1);
                setObjVar(self, "event.lost_squadron.core_removed", 1);
                sendSystemMessage(player, new string_id("event/lost_squadron", "sys_msg_core_removed"));
                showFlyText(player, new string_id("event/lost_squadron", "fly_txt_core_removed"), 1.5f, colors.LIGHTGOLDENRODYELLOW);
                playMusic(player, "sound/music_combat_bfield_vict.snd");
                CustomerServiceLog("EventPerk", "[Lost Squadron Event] CORE REMOVED: (" + type + ") by player [" + playerName + "|" + player + " Located at: " + here);
            }
            else 
            {
                utils.removeScriptVar(self, "core_being_removed");
                sendSystemMessage(player, new string_id("event/lost_squadron", "sys_msg_full_inv"));
            }
        }
        if (item == menu_info_types.SERVER_MENU2)
        {
            if (coreRemoved == 1 || utils.hasScriptVar(self, "core_being_removed"))
            {
                sendSystemMessage(player, new string_id("event/lost_squadron", "sys_msg_inspect_no"));
                return SCRIPT_CONTINUE;
            }
            else 
            {
                sendSystemMessage(player, new string_id("event/lost_squadron", "sys_msg_inspect_yes"));
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id whoTriggeredMe) throws InterruptedException
    {
        if (!isPlayer(whoTriggeredMe) || isGod(whoTriggeredMe))
        {
            return SCRIPT_CONTINUE;
        }
        int coreRemoved = getIntObjVar(self, "event.lost_squadron.core_removed");
        if (coreRemoved == 1 || utils.hasScriptVar(self, "already_spawned") || utils.hasScriptVar(self, "recently_triggered"))
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "recently_triggered", 1);
        messageTo(self, "resetAmbushTimerTrigger", null, 300, false);
        int ambushChance = rand(1, 2);
        if (ambushChance == 1)
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "already_spawned", 1);
        int playerLevel = getLevel(whoTriggeredMe);
        int numThugs = rand(1, 3);
        int randThug = rand(0, 2);
        int wittyPhrase = rand(1, 3);
        location here = getLocation(self);
        here.x -= 18;
        for (int i = 0; i < numThugs; i++)
        {
            int levelMod = rand(-2, 2);
            obj_id thug = create.object(THUG[randThug], here);
            here.x += i * 7;
            setLevel(thug, playerLevel + levelMod);
            ai_combat.doAttack(thug, whoTriggeredMe);
            if (i == 0)
            {
                chat.chat(thug, chat.CHAT_SAY, chat.MOOD_CALLOUS, new string_id(STF_FILE, "thug_taunt_" + wittyPhrase));
            }
        }
        return SCRIPT_CONTINUE;
    }
}
