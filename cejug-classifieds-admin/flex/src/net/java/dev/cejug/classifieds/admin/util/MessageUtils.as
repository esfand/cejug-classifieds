package net.java.dev.cejug.classifieds.admin.util
{
    import mx.controls.Alert;
    
    public class MessageUtils
    {
        public static function showError(message:String):void {
            showMessage(message, "Error");
        }
        public static function showInfo(message:String):void {
            showMessage(message, "Information");
        }
        public static function showMessage(message:String, title:String):void {
            Alert.show(message, title);
        }
        public static function showQuestion(message:String, callback:Function):void {
            Alert.show(message, "Question", Alert.YES | Alert.NO, null, callback, null, Alert.YES);
        }
    }
}