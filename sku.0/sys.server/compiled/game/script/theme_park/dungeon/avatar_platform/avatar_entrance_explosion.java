package script.theme_park.dungeon.avatar_platform;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.chat;

public class avatar_entrance_explosion extends script.base_script
{
    public avatar_entrance_explosion()
    {
    }
    public static final String STF = "dungeon/avatar_platform";
    public static final string_id OPENING = new string_id(STF, "trando_chat_01");
    public static final string_id RESPONSE = new string_id(STF, "trando_chat_02");
    public int OnReceivedItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        if (!groundquests.isQuestActive(item, "ep3_trando_hssissk_zssik_10"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = getTopMostContainer(self);
        if (hasObjVar(structure, "avatar_platform.avatar_destruct_sequence") && !hasObjVar(structure, "avatar_platform.explosion_sequence_hall"))
        {
            startExplosionFinal(item, self);
        }
        return SCRIPT_CONTINUE;
    }
    public void startExplosionFinal(obj_id player, obj_id self) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        obj_id[] items = getContents(self);
        if (items != null && items.length > 0)
        {
            for (int i = 0; i < items.length; i++)
            {
                if (isPlayer(items[i]))
                {
                    startExplosionSequence(player, self);
                    setObjVar(structure, "avatar_platform.explosion_sequence_hall", 1);
                    return;
                }
            }
        }
        if (hasObjVar(structure, "avatar_platform.explosion_sequence_hall"))
        {
            removeObjVar(structure, "avatar_platform.explosion_sequence_hall");
        }
        return;
    }
    public void startExplosionSequence(obj_id player, obj_id self) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        obj_id entrance = getCellId(structure, "entrance");
        location here = getLocation(self);
        String planet = here.area;
        dictionary info = new dictionary();
        info.put("player", player);
        location explosionPnt = new location(91.1f, 0.0f, 24.4f, planet, entrance);
        playClientEffectLoc(player, "clienteffect/avatar_hallway_explosion.cef", explosionPnt, 0.0f);
        messageTo(self, "handleMoreExplosion01", info, 2f, false);
        return;
    }
    public int handleMoreExplosion01(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id structure = getTopMostContainer(self);
        obj_id entrance = getCellId(structure, "entrance");
        location here = getLocation(self);
        String planet = here.area;
        dictionary info = new dictionary();
        info.put("player", player);
        location explosionPnt = new location(91.1f, 0.0f, 18.4f, planet, entrance);
        playClientEffectLoc(player, "clienteffect/avatar_hallway_explosion.cef", explosionPnt, 0.0f);
        messageTo(self, "handleMoreExplosion02", info, 2f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleMoreExplosion02(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id structure = getTopMostContainer(self);
        obj_id entrance = getCellId(structure, "entrance");
        location here = getLocation(self);
        String planet = here.area;
        dictionary info = new dictionary();
        info.put("player", player);
        location explosionPnt = new location(97.2f, 0.0f, 18.4f, planet, entrance);
        playClientEffectLoc(player, "clienteffect/avatar_hallway_explosion.cef", explosionPnt, 0.0f);
        messageTo(self, "handleMoreExplosion03", info, 2f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleMoreExplosion03(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id structure = getTopMostContainer(self);
        obj_id entrance = getCellId(structure, "entrance");
        location here = getLocation(self);
        String planet = here.area;
        dictionary info = new dictionary();
        info.put("player", player);
        location explosionPnt = new location(97.2f, 0.0f, 24.4f, planet, entrance);
        playClientEffectLoc(player, "clienteffect/avatar_hallway_explosion.cef", explosionPnt, 0.0f);
        messageTo(self, "handleMoreExplosion04", info, 2f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleMoreExplosion04(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id structure = getTopMostContainer(self);
        obj_id entrance = getCellId(structure, "entrance");
        location here = getLocation(self);
        String planet = here.area;
        dictionary info = new dictionary();
        info.put("player", player);
        location explosionPnt = new location(101.8f, 0.0f, 18.4f, planet, entrance);
        playClientEffectLoc(player, "clienteffect/avatar_hallway_explosion.cef", explosionPnt, 0.0f);
        messageTo(self, "handleMoreExplosion05", info, 2f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleMoreExplosion05(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id structure = getTopMostContainer(self);
        obj_id entrance = getCellId(structure, "entrance");
        location here = getLocation(self);
        String planet = here.area;
        dictionary info = new dictionary();
        info.put("player", player);
        location explosionPnt = new location(101.8f, 0.0f, 24.4f, planet, entrance);
        playClientEffectLoc(player, "clienteffect/avatar_hallway_explosion.cef", explosionPnt, 0.0f);
        messageTo(self, "handleMoreExplosion06", info, 2f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleMoreExplosion06(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id structure = getTopMostContainer(self);
        obj_id entrance = getCellId(structure, "entrance");
        location here = getLocation(self);
        String planet = here.area;
        dictionary info = new dictionary();
        info.put("player", player);
        location explosionPnt = new location(108.9f, 0.0f, 18.4f, planet, entrance);
        playClientEffectLoc(player, "clienteffect/avatar_hallway_explosion.cef", explosionPnt, 0.0f);
        messageTo(self, "handleMoreExplosion07", info, 2f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleMoreExplosion07(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id structure = getTopMostContainer(self);
        obj_id entrance = getCellId(structure, "entrance");
        location here = getLocation(self);
        String planet = here.area;
        dictionary info = new dictionary();
        info.put("player", player);
        location explosionPnt = new location(108.9f, 0.0f, 24.4f, planet, entrance);
        playClientEffectLoc(player, "clienteffect/avatar_hallway_explosion.cef", explosionPnt, 0.0f);
        messageTo(self, "handlePlayerCheck", info, 2f, false);
        return SCRIPT_CONTINUE;
    }
    public int handlePlayerCheck(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        obj_id[] items = getContents(self);
        if (items != null && items.length > 0)
        {
            for (int i = 0; i < items.length; i++)
            {
                if (isPlayer(items[i]))
                {
                    startExplosionSequence(items[i], self);
                    setObjVar(structure, "avatar_platform.explosion_sequence_hall", 1);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (hasObjVar(structure, "avatar_platform.explosion_sequence_hall"))
        {
            removeObjVar(structure, "avatar_platform.explosion_sequence_hall");
        }
        return SCRIPT_CONTINUE;
    }
}
