package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;
import script.library.poi;
import script.library.utils;
import script.library.chat;
import script.library.group;
import script.library.combat;
import script.library.ai_lib;
import script.ai.ai_combat;

public class scenario extends script.base_script
{
    public scenario()
    {
    }
    public static final String MEDIATOR = "mediator";
    public static final String ANTAGONIST = "antagonist";
    public static final String VAR_SCENARIO_BASE = "scenario";
    public static final String VAR_SCENARIO_IDX = VAR_SCENARIO_BASE + ".idx";
    public static final String VAR_SCENARIO_NAME = VAR_SCENARIO_BASE + ".name";
    public static final String VAR_SCENARIO_CONVO = VAR_SCENARIO_BASE + ".convo";
    public static final String VAR_SCENARIO_PLAYERS = VAR_SCENARIO_BASE + ".players";
    public static final String VAR_TEAM = VAR_SCENARIO_BASE + ".team";
    public static final String VAR_SCENARIO_MEDIATOR_TYPE = VAR_SCENARIO_BASE + ".mediator_type";
    public static final String VAR_SCENARIO_ANTAGONIST_TYPE = VAR_SCENARIO_BASE + ".antagonist_type";
    public static final String VAR_MEDIATOR_COUNT = VAR_SCENARIO_BASE + ".mediator_count";
    public static final String VAR_ANTAGONIST_COUNT = VAR_SCENARIO_BASE + ".antagonist_count";
    public static final String VAR_MEDIATOR_LOC = VAR_SCENARIO_BASE + ".mediator_loc";
    public static final String VAR_ANTAGONIST_LOC = VAR_SCENARIO_BASE + ".antagonist_loc";
    public static final String VAR_SCENARIO_DEAD = VAR_SCENARIO_BASE + ".dead";
    public static final String VAR_SCENARIO_DEAD_ANTAGONIST = VAR_SCENARIO_BASE + ".antagonist_dead";
    public static final String VAR_SCENARIO_DEAD_MEDIATOR = VAR_SCENARIO_BASE + ".mediator_dead";
    public static final String VAR_SCENARIO_COMPLETE = VAR_SCENARIO_BASE + ".complete";
    public static final String VAR_TARGET_LOCATION = VAR_SCENARIO_BASE + ".targetLoc";
    public static final String VAR_TARGET_LOCATION_NAME = VAR_SCENARIO_BASE + ".targetLocName";
    public static final String VAR_MY_NAME = VAR_SCENARIO_BASE + ".myName";
    public static final String VAR_RANK = VAR_SCENARIO_BASE + ".rank";
    public static final String VAR_HAS_ARRIVED = VAR_SCENARIO_BASE + ".hasArrived";
    public static final String VAR_PRIMARY_KILLERS = VAR_SCENARIO_BASE + ".primaryKillers";
    public static final String VAR_PROGRESS = "progress";
    public static final String HANDLER_INIT_SCENARIO = "initScenario";
    public static final String HANDLER_RUN_SCENARIO = "runScenario";
    public static final String HANDLER_CLEANUP_SCENARIO = "cleanupScenario";
    public static final String HANDLER_DESTROY_SELF = "destroySelf";
    public static final String HANDLER_TIMER = "handleTimer";
    public static final String HANDLER_PREP_TIMER = "handlePrepTimer";
    public static final String HANDLER_ATTACK_TIMER = "handleAttackTimer";
    public static final String HANDLER_FOLLOW_TIMER = "handleFollowTimer";
    public static final String HANDLER_CHARGE = "handleCharge";
    public static final String HANDLER_ENEMY_ATTACK = "handleEnemyAttack";
    public static final String HANDLER_INCAPACITATION = "handleIncapacitation";
    public static final String HANDLER_MY_DEATH = "handleMyDeath";
    public static final String HANDLER_ACTOR_DEATH = "handleActorDeath";
    public static final String HANDLER_VICTORY = "handleVictory";
    public static final String HANDLER_ADD_TARGET_LOCATION = "handleAddTargetLocation";
    public static final String HANDLER_ONLINE_STATUS_UPDATE = "handleOnlineStatusUpdate";
    public static final int NOT_STARTED = 0;
    public static final String COL_NAME = "SCENARIO_NAME";
    public static final String COL_MEDIATOR = "MEDIATOR_TEMPLATE";
    public static final String COL_MEDIATOR_MINION = "MEDIATOR_MINION_TEMPLATE";
    public static final String COL_MEDIATOR_STRUCTURE = "MEDIATOR_STRUCTURE_TEMPLATE";
    public static final String COL_ANTAGONIST = "ANTAGONIST_TEMPLATE";
    public static final String COL_ANTAGONIST_MINION = "ANTAGONIST_MINION_TEMPLATE";
    public static final String COL_ANTAGONIST_STRUCTURE = "ANTAGONIST_STRUCTURE_TEMPLATE";
    public static final String COL_CONTENDED = "CONTENDED";
    public static final String COL_CONVO = "CONVO_FILE";
    public static final String COL_TOOL = "TOOL";
    public static final String COL_TABLE = "DATATABLE";
    public static final String COL_RING_MIN = "RING_MIN";
    public static final String COL_RING_MAX = "RING_MAX";
    public static final String COL_THEATER = "THEATER";
    public static final String KEY_RANDOM = "random";
    public static final String KEY_NONE = "none";
    public static final String KEY_UNKNOWN = "unknown";
    public static final String DICT_IDX = "idx";
    public static final String DICT_LOC_NAME = "locName";
    public static final String DICT_LOCATION = "location";
    public static final String DICT_RADIUS = "radius";
    public static final String DICT_NAME = "name";
    public static final String DICT_OBJID = "objid";
    public static final String DICT_STATUS = "status";
    public static final int SR_MEDIATOR = 0;
    public static final int SR_ANTAGONIST = 1;
    public static final int SR_MEDIATOR_MINION = 2;
    public static final int SR_ANTAGONIST_MINION = 3;
    public static final int OFFLINE = 0;
    public static final int ONLINE = 1;
    public static final String DEFAULT_CONVO = "poi/default";
    public static final String VAR_UNLOAD_NUKE = "nukeOnUnload";
    public static boolean initScenario(obj_id master, String tbl) throws InterruptedException
    {
        if (!isIdValid(master) || tbl == null || tbl.equals(""))
        {
            return false;
        }
        dictionary params = getRandomScenario(tbl);
        if ((params == null) || (params.isEmpty()))
        {
            return false;
        }
        int idx = params.getInt(DICT_IDX);
        String scenario_name = params.getString(COL_NAME);
        String convo = params.getString(COL_CONVO);
        if (hasObjVar(master, VAR_SCENARIO_BASE))
        {
            removeObjVar(master, VAR_SCENARIO_BASE);
        }
        setObjVar(master, VAR_SCENARIO_IDX, idx);
        setObjVar(master, VAR_SCENARIO_NAME, scenario_name);
        setObjVar(master, VAR_SCENARIO_CONVO, convo);
        return messageTo(master, HANDLER_INIT_SCENARIO, params, 2, true);
    }
    public static boolean createTeam(obj_id master, String team_name, String team_faction) throws InterruptedException
    {
        if (!isIdValid(master))
        {
            return false;
        }
        if ((team_name == null) || (team_name.equals("")))
        {
            return false;
        }
        if ((team_faction == null) || (team_faction.equals("")))
        {
            return false;
        }
        String team_path = VAR_TEAM + "." + team_name;
        setObjVar(master, team_path + ".faction", team_faction);
        setObjVar(master, team_path + ".dead_count", 0);
        setObjVarList(master, team_path + ".kill_credits");
        return true;
    }
    public static boolean setTeamFaction(obj_id master, String team_name, String team_faction) throws InterruptedException
    {
        if (!isIdValid(master))
        {
            return false;
        }
        if ((team_name == null) || (team_name.equals("")))
        {
            return false;
        }
        if ((team_faction == null) || (team_faction.equals("")))
        {
            return false;
        }
        String team_path = VAR_TEAM + "." + team_name;
        String member_path = team_path + ".members";
        Vector team_members = getResizeableObjIdArrayObjVar(master, member_path);
        if (team_members != null && team_members.size() > 0)
        {
            for (int i = 0; i < team_members.size(); i++)
            {
                obj_id target = ((obj_id)team_members.get(i));
                if (isIdValid(target))
                {
                    factions.setFaction(target, team_faction);
                }
            }
        }
        return setObjVar(master, team_path + ".faction", team_faction);
    }
    public static String getTeamFaction(obj_id master, String team_name) throws InterruptedException
    {
        if (!isIdValid(master))
        {
            return null;
        }
        if ((team_name == null) || (team_name.equals("")))
        {
            return null;
        }
        String team_path = VAR_TEAM + "." + team_name;
        String fac_path = team_path + ".faction";
        return getStringObjVar(master, VAR_TEAM + "." + team_name + ".faction");
    }
    public static obj_id createTeamNpc(obj_id master, String team_name, String type, String ident, location here) throws InterruptedException
    {
        if (!isIdValid(master) || here == null || type == null || type.equals(""))
        {
            return null;
        }
        if (team_name == null || team_name.equals(""))
        {
            return null;
        }
        if (!hasObjVar(master, VAR_TEAM + "." + team_name))
        {
            return null;
        }
        Vector team_members = getResizeableObjIdArrayObjVar(master, VAR_TEAM + "." + team_name + ".members");
        Vector member_alive = getResizeableIntArrayObjVar(master, VAR_TEAM + "." + team_name + ".member_alive");
        if ((ident == null) || (ident.equals("")))
        {
            if (team_members == null)
            {
                ident = team_name + "_0";
            }
            else 
            {
                ident = team_name + team_members.size();
            }
        }
        obj_id npc = poi.createNpc(master, ident, type, here);
        if (!isIdValid(npc))
        {
            return null;
        }
        setObjVar(npc, VAR_TEAM, team_name);
        String team_faction = getTeamFaction(master, team_name);
        if ((team_faction != null) && (!team_faction.equals("")))
        {
            String oldFaction = factions.getFaction(npc);
            if ((oldFaction == null) || ((!oldFaction.equals("Imperial")) && (!oldFaction.equals("Rebel"))))
            {
                factions.setFaction(npc, team_faction);
            }
        }
        team_members = utils.addElement(team_members, npc);
        member_alive = utils.addElement(member_alive, 1);
        if (team_members != null && team_members.size() > 0 && member_alive != null && member_alive.size() > 0)
        {
            setObjVar(master, VAR_TEAM + "." + team_name + ".members", team_members);
            setObjVar(master, VAR_TEAM + "." + team_name + ".member_alive", member_alive);
        }
        faceTo(npc, master);
        return npc;
    }
    public static String getTeamName(obj_id actor) throws InterruptedException
    {
        if (!isIdValid(actor))
        {
            return null;
        }
        if (exists(actor) && actor.isLoaded())
        {
            return getStringObjVar(actor, VAR_TEAM);
        }
        else 
        {
            obj_id self = getSelf();
            obj_id master = poi.getBaseObject(self);
            if ((master == null) || (master == obj_id.NULL_ID))
            {
                return null;
            }
            obj_var_list teamsList = getObjVarList(master, VAR_TEAM);
            if (teamsList != null)
            {
                int teamCount = teamsList.getNumItems();
                for (int i = 0; i < teamCount; i++)
                {
                    obj_var ov = teamsList.getObjVar(i);
                    obj_var_list team = (obj_var_list)(ov);
                    if (team != null)
                    {
                        if (team.hasObjVar("members." + actor))
                        {
                            return team.getName();
                        }
                    }
                }
            }
        }
        return null;
    }
    public static boolean isActorAlive(obj_id actor) throws InterruptedException
    {
        if (!isIdValid(actor))
        {
            return false;
        }
        obj_id master = poi.getBaseObject(getSelf());
        if (!isIdValid(master))
        {
            return false;
        }
        String team_name = getTeamName(actor);
        Vector team_members = getResizeableObjIdArrayObjVar(master, VAR_TEAM + "." + team_name + ".members");
        int idx = findTeamMember(team_members, actor);
        if (idx != -1)
        {
            Vector member_alive = getResizeableIntArrayObjVar(master, VAR_TEAM + "." + team_name + ".member_alive");
            return (((Integer)member_alive.get(idx)).intValue() == 1);
        }
        return false;
    }
    public static boolean setActorDead(obj_id actor, obj_id[] killers) throws InterruptedException
    {
        if (!isIdValid(actor))
        {
            return false;
        }
        obj_id master = poi.getBaseObject(getSelf());
        if (!isIdValid(master))
        {
            return false;
        }
        String team_name = getTeamName(actor);
        Vector team_members = getResizeableObjIdArrayObjVar(master, VAR_TEAM + "." + team_name + ".members");
        if (team_members == null || team_members.size() == 0)
        {
            return false;
        }
        int idx = findTeamMember(team_members, actor);
        if (idx != -1)
        {
            Vector member_alive = getResizeableIntArrayObjVar(master, VAR_TEAM + "." + team_name + ".member_alive");
            if (member_alive != null && member_alive.size() > 0)
            {
                member_alive.set(idx, new Integer(0));
                setObjVar(master, VAR_TEAM + "." + team_name + ".member_alive", member_alive);
            }
            else 
            {
                return false;
            }
            grantKillCredit(master, team_name, killers);
            return true;
        }
        return false;
    }
    public static boolean setActorDead(obj_id actor) throws InterruptedException
    {
        return setActorDead(actor, null);
    }
    public static obj_id[] getTeamMembers(obj_id master, String team_name) throws InterruptedException
    {
        if (!isIdValid(master) || team_name == null || team_name.equals(""))
        {
            return null;
        }
        if (!hasObjVar(master, VAR_TEAM + "." + team_name))
        {
            return null;
        }
        return getObjIdArrayObjVar(master, VAR_TEAM + "." + team_name + ".members");
    }
    public static obj_id[] getTeamMembers(String team_name) throws InterruptedException
    {
        return getTeamMembers(poi.getBaseObject(getSelf()), team_name);
    }
    public static obj_id[] getAliveTeamMembers(obj_id master, String team_name) throws InterruptedException
    {
        if (!isIdValid(master) || team_name == null || team_name.equals(""))
        {
            return null;
        }
        if (!hasObjVar(master, VAR_TEAM + "." + team_name))
        {
            return null;
        }
        obj_id[] team_members = getObjIdArrayObjVar(master, VAR_TEAM + "." + team_name + ".members");
        int[] member_alive = getIntArrayObjVar(master, VAR_TEAM + "." + team_name + ".member_alive");
        Vector members = new Vector();
        members.setSize(0);
        for (int i = 0; i < team_members.length; i++)
        {
            if (member_alive[i] == 1)
            {
                members = utils.addElement(members, team_members[i]);
            }
        }
        obj_id[] _members = new obj_id[0];
        if (members != null)
        {
            _members = new obj_id[members.size()];
            members.toArray(_members);
        }
        return _members;
    }
    public static obj_id[] getDeadTeamMembers(obj_id master, String team_name) throws InterruptedException
    {
        if (!isIdValid(master) || team_name == null || team_name.equals(""))
        {
            return null;
        }
        if (!hasObjVar(master, VAR_TEAM + "." + team_name))
        {
            return null;
        }
        obj_id[] team_members = getObjIdArrayObjVar(master, VAR_TEAM + "." + team_name + ".members");
        int[] member_alive = getIntArrayObjVar(master, VAR_TEAM + "." + team_name + ".member_alive");
        Vector members = new Vector();
        members.setSize(0);
        for (int i = 0; i < team_members.length; i++)
        {
            if (member_alive[i] == 0)
            {
                members = utils.addElement(members, team_members[i]);
            }
        }
        obj_id[] _members = new obj_id[0];
        if (members != null)
        {
            _members = new obj_id[members.size()];
            members.toArray(_members);
        }
        return _members;
    }
    public static int getAliveTeamMemberCount(obj_id master, String team_name) throws InterruptedException
    {
        obj_id[] members = getAliveTeamMembers(master, team_name);
        if ((members == null) || (members.length == 0))
        {
            return -1;
        }
        return members.length;
    }
    public static int getDeadTeamMemberCount(obj_id master, String team_name) throws InterruptedException
    {
        obj_id[] members = getDeadTeamMembers(master, team_name);
        if ((members == null) || (members.length == 0))
        {
            return -1;
        }
        return members.length;
    }
    public static obj_id getRandomTeamMember(obj_id master, String team_name) throws InterruptedException
    {
        if (!isIdValid(master) || team_name == null || team_name.equals(""))
        {
            return null;
        }
        if (!hasObjVar(master, VAR_TEAM + "." + team_name))
        {
            return null;
        }
        obj_id[] members = getAliveTeamMembers(master, team_name);
        if ((members == null) || (members.length == 0))
        {
            return null;
        }
        int idx = rand(0, members.length - 1);
        return members[idx];
    }
    public static obj_id getRandomAliveTeamMember(obj_id master, String team_name) throws InterruptedException
    {
        if (!isIdValid(master) || team_name == null || team_name.equals(""))
        {
            return null;
        }
        if (!hasObjVar(master, VAR_TEAM + "." + team_name))
        {
            return null;
        }
        obj_id[] members = getAliveTeamMembers(master, team_name);
        if ((members == null) || (members.length == 0))
        {
            return null;
        }
        int idx = rand(0, members.length - 1);
        return members[idx];
    }
    public static int getTeamCount(obj_id master, String team_name) throws InterruptedException
    {
        if (!isIdValid(master) || team_name == null || team_name.equals(""))
        {
            return -1;
        }
        if (!hasObjVar(master, VAR_TEAM + "." + team_name))
        {
            return -1;
        }
        obj_id[] team_members = getObjIdArrayObjVar(master, VAR_TEAM + "." + team_name + ".members");
        if ((team_members == null) || (team_members.length == 0))
        {
            return -1;
        }
        return team_members.length;
    }
    public static int findTeamMember(Vector team, obj_id member) throws InterruptedException
    {
        for (int i = 0; i < team.size(); i++)
        {
            if (((obj_id)team.get(i)) == member)
            {
                return i;
            }
        }
        return -1;
    }
    public static boolean grantKillCredit(obj_id master, String team_name, obj_id[] killers) throws InterruptedException
    {
        if ((master == null) || (master == obj_id.NULL_ID))
        {
            return false;
        }
        if ((team_name == null) || (team_name.equals("")))
        {
            return false;
        }
        if ((killers == null) || (killers.length == 0))
        {
            return false;
        }
        String creditPath = VAR_TEAM + "." + team_name + ".kill_credits";
        for (int i = 0; i < killers.length; i++)
        {
            LOG("SCENARIO", "granting kill credit to " + getName(killers[i]) + " " + team_name);
            String path = creditPath + "." + killers[i];
            int cnt = 0;
            if (hasObjVar(master, path))
            {
                cnt = getIntObjVar(master, path);
            }
            cnt++;
            setObjVar(master, path, cnt);
        }
        return true;
    }
    public static boolean hasKillCredit(obj_id master, String team_name, obj_id killer) throws InterruptedException
    {
        if ((master == null) || (master == obj_id.NULL_ID))
        {
            return false;
        }
        if ((team_name == null) || (team_name.equals("")))
        {
            return false;
        }
        if ((killer == null) || (killer == obj_id.NULL_ID))
        {
            return false;
        }
        String creditPath = VAR_TEAM + "." + team_name + ".kill_credits." + killer;
        return hasObjVar(master, creditPath);
    }
    public static String getConvo() throws InterruptedException
    {
        obj_id self = getSelf();
        obj_id master = poi.getBaseObject(self);
        if ((master == null) || (master == obj_id.NULL_ID))
        {
            return "";
        }
        if (hasObjVar(master, VAR_SCENARIO_CONVO))
        {
            return getStringObjVar(master, VAR_SCENARIO_CONVO);
        }
        return "";
    }
    public static boolean say(obj_id npc, String convo, String base_id, boolean doAct) throws InterruptedException
    {
        if ((npc == null) || (npc == obj_id.NULL_ID) || (convo.equals("")) || (base_id.equals("")))
        {
            return false;
        }
        string_id msg = new string_id(convo, base_id);
        String chatType = getString(new string_id(convo, base_id + "_chat"));
        String mood = getString(new string_id(convo, base_id + "_mood"));
        String act = getString(new string_id(convo, base_id + "_act"));
        if ((chatType == null) || chatType.equals(""))
        {
            chatType = chat.CHAT_SAY;
        }
        if ((mood == null) || mood.equals(""))
        {
            mood = "none";
        }
        chat.chat(npc, chatType, mood, msg);
        if (doAct)
        {
            if (!act.equals(""))
            {
                queueCommand(npc, (1780871594), null, act, COMMAND_PRIORITY_DEFAULT);
            }
        }
        return true;
    }
    public static boolean say(obj_id npc, String convo, String base_id) throws InterruptedException
    {
        return say(npc, convo, base_id, true);
    }
    public static boolean sayNoAct(obj_id npc, String convo, String base_id) throws InterruptedException
    {
        return say(npc, convo, base_id, false);
    }
    public static boolean brainfart(obj_id npc) throws InterruptedException
    {
        return say(npc, DEFAULT_CONVO, "brainfart_" + rand(1, 10));
    }
    public static boolean complete(obj_id poiMaster) throws InterruptedException
    {
        if (poiMaster == null)
        {
            return false;
        }
        setObjVar(poiMaster, VAR_SCENARIO_COMPLETE, true);
        poi.complete(poiMaster);
        return true;
    }
    public static boolean complete() throws InterruptedException
    {
        obj_id self = getSelf();
        obj_id master = poi.getBaseObject(self);
        if ((master == null) || (master == obj_id.NULL_ID))
        {
            return false;
        }
        return complete(master);
    }
    public static boolean isComplete(obj_id poiMaster) throws InterruptedException
    {
        if (poiMaster == null)
        {
            return false;
        }
        return getBooleanObjVar(poiMaster, VAR_SCENARIO_COMPLETE);
    }
    public static boolean isComplete() throws InterruptedException
    {
        obj_id self = getSelf();
        obj_id master = poi.getBaseObject(self);
        if ((master == null) || (master == obj_id.NULL_ID))
        {
            return false;
        }
        return isComplete(master);
    }
    public static boolean setPlayerProgress(obj_id player, int progress) throws InterruptedException
    {
        if ((player == null) || (!isPlayer(player)))
        {
            return false;
        }
        obj_id self = getSelf();
        obj_id master = poi.getBaseObject(self);
        if ((master == null) || (master == obj_id.NULL_ID))
        {
            return false;
        }
        String path = VAR_SCENARIO_PLAYERS + "." + player;
        return setObjVar(master, path, progress);
    }
    public static boolean clearPlayerProgress(obj_id player) throws InterruptedException
    {
        if ((player == null) || (!isPlayer(player)))
        {
            return false;
        }
        obj_id self = getSelf();
        obj_id master = poi.getBaseObject(self);
        if ((master == null) || (master == obj_id.NULL_ID))
        {
            return false;
        }
        String path = VAR_SCENARIO_PLAYERS + "." + player;
        removeObjVar(master, path);
        return true;
    }
    public static int getPlayerProgress(obj_id player) throws InterruptedException
    {
        if ((player == null) || (!isPlayer(player)))
        {
            return -1;
        }
        obj_id self = getSelf();
        obj_id master = poi.getBaseObject(self);
        if ((master == null) || (master == obj_id.NULL_ID))
        {
            return -1;
        }
        String path = VAR_SCENARIO_PLAYERS + "." + player;
        return getIntObjVar(master, path);
    }
    public static obj_id[] getActorsWithNamePrefix(obj_id poiMaster, String prefix) throws InterruptedException
    {
        if ((poiMaster == null) || (prefix.equals("")))
        {
            return null;
        }
        Vector actors = new Vector();
        actors.setSize(0);
        if (hasObjVar(poiMaster, "poi"))
        {
            obj_var_list ovl = getObjVarList(poiMaster, "poi.stringList");
            if (ovl != null)
            {
                int numItems = ovl.getNumItems();
                if (numItems > 0)
                {
                    for (int i = 0; i < numItems; i++)
                    {
                        obj_var ov = ovl.getObjVar(i);
                        String ovName = ov.getName();
                        if (ovName.startsWith(prefix))
                        {
                            obj_id tmp = getObjIdObjVar(poiMaster, "poi.stringList." + ovName);
                            if ((tmp == null) || (tmp == obj_id.NULL_ID))
                            {
                            }
                            else 
                            {
                                actors = utils.addElement(actors, tmp);
                            }
                        }
                    }
                }
            }
        }
        if ((actors == null) || (actors.size() == 0))
        {
            return null;
        }
        else 
        {
            obj_id[] _actors = new obj_id[0];
            if (actors != null)
            {
                _actors = new obj_id[actors.size()];
                actors.toArray(_actors);
            }
            return _actors;
        }
    }
    public static boolean cleanup(obj_id poiMaster) throws InterruptedException
    {
        if (poiMaster == null)
        {
            return false;
        }
        destroyObject(poiMaster);
        return true;
    }
    public static dictionary getRandomScenario(String tbl) throws InterruptedException
    {
        if (tbl.equals(""))
        {
            return null;
        }
        if (dataTableOpen(tbl))
        {
            int numRows = dataTableGetNumRows(tbl);
            int idx = -1;
            if (numRows < 1)
            {
                return null;
            }
            else if (numRows == 1)
            {
                idx = 0;
            }
            else 
            {
                idx = rand(0, numRows - 1);
            }
            dictionary ret = dataTableGetRow(tbl, idx);
            if (ret != null)
            {
                ret.put(DICT_IDX, idx);
                return ret;
            }
        }
        return null;
    }
    public static boolean requestAddTargetLocation(obj_id target, String locName, location pathLoc, float r) throws InterruptedException
    {
        if ((target == null) || (locName.equals("")) || (pathLoc == null) || (r < 0))
        {
            return false;
        }
        dictionary d = new dictionary();
        d.put(DICT_LOC_NAME, locName);
        d.put(DICT_LOCATION, pathLoc);
        d.put(DICT_RADIUS, r);
        return messageTo(target, HANDLER_ADD_TARGET_LOCATION, d, 0, isObjectPersisted(target));
    }
    public static boolean actorIncapacitated(obj_id self) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return false;
        }
        obj_id poiMaster = poi.getBaseObject(self);
        if (!isIdValid(poiMaster))
        {
            return false;
        }
        obj_id[] primaryKillers = getObjIdArrayObjVar(self, VAR_PRIMARY_KILLERS);
        if ((primaryKillers == null) || (primaryKillers.length == 0))
        {
        }
        else 
        {
            for (int i = 0; i < primaryKillers.length; i++)
            {
                obj_id pk = primaryKillers[i];
                if ((pk == null) || (pk == obj_id.NULL_ID))
                {
                }
                else 
                {
                    if (isPlayer(pk))
                    {
                        obj_id groupId = getGroupObject(pk);
                        if (isIdValid(groupId))
                        {
                            poi.grantCredit(self, pk);
                        }
                        else 
                        {
                            obj_id[] members = getGroupMemberIds(groupId);
                            if (members != null && members.length > 0)
                            {
                                for (int n = 0; i < members.length; i++)
                                {
                                    poi.grantCredit(self, members[n]);
                                }
                            }
                        }
                    }
                    else 
                    {
                        if (group.isGroupObject(pk))
                        {
                            obj_id[] members = getGroupMemberIds(pk);
                            if ((members == null) || (members.length == 0))
                            {
                            }
                            else 
                            {
                                for (int n = 0; i < members.length; i++)
                                {
                                    poi.grantCredit(self, members[n]);
                                }
                            }
                        }
                        else 
                        {
                        }
                    }
                }
            }
        }
        String myName = getStringObjVar(self, VAR_MY_NAME);
        if (!myName.equals(""))
        {
            dictionary d = new dictionary();
            d.put(DICT_NAME, myName);
            d.put(DICT_OBJID, self);
            messageTo(poiMaster, HANDLER_ACTOR_DEATH, d, 0, true);
            return true;
        }
        return false;
    }
    public static void groupAttack(obj_id self, obj_id target) throws InterruptedException
    {
        startCombat(self, target);
    }
    public static void groupFace(obj_id self, obj_id target, String prefix) throws InterruptedException
    {
        obj_id poiMaster = poi.getBaseObject(self);
        if (isIdValid(poiMaster))
        {
            obj_id[] members = getActorsWithNamePrefix(poiMaster, prefix);
            if ((members == null) || (members.length == 0))
            {
            }
            else 
            {
                for (int i = 0; i < members.length; i++)
                {
                    faceTo(members[i], target);
                }
            }
        }
        faceTo(self, target);
    }
    public static void groupFollow(obj_id leader, obj_id formTarget, String prefix) throws InterruptedException
    {
        obj_id poiMaster = poi.getBaseObject(leader);
        if (isIdValid(poiMaster))
        {
            int formation = rand(0, 1);
            obj_id[] members = getActorsWithNamePrefix(poiMaster, prefix);
            if ((members == null) || (members.length == 0))
            {
            }
            else 
            {
                int pos = 1;
                for (int i = 0; i < members.length; i++)
                {
                    if ((members[i] == leader) && (leader != formTarget))
                    {
                        ai_lib.aiFollow(leader, formTarget);
                    }
                    else 
                    {
                        ai_lib.followInFormation(members[i], leader, formation, pos);
                        pos++;
                    }
                }
            }
        }
    }
}
