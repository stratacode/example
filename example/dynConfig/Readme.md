Examples to demonstrate different ways of combining compiled and 
dynamic configuration.

Run 


    scc example/dynConfig/version1
    scc example/dynConfig/version2
    scc example/dynConfig/version3
    scc example/dynConfig/version4
   
The layers: core, version3, and version4 are normal layers 
which by default are compiled but can be made dynamic using the
 -dyn option.  The layers version1 and 2 can only be run as 
 dynamic because they use the dynamic keyword.

Currently version 1 and 2 are the same and 3 and 4 are the same.
The extra layers exist as placeholders for a new mode we'd like to 
add where we can support a single class split into compiled/dynamic 
parts (i.e. a mix of compiled and dynamic fields).  Currently
when you modify a compiled type with a dynamic layer, it makes it
dynamic if possible or leaves it as compiled if it's marked as 
compileOnly.  

We'd like to support a new annotation CompilerSettings.compileTypeForLayer
and possibly even make this the default for when you modify 
a compiled type in a compiled layer from a dynamic type in a dynamic
layer. 

For now, look at the ConfigObj/ConfigClass classes for how you split
an object so there's a compiled base class and a dynamic configuration instance.
