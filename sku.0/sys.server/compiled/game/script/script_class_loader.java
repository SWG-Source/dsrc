package script;

import javax.tools.*;
import java.io.File;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * script_class_loader represents a custom wrapping of Java's
 * native ClassLoader for applications appropriate to SWG Scripting.
 * @see script_entry
 *
 * @since SWG Source 3.1 - August 2021 (Refactored)
 * @author Aconite
 * @author SOE (prior to 3.1 refactor)
 */
public class script_class_loader extends ClassLoader
{
	// keep a dummy method object around to identify methods that are missing from scripts
	private static final Object NO_OBJECT = new Object();
	public static Method NO_METHOD = null;

	private String 		myClassName;		// class this loader is responsible for
	private Class<?>	myClass;			// class this loader has loaded
	private Object		myObject;			// object instance of our class

	/**
	 * Hash Table of Methods for this Class Loader Object with String
	 * Method Name used by SRC mapped to a Java Method Object.
	 */
	private final Hashtable<String, Method> METHODS = new Hashtable<>();
	/**
	 * List of Classes which are Derived From this Class Loader or
	 * which are Inner Classes/Enums to the Class of this Class Loader.
	 *
	 * @since SWG Source 3.1 - August 2021
	 */
	private final ArrayList<String> DERIVED_CLASSES = new ArrayList<>();
	/**
	 * Hash Table of Cached Script Class Loader Classes
	 */
	private static final Hashtable<String, script_class_loader> LOADER_CACHE = new Hashtable<>();
	/**
	 * Contains an immutable collection of script names from the "script"
	 * package (root package) and inner classes of library classes that
	 * should be included during class initialization.
	 *
	 * @since SWG Source 3.1 - August 2021
	 */
	private static final Set<String> DEFAULT_LOAD = getDefaultLoad();
	/**
	 * A class whose name is prefixed with any of the items in this array will
	 * be handled through the default load process which uses Java's native
	 * class loading API in lieu of the system herein. This can be used if
	 * needed to add additional third party libraries to the scripting interface.
	 *
	 * @since SWG Source 3.1 - August 2021
	 */
	private static final String[] NATIVE_CLASS_PREFIXES = {
			"java.",
			"javax.",
			"sun.",
			"intuitive.",
			"jdk.",
			"oracle.",
	};
	/**
	 * Set to true to enable debugging script class loader. See: LOGGER
	 */
	private static final boolean DEBUG = false;
	/**
	 * Because we can't use SRC logging due to its inaccessibility while we
	 * are in the process of initialization, we just use Java's Native Logging
	 * API for a simple logging implementation that only runs if DEBUG = true.
	 *
	 * The log writes to swg-main/exe/linux/script_class_loader.log
	 */
	private static final Logger LOGGER = DEBUG ? Logger.getLogger(script_class_loader.class.getName()) : null;

	static
	{

		// Handle Logger Setup
		if(DEBUG && LOGGER != null)
		{
			try
			{
				FileHandler file = new FileHandler("script_class_loader.log");
				file.setFormatter(new SimpleFormatter());
				LOGGER.setUseParentHandlers(false);
				LOGGER.addHandler(file);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		// initialize the NO_METHOD object
		try
		{
			NO_METHOD = NO_OBJECT.getClass().getMethod("hashCode", (Class<?>[]) null);
		}
		catch( NoSuchMethodException err )
		{
			System.err.println("WARNING: " + err);
		}

	}

	/**
	 * Class constructor.
	 *
	 * @param name      name of class this loader is responsible for
	 */
	private script_class_loader(String name)  throws ClassNotFoundException
	{
		if(DEBUG)
		{
			LOGGER.info("script_class_loader() constructor called for class "+name);
		}

		// initialize basic data
		myClassName = name;
		myClass = null;
		myObject = null;

		LOADER_CACHE.put(name, this);

		// load the class and create an instance of it
		loadClass(name);
		if (myClass != null)
		{
			try
			{
				myObject = myClass.getDeclaredConstructor().newInstance();
			}
			catch (InstantiationException | IllegalAccessException err)
			{
				System.err.println("WARNING: Java Error creating class instance " + name + " : " + err);
			}
			catch (NoSuchMethodException | InvocationTargetException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			System.err.println("WARNING: Java Error script_class_loader creating class " + name);
		}
	}

	/**
	 * Finds the class loader for a given class. If the class loader doesn't
	 * exist, creates it.
	 *
	 * @param name      name of class we want the loader for
	 */
	public static script_class_loader getClassLoader(String name) throws ClassNotFoundException
	{
		if(DEBUG)
		{
			LOGGER.info("script_class_loader.getClassLoader() called for "+name);
		}
		if(LOADER_CACHE.containsKey(name))
		{
			script_class_loader test = LOADER_CACHE.get(name);
			if(test != null)
			{
				if(DEBUG)
				{
					LOGGER.info("script_class_loader.getClassLoader() returned from LOADER_CACHE for "+name);
				}
				return test;
			}
		}

		if (!name.startsWith("script."))
		{
			ClassNotFoundException err =  new ClassNotFoundException("Class " + name + " does not start with 'script.'");
			err.printStackTrace();
			if(DEBUG)
			{
				LOGGER.warning("script_class_loader.getClassLoader() failed to load "+name+" because it did not start with script.");
			}
			throw err;
		}

		script_class_loader loader = new script_class_loader(name);
		if (loader.getMyClass() == null || loader.getMyObject() == null)
		{
			loader = null;
		}
		return loader;
	}

	/**
	 * Removes a loader for a given script, so the class can be loaded again
	 *
	 * @param name      name of class whose loader we want to remove
	 *
	 * @return true if the class was unloaded, false if the class doesn't exist
	 */
	public static boolean unloadClass(String name)
	{
		if(DEBUG)
		{
			LOGGER.info("script_class_loader.unloadClass() unloading class "+name);
		}
		boolean result = false;
		if (LOADER_CACHE.containsKey(name))
		{
			script_class_loader loader = LOADER_CACHE.remove(name);
			// unload all the classes that derive from this one
			if (loader.DERIVED_CLASSES.size() > 0)
			{
				for(String derived_class : loader.DERIVED_CLASSES)
				{
					unloadClass(derived_class);
				}
				loader.DERIVED_CLASSES.clear();
			}
			// unload the class methods
			if (loader.METHODS.size() > 0)
			{
				loader.METHODS.clear();
			}
			loader.myClass = null;
			loader.myObject = null;
			loader.myClassName = null;
			result = true;
		}
		else
		{
			// if the class file exists, go ahead and return true
			String pathedName = name.replace('.', java.io.File.separatorChar);
			String fullname = script_entry.getScriptPath() + pathedName + ".class";
			File file = new File(fullname);
			if (file.isFile())
			{
				result = true;
			}
		}
		return result;
	}

	/**
	 * Adds a derived class name to our derived classes list.
	 *
	 * @param className		the derived class name
	 */
	public void addDerivedClass(String className)
	{
		if(DEBUG)
		{
			LOGGER.info("script_class_loader.addDerivedClass() called to add derived class "+className+" of class "+this.myClassName);
		}
		DERIVED_CLASSES.add(className);
	}

	/**
	 * Gets the cached class for this loader.
	 *
	 * @return the class
	 */
	public Class<?> getMyClass()
	{
		return myClass;
	}

	/**
	 * Gets the cached object for a class.
	 *
	 * @return the object
	 */
	public Object getMyObject()
	{
		return myObject;
	}	// getMyObject()

	/**
	 * Gets the cached methods for a class.
	 *
	 * @return the methods hash table
	 */
	public Hashtable<String, Method> getMyMethods()
	{
		return METHODS;
	}

	/**
	 * Loads a class. If the class is the one this loader is responsible for,
	 * loads it, else passes the request to another loader.
	 *
	 * @param name      class name
	 * @param resolve   flag to resolve the class
	 *
	 * @return the class
	 */
	protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException
	{
		if(DEBUG)
		{
			LOGGER.info("script_class_loader.loadClass() called for "+name+" and resolve value "+resolve);
		}

		// Filter out classes that will be loaded by the default loader
		if (isNativeClass(name) || DEFAULT_LOAD.contains(name))
		{
			if(DEBUG)
			{
				LOGGER.info("loadClass() for "+name+" was in the default load collection or a special flagged class.");
			}
			Class<?> cls = super.loadClass(name, resolve);
			if (myClass == null)
			{
				myClass = cls;
			}
			return cls;
		}

		// if the class we want to load is this class loader's responsibility,
		// load it, otherwise find/create a loader for the class
		if (name.equals(myClassName))
		{
			if (myClass != null)
			{
				return myClass;
			}

			// load the class from it's file
			Class<?> c = findClass(name);
			if (c != null && resolve)
			{
				resolveClass(c);
			}
			return c;
		}
		else
		{
			// see if we already have a loader for the class we want
			script_class_loader newLoader = getClassLoader(name);
			if (newLoader != null)
			{
				return newLoader.getMyClass();
			}
		}
		System.err.println("WARNING: couldn't load class " + name + " in script_class_loader.loadClass");
		return null;
	}

	/**
	 * Finds/creates a class.
	 *
	 * @param name      class name
	 *
	 * @return the class
	 */
	protected Class<?> findClass(String name) throws ClassNotFoundException
	{
		if(DEBUG)
		{
			LOGGER.info("script_class_loader.findClass() called for "+name);
		}
		if (myClass == null)
		{
			try
			{
				byte[] data = loadClassData(name);
				myClass = defineClass(name, data, 0, data.length);

				// we need to keep track of all the superclasses of this class, in case one of them is reloaded
				for (Class<?> superClass = myClass.getSuperclass(); superClass != null; superClass = superClass.getSuperclass())
				{
					script_class_loader superLoader = LOADER_CACHE.get(superClass.getName());
					if (superLoader != null)
					{
						superLoader.addDerivedClass(name);
					}
				}
			}
			catch (ClassFormatError err)
			{
				throw new ClassNotFoundException();
			}
		}
		return myClass;
	}

	/**
	 * Loads a .class file from a file.
	 *
	 * @param name      name of the class to load
	 *
	 * @return byte array containing the class data
	 */
	private static byte[] loadClassData(String name)
	{
		byte[] data = null;
		final String pathName = name.replace('.', java.io.File.separatorChar);
		final String fullName = script_entry.getScriptPath() + pathName + ".class";
		if(DEBUG)
		{
			LOGGER.info("loadClassData is trying to load class "+name+" which was resolved to pathName "+pathName+" and fullName "+fullName);
		}
		try
		{
			RandomAccessFile file = new RandomAccessFile(fullName, "r");
			data = new byte[(int)file.length()];
			file.readFully(data);
			file.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(DEBUG)
			{
				final String message = String.format("loadClassData reached error when trying to load %s (pathName: %s) (fullName %s) -> %s :: %s",
						name, pathName, fullName, e.getMessage(), e.getCause());
				LOGGER.warning(message);
			}
		}
		return data;
	}

	/**
	 * @param name the name of the class
	 * @return true if the first most class name matches a native class prefix
	 *
	 * @since SWG Source 3.1 - August 2021
	 * @author Aconite
	 */
	private static boolean isNativeClass(String name)
	{
		return Arrays.stream(NATIVE_CLASS_PREFIXES).anyMatch(name::startsWith);
	}

	/**
	 * @return An immutable collection of the string names of any class
	 * in the script package and any of their respective inner classes
	 * and any inner classes/enumerations of script.library.*$*
	 * or enumerations formatted for our custom class load process.
	 *
	 * @since SWG Source 3.1 - August 2021
	 * @author Aconite
	 */
	private static Set<String> getDefaultLoad()
	{
		final Set<String> defaultLoad = new HashSet<>();
		try
		{
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
			JavaFileManager.Location location = StandardLocation.CLASS_PATH;
			Set<JavaFileObject.Kind> kinds = new HashSet<>();
			kinds.add(JavaFileObject.Kind.CLASS);
			Iterable<JavaFileObject> baseList = fileManager.list(location, "script", kinds, false);
			Iterable<JavaFileObject> libList = fileManager.list(location, "script.library", kinds, false);
			String script;
			String value;
			for (JavaFileObject o : baseList)
			{
				script = o.getName();
				value = script.strip()
						.substring(script.indexOf(":")+1)
						.substring(script.indexOf("game/")+5)
						.replace("/", ".")
						.replace(".class", "");
				if(DEBUG && LOGGER != null)
				{
					LOGGER.info("script_class_loader static initialization is adding base "+value+" to the default load set.");
				}
				defaultLoad.add(value);
			}
			for (JavaFileObject o : libList)
			{
				script = o.getName();
				if(script.contains("$"))
				{
					value = script.strip()
							.substring(script.indexOf(":")+1)
							.substring(script.indexOf("game/")+5)
							.replace("/", ".")
							.replace(".class", "");
					if(DEBUG && LOGGER != null)
					{
						LOGGER.info("script_class_loader static initialization is adding library "+value+" to the default load set.");
					}
					defaultLoad.add(value);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return Collections.unmodifiableSet(defaultLoad);
	}
}

