@Sync
scope<appSession>
object PageUserInfo {
   int numVisits;
   String userPageTitle = UserSession.userName + "'s " + GlobalPageInfo.pageTitle;
}
