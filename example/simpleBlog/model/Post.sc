@Component
@sc.obj.Sync
class Post {
  String shortName;
  String title;
  String postText;

   @sc.obj.EditorCreate(constructorParamNames="blog, shortName")
   static Post createPost(Blog blog, String shortName) {
      Post newPost = new Post();
      newPost.shortName = shortName;
      blog.posts.add(newPost);
      return newPost;
   }
}
