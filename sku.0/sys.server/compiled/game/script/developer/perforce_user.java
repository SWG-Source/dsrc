package script.developer;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.developer.perforce;
import script.library.chat;
import script.library.utils;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;

public class perforce_user extends script.base_script
{
    public perforce_user()
    {
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        java.util.StringTokenizer st = new java.util.StringTokenizer(text);
        Vector args = new Vector();
        while (st.hasMoreTokens())
        {
            args.add(st.nextToken());
        }
        if (!(((String)args.get(0)).equals("p4")))
        {
            return SCRIPT_CONTINUE;
        }
        if (args.size() > 1 && ((String)args.get(1)).equals("login"))
        {
            perforce.setupPerforce();
        }
        else if (args.size() > 1 && ((String)args.get(1)).equals("edit"))
        {
            if (args.size() > 2)
            {
                String arg2 = ((String)args.get(2));
                perforce.openExistingFileForExclusiveEdit(arg2);
            }
        }
        else if (args.size() > 1 && ((String)args.get(1)).equals("opened"))
        {
            String[] results = perforce.opened();
            if (results != null)
            {
                String openedFiles = new String();
                int iter = 0;
                for (iter = 0; iter < results.length; ++iter)
                {
                    openedFiles += results[iter] + "\n";
                }
                int page = createSUIPage("/Script.messageBox", self, self);
                setSUIProperty(page, "Prompt.lblPrompt", "LocalText", openedFiles);
                setSUIProperty(page, "bg.caption.lblTitle", "Text", "Perforce Opened");
                setSUIProperty(page, "Prompt.lblPrompt", "Editable", "false");
                setSUIProperty(page, "Prompt.lblPrompt", "GetsInput", "true");
                setSUIProperty(page, "btnCancel", "Visible", "false");
                setSUIProperty(page, "btnRevert", "Visible", "false");
                showSUIPage(page);
                flushSUIPage(page);
            }
            else 
            {
                chat.chat(self, "there was an error retrieving the file listing");
            }
        }
        else if (args.size() > 1 && ((String)args.get(1)).equals("submit"))
        {
            String param = null;
            int changeList = 0;
            if (args.size() > 2)
            {
                param = ((String)args.get(2));
            }
            if (param != null)
            {
                changeList = utils.stringToInt(param);
            }
            String defaultChangeText = perforce.change(changeList);
            int page = createSUIPage("/Script.textEditor", self, self);
            setSUIProperty(page, "pageText.text", "LocalText", defaultChangeText);
            setSUIProperty(page, "bg.caption.text", "Text", "Perforce Submit");
            subscribeToSUIEvent(page, sui_event_type.SET_onButton, "btnOk", "onPerforceSubmitTextEditorBtnOk");
            subscribeToSUIPropertyForEvent(page, sui_event_type.SET_onButton, "btnOk", "pageText.text", "LocalText");
            showSUIPage(page);
            flushSUIPage(page);
        }
        else if (args.size() > 1 && ((String)args.get(1)).equals("diff"))
        {
            String param = null;
            int changeList = 0;
            if (args.size() > 2)
            {
                param = ((String)args.get(2));
            }
            String diff = perforce.diff(param);
            String[] diffLines = split(diff, '\n');
            String diffedText = new String("\\#FFFFFF");
            int iter = 0;
            for (iter = 0; iter < diffLines.length; ++iter)
            {
                if (diffLines[iter].startsWith("-"))
                {
                    diffedText += "\\#FF0000" + diffLines[iter] + "\n" + "\\#FFFFFF";
                }
                else if (diffLines[iter].startsWith("+"))
                {
                    diffedText += "\\#00FF00" + diffLines[iter] + "\n" + "\\#FFFFFF";
                }
                else 
                {
                    diffedText += diffLines[iter] + "\n" + "\\#FFFFFF";
                }
            }
            int page = createSUIPage("/Script.textEditor", self, self);
            setSUIProperty(page, "pageText.text", "LocalText", diffedText);
            setSUIProperty(page, "bg.caption.text", "Text", "Perforce Diff");
            setSUIProperty(page, "pageText.text", "Editable", "false");
            setSUIProperty(page, "pageText.text", "GetsInput", "true");
            setSUIProperty(page, "outputPage", "Visible", "false");
            setSUIProperty(page, "btnOk", "Visible", "false");
            setSUIProperty(page, "btnCancel", "Visible", "false");
            showSUIPage(page);
            flushSUIPage(page);
        }
        else if (args.size() < 2)
        {
            int page = createSUIPage("/Script.perforce", self, self);
            HashMap changes = new HashMap();
            String[] openedFiles = perforce.opened();
            String outputText = "p4 opened\n";
            int iter = 0;
            for (iter = 0; iter < openedFiles.length; ++iter)
            {
                String[] elems = split(openedFiles[iter], ' ');
                if (elems.length > 0)
                {
                    String fileSpec = elems[0];
                    if (elems.length > 2)
                    {
                        String action = elems[2];
                        if (elems.length > 3)
                        {
                            String change = elems[3];
                            if (change.equals("change") && elems.length > 4)
                            {
                                change = elems[4];
                            }
                            if (!changes.containsKey(change))
                            {
                                Vector files = new Vector();
                                files.add(fileSpec);
                                changes.put(change, files);
                            }
                            else 
                            {
                                Vector files = (Vector)changes.get(change);
                                files.add(fileSpec);
                            }
                        }
                    }
                }
                outputText += openedFiles[iter] + "\n";
            }
            clearSUIDataSourceContainer(page, "changes.dataTree");
            Set keySet = changes.keySet();
            Iterator changesIter = keySet.iterator();
            while (changesIter.hasNext())
            {
                String key = (String)changesIter.next();
                Vector files = (Vector)changes.get(key);
                addSUIDataSourceContainer(page, "changes.dataTree", key);
                setSUIProperty(page, "changes.dataTree." + key, "text", key);
                for (iter = 0; iter < files.size(); ++iter)
                {
                    String f = ((String)files.get(iter));
                    addSUIDataSourceContainer(page, "changes.dataTree." + key, f);
                    setSUIProperty(page, "changes.dataTree." + key + "." + f, "text", f);
                }
            }
            setSUIProperty(page, "outputPage.text", "LocalText", outputText);
            showSUIPage(page);
            flushSUIPage(page);
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_OVERRIDE;
    }
    public int onPerforceSetupBtnOk(obj_id self, dictionary params) throws InterruptedException
    {
        String userId = params.getString("boxInputUserId.inputUserId.LocalText");
        String password = params.getString("boxInputPassword.inputPassword.LocalText");
        setObjVar(self, "P4USER", userId);
        setObjVar(self, "P4PASSWD", password);
        forceCloseSUIPage(params.getInt("pageId"));
        return SCRIPT_CONTINUE;
    }
    public int onPerforceSubmitTextEditorBtnOk(obj_id self, dictionary params) throws InterruptedException
    {
        int page = params.getInt("pageId");
        if (page < 0)
        {
            return SCRIPT_CONTINUE;
        }
        String submissionContents = params.getString("pageText.text.LocalText");
        if (submissionContents == null || (submissionContents.length() < 1))
        {
            return SCRIPT_CONTINUE;
        }
        Vector outputWindowText = new Vector();
        boolean submitSucceeded = perforce.submit(submissionContents, outputWindowText);
        String results = new String();
        if (submitSucceeded)
        {
            setSUIProperty(page, "pageText.text", "LocalText", "");
            setSUIProperty(page, "pageText.text", "Text", "");
            results += "\\##00FF00";
        }
        else 
        {
            results += "\\##FF0000";
        }
        int iter = 0;
        for (iter = 0; iter < outputWindowText.size(); ++iter)
        {
            results += ((String)outputWindowText.get(iter)) + "\n";
        }
        setSUIProperty(page, "outputPage.text", "Text", results);
        flushSUIPage(page);
        return SCRIPT_CONTINUE;
    }
}
