package script.systems.event_perk;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.prose;

public class event_perk extends script.base_script
{
    public event_perk()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        float lifeSpan = getFloatObjVar(self, "event_perk.lifeSpan");
        float timeStamp = getFloatObjVar(self, "event_perk.timeStamp");
        float deathStamp = timeStamp + lifeSpan;
        float rightNow = getGameTime();
        float dieTime = deathStamp - rightNow;
        messageTo(self, "cleanUp", null, dieTime, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        float lifeSpan = getFloatObjVar(self, "event_perk.lifeSpan");
        float timeStamp = getFloatObjVar(self, "event_perk.timeStamp");
        float deathStamp = timeStamp + lifeSpan;
        float rightNow = getGameTime();
        float dieTime = deathStamp - rightNow;
        messageTo(self, "cleanUp", null, dieTime, false);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "event_perk.noBuild"))
        {
            obj_id noBuild = getObjIdObjVar(self, "event_perk.noBuild");
            messageTo(noBuild, "cleanUp", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id owner = getObjIdObjVar(self, "event_perk.owner");
        if (hasObjVar(self, "event_perk.no_redeed"))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "event_perk.perk_type"))
        {
            String type = getStringObjVar(self, "event_perk.perk_type");
            if (owner == player && (type.equals("honorguard") || type.equals("npc") || type.equals("theater")))
            {
                int menu3 = mi.addRootMenu(menu_info_types.SERVER_MENU3, new string_id("event_perk", "mnu_show_exp_time"));
                int menu2 = mi.addRootMenu(menu_info_types.SERVER_MENU2, new string_id("event_perk", "mnu_redeed"));
                return SCRIPT_CONTINUE;
            }
            if (owner == player && type.equals("static"))
            {
                int menu3 = mi.addRootMenu(menu_info_types.SERVER_MENU3, new string_id("event_perk", "mnu_show_exp_time"));
                int menu2 = mi.addRootMenu(menu_info_types.SERVER_MENU2, new string_id("event_perk", "mnu_redeed"));
                int menu4 = mi.addRootMenu(menu_info_types.SERVER_MENU4, new string_id("event_perk", "mnu_rotate"));
                mi.addSubMenu(menu4, menu_info_types.SERVER_MENU5, new string_id("event_perk", "mnu_rot_left"));
                mi.addSubMenu(menu4, menu_info_types.SERVER_MENU6, new string_id("event_perk", "mnu_rot_right"));
                return SCRIPT_CONTINUE;
            }
            if (owner == player && type.equals("gamedroid"))
            {
                int initialized = 1;
                if (hasObjVar(self, "event_perk.racing.initialized"))
                {
                    initialized = getIntObjVar(self, "event_perk.racing.initialized");
                }
                if (hasObjVar(self, "event_perk.scavenger.initialized"))
                {
                    initialized = getIntObjVar(self, "event_perk.scavenger.initialized");
                }
                if (initialized == 0)
                {
                    int menu8 = mi.addRootMenu(menu_info_types.SERVER_MENU8, new string_id("event_perk", "mnu_show_exp_time"));
                    int menu9 = mi.addRootMenu(menu_info_types.SERVER_MENU9, new string_id("event_perk", "mnu_redeed"));
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        String type = getStringObjVar(self, "event_perk.perk_type");
        if (item == menu_info_types.SERVER_MENU3 || item == menu_info_types.SERVER_MENU8)
        {
            float lifeSpan = getFloatObjVar(self, "event_perk.lifeSpan");
            float timeStamp = getFloatObjVar(self, "event_perk.timeStamp");
            float rightNow = getGameTime();
            float expirationTimeMinutesFloat = ((lifeSpan + timeStamp) - rightNow) / 60;
            int expirationTimeMinutes = (int)expirationTimeMinutesFloat;
            prose_package showExpiration = new prose_package();
            showExpiration = prose.getPackage(new string_id("event_perk", "show_exp_time"));
            prose.setDI(showExpiration, expirationTimeMinutes);
            sendSystemMessageProse(player, showExpiration);
        }
        if (item == menu_info_types.SERVER_MENU2 && (type.equals("static") || type.equals("npc") || type.equals("theater")))
        {
            redeedPerk(player, self);
        }
        if (item == menu_info_types.SERVER_MENU5 && type.equals("static"))
        {
            float yaw = getYaw(self);
            yaw -= 45;
            setYaw(self, yaw);
        }
        if (item == menu_info_types.SERVER_MENU6 && type.equals("static"))
        {
            float yaw = getYaw(self);
            yaw += 45;
            setYaw(self, yaw);
        }
        if (item == menu_info_types.SERVER_MENU9 && type.equals("gamedroid"))
        {
            int initialized = getIntObjVar(self, "event_perk.racing.initialized");
            if (initialized == 0)
            {
                redeedPerk(player, self);
            }
        }
        if (item == menu_info_types.SERVER_MENU2 && type.equals("honorguard"))
        {
            disbandHonorguard(player, self);
            redeedPerk(player, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int cleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        if (self.isBeingDestroyed())
        {
            return SCRIPT_CONTINUE;
        }
        float lifeSpan = getFloatObjVar(self, "event_perk.lifeSpan");
        float timeStamp = getFloatObjVar(self, "event_perk.timeStamp");
        float rightNow = getGameTime();
        float deathStamp = timeStamp + lifeSpan;
        float dieTime = deathStamp - rightNow;
        if (dieTime < 1)
        {
            CustomerServiceLog("EventPerk", "(Spawned Object - [" + self + "]) lifespan expired so it was destroyed.");
            destroyObject(self);
        }
        else 
        {
            messageTo(self, "cleanUp", null, dieTime, false);
        }
        return SCRIPT_CONTINUE;
    }
    public void redeedPerk(obj_id player, obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "event_perk.chest.rewarded"))
        {
            obj_id[] chestContents = utils.getContents(self, true);
            if (chestContents.length > 0)
            {
                sendSystemMessage(player, new string_id("event_perk", "redeed_remove_items"));
                return;
            }
        }
        obj_id[] allContents = utils.getAllItemsInBankAndInventory(player);
        int perkCount = 0;
        for (int i = 0; i < allContents.length; i++)
        {
            if (hasObjVar(allContents[i], "event_perk"))
            {
                perkCount++;
            }
            if (perkCount >= 5)
            {
                sendSystemMessage(player, new string_id("event_perk", "redeed_too_many_deeds"));
                return;
            }
        }
        String sourceDeed = getStringObjVar(self, "event_perk.source_deed");
        float lifeSpan = getFloatObjVar(self, "event_perk.lifeSpan");
        float timeStamp = getFloatObjVar(self, "event_perk.timeStamp");
        float rightNow = getGameTime();
        float adjustedLifeSpan = (lifeSpan + timeStamp) - rightNow;
        obj_id playerInventory = utils.getInventoryContainer(player);
        obj_id perkDeed = createObject(sourceDeed, playerInventory, "");
        if (isIdValid(perkDeed))
        {
            setObjVar(perkDeed, "event_perk.lifeSpan", adjustedLifeSpan);
            setObjVar(perkDeed, "event_perk.redeeded", 1);
            setObjVar(perkDeed, "event_perk.timeStamp", rightNow);
            sendSystemMessage(player, new string_id("event_perk", "redeed_success"));
            if (hasObjVar(self, "event_perk.event_perk"))
            {
                obj_id theater = getObjIdObjVar(self, "event_perk.event_perk");
                destroyObject(theater);
            }
            destroyObject(self);
        }
        else 
        {
            sendSystemMessage(player, new string_id("event_perk", "redeed_failed"));
        }
        return;
    }
    public void disbandHonorguard(obj_id player, obj_id self) throws InterruptedException
    {
        int headCount = getIntObjVar(self, "event_perk.honorguard_headCount");
        for (int i = 1; i < headCount; i++)
        {
            obj_id guardMember = getObjIdObjVar(self, "event_perk.honorguard_num_" + i);
            destroyObject(guardMember);
        }
        return;
    }
    public void checkTimeLimit(obj_id self) throws InterruptedException
    {
        float lifeSpan = getFloatObjVar(self, "event_perk.lifeSpan");
        float timeStamp = getFloatObjVar(self, "event_perk.timeStamp");
        float rightNow = getGameTime();
        float delta = rightNow - timeStamp;
        if (delta > lifeSpan)
        {
            CustomerServiceLog("EventPerk", "(Spawned Object - [" + self + "]) lifespan expired so it was destroyed.");
            destroyObject(self);
        }
        return;
    }
    public int OnAddedToWorld(obj_id self) throws InterruptedException
    {
        checkTimeLimit(self);
        return SCRIPT_CONTINUE;
    }
}
