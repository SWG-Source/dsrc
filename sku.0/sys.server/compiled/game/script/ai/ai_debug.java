package script.ai;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.ai_lib;

public class ai_debug extends script.base_script
{
    public ai_debug()
    {
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        healShockWound(self, getShockWound(self));
        setAttrib(self, HEALTH, getMaxAttrib(self, HEALTH));
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        LOG("debug_ai", "OnSpeaking: " + text);
        if (isGod(self))
        {
            java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(text);
            if (tokenizer.hasMoreTokens())
            {
                String command = tokenizer.nextToken();
                LOG("debug_ai", "command is: " + command + " ---------------------------------------");
                if (command.equalsIgnoreCase("invulnerable"))
                {
                    final obj_id target = getLookAtTarget(self);
                    if (target != null)
                    {
                        setInvulnerable(target, !isInvulnerable(target));
                        LOG("debug_ai", "target(" + target + ") invulnerable(" + (isInvulnerable(target) ? "yes" : "no") + ")");
                    }
                }
                else if (command.equalsIgnoreCase("forceAction"))
                {
                    final obj_id target = getLookAtTarget(self);
                    if (target != null)
                    {
                        setObjVar(target, "ai.combat.forcedAction", "powerAttack");
                    }
                }
                else if (command.equalsIgnoreCase("tuskenline"))
                {
                    for (int i = 0; i < 5; ++i)
                    {
                        location spawnLocation = getLocation(self);
                        if ((i % 1) == 0)
                        {
                            spawnLocation.x += 3.0f * i;
                        }
                        else 
                        {
                            spawnLocation.x += -3.0f * i;
                        }
                        final obj_id ai = create.object("tusken_warrior", spawnLocation);
                        ai_lib.setDefaultCalmBehavior(ai, ai_lib.BEHAVIOR_SENTINEL);
                    }
                }
                else if (command.equalsIgnoreCase("tusken"))
                {
                    location spawnLocation = getLocation(self);
                    LOG("debug_ai", "tusken created @ (" + spawnLocation.x + ", " + spawnLocation.y + ", " + spawnLocation.z + ")");
                    final obj_id ai = create.object("tusken_warrior", spawnLocation);
                }
                else if (command.equalsIgnoreCase("stormtrooper"))
                {
                    final location spawnLocation = getLocation(self);
                    final obj_id ai = create.createCreature("stormtrooper", spawnLocation, true);
                    ai_lib.setDefaultCalmBehavior(ai, ai_lib.BEHAVIOR_SENTINEL);
                }
                else if (command.equalsIgnoreCase("attackMe"))
                {
                    final obj_id target = getLookAtTarget(self);
                    if (isValidId(target))
                    {
                        startCombat(target, self);
                    }
                    else 
                    {
                        debugSpeakMsg(self, "Specify a target");
                    }
                }
                else if (command.equalsIgnoreCase("jedifight"))
                {
                    location here = getLocation(self);
                    here.z = here.z + 2;
                    obj_id jedi = create.object("light_jedi_sentinel", here);
                    here.x = here.x + 2;
                    obj_id sith = create.object("dark_jedi_knight", here);
                    startCombat(jedi, sith);
                }
                else if (command.equalsIgnoreCase("gangfight"))
                {
                    
                    {
                        for (int i = 0; i < 20; ++i)
                        {
                            location spawnLocation = getLocation(self);
                            spawnLocation.x += rand(-20, 20);
                            spawnLocation.z += rand(-20, 20);
                            create.object("tusken_berserker", spawnLocation);
                        }
                    }
                    
                    {
                        for (int i = 0; i < 10; ++i)
                        {
                            location spawnLocation = getLocation(self);
                            spawnLocation.x += rand(-20, 20);
                            spawnLocation.z += rand(-20, 20);
                            create.object("arachne_warrior", spawnLocation);
                        }
                    }
                }
                else if (command.equalsIgnoreCase("group_tusken"))
                {
                    
                    {
                        for (int i = 0; i < 6; ++i)
                        {
                            location spawnLocation = getLocation(self);
                            spawnLocation.x += rand(-20, 20);
                            spawnLocation.z += rand(-20, 20);
                            create.object("tusken_berserker", spawnLocation);
                        }
                    }
                }
                else if (command.equalsIgnoreCase("group_stormtrooper"))
                {
                    
                    {
                        for (int i = 0; i < 20; ++i)
                        {
                            location spawnLocation = getLocation(self);
                            spawnLocation.x += rand(-10, 10);
                            spawnLocation.z += rand(-10, 10);
                            create.object("stormtrooper", spawnLocation);
                        }
                    }
                }
                else if (command.equalsIgnoreCase("trigger"))
                {
                    location spawnLocation = getLocation(self);
                    final obj_id ai = create.createCreature("blurrg_hunter", spawnLocation, true);
                    ai_lib.setDefaultCalmBehavior(ai, ai_lib.BEHAVIOR_SENTINEL);
                }
                else if (command.equalsIgnoreCase("loiterLocation"))
                {
                    final obj_id target = getLookAtTarget(self);
                    if (target != null)
                    {
                        ai_lib.setDefaultCalmBehavior(target, ai_lib.BEHAVIOR_LOITER);
                    }
                    debugConsoleMsg(self, "DONE");
                }
                else if (command.equalsIgnoreCase("heal"))
                {
                    final obj_id lookAtTarget = getLookAtTarget(self);
                    final obj_id healTarget = !isIdValid(lookAtTarget) ? self : lookAtTarget;
                    int maxHealth = 0;
                    if (isMob(healTarget))
                    {
                        maxHealth = getMaxHealth(healTarget);
                        setHealth(healTarget, maxHealth);
                    }
                    else 
                    {
                        maxHealth = getMaxHitpoints(healTarget);
                        setHitpoints(healTarget, maxHealth);
                    }
                    LOG("debug_ai", "healTarget(" + healTarget + ":" + getName(healTarget) + ") maxHealth(" + maxHealth + ")");
                    LOG("debug_ai", "lookAtTarget(" + lookAtTarget + ":" + getName(lookAtTarget) + ")");
                }
                else if (command.equalsIgnoreCase("patrol"))
                {
                    final location anchorLocation = getLocation(self);
                    final obj_id leader = create.object("stormtrooper", anchorLocation);
                    final float radius = 20.0f;
                    final int points = 10;
                    location path[] = new location[points];
                    for (int i = 0; i < points; ++i)
                    {
                        path[i] = new location();
                        final float radian = (float)Math.PI * 2.0f * ((float)i / (float)points);
                        path[i].x = anchorLocation.x + (float)Math.sin(radian) * radius;
                        path[i].y = anchorLocation.y;
                        path[i].z = anchorLocation.z + (float)Math.cos(radian) * radius;
                    }
                    ai_lib.setPatrolPath(leader, path);
                    ai_lib.resumePatrol(leader);
                    final int squadSize = 7;
                    for (int i = 1; i <= squadSize; ++i)
                    {
                        final obj_id follower = create.object("stormtrooper", anchorLocation);
                        ai_lib.followInFormation(follower, leader, ai_lib.FORMATION_WEDGE, i);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
