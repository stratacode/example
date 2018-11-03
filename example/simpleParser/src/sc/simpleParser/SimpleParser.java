package sc.simpleParser;

import sc.lang.ILanguageModel;
import sc.lang.java.*;
import sc.layer.*;
import sc.parser.ParseUtil;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.TreeSet;

/** 
 * This example shows you how to use the parselets parsing, modelling, formatting, transpiling etc apis 
 * without layer definition files or any of the other frameworks in StrataCode.  You provide simply 
 * a srcPath for the source you want to parse and/or a classPath for the class files used by those source files.  
 *
 * This class has a few options but allows you to print or do a find/replace
 * operation on one type or a set of matching types.  It does not yet change the expressions but it
 * could be enhanced to be a real find-replace tool without too much effort.
 *
 * Although this uses StrataCode, for your example you'll have only one 'layer' called 'sysLayer'.  It's an
 * inactive layer because this tool is not going to run any code, just edit source more like an IDE.
 *
 * You specify class files in a traditional class path but you have a choice for how those classes are loaded.
 * The regular classPath uses this classes class loader to load .class files as normal Java classes.
 * The externalClassPath loads classes using the StrataCode CFClass library which
 * reads .class files just for their types, fields, method declarations, etc.  
 * You can use both at the same time, but it's not defined when there are references from classPath classes to externalClassPath classes.
 * The reverse direction is fine.
 * Using the system classPath can avoid loading classes twice but runs into some limitations so externalClassPath is probably the best way to go
 * for most purposes.
 */
public class SimpleParser {
   LayeredSystem sys; // Think of this as your 'project' in StrataCode.  It's used for everything including a set of inactive and inactive layers - inactive are used for editing, active for build/run
   Layer sysLayer; // Here we just create one layer to hold all of the info specified in the classPath/srcPath.

   String classPath = null, externalClassPath = null, srcPath = null;

   String typeName = null, findName = null;

   String fromName = null, toName = null;

   boolean doFields = true;
   boolean doMethods = true;
   boolean doTypes = true;

   boolean outputOnly = true;

   public static void main(String[] args) {
      SimpleParser sp = new SimpleParser(args);
      sp.run();
   }

   public SimpleParser(String[] args) {
      initOptions(args);

      // The first argument is is the 'main-directory' - i.e. where we store the typeIndex
      sys = ParseUtil.createSimpleParser(".", classPath, externalClassPath, srcPath, null);
      // In case we use initTypeIndex, this will maintain this incrementally for speed.  Use TypeIndexModel.Rebuild to just rebuild it each time.
      sys.options.typeIndexMode = TypeIndexMode.Refresh;

      // The simple parser creates a single layer to represent the classPath/srcPath you provided.  Since we are not running this code, it creates
      // an inactive layer.  We need this layer for some of the APIs which work either in active or inactive mode like getSrcTypeDeclaration().
      sysLayer = sys.inactiveLayers.get(0);
   }

   /** Iterate over the types selected from the command line and either print or do a find-replace and print the resulting code */
   public void run() {
      ArrayList<TypeDeclaration> types = new ArrayList<TypeDeclaration>();
      if (typeName != null) {
         TypeDeclaration type = sys.getSrcTypeDeclaration(typeName, sysLayer);
         if (type == null)
            System.err.println("*** typeName not found: " + typeName);
         else
            types.add(type);
      }
      if (findName != null) {
         // This will be expensive if there are lots of files in the srcPath - it will parse them the first time and refresh the index each time for changes
         sys.initTypeIndex();

         LinkedHashSet<String> matchingTypeNames = new LinkedHashSet<String>();
         // NOTE: findName here is a prefix, not a regex or anything.  We should make that more flexible!
         sys.findMatchingGlobalNames(null, sysLayer, findName, matchingTypeNames, true, false, false);
         if (matchingTypeNames.size() == 0)
            System.out.println("*** No matches with prefix: " + findName);
         else {
            System.out.println("Found: " + matchingTypeNames);
            for (String matchingTypeName:matchingTypeNames) {
               TypeDeclaration type = sys.getSrcTypeDeclaration(matchingTypeName, sysLayer);
               if (type == null)
                  System.err.println("*** No src found for: " + matchingTypeName);
               else
                  types.add(type);
            }
         }
      }
      for (TypeDeclaration type:types) {
         JavaModel model = type.getJavaModel();
         boolean modelChanged = false;
         if (doTypes) {
            if (toName == null) {
               System.out.println(type.toDeclarationString());
            }
            else if (type.typeName.equals(fromName)){
               System.out.println("*** Before: " + type.toDeclarationString());
               type.updateTypeName(toName, !outputOnly && type.getEnclosingType() == null);
               System.out.println("*** After: " + type.toDeclarationString());
               modelChanged = true;
            }
         }
         if ((doFields || doMethods) && type.body != null) {
            for (Statement st:type.body) {
               if (doFields && st instanceof FieldDefinition) {
                  if (toName == null) {
                     System.out.println(st.toDeclarationString());
                  }
                  else {
                     FieldDefinition field = (FieldDefinition) st;
                     for (VariableDefinition varDef:field.variableDefinitions) {
                        if (varDef.variableName.equals(fromName)) {
                           System.out.println("*** Before: " + field.toDeclarationString());
                           varDef.setProperty("variableName", toName);
                           System.out.println("*** After: " + field.toDeclarationString());
                           modelChanged = true;
                        }
                     }
                  }
               }
               else if (doMethods && st instanceof AbstractMethodDefinition) {
                  if (toName == null) {
                     System.out.println(st.toDeclarationString());
                  }
                  else {
                     AbstractMethodDefinition meth = (AbstractMethodDefinition) st;
                     if (meth.name != null && meth.name.equals(fromName)) {
                        System.out.println("*** Before: " + meth.toDeclarationString());
                        meth.setProperty("name", toName);
                        System.out.println("*** After: " + meth.toDeclarationString());
                        modelChanged = true;
                     }
                  }
               }
            }
         }
         // Call this after making any setProperty calls anywhere in the
         // AST.  By default, the model does not track changes on every
         // API call but you can also change that.  It can be more 
         // efficient to validate the saved model afterwards if you make
         // lots of changes to the statements.
         if (outputOnly) {
            if (modelChanged)
               model.validateSavedModel(true);
            System.out.println(model.toLanguageString());
         }
         else if (modelChanged) {
            System.out.println("*** Updating file: " + model.getSrcFile());
            model.saveModel();
         }
      }
   }

   public void initOptions(String[] args) {
      for (int i = 0; i < args.length; i++) {
         String arg = args[i];
         if (arg.equals("-apply"))
            outputOnly = false;
         else if (arg.equals("-t")) {
            doFields = false;
            doMethods = false;
            doTypes = true;
         }
         else if (arg.equals("-f")) {
            doFields = true;
            doMethods = false;
            doTypes = false;
         }
         else if (arg.equals("-m")) {
            doFields = false;
            doMethods = true;
            doTypes = false;
         }
         else if (i < args.length - 1) {
            if (arg.equals("-cp"))
               classPath = args[++i];
            else if (arg.equals("-ext"))
               externalClassPath = args[++i];
            else if (arg.equals("-srcPath"))
               srcPath = args[++i];
            else if (arg.equals("-typeName"))
               typeName = args[++i];
            else if (arg.equals("-find"))
               findName = args[++i];
            else if (arg.equals("-from"))
               fromName = args[++i];
            else if (arg.equals("-to"))
               toName = args[++i];
            else
               System.err.println("*** Unrecognized arg: " + arg);
         }
         else
            System.err.println("*** Unrecognized arg: " + arg);
      }
   }
}
