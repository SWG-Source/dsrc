package script.theme_park.poi;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.poi;
import script.ai.ai_combat;

public class base extends script.base_script
{
    public base()
    {
    }
    public static final int POI_INCOMPLETE = poi.POI_INCOMPLETE;
    public static final int POI_SUCCESS = poi.POI_SUCCESS;
    public static final int POI_FAIL = poi.POI_FAIL;
    public static final String POI_STRING_LIST = poi.POI_STRING_LIST;
    public static final String POI_STRING_LIST_INDEXNAME = poi.POI_STRING_LIST_INDEXNAME;
    public static final String POI_CREDIT_LIST = poi.POI_CREDIT_LIST;
    public static final String POI_DENY_LIST = poi.POI_DENY_LIST;
    public static final String POI_DESTROY_MESSAGE = poi.POI_DESTROY_MESSAGE;
    public static final String POI_DESTROY_MESSAGE_DELAY = poi.POI_DESTROY_MESSAGE_DELAY;
    public static final String POI_BASE_OBJECT = poi.POI_BASE_OBJECT;
    public static final String POI_NO_MESSAGE_ON_DESTROY = poi.POI_NO_MESSAGE_ON_DESTROY;
    public static final String POI_COMPLETED = poi.POI_COMPLETED;
    public static final String POI_DIFFICULTY = poi.POI_DIFFICULTY;
    public static final String POI_FACTION = poi.POI_FACTION;
    public static final String POI_FACTION_VALUE = poi.POI_FACTION_VALUE;
    public static final String POI_OBJECTIVE = poi.POI_OBJECTIVE;
    public static final String POI_OBJECT_SCRIPT = poi.POI_OBJECT_SCRIPT;
    public obj_id poiCreateObject(obj_id poiObject, String template, float x, float z) throws InterruptedException
    {
        return poi.createObject(poiObject, template, x, z);
    }
    public obj_id poiCreateObject(obj_id poiObject, String template, float x, float z, int level) throws InterruptedException
    {
        return poi.createObject(poiObject, template, x, z, level);
    }
    public obj_id poiCreateObject(String template, float x, float z) throws InterruptedException
    {
        return poi.createObject(template, x, z);
    }
    public obj_id poiCreateObject(String template, float x, float z, int level) throws InterruptedException
    {
        return poi.createObject(template, x, z, level);
    }
    public obj_id poiCreateObject(obj_id poiObject, String name, String template, float x, float z) throws InterruptedException
    {
        return poi.createObject(poiObject, name, template, x, z);
    }
    public obj_id poiCreateObject(obj_id poiObject, String name, String template, float x, float z, int level) throws InterruptedException
    {
        return poi.createObject(poiObject, name, template, x, z, level);
    }
    public obj_id poiCreateObject(obj_id poiObject, String strName, location locCenter) throws InterruptedException
    {
        return poi.createObject(poiObject, strName, locCenter);
    }
    public obj_id poiCreateObject(obj_id poiObject, String strName, location locCenter, int level) throws InterruptedException
    {
        return poi.createObject(poiObject, strName, locCenter, level);
    }
    public obj_id poiCreateObject(String name, String template, float x, float z) throws InterruptedException
    {
        return poi.createObject(name, template, x, z);
    }
    public obj_id poiCreateObject(String name, String template, float x, float z, int level) throws InterruptedException
    {
        return poi.createObject(name, template, x, z, level);
    }
    public obj_id poiCreateNpc(obj_id poiObject, String type, float x, float z) throws InterruptedException
    {
        return poi.createNpc(poiObject, type, x, z);
    }
    public obj_id poiCreateNpc(obj_id poiObject, String type, float x, float z, int level) throws InterruptedException
    {
        return poi.createNpc(poiObject, type, x, z, level);
    }
    public obj_id poiCreateNpc(String type, float x, float z) throws InterruptedException
    {
        return poi.createNpc(type, x, z);
    }
    public obj_id poiCreateNpc(String type, float x, float z, int level) throws InterruptedException
    {
        return poi.createNpc(type, x, z, level);
    }
    public obj_id poiCreateNpc(obj_id poiObject, String ident, String type, float x, float z) throws InterruptedException
    {
        return poi.createNpc(poiObject, ident, type, x, z);
    }
    public obj_id poiCreateNpc(obj_id poiObject, String ident, String type, float x, float z, int level) throws InterruptedException
    {
        return poi.createNpc(poiObject, ident, type, x, z, level);
    }
    public obj_id poiCreateNpc(String ident, String type, float x, float z) throws InterruptedException
    {
        return poi.createNpc(ident, type, x, z);
    }
    public obj_id poiCreateNpc(String ident, String type, float x, float z, int level) throws InterruptedException
    {
        return poi.createNpc(ident, type, x, z, level);
    }
    public obj_id poiFindObject(obj_id poiObject, String name) throws InterruptedException
    {
        return poi.findObject(poiObject, name);
    }
    public obj_id poiFindObject(String name) throws InterruptedException
    {
        return poi.findObject(name);
    }
    public String poiFindObjectName(obj_id poiObject) throws InterruptedException
    {
        return poi.findObjectName(poiObject);
    }
    public String poiFindObjectName() throws InterruptedException
    {
        return poi.findObjectName();
    }
    public void poiSetDestroyMessage(obj_id poiObject, String name, String messageHandlerName, int delay) throws InterruptedException
    {
        poi.setDestroyMessage(poiObject, name, messageHandlerName, delay);
    }
    public void poiSetDestroyMessage(obj_id poiObject, String name, String messageHandlerName) throws InterruptedException
    {
        poi.setDestroyMessage(poiObject, name, messageHandlerName);
    }
    public void poiSetDestroyMessage(String name, String messageHandlerName, int delay) throws InterruptedException
    {
        poi.setDestroyMessage(name, messageHandlerName, delay);
    }
    public void poiSetDestroyMessage(String name, String messageHandlerName) throws InterruptedException
    {
        poi.setDestroyMessage(name, messageHandlerName);
    }
    public void poiSetDestroyMessage(obj_id poiObject, String messageHandlerName, int delay) throws InterruptedException
    {
        poi.setDestroyMessage(poiObject, messageHandlerName, delay);
    }
    public void poiSetDestroyMessage(obj_id poiObject, String messageHandlerName) throws InterruptedException
    {
        poi.setDestroyMessage(poiObject, messageHandlerName);
    }
    public void poiSetDestroyMessage(String messageHandlerName, int delay) throws InterruptedException
    {
        poi.setDestroyMessage(messageHandlerName, delay);
    }
    public void poiSetDestroyMessage(String messageHandlerName) throws InterruptedException
    {
        poi.setDestroyMessage(messageHandlerName);
    }
    public void poiGrantCredit(obj_id poiObject, obj_id player) throws InterruptedException
    {
        poi.grantCredit(poiObject, player);
    }
    public void poiGrantCredit(obj_id player) throws InterruptedException
    {
        poi.grantCredit(player);
    }
    public void poiRemoveCredit(obj_id poiObject, obj_id player) throws InterruptedException
    {
        poi.removeCredit(poiObject, player);
    }
    public void poiRemoveCredit(obj_id player) throws InterruptedException
    {
        poi.removeCredit(player);
    }
    public void poiDenyCredit(obj_id poiObject, obj_id player) throws InterruptedException
    {
        poi.denyCredit(poiObject, player);
    }
    public void poiDenyCredit(obj_id player) throws InterruptedException
    {
        poi.denyCredit(player);
    }
    public boolean poiIsGrantedCredit(obj_id poiObject, obj_id player) throws InterruptedException
    {
        return poi.isGrantedCredit(poiObject, player);
    }
    public boolean poiIsGrantedCredit(obj_id player) throws InterruptedException
    {
        return poi.isGrantedCredit(player);
    }
    public boolean poiIsGrantedCredit(obj_id poiBaseObject, String objVarName) throws InterruptedException
    {
        return poi.isGrantedCredit(poiBaseObject, objVarName);
    }
    public boolean poiIsDeniedCredit(obj_id poiObject, obj_id player) throws InterruptedException
    {
        return poi.isDeniedCredit(poiObject, player);
    }
    public boolean poiIsDeniedCredit(obj_id player) throws InterruptedException
    {
        return poi.isDeniedCredit(player);
    }
    public boolean poiIsDeniedCredit(obj_id poiBaseObject, String objVarName) throws InterruptedException
    {
        return poi.isDeniedCredit(poiBaseObject, objVarName);
    }
    public void poiAddToStringList(obj_id element, String name) throws InterruptedException
    {
        poi.addToStringList(element, name);
    }
    public void poiAddToStringList(String name) throws InterruptedException
    {
        poi.addToStringList(name);
    }
    public void poiRemoveFromStringList(obj_id element) throws InterruptedException
    {
        poi.removeFromStringList(element);
    }
    public void poiRemoveFromStringList() throws InterruptedException
    {
        poi.removeFromStringList();
    }
    public void poiRemoveFromStringList(obj_id poiObject, String name) throws InterruptedException
    {
        poi.removeFromStringList(poiObject, name);
    }
    public void poiRemoveFromStringList(String name) throws InterruptedException
    {
        poi.removeFromStringList(name);
    }
    public void poiAddToMasterList(obj_id poiBaseObject, obj_id elementToAdd) throws InterruptedException
    {
        poi.addToMasterList(poiBaseObject, elementToAdd);
    }
    public void poiRemoveFromMasterList(obj_id poiBaseObject, obj_id elementToRemove) throws InterruptedException
    {
        poi.removeFromMasterList(poiBaseObject, elementToRemove);
    }
    public boolean poiIsInMasterList(obj_id poiBaseObject, obj_id element) throws InterruptedException
    {
        return poi.isInMasterList(poiBaseObject, element);
    }
    public obj_id poiGetBaseObject(obj_id poiObject) throws InterruptedException
    {
        return poi.getBaseObject(poiObject);
    }
    public obj_id poiGetBaseObject() throws InterruptedException
    {
        return poi.getBaseObject();
    }
    public void poiBaseObjectDestroyed(obj_id poiBaseObject) throws InterruptedException
    {
        poi.baseObjectDestroyed(poiBaseObject);
    }
    public void poiObjectDestroyed(obj_id element) throws InterruptedException
    {
        poi.objectDestroyed(element);
    }
    public String poiGetDifficulty(obj_id poiObject) throws InterruptedException
    {
        return poi.getDifficulty(poiObject);
    }
    public String poiGetDifficulty() throws InterruptedException
    {
        return poi.getDifficulty();
    }
    public int poiGetIntDifficulty() throws InterruptedException
    {
        return poi.getIntDifficulty();
    }
    public String poiGetFaction(obj_id poiObject) throws InterruptedException
    {
        return poi.getFaction(poiObject);
    }
    public String poiGetFaction() throws InterruptedException
    {
        return poi.getFaction();
    }
    public float poiGetFactionValue(obj_id poiObject) throws InterruptedException
    {
        return poi.getFactionValue(poiObject);
    }
    public float poiGetFactionValue() throws InterruptedException
    {
        return poi.getFactionValue();
    }
    public String poiGetObjective(obj_id poiObject) throws InterruptedException
    {
        return poi.getObjective(poiObject);
    }
    public String poiGetObjective() throws InterruptedException
    {
        return poi.getObjective();
    }
    public void poiSetFaction(obj_id poiObject, String faction) throws InterruptedException
    {
        poi.setFaction(poiObject, faction);
    }
    public void poiSetFaction(String faction) throws InterruptedException
    {
        poi.setFaction(faction);
    }
    public void poiSetFactionValue(obj_id poiObject, float factionValue) throws InterruptedException
    {
        poi.setFactionValue(poiObject, factionValue);
    }
    public void poiSetFactionValue(float factionValue) throws InterruptedException
    {
        poi.setFactionValue(factionValue);
    }
    public void poiComplete(obj_id poiObject, int status) throws InterruptedException
    {
        poi.complete(poiObject, status);
    }
    public void poiComplete(int status) throws InterruptedException
    {
        poi.complete(status);
    }
    public void poiComplete(obj_id poiObject) throws InterruptedException
    {
        poi.complete(poiObject);
    }
    public void poiComplete() throws InterruptedException
    {
        poi.complete();
    }
    public void poiAwardFactionStanding(obj_id poiBaseObject) throws InterruptedException
    {
        poi.awardFactionStanding(poiBaseObject);
    }
    public void poiSendMissionStatus(obj_id poiBaseObject, int status) throws InterruptedException
    {
        poi.sendMissionStatus(poiBaseObject, status);
    }
    public void poiBaseObjectRemovedFromWorld(obj_id poiBaseObject) throws InterruptedException
    {
        poi.baseObjectRemovedFromWorld(poiBaseObject);
    }
    public boolean poiIsCompleted(obj_id poiObject) throws InterruptedException
    {
        return poi.isComplete(poiObject);
    }
    public boolean poiIsCompleted() throws InterruptedException
    {
        return poi.isComplete();
    }
}
