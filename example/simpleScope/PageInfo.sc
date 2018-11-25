import sc.obj.AppGlobalScopeDefinition;

@Sync
scope<appGlobal>
object PageInfo {
   // Since this is created in the appGlobal scope, we'll use the API to access the current appGlobal context
   // and then use the 'id' as the initial value of the pageTitle.  It makes a nice way to see what scope we
   // are using for this page and also shows the apis.
   String pageTitle = AppGlobalScopeDefinition.getAppGlobalScope().getAppId();
}
