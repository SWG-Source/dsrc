package script.systems.npc_lair;

import script.*;
import script.library.buff;
import script.library.resource;
import script.library.utils;

public class lair_interactivity extends script.base_script
{
    public lair_interactivity()
    {
    }
    public static final boolean LOGGING_ON = false;
    public static final String LOGGING_CATEGORY = "lair_interactivity";
    public static final String LAIR_SEARCHED = "lair.searched";
    public static final int LAIR_BUFF_INCREASE = 2;
    public static final string_id SID_SEARCH_LAIR = new string_id("lair_n", "search_lair");
    public static final string_id SID_FOUND_NOTHING = new string_id("lair_n", "found_nothing");
    public static final string_id SID_INVENTORY_FULL = new string_id("lair_n", "inventory_full");
    public static final string_id SID_FOUND_EGGS = new string_id("lair_n", "found_eggs");
    public static final string_id SID_FOUND_BUGS = new string_id("lair_n", "found_bugs");
    public static final String[] BUG_SAMPLE_OBJECTS = 
    {
        "object/tangible/bug_jar/sample_bats.iff",
        "object/tangible/bug_jar/sample_bees.iff",
        "object/tangible/bug_jar/sample_butterflies.iff",
        "object/tangible/bug_jar/sample_flies.iff",
        "object/tangible/bug_jar/sample_glowzees.iff",
        "object/tangible/bug_jar/sample_moths.iff"
    };
    public static final String[] RARE_BUG_SAMPLE_OBJECTS = 
    {
        "object/tangible/fishing/bait/bait_grub.iff",
        "object/tangible/fishing/bait/bait_worm.iff",
        "object/tangible/fishing/bait/bait_insect.iff"
    };
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!hasObjVar(self, "npc_lair.isCreatureLair"))
        {
            detachScript(self, "systems.npc_lair.lair_interactivity");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int searchLair(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        searchLair(self, player);
        return SCRIPT_CONTINUE;
    }
    public void searchLair(obj_id self, obj_id player) throws InterruptedException
    {
        if (utils.hasScriptVar(self, LAIR_SEARCHED))
        {
            return;
        }
        int searchagain = rand(0, 2);
        if (searchagain == 0)
        {
            utils.setScriptVar(self, LAIR_SEARCHED, 1);
        }
        obj_id pInv = utils.getInventoryContainer(player);
        if (!isValidId(pInv) || !exists(pInv))
        {
            return;
        }
        if (getVolumeFree(pInv) <= 0)
        {
            sendSystemMessage(player, SID_INVENTORY_FULL);
            return;
        }
        int searchRoll = rand(1, 100);
        if (searchRoll < 10)
        {
            sendSystemMessage(player, SID_FOUND_NOTHING);
        }
        else if (searchRoll < 90)
        {
            sendSystemMessage(player, SID_FOUND_EGGS);
            int amt = rand(10, 20);
            if (buff.hasBuff(player, "lair_egg_buff"))
            {
                amt = amt * LAIR_BUFF_INCREASE;
            }
            obj_id[] resourceList = resource.createRandom("meat_egg_" + getCurrentSceneName(), amt, getLocation(self), pInv, player, 2);
            if (resourceList == null)
            {
                blog("ai.handleMilking: cannot get resource data from resource.createRandom function");
                CustomerServiceLog("milking_and_lair_search", "handleMilking: Player: " + getName(player) + " OID: " + player + " attempted to milk but could not resource data from resource.createRandom function while milking " + self + " " + getName(self));
                return;
            }
            location curloc = getLocation(player);
            if (curloc == null)
            {
                blog("ai.handleMilking: cannot get resource data from getLocation cpp");
                CustomerServiceLog("milking_and_lair_search", "handleMilking: Player: " + getName(player) + " OID: " + player + " attempted to milk but could not retrieve location data while milking " + self + " " + getName(self));
                return;
            }
            for (obj_id obj_id : resourceList) {
                blog("" + obj_id);
                setLocation(obj_id, curloc);
                putIn(obj_id, pInv, player);
            }
        }
        else 
        {
            sendSystemMessage(player, SID_FOUND_BUGS);
            if (rand(1, 100) < 50)
            {
                createObject(RARE_BUG_SAMPLE_OBJECTS[rand(0, RARE_BUG_SAMPLE_OBJECTS.length - 1)], pInv, "");
            }
            else 
            {
                createObject(BUG_SAMPLE_OBJECTS[rand(0, BUG_SAMPLE_OBJECTS.length - 1)], pInv, "");
            }
        }
    }
    public boolean blog(String msg) throws InterruptedException
    {
        if (LOGGING_ON && !msg.equals(""))
        {
            LOG(LOGGING_CATEGORY, msg);
        }
        return true;
    }
}
