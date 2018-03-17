package script.library;

import script.obj_id;
import script.prose_package;
import script.string_id;

public class prose extends script.base_script
{
    public prose()
    {
    }
    public static prose_package setStringId(prose_package pp, string_id strSpam) throws InterruptedException
    {
        pp.stringId = strSpam;
        return pp;
    }
    public static prose_package setTT(prose_package pp, String strTarget) throws InterruptedException
    {
        pp.target.set(strTarget);
        return pp;
    }
    public static prose_package setTT(prose_package pp, obj_id objTarget) throws InterruptedException
    {
        pp.target.set(objTarget);
        return pp;
    }
    public static prose_package setTT(prose_package pp, string_id strTarget) throws InterruptedException
    {
        pp.target.set(strTarget);
        return pp;
    }
    public static prose_package setTU(prose_package pp, String strTarget) throws InterruptedException
    {
        pp.actor.set(strTarget);
        return pp;
    }
    public static prose_package setTU(prose_package pp, obj_id objTarget) throws InterruptedException
    {
        pp.actor.set(objTarget);
        return pp;
    }
    public static prose_package setTU(prose_package pp, string_id strTarget) throws InterruptedException
    {
        pp.actor.set(strTarget);
        return pp;
    }
    public static prose_package setTO(prose_package pp, String strTarget) throws InterruptedException
    {
        pp.other.set(strTarget);
        return pp;
    }
    public static prose_package setTO(prose_package pp, obj_id objTarget) throws InterruptedException
    {
        pp.other.set(objTarget);
        return pp;
    }
    public static prose_package setTO(prose_package pp, string_id strTarget) throws InterruptedException
    {
        pp.other.set(strTarget);
        return pp;
    }
    public static prose_package setDI(prose_package pp, int intTest) throws InterruptedException
    {
        pp.digitInteger = intTest;
        return pp;
    }
    public static prose_package setDF(prose_package pp, float fltTest) throws InterruptedException
    {
        pp.digitFloat = fltTest;
        return pp;
    }
    public static prose_package getPackage(string_id sid) throws InterruptedException
    {
        prose_package pp = new prose_package();
        pp.stringId = sid;
        return pp;
    }
    public static prose_package getPackage(string_id sid, obj_id actor, String actorString, string_id actorStringId, obj_id target, String targetString, string_id targetStringId, obj_id other, String otherString, string_id otherStringId, int di, float df) throws InterruptedException
    {
        if (actorString != null && !actorString.equals(""))
        {
            actorStringId = null;
        }
        if (targetString != null && !targetString.equals(""))
        {
            targetStringId = null;
        }
        prose_package pp = new prose_package();
        pp.stringId = sid;
        pp.actor.set(actor, actorStringId, actorString);
        pp.target.set(target, targetStringId, targetString);
        pp.other.set(other, otherStringId, otherString);
        pp.digitInteger = di;
        pp.digitFloat = df;
        return pp;
    }
    public static prose_package getPackage(string_id sid, obj_id actor, obj_id target, obj_id other, int di) throws InterruptedException
    {
        prose_package pp = new prose_package();
        pp.stringId = sid;
        if (actor != null)
        {
            pp.actor.set(actor);
        }
        if (target != null)
        {
            pp.target.set(target);
        }
        if (other != null)
        {
            pp.other.set(other);
        }
        pp.digitInteger = di;
        return pp;
    }
    public static prose_package getPackage(string_id sid, obj_id actor, obj_id target, obj_id other) throws InterruptedException
    {
        return getPackage(sid, actor, target, other, 0);
    }
    public static prose_package getPackage(string_id sid, obj_id actor, obj_id target, string_id other, int di) throws InterruptedException
    {
        prose_package pp = new prose_package();
        pp.stringId = sid;
        if (actor != null)
        {
            pp.actor.set(actor);
        }
        if (target != null)
        {
            pp.target.set(target);
        }
        pp.other.set(other);
        pp.digitInteger = di;
        return pp;
    }
    public static prose_package getPackage(string_id sid, obj_id actor, obj_id target, string_id other) throws InterruptedException
    {
        return getPackage(sid, actor, target, other, 0);
    }
    public static prose_package getPackage(string_id sid, obj_id target, string_id other) throws InterruptedException
    {
        return getPackage(sid, null, target, other, 0);
    }
    public static prose_package getPackage(string_id sid, obj_id actor, obj_id target, String other, int di) throws InterruptedException
    {
        prose_package pp = new prose_package();
        pp.stringId = sid;
        if (actor != null)
        {
            pp.actor.set(actor);
        }
        if (target != null)
        {
            pp.target.set(target);
        }
        pp.other.set(other);
        pp.digitInteger = di;
        return pp;
    }
    public static prose_package getPackage(string_id sid, obj_id actor, obj_id target, String other) throws InterruptedException
    {
        return getPackage(sid, actor, target, other, 0);
    }
    public static prose_package getPackage(string_id sid, obj_id target, String other) throws InterruptedException
    {
        return getPackage(sid, null, target, other, 0);
    }
    public static prose_package getPackage(string_id sid, obj_id actor, obj_id target, int di) throws InterruptedException
    {
        prose_package pp = new prose_package();
        pp.stringId = sid;
        if (actor != null)
        {
            pp.actor.set(actor);
        }
        if (target != null)
        {
            pp.target.set(target);
        }
        pp.digitInteger = di;
        return pp;
    }
    public static prose_package getPackage(string_id sid, obj_id actor, obj_id target) throws InterruptedException
    {
        return getPackage(sid, actor, target, 0);
    }
    public static prose_package getPackage(string_id sid, obj_id target) throws InterruptedException
    {
        return getPackage(sid, null, target, 0);
    }
    public static prose_package getPackage(string_id sid, obj_id target, int di) throws InterruptedException
    {
        return getPackage(sid, null, target, di);
    }
    public static prose_package getPackage(string_id sid, string_id other, int di, float df) throws InterruptedException
    {
        prose_package pp = new prose_package();
        pp.stringId = sid;
        pp.other.set(other);
        pp.digitInteger = di;
        pp.digitFloat = df;
        return pp;
    }
    public static prose_package getPackage(string_id sid, string_id other) throws InterruptedException
    {
        return getPackage(sid, other, 0, 0f);
    }
    public static prose_package getPackage(string_id sid, string_id other, int di) throws InterruptedException
    {
        return getPackage(sid, other, di, 0f);
    }
    public static prose_package getPackage(string_id sid, string_id other, float df) throws InterruptedException
    {
        return getPackage(sid, other, 0, df);
    }
    public static prose_package getPackage(string_id sid, string_id target, string_id other, int di) throws InterruptedException
    {
        prose_package pp = new prose_package();
        pp.stringId = sid;
        pp.target.set(target);
        pp.other.set(other);
        pp.digitInteger = di;
        return pp;
    }
    public static prose_package getPackage(string_id sid, string_id target, string_id other) throws InterruptedException
    {
        return getPackage(sid, target, other, 0);
    }
    public static prose_package getPackage(string_id sid, string_id target, String other, int di) throws InterruptedException
    {
        prose_package pp = new prose_package();
        pp.stringId = sid;
        pp.target.set(target);
        pp.other.set(other);
        pp.digitInteger = di;
        return pp;
    }
    public static prose_package getPackage(string_id sid, string_id target, String other) throws InterruptedException
    {
        return getPackage(sid, target, other, 0);
    }
    public static prose_package getPackage(string_id sid, String other, int di, float df) throws InterruptedException
    {
        prose_package pp = new prose_package();
        pp.stringId = sid;
        pp.other.set(other);
        pp.digitInteger = di;
        pp.digitFloat = df;
        return pp;
    }
    public static prose_package getPackage(string_id sid, String other) throws InterruptedException
    {
        return getPackage(sid, other, 0, 0f);
    }
    public static prose_package getPackage(string_id sid, String other, int di) throws InterruptedException
    {
        return getPackage(sid, other, di, 0f);
    }
    public static prose_package getPackage(string_id sid, String other, float df) throws InterruptedException
    {
        return getPackage(sid, other, 0, df);
    }
    public static prose_package getPackage(string_id sid, int di, float df) throws InterruptedException
    {
        prose_package pp = new prose_package();
        pp.stringId = sid;
        pp.digitInteger = di;
        pp.digitFloat = df;
        return pp;
    }
    public static prose_package getPackage(string_id sid, int di) throws InterruptedException
    {
        return getPackage(sid, di, 0f);
    }
    public static prose_package getPackage(string_id sid, float df) throws InterruptedException
    {
        return getPackage(sid, 0, df);
    }
    public static prose_package getPackage(string_id sid, String target, String other) throws InterruptedException
    {
        prose_package pp = new prose_package();
        pp.stringId = sid;
        pp.target.set(target);
        pp.other.set(other);
        return pp;
    }
    public static prose_package getPackage(string_id sid, String target, String other, int di) throws InterruptedException
    {
        prose_package pp = new prose_package();
        pp.stringId = sid;
        pp.target.set(target);
        pp.other.set(other);
        pp.digitInteger = di;
        return pp;
    }
    public static prose_package getPackage(string_id sid, String target, String other, String actor) throws InterruptedException
    {
        prose_package pp = new prose_package();
        pp.stringId = sid;
        pp.target.set(target);
        pp.other.set(other);
        pp.actor.set(actor);
        return pp;
    }
}
