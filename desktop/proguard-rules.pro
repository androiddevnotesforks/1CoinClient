-dontwarn **

-keep class com.finance_tracker.finance_tracker.MainKt { *; }

-keep class kotlin.** { *; }
-keep class kotlinx.coroutines.** { *; }
-keep class org.jetbrains.skia.** { *; }
-keep class org.jetbrains.skiko.** { *; }
-keep class org.sqlite.** { *; }

-assumenosideeffects public class androidx.compose.runtime.ComposerKt {
    void sourceInformation(androidx.compose.runtime.Composer,java.lang.String);
    void sourceInformationMarkerStart(androidx.compose.runtime.Composer,int,java.lang.String);
    void sourceInformationMarkerEnd(androidx.compose.runtime.Composer);
}

### Kotlinx Serialization ###

# Keep `Companion` object fields of serializable classes.
# This avoids serializer lookup through `getDeclaredClasses` as done for named companion objects.
-if @kotlinx.serialization.Serializable class **
-keepclassmembers class <1> {
    static <1>$Companion Companion;
}

# Keep `serializer()` on companion objects (both default and named) of serializable classes.
-if @kotlinx.serialization.Serializable class ** {
    static **$* *;
}
-keepclassmembers class <2>$<3> {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep `INSTANCE.serializer()` of serializable objects.
-if @kotlinx.serialization.Serializable class ** {
    public static ** INSTANCE;
}
-keepclassmembers class <1> {
    public static <1> INSTANCE;
    kotlinx.serialization.KSerializer serializer(...);
}

# @Serializable and @Polymorphic are used at runtime for polymorphic serialization.
-keepattributes RuntimeVisibleAnnotations,AnnotationDefault

-keepclassmembers @kotlinx.serialization.Serializable class com.finance_tracker.finance_tracker.** {
    # lookup for plugin generated serializable classes
    *** Companion;
    # lookup for serializable objects
    *** INSTANCE;
    kotlinx.serialization.KSerializer serializer(...);
}
# lookup for plugin generated serializable classes
-if @kotlinx.serialization.Serializable class com.finance_tracker.finance_tracker.**
-keepclassmembers class com.finance_tracker.finance_tracker.<1>$Companion {
    kotlinx.serialization.KSerializer serializer(...);
}

# Serialization supports named companions but for such classes it is necessary to add an additional rule.
# This rule keeps serializer and serializable class from obfuscation. Therefore, it is recommended not to use wildcards in it, but to write rules for each such class.
-keepattributes InnerClasses # Needed for `getDeclaredClasses`.
-keep class com.finance_tracker.finance_tracker.SerializableClassWithNamedCompanion$$serializer {
    *** INSTANCE;
}

-keep class kotlinx.serialization.json.** { *; }

### Ktor ###

-keep class io.ktor.** { *; }

### Amplitude ###

-keep class com.amplitude.** { *; }