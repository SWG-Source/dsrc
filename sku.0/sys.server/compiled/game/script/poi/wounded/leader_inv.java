package script.poi.wounded;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.poi;
import script.library.scenario;
import script.library.healing;
import script.library.consumable;

public class leader_inv extends script.item.container.add_only
{
    public leader_inv()
    {
    }
    public static final int PROVIDED_MEDICINE = 4;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        obj_id actor = getContainedBy(self);
        if ((actor == null) || (actor == obj_id.NULL_ID))
        {
            return SCRIPT_OVERRIDE;
        }
        obj_id poiMaster = poi.getBaseObject(actor);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            LOG("poiWounded", "OnStartNpcConversation: unable to locate base object");
            return SCRIPT_CONTINUE;
        }
        String convo = getStringObjVar(poiMaster, scenario.VAR_SCENARIO_CONVO);
        if (convo.equals(""))
        {
            LOG("poiWounded", "OnStartNpcConversation: unable to determine conversation file");
            return SCRIPT_CONTINUE;
        }
        if (isPlayer(transferer))
        {
            if (!healing.isMedicine(item))
            {
                scenario.say(actor, convo, "item_not_medicine");
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        obj_id actor = getContainedBy(self);
        if ((actor == null) || (actor == obj_id.NULL_ID))
        {
            return SCRIPT_OVERRIDE;
        }
        obj_id poiMaster = poi.getBaseObject(actor);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            LOG("poiWounded", "OnStartNpcConversation: unable to locate base object");
            return SCRIPT_CONTINUE;
        }
        String convo = getStringObjVar(poiMaster, scenario.VAR_SCENARIO_CONVO);
        if (convo.equals(""))
        {
            LOG("poiWounded", "OnStartNpcConversation: unable to determine conversation file");
            return SCRIPT_CONTINUE;
        }
        if (isPlayer(transferer))
        {
            if (healing.isMedicine(item))
            {
                int cnt = getIntObjVar(item, consumable.VAR_CONSUMABLE_CHARGES);
                obj_id[] actors = scenario.getActorsWithNamePrefix(poiMaster, scenario.MEDIATOR);
                if ((actors == null) || (actors.length == 0))
                {
                }
                else 
                {
                    boolean assisted = false;
                    for (int i = 0; i < cnt; i++)
                    {
                        obj_id wounded = getFirstWoundedActor(actors);
                        if (wounded == null)
                        {
                            i = cnt;
                        }
                    }
                    if (assisted)
                    {
                        scenario.setPlayerProgress(transferer, PROVIDED_MEDICINE);
                        scenario.say(actor, convo, "m_reward");
                        int amt = 0;
                        attrib_mod[] mods = getAttribModArrayObjVar(item, consumable.VAR_CONSUMABLE_MODS);
                        if ((mods == null) || (mods.length == 0))
                        {
                        }
                        else 
                        {
                            for (int n = 0; n < mods.length; n++)
                            {
                                amt += mods[n].getValue();
                            }
                        }
                        amt *= cnt;
                        obj_id wounded = getFirstWoundedActor(actors);
                        if (wounded == null)
                        {
                            scenario.say(actor, convo, "m_all_healed");
                            poi.complete(poiMaster);
                            amt *= 1.25f;
                        }
                        amt /= 10;
                        dictionary d = new dictionary();
                        d.put("target", transferer);
                        d.put("amt", amt);
                        messageTo(actor, "giveReward", d, 0, false);
                    }
                    destroyObject(item);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean healActorWounds(obj_id actor, int[] wounds) throws InterruptedException
    {
        if ((actor == null) || (wounds == null) || (wounds.length == 0))
        {
            return false;
        }
        attribute[] maxAttribs = getUnmodifiedMaxAttribs(actor);
        for (int i = 0; i < maxAttribs.length; ++i)
        {
            int woundVal = wounds[i];
            if (woundVal > 0)
            {
                addAttribModifier(actor, maxAttribs[i].getType(), maxAttribs[i].getValue(), 0, 0, MOD_POOL);
            }
        }
        return true;
    }
    public obj_id getFirstWoundedActor(obj_id[] actors) throws InterruptedException
    {
        return null;
    }
}
