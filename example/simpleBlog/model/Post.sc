class Post {
  String shortName;
  String title;
  String postText;

   @sc.obj.EditorCreate(constructorParamNames="blog, shortName")
   static Post newPost(Blog blog, String shortName) {
      Post newPost = new Post();
      newPost.shortName = shortName;
      blog.posts.add(newPost);
      return newPost;
   }
}
