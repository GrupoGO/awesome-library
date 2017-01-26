# awesome-library
With this library you will have access to common and useful functions in your projects, and you won't need to program them each time. Awesome!

#How to add the library to your project?

You will need to add the jitpack.io repository in your build.gradle project file (Note: do not add the jitpack.io repository under buildscript)

```
repositories {
    jcenter()
    maven { url "https://jitpack.io" }
}
```

You will also need to add this line in the dependencies of your build.gradle module file

```
dependencies {
    compile 'com.github.GrupoGO:awesome-library:2.0'
}
```

