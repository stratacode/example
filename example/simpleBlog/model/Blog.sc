import sc.example.simpleBlog.blogs.*;

class Blog {
   int blogId;
   String blogName;
   String blogDesc;

   static ArrayList<Blog> blogs = { scNewsBlog, scDevBlog };

   ArrayList<Post> posts;

   static Blog getBlog(Integer blogId) {
      if (blogId == null)
         return null;
      for (Blog blog:blogs) {
         if (blog.blogId == blogId)
            return blog;
      }
      return null;
   }

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
   static Blog newBlog(int blogId) {
      Blog newBlog = new Blog();
      newBlog.blogId = blogId;
      newBlog.posts = new ArrayList<Post>();
      blogs.add(newBlog);
      return newBlog;
   }
}
