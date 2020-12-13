# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keep public class com.dw.capstonebnform.dto.RecallWithInjuriesAndImagesAndProducts
-keep public class com.dw.capstonebnform.adapter.LowAlertAdapter
# Picasso
-dontwarn com.squareup.okhttp.**

# Needed for Parcelable/SafeParcelable Creators to not get stripped
-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

# retrofit
-dontwarn okio.**
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.* { *; }
-keep interface com.squareup.okhttp.* { *; }

-dontwarn rx.**
-dontwarn retrofit.**
-keep class retrofit.* { *; }
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}

-dontwarn com.squareup.okhttp.**
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

-verbose

-dump obfuscation/class_files.txt
-printseeds obfuscation/seeds.txt

#Files that are stripped
-printusage obfuscation/unused.txt
#Files that are not stripped
-printmapping obfuscation/mapping.txt


#-printconfiguration /Users/darinwilliams/Downloads/full-r8-config.txt