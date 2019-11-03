import sc.example.simpleBlog.blogs.dev.*;

// Manage a blog instance with source code
object scDevBlog extends Blog {
   blogId = 2;
   // The blog name
   blogName = "Development";
   blogDesc = "StrataCode Developers";

   posts = { devIntro, layersOverview };
}
