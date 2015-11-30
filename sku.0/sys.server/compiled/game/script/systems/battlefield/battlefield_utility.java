package script.systems.battlefield;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.battlefield;
import script.library.factions;
import script.library.regions;
import script.library.utils;

public class battlefield_utility extends script.base_script
{
    public battlefield_utility()
    {
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        location loc = getLocation(self);
        if (text.startsWith("bfdestroy"))
        {
            obj_id target = getLookAtTarget(self);
            if (target != null)
            {
                loc = getLocation(target);
            }
            else 
            {
                java.util.StringTokenizer st = new java.util.StringTokenizer(text);
                LOG("LOG_CHANNEL", "tokens -> " + st.countTokens() + " nextToken ->" + st.nextToken());
                if (st.hasMoreTokens())
                {
                    String target_str = st.nextToken();
                    LOG("LOG_CHANNEL", "target_str ->" + target_str);
                    target = utils.stringToObjId(target_str);
                    if (target != null)
                    {
                        loc = getLocation(target);
                    }
                }
            }
            battlefield.destroyBattlefield(target);
        }
        if (text.startsWith("endbattle"))
        {
            obj_id target = getLookAtTarget(self);
            if (target == null || target == obj_id.NULL_ID)
            {
                java.util.StringTokenizer st = new java.util.StringTokenizer(text);
                LOG("LOG_CHANNEL", "tokens -> " + st.countTokens() + " nextToken ->" + st.nextToken());
                if (st.hasMoreTokens())
                {
                    String target_str = st.nextToken();
                    LOG("LOG_CHANNEL", "target_str ->" + target_str);
                    target = utils.stringToObjId(target_str);
                }
            }
            if (target != null && target != obj_id.NULL_ID)
            {
                battlefield.endBattlefield(target);
                sendSystemMessageTestingOnly(self, "Battle ended.");
            }
        }
        if (text.equals("battlefield"))
        {
            String area = getCurrentSceneName();
            battlefield.createBattlefieldRegions(area);
            sendSystemMessageTestingOnly(self, "Battlefields created.");
        }
        if (text.equals("destroyall"))
        {
            String area = getCurrentSceneName();
            battlefield.destroyBattlefieldRegions(area);
            sendSystemMessageTestingOnly(self, "Battlefields destroyed.");
        }
        if (text.startsWith("delta"))
        {
            obj_id target = obj_id.NULL_ID;
            java.util.StringTokenizer st = new java.util.StringTokenizer(text);
            LOG("LOG_CHANNEL", "tokens -> " + st.countTokens() + " nextToken ->" + st.nextToken());
            if (st.hasMoreTokens())
            {
                String target_str = st.nextToken();
                LOG("LOG_CHANNEL", "target_str ->" + target_str);
                target = utils.stringToObjId(target_str);
            }
            if (target == null)
            {
                target = getLookAtTarget(self);
            }
            if (target != null && target != obj_id.NULL_ID)
            {
                location targ_loc = getLocation(target);
                float delta_x = targ_loc.x - loc.x;
                float delta_y = targ_loc.y - loc.y;
                float delta_z = targ_loc.z - loc.z;
                LOG("LOG_CHANNEL", "delta ->" + "(" + delta_x + ", " + delta_y + ", " + delta_z + ")");
                sendSystemMessageTestingOnly(self, "(" + delta_x + ", " + delta_y + ", " + delta_z + ")");
            }
        }
        if (text.equals("clearbattleobjs"))
        {
            obj_id target = getLookAtTarget(self);
            if (target != null && target != obj_id.NULL_ID)
            {
                if (hasObjVar(target, battlefield.VAR_BATTLEFIELD))
                {
                    removeObjVar(target, battlefield.VAR_BATTLEFIELD);
                }
            }
            else 
            {
                if (hasObjVar(self, battlefield.VAR_BATTLEFIELD))
                {
                    removeObjVar(self, battlefield.VAR_BATTLEFIELD);
                }
            }
        }
        if (text.equals("nuke"))
        {
            obj_id[] objects = getObjectsInRange(loc, 20.0f);
            for (int i = 0; i < objects.length; i++)
            {
                if (!isPlayer(objects[i]))
                {
                    LOG("LOG_CHANNEL", "object deleted ->" + objects[i]);
                    destroyObject(objects[i]);
                }
            }
        }
        if (text.equals("meganuke"))
        {
            obj_id[] objects = getObjectsInRange(loc, 250.0f);
            for (int i = 0; i < objects.length; i++)
            {
                if (!isPlayer(objects[i]))
                {
                    LOG("LOG_CHANNEL", "object deleted ->" + objects[i]);
                    destroyObject(objects[i]);
                }
            }
        }
        if (text.equals("faction"))
        {
            obj_id target = getLookAtTarget(self);
            if (target != null)
            {
                region bf = battlefield.getBattlefield(target);
                int faction_id = pvpBattlefieldGetFaction(target, bf);
                String faction = factions.getFactionNameByHashCode(faction_id);
                sendSystemMessageTestingOnly(self, "faction ->" + faction);
                factions.addFactionStanding(self, faction, 250.0f);
            }
            else 
            {
                int faction_id = pvpGetAlignedFaction(self);
                String faction = factions.getFactionNameByHashCode(faction_id);
                sendSystemMessageTestingOnly(self, "faction ->" + faction);
                factions.addFactionStanding(self, faction, 250.0f);
            }
        }
        if (text.startsWith("warp"))
        {
            obj_id target = obj_id.NULL_ID;
            java.util.StringTokenizer st = new java.util.StringTokenizer(text);
            LOG("LOG_CHANNEL", "tokens -> " + st.countTokens() + " nextToken ->" + st.nextToken());
            if (st.hasMoreTokens())
            {
                String planet = st.nextToken();
                LOG("LOG_CHANNEL", "planet ->" + planet);
                float x = (float)utils.stringToInt(st.nextToken());
                float y = (float)utils.stringToInt(st.nextToken());
                float z = (float)utils.stringToInt(st.nextToken());
                LOG("LOG_CHANNEL", "loc ->" + x + "/" + y + "/" + z);
                warpPlayer(self, planet, x, y, z, null, 0.0f, 0.0f, 0.0f);
            }
        }
        if (text.equals("bfobject"))
        {
            region bf = battlefield.getBattlefield(self);
            if (bf != null)
            {
                obj_id bf_object = battlefield.getMasterObjectFromRegion(bf);
                sendSystemMessageTestingOnly(self, "bf_object ->" + bf_object);
            }
        }
        if (text.startsWith("getposture"))
        {
            obj_id target = obj_id.NULL_ID;
            java.util.StringTokenizer st = new java.util.StringTokenizer(text);
            LOG("LOG_CHANNEL", "tokens -> " + st.countTokens() + " nextToken ->" + st.nextToken());
            if (st.hasMoreTokens())
            {
                String target_str = st.nextToken();
                LOG("LOG_CHANNEL", "target_str ->" + target_str);
                target = utils.stringToObjId(target_str);
            }
            if (isIdValid(target))
            {
                sendSystemMessageTestingOnly(self, "posture ->" + getPosture(target));
            }
        }
        return SCRIPT_CONTINUE;
    }
}
