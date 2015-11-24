package script.theme_park.kashyyyk;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_dungeon;
import script.library.utils;

public class hracca_controller extends script.base_script
{
    public hracca_controller()
    {
    }
    public static final int CHISS_FOG_ON = 0;
    public static final int CHISS_FOG_OFF = 1;
    public int beginSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, space_dungeon.VAR_QUEST_TYPE))
        {
            return SCRIPT_CONTINUE;
        }
        String questName = getStringObjVar(self, space_dungeon.VAR_QUEST_TYPE);
        if (questName == null || questName.equals("") || questName.equals("none"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] spawners = space_dungeon.getRegisteredObjects(self);
        if (spawners != null && spawners.length > 0)
        {
            for (int i = 0; i < spawners.length; i++)
            {
                obj_id spawner = spawners[i];
                dictionary webster = new dictionary();
                webster.put("controllerObject", self);
                webster.put("spawnChissOnly", true);
                messageTo(spawner, "doHraccaSpawnEvent", webster, 1, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int registerChissPoacher(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id chissPoacher = params.getObjId("chissPoacher");
        if (isIdValid(chissPoacher))
        {
            if (!hasObjVar(self, "chissPoacherList"))
            {
                Vector chissPoachers = new Vector();
                chissPoachers.setSize(0);
                utils.addElement(chissPoachers, chissPoacher);
                if (chissPoachers != null && chissPoachers.size() > 0)
                {
                    setObjVar(self, "chissPoacherList", chissPoachers);
                }
            }
            else 
            {
                Vector chissPoacherList = getResizeableObjIdArrayObjVar(self, "chissPoacherList");
                chissPoacherList.add(chissPoacher);
                if (chissPoacherList != null && chissPoacherList.size() > 0)
                {
                    setObjVar(self, "chissPoacherList", chissPoacherList);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleChissPoacherDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id deadChissPoacher = params.getObjId("deadChissPoacher");
        if (isIdValid(deadChissPoacher))
        {
            if (hasObjVar(self, "chissPoacherList"))
            {
                Vector chissPoacherList = getResizeableObjIdArrayObjVar(self, "chissPoacherList");
                if (chissPoacherList != null && chissPoacherList.size() > 0)
                {
                    int previousNumPoachers = chissPoacherList.size();
                    if (chissPoacherList.contains(deadChissPoacher))
                    {
                        chissPoacherList.remove(deadChissPoacher);
                        if (chissPoacherList != null && chissPoacherList.size() > 0)
                        {
                            setObjVar(self, "chissPoacherList", chissPoacherList);
                            string_id message = new string_id("theme_park/kashyyyk/hracca", "chiss_report_38");
                            switch (chissPoacherList.size())
                            {
                                case 38:
                                message = new string_id("theme_park/kashyyyk/hracca", "chiss_report_38");
                                sendChissCountUpdateMessage(self, message);
                                break;
                                case 20:
                                message = new string_id("theme_park/kashyyyk/hracca", "chiss_report_20");
                                sendChissCountUpdateMessage(self, message);
                                break;
                                case 10:
                                message = new string_id("theme_park/kashyyyk/hracca", "chiss_report_10");
                                sendChissCountUpdateMessage(self, message);
                                break;
                                case 5:
                                message = new string_id("theme_park/kashyyyk/hracca", "chiss_report_5");
                                sendChissCountUpdateMessage(self, message);
                                break;
                                case 2:
                                message = new string_id("theme_park/kashyyyk/hracca", "chiss_report_2");
                                sendChissCountUpdateMessage(self, message);
                                break;
                                case 1:
                                message = new string_id("theme_park/kashyyyk/hracca", "chiss_report_1");
                                sendChissCountUpdateMessage(self, message);
                                break;
                                default:
                                break;
                            }
                        }
                        else 
                        {
                            removeObjVar(self, "chissPoacherList");
                            obj_id[] spawners = space_dungeon.getRegisteredObjects(self);
                            if (spawners != null && spawners.length > 0)
                            {
                                for (int i = 0; i < spawners.length; i++)
                                {
                                    obj_id spawner = spawners[i];
                                    dictionary webster = new dictionary();
                                    webster.put("controllerObject", self);
                                    webster.put("spawnKkorrwrot", true);
                                    messageTo(spawner, "doHraccaSpawnEvent", webster, 1, false);
                                }
                            }
                            obj_id[] playersInInstance = space_dungeon.getPlayersInInstance(self);
                            if (playersInInstance != null && playersInInstance.length > 0)
                            {
                                for (int i = 0; i < playersInInstance.length; i++)
                                {
                                    obj_id player = playersInInstance[i];
                                    play2dNonLoopingSound(player, "sound/quest_hracca_kkorrwrot_roar.snd");
                                }
                            }
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void sendChissCountUpdateMessage(obj_id controller, string_id message) throws InterruptedException
    {
        obj_id[] playersInInstance = space_dungeon.getPlayersInInstance(controller);
        if (playersInInstance != null && playersInInstance.length > 0)
        {
            for (int i = 0; i < playersInInstance.length; i++)
            {
                obj_id player = playersInInstance[i];
                sendSystemMessage(player, message);
            }
        }
        return;
    }
    public int msgSpaceDungeonCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] spawners = space_dungeon.getRegisteredObjects(self);
        if (spawners != null && spawners.length > 0)
        {
            for (int i = 0; i < spawners.length; i++)
            {
                obj_id spawner = spawners[i];
                messageTo(spawner, "doCleanupEvent", null, 1, false);
            }
        }
        if (hasObjVar(self, "chissPoacherList"))
        {
            removeObjVar(self, "chissPoacherList");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int testWeatherStateChange(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}
