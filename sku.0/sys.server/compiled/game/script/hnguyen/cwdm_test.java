package script.hnguyen;

import script.*;
import script.combat_engine.hit_result;
import script.library.*;

import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

public class cwdm_test extends script.base_script
{
    public cwdm_test()
    {
    }
    public static final String PROP_TEXT = "Text";
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (objSpeaker != self)
        {
            return SCRIPT_CONTINUE;
        }
        if (strText.startsWith("createInOverloaded "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                String item = st.nextToken();
                String container = st.nextToken();
                obj_id containerOid = utils.stringToObjId(container);
                createObjectOverloaded(item, containerOid);
            }
        }
        else if (strText.startsWith("hasProxyOrAuthObject "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id id = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "hasProxyOrAuthObject(id=" + id + ") returns " + hasProxyOrAuthObject(id));
            }
        }
        else if (strText.startsWith("getCharacterCtsHistory "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id player = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "getCharacterCtsHistory(player=" + player + ") returns:");
                dictionary ctsHistory = getCharacterCtsHistory(player);
                if (ctsHistory == null)
                {
                    sendSystemMessageTestingOnly(self, "null dictionary");
                }
                else 
                {
                    String[] sourceCharacterNames = ctsHistory.getStringArray("character_name");
                    String[] sourceClusterNames = ctsHistory.getStringArray("cluster_name");
                    int[] transferTimes = ctsHistory.getIntArray("transfer_time");
                    if (sourceCharacterNames == null)
                    {
                        sendSystemMessageTestingOnly(self, "null \"character_name\" dictionary");
                    }
                    else if (sourceClusterNames == null)
                    {
                        sendSystemMessageTestingOnly(self, "null \"cluster_name\" dictionary");
                    }
                    else if (transferTimes == null)
                    {
                        sendSystemMessageTestingOnly(self, "null \"transfer_time\" dictionary");
                    }
                    else if (sourceCharacterNames.length <= 0)
                    {
                        sendSystemMessageTestingOnly(self, "empty \"character_name\" dictionary");
                    }
                    else if (sourceClusterNames.length <= 0)
                    {
                        sendSystemMessageTestingOnly(self, "empty \"cluster_name\" dictionary");
                    }
                    else if (transferTimes.length <= 0)
                    {
                        sendSystemMessageTestingOnly(self, "empty \"transfer_time\" dictionary");
                    }
                    else if (sourceCharacterNames.length != sourceClusterNames.length)
                    {
                        sendSystemMessageTestingOnly(self, "\"character_name\" dictionary length != \"cluster_name\" dictionary length");
                    }
                    else if (sourceCharacterNames.length != transferTimes.length)
                    {
                        sendSystemMessageTestingOnly(self, "\"character_name\" dictionary length != \"transfer_time\" dictionary length");
                    }
                    else 
                    {
                        for (int i = 0; i < sourceCharacterNames.length; ++i)
                        {
                            sendSystemMessageTestingOnly(self, "(" + sourceCharacterNames[i] + ") (" + sourceClusterNames[i] + ") (" + transferTimes[i] + ", " + getCalendarTimeStringLocal(transferTimes[i]) + ")");
                        }
                    }
                }
            }
        }
        else if (strText.startsWith("setHealth "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                int value = utils.stringToInt(st.nextToken());
                sendSystemMessageTestingOnly(self, "calling setHealth(target=" + target + ", value=" + value + ")");
                setHealth(target, value);
            }
        }
        else if (strText.startsWith("setHitpoints "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                int value = utils.stringToInt(st.nextToken());
                sendSystemMessageTestingOnly(self, "calling setHitpoints(target=" + target + ", value=" + value + ")");
                setHitpoints(target, value);
            }
        }
        else if (strText.startsWith("getShipContents "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id ship = utils.stringToObjId(st.nextToken());
                int got = getGameObjectType(ship);
                if (isGameObjectTypeOf(got, GOT_ship))
                {
                    obj_id[] contents = getContents(ship);
                    if (contents == null)
                    {
                        sendSystemMessageTestingOnly(self, ship + " has null contents");
                    }
                    else if (contents.length <= 0)
                    {
                        sendSystemMessageTestingOnly(self, ship + " has " + contents.length + " contents");
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, ship + " has " + contents.length + " contents");
                        for (obj_id content : contents) {
                            sendSystemMessageTestingOnly(self, "" + content);
                        }
                    }
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, ship + " is not of type GOT_ship");
                }
            }
        }
        else if (strText.startsWith("getShipSlots "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id ship = utils.stringToObjId(st.nextToken());
                int got = getGameObjectType(ship);
                if (isGameObjectTypeOf(got, GOT_ship))
                {
                    int[] shipSlots = getShipChassisSlots(ship);
                    if (shipSlots == null)
                    {
                        sendSystemMessageTestingOnly(self, ship + " has null slots");
                    }
                    else if (shipSlots.length <= 0)
                    {
                        sendSystemMessageTestingOnly(self, ship + " has " + shipSlots.length + " slots");
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, ship + " has " + shipSlots.length + " slots");
                        for (int i = 0; i < shipSlots.length; ++i)
                        {
                            sendSystemMessageTestingOnly(self, ship + " slot " + (i + 1) + " " + (isShipSlotInstalled(ship, shipSlots[i]) ? "installed" : "NOT installed"));
                        }
                        sendSystemMessageTestingOnly(self, ship + " getChassisComponentMassMaximum()=" + getChassisComponentMassMaximum(ship));
                        sendSystemMessageTestingOnly(self, ship + " getShipMaximumChassisHitPoints()=" + getShipMaximumChassisHitPoints(ship));
                        sendSystemMessageTestingOnly(self, ship + " getShipCurrentChassisHitPoints()=" + getShipCurrentChassisHitPoints(ship));
                    }
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, ship + " is not of type GOT_ship");
                }
            }
        }
        else if (strText.equals("testEmptyDictionary"))
        {
            dictionary itemDictionary = new dictionary();
            dictionary itemContentsIn = new dictionary();
            itemDictionary.put("contents", itemContentsIn);
            dictionary itemContentsOut = itemDictionary.getDictionary("contents");
            if (itemContentsOut == null)
            {
                sendSystemMessageTestingOnly(self, "itemContentsOut is null");
            }
            else 
            {
                Set keySet = itemContentsOut.keySet();
                if (keySet == null)
                {
                    sendSystemMessageTestingOnly(self, "keySet is null");
                }
                else 
                {
                    Iterator contentsIterator = keySet.iterator();
                    if (contentsIterator == null)
                    {
                        sendSystemMessageTestingOnly(self, "contentsIterator is null");
                    }
                    else 
                    {
                        if (contentsIterator.hasNext())
                        {
                            sendSystemMessageTestingOnly(self, "contentsIterator.hasNext() is true");
                        }
                        Integer[] contents = new Integer[keySet.size()];
                        keySet.toArray(contents);
                        sendSystemMessageTestingOnly(self, "contents.length is " + contents.length);
                    }
                }
            }
            sendSystemMessageTestingOnly(self, "testEmptyDictionary done");
        }
        if (strText.equals("testIntegerLongReferenceParam"))
        {
            int[] i = new int[1];
            i[0] = 777;
            long[] l = new long[1];
            l[0] = 7777L;
            sendSystemMessageTestingOnly(self, "before i=" + i[0] + " l=" + l[0]);
            testIntegerLongReferenceParam(i, l);
            sendSystemMessageTestingOnly(self, "after i=" + i[0] + " l=" + l[0]);
        }
        else if (strText.startsWith("isAccountQualifiedForHousePackup "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "isAccountQualifiedForHousePackup(target=" + target + ") returns " + isAccountQualifiedForHousePackup(target));
            }
        }
        else if (strText.equals("nukeItems"))
        {
            obj_id[] old_waypoints = getWaypointsInDatapad(self);
            if (old_waypoints != null && old_waypoints.length > 0)
            {
                for (obj_id old_waypoint : old_waypoints) {
                    destroyWaypointInDatapad(old_waypoint, self);
                }
            }
            obj_id[] existingItems = getInventoryAndEquipment(self);
            obj_id inv = utils.getInventoryContainer(self);
            if (existingItems != null)
            {
                int existingIter;
                for (existingIter = 0; existingIter < existingItems.length; ++existingIter)
                {
                    if (existingItems[existingIter] != self && existingItems[existingIter] != inv)
                    {
                        destroyObject(existingItems[existingIter]);
                    }
                }
            }
            obj_id playerDatapad = utils.getPlayerDatapad(self);
            obj_id datapadObjects[] = getContents(playerDatapad);
            for (obj_id datapadObject : datapadObjects) {
                destroyObject(datapadObject);
            }
        }
        else if (strText.startsWith("getResourceCtsData "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "getResourceCtsData(resourceContainer=" + object + ") returns " + getResourceCtsData(object));
            }
        }
        else if (strText.startsWith("setCount "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                int count = utils.stringToInt(st.nextToken());
                sendSystemMessageTestingOnly(self, "setCount(target=" + object + ", value=" + count + ") returns " + setCount(object, count));
            }
        }
        else if (strText.startsWith("setCrafter "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                obj_id crafter = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "setCrafter(target=" + target + ", crafter=" + crafter + ") returns " + setCrafter(target, crafter));
            }
        }
        else if (strText.startsWith("getCrafter "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "getCrafter(object=" + object + ") returns " + getCrafter(object));
            }
        }
        else if (strText.startsWith("recomputeCrateAttributes "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id crate = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "calling recomputeCrateAttributes(crate=" + crate + ")");
                recomputeCrateAttributes(crate);
            }
        }
        else if (strText.startsWith("getSkillModBonus "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                String skillMod = st.nextToken();
                sendSystemMessageTestingOnly(self, "getSkillModBonus(target=" + target + ", skillMod=" + skillMod + ") returns " + getSkillModBonus(target, skillMod));
            }
        }
        else if (strText.startsWith("setSkillModBonus "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 4)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                String skillMod = st.nextToken();
                int bonus = utils.stringToInt(st.nextToken());
                sendSystemMessageTestingOnly(self, "setSkillModBonus(target=" + target + ", skillMod=" + skillMod + ", bonus=" + bonus + ") returns " + setSkillModBonus(target, skillMod, bonus));
            }
        }
        else if (strText.startsWith("setCategorizedSkillModBonus "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 5)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                String category = st.nextToken();
                String skillMod = st.nextToken();
                int bonus = utils.stringToInt(st.nextToken());
                sendSystemMessageTestingOnly(self, "setCategorizedSkillModBonus(target=" + target + ", category=" + category + ", skillMod=" + skillMod + ", bonus=" + bonus + ") returns " + setCategorizedSkillModBonus(target, category, skillMod, bonus));
            }
        }
        else if (strText.startsWith("removeCategorizedSkillModBonuses "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                String category = st.nextToken();
                sendSystemMessageTestingOnly(self, "calling removeCategorizedSkillModBonuses(target=" + target + ", category=" + category + ")");
                removeCategorizedSkillModBonuses(target, category);
            }
        }
        else if (strText.startsWith("setObjIdScriptVar "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 4)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                String name = st.nextToken();
                obj_id value = utils.stringToObjId(st.nextToken());
                utils.setScriptVar(object, name, value);
            }
        }
        else if (strText.startsWith("setLocationScriptVar "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 8)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                String name = st.nextToken();
                float x = utils.stringToFloat(st.nextToken());
                float y = utils.stringToFloat(st.nextToken());
                float z = utils.stringToFloat(st.nextToken());
                String scene = st.nextToken();
                obj_id cell = utils.stringToObjId(st.nextToken());
                location value = new location(x, y, z, scene, cell);
                utils.setScriptVar(object, name, value);
            }
        }
        else if (strText.startsWith("setLocationArrayScriptVar "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 8)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                String name = st.nextToken();
                float x = utils.stringToFloat(st.nextToken());
                float y = utils.stringToFloat(st.nextToken());
                float z = utils.stringToFloat(st.nextToken());
                String scene = st.nextToken();
                obj_id cell = utils.stringToObjId(st.nextToken());
                location[] value = new location[1];
                value[0] = new location(x, y, z, scene, cell);
                utils.setScriptVar(object, name, value);
            }
        }
        else if (strText.startsWith("setObjIdArrayScriptVar "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 4)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                String name = st.nextToken();
                obj_id value = utils.stringToObjId(st.nextToken());
                obj_id[] existingValues = utils.getObjIdArrayScriptVar(object, name);
                utils.removeScriptVar(object, name);
                if (existingValues == null)
                {
                    obj_id[] newValues = new obj_id[1];
                    newValues[0] = value;
                    utils.setScriptVar(object, name, newValues);
                }
                else 
                {
                    Vector newValues = new Vector();
                    newValues.setSize(existingValues.length);
                    for (int i = 0; i < existingValues.length; ++i)
                    {
                        newValues.set(i, existingValues[i]);
                    }
                    newValues = utils.addElement(newValues, value);
                    utils.setScriptVar(object, name, newValues);
                }
            }
        }
        else if (strText.startsWith("setObjIdDictionaryScriptVar "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 4)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                String name = st.nextToken();
                obj_id value = utils.stringToObjId(st.nextToken());
                utils.removeScriptVar(object, name);
                dictionary d = new dictionary();
                d.put("value1", value);
                obj_id[] valueArray = new obj_id[1];
                valueArray[0] = value;
                d.put("value2", valueArray);
                utils.setScriptVar(object, name, d);
            }
        }
        else if (strText.equals("checkRadialMenuItems"))
        {
            sendSystemMessageTestingOnly(self, "SERVER_MENU1 = " + menu_info_types.SERVER_MENU1);
            sendSystemMessageTestingOnly(self, "SERVER_MENU2 = " + menu_info_types.SERVER_MENU2);
            sendSystemMessageTestingOnly(self, "SERVER_PET_MOUNT = " + menu_info_types.SERVER_PET_MOUNT);
            sendSystemMessageTestingOnly(self, "SERVER_VEHICLE_ENTER_EXIT = " + menu_info_types.SERVER_VEHICLE_ENTER_EXIT);
        }
        else if (strText.startsWith("createStaticItem "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                String item = st.nextToken();
                String container = st.nextToken();
                obj_id containerOid = utils.stringToObjId(container);
                sendSystemMessageTestingOnly(self, "static_item.createNewItemFunction(item=" + item + ", container=" + containerOid + ") returns " + static_item.createNewItemFunction(item, containerOid));
            }
        }
        else if (strText.startsWith("random "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                int min = utils.stringToInt(st.nextToken());
                int max = utils.stringToInt(st.nextToken());
                sendSystemMessageTestingOnly(self, "rand(min=" + min + ", max=" + max + ") returns " + rand(min, max));
            }
        }
        else if (strText.startsWith("modifyYaw "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                float degrees = utils.stringToFloat(st.nextToken());
                sendSystemMessageTestingOnly(self, "modifyYaw(object=" + object + ", degrees=" + degrees + ")");
                modifyYaw(object, degrees);
            }
        }
        else if (strText.startsWith("modifyPitch "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                float degrees = utils.stringToFloat(st.nextToken());
                sendSystemMessageTestingOnly(self, "modifyPitch(object=" + object + ", degrees=" + degrees + ")");
                modifyPitch(object, degrees);
            }
        }
        else if (strText.startsWith("modifyRoll "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                float degrees = utils.stringToFloat(st.nextToken());
                sendSystemMessageTestingOnly(self, "modifyRoll(object=" + object + ", degrees=" + degrees + ")");
                modifyRoll(object, degrees);
            }
        }
        else if (strText.startsWith("resetRotation "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "resetRotation(object=" + object + ")");
                setQuaternion(object, 1.0f, 0.0f, 0.0f, 0.0f);
            }
        }
        else if (strText.startsWith("rotateRandom "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                int yaw = rand(-180, 180);
                int pitch = rand(-180, 180);
                int roll = rand(-180, 180);
                sendSystemMessageTestingOnly(self, "rotateRandom(object=" + object + ") y=" + yaw + ", p=" + pitch + ", r=" + roll);
                modifyYaw(object, yaw);
                modifyPitch(object, pitch);
                modifyRoll(object, roll);
            }
        }
        else if (strText.startsWith("rotateRandomYaw "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                int degrees = rand(-180, 180);
                sendSystemMessageTestingOnly(self, "rotateRandomYaw(object=" + object + ") degrees=" + degrees);
                modifyYaw(object, degrees);
            }
        }
        else if (strText.startsWith("rotateRandomPitch "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                int degrees = rand(-180, 180);
                sendSystemMessageTestingOnly(self, "rotateRandomPitch(object=" + object + ") degrees=" + degrees);
                modifyPitch(object, degrees);
            }
        }
        else if (strText.startsWith("rotateRandomRoll "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                int degrees = rand(-180, 180);
                sendSystemMessageTestingOnly(self, "rotateRandomRoll(object=" + object + ") degrees=" + degrees);
                modifyRoll(object, degrees);
            }
        }
        else if (strText.startsWith("copyRotation "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                obj_id source = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "copyRotation(target=" + target + ", source=" + source + ")");
                float[] quaternion = getQuaternion(source);
                if ((quaternion != null) && (quaternion.length == 4))
                {
                    setQuaternion(target, quaternion[0], quaternion[1], quaternion[2], quaternion[3]);
                }
            }
        }
        else if (strText.startsWith("copyPosition "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                obj_id source = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "verifying copyPosition(target=" + target + ", source=" + source + ")");
                location sourceLoc = getLocation(source);
                location targetLoc = getLocation(target);
                sendSystemMessageTestingOnly(self, "sourceLoc=" + sourceLoc);
                sendSystemMessageTestingOnly(self, "targetLoc=" + targetLoc);
                if ((sourceLoc != null) && (targetLoc != null) && (sourceLoc.area != null) && (targetLoc.area != null) && (sourceLoc.area.equals(targetLoc.area)) && (isIdValid(sourceLoc.cell)) && (isIdValid(targetLoc.cell)) && (sourceLoc.cell == targetLoc.cell))
                {
                    targetLoc.x = sourceLoc.x;
                    targetLoc.z = sourceLoc.z;
                    sendSystemMessageTestingOnly(self, "doing copyPosition(target=" + target + ", source=" + source + ")");
                    setLocation(target, targetLoc);
                }
            }
        }
        else if (strText.startsWith("copyHeight "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                obj_id source = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "verifying copyHeight(target=" + target + ", source=" + source + ")");
                location sourceLoc = getLocation(source);
                location targetLoc = getLocation(target);
                sendSystemMessageTestingOnly(self, "sourceLoc=" + sourceLoc);
                sendSystemMessageTestingOnly(self, "targetLoc=" + targetLoc);
                if ((sourceLoc != null) && (targetLoc != null) && (sourceLoc.area != null) && (targetLoc.area != null) && (sourceLoc.area.equals(targetLoc.area)) && (isIdValid(sourceLoc.cell)) && (isIdValid(targetLoc.cell)) && (sourceLoc.cell == targetLoc.cell))
                {
                    targetLoc.y = sourceLoc.y;
                    sendSystemMessageTestingOnly(self, "doing copyHeight(target=" + target + ", source=" + source + ")");
                    setLocation(target, targetLoc);
                }
            }
        }
        else if (strText.startsWith("readStringObjvarConvertToOid "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                String objVarName = st.nextToken();
                if (hasObjVar(self, objVarName))
                {
                    String objVarValue = getStringObjVar(self, objVarName);
                    if (objVarValue != null && objVarValue.length() > 0)
                    {
                        obj_id objVarValueAsOid = utils.stringToObjId(objVarValue.substring(1));
                        if (isValidId(objVarValueAsOid))
                        {
                            sendSystemMessageTestingOnly(self, self + " has objvar " + objVarName + " with objvar string value " + objVarValue + " which converts to valid oid " + objVarValueAsOid);
                        }
                        else 
                        {
                            sendSystemMessageTestingOnly(self, self + " has objvar " + objVarName + " but objvar string value " + objVarValue + " is not a valid oid");
                        }
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, self + " has objvar " + objVarName + " but objvar string value is null or empty");
                    }
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, self + " does have objvar " + objVarName);
                }
            }
        }
        else if (strText.startsWith("cspMiss "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 4)
            {
                String command = st.nextToken();
                obj_id attacker = utils.stringToObjId(st.nextToken());
                obj_id defender = utils.stringToObjId(st.nextToken());
                obj_id weapon = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "cspMiss(attacker=" + attacker + ", defender=" + defender + ", weapon=" + weapon + ")");
                combat_engine.hit_result hr = new combat_engine.hit_result();
                hr.success = false;
                sendCombatSpam(attacker, defender, weapon, hr, new string_id(), true, true, true, COMBAT_RESULT_MISS);
            }
        }
        else if (strText.startsWith("cspDodge "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 4)
            {
                String command = st.nextToken();
                obj_id attacker = utils.stringToObjId(st.nextToken());
                obj_id defender = utils.stringToObjId(st.nextToken());
                obj_id weapon = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "cspDodge(attacker=" + attacker + ", defender=" + defender + ", weapon=" + weapon + ")");
                combat_engine.hit_result hr = new combat_engine.hit_result();
                hr.success = false;
                hr.dodge = true;
                sendCombatSpam(attacker, defender, weapon, hr, new string_id(), true, true, true, COMBAT_RESULT_MISS);
            }
        }
        else if (strText.startsWith("cspParry "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 4)
            {
                String command = st.nextToken();
                obj_id attacker = utils.stringToObjId(st.nextToken());
                obj_id defender = utils.stringToObjId(st.nextToken());
                obj_id weapon = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "cspParry(attacker=" + attacker + ", defender=" + defender + ", weapon=" + weapon + ")");
                combat_engine.hit_result hr = new combat_engine.hit_result();
                hr.success = false;
                hr.parry = true;
                sendCombatSpam(attacker, defender, weapon, hr, new string_id(), true, true, true, COMBAT_RESULT_MISS);
            }
        }
        else if (strText.startsWith("cspHit "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 4)
            {
                String command = st.nextToken();
                obj_id attacker = utils.stringToObjId(st.nextToken());
                obj_id defender = utils.stringToObjId(st.nextToken());
                obj_id weapon = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "cspHit(attacker=" + attacker + ", defender=" + defender + ", weapon=" + weapon + ")");
                combat_engine.hit_result hr = new combat_engine.hit_result();
                hr.success = true;
                hr.damage = 777;
                sendCombatSpam(attacker, defender, weapon, hr, new string_id(), true, true, true, COMBAT_RESULT_HIT);
            }
        }
        else if (strText.startsWith("cspCrush "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 4)
            {
                String command = st.nextToken();
                obj_id attacker = utils.stringToObjId(st.nextToken());
                obj_id defender = utils.stringToObjId(st.nextToken());
                obj_id weapon = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "cspCrush(attacker=" + attacker + ", defender=" + defender + ", weapon=" + weapon + ")");
                combat_engine.hit_result hr = new combat_engine.hit_result();
                hr.success = true;
                hr.damage = 777;
                hr.crushing = true;
                sendCombatSpam(attacker, defender, weapon, hr, new string_id(), true, true, true, COMBAT_RESULT_HIT);
            }
        }
        else if (strText.startsWith("cspStrikethrough "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 4)
            {
                String command = st.nextToken();
                obj_id attacker = utils.stringToObjId(st.nextToken());
                obj_id defender = utils.stringToObjId(st.nextToken());
                obj_id weapon = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "cspStrikethrough(attacker=" + attacker + ", defender=" + defender + ", weapon=" + weapon + ")");
                combat_engine.hit_result hr = new combat_engine.hit_result();
                hr.success = true;
                hr.damage = 777;
                hr.strikethrough = true;
                hr.strikethroughAmmount = 12.9f;
                sendCombatSpam(attacker, defender, weapon, hr, new string_id(), true, true, true, COMBAT_RESULT_HIT);
            }
        }
        else if (strText.startsWith("cspEvade "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 4)
            {
                String command = st.nextToken();
                obj_id attacker = utils.stringToObjId(st.nextToken());
                obj_id defender = utils.stringToObjId(st.nextToken());
                obj_id weapon = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "cspEvade(attacker=" + attacker + ", defender=" + defender + ", weapon=" + weapon + ")");
                combat_engine.hit_result hr = new combat_engine.hit_result();
                hr.success = true;
                hr.damage = 777;
                hr.evadeResult = true;
                hr.evadeAmmount = 7.1f;
                sendCombatSpam(attacker, defender, weapon, hr, new string_id(), true, true, true, COMBAT_RESULT_HIT);
            }
        }
        else if (strText.startsWith("cspBlock "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 4)
            {
                String command = st.nextToken();
                obj_id attacker = utils.stringToObjId(st.nextToken());
                obj_id defender = utils.stringToObjId(st.nextToken());
                obj_id weapon = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "cspBlock(attacker=" + attacker + ", defender=" + defender + ", weapon=" + weapon + ")");
                combat_engine.hit_result hr = new combat_engine.hit_result();
                hr.success = true;
                hr.damage = 777;
                hr.blockResult = true;
                hr.block = 183;
                sendCombatSpam(attacker, defender, weapon, hr, new string_id(), true, true, true, COMBAT_RESULT_HIT);
            }
        }
        else if (strText.startsWith("residenceHouseId "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                obj_id value = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "setting residenceHouseId objvar for " + object + " value " + value);
                setObjVar(object, "residenceHouseId", value);
            }
        }
        else if (strText.startsWith("getPlayerIdFromFirstName "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                String firstName = st.nextToken();
                sendSystemMessageTestingOnly(self, "getPlayerIdFromFirstName(" + firstName + ") returns " + getPlayerIdFromFirstName(firstName));
            }
        }
        else if (strText.startsWith("getName "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "getName(object=" + object + ") is (" + getName(object) + ")");
                sendSystemMessageTestingOnly(self, "getFirstName(object=" + object + ") is (" + getFirstName(object) + ")");
                sendSystemMessageTestingOnly(self, "getPlayerName(object=" + object + ") is (" + getPlayerName(object) + ")");
                sendSystemMessageTestingOnly(self, "getPlayerFullName(object=" + object + ") is (" + getPlayerFullName(object) + ")");
            }
        }
        else if (strText.startsWith("msgDestroyStructure "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id structure = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "sending msgDestroyStructure for structure " + structure + " with a delay of 10 seconds");
                messageTo(structure, "msgDestroyStructure", null, 10.0f, false);
            }
        }
        else if (strText.startsWith("isAuthoritative "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                if (exists(object))
                {
                    sendSystemMessageTestingOnly(self, "obj_id.isAuthoritative(object=" + object + ") returns " + object.isAuthoritative());
                    sendSystemMessageTestingOnly(self, "obj_id.isLoaded(object=" + object + ") returns " + object.isLoaded());
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "object " + object + " does not exist");
                }
            }
        }
        else if (strText.startsWith("getOwner "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                if (exists(object))
                {
                    sendSystemMessageTestingOnly(self, "getOwner(object=" + object + ") returns " + getOwner(object));
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "object " + object + " does not exist");
                }
            }
        }
        else if (strText.startsWith("getCitizenOfCityId "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "getCitizenOfCityId target=" + object + " returns " + getCitizenOfCityId(object));
            }
        }
        else if (strText.startsWith("getPlanetByName "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                String planet = st.nextToken();
                sendSystemMessageTestingOnly(self, "getPlanetByName planet=" + planet + " returns " + getPlanetByName(planet));
            }
        }
        else if (strText.equals("registerCloningFacility"))
        {
            location cloneLoc = getWorldLocation(self);
            location cloneRespawn = getLocation(self);
            obj_id planet = getPlanetByName(cloneLoc.area);
            if (isIdValid(planet))
            {
                dictionary params = new dictionary();
                params.put("id", self);
                params.put("name", getName(self));
                params.put("buildout", "");
                params.put("areaId", self);
                params.put("loc", cloneLoc);
                params.put("respawn", cloneRespawn);
                params.put("type", 0);
                messageTo(planet, "registerCloningFacility", params, 1.0f, false);
                sendSystemMessageTestingOnly(self, "registerCloningFacility planet=" + planet);
            }
        }
        else if (strText.equals("removeTef"))
        {
            pvpRemoveAllTempEnemyFlags(self);
            sendSystemMessageTestingOnly(self, "removeTef");
        }
        else if (strText.startsWith("doDamage "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                int amount = utils.stringToInt(st.nextToken());
                hit_result hit = new hit_result();
                hit.damage = amount;
                sendSystemMessageTestingOnly(self, "doDamage(attacker=" + self + ", target=" + target + ", amount=" + amount + ")");
                doDamage(self, target, hit);
                addHate(self, target, 0.0f);
                addHate(target, self, 0.0f);
            }
        }
        else if (strText.startsWith("logActivity "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id player = utils.stringToObjId(st.nextToken());
                int activity = utils.stringToInt(st.nextToken());
                sendSystemMessageTestingOnly(self, "session.logActivity(player=" + player + ", activity=" + activity + ")");
                session.logActivity(player, activity);
            }
        }
        else if (strText.startsWith("canHelp "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id actor = utils.stringToObjId(st.nextToken());
                obj_id target = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "pvpCanHelp(actor=" + actor + ", target=" + target + ") returns " + pvpCanHelp(actor, target));
            }
        }
        else if (strText.startsWith("canSee "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id source = utils.stringToObjId(st.nextToken());
                obj_id target = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "canSee(source=" + source + ", target=" + target + ") returns " + canSee(source, target));
                sendSystemMessageTestingOnly(self, "canSee(source=" + target + ", target=" + source + ") returns " + canSee(target, source));
            }
        }
        else if (strText.startsWith("testSui "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                int buttons = utils.stringToInt(st.nextToken());
                sendSystemMessageTestingOnly(self, "calling sui.msgbox() with button(s) " + buttons);
                int pid = sui.msgbox(self, self, "this is a test message", buttons, "testSuiHandler");
            }
        }
        else if (strText.startsWith("testSuiObjectRange "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 4)
            {
                String command = st.nextToken();
                int buttons = utils.stringToInt(st.nextToken());
                obj_id object = utils.stringToObjId(st.nextToken());
                float range = utils.stringToFloat(st.nextToken());
                int pid = createSUIPage(sui.SUI_MSGBOX, self, self, "testSuiHandler");
                setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, "this is a test message");
                setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, sui.DEFAULT_TITLE);
                sui.msgboxButtonSetup(pid, buttons);
                setSUIAssociatedObject(pid, object);
                setSUIMaxRangeToObject(pid, range);
                showSUIPage(pid);
                sendSystemMessageTestingOnly(self, "calling sui.msgbox() with button(s)=" + buttons + ", object=" + object + ", range=" + range + " returns pid " + pid);
            }
        }
        else if (strText.startsWith("testSuiLocationRange "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 4)
            {
                String command = st.nextToken();
                int buttons = utils.stringToInt(st.nextToken());
                obj_id object = utils.stringToObjId(st.nextToken());
                float range = utils.stringToFloat(st.nextToken());
                int pid = createSUIPage(sui.SUI_MSGBOX, self, self, "testSuiHandler");
                setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, "this is a test message");
                setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, sui.DEFAULT_TITLE);
                sui.msgboxButtonSetup(pid, buttons);
                setSUIAssociatedLocation(pid, object);
                setSUIMaxRangeToObject(pid, range);
                showSUIPage(pid);
                sendSystemMessageTestingOnly(self, "calling sui.msgbox() with button(s)=" + buttons + ", object=" + object + ", range=" + range + " returns pid " + pid);
            }
        }
        else if (strText.startsWith("hidePlayer "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "calling setCreatureCoverVisibility(target=" + target + ", isVisible=false)");
                setCreatureCoverVisibility(target, false);
            }
        }
        else if (strText.startsWith("huyPilotShip "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id pilot = utils.stringToObjId(st.nextToken());
                obj_id ship = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "pilotShip(pilot=" + pilot + ", ship=" + ship + ") returns " + pilotShip(pilot, ship));
            }
        }
        else if (strText.startsWith("showPlayer "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "calling setCreatureCoverVisibility(target=" + target + ", isVisible=true)");
                setCreatureCoverVisibility(target, true);
            }
        }
        else if (strText.startsWith("hideObject "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "calling hideFromClient(target=" + target + ", hide=true)");
                hideFromClient(target, true);
            }
        }
        else if (strText.startsWith("showObject "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "calling hideFromClient(target=" + target + ", hide=false)");
                hideFromClient(target, false);
            }
        }
        else if (strText.startsWith("addPassiveReveal "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 4)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                obj_id target = utils.stringToObjId(st.nextToken());
                int range = utils.stringToInt(st.nextToken());
                sendSystemMessageTestingOnly(self, "calling addPassiveReveal(object=" + object + ", target=" + target + ", range=" + range + ")");
                addPassiveReveal(object, target, range);
            }
        }
        else if (strText.startsWith("removePassiveReveal "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                obj_id target = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "calling removePassiveReveal(object=" + object + ", target=" + target + ")");
                removePassiveReveal(object, target);
            }
        }
        else if (strText.startsWith("clearPassiveRevealList "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "calling clearPassiveRevealList(object=" + object + ")");
                clearPassiveRevealList(object);
            }
        }
        else if (strText.startsWith("getPassiveRevealRange "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                obj_id target = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "getPassiveRevealRange(object=" + object + ", target=" + target + ") returns " + getPassiveRevealRange(object, target));
            }
        }
        else if (strText.startsWith("getPassiveRevealList "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                dictionary revealList = getPassiveRevealList(object);
                if (revealList == null)
                {
                    sendSystemMessageTestingOnly(self, "getPassiveRevealList(object=" + object + ") returns null");
                }
                else 
                {
                    obj_id[] ids = revealList.getObjIdArray("id");
                    int[] range = revealList.getIntArray("range");
                    if (ids == null)
                    {
                        sendSystemMessageTestingOnly(self, "getPassiveRevealList(object=" + object + ") returns null id list");
                    }
                    if (ids.length == 0)
                    {
                        sendSystemMessageTestingOnly(self, "getPassiveRevealList(object=" + object + ") returns 0-length id list");
                    }
                    if (range == null)
                    {
                        sendSystemMessageTestingOnly(self, "getPassiveRevealList(object=" + object + ") returns null range list");
                    }
                    if (range.length == 0)
                    {
                        sendSystemMessageTestingOnly(self, "getPassiveRevealList(object=" + object + ") returns 0-length range list");
                    }
                    if (ids.length != range.length)
                    {
                        sendSystemMessageTestingOnly(self, "getPassiveRevealList(object=" + object + ") returns different size id (" + ids.length + ") and range (" + range.length + ") lists");
                    }
                    if ((ids != null) && (ids.length > 0) && (range != null) && (range.length > 0) && (ids.length == range.length))
                    {
                        sendSystemMessageTestingOnly(self, "getPassiveRevealList(object=" + object + ") returns:");
                        for (int i = 0; i < ids.length; ++i)
                        {
                            sendSystemMessageTestingOnly(self, "id=" + ids[i] + ", range=" + range[i]);
                        }
                    }
                }
            }
        }
        else if (strText.startsWith("modifyCurrentGcwPoints "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                int amount = utils.stringToInt(st.nextToken());
                sendSystemMessageTestingOnly(self, "modifyCurrentGcwPoints(target=" + target + ", adjustment=" + amount + ")");
                pvpModifyCurrentGcwPoints(target, amount);
            }
        }
        else if (strText.startsWith("modifyCurrentPvpKills "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                int amount = utils.stringToInt(st.nextToken());
                sendSystemMessageTestingOnly(self, "modifyCurrentPvpKills(target=" + target + ", adjustment=" + amount + ")");
                pvpModifyCurrentPvpKills(target, amount);
            }
        }
        else if (strText.startsWith("getGcwRank "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "getGcwRank(target=" + target + ") returns current=" + pvpGetCurrentGcwRank(target) + ", maxImperial=" + pvpGetMaxGcwImperialRank(target) + ", maxRebel=" + pvpGetMaxGcwRebelRank(target));
            }
        }
        else if (strText.startsWith("getGcwInfo "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "getGcwInfo(target=" + target + ") returns points=" + pvpGetCurrentGcwPoints(target) + ", rating=" + pvpGetCurrentGcwRating(target) + ", kills=" + pvpGetCurrentPvpKills(target) + ", lifetime points=" + pvpGetLifetimeGcwPoints(target) + ", max imp rating=" + pvpGetMaxGcwImperialRating(target) + ", max reb rating=" + pvpGetMaxGcwRebelRating(target) + ", lifetime kills=" + pvpGetLifetimePvpKills(target) + ", next recalc=" + pvpGetNextGcwRatingCalcTime(target));
            }
        }
        else if (strText.startsWith("transferGcwInfo "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id source = utils.stringToObjId(st.nextToken());
                obj_id target = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "transferGcwInfo(source=" + source + ", target=" + target + ")");
                dictionary characterData = new dictionary();
                characterData.put("gcw_current_point", pvpGetCurrentGcwPoints(source));
                characterData.put("gcw_current_rating", pvpGetCurrentGcwRating(source));
                characterData.put("gcw_current_pvp_kill", pvpGetCurrentPvpKills(source));
                characterData.put("gcw_lifetime_point", pvpGetLifetimeGcwPoints(source));
                characterData.put("gcw_max_imperial_rating", pvpGetMaxGcwImperialRating(source));
                characterData.put("gcw_max_rebel_rating", pvpGetMaxGcwRebelRating(source));
                characterData.put("gcw_lifetime_pvp_kill", pvpGetLifetimePvpKills(source));
                characterData.put("gcw_next_rating_calc_time", pvpGetNextGcwRatingCalcTime(source));
                ctsUseOnlySetGcwInfo(target, characterData.getInt("gcw_current_point"), characterData.getInt("gcw_current_rating"), characterData.getInt("gcw_current_pvp_kill"), characterData.getLong("gcw_lifetime_point"), characterData.getInt("gcw_max_imperial_rating"), characterData.getInt("gcw_max_rebel_rating"), characterData.getInt("gcw_lifetime_pvp_kill"), characterData.getInt("gcw_next_rating_calc_time"));
            }
        }
        else if (strText.startsWith("isInWorldCell "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "isInWorldCell(target=" + target + ") returns " + isInWorldCell(target));
            }
        }
        else if (strText.startsWith("isAccountQualifiedForHousePackup "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "isAccountQualifiedForHousePackup(target=" + target + ") returns " + isAccountQualifiedForHousePackup(target));
            }
        }
        else if (strText.equals("sneak"))
        {
            sendSystemMessageTestingOnly(self, "doing queueCommand(sp_buff_stealth_1)");
            queueCommand(self, (-1926826718), null, "", COMMAND_PRIORITY_DEFAULT);
        }
        else if (strText.startsWith("checkBadgeCount "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "badge.checkBadgeCount(target=" + target + ") returns " + badge.checkBadgeCount(target));
            }
        }
        else if (strText.startsWith("addOldBadge "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id player = utils.stringToObjId(st.nextToken());
                int badgeId = utils.stringToInt(st.nextToken());
                int[] badges = getIntArrayObjVar(player, "badge.tracking");
                int count = getIntObjVar(player, "badge.count");
                removeObjVar(player, "badge.tracking");
                removeObjVar(player, "badge.count");
                if ((badges == null) || (badges.length == 0))
                {
                    badges = new int[15];
                    for (int i = 0; i < 15; ++i)
                    {
                        badges[i] = 0;
                    }
                }
                else if (badges.length < 15)
                {
                    int[] newBadges = new int[15];
                    for (int i = 0; i < 15; ++i)
                    {
                        newBadges[i] = 0;
                    }
                    for (int j = 0; j < badges.length; ++j)
                    {
                        newBadges[j] = badges[j];
                    }
                    badges = newBadges;
                }
                int arrayIdx = badgeId / 32;
                int bitIdx = badgeId % 32;
                if ((arrayIdx < 15) && (!utils.checkBit(badges[arrayIdx], bitIdx)))
                {
                    badges[arrayIdx] = utils.setBit(badges[arrayIdx], bitIdx);
                    count++;
                }
                setObjVar(player, "badge.tracking", badges);
                setObjVar(player, "badge.count", count);
                sendSystemMessageTestingOnly(self, "addOldBadge player=" + player + ", badgeId=" + badgeId + " (" + getCollectionSlotName(badgeId) + ")");
            }
        }
        else if (strText.startsWith("explorerBadgeByNumber "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                int badgeNum = utils.stringToInt(st.nextToken());
                sendSystemMessageTestingOnly(self, "explorerBadgeByNumber(target=" + target + ", badgeNum=" + badgeNum + ")");
                dictionary explorerBadges = new dictionary();
                explorerBadges.put("badgeNumber", badgeNum);
                messageTo(target, "explorerBadge", explorerBadges, 0, false);
            }
        }
        else if (strText.startsWith("explorerBadgeByName "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                String badgeName = st.nextToken();
                sendSystemMessageTestingOnly(self, "explorerBadgeByNumber(target=" + target + ", badgeName=" + badgeName + ")");
                dictionary explorerBadges = new dictionary();
                explorerBadges.put("badgeName", badgeName);
                messageTo(target, "explorerBadge", explorerBadges, 0, false);
            }
        }
        else if (strText.startsWith("addHate "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 4)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                obj_id hateTarget = utils.stringToObjId(st.nextToken());
                float hateValue = utils.stringToFloat(st.nextToken());
                sendSystemMessageTestingOnly(self, "addHate(object=" + object + ", hateTarget=" + hateTarget + ", hate=" + hateValue + ")");
                addHate(object, hateTarget, hateValue);
            }
        }
        else if (strText.startsWith("addHateDot "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 5)
            {
                String command = st.nextToken();
                obj_id o = utils.stringToObjId(st.nextToken());
                obj_id target = utils.stringToObjId(st.nextToken());
                float hate = utils.stringToFloat(st.nextToken());
                int seconds = utils.stringToInt(st.nextToken());
                sendSystemMessageTestingOnly(self, "addHateDot(object=" + o + ", hateTarget=" + target + ", hateAmount=" + hate + ", seconds=" + seconds + ")");
                addHateDot(o, target, hate, seconds);
            }
        }
        else if (strText.startsWith("getHate "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                obj_id hateTarget = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "getHate(object=" + object + ", hateTarget=" + hateTarget + ") returns " + getHate(object, hateTarget));
            }
        }
        else if (strText.startsWith("getDefaultWeapon "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id player = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "getDefaultWeapon(player=" + player + ") returns " + getDefaultWeapon(player));
            }
        }
        else if (strText.startsWith("removeOldBadge "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id player = utils.stringToObjId(st.nextToken());
                int badgeId = utils.stringToInt(st.nextToken());
                int[] badges = getIntArrayObjVar(player, "badge.tracking");
                int count = getIntObjVar(player, "badge.count");
                removeObjVar(player, "badge.tracking");
                removeObjVar(player, "badge.count");
                if ((badges == null) || (badges.length == 0))
                {
                    badges = new int[15];
                    for (int i = 0; i < 15; ++i)
                    {
                        badges[i] = 0;
                    }
                }
                else if (badges.length < 15)
                {
                    int[] newBadges = new int[15];
                    for (int i = 0; i < 15; ++i)
                    {
                        newBadges[i] = 0;
                    }
                    for (int j = 0; j < badges.length; ++j)
                    {
                        newBadges[j] = badges[j];
                    }
                    badges = newBadges;
                }
                int arrayIdx = badgeId / 32;
                int bitIdx = badgeId % 32;
                if ((arrayIdx < 15) && (utils.checkBit(badges[arrayIdx], bitIdx)))
                {
                    badges[arrayIdx] = utils.clearBit(badges[arrayIdx], bitIdx);
                    count--;
                }
                setObjVar(player, "badge.tracking", badges);
                setObjVar(player, "badge.count", count);
                sendSystemMessageTestingOnly(self, "removeOldBadge player=" + player + ", badgeId=" + badgeId + " (" + getCollectionSlotName(badgeId) + ")");
            }
        }
        else if (strText.startsWith("modifyCollectionSlotValue "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 4)
            {
                String command = st.nextToken();
                obj_id player = utils.stringToObjId(st.nextToken());
                String slotName = st.nextToken();
                obj_id delta = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "modifyCollectionSlotValue(player=" + player + ", slotName=" + slotName + ", delta=" + delta + ") returns " + modifyCollectionSlotValue(player, slotName, delta.getValue()));
            }
        }
        else if (strText.startsWith("getCollectionSlotValue "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id player = utils.stringToObjId(st.nextToken());
                String slotName = st.nextToken();
                sendSystemMessageTestingOnly(self, "getCollectionSlotValue(player=" + player + ", slotName=" + slotName + ") returns " + getCollectionSlotValue(player, slotName));
            }
        }
        else if (strText.startsWith("hasCompletedCollectionSlotPrereq "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id player = utils.stringToObjId(st.nextToken());
                String slotName = st.nextToken();
                sendSystemMessageTestingOnly(self, "hasCompletedCollectionSlotPrereq(player=" + player + ", slotName=" + slotName + ") returns " + hasCompletedCollectionSlotPrereq(player, slotName));
            }
        }
        else if (strText.startsWith("hasCompletedCollectionSlot "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id player = utils.stringToObjId(st.nextToken());
                String slotName = st.nextToken();
                sendSystemMessageTestingOnly(self, "hasCompletedCollectionSlot(player=" + player + ", slotName=" + slotName + ") returns " + hasCompletedCollectionSlot(player, slotName));
            }
        }
        else if (strText.startsWith("hasCompletedCollection "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id player = utils.stringToObjId(st.nextToken());
                String collectionName = st.nextToken();
                sendSystemMessageTestingOnly(self, "hasCompletedCollection(player=" + player + ", collectionName=" + collectionName + ") returns " + hasCompletedCollection(player, collectionName));
            }
        }
        else if (strText.startsWith("hasCompletedCollectionPage "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id player = utils.stringToObjId(st.nextToken());
                String pageName = st.nextToken();
                sendSystemMessageTestingOnly(self, "hasCompletedCollectionPage(player=" + player + ", pageName=" + pageName + ") returns " + hasCompletedCollectionPage(player, pageName));
            }
        }
        else if (strText.startsWith("hasCompletedCollectionBook "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id player = utils.stringToObjId(st.nextToken());
                String bookName = st.nextToken();
                sendSystemMessageTestingOnly(self, "hasCompletedCollectionBook(player=" + player + ", bookName=" + bookName + ") returns " + hasCompletedCollectionBook(player, bookName));
            }
        }
        else if (strText.startsWith("getCompletedCollectionSlotsInCollection "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id player = utils.stringToObjId(st.nextToken());
                String collectionName = st.nextToken();
                sendSystemMessageTestingOnly(self, "getCompletedCollectionSlotsInCollection(player=" + player + ", collectionName=" + collectionName + ") returns:");
                String[] slotNames = getCompletedCollectionSlotsInCollection(player, collectionName);
                if ((slotNames == null) || (slotNames.length < 1))
                {
                    sendSystemMessageTestingOnly(self, "no entries");
                }
                else 
                {
                    for (String slotName : slotNames) {
                        sendSystemMessageTestingOnly(self, slotName);
                    }
                }
            }
        }
        else if (strText.startsWith("getCompletedCollectionSlotsInPage "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id player = utils.stringToObjId(st.nextToken());
                String pageName = st.nextToken();
                sendSystemMessageTestingOnly(self, "getCompletedCollectionSlotsInPage(player=" + player + ", pageName=" + pageName + ") returns:");
                String[] slotNames = getCompletedCollectionSlotsInPage(player, pageName);
                if ((slotNames == null) || (slotNames.length < 1))
                {
                    sendSystemMessageTestingOnly(self, "no entries");
                }
                else 
                {
                    for (String slotName : slotNames) {
                        sendSystemMessageTestingOnly(self, slotName);
                    }
                }
            }
        }
        else if (strText.startsWith("getCompletedCollectionsInPage "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id player = utils.stringToObjId(st.nextToken());
                String pageName = st.nextToken();
                sendSystemMessageTestingOnly(self, "getCompletedCollectionsInPage(player=" + player + ", pageName=" + pageName + ") returns:");
                String[] collectionNames = getCompletedCollectionsInPage(player, pageName);
                if ((collectionNames == null) || (collectionNames.length < 1))
                {
                    sendSystemMessageTestingOnly(self, "no entries");
                }
                else 
                {
                    for (String collectionName : collectionNames) {
                        sendSystemMessageTestingOnly(self, collectionName);
                    }
                }
            }
        }
        else if (strText.startsWith("getCompletedCollectionSlotsInBook "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id player = utils.stringToObjId(st.nextToken());
                String bookName = st.nextToken();
                sendSystemMessageTestingOnly(self, "getCompletedCollectionSlotsInBook(player=" + player + ", bookName=" + bookName + ") returns:");
                String[] slotNames = getCompletedCollectionSlotsInBook(player, bookName);
                if ((slotNames == null) || (slotNames.length < 1))
                {
                    sendSystemMessageTestingOnly(self, "no entries");
                }
                else 
                {
                    for (String slotName : slotNames) {
                        sendSystemMessageTestingOnly(self, slotName);
                    }
                }
            }
        }
        else if (strText.startsWith("getCompletedCollectionsInBook "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id player = utils.stringToObjId(st.nextToken());
                String bookName = st.nextToken();
                sendSystemMessageTestingOnly(self, "getCompletedCollectionsInBook(player=" + player + ", bookName=" + bookName + ") returns:");
                String[] collectionNames = getCompletedCollectionsInBook(player, bookName);
                if ((collectionNames == null) || (collectionNames.length < 1))
                {
                    sendSystemMessageTestingOnly(self, "no entries");
                }
                else 
                {
                    for (String collectionName : collectionNames) {
                        sendSystemMessageTestingOnly(self, collectionName);
                    }
                }
            }
        }
        else if (strText.startsWith("getCompletedCollectionPagesInBook "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id player = utils.stringToObjId(st.nextToken());
                String bookName = st.nextToken();
                sendSystemMessageTestingOnly(self, "getCompletedCollectionPagesInBook(player=" + player + ", bookName=" + bookName + ") returns:");
                String[] pageNames = getCompletedCollectionPagesInBook(player, bookName);
                if ((pageNames == null) || (pageNames.length < 1))
                {
                    sendSystemMessageTestingOnly(self, "no entries");
                }
                else 
                {
                    for (String pageName : pageNames) {
                        sendSystemMessageTestingOnly(self, pageName);
                    }
                }
            }
        }
        else if (strText.startsWith("getCompletedCollectionBooks "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id player = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "getCompletedCollectionBooks(player=" + player + ") returns:");
                String[] bookNames = getCompletedCollectionBooks(player);
                if ((bookNames == null) || (bookNames.length < 1))
                {
                    sendSystemMessageTestingOnly(self, "no entries");
                }
                else 
                {
                    for (String bookName : bookNames) {
                        sendSystemMessageTestingOnly(self, bookName);
                    }
                }
            }
        }
        else if (strText.startsWith("getCollectionSlotInfo "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                String slotName = st.nextToken();
                sendSystemMessageTestingOnly(self, "getCollectionSlotInfo(slotName=" + slotName + ") returns:");
                String[] info = getCollectionSlotInfo(slotName);
                if ((info == null) || (info.length != COLLECTION_INFO_ARRAY_SIZE))
                {
                    sendSystemMessageTestingOnly(self, "no entries");
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "book=" + info[COLLECTION_INFO_INDEX_BOOK]);
                    sendSystemMessageTestingOnly(self, "page=" + info[COLLECTION_INFO_INDEX_PAGE]);
                    sendSystemMessageTestingOnly(self, "collection=" + info[COLLECTION_INFO_INDEX_COLLECTION]);
                    sendSystemMessageTestingOnly(self, "music=" + info[COLLECTION_INFO_INDEX_MUSIC]);
                }
            }
        }
        else if (strText.startsWith("isCollectionSlotATitle "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                String slotName = st.nextToken();
                sendSystemMessageTestingOnly(self, "isCollectionSlotATitle(slotName=" + slotName + ") returns " + isCollectionSlotATitle(slotName));
            }
        }
        else if (strText.startsWith("isCollectionATitle "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                String collectionName = st.nextToken();
                sendSystemMessageTestingOnly(self, "isCollectionATitle(collectionName=" + collectionName + ") returns " + isCollectionATitle(collectionName));
            }
        }
        else if (strText.startsWith("isCollectionPageATitle "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                String pageName = st.nextToken();
                sendSystemMessageTestingOnly(self, "isCollectionPageATitle(pageName=" + pageName + ") returns " + isCollectionPageATitle(pageName));
            }
        }
        else if (strText.startsWith("getCollectionSlotCategoryInfo "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                String slotName = st.nextToken();
                sendSystemMessageTestingOnly(self, "getCollectionSlotCategoryInfo(slotName=" + slotName + ") returns:");
                String[] category = getCollectionSlotCategoryInfo(slotName);
                if ((category == null) || (category.length < 1))
                {
                    sendSystemMessageTestingOnly(self, "no entries");
                }
                else 
                {
                    for (String s : category) {
                        sendSystemMessageTestingOnly(self, s);
                    }
                }
            }
        }
        else if (strText.startsWith("getCollectionSlotPrereqInfo "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                String slotName = st.nextToken();
                sendSystemMessageTestingOnly(self, "getCollectionSlotPrereqInfo(slotName=" + slotName + ") returns:");
                String[] prereqs = getCollectionSlotPrereqInfo(slotName);
                if ((prereqs == null) || (prereqs.length < 1))
                {
                    sendSystemMessageTestingOnly(self, "no entries");
                }
                else 
                {
                    for (String prereq : prereqs) {
                        sendSystemMessageTestingOnly(self, prereq);
                    }
                }
            }
        }
        else if (strText.startsWith("getCollectionSlotName "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                int collectionId = utils.stringToInt(st.nextToken());
                sendSystemMessageTestingOnly(self, "getCollectionSlotName(collectionId=" + collectionId + ") returns " + getCollectionSlotName(collectionId));
            }
        }
        else if (strText.startsWith("getAllCollectionSlotsInCollection "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                String collectionName = st.nextToken();
                sendSystemMessageTestingOnly(self, "getAllCollectionSlotsInCollection(collectionName=" + collectionName + ") returns:");
                String[] slotNames = getAllCollectionSlotsInCollection(collectionName);
                if ((slotNames == null) || (slotNames.length < 1))
                {
                    sendSystemMessageTestingOnly(self, "no entries");
                }
                else 
                {
                    for (String slotName : slotNames) {
                        sendSystemMessageTestingOnly(self, slotName);
                    }
                }
            }
        }
        else if (strText.startsWith("getAllCollectionSlotsInPage "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                String pageName = st.nextToken();
                sendSystemMessageTestingOnly(self, "getAllCollectionSlotsInPage(pageName=" + pageName + ") returns:");
                String[] slotNames = getAllCollectionSlotsInPage(pageName);
                if ((slotNames == null) || (slotNames.length < 1))
                {
                    sendSystemMessageTestingOnly(self, "no entries");
                }
                else 
                {
                    for (String slotName : slotNames) {
                        sendSystemMessageTestingOnly(self, slotName);
                    }
                }
            }
        }
        else if (strText.startsWith("getAllCollectionsInPage "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                String pageName = st.nextToken();
                sendSystemMessageTestingOnly(self, "getAllCollectionsInPage(pageName=" + pageName + ") returns:");
                String[] collectionNames = getAllCollectionsInPage(pageName);
                if ((collectionNames == null) || (collectionNames.length < 1))
                {
                    sendSystemMessageTestingOnly(self, "no entries");
                }
                else 
                {
                    for (String collectionName : collectionNames) {
                        sendSystemMessageTestingOnly(self, collectionName);
                    }
                }
            }
        }
        else if (strText.startsWith("getAllCollectionSlotsInBook "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                String bookName = st.nextToken();
                sendSystemMessageTestingOnly(self, "getAllCollectionSlotsInBook(bookName=" + bookName + ") returns:");
                String[] slotNames = getAllCollectionSlotsInBook(bookName);
                if ((slotNames == null) || (slotNames.length < 1))
                {
                    sendSystemMessageTestingOnly(self, "no entries");
                }
                else 
                {
                    for (String slotName : slotNames) {
                        sendSystemMessageTestingOnly(self, slotName);
                    }
                }
            }
        }
        else if (strText.startsWith("getAllCollectionsInBook "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                String bookName = st.nextToken();
                sendSystemMessageTestingOnly(self, "getAllCollectionsInBook(bookName=" + bookName + ") returns:");
                String[] collectionNames = getAllCollectionsInBook(bookName);
                if ((collectionNames == null) || (collectionNames.length < 1))
                {
                    sendSystemMessageTestingOnly(self, "no entries");
                }
                else 
                {
                    for (String collectionName : collectionNames) {
                        sendSystemMessageTestingOnly(self, collectionName);
                    }
                }
            }
        }
        else if (strText.startsWith("getAllCollectionPagesInBook "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                String bookName = st.nextToken();
                sendSystemMessageTestingOnly(self, "getAllCollectionPagesInBook(bookName=" + bookName + ") returns:");
                String[] pageNames = getAllCollectionPagesInBook(bookName);
                if ((pageNames == null) || (pageNames.length < 1))
                {
                    sendSystemMessageTestingOnly(self, "no entries");
                }
                else 
                {
                    for (String pageName : pageNames) {
                        sendSystemMessageTestingOnly(self, pageName);
                    }
                }
            }
        }
        else if (strText.equals("getAllCollectionBooks"))
        {
            sendSystemMessageTestingOnly(self, "getAllCollectionBooks() returns:");
            String[] bookNames = getAllCollectionBooks();
            if ((bookNames == null) || (bookNames.length < 1))
            {
                sendSystemMessageTestingOnly(self, "no entries");
            }
            else 
            {
                for (String bookName : bookNames) {
                    sendSystemMessageTestingOnly(self, bookName);
                }
            }
        }
        else if (strText.startsWith("getAllCollectionSlotsInCategory "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                String categoryName = st.nextToken();
                sendSystemMessageTestingOnly(self, "getAllCollectionSlotsInCategory(categoryName=" + categoryName + ") returns:");
                String[] slotNames = getAllCollectionSlotsInCategory(categoryName);
                if ((slotNames == null) || (slotNames.length < 1))
                {
                    sendSystemMessageTestingOnly(self, "no entries");
                }
                else 
                {
                    for (String slotName : slotNames) {
                        sendSystemMessageTestingOnly(self, slotName);
                    }
                }
            }
        }
        else if (strText.startsWith("getAllCollectionSlotsInCategoryInCollection "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                String collectionName = st.nextToken();
                String categoryName = st.nextToken();
                sendSystemMessageTestingOnly(self, "getAllCollectionSlotsInCategoryInCollection(collectionName=" + collectionName + ", categoryName=" + categoryName + ") returns:");
                String[] slotNames = getAllCollectionSlotsInCategoryInCollection(collectionName, categoryName);
                if ((slotNames == null) || (slotNames.length < 1))
                {
                    sendSystemMessageTestingOnly(self, "no entries");
                }
                else 
                {
                    for (String slotName : slotNames) {
                        sendSystemMessageTestingOnly(self, slotName);
                    }
                }
            }
        }
        else if (strText.startsWith("getAllCollectionSlotsInCategoryInPage "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                String pageName = st.nextToken();
                String categoryName = st.nextToken();
                sendSystemMessageTestingOnly(self, "getAllCollectionSlotsInCategoryInPage(pageName=" + pageName + ", categoryName=" + categoryName + ") returns:");
                String[] slotNames = getAllCollectionSlotsInCategoryInPage(pageName, categoryName);
                if ((slotNames == null) || (slotNames.length < 1))
                {
                    sendSystemMessageTestingOnly(self, "no entries");
                }
                else 
                {
                    for (String slotName : slotNames) {
                        sendSystemMessageTestingOnly(self, slotName);
                    }
                }
            }
        }
        else if (strText.startsWith("getAllCollectionSlotsInCategoryInBook "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                String bookName = st.nextToken();
                String categoryName = st.nextToken();
                sendSystemMessageTestingOnly(self, "getAllCollectionSlotsInCategoryInBook(bookName=" + bookName + ", categoryName=" + categoryName + ") returns:");
                String[] slotNames = getAllCollectionSlotsInCategoryInBook(bookName, categoryName);
                if ((slotNames == null) || (slotNames.length < 1))
                {
                    sendSystemMessageTestingOnly(self, "no entries");
                }
                else 
                {
                    for (String slotName : slotNames) {
                        sendSystemMessageTestingOnly(self, slotName);
                    }
                }
            }
        }
        else if (strText.startsWith("getAllCollectionSlotCategoriesInCollection "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                String collectionName = st.nextToken();
                sendSystemMessageTestingOnly(self, "getAllCollectionSlotCategoriesInCollection(collectionName=" + collectionName + ") returns:");
                String[] categoryNames = getAllCollectionSlotCategoriesInCollection(collectionName);
                if ((categoryNames == null) || (categoryNames.length < 1))
                {
                    sendSystemMessageTestingOnly(self, "no entries");
                }
                else 
                {
                    for (String categoryName : categoryNames) {
                        sendSystemMessageTestingOnly(self, categoryName);
                    }
                }
            }
        }
        else if (strText.startsWith("getAllCollectionSlotCategoriesInPage "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                String pageName = st.nextToken();
                sendSystemMessageTestingOnly(self, "getAllCollectionSlotCategoriesInPage(pageName=" + pageName + ") returns:");
                String[] categoryNames = getAllCollectionSlotCategoriesInPage(pageName);
                if ((categoryNames == null) || (categoryNames.length < 1))
                {
                    sendSystemMessageTestingOnly(self, "no entries");
                }
                else 
                {
                    for (String categoryName : categoryNames) {
                        sendSystemMessageTestingOnly(self, categoryName);
                    }
                }
            }
        }
        else if (strText.startsWith("getAllCollectionSlotCategoriesInBook "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                String bookName = st.nextToken();
                sendSystemMessageTestingOnly(self, "getAllCollectionSlotCategoriesInBook(bookName=" + bookName + ") returns:");
                String[] categoryNames = getAllCollectionSlotCategoriesInBook(bookName);
                if ((categoryNames == null) || (categoryNames.length < 1))
                {
                    sendSystemMessageTestingOnly(self, "no entries");
                }
                else 
                {
                    for (String categoryName : categoryNames) {
                        sendSystemMessageTestingOnly(self, categoryName);
                    }
                }
            }
        }
        else if (strText.equals("getAllCollectionSlotCategories"))
        {
            sendSystemMessageTestingOnly(self, "getAllCollectionSlotCategories() returns:");
            String[] categoryNames = getAllCollectionSlotCategories();
            if ((categoryNames == null) || (categoryNames.length < 1))
            {
                sendSystemMessageTestingOnly(self, "no entries");
            }
            else 
            {
                for (String categoryName : categoryNames) {
                    sendSystemMessageTestingOnly(self, categoryName);
                }
            }
        }
        else if (strText.startsWith("transferCollections "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id source = utils.stringToObjId(st.nextToken());
                obj_id target = utils.stringToObjId(st.nextToken());
                obj_id playerObjectSource = getPlayerObject(source);
                if (isIdValid(playerObjectSource))
                {
                    obj_id playerObjectTarget = getPlayerObject(target);
                    if (isIdValid(playerObjectTarget))
                    {
                        byte[] collections = getByteStreamFromAutoVariable(playerObjectSource, "collections");
                        if (collections != null && collections.length > 0)
                        {
                            setAutoVariableFromByteStream(playerObjectTarget, "collections", collections);
                            sendSystemMessageTestingOnly(self, "transferCollections(source=" + source + ", target=" + target + ")");
                        }
                        else 
                        {
                            sendSystemMessageTestingOnly(self, "transferCollections(source=" + source + ", target=" + target + ") source doesn't have any collections");
                        }
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "transferCollections(source=" + source + ", target=" + target + ") target has invalid PlayerObject");
                    }
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "transferCollections(source=" + source + ", target=" + target + ") source has invalid PlayerObject");
                }
            }
        }
        else if (strText.startsWith("makeOnLeave "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "makeOnLeave(target=" + target + ")");
                pvpMakeOnLeave(target);
            }
        }
        else if (strText.startsWith("modifyShipShield "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 7)
            {
                String command = st.nextToken();
                obj_id shipOid = utils.stringToObjId(st.nextToken());
                float frontCurrent = utils.stringToFloat(st.nextToken());
                float frontMax = utils.stringToFloat(st.nextToken());
                float backCurrent = utils.stringToFloat(st.nextToken());
                float backMax = utils.stringToFloat(st.nextToken());
                float rechargeRate = utils.stringToFloat(st.nextToken());
                sendSystemMessageTestingOnly(self, "changing shield for ship " + shipOid + " (front=" + frontCurrent + "/" + frontMax + " back=" + backCurrent + "/" + backMax + " rechargeRate=" + rechargeRate + ")");
                setShipShieldHitpointsFrontCurrent(shipOid, frontCurrent);
                setShipShieldHitpointsFrontMaximum(shipOid, frontMax);
                setShipShieldHitpointsBackCurrent(shipOid, backCurrent);
                setShipShieldHitpointsBackMaximum(shipOid, backMax);
                setShipShieldRechargeRate(shipOid, rechargeRate);
            }
        }
        else if (strText.startsWith("setResidenceHouseId "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                obj_id value = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "setResidenceHouseId for " + object + " to " + value);
                setHouseId(object, value);
            }
        }
        else if (strText.startsWith("getResidenceHouseId "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "getResidenceHouseId for " + object + " returned " + getHouseId(object));
            }
        }
        else if (strText.equals("progressBar"))
        {
            int result = progressBar(self);
            sendSystemMessageTestingOnly(self, "progress bar SUI page " + result + " shown");
        }
        else if (strText.startsWith("setProgressBarText "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                int pageId = utils.stringToInt(st.nextToken());
                String newText = st.nextToken();
                setSUIProperty(pageId, "comp.pText.text", PROP_TEXT, newText);
                flushSUIPage(pageId);
                sendSystemMessageTestingOnly(self, "setting progress bar SUI page " + pageId + " text to " + newText);
            }
        }
        else if (strText.startsWith("createCountdownTimerBar "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 6)
            {
                String command = st.nextToken();
                String type = st.nextToken();
                String sid_table = st.nextToken();
                String sid_text = st.nextToken();
                string_id sid = new string_id(sid_table, sid_text);
                int currentTime = utils.stringToInt(st.nextToken());
                int totalTime = utils.stringToInt(st.nextToken());
                int pid = sui.countdownTimerSUI(self, self, type, sid, currentTime, totalTime, "CountdownTimerBarCallback");
                sendSystemMessageTestingOnly(self, "sui.countdownTimerSUI(...," + type + ", " + utils.packStringId(sid) + ", " + currentTime + ", " + totalTime + ", ...) returned pid " + pid);
            }
        }
        else if (strText.startsWith("updateCountdownTimerBar "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 6)
            {
                String command = st.nextToken();
                int pageId = utils.stringToInt(st.nextToken());
                String sid_table = st.nextToken();
                String sid_text = st.nextToken();
                string_id sid = new string_id(sid_table, sid_text);
                int currentTime = utils.stringToInt(st.nextToken());
                int totalTime = utils.stringToInt(st.nextToken());
                boolean result = sui.updateCountdownTimerSUI(pageId, sid, currentTime, totalTime);
                sendSystemMessageTestingOnly(self, "sui.updateCountdownTimerSUI(" + pageId + ", " + utils.packStringId(sid) + ", " + currentTime + ", " + totalTime + ") returned result " + result);
            }
        }
        else if (strText.startsWith("factionMessagePP "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 4)
            {
                String command = st.nextToken();
                float radius = utils.stringToFloat(st.nextToken());
                int notifyImperial = utils.stringToInt(st.nextToken());
                int notifyRebel = utils.stringToInt(st.nextToken());
                int callTime = 45;
                prose_package pp = prose.getPackage(pet_lib.SID_SYS_CALL_PET_DELAY, callTime);
                sendSystemMessageTestingOnly(self, "calling sendFactionalSystemMessagePlanet() for radius " + radius);
                sendFactionalSystemMessagePlanet(pp, getLocation(self), radius, (notifyImperial != 0), (notifyRebel != 0));
            }
        }
        else if (strText.startsWith("factionMessage "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 6)
            {
                String command = st.nextToken();
                float radius = utils.stringToFloat(st.nextToken());
                String sid_table = st.nextToken();
                String sid_text = st.nextToken();
                string_id sid = new string_id(sid_table, sid_text);
                int notifyImperial = utils.stringToInt(st.nextToken());
                int notifyRebel = utils.stringToInt(st.nextToken());
                sendSystemMessageTestingOnly(self, "calling sendFactionalSystemMessagePlanet() for radius " + radius);
                sendFactionalSystemMessagePlanet(sid, null, radius, (notifyImperial != 0), (notifyRebel != 0));
            }
        }
        else if (strText.startsWith("setCondition "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                int condition = utils.stringToInt(st.nextToken());
                setCondition(object, condition);
                sendSystemMessageTestingOnly(self, "setting condition " + condition + " for object " + object);
            }
        }
        else if (strText.startsWith("clearCondition "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                int condition = utils.stringToInt(st.nextToken());
                clearCondition(object, condition);
                sendSystemMessageTestingOnly(self, "clearing condition " + condition + " for object " + object);
            }
        }
        else if (strText.startsWith("addMapLocation "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                boolean result = planetary_map.addMapLocation(object);
                sendSystemMessageTestingOnly(self, "addMapLocation for object " + object + " result " + result);
            }
        }
        else if (strText.startsWith("removeMapLocation "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                planetary_map.removeMapLocation(object);
                sendSystemMessageTestingOnly(self, "removeMapLocation for object " + object);
            }
        }
        else if (strText.startsWith("updateBankTerminalMapLocationName "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                String newName = st.nextToken();
                newName = "\\#FFFF22" + newName;
                location loc = getWorldLocation(object);
                boolean result = addPlanetaryMapLocationIgnoreLocationCountLimits(object, newName, (int)loc.x, (int)loc.z, "terminal", "terminal_bank", MLT_STATIC, 0);
                sendSystemMessageTestingOnly(self, "updateBankTerminalMapLocationName for object " + object + " newName " + newName + " result " + result);
            }
        }
        else if (strText.startsWith("updateMissionTerminalMapLocationName "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                String newName = st.nextToken();
                newName = "\\#FFFF22" + newName;
                location loc = getWorldLocation(object);
                boolean result = addPlanetaryMapLocationIgnoreLocationCountLimits(object, newName, (int)loc.x, (int)loc.z, "terminal", "terminal_mission", MLT_STATIC, 0);
                sendSystemMessageTestingOnly(self, "updateMissionTerminalMapLocationName for object " + object + " newName " + newName + " result " + result);
            }
        }
        else if (strText.startsWith("pvpSetAttackableOverride "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                int value = utils.stringToInt(st.nextToken());
                boolean bValue;
                if (value != 0)
                {
                    bValue = true;
                }
                else 
                {
                    bValue = false;
                }
                sendSystemMessageTestingOnly(self, "calling pvpSetAttackableOverride(" + target + ", " + bValue + ")");
                pvpSetAttackableOverride(target, bValue);
            }
        }
        else if (strText.startsWith("setMaxHitpoints "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                int value = utils.stringToInt(st.nextToken());
                sendSystemMessageTestingOnly(self, "calling setMaxHitpoints(" + target + ", " + value + ")");
                setMaxHitpoints(target, value);
            }
        }
        else if (strText.startsWith("areAllContentsLoaded "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "areAllContentsLoaded(" + target + ")=" + areAllContentsLoaded(target));
            }
        }
        else if (strText.startsWith("enterClientTicketPurchaseMode "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                String startPlanet = st.nextToken();
                enterClientTicketPurchaseMode(self, startPlanet, "Starport", false);
            }
        }
        else if (strText.startsWith("spawnNoAI "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                String creature = st.nextToken();
                create.object(creature, getLocation(self), false);
            }
        }
        else if (strText.equals("getTime"))
        {
            int gameTime = getGameTime();
            int[] convertedGameTime = player_structure.convertSecondsTime(gameTime);
            sendSystemMessageTestingOnly(self, "getGameTime() returns " + gameTime + " (" + (convertedGameTime[0] / 365) + "y:" + (convertedGameTime[0] % 365) + "d:" + convertedGameTime[1] + "h:" + convertedGameTime[2] + "m:" + convertedGameTime[3] + "s)");
            int calendarTime = getCalendarTime();
            int[] convertedCalendarTime = player_structure.convertSecondsTime(calendarTime);
            sendSystemMessageTestingOnly(self, "getCalendarTime() returns " + calendarTime + " (" + (convertedCalendarTime[0] / 365) + "y:" + (convertedCalendarTime[0] % 365) + "d:" + convertedCalendarTime[1] + "h:" + convertedCalendarTime[2] + "m:" + convertedCalendarTime[3] + "s)");
            sendSystemMessageTestingOnly(self, "getCalendarTimeStringGMT(" + calendarTime + ") returns " + getCalendarTimeStringGMT(calendarTime));
            sendSystemMessageTestingOnly(self, "getCalendarTimeStringLocal(" + calendarTime + ") returns " + getCalendarTimeStringLocal(calendarTime));
        }
        else if (strText.startsWith("getTime "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 7)
            {
                String command = st.nextToken();
                int year = utils.stringToInt(st.nextToken());
                int month = utils.stringToInt(st.nextToken());
                int day = utils.stringToInt(st.nextToken());
                int hour = utils.stringToInt(st.nextToken());
                int minute = utils.stringToInt(st.nextToken());
                int second = utils.stringToInt(st.nextToken());
                int time = getCalendarTime(year, month, day, hour, minute, second);
                if (time < 0)
                {
                    sendSystemMessageTestingOnly(self, month + "/" + day + "/" + year + " " + hour + ":" + minute + ":" + second + " is not a valid date/time (" + time + ")");
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, month + "/" + day + "/" + year + " " + hour + ":" + minute + ":" + second + " is " + time + ", " + getCalendarTimeStringLocal(time) + ", " + getCalendarTimeStringGMT(time));
                }
            }
        }
        else if (strText.startsWith("makeInvulnerable "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id obj = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "making " + obj + " invulnerable");
                setInvulnerable(obj, true);
            }
        }
        else if (strText.startsWith("makeVulnerable "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id obj = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "making " + obj + " vulnerable");
                setInvulnerable(obj, false);
            }
        }
        else if (strText.startsWith("hourlyAlarm "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                int minute = utils.stringToInt(st.nextToken());
                int second = utils.stringToInt(st.nextToken());
                int now = getGameTime();
                dictionary d = new dictionary();
                d.put("gameTime", now);
                int timeUntil = createHourlyAlarmClock(self, "huyHourlyAlarmClock", d, minute, second);
                if (timeUntil <= -1)
                {
                    sendSystemMessageTestingOnly(self, "createHourlyAlarmClock(minute=" + minute + ", second=" + second + ") now=" + now + " returns " + timeUntil);
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "createHourlyAlarmClock(minute=" + minute + ", second=" + second + ") now=" + now + " returns " + timeUntil + " (" + (timeUntil / (60 * 60 * 24)) + "d:" + ((timeUntil % (60 * 60 * 24)) / (60 * 60)) + "h:" + ((timeUntil % (60 * 60)) / 60) + "m:" + (timeUntil % 60) + "s)");
                }
            }
        }
        else if (strText.startsWith("dailyAlarm "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 4)
            {
                String command = st.nextToken();
                int hour = utils.stringToInt(st.nextToken());
                int minute = utils.stringToInt(st.nextToken());
                int second = utils.stringToInt(st.nextToken());
                int now = getGameTime();
                dictionary d = new dictionary();
                d.put("gameTime", now);
                int timeUntil = createDailyAlarmClock(self, "huyDailyAlarmClock", d, hour, minute, second);
                if (timeUntil <= -1)
                {
                    sendSystemMessageTestingOnly(self, "createDailyAlarmClock(hour=" + hour + ", minute=" + minute + ", second=" + second + ") now=" + now + " returns " + timeUntil);
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "createDailyAlarmClock(hour=" + hour + ", minute=" + minute + ", second=" + second + ") now=" + now + " returns " + timeUntil + " (" + (timeUntil / (60 * 60 * 24)) + "d:" + ((timeUntil % (60 * 60 * 24)) / (60 * 60)) + "h:" + ((timeUntil % (60 * 60)) / 60) + "m:" + (timeUntil % 60) + "s)");
                }
            }
        }
        else if (strText.startsWith("weeklyAlarm "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 5)
            {
                String command = st.nextToken();
                String dayOfWeek = st.nextToken();
                int hour = utils.stringToInt(st.nextToken());
                int minute = utils.stringToInt(st.nextToken());
                int second = utils.stringToInt(st.nextToken());
                int intDayOfWeek = -1;
                switch (dayOfWeek) {
                    case "sun":
                        intDayOfWeek = DAY_OF_WEEK_SUN;
                        break;
                    case "mon":
                        intDayOfWeek = DAY_OF_WEEK_MON;
                        break;
                    case "tue":
                        intDayOfWeek = DAY_OF_WEEK_TUE;
                        break;
                    case "wed":
                        intDayOfWeek = DAY_OF_WEEK_WED;
                        break;
                    case "thu":
                        intDayOfWeek = DAY_OF_WEEK_THU;
                        break;
                    case "fri":
                        intDayOfWeek = DAY_OF_WEEK_FRI;
                        break;
                    case "sat":
                        intDayOfWeek = DAY_OF_WEEK_SAT;
                        break;
                }
                if ((intDayOfWeek < DAY_OF_WEEK_SUN) || (intDayOfWeek > DAY_OF_WEEK_SAT))
                {
                    sendSystemMessageTestingOnly(self, "weeklyAlarm invalid day of week " + intDayOfWeek);
                }
                else 
                {
                    int now = getGameTime();
                    dictionary d = new dictionary();
                    d.put("gameTime", now);
                    int timeUntil = createWeeklyAlarmClock(self, "huyWeeklyAlarmClock", d, intDayOfWeek, hour, minute, second);
                    if (timeUntil <= -1)
                    {
                        sendSystemMessageTestingOnly(self, "createWeeklyAlarmClock(dow=" + intDayOfWeek + ", hour=" + hour + ", minute=" + minute + ", second=" + second + ") now=" + now + " returns " + timeUntil);
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "createWeeklyAlarmClock(dow=" + intDayOfWeek + ", hour=" + hour + ", minute=" + minute + ", second=" + second + ") now=" + now + " returns " + timeUntil + " (" + (timeUntil / (60 * 60 * 24)) + "d:" + ((timeUntil % (60 * 60 * 24)) / (60 * 60)) + "h:" + ((timeUntil % (60 * 60)) / 60) + "m:" + (timeUntil % 60) + "s)");
                    }
                }
            }
        }
        else if (strText.startsWith("monthlyAlarm "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 5)
            {
                String command = st.nextToken();
                int dayOfMonth = utils.stringToInt(st.nextToken());
                int hour = utils.stringToInt(st.nextToken());
                int minute = utils.stringToInt(st.nextToken());
                int second = utils.stringToInt(st.nextToken());
                int now = getGameTime();
                dictionary d = new dictionary();
                d.put("gameTime", now);
                int timeUntil = createMonthlyAlarmClock(self, "huyMonthlyAlarmClock", d, dayOfMonth, hour, minute, second);
                if (timeUntil <= -1)
                {
                    sendSystemMessageTestingOnly(self, "createMonthlyAlarmClock(dom=" + dayOfMonth + ", hour=" + hour + ", minute=" + minute + ", second=" + second + ") now=" + now + " returns " + timeUntil);
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "createMonthlyAlarmClock(dom=" + dayOfMonth + ", hour=" + hour + ", minute=" + minute + ", second=" + second + ") now=" + now + " returns " + timeUntil + " (" + (timeUntil / (60 * 60 * 24)) + "d:" + ((timeUntil % (60 * 60 * 24)) / (60 * 60)) + "h:" + ((timeUntil % (60 * 60)) / 60) + "m:" + (timeUntil % 60) + "s)");
                }
            }
        }
        else if (strText.startsWith("yearlyAlarm "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 6)
            {
                String command = st.nextToken();
                int month = utils.stringToInt(st.nextToken());
                int dayOfMonth = utils.stringToInt(st.nextToken());
                int hour = utils.stringToInt(st.nextToken());
                int minute = utils.stringToInt(st.nextToken());
                int second = utils.stringToInt(st.nextToken());
                int now = getGameTime();
                dictionary d = new dictionary();
                d.put("gameTime", now);
                int timeUntil = createYearlyAlarmClock(self, "huyYearlyAlarmClock", d, month, dayOfMonth, hour, minute, second);
                if (timeUntil <= -1)
                {
                    sendSystemMessageTestingOnly(self, "createYearlyAlarmClock(month=" + month + ", dom=" + dayOfMonth + ", hour=" + hour + ", minute=" + minute + ", second=" + second + ") now=" + now + " returns " + timeUntil);
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "createYearlyAlarmClock(month=" + month + ", dom=" + dayOfMonth + ", hour=" + hour + ", minute=" + minute + ", second=" + second + ") now=" + now + " returns " + timeUntil + " (" + (timeUntil / (60 * 60 * 24)) + "d:" + ((timeUntil % (60 * 60 * 24)) / (60 * 60)) + "h:" + ((timeUntil % (60 * 60)) / 60) + "m:" + (timeUntil % 60) + "s)");
                }
            }
        }
        else if (strText.startsWith("secondsUntilHourly "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                int minute = utils.stringToInt(st.nextToken());
                int second = utils.stringToInt(st.nextToken());
                int now = getCalendarTime();
                int secondsUntil = secondsUntilNextHourlyTime(minute, second);
                int then = now + secondsUntil;
                sendSystemMessageTestingOnly(self, "now is " + getCalendarTimeStringLocal(now) + ", " + getCalendarTimeStringGMT(now));
                sendSystemMessageTestingOnly(self, secondsUntil + " seconds until " + getCalendarTimeStringLocal(then) + ", " + getCalendarTimeStringGMT(then));
            }
        }
        else if (strText.startsWith("secondsUntilDaily "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 4)
            {
                String command = st.nextToken();
                int hour = utils.stringToInt(st.nextToken());
                int minute = utils.stringToInt(st.nextToken());
                int second = utils.stringToInt(st.nextToken());
                int now = getCalendarTime();
                int secondsUntil = secondsUntilNextDailyTime(hour, minute, second);
                int then = now + secondsUntil;
                sendSystemMessageTestingOnly(self, "now is " + getCalendarTimeStringLocal(now) + ", " + getCalendarTimeStringGMT(now));
                sendSystemMessageTestingOnly(self, secondsUntil + " seconds until " + getCalendarTimeStringLocal(then) + ", " + getCalendarTimeStringGMT(then));
            }
        }
        else if (strText.startsWith("secondsUntilWeekly "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 5)
            {
                String command = st.nextToken();
                int dayOfWeek = utils.stringToInt(st.nextToken());
                int hour = utils.stringToInt(st.nextToken());
                int minute = utils.stringToInt(st.nextToken());
                int second = utils.stringToInt(st.nextToken());
                int now = getCalendarTime();
                int secondsUntil = secondsUntilNextWeeklyTime(dayOfWeek, hour, minute, second);
                int then = now + secondsUntil;
                sendSystemMessageTestingOnly(self, "now is " + getCalendarTimeStringLocal(now) + ", " + getCalendarTimeStringGMT(now));
                sendSystemMessageTestingOnly(self, secondsUntil + " seconds until " + getCalendarTimeStringLocal(then) + ", " + getCalendarTimeStringGMT(then));
            }
        }
        else if (strText.startsWith("secondsUntilMonthly "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 5)
            {
                String command = st.nextToken();
                int dayOfMonth = utils.stringToInt(st.nextToken());
                int hour = utils.stringToInt(st.nextToken());
                int minute = utils.stringToInt(st.nextToken());
                int second = utils.stringToInt(st.nextToken());
                int now = getCalendarTime();
                int secondsUntil = secondsUntilNextMonthlyTime(dayOfMonth, hour, minute, second);
                int then = now + secondsUntil;
                sendSystemMessageTestingOnly(self, "now is " + getCalendarTimeStringLocal(now) + ", " + getCalendarTimeStringGMT(now));
                sendSystemMessageTestingOnly(self, secondsUntil + " seconds until " + getCalendarTimeStringLocal(then) + ", " + getCalendarTimeStringGMT(then));
            }
        }
        else if (strText.startsWith("secondsUntilYearly "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 6)
            {
                String command = st.nextToken();
                int month = utils.stringToInt(st.nextToken());
                int dayOfMonth = utils.stringToInt(st.nextToken());
                int hour = utils.stringToInt(st.nextToken());
                int minute = utils.stringToInt(st.nextToken());
                int second = utils.stringToInt(st.nextToken());
                int now = getCalendarTime();
                int secondsUntil = secondsUntilNextYearlyTime(month, dayOfMonth, hour, minute, second);
                int then = now + secondsUntil;
                sendSystemMessageTestingOnly(self, "now is " + getCalendarTimeStringLocal(now) + ", " + getCalendarTimeStringGMT(now));
                sendSystemMessageTestingOnly(self, secondsUntil + " seconds until " + getCalendarTimeStringLocal(then) + ", " + getCalendarTimeStringGMT(then));
            }
        }
        else if (strText.startsWith("createRegion "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                String regionName = st.nextToken();
                int regionSize = utils.stringToInt(st.nextToken());
                sendSystemMessageTestingOnly(self, "createRegion " + regionName + " " + regionSize + " meters");
                createCircleRegion(getWorldLocation(self), regionSize, regionName, regions.PVP_REGION_TYPE_NORMAL, regions.BUILD_FALSE, regions.MUNI_TRUE, regions.GEO_CITY, 0, 0, regions.SPAWN_FALSE, regions.MISSION_NONE, false, true);
            }
        }
        else if (strText.startsWith("getRegionsByPlanet "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                String planetName = st.nextToken();
                region[] regions = getRegions(planetName);
                if ((regions != null) && (regions.length > 0))
                {
                    String strOutput = "";
                    for (region region : regions) {
                        strOutput += "region (";
                        strOutput += region;
                        strOutput += ",";
                        strOutput += planetName;
                        strOutput += ",";
                        strOutput += region.getName();
                        strOutput += ",notify=";
                        strOutput += isNotifyRegion(region);
                        strOutput += ")\r\n";
                    }
                    saveTextOnClient(self, planetName + "_regions.txt", strOutput);
                    sendSystemMessageTestingOnly(self, regions.length + " regions saved in " + planetName + "_regions.txt");
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "no regions returned for planet " + planetName);
                }
            }
        }
        else if (strText.startsWith("deleteRegion "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                String planetName = st.nextToken();
                String regionName = st.nextToken();
                region region = getRegion(planetName, regionName);
                if (region != null)
                {
                    sendSystemMessageTestingOnly(self, "deleteRegion(" + planetName + ", " + regionName + ") returned " + deleteRegion(region));
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "no region returned for planet " + planetName + ", region " + regionName);
                }
            }
        }
        else if (strText.startsWith("setBountyValue "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                int bountyValue = utils.stringToInt(st.nextToken());
                setJediBountyValue(target, bountyValue);
            }
        }
        else if (strText.startsWith("requestJediBounty "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                obj_id hunter = utils.stringToObjId(st.nextToken());
                requestJediBounty(target, hunter, "huyRequestJediBountySuccess", "huyRequestJediBountyFailure");
            }
        }
        else if (strText.startsWith("removeJediBounty "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                obj_id hunter = utils.stringToObjId(st.nextToken());
                removeJediBounty(target, hunter);
            }
        }
        else if (strText.startsWith("removeAllJediBounties "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                removeAllJediBounties(target);
            }
        }
        else if (strText.startsWith("listenToMessage "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id broadcasterOid = utils.stringToObjId(st.nextToken());
                String messageName = st.nextToken();
                sendSystemMessageTestingOnly(self, self + " listen to " + broadcasterOid + " for message " + messageName);
                listenToMessage(broadcasterOid, messageName);
            }
        }
        else if (strText.startsWith("stopListeningToMessage "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id broadcasterOid = utils.stringToObjId(st.nextToken());
                String messageName = st.nextToken();
                sendSystemMessageTestingOnly(self, self + " stop listen to " + broadcasterOid + " for message " + messageName);
                stopListeningToMessage(broadcasterOid, messageName);
            }
        }
        else if (strText.startsWith("getListeners "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                String messageName = st.nextToken();
                sendSystemMessageTestingOnly(self, "get listeners for " + self + " for message " + messageName);
                obj_id[] listeners = getMessageListeners(messageName);
                if (listeners != null && listeners.length > 0)
                {
                    for (obj_id listener : listeners) {
                        sendSystemMessageTestingOnly(self, "" + listener);
                    }
                }
            }
        }
        else if (strText.startsWith("guildWarCoolDown "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                String dest = st.nextToken();
                obj_id destOid = utils.stringToObjId(dest);
                pvpSetGuildWarCoolDownPeriodEnemyFlag(destOid);
            }
        }
        else if (strText.startsWith("getGuildId "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                String dest = st.nextToken();
                obj_id destOid = utils.stringToObjId(dest);
                int intGuildId = getGuildId(destOid);
                if (intGuildId != 0)
                {
                    sendSystemMessageTestingOnly(self, destOid + " is in guild " + intGuildId + " (" + guildGetName(intGuildId) + "," + guildGetAbbrev(intGuildId) + ")");
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, destOid + " is in guild " + intGuildId);
                }
            }
        }
        else if (strText.startsWith("guildGetEnemiesAB "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                String guildId = st.nextToken();
                int intGuildId = utils.stringToInt(guildId);
                int[] enemies_A_to_B = guildGetEnemies(intGuildId);
                if (enemies_A_to_B == null || enemies_A_to_B.length <= 0)
                {
                    sendSystemMessageTestingOnly(self, "guild " + intGuildId + " has not declared war on any other guild");
                }
                else 
                {
                    for (int i1 : enemies_A_to_B) {
                        sendSystemMessageTestingOnly(self, "guild " + intGuildId + " has declared war on guild " + i1 + " (" + guildGetName(i1) + "," + guildGetAbbrev(i1) + ")");
                    }
                }
            }
        }
        else if (strText.startsWith("guildGetEnemiesBA "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                String guildId = st.nextToken();
                int intGuildId = utils.stringToInt(guildId);
                int[] enemies_B_to_A = getGuildsAtWarWith(intGuildId);
                if (enemies_B_to_A == null || enemies_B_to_A.length <= 0)
                {
                    sendSystemMessageTestingOnly(self, "no other guild has declared war on guild " + intGuildId);
                }
                else 
                {
                    for (int i1 : enemies_B_to_A) {
                        sendSystemMessageTestingOnly(self, "guild " + i1 + " (" + guildGetName(i1) + "," + guildGetAbbrev(i1) + ") has declared war on guild " + intGuildId);
                    }
                }
            }
        }
        else if (strText.startsWith("addRebelFactionStanding "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                float factionPoints = utils.stringToFloat(st.nextToken());
                sendSystemMessageTestingOnly(self, "setting rebel faction points for " + self + " to " + factionPoints);
                factions.addFactionStanding(self, factions.FACTION_REBEL, factionPoints);
            }
        }
        else if (strText.equals("unequipFactionEquipmentCheckObjvar"))
        {
            sendSystemMessageTestingOnly(self, "unequipping faction equipments (check objvar) for " + self);
            factions.unequipFactionEquipment(self, true);
        }
        else if (strText.equals("unequipFactionEquipmentNoCheckObjvar"))
        {
            sendSystemMessageTestingOnly(self, "unequipping faction equipments (no check objvar) for " + self);
            factions.unequipFactionEquipment(self, false);
        }
        else if (strText.equals("tblair_bug"))
        {
            Vector objJedis = new Vector();
            objJedis.setSize(0);
            Vector boolOnline = new Vector();
            boolOnline.setSize(0);
            objJedis = utils.addElement(objJedis, utils.stringToObjId("1"));
            boolOnline = utils.addElement(boolOnline, true);
            objJedis = utils.addElement(objJedis, utils.stringToObjId("2"));
            boolOnline = utils.addElement(boolOnline, false);
            objJedis = utils.addElement(objJedis, utils.stringToObjId("3"));
            boolOnline = utils.addElement(boolOnline, true);
            objJedis = utils.addElement(objJedis, utils.stringToObjId("4"));
            boolOnline = utils.addElement(boolOnline, false);
            objJedis = utils.addElement(objJedis, utils.stringToObjId("5"));
            boolOnline = utils.addElement(boolOnline, true);
            objJedis = utils.addElement(objJedis, utils.stringToObjId("6"));
            boolOnline = utils.addElement(boolOnline, false);
            objJedis = utils.addElement(objJedis, utils.stringToObjId("7"));
            boolOnline = utils.addElement(boolOnline, true);
            objJedis = utils.addElement(objJedis, utils.stringToObjId("8"));
            boolOnline = utils.addElement(boolOnline, false);
            objJedis = utils.addElement(objJedis, utils.stringToObjId("9"));
            boolOnline = utils.addElement(boolOnline, true);
            objJedis = utils.addElement(objJedis, utils.stringToObjId("10"));
            boolOnline = utils.addElement(boolOnline, false);
            for (int iHuy = 0; iHuy < objJedis.size(); ++iHuy)
            {
                if ((Boolean) boolOnline.get(iHuy))
                {
                    LOG("***TBLAIR***", "jedi " + ((obj_id)objJedis.get(iHuy)) + " is online");
                }
                else 
                {
                    LOG("***TBLAIR***", "jedi " + ((obj_id)objJedis.get(iHuy)) + " is offline");
                }
            }
            LOG("***TBLAIR***", "starting test loop");
            int jHuy = 0;
            for (jHuy = 1; jHuy <= 100000; ++jHuy)
            {
                int intRoll = -1;
                Vector jediList = new Vector();
                Vector jediIdx = new Vector();
                for (int i = 0; i < objJedis.size(); i++)
                {
                    jediIdx = utils.addElement(jediIdx, i);
                }
                jediList = utils.concatArrays(jediList, objJedis);
                while (jediList.size() > 0)
                {
                    intRoll = rand(0, jediList.size() - 1);
                    if (!isIdValid(((obj_id)jediList.get(intRoll))) || !(Boolean) boolOnline.get((Integer) (jediIdx.get(intRoll))))
                    {
                        jediList = utils.removeElementAt(jediList, intRoll);
                        jediIdx = utils.removeElementAt(jediIdx, intRoll);
                    }
                    else 
                    {
                        intRoll = (Integer) jediIdx.get(intRoll);
                        obj_id objTarget = ((obj_id)objJedis.get(intRoll));
                        boolean online = (Boolean) boolOnline.get(intRoll);
                        if (!online)
                        {
                            LOG("***TBLAIR***", "WRONG MATCH!!!! " + objTarget);
                        }
                        break;
                    }
                }
            }
            LOG("***TBLAIR***", "done with tblair_bug jHuy=" + jHuy);
        }
        else if (strText.startsWith("createVendorMarket "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                if (isIdValid(object))
                {
                    if (exists(object))
                    {
                        sendSystemMessageTestingOnly(self, "calling createVendorMarket() for vendor " + object);
                        createVendorMarket(self, object, 0);
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "object " + object + " does not exist");
                    }
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "invalid id for object " + object);
                }
            }
        }
        else if (strText.startsWith("setState "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                int state = utils.stringToInt(st.nextToken());
                int on = utils.stringToInt(st.nextToken());
                sendSystemMessageTestingOnly(self, "calling setState() for " + self + " state " + state + " on " + on);
                setState(self, state, ((on != 0) ? true : false));
            }
        }
        else if (strText.startsWith("isAFK "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                if (isIdValid(object))
                {
                    if (exists(object))
                    {
                        sendSystemMessageTestingOnly(self, "isAFK object " + object + " is " + isAwayFromKeyBoard(object));
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "object " + object + " does not exist");
                    }
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "invalid id for object " + object);
                }
            }
        }
        else if (strText.startsWith("getVolume "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                if (isIdValid(object))
                {
                    if (exists(object))
                    {
                        sendSystemMessageTestingOnly(self, "getVolume for object " + object + " is " + getVolume(object));
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "object " + object + " does not exist");
                    }
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "invalid id for object " + object);
                }
            }
        }
        else if (strText.startsWith("getNumItemsIn "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                if (isIdValid(object))
                {
                    if (exists(object))
                    {
                        sendSystemMessageTestingOnly(self, "getNumItemsIn for object " + object + " is " + getNumItemsIn(object));
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "object " + object + " does not exist");
                    }
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "invalid id for object " + object);
                }
            }
        }
        else if (strText.startsWith("putInOverloaded "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                if (isIdValid(object))
                {
                    if (exists(object))
                    {
                        sendSystemMessageTestingOnly(self, "calling putInOverloaded() for object " + object);
                        obj_id inv = getObjectInSlot(self, "inventory");
                        putInOverloaded(object, inv);
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "object " + object + " does not exist");
                    }
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "invalid id for object " + object);
                }
            }
        }
        else if (strText.startsWith("isInWorld "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                if (isIdValid(object))
                {
                    if (exists(object))
                    {
                        sendSystemMessageTestingOnly(self, "isInWorld for object " + object + " is " + isInWorld(object));
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "object " + object + " does not exist");
                    }
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "invalid id for object " + object);
                }
            }
        }
        else if (strText.startsWith("getTravelPointName "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                if (isIdValid(object))
                {
                    if (exists(object))
                    {
                        sendSystemMessageTestingOnly(self, "getCurrentSceneName is " + getCurrentSceneName() + " getTravelPointName for object " + object + " is " + travel.getTravelPointName(object) + " getArrivalLocation for object " + object + " is " + travel.getArrivalLocation(object) + " getTravelCost for object " + object + " is " + travel.getTravelCost(object));
                        String planet = getCurrentSceneName();
                        String travel_point = travel.getTravelPointName(object);
                        location arrival_loc = travel.getArrivalLocation(object);
                        int travel_cost = travel.getTravelCost(object);
                        if (travel_point == null || travel_cost == -1)
                        {
                            sendSystemMessageTestingOnly(self, "not calling calling initializeStarport()");
                        }
                        else 
                        {
                            sendSystemMessageTestingOnly(self, "calling initializeStarport()");
                            initializeStarport(self, object, travel_point, travel_cost, true);
                        }
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "object " + object + " does not exist");
                    }
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "invalid id for object " + object);
                }
            }
        }
        else if (strText.equals("getCityAtLocation"))
        {
            location loc = getWorldLocation(self);
            sendSystemMessageTestingOnly(self, "I am currently at " + loc);
            sendSystemMessageTestingOnly(self, "City I am currently in " + getCityAtLocation(loc, 0));
        }
        else if (strText.startsWith("useTicketTerminal "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                if (isIdValid(object))
                {
                    if (exists(object))
                    {
                        String planet = getCurrentSceneName();
                        obj_id starport = travel.getStarportFromTerminal(object);
                        String travel_point = travel.getTravelPointName(starport);
                        if (player_structure.isCivic(starport))
                        {
                            int city_id = getCityAtLocation(getLocation(starport), 0);
                            if (city.isCityBanned(self, city_id))
                            {
                                sendSystemMessageTestingOnly(self, "Can't buy ticket");
                                return SCRIPT_CONTINUE;
                            }
                        }
                        String config = getConfigSetting("GameServer", "disableTravelSystem");
                        if (config != null)
                        {
                            if (config.equals("on"))
                            {
                                sendSystemMessageTestingOnly(self, "Travel disabled");
                                return SCRIPT_CONTINUE;
                            }
                        }
                        sendSystemMessageTestingOnly(self, "OK to buy ticket");
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "object " + object + " does not exist");
                    }
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "invalid id for object " + object);
                }
            }
        }
        else if (strText.startsWith("getGroupInfo "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                obj_id groupObject = getGroupObject(object);
                if (isIdValid(groupObject))
                {
                    sendSystemMessageTestingOnly(self, "group " + groupObject + " has " + getPCGroupSize(groupObject) + "/" + getGroupSize(groupObject) + " player controlled member(s)");
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "invalid group id for object " + object);
                }
            }
        }
        else if (strText.startsWith("NMGetPlayerName "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "getPlayerName(" + object + ") is " + getPlayerName(object));
            }
        }
        else if (strText.startsWith("NMGetPlayerFullName "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "getPlayerFullName(" + object + ") is " + getPlayerFullName(object));
            }
        }
        else if (strText.startsWith("messageToAtSameTime "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                int count = utils.stringToInt(st.nextToken());
                int delay = utils.stringToInt(st.nextToken());
                sendSystemMessageTestingOnly(self, "messageToAtSameTime count=" + count + " delay=" + delay);
                dictionary d = new dictionary();
                for (int i = 1; i <= count; ++i)
                {
                    d.put("count", i);
                    messageTo(self, "huyTestMessageTo3", d, delay, false);
                }
            }
        }
        else if (strText.startsWith("destroyObject "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                destroyObject(object);
            }
        }
        else if (strText.equals("getServerSpawnLimit"))
        {
            sendSystemMessageTestingOnly(self, "serverSpawnLimit is " + getServerSpawnLimit());
        }
        else if (strText.startsWith("getOneJedi "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                dictionary result = requestJedi(object);
                if (result != null)
                {
                    if (result.size() > 0)
                    {
                        sendSystemMessageTestingOnly(self, result.getString("name") + "(" + result.getObjId("id") + ") " + result.getInt("spentJediSkillPoints"));
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "No jedi " + object);
                    }
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "Error getting jedi " + object);
                }
            }
        }
        else if (strText.equals("getAllJedi"))
        {
            dictionary result = requestJedi(IGNORE_JEDI_STAT, IGNORE_JEDI_STAT, 1, 1000, IGNORE_JEDI_STAT, IGNORE_JEDI_STAT, IGNORE_JEDI_STAT);
            if (result != null)
            {
                obj_id[] id = result.getObjIdArray("id");
                String[] name = result.getStringArray("name");
                int[] spentJediSkillPoints = result.getIntArray("spentJediSkillPoints");
                if (id != null && id.length > 0)
                {
                    for (int i = 0; i < id.length; ++i)
                    {
                        sendSystemMessageTestingOnly(self, name[i] + "(" + id[i] + ") " + spentJediSkillPoints[i]);
                    }
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "No jedi");
                }
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "Error getting jedi");
            }
        }
        else if (strText.equals("getNumAI"))
        {
            sendSystemMessageTestingOnly(self, "getNumAI=" + getNumAI());
        }
        else if (strText.equals("calculateWeaponRepair"))
        {
            float base_complexity = 10.0f;
            int assembly_mod = getSkillStatMod(self, "general_assembly");
            float complexity = base_complexity * (1.0f - (0.15f * assembly_mod / 100.0f));
            sendSystemMessageTestingOnly(self, "assembly_mod=" + assembly_mod + " complexity=" + complexity);
            int elite_mod = getSkillStatMod(self, "weapon_assembly");
            if (elite_mod > 0)
            {
                complexity -= base_complexity * (0.35f * elite_mod / 100.0f);
            }
            sendSystemMessageTestingOnly(self, "elite_mod=" + elite_mod + " complexity=" + complexity);
        }
        else if (strText.startsWith("getEntitlementInfo "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id object = utils.stringToObjId(st.nextToken());
                dictionary timeData = getAccountTimeData(object);
                if (timeData == null)
                {
                    sendSystemMessageTestingOnly(self, "couldn't get entitlement info for " + object);
                }
                else 
                {
                    int totalTime = timeData.getInt("total_subscription_time");
                    int entitledTime = timeData.getInt("total_entitled_time");
                    int lastLoginTime = timeData.getInt("last_login_time");
                    int entitledLoginTime = timeData.getInt("entitled_login_time");
                    sendSystemMessageTestingOnly(self, "entitlement info for " + object + " is total: " + entitledTime + "/" + totalTime + ",  since last login: " + entitledLoginTime + "/" + lastLoginTime);
                }
            }
        }
        else if (strText.equals("createFsTheater"))
        {
            sendSystemMessageTestingOnly(self, "Creating FS Theater");
            createTheater("datatables/theater/fs_quest_combat3/fs_quest_combat3.iff", getLocation(self), "", TLT_flatten);
        }
        else if (strText.equals("setJediVisibility"))
        {
            sendSystemMessageTestingOnly(self, "Setting Jedi Visibility to 9000");
            setJediVisibility(self, 9000);
        }
        else if (strText.equals("clearJediVisibility"))
        {
            sendSystemMessageTestingOnly(self, "Setting Jedi Visibility to 0");
            setJediVisibility(self, 0);
        }
        else if (strText.equals("cwdm_doit"))
        {
            LOG("***HUY***", "OnHearSpeech() initiating cluster wide data test");
            int requestId = getClusterWideData("dungeon", "Corellian Corvette - Instance 1", true, self);
            LOG("***HUY***", "getClusterWideData() to lock data for initial registration returned request Id (" + requestId + ")");
        }
        else if (strText.equals("cwdm_ticket_tatooine"))
        {
            space_dungeon.createTicket(self, "tatooine", "huy_terminal", "test_dungeon");
        }
        else if (strText.equals("cwdm_ticket_lok"))
        {
            space_dungeon.createTicket(self, "lok", "huy_terminal", "test_dungeon");
        }
        else if (strText.equals("cwdm_ticket_corvette_neutral"))
        {
            space_dungeon.createTicket(self, "lok", "corvette_neutral_terminal", "corvette_neutral");
        }
        else if (strText.equals("cwdm_ticket_corvette_imperial"))
        {
            space_dungeon.createTicket(self, "lok", "corvette_imperial_terminal", "corvette_imperial");
        }
        else if (strText.equals("cwdm_ticket_corvette_rebel"))
        {
            space_dungeon.createTicket(self, "lok", "corvette_rebel_terminal", "corvette_rebel");
        }
        else if (strText.equals("cwdm_reset_dungeon"))
        {
            obj_id dungeon = getTopMostContainer(self);
            if (isIdValid(dungeon))
            {
                LOG("***HUY***", "OnHearSpeech() ending dungeon session for dungeon (" + dungeon + ")");
                space_dungeon.endDungeonSession(dungeon);
            }
            else 
            {
                LOG("***HUY***", "OnHearSpeech() could not find top most container for ending dungeon session");
            }
        }
        else if (strText.equals("start_messageto_test"))
        {
            int messageNumber = 1;
            dictionary d = new dictionary();
            d.put("messageNumber", messageNumber);
            messageTo(self, "huyTestMessageTo", d, 120, true);
            LOG("***HUY***", "start_messageto_test with messageNumber=" + messageNumber);
        }
        else if (strText.equals("start_messageto_test2"))
        {
            int messageNumber = 1;
            dictionary d = new dictionary();
            d.put("messageNumber", messageNumber);
            messageTo(self, "huyTestMessageTo2", d, 5, false);
            LOG("***HUY***", "start_messageto_test2 with messageNumber=" + messageNumber);
            messageNumber = 2;
            d.put("messageNumber", messageNumber);
            messageTo(self, "huyTestMessageTo2", d, 5, false);
            LOG("***HUY***", "start_messageto_test2 with messageNumber=" + messageNumber);
            messageNumber = 3;
            d.put("messageNumber", messageNumber);
            messageTo(self, "huyTestMessageTo2", d, 5, false);
            LOG("***HUY***", "start_messageto_test2 with messageNumber=" + messageNumber);
            messageNumber = 4;
            d.put("messageNumber", messageNumber);
            messageTo(self, "huyTestMessageTo2", d, 5, false);
            LOG("***HUY***", "start_messageto_test2 with messageNumber=" + messageNumber);
            messageNumber = 5;
            d.put("messageNumber", messageNumber);
            messageTo(self, "huyTestMessageTo2", d, 5, false);
            LOG("***HUY***", "start_messageto_test2 with messageNumber=" + messageNumber);
            messageNumber = 6;
            d.put("messageNumber", messageNumber);
            messageTo(self, "huyTestMessageTo2", d, 10, false);
            LOG("***HUY***", "start_messageto_test2 with messageNumber=" + messageNumber);
            messageNumber = 7;
            d.put("messageNumber", messageNumber);
            messageTo(self, "huyTestMessageTo2", d, 10, false);
            LOG("***HUY***", "start_messageto_test2 with messageNumber=" + messageNumber);
            messageNumber = 8;
            d.put("messageNumber", messageNumber);
            messageTo(self, "huyTestMessageTo2", d, 10, false);
            LOG("***HUY***", "start_messageto_test2 with messageNumber=" + messageNumber);
            messageNumber = 9;
            d.put("messageNumber", messageNumber);
            messageTo(self, "huyTestMessageTo2", d, 10, false);
            LOG("***HUY***", "start_messageto_test2 with messageNumber=" + messageNumber);
            messageNumber = 10;
            d.put("messageNumber", messageNumber);
            messageTo(self, "huyTestMessageTo2", d, 10, false);
            LOG("***HUY***", "start_messageto_test2 with messageNumber=" + messageNumber);
        }
        else if (strText.equals("start_messageto_test_remote"))
        {
            int messageNumber = 1;
            dictionary d = new dictionary();
            d.put("messageNumber", messageNumber);
            obj_id target = obj_id.getObjId(5461449);
            messageTo(target, "huyTestMessageTo", d, 300, true);
            LOG("***HUY***", "start_messageto_test_remote to " + target + " with messageNumber=" + messageNumber);
        }
        else if (strText.equals("testDictionaryPackUnpack"))
        {
            obj_id target = obj_id.getObjId(7600674);
            dictionary d = new dictionary();
            d.put("target", target);
            d.put("amt", 100);
            d.put("useCash", true);
            d.put("targetName", "Weaponsmith");
            d.put("actor", self);
            d.put("actorName", "vendortest");
            byte[] packed = d.pack();
            dictionary unpacked = dictionary.unpack(packed);
            LOG("***HUY***", "testDictionaryPackUnpack unpacked dictionary is " + unpacked);
        }
        else if (strText.equals("start_messageto_test_persisted"))
        {
            obj_id target = obj_id.getObjId(7600674);
            dictionary d = new dictionary();
            d.put("target", target);
            d.put("amt", 100);
            d.put("useCash", true);
            d.put("targetName", "Weaponsmith");
            d.put("actor", self);
            d.put("actorName", "vendortest");
            messageTo(target, "huyTestPersistedMessageTo", d, 1.0f, true);
        }
        else if (strText.equals("start_messageto_test_persisted2"))
        {
            obj_id target = obj_id.getObjId(7600674);
            dictionary d = new dictionary();
            d.put("amt", 100);
            messageTo(target, "huyTestPersistedMessageTo2", d, 1.0f, true);
        }
        else if (strText.equals("start_xp_test"))
        {
            obj_id target = self;
            messageTo(target, "huyTestXP", null, 5, false);
            LOG("***HUY***", "start_xp_test to " + target);
        }
        else if (strText.equals("hmmm"))
        {
            if (objSpeaker == self)
            {
                obj_id target = getLookAtTarget(self);
                if (target != null)
                {
                    deltadictionary dctScriptVars = target.getScriptVars();
                }
            }
        }
        else if (strText.equals("doJediTest"))
        {
            obj_id inv = getObjectInSlot(self, "inventory");
            if (inv == null)
            {
                LOG("***HUY***", "player " + self + " inventory is null.");
                return SCRIPT_CONTINUE;
            }
            int free_space = getVolumeFree(inv);
            if (free_space < 1)
            {
                LOG("***HUY***", "player " + self + " inventory is full.");
                return SCRIPT_CONTINUE;
            }
            obj_id ticket = createObject("object/weapon/melee/sword/crafted_saber/sword_lightsaber_one_handed_gen1.iff", inv, "");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnClusterWideDataResponse(obj_id self, String strManagerName, String strElementNameRegex, int requestID, String[] elementNameList, dictionary[] dictionaryList, int lockKey) throws InterruptedException
    {
        LOG("***HUY***", "OnClusterWideDataResponse() manager name (" + strManagerName + ") element name regex (" + strElementNameRegex + ") request id (" + requestID + ") match count (" + elementNameList.length + ") lock key (" + lockKey + ")");
        for (int i = 0; i < elementNameList.length; ++i)
        {
            LOG("***HUY***", "element " + (i + 1) + " is (" + elementNameList[i] + ")");
            LOG("***HUY***", "dictionary " + dictionaryList[i].toString());
        }
        if (requestID == 1)
        {
            dictionary dict = new dictionary();
            dict.put("bool true", true);
            dict.put("bool false", false);
            dict.put("float 77.77", Float.valueOf(77.77f));
            dict.put("objid", self);
            dict.put("int 7777", 7777);
            dict.put("string 77777", "77777");
            updateClusterWideData(strManagerName, "Corellian Corvette - Instance 1", dict, lockKey);
            replaceClusterWideData(strManagerName, "Corellian Corvette - Instance 1", dict, true, lockKey);
            replaceClusterWideData(strManagerName, "Corellian Corvette - Instance 1", dict, true, lockKey);
            int requestId = getClusterWideData(strManagerName, "Corellian Corvette - Instance 1", true, self);
            LOG("***HUY***", "getClusterWideData() to lock data for update returned request Id (" + requestId + ")");
            requestId = getClusterWideData(strManagerName, "Corellian Corvette - Instance 1", true, self);
            LOG("***HUY***", "getClusterWideData() to lock data for update returned request Id (" + requestId + ")");
            requestId = getClusterWideData(strManagerName, "Corellian Corvette - Instance 1", true, self);
            LOG("***HUY***", "getClusterWideData() to lock data for update returned request Id (" + requestId + ")");
        }
        else if (requestID == 2)
        {
            dictionary dict = new dictionary();
            dict.put("new value 1", "added value 1");
            updateClusterWideData(strManagerName, "Corellian Corvette - Instance 1", dict, lockKey);
            dictionary dict2 = new dictionary();
            dict2.put("new value 2", "added value 2");
            updateClusterWideData(strManagerName, "Corellian Corvette - Instance 1", dict2, lockKey);
            dictionary dict3 = new dictionary();
            dict3.put("new value 3", "added value 3");
            updateClusterWideData(strManagerName, "Corellian Corvette - Instance 1", dict3, lockKey);
        }
        else if (requestID == 3)
        {
            removeClusterWideData(strManagerName, "Corellian Corvette - Instance 1", 0);
            removeClusterWideData(strManagerName, "Corellian Corvette - Instance 1", lockKey);
        }
        return SCRIPT_CONTINUE;
    }
    public int huyTestMessageTo(obj_id self, dictionary params) throws InterruptedException
    {
        int messageNumber = params.getInt("messageNumber");
        LOG("***HUY***", "huyTestMessageTo processing message with messageNumber=" + messageNumber);
        if (self.getValue() != 5461449)
        {
            dictionary d = new dictionary();
            d.put("messageNumber", messageNumber + 1);
            messageTo(self, "huyTestMessageTo", d, 300, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int huyTestMessageTo2(obj_id self, dictionary params) throws InterruptedException
    {
        int messageNumber = params.getInt("messageNumber");
        LOG("***HUY***", "huyTestMessageTo2 processing message with messageNumber=" + messageNumber);
        return SCRIPT_CONTINUE;
    }
    public int huyTestMessageTo3(obj_id self, dictionary params) throws InterruptedException
    {
        int count = params.getInt("count");
        sendSystemMessageTestingOnly(self, "huyTestMessageTo3 count=" + count);
        return SCRIPT_CONTINUE;
    }
    public int huyTestXP(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("***HUY***", "in huyTestXP messageHandler");
        xp.grant(self, "combat_general", 10);
        return SCRIPT_CONTINUE;
    }
    public int huyTestPersistedMessageTo(obj_id self, dictionary params) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "huyTestPersistedMessageTo() params is " + params.toString());
        return SCRIPT_CONTINUE;
    }
    public int huyTestPersistedMessageTo2(obj_id self, dictionary params) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "huyTestPersistedMessageTo2() params is " + params.toString());
        return SCRIPT_CONTINUE;
    }
    public boolean initializeStarport(obj_id self, obj_id structure, String travel_point, int travel_cost, boolean civic) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "initializeStarport() in");
        if (structure == null || structure == obj_id.NULL_ID)
        {
            sendSystemMessageTestingOnly(self, "structure == null || structure == obj_id.NULL_ID");
            return false;
        }
        int num_items = dataTableGetNumRows(travel.STARPORT_DATATABLE);
        String template = getTemplateName(structure);
        int idx = travel.getStarportTableIndex(template);
        if (idx == -1)
        {
            sendSystemMessageTestingOnly(self, "idx == -1");
            return false;
        }
        String planet = getCurrentSceneName();
        dictionary row = dataTableGetRow(travel.STARPORT_DATATABLE, idx);
        float arrival_x = row.getFloat(travel.DATATABLE_COL_ARRIVAL_X);
        float arrival_y = row.getFloat(travel.DATATABLE_COL_ARRIVAL_Y);
        float arrival_z = row.getFloat(travel.DATATABLE_COL_ARRIVAL_Z);
        String arrival_cell = row.getString(travel.DATATABLE_COL_ARRIVAL_CELL);
        int ground_time = row.getInt(travel.DATATABLE_COL_GROUND_TIME);
        int air_time = row.getInt(travel.DATATABLE_COL_AIR_TIME);
        int is_shuttleport = row.getInt(travel.DATATABLE_COL_IS_SHUTTLEPORT);
        location arrival_loc;
        if (arrival_cell.equals("WORLD_DELTA"))
        {
            location s_loc = getLocation(structure);
            float s_yaw = getYaw(structure);
            if (s_yaw < 0.0f)
            {
                s_yaw = s_yaw + 360.0f;
            }
            int rotation = (int)(s_yaw + 1) / 90;
            float[] transform = travel.transformDeltaWorldYaw(structure, arrival_x, arrival_z);
            arrival_x = transform[0];
            arrival_z = transform[1];
            arrival_loc = new location(s_loc.x - arrival_x, s_loc.y - arrival_y, s_loc.z - arrival_z, planet, obj_id.NULL_ID);
        }
        else 
        {
            obj_id cell_id = getCellId(structure, arrival_cell);
            if (cell_id == null || cell_id == obj_id.NULL_ID)
            {
                sendSystemMessageTestingOnly(self, "cell_id == null || cell_id == obj_id.NULL_ID");
                return false;
            }
            arrival_loc = new location(arrival_x, arrival_y, arrival_z, planet, cell_id);
        }
        setObjVar(structure, travel.VAR_GROUND_TIME, ground_time);
        setObjVar(structure, travel.VAR_AIR_TIME, air_time);
        setObjVar(structure, travel.VAR_SHUTTLE_AVAILABLE, 1);
        setObjVar(structure, travel.VAR_SHUTTLE_TIMESTAMP, getGameTime());
        setObjVar(structure, travel.VAR_VERSION, travel.CURRENT_VERSION);
        if (is_shuttleport > 0)
        {
            setObjVar(structure, travel.VAR_IS_SHUTTLEPORT, is_shuttleport);
        }
        else if (hasObjVar(structure, travel.VAR_IS_SHUTTLEPORT))
        {
            removeObjVar(structure, travel.VAR_IS_SHUTTLEPORT);
        }
        if (travel.setStarportTravelPoint(structure, travel_point, arrival_loc, travel_cost, civic))
        {
            sendSystemMessageTestingOnly(self, "travel.setStarportTravelPoint() OK");
        }
        else 
        {
            sendSystemMessageTestingOnly(self, "travel.setStarportTravelPoint() FAILED");
        }
        if (hasObjVar(structure, travel.VAR_BASE_OBJECT))
        {
            travel.destroyBaseObjects(structure);
        }
        if (num_items > idx + 1)
        {
            float s_yaw = getYaw(structure);
            if (s_yaw < 0.0f)
            {
                s_yaw = s_yaw + 360.0f;
            }
            int rotation = (int)(s_yaw + 1) / 90;
            LOG("LOG_CHANNEL", "(" + structure + ")" + "rotation ->" + rotation);
            Vector object_list = new Vector();
            object_list.setSize(0);
            for (int i = idx + 1; i < num_items; i++)
            {
                String struct_temp = dataTableGetString(travel.STARPORT_DATATABLE, i, travel.DATATABLE_COL_STRUCTURE);
                if (struct_temp.length() > 0)
                {
                    break;
                }
                dictionary object_row = dataTableGetRow(travel.STARPORT_DATATABLE, i);
                String obj_template = object_row.getString(travel.DATATABLE_COL_OBJECT);
                float x = object_row.getFloat(travel.DATATABLE_COL_X);
                float y = object_row.getFloat(travel.DATATABLE_COL_Y);
                float z = object_row.getFloat(travel.DATATABLE_COL_Z);
                String cell = object_row.getString(travel.DATATABLE_COL_CELL);
                float heading = object_row.getFloat(travel.DATATABLE_COL_HEADING);
                int is_terminal = object_row.getInt(travel.DATATABLE_COL_IS_TERMINAL);
                int is_transport = object_row.getInt(travel.DATATABLE_COL_IS_TRANSPORT);
                int is_pilot = object_row.getInt(travel.DATATABLE_COL_IS_PILOT);
                location obj_loc;
                obj_id object;
                if (cell.equals("WORLD_DELTA"))
                {
                    float[] delta_trans = travel.transformDeltaWorldYaw(structure, x, z);
                    x = delta_trans[0];
                    z = delta_trans[1];
                    heading = heading + s_yaw;
                    if (heading > 360)
                    {
                        heading = heading - 360;
                    }
                    location s_loc = getLocation(structure);
                    obj_loc = new location(s_loc.x - x, s_loc.y - y, s_loc.z - z, planet, obj_id.NULL_ID);
                    LOG("LOG_CHANNEL", "obj_loc ->" + obj_loc);
                    object = createObject(obj_template, obj_loc);
                }
                else 
                {
                    obj_id cell_id = getCellId(structure, cell);
                    if (cell_id == null || cell_id == obj_id.NULL_ID)
                    {
                        LOG("LOG_CHANNEL", "Unable to find valid cell name for " + obj_template);
                        continue;
                    }
                    obj_loc = new location(x, y, z, planet, cell_id);
                    object = createObjectInCell(obj_template, structure, cell, obj_loc);
                    LOG("LOG_CHANNEL", "object ->" + object);
                }
                if (obj_loc == null)
                {
                    LOG("LOG_CHANNEL", "Unable to create " + obj_template);
                }
                else 
                {
                    if (heading != 0.0f)
                    {
                        
                    }
                    
                    {
                        setYaw(object, heading);
                        object_list = utils.addElement(object_list, object);
                    }
                    if (is_terminal == 1)
                    {
                        setObjVar(object, travel.VAR_STARPORT, structure);
                    }
                    if (is_transport == 1)
                    {
                        setObjVar(object, travel.VAR_STARPORT, structure);
                        attachScript(object, travel.SCRIPT_SHUTTLE);
                    }
                    if (is_pilot == 1)
                    {
                        setObjVar(object, travel.VAR_STARPORT, structure);
                        attachScript(object, travel.SCRIPT_SHUTTLE_PILOT);
                    }
                }
                if (object_list.size() > 0)
                {
                    setObjVar(structure, travel.VAR_BASE_OBJECT, object_list);
                }
            }
        }
        sendSystemMessageTestingOnly(self, "initializeStarport() out");
        return true;
    }
    public int huyRequestJediBountySuccess(obj_id self, dictionary params) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "huyRequestJediBountySuccess() params is " + params.toString());
        return SCRIPT_CONTINUE;
    }
    public int huyRequestJediBountyFailure(obj_id self, dictionary params) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "huyRequestJediBountyFailure() params is " + params.toString());
        return SCRIPT_CONTINUE;
    }
    public int huyTestSUIProgressBar(obj_id self, dictionary params) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "ProgressBar update");
        int startTime = params.getInt("startTime");
        int pid = params.getInt("pid");
        if (setSUIProperty(pid, "comp.pText.text", PROP_TEXT, (getGameTime() - startTime) + " seconds have elapsed"))
        {
            flushSUIPage(pid);
            messageTo(self, "huyTestSUIProgressBar", params, 5, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int progressBar(obj_id player) throws InterruptedException
    {
        int pid = createSUIPage("Script.ProgressBar", player, player, "ProgressBarCallback");
        setSUIProperty(pid, "comp.pText.text", PROP_TEXT, "0 seconds have elapsed");
        showSUIPage(pid);
        int now = getGameTime();
        dictionary d = new dictionary();
        d.put("startTime", now);
        d.put("pid", pid);
        messageTo(player, "huyTestSUIProgressBar", d, 5, false);
        return pid;
    }
    public int ProgressBarCallback(obj_id self, dictionary params) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "ProgressBarCallback");
        return SCRIPT_CONTINUE;
    }
    public int countdownTimerBar(obj_id player) throws InterruptedException
    {
        int pid = createSUIPage("Script.CountdownTimerBar", player, player, "CountdownTimerBarCallback");
        string_id testStringId = new string_id("error_message", "prose_over_max_entries");
        String prompt = utils.packStringId(testStringId);
        setSUIProperty(pid, "comp.text", PROP_TEXT, prompt);
        setSUIProperty(pid, "bg.caption.lbltitle", PROP_TEXT, prompt);
        setSUIProperty(pid, "this", "countdownTimerTimeValue", "0,60");
        showSUIPage(pid);
        return pid;
    }
    public int CountdownTimerBarCallback(obj_id self, dictionary params) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "CountdownTimerBarCallback");
        return SCRIPT_CONTINUE;
    }
    public int OnEnterRegion(obj_id self, String planetName, String regionName) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "OnEnterRegion planet=" + planetName + " region=" + regionName);
        LOG("***HUY_REGION***", self + " OnEnterRegion planet=" + planetName + " region=" + regionName);
        return SCRIPT_CONTINUE;
    }
    public int OnExitRegion(obj_id self, String planetName, String regionName) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "OnExitRegion planet=" + planetName + " region=" + regionName);
        LOG("***HUY_REGION***", self + " OnExitRegion planet=" + planetName + " region=" + regionName);
        return SCRIPT_CONTINUE;
    }
    public int OnCollectionSlotModified(obj_id self, String bookName, String pageName, String collectionName, String slotName, boolean isCounterTypeSlot, int previousValue, int currentValue, int maxSlotValue, boolean slotCompleted) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "OnCollectionSlotModified book=" + bookName + ", page=" + pageName + ", collection=" + collectionName + ", slot=" + slotName + ", isCounterTypeSlot=" + isCounterTypeSlot + ", previousValue=" + previousValue + ", currentValue=" + currentValue + ", maxSlotValue=" + maxSlotValue + ", slotCompleted=" + slotCompleted);
        return SCRIPT_CONTINUE;
    }
    public int OnCollectionServerFirst(obj_id self, String bookName, String pageName, String collectionName) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "OnCollectionServerFirst book=" + bookName + ", page=" + pageName + ", collection=" + collectionName);
        return SCRIPT_CONTINUE;
    }
    public int OnIncubatorCommitted(obj_id self, obj_id playerId, obj_id terminalId, obj_id slot1Id, obj_id slot2Id, obj_id slot3Id, obj_id slot4Id, int initialPointsSurvival, int initialPointsBeastialResilience, int initialPointsCunning, int initialPointsIntelligence, int initialPointsAggression, int initialPointsHuntersInstinct, int totalPointsSurvival, int totalPointsBeastialResilience, int totalPointsCunning, int totalPointsIntelligence, int totalPointsAggression, int totalPointsHuntersInstinct, int temperatureGauge, int nutrientGauge, int newCreatureColorIndex) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "OnIncubatorCommitted");
        return SCRIPT_CONTINUE;
    }
    public int OnIncubatorCancelled(obj_id self, obj_id playerId, obj_id terminalId) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "OnIncubatorCancelled");
        return SCRIPT_CONTINUE;
    }
    public int endDuelCommandNotification(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = params.getObjId("target");
        sendSystemMessageTestingOnly(self, "endDuelCommandNotification() I am=" + self + " target=" + target);
        return SCRIPT_CONTINUE;
    }
    public int OnPvpRankingChanged(obj_id self, int oldRank, int newRank) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "OnPvpRankingChanged oldRank=" + oldRank + " newRank=" + newRank);
        LOG("***HUY_ONPVPRANKINGCHANGED***", self + " OnPvpRankingChanged oldRank=" + oldRank + " newRank=" + newRank);
        return SCRIPT_CONTINUE;
    }
    public int huyHourlyAlarmClock(obj_id self, dictionary params) throws InterruptedException
    {
        int now = params.getInt("gameTime");
        sendSystemMessageTestingOnly(self, "huyHourlyAlarmClock now=" + now);
        return SCRIPT_CONTINUE;
    }
    public int huyDailyAlarmClock(obj_id self, dictionary params) throws InterruptedException
    {
        int now = params.getInt("gameTime");
        sendSystemMessageTestingOnly(self, "huyDailyAlarmClock now=" + now);
        return SCRIPT_CONTINUE;
    }
    public int huyWeeklyAlarmClock(obj_id self, dictionary params) throws InterruptedException
    {
        int now = params.getInt("gameTime");
        sendSystemMessageTestingOnly(self, "huyWeeklyAlarmClock now=" + now);
        return SCRIPT_CONTINUE;
    }
    public int huyMonthlyAlarmClock(obj_id self, dictionary params) throws InterruptedException
    {
        int now = params.getInt("gameTime");
        sendSystemMessageTestingOnly(self, "huyMonthlyAlarmClock now=" + now);
        return SCRIPT_CONTINUE;
    }
    public int huyYearlyAlarmClock(obj_id self, dictionary params) throws InterruptedException
    {
        int now = params.getInt("gameTime");
        sendSystemMessageTestingOnly(self, "huyYearlyAlarmClock now=" + now);
        return SCRIPT_CONTINUE;
    }
    public int testSuiHandler(obj_id self, dictionary params) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "buttonPressed=" + params.getString("buttonPressed"));
        return SCRIPT_CONTINUE;
    }
    public int OnUnloadedFromMemory(obj_id self) throws InterruptedException
    {
        LOG("***HUY_SSI***", "OnUnloadedFromMemory() " + self);
        return SCRIPT_CONTINUE;
    }
    public void testIntegerLongReferenceParam(int[] i, long[] l) throws InterruptedException
    {
        i[0] = i[0] + 1;
        l[0] = l[0] + 1L;
    }
}
