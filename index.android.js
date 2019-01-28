var RNPhotoEditor = require('react-native').NativeModules.RNPhotoEditor;

module.exports = {
  /**
   * Open photo editor screen with props
   */
  Edit: function(props) {
    console.log('>>>>>>>> photoeditor native module <<<<<<<<<');
    console.log(RNPhotoEditor);

    return RNPhotoEditor.Edit({path: props.path}, props.onDone || null, props.onCancel || null);
  }
};
