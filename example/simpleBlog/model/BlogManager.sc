import sc.example.simpleBlog.blogs.*;

@sc.obj.Sync
object BlogManager {
   private ArrayList<Blog> blogs = new ArrayList<Blog>();

   private boolean blogsInited = false;

   ArrayList<Blog> getBlogs() {
      if (!blogsInited) {
         blogsInited = true;
         // This will call addBlog and update the blogs array
         Blog[] staticBlogs = {scNewsBlog, scDevBlog};
      }
      return blogs;
   }

   Blog findBlog(Integer blogId) {
      if (blogId == null)
         return null;
      for (Blog blog:blogs) {
         if (blog.blogId == blogId)
            return blog;
      }
      return null;
   }

   void addBlog(Blog blog) {
      blogs.add(blog);
   }

   // Needed so there's a reference to these classes - so they get initialized and are included in the JS runtime
   // TODO: is this even a good use case? Storing data as code?

}