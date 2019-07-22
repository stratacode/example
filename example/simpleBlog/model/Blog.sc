
@Component
@sc.obj.Sync
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

   @sc.obj.EditorCreate(constructorParamNames="blogId")
   static Blog createBlog(int blogId) {
      Blog newBlog = new Blog();
      newBlog.blogId = blogId;
      newBlog.posts = new ArrayList<Post>();
      BlogManager.addBlog(newBlog);
      return newBlog;
   }
}
