package script.theme_park.alderaan.act3;

import script.*;
import script.library.badge;
import script.library.chat;
import script.library.factions;

public class imperial_research_terminal extends script.base_script
{
    public imperial_research_terminal()
    {
    }
    public static final String IMPERIAL_SHARED_STF = "theme_park/alderaan/act3/shared_imperial_missions";
    public static final string_id MISSION_COMPLETE = new string_id(IMPERIAL_SHARED_STF, "mission_complete");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id facility = getObjIdObjVar(self, "coa3.imperial.facility");
        int numGuards = getIntObjVar(facility, "coa3.imperial.numGuards");
        if (numGuards > 0)
        {
            return SCRIPT_CONTINUE;
        }
        int mnu = mi.addRootMenu(menu_info_types.SELF_DESTRUCT, new string_id("self_destruct", "self_destruct"));
        mi.addSubMenu(mnu, menu_info_types.THIRTY_SEC, new string_id("self_destruct", "thirty"));
        mi.addSubMenu(mnu, menu_info_types.FIFTEEN_SEC, new string_id("self_destruct", "fifteen"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.THIRTY_SEC)
        {
            obj_id bldg = getObjIdObjVar(self, "coa3.imperial.facility");
            playMusic(player, "sound/music_darth_vader_theme.snd");
            chat.chat(self, new string_id("self_destruct", "thirty_seconds"));
            dictionary destroyer = new dictionary();
            destroyer.put("player", player);
            destroyer.put("bldg", bldg);
            messageTo(self, "firstPop", destroyer, 10, true);
            messageTo(self, "firstPop", destroyer, 20, true);
            messageTo(self, "blowUp", destroyer, 30, true);
            return SCRIPT_CONTINUE;
        }
        else if (item == menu_info_types.FIFTEEN_SEC)
        {
            obj_id bldg = getObjIdObjVar(self, "coa3.imperial.facility");
            chat.chat(self, new string_id("self_destruct", "fifteen_seconds"));
            dictionary destroyer = new dictionary();
            destroyer.put("player", player);
            destroyer.put("bldg", bldg);
            messageTo(self, "firstPop", destroyer, 5, true);
            messageTo(self, "blowUp", destroyer, 15, true);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int blowUp(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id viewer = params.getObjId("player");
        obj_id bldg = params.getObjId("bldg");
        location death = getLocation(bldg);
        playClientEffectLoc(viewer, "clienteffect/combat_explosion_lair_large.cef", death, 10.0f);
        death.x = death.x + 5;
        playClientEffectLoc(viewer, "clienteffect/combat_explosion_lair_large.cef", death, 10.0f);
        death.z = death.z + 5;
        playClientEffectLoc(viewer, "clienteffect/combat_explosion_lair_large.cef", death, 10.0f);
        death.x = death.x - 10;
        playClientEffectLoc(viewer, "clienteffect/combat_explosion_lair_large.cef", death, 10.0f);
        death.z = death.z - 10;
        playClientEffectLoc(viewer, "clienteffect/combat_explosion_lair_large.cef", death, 10.0f);
        boolean blowUp = destroyObject(bldg);
        setObjVar(self, "coa3.imperial.success", 1);
        messageTo(self, "bldgCheck", params, 1, true);
        return SCRIPT_CONTINUE;
    }
    public int firstPop(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id viewer = params.getObjId("player");
        obj_id bldg = getTopMostContainer(self);
        location death = getLocation(bldg);
        playClientEffectLoc(viewer, "clienteffect/combat_explosion_lair_large.cef", death, 10.0f);
        return SCRIPT_CONTINUE;
    }
    public int bldgCheck(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id viewer = params.getObjId("player");
        obj_id bldg = params.getObjId("bldg");
        if (exists(bldg))
        {
            removeObjVar(self, "coa3.imperial.success");
            sendSystemMessage(viewer, new string_id(IMPERIAL_SHARED_STF, "self_destruct_aborted"));
        }
        else 
        {
            obj_id player = getObjIdObjVar(self, "coa3.imperial.playerId");
            setObjVar(player, "coa3.imperial.finalMission", 1);
            CustomerServiceLog("Badge", getName(player) + "(" + player + ") has recieved badge event_coa3_imperial");
            factions.awardFactionStanding(player, "Imperial", 750);
            sendSystemMessage(player, MISSION_COMPLETE);
            badge.grantBadge(player, "event_coa3_imperial");
            playMusic(player, "sound/music_darth_vader_theme.snd");
            obj_id tatooine = getPlanetByName("tatooine");
            int winCount = getIntObjVar(tatooine, "coa3.imperialWinCount");
            winCount++;
            setObjVar(tatooine, "coa3.imperialWinCount", winCount);
            messageTo(player, "createReturnMission", null, 1, false);
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int cleanup(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "coa3.imperial.success"))
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
}
