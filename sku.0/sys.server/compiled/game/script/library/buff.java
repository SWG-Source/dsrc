package script.library;

import script.combat_engine;
import script.combat_engine.buff_data;
import script.deltadictionary;
import script.obj_id;

import java.util.Vector;

public class buff extends script.base_script
{
    public buff()
    {
    }
    public static final int MAX_EFFECTS = 5;
    public static final int GROUP_BUFF_DISTANCE = 100;
    public static final String BUFF_TABLE = "datatables/buff/buff.iff";
    public static final String DEBUFF_STATE_PARALYZED = "buff.state.paralyzed";
    public static final String AGGRO_TRANSFER_TO = "aggroBuffTransfer";
    public static final String DOT_BLEEDING = "dot_bleeding";
    public static final String DOT_POISON = "dot_poison";
    public static final String DOT_DISEASE = "dot_disease";
    public static final String DOT_FIRE = "dot_fire";
    public static final String DOT_ACID = "dot_acid";
    public static final String DOT_ENERGY = "dot_energy";
    public static final String DOT_COLD = "dot_cold";
    public static final String DOT_ELECTRICITY = "dot_electricity";
    public static final String DOT_KINETIC = "dot_kinetic";
    public static final String ON_ATTACK_REMOVE = "onAttackRemoveBuffList";
    public static final int STATE_NONE = -1;
    public static final int STATE_COVER = 0;
    public static final int STATE_ALERT = 4;
    public static final int STATE_BERSERK = 5;
    public static final int STATE_FEIGN_DEATH = 6;
    public static final int STATE_TUMBLING = 10;
    public static final int STATE_RALLIED = 11;
    public static final int STATE_STUNNED = 12;
    public static final int STATE_BLINDED = 13;
    public static final int STATE_DIZZY = 14;
    public static final int STATE_INTIMIDATED = 15;
    public static final int STATE_IMMOBILIZED = 16;
    public static final int STATE_FROZEN = 17;
    public static final int BUFF_DOT_TICK = 2;
    public static boolean isParalyzed(obj_id target) throws InterruptedException
    {
        deltadictionary dd = target.getScriptVars();
        java.util.Enumeration keys = dd.keys();
        while (keys.hasMoreElements())
        {
            String key = (String)(keys.nextElement());
            if (key.startsWith(DEBUFF_STATE_PARALYZED))
            {
                return true;
            }
        }
        return false;
    }
    public static boolean canApplyBuff(obj_id target, String name) throws InterruptedException
    {
        return canApplyBuff(target, null, getStringCrc(name.toLowerCase()));
    }
    public static boolean canApplyBuff(obj_id target, obj_id owner, String name) throws InterruptedException
    {
        return canApplyBuff(target, owner, getStringCrc(name.toLowerCase()));
    }
    public static boolean canApplyBuff(obj_id target, int nameCrc) throws InterruptedException
    {
        return canApplyBuff(target, null, nameCrc);
    }
    public static boolean canApplyBuff(obj_id target, obj_id owner, int nameCrc) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if (!isPlayer(target) && !isMob(target))
        {
            return false;
        }
        buff_data bdata = combat_engine.getBuffData(nameCrc);
        if (bdata == null)
        {
            return false;
        }
        if (hasBuff(target, nameCrc))
        {
            return true;
        }
        if (checkForStateImmunity(target, bdata))
        {
            return false;
        }
        int buffCRCs[] = _getAllBuffs(target);
        if (buffCRCs == null || buffCRCs.length == 0)
        {
            return true;
        }
        int[] groups = getGroups(bdata);
        if (groups == null || groups.length != 3)
        {
            return false;
        }
        int groupOne = groups[0];
        int groupTwo = groups[1];
        if (groupOne != 0 || groupTwo != 0)
        {
            int priority = bdata.priority;
            buff_data oldBuffData;

            for (int buffCRC : buffCRCs) {
                oldBuffData = combat_engine.getBuffData(buffCRC);
                if (oldBuffData == null) {
                    continue;
                }
                int oldPriority = oldBuffData.priority;
                if (priority < oldPriority) {
                    int[] oldGroups = getGroups(oldBuffData);
                    if (oldGroups == null || oldGroups.length != 3) {
                        continue;
                    }
                    int oldGroupOne = oldGroups[0];
                    int oldGroupTwo = oldGroups[1];
                    int oldBlockGroup = oldGroups[2];
                    if ((groupOne != 0 && (groupOne == oldGroupOne || groupOne == oldGroupTwo)) || (groupTwo != 0 && (groupTwo == oldGroupOne || groupTwo == oldGroupTwo)) || (oldBlockGroup != 0 && (groupOne == oldBlockGroup || groupTwo == oldBlockGroup))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public static boolean applyBuff(obj_id target, String name) throws InterruptedException
    {
        return applyBuff(target, null, getStringCrc(name.toLowerCase()), 0.0f, 0.0f);
    }
    public static boolean applyBuff(obj_id target, obj_id owner, String name) throws InterruptedException
    {
        return applyBuff(target, owner, getStringCrc(name.toLowerCase()), 0.0f, 0.0f);
    }
    public static boolean[] applyBuff(obj_id[] targets, String name) throws InterruptedException
    {
        if (targets == null || targets.length == 0)
        {
            return null;
        }
        boolean[] returnList = new boolean[targets.length];
        for (int i = 0; i < targets.length; i++)
        {
            if (isIdValid(targets[i]))
            {
                returnList[i] = applyBuff(targets[i], name);
            }
            else 
            {
                returnList[i] = false;
            }
        }
        return returnList;
    }
    public static boolean[] applyBuff(obj_id target, String[] buffList) throws InterruptedException
    {
        boolean[] results = new boolean[buffList.length];
        for (int i = 0; i < buffList.length; i++)
        {
            results[i] = applyBuff(target, buffList[i]);
        }
        return results;
    }
    public static boolean[][] applyBuff(obj_id[] targets, String[] buffList) throws InterruptedException
    {
        boolean[][] results = new boolean[targets.length][];
        for (int i = 0; i < targets.length; i++)
        {
            results[i] = applyBuff(targets[i], buffList);
        }
        return results;
    }
    public static boolean[] applyBuff(obj_id[] targets, obj_id caster, String buffName) throws InterruptedException
    {
        boolean[] results = new boolean[targets.length];
        for (int i = 0; i < targets.length; i++)
        {
            results[i] = applyBuff(targets[i], caster, buffName);
        }
        return results;
    }
    public static boolean applyBuff(obj_id target, int nameCrc) throws InterruptedException
    {
        return applyBuff(target, null, nameCrc, 0.0f, 0.0f);
    }
    public static boolean applyBuff(obj_id target, obj_id owner, int nameCrc) throws InterruptedException
    {
        return applyBuff(target, owner, nameCrc, 0.0f, 0.0f);
    }
    public static boolean applyBuffWithStackCount(obj_id target, String name, int stack) throws InterruptedException
    {
        return applyBuff(target, null, getStringCrc(name.toLowerCase()), 0.0f, 0.0f, stack);
    }
    public static boolean applyBuffWithStackCount(obj_id target, obj_id owner, String name, int stack) throws InterruptedException
    {
        return applyBuff(target, owner, getStringCrc(name.toLowerCase()), 0.0f, 0.0f, stack);
    }
    public static boolean applyBuff(obj_id target, String name, float duration) throws InterruptedException
    {
        return applyBuff(target, null, getStringCrc(name.toLowerCase()), duration, 0.0f);
    }
    public static boolean applyBuff(obj_id target, obj_id owner, String name, float duration) throws InterruptedException
    {
        return applyBuff(target, owner, getStringCrc(name.toLowerCase()), duration, 0.0f);
    }
    public static boolean applyBuff(obj_id target, int nameCrc, float duration) throws InterruptedException
    {
        return applyBuff(target, null, nameCrc, duration, 0.0f);
    }
    public static boolean applyBuff(obj_id target, obj_id owner, int nameCrc, float duration) throws InterruptedException
    {
        return applyBuff(target, owner, nameCrc, duration, 0.0f);
    }
    public static boolean applyBuff(obj_id target, String name, float duration, float customValue) throws InterruptedException
    {
        return applyBuff(target, null, getStringCrc(name.toLowerCase()), duration, customValue);
    }
    public static boolean applyBuff(obj_id target, obj_id owner, String name, float duration, float customValue) throws InterruptedException
    {
        return applyBuff(target, owner, getStringCrc(name.toLowerCase()), duration, customValue);
    }
    public static boolean applyBuff(obj_id target, int nameCrc, float duration, float customValue) throws InterruptedException
    {
        return applyBuff(target, null, nameCrc, duration, customValue);
    }
    public static boolean applyBuff(obj_id target, obj_id owner, int nameCrc, float duration, float customValue) throws InterruptedException
    {
        return applyBuff(target, owner, nameCrc, duration, customValue, 1);
    }
    public static boolean applyBuff(obj_id target, obj_id owner, int nameCrc, float duration, float customValue, int stack) throws InterruptedException
    {
        if (!canApplyBuff(target, owner, nameCrc))
        {
            return false;
        }
        int[] discarded = _getDiscardedBuffs(target, owner, nameCrc);
        buff_data bdata = combat_engine.getBuffData(nameCrc);
        if (bdata == null)
        {
            LOG("buff.scriptlib", "Buff CRC failed (" + nameCrc + ")");
            return false;
        }
        boolean isStackable = bdata.maxStacks > 1;
        if (discarded != null && discarded.length > 0)
        {
            obj_id caster;
            for (int aDiscarded : discarded) {
                caster = getBuffCaster(target, aDiscarded);
                if (nameCrc == aDiscarded) {
                    float oldBuffTime = getBuffTimeRemaining(target, aDiscarded);
                    if (!isStackable) {
                        if (oldBuffTime <= duration) {
                            _removeBuff(target, aDiscarded);
                        }
                    }
                } else {
                    if (owner != caster && isStackable) {
                        continue;
                    } else {
                        _removeBuff(target, aDiscarded);
                    }
                }
            }
        }
        String particle = bdata.particle;
        String hardpoint = bdata.particleHardpoint;
        String buffName = bdata.buffName;
        if (particle != null && particle.length() > 0 && buffName != null && buffName.length() > 0)
        {
            String particles[] = split(particle, ',');
            String hardpoints[] = split(hardpoint, ',');
            if (particles.length > 1)
            {
                for (int i = 0; i < particles.length; i++)
                {
                    if (hardpoints.length <= i || hardpoints[i].length() <= 0 || hardpoints[i].equals(""))
                    {
                        hardpoint = "";
                    }
                    else 
                    {
                        hardpoint = hardpoints[i];
                    }
                    playClientEffectObj(target, particles[i], target, hardpoint, null, buffName);
                }
            }
            else 
            {
                if (hardpoint.length() <= 0 || hardpoint.equals(""))
                {
                    hardpoint = "";
                }
                playClientEffectObj(target, particle, target, hardpoint, null, buffName);
            }
        }
        if (isIdValid(owner))
        {
            utils.setScriptVar(target, "buffOwner." + nameCrc, owner);
        }
        if (isGroupBuff(nameCrc))
        {
            if (!isIdValid(owner))
            {
                utils.setScriptVar(target, "groupBuff." + nameCrc, target);
            }
            else 
            {
                utils.setScriptVar(target, "groupBuff." + nameCrc, owner);
            }
        }
        if (stack < 1)
        {
            stack = 1;
        }
        return _addBuff(target, owner, nameCrc, duration, customValue, stack);
    }
    public static boolean removeBuff(obj_id target, String name) throws InterruptedException
    {
        return removeBuff(target, getStringCrc(name.toLowerCase()));
    }
    public static boolean removeBuffs(obj_id target, Vector names) throws InterruptedException
    {
        boolean success = true;
        for (Object name : names) {
            if (!removeBuff(target, ((String) name))) {
                success = false;
            }
        }
        return success;
    }
    public static boolean removeBuffs(obj_id target, String[] names) throws InterruptedException
    {
        boolean success = true;
        for (String name : names) {
            if (!removeBuff(target, name)) {
                success = false;
            }
        }
        return success;
    }
    public static boolean removeBuff(obj_id target, int nameCrc) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if (!isPlayer(target) && !isMob(target))
        {
            return false;
        }
        if (!isValidBuff(nameCrc))
        {
            return false;
        }
        utils.removeScriptVar(target, "buffOwner." + nameCrc);
        return _removeBuff(target, nameCrc);
    }
    public static boolean removeAllBuffs(obj_id target) throws InterruptedException
    {
        return removeAllBuffs(target, false);
    }
    public static boolean removeAllBuffs(obj_id target, boolean fromDeath) throws InterruptedException
    {
        return removeAllBuffs(target, fromDeath, false);
    }
    public static boolean removeAllBuffs(obj_id target, boolean fromDeath, boolean fromRespec) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if (!isPlayer(target) && !isMob(target))
        {
            return false;
        }
        if (isPlayer(target))
        {
            player_stomach.resetStomachs(target);
        }
        int[] buffs = getAllBuffs(target);
        if (buffs == null || buffs.length == 0)
        {
            return true;
        }
        buff_data bdata;
        for (int b : buffs) {
            bdata = combat_engine.getBuffData(b);
            if (bdata == null) {
                LOG("buff.scriptlib", "removeAllBuffs bdata is null");
                continue;
            }
            int removeOnDeath = bdata.removeOnDeath;
            int removeOnRespec = bdata.removeOnRespec;
            if (removeOnDeath == 0 && fromDeath) {
                continue;
            }
            if (removeOnRespec == 0 && fromRespec) {
                continue;
            }
            if (isMob(target)) {
                int removeOnCombatEnd = bdata.aiRemoveOnCombatEnd;
                if (removeOnCombatEnd == 0 && !isDead(target) && !isIncapacitated(target)) {
                    continue;
                }
            }
            _removeBuff(target, b);
        }
        return true;
    }
    public static boolean removeAllDebuffsByOwner(obj_id target, obj_id owner) throws InterruptedException
    {
        if (!isIdValid(target) || !exists(target) || !isIdValid(owner) || !exists(owner))
        {
            return false;
        }
        if (target == owner)
        {
            return false;
        }
        int[] buffs = getAllBuffs(target);
        if (buffs == null || buffs.length == 0)
        {
            return true;
        }
        for (int b : buffs) {
            buff_data bdata = combat_engine.getBuffData(b);
            if (bdata.debuff == 1 && getBuffOwner(target, b) == owner) {
                removeBuff(target, b);
            }
        }
        return true;
    }
    public static int[] getAllBuffsByEffect(obj_id target, String effect) throws InterruptedException
    {
        if (!isIdValid(target) || !exists(target))
        {
            return null;
        }
        int[] buffs = getAllBuffs(target);
        if (buffs == null || buffs.length == 0)
        {
            return null;
        }
        Vector matchedBuffs = new Vector();
        matchedBuffs.setSize(0);
        buff_data bdata;
        String tempEffect;
        for (int b : buffs) {
            int j = 1;
            bdata = combat_engine.getBuffData(b);
            tempEffect = getEffectParam(bdata, 1);
            boolean matched = false;
            while (!matched && j < 6 && tempEffect != null && tempEffect.length() > 0) {
                if (tempEffect.equals(effect)) {
                    utils.addElement(matchedBuffs, b);
                    matched = true;
                }
                j++;
                tempEffect = getEffectParam(bdata, j);
            }
        }
        if (matchedBuffs.size() < 1)
        {
            return null;
        }
        int[] _matchedBuffs = new int[matchedBuffs.size()];
        for (int _i = 0; _i < matchedBuffs.size(); ++_i)
		{
			_matchedBuffs[_i] = (Integer) matchedBuffs.get(_i);
		}
        return _matchedBuffs;
    }
    public static int[] getAllBuffs(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        if (!isPlayer(target) && !isMob(target))
        {
            return null;
        }
        return _getAllBuffs(target);
    }
    public static boolean hasBuff(obj_id target) throws InterruptedException
    {
        int[] buffCRCs = _getAllBuffs(target);
        if (buffCRCs == null || buffCRCs.length == 0)
        {
            return false;
        }
        return true;
    }
    public static boolean hasBuff(obj_id target, String name) throws InterruptedException
    {
        return _hasBuff(target, getStringCrc(name.toLowerCase()));
    }
    public static boolean hasBuff(obj_id target, int nameCrc) throws InterruptedException
    {
        return _hasBuff(target, nameCrc);
    }
    public static boolean hasAnyBuffInList(obj_id target, String buffList) throws InterruptedException
    {
        String[] buffs = split(buffList, ',');
        for (String buff : buffs) {
            if (hasBuff(target, buff)) {
                return true;
            }
        }
        return false;
    }
    public static boolean hasAnyBuffInList(obj_id target, String[] buffList) throws InterruptedException
    {
        for (String aBuffList : buffList) {
            if (hasBuff(target, aBuffList)) {
                return true;
            }
        }
        return false;
    }
    public static boolean refreshAllBuffs(obj_id target) throws InterruptedException
    {
        int[] buffs = _getAllBuffs(target);
        if (buffs == null || buffs.length == 0)
        {
            return true;
        }
        float[] buffs_d = new float[buffs.length];
        float[] buffs_v = new float[buffs.length];
        for (int i = 0; i < buffs.length; i++)
        {
            buffs_d[i] = _getBuffTimeRemaining(target, buffs[i]);
            buffs_v[i] = _getBuffCustomValue(target, buffs[i]);
        }
        removeAllBuffs(target, true);
        boolean success = true;
        obj_id owner;
        for (int i = 0; i < buffs.length; i++)
        {
            if (buffs_d[i] <= 0.0f)
            {
                continue;
            }
            owner = getBuffOwner(target, buffs[i]);
            if (isIdValid(owner) && owner != target)
            {
                continue;
            }
            success &= applyBuff(target, buffs[i], buffs_d[i], buffs_v[i]);
        }
        return success;
    }
    public static String getBuffNameFromCrc(int nameCrc) throws InterruptedException
    {
        buff_data bdata = combat_engine.getBuffData(nameCrc);
        if (bdata == null)
        {
            return null;
        }
        return bdata.buffName;
    }
    public static int getGroupOne(String name) throws InterruptedException
    {
        return getGroupOne(getStringCrc(name.toLowerCase()));
    }
    public static int getGroupOne(int nameCrc) throws InterruptedException
    {
        buff_data bdata = combat_engine.getBuffData(nameCrc);
        if (bdata == null)
        {
            return 0;
        }
        if (bdata.buffGroup1 == null || bdata.buffGroup1.length() <= 0)
        {
            return 0;
        }
        else 
        {
            return bdata.buffGroup1Crc;
        }
    }
    public static int getGroupTwo(String name) throws InterruptedException
    {
        return getGroupTwo(getStringCrc(name.toLowerCase()));
    }
    public static int getGroupTwo(int nameCrc) throws InterruptedException
    {
        buff_data bdata = combat_engine.getBuffData(nameCrc);
        if (bdata == null)
        {
            return 0;
        }
        if (bdata.buffGroup2 == null || bdata.buffGroup2.length() <= 0)
        {
            return 0;
        }
        else 
        {
            return bdata.buffGroup2Crc;
        }
    }
    public static String getStringGroupTwo(String name) throws InterruptedException
    {
        return getStringGroupTwo(getStringCrc(name.toLowerCase()));
    }
    public static String getStringGroupTwo(int nameCrc) throws InterruptedException
    {
        buff_data bdata = combat_engine.getBuffData(nameCrc);
        if (bdata == null)
        {
            return "";
        }
        if (bdata.buffGroup2 == null || bdata.buffGroup2.length() <= 0)
        {
            return "";
        }
        else 
        {
            return bdata.buffGroup2;
        }
    }
    public static int getBlockGroup(String name) throws InterruptedException
    {
        return getBlockGroup(getStringCrc(name.toLowerCase()));
    }
    public static int getBlockGroup(int nameCrc) throws InterruptedException
    {
        buff_data bdata = combat_engine.getBuffData(nameCrc);
        if (bdata == null)
        {
            return 0;
        }
        if (bdata.blockGroup == null || bdata.blockGroup.length() <= 0)
        {
            return 0;
        }
        else 
        {
            return bdata.blockGroupCrc;
        }
    }
    public static int[] getGroups(buff_data bdata) throws InterruptedException
    {
        if (bdata == null)
        {
            return null;
        }
        int[] groups = new int[3];
        if (bdata.buffGroup1 == null || bdata.buffGroup1.length() <= 0)
        {
            groups[0] = 0;
        }
        else 
        {
            groups[0] = bdata.buffGroup1Crc;
        }
        if (bdata.buffGroup2 == null || bdata.buffGroup2.length() <= 0)
        {
            groups[1] = 0;
        }
        else 
        {
            groups[1] = bdata.buffGroup2Crc;
        }
        if (bdata.blockGroup == null || bdata.blockGroup.length() <= 0)
        {
            groups[2] = 0;
        }
        else 
        {
            groups[2] = bdata.blockGroupCrc;
        }
        return groups;
    }
    public static int[] getGroups(String name) throws InterruptedException
    {
        return getGroups(getStringCrc(name.toLowerCase()));
    }
    public static int[] getGroups(int nameCrc) throws InterruptedException
    {
        buff_data bdata = combat_engine.getBuffData(nameCrc);
        return getGroups(bdata);
    }
    public static int getBuffOnTargetFromGroup(obj_id target, String group) throws InterruptedException
    {
        return getBuffOnTargetFromGroup(target, getStringCrc(group));
    }
    public static int getBuffOnTargetFromGroup(obj_id target, int groupCrc) throws InterruptedException
    {
        int[] buffs = getAllBuffs(target);
        if (buffs == null || buffs.length == 0)
        {
            return 0;
        }
        for (int b : buffs) {
            int[] groups = getGroups(b);
            if (groups == null || groups.length == 0) {
                continue;
            }
            for (int group : groups) {
                if (group == groupCrc) {
                    return b;
                }
            }
        }
        return 0;
    }
    public static int[] getGroup2BuffsOnTarget(obj_id target, String groupName) throws InterruptedException
    {
        int[] buffs = getAllBuffs(target);
        if (buffs == null || buffs.length == 0)
        {
            return null;
        }
        Vector allGroupBuffs = new Vector();
        allGroupBuffs.setSize(0);
        String tempGroupName;
        for (int b : buffs) {
            tempGroupName = getStringGroupTwo(b);
            if (tempGroupName.startsWith(groupName)) {
                utils.addElement(allGroupBuffs, b);
            }
        }
        int[] _allGroupBuffs = new int[0];
        _allGroupBuffs = new int[allGroupBuffs.size()];
        for (int _i = 0; _i < allGroupBuffs.size(); ++_i)
		{
			_allGroupBuffs[_i] = (Integer) allGroupBuffs.get(_i);
		}
        return _allGroupBuffs;
    }
    public static int getPriority(String name) throws InterruptedException
    {
        return getPriority(getStringCrc(name.toLowerCase()));
    }
    public static int getPriority(int nameCrc) throws InterruptedException
    {
        buff_data bdata = combat_engine.getBuffData(nameCrc);
        if (bdata == null)
        {
            return 0;
        }
        return bdata.priority;
    }
    public static float getDuration(String name) throws InterruptedException
    {
        return getDuration(getStringCrc(name.toLowerCase()));
    }
    public static float getDuration(int nameCrc) throws InterruptedException
    {
        buff_data bdata = combat_engine.getBuffData(nameCrc);
        if (bdata == null)
        {
            return 0.0f;
        }
        return bdata.duration;
    }
    public static int getState(String name) throws InterruptedException
    {
        return getState(getStringCrc(name.toLowerCase()));
    }
    public static int getState(int nameCrc) throws InterruptedException
    {
        buff_data bdata = combat_engine.getBuffData(nameCrc);
        if (bdata == null)
        {
            return STATE_NONE;
        }
        return bdata.buffState;
    }
    public static String getCallback(String name) throws InterruptedException
    {
        return getCallback(getStringCrc(name.toLowerCase()));
    }
    public static String getCallback(int nameCrc) throws InterruptedException
    {
        buff_data bdata = combat_engine.getBuffData(nameCrc);
        if (bdata == null)
        {
            return "";
        }
        return bdata.callback;
    }
    public static String getParticle(String name) throws InterruptedException
    {
        return getParticle(getStringCrc(name.toLowerCase()));
    }
    public static String getParticle(int nameCrc) throws InterruptedException
    {
        buff_data bdata = combat_engine.getBuffData(nameCrc);
        if (bdata == null)
        {
            return "";
        }
        return bdata.particle;
    }
    public static String getParticleHardpoint(String name) throws InterruptedException
    {
        return getParticle(getStringCrc(name.toLowerCase()));
    }
    public static String getParticleHardpoint(int nameCrc) throws InterruptedException
    {
        buff_data bdata = combat_engine.getBuffData(nameCrc);
        if (bdata == null)
        {
            return "";
        }
        return bdata.particleHardpoint;
    }
    public static String getEffectParam(String name, int effNum) throws InterruptedException
    {
        return getEffectParam(getStringCrc(name.toLowerCase()), effNum);
    }
    public static String getEffectParam(int nameCrc, int effNum) throws InterruptedException
    {
        buff_data bdata = combat_engine.getBuffData(nameCrc);
        if (bdata == null)
        {
            return null;
        }
        return getEffectParam(bdata, effNum);
    }
    public static String getEffectParam(buff_data bdata, int effNum) throws InterruptedException
    {
        if (effNum <= 0 || effNum > MAX_EFFECTS)
        {
            return null;
        }
        if (bdata == null)
        {
            return null;
        }
        switch (effNum)
        {
            case 1:
                return bdata.effect1Param;
            case 2:
                return bdata.effect2Param;
            case 3:
                return bdata.effect3Param;
            case 4:
                return bdata.effect4Param;
            case 5:
                return bdata.effect5Param;
            default:
                return null;
        }
    }
    public static float getEffectValue(String name, int effNum) throws InterruptedException
    {
        return getEffectValue(getStringCrc(name.toLowerCase()), effNum);
    }
    public static float getEffectValue(int nameCrc, int effNum) throws InterruptedException
    {
        if (effNum <= 0 || effNum > MAX_EFFECTS)
        {
            return Float.NEGATIVE_INFINITY;
        }
        buff_data bdata = combat_engine.getBuffData(nameCrc);
        if (bdata == null)
        {
            return 0;
        }
        switch (effNum)
        {
            case 1:
                return bdata.effect1Value;
            case 2:
                return bdata.effect2Value;
            case 3:
                return bdata.effect3Value;
            case 4:
                return bdata.effect4Value;
            case 5:
                return bdata.effect5Value;
            default:
                return 0;
        }
    }
    public static boolean isDebuff(String name) throws InterruptedException
    {
        return isDebuff(getStringCrc(name.toLowerCase()));
    }
    public static boolean isDebuff(int nameCrc) throws InterruptedException
    {
        buff_data bdata = combat_engine.getBuffData(nameCrc);
        if (bdata == null)
        {
            return false;
        }
        int debuff = bdata.debuff;
        return debuff == 1;
    }
    public static boolean canBeDispelled(String name) throws InterruptedException
    {
        return canBeDispelled(getStringCrc(name.toLowerCase()));
    }
    public static boolean canBeDispelled(int nameCrc) throws InterruptedException
    {
        buff_data bdata = combat_engine.getBuffData(nameCrc);
        if (bdata == null)
        {
            return false;
        }
        return bdata.dispellPlayer == 1;
    }
    public static boolean isGroupBuff(String name) throws InterruptedException
    {
        return isGroupBuff(getStringCrc(name.toLowerCase()));
    }
    public static boolean isGroupBuff(int nameCrc) throws InterruptedException
    {
        String effect;
        for (int i = 1; i <= MAX_EFFECTS; i++)
        {
            effect = getEffectParam(nameCrc, i);
            if (effect != null && effect.equals("group"))
            {
                return true;
            }
        }
        return false;
    }
    public static boolean isAuraBuff(String name) throws InterruptedException
    {
        return isAuraBuff(getStringCrc(name.toLowerCase()));
    }
    public static boolean isAuraBuff(int nameCrc) throws InterruptedException
    {
        String groupTwo = getStringGroupTwo(nameCrc);
        return groupTwo.contains("aura");
    }
    public static boolean isOwnedBuff(String name) throws InterruptedException
    {
        return isOwnedBuff(getStringCrc(name.toLowerCase()));
    }
    public static boolean isOwnedBuff(int nameCrc) throws InterruptedException
    {
        obj_id self = getSelf();
        if (!utils.hasScriptVar(self, "groupBuff." + nameCrc))
        {
            return true;
        }
        obj_id owner = utils.getObjIdScriptVar(self, "groupBuff." + nameCrc);
        if (isIdValid(owner) && owner == self)
        {
            return true;
        }
        return false;
    }
    public static boolean isValidBuff(String name) throws InterruptedException
    {
        return isValidBuff(getStringCrc(name.toLowerCase()));
    }
    public static boolean isValidBuff(int nameCrc) throws InterruptedException
    {
        return (combat_engine.getBuffData(nameCrc) != null);
    }
    public static int[] _getDiscardedBuffs(obj_id target, int nameCrc) throws InterruptedException
    {
        return _getDiscardedBuffs(target, null, nameCrc);
    }
    public static int[] _getDiscardedBuffs(obj_id target, obj_id owner, int nameCrc) throws InterruptedException
    {
        buff_data bdata = combat_engine.getBuffData(nameCrc);
        if (bdata == null)
        {
            return null;
        }
        return _getDiscardedBuffs(target, owner, bdata);
    }
    public static int[] _getDiscardedBuffs(obj_id target, obj_id owner, buff_data bdata) throws InterruptedException
    {
        if (bdata == null)
        {
            return null;
        }
        int[] buffs = _getAllBuffs(target);
        if (buffs == null || buffs.length == 0)
        {
            return null;
        }
        int discardCount = 0;
        int[] discarded = new int[buffs.length];
        int[] groups = getGroups(bdata);
        if (groups == null || groups.length != 3)
        {
            return null;
        }
        int groupOne = groups[0];
        int groupTwo = groups[1];
        if (groupOne != 0 || groupTwo != 0)
        {
            int priority = bdata.priority;
            buff_data oldBuffData;
            obj_id effectOwner;
            for (int b : buffs) {
                oldBuffData = combat_engine.getBuffData(b);
                if (oldBuffData == null) {
                    continue;
                }
                effectOwner = getBuffOwner(target, b);
                if (isIdValid(effectOwner) && effectOwner != target) {
                    continue;
                }
                int oldPriority = oldBuffData.priority;
                if (priority >= oldPriority) {
                    int[] oldGroups = getGroups(oldBuffData);
                    if (oldGroups == null || oldGroups.length != 3) {
                        return null;
                    }
                    int oldGroupOne = oldGroups[0];
                    int oldGroupTwo = oldGroups[1];
                    if ((groupOne != 0 && (groupOne == oldGroupOne || groupOne == oldGroupTwo)) || (groupTwo != 0 && (groupTwo == oldGroupOne || groupTwo == oldGroupTwo))) {
                        discarded[discardCount++] = b;
                    }
                }
            }
        }
        int[] returnArray = new int[discardCount];
        System.arraycopy(discarded, 0, returnArray, 0, discardCount);
        return returnArray;
    }
    public static obj_id getBuffOwner(obj_id target, String name) throws InterruptedException
    {
        return getBuffOwner(target, getStringCrc(name.toLowerCase()));
    }
    public static obj_id getBuffOwner(obj_id target, int nameCrc) throws InterruptedException
    {
        if (!isGroupBuff(nameCrc))
        {
            return target;
        }
        if (!utils.hasScriptVar(target, "groupBuff." + nameCrc))
        {
            return null;
        }
        return utils.getObjIdScriptVar(target, "groupBuff." + nameCrc);
    }
    public static void addGroupBuffEffect(obj_id target, obj_id owner, int[] buffList, float[] strList, float[] durList) throws InterruptedException
    {
        if (!isIdValid(target) || !isIdValid(owner))
        {
            return;
        }
        if (buffList == null || buffList.length < 1)
        {
            return;
        }
        for (int i = 0; i < buffList.length; i++)
        {
            utils.setScriptVar(target, "groupBuff." + buffList[i], owner);
            applyBuff(target, owner, buffList[i], durList[i], strList[i]);
            if (beast_lib.isBeastMaster(target))
            {
                obj_id beast = beast_lib.getBeastOnPlayer(target);
                if (isIdValid(beast) && !isIdNull(beast))
                {
                    applyBuff(beast, owner, buffList[i], durList[i], strList[i]);
                }
            }
        }
    }
    public static void removeGroupBuffEffect(obj_id target, int[] buffList) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return;
        }
        if (buffList == null || buffList.length < 1)
        {
            return;
        }
        obj_id owner;
        obj_id beast;
        for (int aBuffList : buffList) {
            owner = utils.getObjIdScriptVar(target, "groupBuff." + aBuffList);
            if (owner != target) {
                _removeBuff(target, aBuffList);
                utils.removeScriptVar(target, "groupBuff." + aBuffList);
                if (beast_lib.isBeastMaster(target)) {
                    beast = beast_lib.getBeastOnPlayer(target);
                    if (isIdValid(beast) && !isIdNull(beast)) {
                        _removeBuff(beast, aBuffList);
                    }
                }
            }
        }
    }
    public static int[] getOwnedGroupBuffs(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        int[] buffCrcList = getAllBuffs(target);
        if (buffCrcList == null || buffCrcList.length < 1)
        {
            return null;
        }
        Vector buffList = new Vector();
        buffList.setSize(0);
        obj_id owner;
        for (int aBuffCrcList : buffCrcList) {
            owner = utils.getObjIdScriptVar(target, "groupBuff." + aBuffCrcList);
            if (isIdValid(owner) && owner == target && isGroupBuff(aBuffCrcList)) {
                buffList = utils.addElement(buffList, aBuffCrcList);
            }
        }
        int[] _buffList = new int[0];
        if (buffList != null)
        {
            _buffList = new int[buffList.size()];
            for (int _i = 0; _i < buffList.size(); ++_i)
            {
                _buffList[_i] = (Integer) buffList.get(_i);
            }
        }
        return _buffList;
    }
    public static int[] getGroupBuffEffects(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        int[] buffCrcList = getAllBuffs(target);
        if (buffCrcList == null || buffCrcList.length < 1)
        {
            return null;
        }
        Vector buffList = new Vector();
        buffList.setSize(0);
        obj_id owner;

        for (int aBuffCrcList : buffCrcList) {
            owner = utils.getObjIdScriptVar(target, "groupBuff." + aBuffCrcList);
            if (isIdValid(owner) && owner != target && isGroupBuff(aBuffCrcList)) {
                buffList = utils.addElement(buffList, aBuffCrcList);
            }
        }
        int[] _buffList = new int[0];
        if (buffList != null)
        {
            _buffList = new int[buffList.size()];
            for (int _i = 0; _i < buffList.size(); ++_i)
            {
                _buffList[_i] = (Integer) buffList.get(_i);
            }
        }
        return _buffList;
    }
    public static float[] getGroupBuffDuration(obj_id player, int[] buffList) throws InterruptedException
    {
        if (buffList == null || buffList.length == 0)
        {
            return null;
        }
        float[] durationList = new float[buffList.length];
        for (int i = 0; i < buffList.length; i++)
        {
            durationList[i] = _getBuffTimeRemaining(player, buffList[i]);
        }
        return durationList;
    }
    public static float[] getGroupBuffStrength(obj_id player, int[] buffList) throws InterruptedException
    {
        if (buffList == null || buffList.length == 0)
        {
            return null;
        }
        float[] strengthList = new float[buffList.length];
        for (int i = 0; i < buffList.length; i++)
        {
            strengthList[i] = _getBuffCustomValue(player, buffList[i]);
        }
        return strengthList;
    }
    public static boolean checkForStateImmunity(obj_id target, buff_data bdata) throws InterruptedException
    {
        if (bdata.buffState == STATE_NONE)
        {
            return false;
        }
        else if (bdata.buffState == STATE_STUNNED && utils.hasScriptVar(target, "immunity.state.stun"))
        {
            LOG("Immunity//STATE:", "Block a STUN state from being applied on ---" + getPlayerName(target));
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static boolean removeAllBuffsOfStateType(obj_id target, int stateType) throws InterruptedException
    {
        boolean removed = false;
        int[] buffs = getAllBuffs(target);
        if (buffs == null || buffs.length == 0)
        {
            return true;
        }
        for (int b : buffs) {
            if (isDebuff(b) && getState(b) == stateType) {
                _removeBuff(target, b);
                removed = true;
            }
        }
        return removed;
    }
    public static boolean toggleStance(obj_id player, String attemptedBuff) throws InterruptedException
    {
        int[] buffs = getAllBuffs(player);
        if (buffs == null)
        {
            return false;
        }
        buff_data attemptedBuffData = combat_engine.getBuffData(getStringCrc(attemptedBuff.toLowerCase()));
        if (attemptedBuffData == null)
        {
            return false;
        }
        String groupAtt = attemptedBuffData.buffGroup1;
        buff_data bdata;
        String curBuff;
        String groupCur;
        for (int b : buffs) {
            bdata = combat_engine.getBuffData(b);
            if (bdata == null) {
                continue;
            }
            curBuff = bdata.buffName;
            groupCur = bdata.buffGroup1;
            if (groupCur.equals(groupAtt)) {
                removeBuff(player, curBuff);
                if (!curBuff.equals(attemptedBuff.toLowerCase())) {
                    applyBuff(player, player, attemptedBuff);
                    return false;
                }
                return true;
            }
        }
        return false;
    }
    public static boolean isInStance(obj_id player) throws InterruptedException
    {
        if (!isPlayer(player))
        {
            return true;
        }
        if (hasBuff(player, "fs_buff_def_1_1"))
        {
            return true;
        }
        return false;
    }
    public static boolean isInFocus(obj_id player) throws InterruptedException
    {
        if (!isPlayer(player))
        {
            return true;
        }
        if (hasBuff(player, "fs_buff_ca_1"))
        {
            return true;
        }
        return false;
    }
    public static boolean playStanceVisual(obj_id target, String effectName) throws InterruptedException
    {
        effectName = effectName.substring(0, (effectName.lastIndexOf("_")));
        buff_data bdata = combat_engine.getBuffData(getStringCrc(effectName.toLowerCase()));
        if (bdata == null)
        {
            return false;
        }
        String effectPlayed = bdata.stanceParticle;
        if (effectPlayed != null && effectPlayed.length() > 0)
        {
            playClientEffectObj(target, effectPlayed, target, "");
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static boolean isDotIconOnlyBuff(String name) throws InterruptedException
    {
        boolean isDotIconOnly = false;
        if (name.equals("bleeding") || name.equals("poisoned") || name.equals("diseased") || name.equals("onfire"))
        {
            isDotIconOnly = true;
        }
        return isDotIconOnly;
    }
    public static long getBuffStackCount(obj_id target, int nameCrc) throws InterruptedException
    {
        return _getBuffStackCount(target, nameCrc);
    }
    public static long getBuffStackCount(obj_id target, String nameCrc) throws InterruptedException
    {
        return _getBuffStackCount(target, getStringCrc(nameCrc.toLowerCase()));
    }
    public static boolean decrementBuffStack(obj_id target, int nameCrc, int stacksToRemove) throws InterruptedException
    {
        return _decrementBuffStack(target, nameCrc, stacksToRemove);
    }
    public static boolean decrementBuffStack(obj_id target, String nameCrc, int stacksToRemove) throws InterruptedException
    {
        return _decrementBuffStack(target, getStringCrc(nameCrc.toLowerCase()), stacksToRemove);
    }
    public static boolean decrementBuffStack(obj_id target, int nameCrc) throws InterruptedException
    {
        return _decrementBuffStack(target, nameCrc, 1);
    }
    public static boolean decrementBuffStack(obj_id target, String nameCrc) throws InterruptedException
    {
        return _decrementBuffStack(target, getStringCrc(nameCrc.toLowerCase()), 1);
    }
    public static obj_id getBuffCaster(obj_id target, int nameCrc) throws InterruptedException
    {
        return obj_id.getObjId(_getBuffCaster(target, nameCrc));
    }
    public static obj_id getBuffCaster(obj_id target, String nameCrc) throws InterruptedException
    {
        return obj_id.getObjId(_getBuffCaster(target, getStringCrc(nameCrc.toLowerCase())));
    }
    public static float getBuffTimeRemaining(obj_id target, int nameCrc) throws InterruptedException
    {
        return _getBuffTimeRemaining(target, nameCrc);
    }
    public static float getBuffTimeRemaining(obj_id target, String nameCrc) throws InterruptedException
    {
        return _getBuffTimeRemaining(target, getStringCrc(nameCrc.toLowerCase()));
    }
    public static int[] getAllDotBuffsOfType(obj_id target, String type) throws InterruptedException
    {
        int[] allDotBuffs = getAllBuffsByEffect(target, "dot");
        Vector allDotBuffsOfType = new Vector();
        allDotBuffsOfType.setSize(0);
        if (allDotBuffs == null || allDotBuffs.length <= 0)
        {
            return null;
        }
        String param1;
        for (int allDotBuff : allDotBuffs) {
            param1 = getEffectParam(allDotBuff, 1);
            if (param1.equals(type)) {
                utils.addElement(allDotBuffsOfType, allDotBuff);
            }
        }
        int[] _allDotBuffsOfType = new int[0];
        _allDotBuffsOfType = new int[allDotBuffsOfType.size()];
        for (int _i = 0; _i < allDotBuffsOfType.size(); ++_i)
		{
			_allDotBuffsOfType[_i] = (Integer) allDotBuffsOfType.get(_i);
		}
        return _allDotBuffsOfType;
    }
    public static boolean performBuffDotImmunity(obj_id target, String dotType) throws InterruptedException
    {
        if (dotType.equals("all"))
        {
            int[] allDotBuffs = getAllBuffsByEffect(target, "dot");
            if (allDotBuffs == null || allDotBuffs.length <= 0)
            {
                return false;
            }
            for (int allDotBuff : allDotBuffs) {
                removeBuff(target, allDotBuff);
            }
            return true;
        }
        int[] allBuffDotsOfType = buff.getAllDotBuffsOfType(target, dotType);
        if (allBuffDotsOfType == null || allBuffDotsOfType.length <= 0)
        {
            return false;
        }
        for (int anAllBuffDotsOfType : allBuffDotsOfType) {
            removeBuff(target, anAllBuffDotsOfType);
        }
        return true;
    }
    public static boolean isBuffDot(String buffName) throws InterruptedException
    {
        return buff.getEffectParam(buffName, 2).equals("dot");
    }
    public static void reduceBuffDotStackCount(obj_id target, String dotType, int count) throws InterruptedException
    {
        int[] allBuffsOfType = buff.getAllDotBuffsOfType(target, dotType);
        if (allBuffsOfType == null || allBuffsOfType.length == 0)
        {
            return;
        }
        for (int anAllBuffsOfType : allBuffsOfType) {
            long stackCount = getBuffStackCount(target, anAllBuffsOfType);
            if (stackCount <= count) {
                removeBuff(target, anAllBuffsOfType);
            } else {
                decrementBuffStack(target, anAllBuffsOfType, count);
            }
        }
    }
    public static void divideBuffDotStackCount(obj_id target, String dotType, int reduction) throws InterruptedException
    {
        int[] allBuffsOfType = buff.getAllDotBuffsOfType(target, dotType);
        if (allBuffsOfType == null || allBuffsOfType.length == 0)
        {
            return;
        }
        for (int anAllBuffsOfType : allBuffsOfType) {
            long stackCount = getBuffStackCount(target, anAllBuffsOfType);
            if (stackCount <= 1) {
                removeBuff(target, anAllBuffsOfType);
            } else {
                reduction = Math.round(stackCount * (reduction / 100.0f));
                reduction = reduction < 1 ? 1 : reduction;
                if (stackCount <= reduction) {
                    removeBuff(target, anAllBuffsOfType);
                } else {
                    decrementBuffStack(target, anAllBuffsOfType, reduction);
                }
            }
        }
    }
    public static boolean removeAllAuraBuffs(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return false;
        }
        int[] allBuffs = buff.getAllBuffs(player);
        if (allBuffs != null && allBuffs.length > 0)
        {
            for (int allBuff : allBuffs) {
                if (buff.isAuraBuff(allBuff)) {
                    buff.removeBuff(player, allBuff);
                }
            }
        }
        return true;
    }
    public static int getBuffWithEffect(obj_id player, String effect) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return -1;
        }
        if (effect == null || effect.equals(""))
        {
            return -1;
        }
        int[] allBuffs = getAllBuffs(player);
        if (allBuffs != null && allBuffs.length > 0)
        {
            String tempEffect;
            for (int allBuff : allBuffs) {
                tempEffect = getEffectParam(allBuff, 1);
                if (effect.equals(tempEffect)) {
                    return allBuff;
                }
            }
        }
        return -1;
    }
    public static void partyBuff(obj_id caster, String buffName) throws InterruptedException
    {
        Vector toBuff = new Vector();
        toBuff.setSize(0);
        obj_id myBeast = beast_lib.getBeastOnPlayer(caster);
        obj_id groupId = getGroupObject(caster);
        if (isIdValid(myBeast) && exists(myBeast))
        {
            toBuff.add(myBeast);
        }
        if (isIdValid(groupId))
        {
            obj_id[] groupPlayers = getGroupMemberIds(groupId);
            obj_id thisBeast;
            for (obj_id groupPlayer : groupPlayers) {
                if (isIdValid(groupPlayer) && exists(groupPlayer) && pvpCanHelp(caster, groupPlayer) && getDistance(groupPlayer, caster) < 100.0f) {
                    toBuff.add(groupPlayer);
                    thisBeast = beast_lib.getBeastOnPlayer(groupPlayer);
                    if (isIdValid(thisBeast) && exists(thisBeast)) {
                        toBuff.add(thisBeast);
                    }
                }
            }
        }
        else 
        {
            toBuff.add(caster);
        }
        obj_id[] buffList = new obj_id[0];
        buffList = new obj_id[toBuff.size()];
        toBuff.toArray(buffList);
        applyBuff(buffList, caster, buffName);
    }
    public static boolean decayBuff(obj_id target, String buffName) throws InterruptedException
    {
        return decayBuff(target, getStringCrc(buffName.toLowerCase()), 0.10f);
    }
    public static boolean decayBuff(obj_id target, String buffName, float percent) throws InterruptedException
    {
        return decayBuff(target, getStringCrc(buffName.toLowerCase()), percent);
    }
    public static boolean decayBuff(obj_id target, int buffCrc, float percent) throws InterruptedException
    {
        if (!isIdValid(target) || !exists(target))
        {
            return false;
        }
        if (buffCrc == 0 || percent < 0.0f || percent > 1.0f)
        {
            return false;
        }
        return _decayBuff(target, buffCrc, percent);
    }
    public static boolean decayAllBuffsFromPvpDeath(obj_id target) throws InterruptedException
    {
        return decayAllBuffsFromPvpDeath(target, 0.10f);
    }
    public static boolean decayAllBuffsFromPvpDeath(obj_id target, float percent) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if (!isPlayer(target))
        {
            return false;
        }
        int[] buffs = getAllBuffs(target);
        if (buffs == null || buffs.length == 0)
        {
            return true;
        }
        buff_data bdata;
        for (int b : buffs) {
            bdata = combat_engine.getBuffData(b);
            if (bdata == null) {
                LOG("buff.scriptlib", "decayAllBuffsFromPvpDeath bdata is null");
                continue;
            }
            int decayOnPvpDeath = bdata.decayOnPvpDeath;
            int removalOnDeath = bdata.removeOnDeath;
            if (decayOnPvpDeath == 0 && removalOnDeath == 0) {
                continue;
            } else if (decayOnPvpDeath == 0 && removalOnDeath == 1) {
                _removeBuff(target, b);
                continue;
            } else if (removalOnDeath == 0) {
                continue;
            }
            _decayBuff(target, b, percent);
        }
        return true;
    }
}
