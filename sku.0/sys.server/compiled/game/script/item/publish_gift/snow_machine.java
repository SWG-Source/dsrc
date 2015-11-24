package script.item.publish_gift;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_structure;
import script.library.static_item;
import script.library.sui;
import script.library.utils;

public class snow_machine extends script.base_script
{
    public snow_machine()
    {
    }
    public static final string_id SID_WHILE_DEAD = new string_id("spam", "while_dead");
    public static final string_id SID_HAS_SNOWBALL_ALREADY = new string_id("spam", "has_snowball");
    public static final string_id SID_SNOWBALL_VERY_HOT = new string_id("spam", "snowball_hot_environment");
    public static final string_id SID_TURN_ON = new string_id("spam", "snow_machine_on");
    public static final string_id SID_TURN_OFF = new string_id("spam", "snow_machine_off");
    public static final string_id SID_STORM_INTENSITY = new string_id("spam", "snow_machine_intensity");
    public static final string_id SID_MAKE_SNOWBALLS = new string_id("spam", "make_snowballs");
    public static final string_id SID_STORM_01 = new string_id("spam", "storm1");
    public static final string_id SID_STORM_02 = new string_id("spam", "storm2");
    public static final string_id SID_STORM_03 = new string_id("spam", "storm3");
    public static final String SNOW_PARTICLE_01 = "object/static/particle/particle_snow_machine_light.iff";
    public static final String SNOW_PARTICLE_02 = "object/static/particle/particle_snow_machine_medium.iff";
    public static final String SNOW_PARTICLE_03 = "object/static/particle/particle_snow_machine_heavy.iff";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "snow_machine_on"))
        {
            playSnowParticle(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id snowMachine = self;
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            sendSystemMessage(player, SID_WHILE_DEAD);
            return SCRIPT_CONTINUE;
        }
        if (utils.isInHouseCellSpace(snowMachine))
        {
            obj_id structure = getTopMostContainer(snowMachine);
            if (!player_structure.isAdmin(structure, player))
            {
                return SCRIPT_CONTINUE;
            }
            if (hasObjVar(snowMachine, "snow_machine_on"))
            {
                int stormOnOff = mi.addRootMenu(menu_info_types.ITEM_USE, SID_TURN_OFF);
            }
            else 
            {
                int stormOnOff = mi.addRootMenu(menu_info_types.ITEM_USE, SID_TURN_ON);
            }
            int intensityMain = mi.addRootMenu(menu_info_types.SERVER_MENU2, SID_STORM_INTENSITY);
            mi.addSubMenu(intensityMain, menu_info_types.SERVER_MENU3, SID_STORM_01);
            mi.addSubMenu(intensityMain, menu_info_types.SERVER_MENU4, SID_STORM_02);
            mi.addSubMenu(intensityMain, menu_info_types.SERVER_MENU5, SID_STORM_03);
            sendDirtyObjectMenuNotification(snowMachine);
        }
        if (utils.isNestedWithinAPlayer(snowMachine))
        {
            menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
            if (mid != null)
            {
                mid.setLabel(new string_id("spam", "make_snowballs"));
                mid.setServerNotify(true);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        obj_id snowMachine = self;
        if (!isIdValid(player) || !isIdValid(snowMachine))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            sendSystemMessage(player, SID_WHILE_DEAD);
            return SCRIPT_CONTINUE;
        }
        if (utils.isInHouseCellSpace(snowMachine))
        {
            if (item == menu_info_types.ITEM_USE && !hasObjVar(snowMachine, "snow_machine_on"))
            {
                setObjVar(snowMachine, "snow_machine_on", 1);
                setObjVar(snowMachine, "unmoveable", 1);
                playSnowParticle(snowMachine);
                return SCRIPT_CONTINUE;
            }
            if (item == menu_info_types.ITEM_USE && hasObjVar(snowMachine, "snow_machine_on"))
            {
                stopSnowParticle(snowMachine);
                return SCRIPT_CONTINUE;
            }
        }
        if (utils.isNestedWithinAPlayer(snowMachine))
        {
            if (item == menu_info_types.ITEM_USE)
            {
                if (!utils.playerHasStaticItemInBankOrInventory(player, "publish_gift_chapter_11_snow_balls_02_01"))
                {
                    String planetName = getCurrentSceneName();
                    if (planetName.equals("mustafar") || planetName.equals("tatooine"))
                    {
                        sendSystemMessage(player, SID_SNOWBALL_VERY_HOT);
                    }
                    obj_id pInv = utils.getInventoryContainer(player);
                    obj_id snowballs = static_item.createNewItemFunction("publish_gift_chapter_11_snow_balls_02_01", pInv);
                }
                else 
                {
                    sendSystemMessage(player, SID_HAS_SNOWBALL_ALREADY);
                }
            }
        }
        if (item == menu_info_types.SERVER_MENU3)
        {
            removeStormObjVar(snowMachine);
            setObjVar(snowMachine, "snowMachine.storm1", 1);
            checkRefreshParticle(snowMachine);
        }
        if (item == menu_info_types.SERVER_MENU4)
        {
            removeStormObjVar(snowMachine);
            setObjVar(snowMachine, "snowMachine.storm2", 1);
            checkRefreshParticle(snowMachine);
        }
        if (item == menu_info_types.SERVER_MENU5)
        {
            removeStormObjVar(snowMachine);
            setObjVar(snowMachine, "snowMachine.storm3", 1);
            checkRefreshParticle(snowMachine);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (hasObjVar(self, "snow_machine_on"))
        {
            if (hasObjVar(self, "snowParticleId"))
            {
                obj_id particleToDelete = getObjIdObjVar(self, "snowParticleId");
                if (!isIdNull(particleToDelete))
                {
                    destroyObject(particleToDelete);
                    if (!exists(particleToDelete))
                    {
                        cleanUpScriptsAndSuch(self);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void cleanUpScriptsAndSuch(obj_id snowMachine) throws InterruptedException
    {
        if (hasObjVar(snowMachine, "weatherParticleId"))
        {
            obj_id particle_id = getObjIdObjVar(snowMachine, "weatherParticleId");
            if (exists(particle_id))
            {
                destroyObject(particle_id);
                if (!exists(particle_id))
                {
                    removeObjVar(snowMachine, "weatherParticleId");
                }
            }
            else 
            {
                removeObjVar(snowMachine, "weatherParticleId");
            }
        }
        if (hasObjVar(snowMachine, "snow_machine_on"))
        {
            removeObjVar(snowMachine, "snow_machine_on");
        }
        if (hasObjVar(snowMachine, "unmoveable"))
        {
            removeObjVar(snowMachine, "unmoveable");
        }
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "snow_machine_on"))
        {
            if (hasObjVar(self, "snowParticleId"))
            {
                obj_id particleToDelete = getObjIdObjVar(self, "snowParticleId");
                if (!isIdNull(particleToDelete))
                {
                    destroyObject(particleToDelete);
                    if (!exists(particleToDelete))
                    {
                        cleanUpScriptsAndSuch(self);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void checkRefreshParticle(obj_id snowMachine) throws InterruptedException
    {
        if (hasObjVar(snowMachine, "snow_machine_on"))
        {
            stopSnowParticle(snowMachine);
            setObjVar(snowMachine, "snow_machine_on", 1);
            setObjVar(snowMachine, "unmoveable", 1);
            playSnowParticle(snowMachine);
        }
    }
    public void stopSnowParticle(obj_id snowMachine) throws InterruptedException
    {
        if (hasObjVar(snowMachine, "snowParticleId"))
        {
            obj_id particleToDelete = getObjIdObjVar(snowMachine, "snowParticleId");
            destroyObject(particleToDelete);
            if (!exists(particleToDelete))
            {
                cleanUpScriptsAndSuch(snowMachine);
            }
        }
    }
    public void playSnowParticle(obj_id snowMachine) throws InterruptedException
    {
        if (!isIdValid(snowMachine))
        {
            return;
        }
        if (hasObjVar(snowMachine, "snowParticleId"))
        {
            obj_id particleToDelete = getObjIdObjVar(snowMachine, "snowParticleId");
            String templateName = getTemplateName(particleToDelete);
            if (templateName == null || templateName.startsWith("object/static/particle/particle_snow_machine"))
            {
                destroyObject(particleToDelete);
                removeObjVar(snowMachine, "snowParticleId");
            }
        }
        if (hasObjVar(snowMachine, "snow_machine_on"))
        {
            if (hasObjVar(snowMachine, "snowMachine.storm1"))
            {
                obj_id snowParticle = createObject(SNOW_PARTICLE_01, getLocation(snowMachine));
                setObjVar(snowMachine, "snowParticleId", snowParticle);
                return;
            }
            if (hasObjVar(snowMachine, "snowMachine.storm2"))
            {
                obj_id snowParticle = createObject(SNOW_PARTICLE_02, getLocation(snowMachine));
                setObjVar(snowMachine, "snowParticleId", snowParticle);
                return;
            }
            if (hasObjVar(snowMachine, "snowMachine.storm3"))
            {
                obj_id snowParticle = createObject(SNOW_PARTICLE_03, getLocation(snowMachine));
                setObjVar(snowMachine, "snowParticleId", snowParticle);
                return;
            }
            obj_id snowParticle = createObject(SNOW_PARTICLE_02, getLocation(snowMachine));
            setObjVar(snowMachine, "snowParticleId", snowParticle);
        }
    }
    public void removeStormObjVar(obj_id snowMachine) throws InterruptedException
    {
        if (hasObjVar(snowMachine, "snowMachine.storm1"))
        {
            removeObjVar(snowMachine, "snowMachine.storm1");
        }
        if (hasObjVar(snowMachine, "snowMachine.storm2"))
        {
            removeObjVar(snowMachine, "snowMachine.storm2");
        }
        if (hasObjVar(snowMachine, "snowMachine.storm3"))
        {
            removeObjVar(snowMachine, "snowMachine.storm3");
        }
    }
}
