package sc.example.buildTag;

/** 
   The code here is interpreted, not compiled as it's the layer definition so it runs at build time. 
   When the layer is initialized, we'll run some git commands to create an scmVersion which can then
   be used in a BuildInit annotation (see ExBuildTag.sc) 
 */
public example.buildTag extends sys.std {
   String scmVersion;

   void init() {
      if (!activated) // Not loaded as part of a build so nothing to do here.
         return;
      String branch = FileUtil.execCmd("git symbolic-ref --short HEAD"); // Current branch name only
      if (branch != null) {
         branch = branch.trim();
         String hash = FileUtil.execCmd("git rev-parse --short HEAD").trim();

         // Look at 'git status' and see if there are local changes in this build - if so, summarize them like '3M,2C,1D'
         String status = FileUtil.execCmd("git status -suno --porcelain").trim();
         String repoStatus = ""; 
         if (status.length() > 0) {
            String[] statusLines = status.split("\n");
            int numMods = 0, numAdds = 0, numDels = 0, numOther = 0;
            for (String line:statusLines) {
               char start = line.trim().charAt(0);
               switch (start) {
                  case 'M':
                     numMods++;
                     break;
                  case 'A':
                     numAdds++;
                     break;
                  case 'D':
                     numDels++;
                     break;
                  default:
                     numOther++;
                     break;
               }
            }
            if (numMods != 0) 
               repoStatus += numMods + "M";
            if (numAdds != 0)
               repoStatus += repoStatus.length() == 0 ? "" : "," + numAdds + "A";
            if (numDels != 0)
               repoStatus += repoStatus.length() == 0 ? "" : "," + numDels + "D";
            if (numOther != 0)
               repoStatus += repoStatus.length() == 0 ? "" : "," + numOther + "?";
            repoStatus = "+" + repoStatus;
         }
         scmVersion = branch + '@' + hash + repoStatus; 
      }
      else 
         scmVersion = "not from git dir";
   }
}
