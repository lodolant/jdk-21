# java21

Get The JDK (https://jdk.java.net/21/, https://javaalmanac.io/jdk/download/#version=21&type=jdk)

Unzip it

Add/Replace "bin" directory to the path

## Useful compilation flags

You will need to activate previews for previews features.

For some incubator features such as jdk.incubator.concurrent, you will need to both add the module, and export the module. Both for the build, and the execution. Following flags can be used for this project:

```
--enable-preview 
--add-modules jdk.incubator.concurrent 
--add-exports jdk.incubator.concurrent/jdk.incubator.concurrent=ALL-UNNAMED 
--add-exports java.base/jdk.internal.vm=ALL-UNNAMED 
```

# IntelliJ

## For a successful build
You will need to use flags to enable preview features/incubator features (See Useful compilation flags section). It can be configured in one of the two sections: 
```
"Build, Execution, Deployment" / "Compiler" / "Java Compiler" / "Additional command line parameters"
```
```
"Build, Execution, Deployment" / "Compiler" / "Java Compiler" / "Override compiler parameters per module"
```
Just be mindful not to override the parameters in the first section with empty parameters in the second.

## For a successful execution
You will need to use flags to enable preview features/incubator features (See Useful compilation flags section). It can be configured here:
```
"Run/Debug configurations" / "Modify options" / "Add VM Options"
```