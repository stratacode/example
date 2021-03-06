
@Component
@sc.obj.Sync
@CompilerSettings(constructorProperties="blogId")
@sc.obj.EditorCreate
class Blog {
   int blogId;
   String blogName;
   String blogDesc;
   
   ArrayList<Post> posts;

   Post getPost(String postShortName) {
      if (postShortName == null)
         return posts.size() == 0 ? null : posts.get(posts.size()-1);

      for (Post post:posts) {
         if (post.shortName.equals(postShortName))
            return post;
      }
      return null;
   }

   void init() {
      if (posts == null)
         posts = new ArrayList<Post>();
      BlogManager.addBlog(this);
   }
}