@Component
@sc.obj.Sync
class Post {
   String shortName;
   String title;
   String postText;
   long postTime;

   @sc.obj.EditorCreate(constructorParamNames="blog, shortName")
   static Post createPost(Blog blog, String shortName) {
      Post newPost = new Post();
      newPost.shortName = shortName;
      newPost.postTime = System.currentTimeMillis();
      blog.posts.add(newPost);
      return newPost;
   }

   final static int millisPerDay = 1000*60*60*24;

   // Simulate a postTime of this # of days ago for testing purposes
   static long daysAgoTime(int daysInThePast) {
      return System.currentTimeMillis() - millisPerDay*daysInThePast;
   }

   int getDaysSincePost() {
      return (int) ((System.currentTimeMillis() - postTime)/millisPerDay);
   }
}
