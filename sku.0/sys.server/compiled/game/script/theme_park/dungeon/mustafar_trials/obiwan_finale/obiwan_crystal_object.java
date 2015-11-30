package script.theme_park.dungeon.mustafar_trials.obiwan_finale;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.anims;
import script.library.buff;
import script.library.chat;
import script.library.mustafar;
import script.library.prose;
import script.library.sui;
import script.library.utils;

public class obiwan_crystal_object extends script.base_script
{
    public obiwan_crystal_object()
    {
    }
    public static final string_id SID_USE_CRYSTAL = new string_id(mustafar.STF_OBI_MSGS, "obiwan_finale_use_crystal");
    public static final string_id SID_DESTROY_CRYSTAL = new string_id(mustafar.STF_OBI_MSGS, "obiwan_finale_destroy_crystal");
    public static final string_id SID_TAKE_CRYSTAL = new string_id(mustafar.STF_OBI_MSGS, "obiwan_finale_take_crystal");
    public static final boolean CONST_FLAG_DO_LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        debugLogging("//***// OnAttach: ", "////>>>> spawned crystal.");
        obj_id dungeon = getTopMostContainer(self);
        if (!isIdValid(dungeon))
        {
            debugLogging("//***// crystal object OnAttach: ", "////>>>> dungeon obj_id is invalid. BROKEN");
        }
        utils.setScriptVar(dungeon, "crystal", self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        debugLogging("OnObjectMenuRequest: ", " entered.");
        if (utils.hasScriptVar(player, "readyToUseCrystal"))
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_USE_CRYSTAL);
        }
        else if (utils.hasScriptVar(player, "dealWithCrystal"))
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_DESTROY_CRYSTAL);
            mi.addRootMenu(menu_info_types.SERVER_MENU2, SID_TAKE_CRYSTAL);
        }
        else if (hasObjVar(self, "drainedCrystal"))
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_USE_CRYSTAL);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        debugLogging("OnObjectMenuSelect: ", " entered.");
        if (utils.hasScriptVar(player, "readyToUseCrystal"))
        {
            if (item == menu_info_types.SERVER_MENU1)
            {
                theBigCrystalBuff(player, self);
            }
        }
        else if (utils.hasScriptVar(player, "dealWithCrystal"))
        {
            if (item == menu_info_types.SERVER_MENU1)
            {
                debugLogging("OnObjectMenuRequest: ", " selected 'destroy the crystal'.");
                destroyTheCrystal(player, self);
            }
            else if (item == menu_info_types.SERVER_MENU2)
            {
                debugLogging("OnObjectMenuRequest: ", " selected 'take the Crystal'.");
                takeTheCrystal(player, self);
            }
        }
        else if (hasObjVar(self, "drainedCrystal"))
        {
            if (item == menu_info_types.SERVER_MENU1)
            {
                drainedCrystalBuff(player, self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void debugLogging(String section, String message) throws InterruptedException
    {
        if (CONST_FLAG_DO_LOGGING)
        {
            LOG("debug/obiwan_crystal_object/" + section, message);
        }
    }
    public void theBigCrystalBuff(obj_id player, obj_id terminal) throws InterruptedException
    {
        buff.applyBuff(player, "crystal_buff");
        utils.removeScriptVar(player, "readyToUseCrystal");
        playClientEffectObj(player, "clienteffect/mustafar/som_force_crystal_buff.cef", player, "");
        sendDirtyObjectMenuNotification(terminal);
        return;
    }
    public void destroyTheCrystal(obj_id player, obj_id crystal) throws InterruptedException
    {
        debugLogging("destroyTheCrystal: ", " ENTERED");
        obj_id self = getSelf();
        obj_id dungeon = getTopMostContainer(self);
        if (!isIdValid(dungeon))
        {
            debugLogging("//***// specialForcePowerAttackWindup: ", "////>>>> dungeon obj_id is invalid. BROKEN");
        }
        obj_id playerJedi = utils.getObjIdScriptVar(self, "player");
        doAnimationAction(playerJedi, anims.PLAYER_FORCE_BLAST);
        playClientEffectObj(playerJedi, "clienteffect/mustafar/som_force_crystal_destruction.cef", self, "");
        messageTo(dungeon, "blowUpCrystal", null, 3, false);
        messageTo(dungeon, "obiCongratulatesPlayer", null, 5, false);
        utils.removeScriptVar(player, "dealWithCrystal");
        return;
    }
    public void takeTheCrystal(obj_id player, obj_id crystal) throws InterruptedException
    {
        utils.removeScriptVar(player, "dealWithCrystal");
        obj_id self = getSelf();
        obj_id dungeon = getTopMostContainer(self);
        obj_id obiwan = utils.getObjIdScriptVar(dungeon, "obiwan");
        faceTo(obiwan, player);
        string_id strSpam = new string_id(mustafar.STF_OBI_MSGS, "som_obi_disappointed");
        prose_package pp = prose.getPackage(strSpam);
        prose.setTT(pp, player);
        chat.chat(obiwan, player, pp);
        messageTo(dungeon, "moveObiwanToCrystalSuckLocation", null, 2, false);
        return;
    }
    public void drainedCrystalBuff(obj_id player, obj_id crystal) throws InterruptedException
    {
        buff.applyBuff(player, "crystal_buff_drained");
        playClientEffectObj(player, "clienteffect/mustafar/som_force_crystal_buff.cef", player, "");
        return;
    }
    public int crystalTest1(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id dungeon = getTopMostContainer(self);
        if (!isIdValid(dungeon))
        {
            debugLogging("//***// specialForcePowerAttackWindup: ", "////>>>> dungeon obj_id is invalid. BROKEN");
        }
        obj_id player = utils.getObjIdScriptVar(dungeon, "player");
        doAnimationAction(player, "anims.PLAYER_FORCE_BLAST");
        playClientEffectObj(player, "clienteffect/mustafar/som_force_crystal_destruction.cef", self, "");
        return SCRIPT_CONTINUE;
    }
    public int crystalTest2(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id dungeon = getTopMostContainer(self);
        if (!isIdValid(dungeon))
        {
            debugLogging("//***// specialForcePowerAttackWindup: ", "////>>>> dungeon obj_id is invalid. BROKEN");
        }
        obj_id player = utils.getObjIdScriptVar(dungeon, "player");
        doAnimationAction(player, "anims.PLAYER_FORCE_BLAST");
        playClientEffectObj(player, "clienteffect/mustafar/som_force_crystal_drain.cef", self, "");
        return SCRIPT_CONTINUE;
    }
}
