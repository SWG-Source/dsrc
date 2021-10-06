package script.developer;

import script.obj_id;
import script.system_process;
import script.transform;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility used for loading and writing interior layout files.
 *
 * To use, you must disable rendering of interior layouts in your client with:
 * [ClientGame]
 * useInteriorLayoutFiles = false
 *
 * You must also put all interior layout files in your server-side dsrc at
 * dsrc/sku.0/sys.client
 * You can do this by extracting from SIE the client/interiorlayout folder
 * directly to sys.client
 *
 * Once you are in-game after making these changes, attach this script to
 * your player, then you can use the following commands via spatial chat:
 *
 * "loadInteriorLayout" will create all the objects in the interior layout file
 * 		for the building you are currently standing in
 *
 * "clearInteriorLayout" will destroy all objects spawned by loadInteriorLayout.
 *
 * "saveInteriorLayout" will take all changes made to the tangibles spawned by
 * 		the loadInteriorLayout command as well as any new objects added to the
 * 		building and write them to the interior layout file in your dsrc.
 *
 * Tips:
 *  * if you're decorating a new building, make an empty .ilf file in the dsrc
 *  	and add it to the building shared template .tpf file so you can write to it.
 *
 *  * when you're done writing everything, you can run saveInteriorLayout, then do
 *  	clearInteriorLayout and then loadInteriorLayout to make sure all of your
 *  	changes happened correctly.
 *
 *  * remember that interior layouts are client-side objects only. if you are adding
 *  	something that a player will need to interact with, or something that the
 *  	server cares about (like a spawner), they don't go in interior layouts.
 *
 *  * if you need to exempt an object that would otherwise get added to the ILF, you
 *  	can add the ObjVar "no_interior_layout" to the object.
 *
 * @since SWG Source 3.1 - October 2021
 * @author Aconite
 */
public class interior_layout_utility extends script.base_script {

	public interior_layout_utility()
	{
	}

	public int OnSpeaking(obj_id self, String text) throws InterruptedException, IOException {

		if(!isGod(self))
		{
			return SCRIPT_CONTINUE;
		}

		if(text.equalsIgnoreCase("loadInteriorLayout"))
		{
			final obj_id top = getTopMostContainer(self);
			if(!isIdValid(top))
			{
				sendSystemMessageTestingOnly(self, "Error: You must be inside a building to use this command.");
				return SCRIPT_CONTINUE;
			}
			final String layoutFile = getInteriorLayoutFile(top);
			if(layoutFile == null)
			{
				sendSystemMessageTestingOnly(self, "Error: Could not find an ILF defined for this building's template or couldn't read this building's template file.");
				return SCRIPT_CONTINUE;
			}
			spawnAllInteriorLayoutObjectsAsTangibles(self, top, layoutFile);
			return SCRIPT_CONTINUE;
		}

		if(text.equalsIgnoreCase("clearInteriorLayout"))
		{
			final obj_id top = getTopMostContainer(self);
			if(!isIdValid(top))
			{
				sendSystemMessageTestingOnly(self, "Error: You must be inside a building to use this command.");
				return SCRIPT_CONTINUE;
			}
			final obj_id[] cells = getCellIds(top);
			for(obj_id cell : cells)
			{
				final obj_id[] contents = getContents(cell);
				for(obj_id object : contents)
				{
					if(hasObjVar(object, "interior_layout_utility"))
					{
						destroyObject(object);
					}
				}
			}
			sendSystemMessageTestingOnly(self, "Success. Finished removing all objects spawned with Interior Layout Utility.");
			return SCRIPT_CONTINUE;
		}

		if(text.equalsIgnoreCase("saveInteriorLayout"))
		{
			final obj_id top = getTopMostContainer(self);
			if(!isIdValid(top))
			{
				sendSystemMessageTestingOnly(self, "You must be inside a building to use this command.");
				return SCRIPT_CONTINUE;
			}
			final String layoutFile = getInteriorLayoutFile(top);
			if(layoutFile == null)
			{
				sendSystemMessageTestingOnly(self, "Error: Could not find an ILF defined for this building's template or couldn't read this building's template file.");
				return SCRIPT_CONTINUE;
			}

			final obj_id[] cells = getCellIds(top);
			final HashSet<obj_id> idsToAdd = new HashSet<>();
			for(obj_id cell : cells)
			{
				final obj_id[] contents = getContents(cell);
				for (obj_id object : contents)
				{
					final String template = getTemplateName(object);
					if(!isPlayer(object) && !isMob(object))
					{
						if(!hasObjVar(object, "no_interior_layout"))
						{
							if(isTangible(object))
							{
								if(!template.contains("spawnegg") &&
										!template.contains("ground_spawning") &&
										!template.contains("poi") &&
										!template.contains("buildout") &&
										!template.contains("content_infrastructure"))
								{
									idsToAdd.add(object);
								}
							}
							else if(template.contains("object/building") || template.contains("object/static"))
							{
								idsToAdd.add(object);
							}
						}
					}
				}
			}
			if(idsToAdd.size() > 0)
			{
				sendSystemMessageTestingOnly(self, "Starting ILF save process. Java Virtual Machine and Script Engine will freeze while this process is ongoing. Be PATIENT and wait for response...");
				saveInteriorLayoutFile(self, getInteriorLayoutFile(top), idsToAdd);
			}
		}
		return SCRIPT_CONTINUE;
	}

	private static String getInteriorLayoutFile(obj_id building)
	{
		final String template = getSharedObjectTemplateName(building);
		if(!template.contains("object/building"))
		{
			return null;
		}
		final String templateFile = "../../dsrc/sku.0/sys.shared/compiled/game/" + template.replace(".iff", ".tpf");
		try
		{
			File f = new File(templateFile);
			if(f.exists() && f.canRead())
			{
				List<String> lines = Files.readAllLines(Path.of(templateFile));
				for(String line : lines)
				{
					if(line.contains("interiorLayoutFileName"))
					{
						Pattern p = Pattern.compile("\"([^\"]*)\"");
						Matcher m = p.matcher(line);
						if(m.find())
						{
							return m.group(1);
						}
					}
				}
			}
			else
			{
				return null;
			}
		}
		catch(Exception e)
		{
			return null;
		}
		return null;
	}

	private static void saveInteriorLayoutFile(obj_id self, String interiorLayoutFile, HashSet<obj_id> ids)
	{
		final String ilfPath = "../../dsrc/sku.0/sys.client/" + interiorLayoutFile;
		final File ilfFile = new File(ilfPath);
		if(!ilfFile.exists() || !ilfFile.canRead() || !ilfFile.canWrite())
		{
			sendSystemMessageTestingOnly(self, "Error: ILF File doesn't exist or isn't readable and writable at "+ilfPath);
			return;
		}
		if(ids.size() <= 0)
		{
			return;
		}
		File mifFile = new File(ilfPath.replace("ilf", "mif"));
		try
		{
			FileWriter fw = new FileWriter(mifFile);
			fw.write("// ======= Generated by Interior Layout Utility Script ========\n");
			fw.write("// ======= Created by "+getPlayerFullName(self)+" ("+self+") ========\n");
			fw.write("// ======= Generated on "+ LocalDateTime.now() +" ========\n\n");
			fw.write("FORM \"INLY\"\n");
			fw.write("{\n");
			fw.write("\tFORM \"0000\"\n");
			fw.write("\t{\n");
			for(obj_id id: ids)
			{
				fw.write("\t\tCHUNK \"NODE\"\n");
				fw.write("\t\t{\n");
				fw.write("\t\t\tcstring \""+getSharedObjectTemplateName(id)+"\"\n");
				fw.write("\t\t\tcstring \""+getCellName(getContainedBy(id))+"\"\n");
				final transform pos = getTransform_o2p(id);
				fw.write("\t\t\tfloat "+String.format("%.8f", pos.matrix[0][0])+"\n");
				fw.write("\t\t\tfloat "+String.format("%.8f", pos.matrix[0][1])+"\n");
				fw.write("\t\t\tfloat "+String.format("%.8f", pos.matrix[0][2])+"\n");
				fw.write("\t\t\tfloat "+String.format("%.8f", pos.matrix[0][3])+"\n");
				fw.write("\t\t\tfloat "+String.format("%.8f", pos.matrix[1][0])+"\n");
				fw.write("\t\t\tfloat "+String.format("%.8f", pos.matrix[1][1])+"\n");
				fw.write("\t\t\tfloat "+String.format("%.8f", pos.matrix[1][2])+"\n");
				fw.write("\t\t\tfloat "+String.format("%.8f", pos.matrix[1][3])+"\n");
				fw.write("\t\t\tfloat "+String.format("%.8f", pos.matrix[2][0])+"\n");
				fw.write("\t\t\tfloat "+String.format("%.8f", pos.matrix[2][1])+"\n");
				fw.write("\t\t\tfloat "+String.format("%.8f", pos.matrix[2][2])+"\n");
				fw.write("\t\t\tfloat "+String.format("%.8f", pos.matrix[2][3])+"\n");
				fw.write("\t\t}\n");
			}
			fw.write("\t}\n");
			fw.write("}\n");
			fw.close();
		}
		catch (IOException e)
		{
			sendSystemMessageTestingOnly(self, "Error: Exception trying to save ILF: "+e.getMessage());
			return;
		}
		final String result = system_process.runAndGetOutput("ant compile_miff", new File("../../"));
		if(!result.contains("BUILD SUCCESSFUL"))
		{
			sendSystemMessageTestingOnly(self, "Error: ant compile_miff failed.");
			return;
		}
		final String iffPath = "../../data/sku.0/sys.client/" + interiorLayoutFile.replace("ilf", "iff");
		File iffFile = new File(iffPath);
		if(!iffFile.exists() || !iffFile.canRead() || !iffFile.canWrite())
		{
			sendSystemMessageTestingOnly(self, "Error: There was an error finding/reading/writing the IFF generated by the utility's MIFF.");
			return;
		}
		File iffFileTemp = new File(iffPath.replace("iff", "ilf"));
		if(!iffFile.renameTo(iffFileTemp))
		{
			sendSystemMessageTestingOnly(self, "Error: Couldn't rename MIF-generated IFF of the ILF to the ILF file type.");
			return;
		}
		if(!ilfFile.delete())
		{
			sendSystemMessageTestingOnly(self, "Error: Couldn't delete original ILF File.");
			return;
		}
		try
		{
			Files.move(Paths.get("../../data/sku.0/sys.client/" + interiorLayoutFile), Paths.get("../../dsrc/sku.0/sys.client/" + interiorLayoutFile));
		}
		catch (IOException e)
		{
			sendSystemMessageTestingOnly(self, "Error: Couldn't move newly generated data ILF to dsrc ILF.");
			return;
		}
		sendSystemMessageTestingOnly(self, "Success! The interior layout has been written.");
	}

	private static void spawnAllInteriorLayoutObjectsAsTangibles(obj_id self, obj_id building, String interiorLayoutFile)
	{
		final String ilfPath = "../../dsrc/sku.0/sys.client/" + interiorLayoutFile;
		final File ilfFile = new File(ilfPath);
		if(!ilfFile.exists() || !ilfFile.canRead())
		{
			sendSystemMessageTestingOnly(self, "Error: ILF File doesn't exist or isn't readable at "+ilfPath);
			return;
		}
		try
		{
			FileChannel channel = FileChannel.open(ilfFile.toPath());
			MappedByteBuffer bb = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
			bb.order(ByteOrder.BIG_ENDIAN);
			bb.get(new byte[4]); // skip FORM (declaration)
			bb.getInt(); // get form size
			bb.get(new byte[8]); // skip INLY (form name) and FORM 0000 (declaration)
			final int NodeSize = bb.getInt(); // get size of 0000 form (containing NODE chunks)
			bb.get(new byte[4]); // skip 0000 (form name)
			final int endPosition = bb.position() + NodeSize - 4;
			while(bb.position() < endPosition) // loop of NODE chunks
			{
				byte[] nodeName = new byte[4];
				bb.get(nodeName); // "NODE"
				int chunkLength = bb.getInt();
				byte[] chunkData = new byte[chunkLength];
				bb.get(chunkData);
				handleNodeDataBytes(chunkData, building);
			}
			channel.close();
			sendSystemMessageTestingOnly(self, "Success. Finished populating all cells in the building with all objects existing in the interior layout.");
		}
		catch (IOException e)
		{
			sendSystemMessageTestingOnly(self, "Error: Exception trying to spawn ILF Objects: "+e.getMessage());
		}
	}

	public static void handleNodeDataBytes(byte[] bytes, obj_id building)
	{
		ByteBuffer data = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);

		// get template
		String template;
		StringBuilder sb = new StringBuilder();
		byte b;
		while((b = data.get()) != 0)
		{
			sb.append((char)b);
		}
		template = sb.toString().replace("shared_", "");

		// get cell
		String cell;
		sb.setLength(0);
		while((b = data.get()) != 0)
		{
			sb.append((char)b);
		}
		cell = sb.toString();

		// get object position as transform
		transform t = new transform(
				data.getFloat(),
				data.getFloat(),
				data.getFloat(),
				data.getFloat(),
				data.getFloat(),
				data.getFloat(),
				data.getFloat(),
				data.getFloat(),
				data.getFloat(),
				data.getFloat(),
				data.getFloat(),
				data.getFloat()
		);
		obj_id object = createObject(template, t, getCellId(building, cell));
		setObjVar(object, "interior_layout_utility", true);
	}

}
