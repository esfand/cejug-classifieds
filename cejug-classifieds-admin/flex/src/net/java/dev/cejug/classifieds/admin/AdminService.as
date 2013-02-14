package net.java.dev.cejug.classifieds.admin
{
    import mx.rpc.remoting.mxml.RemoteObject;
    
    public class AdminService
    {
        private var serviceName:String = "CejugClassifiedsAdminService";
        //private var serviceName:String = "CejugClassifiedsAdminServiceEJB";

        public function getRemoteObject():RemoteObject {
            return new RemoteObject(serviceName);
        }
    }
}