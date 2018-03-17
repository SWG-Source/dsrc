package script.systems.storyteller;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.static_item;
import script.library.storyteller;
import script.library.trial;

public class prop_controller extends script.base_script
{
    public prop_controller()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "storytellerCreationTime", getGameTime());
        utils.setScriptVar(self, "storytellerOnAttachFired", true);
        messageTo(self, "startPropSetup", null, 1, false);
        checkBonusCleanupTime(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleStorytellerPropInitialize", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int handleStorytellerPropInitialize(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "eventTeamCleaupOverride") && !utils.hasScriptVar(self, "storytellerOnAttachFired"))
        {
            if (hasObjVar(self, "storytellerCreationTime"))
            {
                int storytellerCreationTime = getIntObjVar(self, "storytellerCreationTime");
                if (getGameTime() >= storytellerCreationTime + getStandardCleanupTime(self))
                {
                    messageTo(self, "cleanupProp", null, 1, false);
                }
                else 
                {
                    messageTo(self, "prepCleanupProp", null, getStandardCleanupTime(self) - (getGameTime() - storytellerCreationTime), false);
                }
            }
            else 
            {
                setObjVar(self, "storytellerCreationTime", getGameTime());
                messageTo(self, "startPropSetup", null, 1, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int startPropSetup(obj_id self, dictionary params) throws InterruptedException
    {
        setupProp();
        return SCRIPT_CONTINUE;
    }
    public void setupProp() throws InterruptedException
    {
        obj_id self = getSelf();
        if (!hasObjVar(self, "eventTeamCleaupOverride"))
        {
            int cleanup_time = getStandardCleanupTime(self);
            messageTo(self, "prepCleanupProp", null, cleanup_time, false);
        }
        return;
    }
    public int getStandardCleanupTime(obj_id prop_controller) throws InterruptedException
    {
        if (hasObjVar(prop_controller, "storytellerCleanUpTime"))
        {
            int cleanUpTime = getIntObjVar(prop_controller, "storytellerCleanUpTime");
            return cleanUpTime;
        }
        return storyteller.DEFAULT_PROP_CLEANUP_TIME;
    }
    public int cleanupProp(obj_id self, dictionary params) throws InterruptedException
    {
        trial.cleanupObject(self);
        return SCRIPT_CONTINUE;
    }
    public int prepCleanupProp(obj_id self, dictionary params) throws InterruptedException
    {
        storyteller.confirmCleanuptime(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id myStorytellerId = getObjIdObjVar(self, "storytellerid");
        if (myStorytellerId == player || myStorytellerId == utils.getObjIdScriptVar(player, "storytellerAssistant") || isGod(player))
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id("storyteller", "destroy_prop"));
            if (hasObjVar(self, storyteller.EFFECT_ACTIVE_OBJVAR))
            {
                mi.addRootMenu(menu_info_types.SERVER_MENU2, new string_id("storyteller", "remove_persisted_effect"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        obj_id myStorytellerId = getObjIdObjVar(self, "storytellerid");
        if (myStorytellerId == player || myStorytellerId == utils.getObjIdScriptVar(player, "storytellerAssistant") || isGod(player))
        {
            if (item == menu_info_types.SERVER_MENU1)
            {
                trial.cleanupObject(self);
            }
            else if (item == menu_info_types.SERVER_MENU2)
            {
                storyteller.removeStorytellerPersistedEffect(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRemoveStorytellerPersistedEffect(obj_id self, dictionary params) throws InterruptedException
    {
        storyteller.removeStorytellerPersistedEffect(self);
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id myStorytellerId = getObjIdObjVar(self, "storytellerid");
        if ((myStorytellerId == player || myStorytellerId == utils.getObjIdScriptVar(player, "storytellerAssistant") || isGod(player)))
        {
            if (hasObjVar(self, "storytellerCreationTime"))
            {
                int storytellerCreationTime = getIntObjVar(self, "storytellerCreationTime");
                int timeRemaining = storytellerCreationTime + getStandardCleanupTime(self) - getGameTime();
                names[idx] = "storyteller_time_remaining";
                attribs[idx] = utils.formatTimeVerbose(timeRemaining);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (hasObjVar(self, "storytellerName"))
        {
            String storytellerName = getStringObjVar(self, "storytellerName");
            if (storytellerName != null && storytellerName.length() > 0)
            {
                names[idx] = "storyteller_prop_owner";
                attribs[idx] = storytellerName;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        location death = getLocation(self);
        playClientEffectObj(killer, "clienteffect/combat_explosion_lair_large.cef", self, "");
        playClientEffectLoc(killer, "clienteffect/combat_explosion_lair_large.cef", death, 0);
        setInvulnerable(self, true);
        messageTo(self, "cleanupProp", null, 1f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDamaged(obj_id self, obj_id attacker, obj_id weapon, int damage) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int effectManager(obj_id self, dictionary params) throws InterruptedException
    {
        removeObjVar(self, "playingEffect");
        return SCRIPT_CONTINUE;
    }
    public int handleBlueprintElevation(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        storyteller.handleBlueprintObjectElevation(self, params);
        return SCRIPT_CONTINUE;
    }
    public void checkBonusCleanupTime(obj_id prop_controller) throws InterruptedException
    {
        storyteller.calculatePropBonusExistTime(prop_controller);
    }
    public int st_receivedCityResponse(obj_id self, dictionary params) throws InterruptedException
    {
        storyteller.setBonusExistTime(self);
        return SCRIPT_CONTINUE;
    }
}
