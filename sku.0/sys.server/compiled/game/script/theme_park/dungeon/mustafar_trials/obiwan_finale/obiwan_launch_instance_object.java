package script.theme_park.dungeon.mustafar_trials.obiwan_finale;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.groundquests;
import script.library.mustafar;
import script.library.pet_lib;
import script.library.prose;
import script.library.space_dungeon;
import script.library.space_dungeon_data;
import script.library.sui;
import script.library.utils;
import script.library.instance;

public class obiwan_launch_instance_object extends script.base_script
{
    public obiwan_launch_instance_object()
    {
    }
    public static final string_id SID_LAST_QUEST = new string_id(mustafar.STF_OBI_MSGS, "obiwan_quest_almost_complete");
    public static final string_id SID_LAUNCH = new string_id(mustafar.STF_OBI_MSGS, "obiwan_finale_launch");
    public static final string_id SID_EJECT = new string_id(mustafar.STF_OBI_MSGS, "obiwan_finale_eject");
    public static final string_id SID_LEAVE_YOUR_GROUP = new string_id(mustafar.STF_OBI_MSGS, "obi_leave_your_group");
    public static final String TRIGGER_VOLUME_OBI = "obiwan_interest_volume";
    public static final float OBI_INTEREST_RADIUS = 8f;
    public static final string_id SID_UNABLE_TO_FIND_DUNGEON = new string_id("dungeon/space_dungeon", "unable_to_find_dungeon");
    public static final string_id SID_NO_TICKET = new string_id("dungeon/space_dungeon", "no_ticket");
    public static final string_id SID_REQUEST_TRAVEL = new string_id("dungeon/space_dungeon", "request_travel");
    public static final string_id SID_REQUEST_TRAVEL_OUTSTANDIN = new string_id("dungeon/space_dungeon", "request_travel_outstanding");
    public static final string_id SID_LAIR_CRYSTAL = new string_id("travel/zone_transition", "lair_of_the_crystal");
    public static final string_id SID_NO_PERMISSION = new string_id("travel/zone_transition", "default_no_access");
    public static final boolean CONST_FLAG_DO_LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        launchObjectTriggerVolumeInitializer(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        launchObjectTriggerVolumeInitializer(self);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        debugLogging("OnTriggerVolumeEntered: ", " entered.");
        if (isPlayer(breacher) && !isIncapacitated(breacher))
        {
            if (volumeName.equals(TRIGGER_VOLUME_OBI))
            {
                if (readyForLair(breacher))
                {
                    if (mustafar.canCallObiwan(breacher, self))
                    {
                        spawnObi(breacher, self);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info item) throws InterruptedException
    {
        if (hasObjVar(player, "didMustafarCrystalLair"))
        {
            sendSystemMessage(player, mustafar.SID_ALREADY_COMPLETED);
            debugLogging("OnObjectMenuRequest: ", " player has already completed final quest. No call.");
            return SCRIPT_CONTINUE;
        }
        if (groundquests.hasCompletedQuest(player, "som_kenobi_main_quest_3_visible") || groundquests.hasCompletedQuest(player, "som_kenobi_main_quest_3_b_visible"))
        {
            sendSystemMessage(player, mustafar.SID_ALREADY_COMPLETED);
            debugLogging("OnObjectMenuRequest: ", " player has already completed final quest. No call.");
            setObjVar(player, "didMustafarCrystalLair", 1);
            return SCRIPT_CONTINUE;
        }
        if (isGod(player))
        {
            item.addRootMenu(menu_info_types.ITEM_USE, SID_LAUNCH);
            if (getDistance(player, self) > 6.0f)
            {
                return SCRIPT_CONTINUE;
            }
            utils.dismountRiderJetpackCheck(player);
            location locHere = getLocation(player);
            String scene = locHere.area;
            item.addRootMenu(menu_info_types.ITEM_USE, SID_LAIR_CRYSTAL);
        }
        else if (hasObjVar(player, "sawObiwanAtLauncher"))
        {
            item.addRootMenu(menu_info_types.ITEM_USE, SID_LAUNCH);
            if (getDistance(player, self) > 6.0f)
            {
                return SCRIPT_CONTINUE;
            }
            utils.dismountRiderJetpackCheck(player);
            location locHere = getLocation(player);
            String scene = locHere.area;
            item.addRootMenu(menu_info_types.ITEM_USE, SID_LAIR_CRYSTAL);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        LOG("space_dungeon", "theme_park.dungeon.mustafar_trials.obiwan_finale.launch_instance_object.OnObjectMenuSelect()");
        if (hasObjVar(player, "didMustafarCrystalLair"))
        {
            sendSystemMessage(player, mustafar.SID_ALREADY_COMPLETED);
            debugLogging("OnObjectMenuRequest: ", " player has already completed final quest. No call.");
            return SCRIPT_CONTINUE;
        }
        if (groundquests.hasCompletedQuest(player, "som_kenobi_main_quest_3_visible") || groundquests.hasCompletedQuest(player, "som_kenobi_main_quest_3_b_visible"))
        {
            sendSystemMessage(player, mustafar.SID_ALREADY_COMPLETED);
            debugLogging("OnObjectMenuRequest: ", " player has already completed final quest. No call.");
            setObjVar(player, "didMustafarCrystalLair", 1);
            return SCRIPT_CONTINUE;
        }
        else if (isGod(player) || hasObjVar(player, "sawObiwanAtLauncher"))
        {
            if (item == menu_info_types.ITEM_USE)
            {
                instance.requestInstanceMovement(player, "obiwan_crystal_cave");
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void debugLogging(String section, String message) throws InterruptedException
    {
        if (CONST_FLAG_DO_LOGGING)
        {
            LOG("debug/obiwan_launch_instance_object/" + section, message);
        }
    }
    public boolean readyForLair(obj_id player) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "som_kenobi_main_quest_3", "talkKenobi3") || groundquests.isTaskActive(player, "som_kenobi_main_quest_3_b", "talkKenobi3") || groundquests.isTaskActive(player, "som_kenobi_main_quest_3", "killSinistro") || groundquests.isTaskActive(player, "som_kenobi_main_quest_3_b", "killSinistro"))
        {
            return true;
        }
        return false;
    }
    public void confirmLaunch(obj_id player, obj_id terminal) throws InterruptedException
    {
        debugLogging("confirmLaunch: ", " entered.");
        if (hasObjVar(player, "didMustafarCrystalLair"))
        {
            sendSystemMessage(player, mustafar.SID_ALREADY_COMPLETED);
            debugLogging("OnObjectMenuRequest: ", " player has already completed final quest. No call.");
            return;
        }
        if (groundquests.hasCompletedQuest(player, "som_kenobi_main_quest_3_visible") || groundquests.hasCompletedQuest(player, "som_kenobi_main_quest_3_b_visible"))
        {
            sendSystemMessage(player, mustafar.SID_ALREADY_COMPLETED);
            debugLogging("OnObjectMenuRequest: ", " player has already completed final quest. No call.");
            setObjVar(player, "didMustafarCrystalLair", 1);
            return;
        }
        else if (isIdValid(getGroupObject(player)))
        {
            prose_package pp = prose.getPackage(SID_LEAVE_YOUR_GROUP, getEncodedName(player));
            prose.setTT(pp, player);
            sendSystemMessageProse(player, pp);
            return;
        }
        LOG("space_dungeon", "theme_park.dungeon.mustafar_trials.obiwan_finale.launch_instance_object.confirmLaunch()");
        String title = "@mustafar/obiwan_finale:begin_quest_title";
        string_id prompt = new string_id(mustafar.STF_OBI_MSGS, "begin_quest_prompt");
        prose_package pp = new prose_package();
        prose.setStringId(pp, prompt);
        prose.setTU(pp, getEncodedName(player));
        int pid = sui.msgbox(terminal, player, pp, sui.YES_NO, title, "msgDungeonLaunchConfirmed");
        utils.setScriptVar(terminal, "player", player);
        debugLogging("//*************// confirmLaunch: ", "////>>>> just fired off the SUI.");
        return;
    }
    public void confirmEject(obj_id player, obj_id terminal) throws InterruptedException
    {
        if (hasScript(player, "theme_park.dungeon.mustafar_trials.obiwan_finale.obiwan_event_player"))
        {
            LOG("space_dungeon", "theme_park.dungeon.mustafar_trials.obiwan_finale.launch_instance_object.confirmEject()");
            String title = "@mustafar/obiwan_finale:end_quest_title";
            string_id prompt = new string_id(mustafar.STF_OBI_MSGS, "end_quest_prompt");
            prose_package pp = new prose_package();
            prose.setStringId(pp, prompt);
            prose.setTU(pp, getEncodedName(player));
            int pid = sui.msgbox(terminal, player, pp, sui.YES_NO, title, "msgDungeonEjectConfirmed");
        }
        else 
        {
            warpPlayer(player, "mustafar", -670, 18, -137, null, -670, 18, -137);
        }
        return;
    }
    public void launchObjectTriggerVolumeInitializer(obj_id self) throws InterruptedException
    {
        if (!hasTriggerVolume(self, TRIGGER_VOLUME_OBI))
        {
            createTriggerVolume(TRIGGER_VOLUME_OBI, OBI_INTEREST_RADIUS, true);
        }
        else 
        {
            obj_id[] denizens = getTriggerVolumeContents(self, TRIGGER_VOLUME_OBI);
            for (int i = 0; i < denizens.length; i++)
            {
                if (isPlayer(denizens[i]) && !isIncapacitated(denizens[i]))
                {
                    if (mustafar.canCallObiwan(denizens[i], self))
                    {
                        spawnObi(denizens[i], self);
                        return;
                    }
                }
            }
        }
        return;
    }
    public void spawnObi(obj_id player, obj_id landmark) throws InterruptedException
    {
        obj_id obiwan = mustafar.callObiwan(player, landmark, true);
        prose_package pp = prose.getPackage(SID_LAST_QUEST);
        prose.setTT(pp, player);
        chat.chat(obiwan, player, pp);
        return;
    }
}
