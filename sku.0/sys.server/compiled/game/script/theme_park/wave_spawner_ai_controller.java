package script.theme_park;

import script.dictionary;
import script.library.*;
import script.location;
import script.obj_id;
import script.string_id;

import java.util.Vector;

public class wave_spawner_ai_controller extends script.base_script
{
    public wave_spawner_ai_controller()
    {
    }
    public static final boolean LOGGING = true;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (getIntObjVar(self, restuss_event.PATROL_TYPE) > 0)
        {
            findWayPoints(self);
            messageTo(self, "pathToNextPoint", null, 2, false);
        }
        else 
        {
            ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        }
        trial.setInterest(self);
        messageTo(self, "spawnSquad", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        trial.cleanupObject(self);
        return SCRIPT_CONTINUE;
    }
    public int spawnSquad(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] children = trial.getChildrenInRange(self, self, 200.0f);
        if (children != null && children.length > 0)
        {
            trial.cleanupObject(children);
        }
        int squadSize = 4;
        if (hasObjVar(self, "squad_size"))
        {
            squadSize = getIntObjVar(self, "squad_size");
        }
        int formation = ai_lib.FORMATION_WEDGE;
        if (hasObjVar(self, "squad_formation"))
        {
            formation = getIntObjVar(self, "squad_formation");
        }
        obj_id[] squad = new obj_id[squadSize];
        location baseLoc = getLocation(self);
        String squadMember = "none";
        if (hasObjVar(self, "squad_member"))
        {
            squadMember = getStringObjVar(self, "squad_member");
        }
        if (squadMember.equals("none"))
        {
            return SCRIPT_CONTINUE;
        }
        if (dataTableGetString("datatables/mob/creatures.iff", squadMember, "creatureName") == null)
        {
            doLogging("spawnSquad", "Creature(" + squadMember + ") was not in the creatures datatable");
            return SCRIPT_CONTINUE;
        }
        obj_id[] allies = new obj_id[squadSize + 1];
        allies[0] = self;
        for (int i = 0; i < squad.length; i++)
        {
            location spawnLoc = new location(baseLoc.x - 5, baseLoc.y, baseLoc.z - 5, baseLoc.area, baseLoc.cell);
            squad[i] = create.object(squadMember, spawnLoc);
            if (isIdValid(squad[i]))
            {
                attachScript(squad[i], "theme_park.restuss_event.ai_controller");
                setMaster(squad[i], self);
                trial.setParent(self, squad[i], false);
                follow(squad[i], self, 4.0f, 4.0f);
                if (utils.hasScriptVar(self, restuss_event.PATROL_POINTS))
                {
                    utils.setScriptVar(squad[i], restuss_event.PATROL_POINTS, utils.getLocationArrayScriptVar(self, restuss_event.PATROL_POINTS));
                }
                trial.markAsTempObject(squad[i], true);
                allies[i + 1] = squad[i];
            }
        }
        ai_lib.establishAgroLink(self, allies);
        return SCRIPT_CONTINUE;
    }
    public void findWayPoints(obj_id self) throws InterruptedException
    {
        Vector wayPoints = utils.getResizeableObjIdArrayScriptVar(self, restuss_event.MASTER_PATROL_ARRAY);
        if (wayPoints == null || wayPoints.size() == 0)
        {
            doLogging("findWayPoints", "Waypoint list was empty, exiting");
            trial.cleanupObject(self);
            return;
        }
        int pathNum = 0;
        if (hasObjVar(self, "path"))
        {
            pathNum = getIntObjVar(self, "path");
        }
        else 
        {
            doLogging("findWayPoints", "I did not have the path ObjVar");
            trial.cleanupObject(self);
            return;
        }
        String myDataTable = getDataTable(self);
        if (!dataTableOpen(myDataTable))
        {
            return;
        }
        String path = dataTableGetString(getDataTable(self), pathNum, "path");
        String[] pathList = split(path, ';');
        if (pathList == null || pathList.length == 0)
        {
            doLogging("findWayPoints", "Path list was empty, exiting");
            trial.cleanupObject(self);
            return;
        }
        Vector myPath = new Vector();
        myPath.setSize(0);
        for (String s : pathList) {
            for (Object wayPoint : wayPoints) {
                if (hasObjVar(((obj_id) wayPoint), "wp_name")) {
                    if (s.equals(getStringObjVar(((obj_id) wayPoint), "wp_name"))) {
                        utils.addElement(myPath, getLocation(((obj_id) wayPoint)));
                    }
                }
            }
        }
        if (myPath == null || myPath.size() == 0)
        {
            doLogging("findWayPoints", "No waypoints were found, exiting");
            trial.cleanupObject(self);
            return;
        }
        location[] patrolPoints = new location[0];
        if (myPath != null)
        {
            patrolPoints = new location[myPath.size()];
            myPath.toArray(patrolPoints);
        }
        if (patrolPoints.length == 0)
        {
            doLogging("findWayPoints", "Patrol Point list was empty, exiting");
            trial.cleanupObject(self);
            return;
        }
        utils.setScriptVar(self, restuss_event.PATROL_POINTS, patrolPoints);
    }
    public int pathToNextPoint(obj_id self, dictionary params) throws InterruptedException
    {
        location[] patrolPoints = utils.getLocationArrayScriptVar(self, restuss_event.PATROL_POINTS);
        int patrolType = getIntObjVar(self, restuss_event.PATROL_TYPE);
        switch (patrolType)
        {
            case restuss_event.PATROL:
            ai_lib.setPatrolPath(self, patrolPoints);
            break;
            case restuss_event.PATROL_ONCE:
            ai_lib.setPatrolOncePath(self, patrolPoints);
            break;
            case restuss_event.PATROL_FLIP:
            ai_lib.setPatrolFlipPath(self, patrolPoints);
            break;
            case restuss_event.PATROL_FLIP_ONCE:
            ai_lib.setPatrolFlipOncePath(self, patrolPoints);
            break;
            case restuss_event.PATROL_RANDOM:
            ai_lib.setPatrolRandomPath(self, patrolPoints);
            break;
            case restuss_event.PATROL_RANDOM_ONCE:
            ai_lib.setPatrolRandomOncePath(self, patrolPoints);
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public String getDataTable(obj_id self) throws InterruptedException
    {
        return hasObjVar(self, "wave_spawner.data_table") ? getStringObjVar(self, "wave_spawner.data_table") : "noTable";
    }
    public int wsDoAnimation(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int wsPlayEmote(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int wsSignalMaster(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int wsDespawn(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "destroySelf", null, 5.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int wsToggleInvulnerability(obj_id self, dictionary params) throws InterruptedException
    {
        boolean toggleVulnerable = !isInvulnerable(self);
        setInvulnerable(self, toggleVulnerable);
        return SCRIPT_CONTINUE;
    }
    public int customSignal(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("doLogging", "in custom signal");
        String signalName = params.getString("triggerName");
        if (signalName == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, restuss_event.TRIG_CUSTOMSIGNAL))
        {
            Vector customTrigger = utils.getResizeableStringArrayScriptVar(self, restuss_event.TRIG_CUSTOMSIGNAL);
            if (customTrigger != null && customTrigger.size() > 0)
            {
                for (Object o : customTrigger) {
                    if (((String) o).startsWith(signalName)) {
                        executeTriggerData(self, ((String) o).substring(signalName.length() + 1, ((String) o).length()));
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        if (utils.hasScriptVar(self, restuss_event.TRIG_ONDEATH))
        {
            Vector deathTriggers = utils.getResizeableStringArrayScriptVar(self, restuss_event.TRIG_ONDEATH);
            if (deathTriggers != null && deathTriggers.size() > 0)
            {
                for (Object deathTrigger : deathTriggers) {
                    executeTriggerData(self, ((String) deathTrigger).concat(":" + killer));
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, restuss_event.TRIG_ENTERCOMBAT))
        {
            Vector combatTriggers = utils.getResizeableStringArrayScriptVar(self, restuss_event.TRIG_ENTERCOMBAT);
            if (combatTriggers != null && combatTriggers.size() > 0)
            {
                for (Object combatTrigger : combatTriggers) {
                    executeTriggerData(self, ((String) combatTrigger));
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        if (ai_lib.isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, restuss_event.TRIG_EXITCOMBAT))
        {
            Vector combatTriggers = utils.getResizeableStringArrayScriptVar(self, restuss_event.TRIG_EXITCOMBAT);
            if (combatTriggers != null && combatTriggers.size() > 0)
            {
                for (Object combatTrigger : combatTriggers) {
                    executeTriggerData(self, ((String) combatTrigger));
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, restuss_event.TRIG_ARRIVELOCATION))
        {
            Vector moveTriggers = utils.getResizeableStringArrayScriptVar(self, restuss_event.TRIG_ARRIVELOCATION);
            if (moveTriggers != null && moveTriggers.size() > 0)
            {
                for (Object moveTrigger : moveTriggers) {
                    executeTriggerData(self, ((String) moveTrigger));
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void executeTriggerData(obj_id self, String triggerData) throws InterruptedException
    {
        String[] parse = split(triggerData, ':');
        if (triggerData.startsWith("playEmote"))
        {
            String chatType = parse[1];
            String chatMessage = parse[2];
            chat.chat(self, chatType, new string_id("spam", chatMessage));
        }
        if (triggerData.startsWith("signalMaster"))
        {
            String handlerName = parse[1];
            dictionary dict = trial.getSessionDict(trial.getParent(self));
            if (!parse[2].equals("none"))
            {
                String[] valueSplit = split(parse[2], '=');
                if (valueSplit[0].equals("int"))
                {
                    dict.put(parse[1], utils.stringToInt(valueSplit[1]));
                }
                if (valueSplit[0].equals("float"))
                {
                    dict.put(parse[1], utils.stringToFloat(valueSplit[1]));
                }
                else 
                {
                    dict.put(parse[1], valueSplit[1]);
                }
            }
            messageTo(trial.getParent(self), handlerName, dict, 0.0f, false);
        }
        if (triggerData.startsWith("signalKiller"))
        {
        }
        if (triggerData.startsWith("doAnimationAction"))
        {
        }
        if (triggerData.startsWith("toggleInvulnerable"))
        {
            messageTo(self, "wsToggleInvulnerability", null, 0.0f, false);
        }
        if (triggerData.startsWith("broadcastMessage"))
        {
        }
        if (triggerData.startsWith("messageSelf"))
        {
            String handlerName = parse[1];
            dictionary dict = new dictionary();
            if (!parse[2].equals("none"))
            {
                String[] valueSplit = split(parse[3], '=');
                if (valueSplit[0].equals("int"))
                {
                    dict.put(parse[2], utils.stringToInt(valueSplit[1]));
                }
                if (valueSplit[0].equals("float"))
                {
                    dict.put(parse[2], utils.stringToFloat(valueSplit[1]));
                }
                else 
                {
                    dict.put(parse[2], valueSplit[1]);
                }
            }
            messageTo(self, handlerName, dict, 5.0f, false);
        }
        if (triggerData.startsWith("triggerId"))
        {
            dictionary dict = trial.getSessionDict(trial.getParent(self));
            dict.put("triggerName", parse[1]);
            dict.put("triggerType", "triggerId");
            messageTo(trial.getParent(self), "triggerFired", dict, 0.0f, false);
        }
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING)
        {
            LOG("doLogging/ai_controller/" + section, message);
        }
    }
}
