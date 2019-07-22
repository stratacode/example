import sc.example.simpleBlog.blogs.*;

@sc.obj.Sync
object BlogManager {
   ArrayList<Blog> blogs = { scNewsBlog, scDevBlog };

   Blog getBlog(Integer blogId) {
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

}