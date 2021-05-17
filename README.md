# Android Registration, Login and Profile Creating using firebase.

##### The application with have these features

* Registration with email verification
* Email login
* Reset password(via forgot password activity)
* Upload profile photo to firebase
* Side navigation using slide root api



 *We will use firestore to store our data. If you dont know anything about firestore you can visit [Getting started with firestore](https://firebase.google.com/docs/firestore/quickstart)*


# Api's Used
To get started with the UI I used [Material Components for Android](https://github.com/material-components/material-components-android) the api comes mostly when you create a new project in android studio but if not you can add it to the gradle

*Most of the apis we will use will require google marven so first add this to your build.gradle(Project: ) file*

```groovy
buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath "com.android.tools.build:gradle:4.1.3"

    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url "https://maven.google.com" }

        maven { url 'https://jitpack.io' }
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}
```


## Adding Material Component

*Add the api to app gradle build.gradle(Module: ) file*
#### Gradle
```groovy
dependencies {
        implementation 'com.google.android.material:material:1.3.0'
}
```

For people using androidx you need to update your styles.xml or else you will get an error with using material component
### For Androidx users

*Change this Styles.xml from AppCompat*
#### AppCompat users Styles.xml
```xml
 <!-- Base application theme. -->
    <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>
```

*Change to this*
#### AndroidX users Styles.xml
```xml
  <!-- Base application theme. -->
    <style name="AppTheme" parent="Theme.MaterialComponents.Light.NoActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>
```


After designing the login UI lets add firebase Api's 
* First create a firebase project and add your apllication to it. [Add firebase to you android application](https://firebase.google.com/docs/android/setup#console)
* Second add firebase configuration file and enable firebase products. [How to add configuration file](https://firebase.google.com/docs/android/setup#add-config-file)
* Thirdly add firebase SDKs to your app. [How to add firebase SDKs](https://firebase.google.com/docs/android/setup#add-sdks)

#### SDK's Required
```groovy
dependencies {
         implementation platform('com.google.firebase:firebase-bom:26.0.0')
          implementation 'com.google.firebase:firebase-core'
          implementation 'com.google.firebase:firebase-auth'
          implementation 'com.google.firebase:firebase-storage'
          implementation 'com.google.firebase:firebase-firestore'
          implementation 'com.google.firebase:firebase-messaging'
}
```
In your firebase dashboard select Authentication then sign in method tab and then enable Email/Password and Google.

Now lets add Slide Root Api for side navigation

### SlidingRootNav
To check more about [SlidingRootNav](https://github.com/yarolegovich/SlidingRootNav#slidingrootnav)
#### Gradle
```groovy
dependencies {
        implementation 'com.yarolegovich:sliding-root-nav:1.1.1'

}
```







