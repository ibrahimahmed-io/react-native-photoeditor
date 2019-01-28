# react-native-photoeditor
React Native android wrapper for Image editing SDK powered by DS Photo Editor SDK

#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`

- Add `import co.stackbytes.photoeditor.PhotoEditorPackage;` to the imports at the top of the file
- Add `new PhotoEditorPackage()` to the list returned by the `getPackages()` method

2. Append the following lines to `android/settings.gradle`:
   ```
   include ':react-native-photoeditor'
   project(':react-native-photoeditor').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-photoeditor/android')
   ```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
   ```
   defaultConfig {
      ...
      renderscriptSupportModeEnabled true
   }

   dependencies {
      implementation (name:'ds-photo-editor-sdk-v8', ext:'aar')
      implementation project(':react-native-photoeditor')
      ...
   }
   ```
4. Insert the following lines inside the dependencies block in `android/build.gradle`:
    ```
    allprojects {
      repositories {
         google() // Google's Maven repository
         flatDir {
            dirs 'libs'
         }
         ...
      }
    }
    ```
5. Insert the following lines inside `android/app/build.gradle`:
   ```
   defaultConfig {
      ...
      renderscriptSupportModeEnabled true
   }

   dependencies {
      implementation project(':react-native-image-tools-wm')
      ...
   }
   ```
5. Insert the following lines inside `android/app/src/main/AndroidManifest.xml`:

   Add the following permissions:
   ```
   <uses-permission android:name="android.permission.INTERNET" />
   <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
   <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
   ```
   
   Within the application component, add these lines:
   ```
   <activity
      android:name="com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity"
      android:screenOrientation="portrait"
      android:theme="@android:style/Theme.Holo.NoActionBar" />

   <activity 
      android:name="com.dsphotoeditor.sdk.activity.DsPhotoEditorStickerActivity" 
      android:screenOrientation="portrait"
      android:theme="@android:style/Theme.Holo.NoActionBar" />

   <activity
      android:name="com.dsphotoeditor.sdk.activity.DsPhotoEditorTextActivity"
      android:screenOrientation="portrait"
      android:theme="@android:style/Theme.Holo.NoActionBar"
      android:windowSoftInputMode="adjustPan" />

   <activity
      android:name="com.dsphotoeditor.sdk.activity.DsPhotoEditorCropActivity" 
      android:screenOrientation="portrait"
      android:theme="@android:style/Theme.Holo.NoActionBar" />

   <activity
      android:name="com.dsphotoeditor.sdk.activity.DsPhotoEditorDrawActivity" 
      android:screenOrientation="portrait"
      android:theme="@android:style/Theme.Holo.NoActionBar" />

   <activity
      android:name="com.dsphotoeditor.sdk.activity.DsPhotoEditorAppsActivity" 
      android:screenOrientation="portrait"
      android:theme="@android:style/Theme.Holo.NoActionBar" />
      
    <meta-data android:name="photoeditor-key" android:value="YOUR_DS_SDK_LISCENCE_KEY" />
    ```
## Usage

```javascript
import RNPhotoEditor from 'react-native-photoeditor';
```
See examples in the API section.

## API
### Edit(options)
#### Parameter(s)
* **options:** Object
    * **path:** String - path to image
    * **onDone:** Function
      ##### Returns Promise of
      * **uri:** String
    * **onCancel:** Function
      ##### Returns Promise
```javascript
RNPhotoEditor.Edit({
   path: uri,
   onDone: (uri) => {
      // Sync with your app state
   },
   onCancel: () => {
      // Handle cancel cb
   }
});
