import sc.util.StringUtil;

@MainInit
object BlogApp extends AppFrame {
   int windowWidth, windowHeight;
   location = new Point(300, 300);
   size = new Dimension(windowWidth, windowHeight);

   int spad = 10;

   // TODO: put this into a shared view model layer
   int blogId;
   String postShortName;

   Blog blog := BlogManager.findBlog(blogId);
   Post post := blog == null ? null : blog.getPost(postShortName);

   double startRow1Y = ypad + baseline;

   object mainLabel extends JLabel {
   // The getRemaining method depends on the 'complete' state of the TodoItem so this binding is refreshed
   // when completeCheckBox.selected changes by calling Bind.refreshBindings
      text := "All blogs";
      location := SwingUtil.point(xpad, startRow1Y);
      size := preferredSize;
   }

   class BlogHeader extends ComponentGroup {
      Blog blog;
      int ix;

      double startY;

      BlogHeader(Blog blog, int ix) {
         this.blog = blog;
         this.ix = ix;
      }

      public void setIx(int ix) {
         this.ix = ix;
         startY = ix == 0 ? startRow1Y : blogHeaderList.repeatComponents.get(ix-1).startY + headerButton.size.height + ypad;
      }

      object headerButton extends JButton {
         text :=: blog.blogName;
         location := SwingUtil.point(mainLabel.location.x + mainLabel.size.width + xpad, startY + baseline);
         size := preferredSize;
         enabled := blog != BlogApp.this.blog;
         clickCount =: BlogApp.this.blog = blog;
      }
   }

   object blogHeaderList extends RepeatComponent<BlogHeader> {
      repeat := BlogManager.blogs;

      double height = ypad;

      parentComponent = BlogApp.this;

      // This will be called once for each blog when the RepeatComponent refreshes
      public BlogHeader createRepeatElement(Object blog, int ix, Object oldComp) {
         return new BlogHeader((Blog)blog, ix);
      }

      public Object getRepeatVar(BlogHeader blogHeader) {
         return blogHeader.blog;
      }

      public void setRepeatIndex(BlogHeader blogHeader, int ix) {
         blogHeader.ix = ix;
      }

      boolean refreshList() {
         boolean anyChanges = super.refreshList();

         int newHeight = ypad;
         for (int i = 0; i < repeatComponents.size(); i++) {
            java.awt.Rectangle bounds = SwingUtil.getBoundingRectangle(repeatComponents.get(i));
            newHeight += bounds.size.height + ypad;
         }
         height = newHeight;

         return anyChanges;
      }
   }

   double startRow2Y := startRow1Y + blogHeaderList.height + ypad;

   object blogDescLabel extends JLabel {
      location := SwingUtil.point(xpad, startRow2Y);
      size := preferredSize;
      text := blog.blogDesc;
   }

   double startRow3Y := startRow2Y + blogDescLabel.height + 4*ypad;

   object blogPanel extends JPanel {
      visible := blog != null;
      location := SwingUtil.point(xpad, startRow3Y);
      size := SwingUtil.dimension(windowWidth, windowHeight - startRow3Y - 2*ypad);

      class PostHeader extends ComponentGroup {
         Post post;
         int ix;
         double startY;

         PostHeader(Post p, int ix) {
            post = p;
            this.ix = ix;
         }

         public void setIx(int ix) {
            this.ix = ix;
            startY = ix == 0 ? ypad : postHeaderList.repeatComponents.get(ix-1).startY + headerButton.size.height + ypad;
         }

         object headerButton extends JButton {
            text :=: post.title;
            location := SwingUtil.point(mainLabel.location.x + mainLabel.size.width + xpad, startY + baseline);
            size := preferredSize;
            enabled := post != BlogApp.this.post;
            clickCount =: BlogApp.this.post = post;
         }
      }

      object postHeaderList extends RepeatComponent<PostHeader> {
         repeat := blog == null ? null : blog.posts;

         double height;

         parentComponent = blogPanel;

         // This will be called once for each blog when the RepeatComponent refreshes
         public PostHeader createRepeatElement(Object post, int ix, Object oldComp) {
            return new PostHeader((Post)post, ix);
         }

         public Object getRepeatVar(PostHeader postHeader) {
            return postHeader.post;
         }

         public void setRepeatIndex(PostHeader postHeader, int ix) {
            postHeader.ix = ix;
         }

         boolean refreshList() {
            boolean anyChanges = super.refreshList();

            int newHeight = ypad;
            for (int i = 0; i < repeatComponents.size(); i++) {
               java.awt.Rectangle bounds = SwingUtil.getBoundingRectangle(repeatComponents.get(i));
               newHeight += bounds.size.height + ypad;
            }
            height = newHeight;

            return anyChanges;
         }

         public void setParentComponent(java.awt.Component component) {
            super.setParentComponent(component);
         }
      }

      object postPanel extends JPanel {
         location := SwingUtil.point(xpad, postHeaderList.height + ypad);
         size := SwingUtil.dimension(windowWidth, windowHeight - startRow3Y - location.y - 4*ypad);
         visible := post != null;

         object postTitleLabel extends JLabel {
            location := SwingUtil.point(0, 0);
            size := preferredSize;
            text := post.title;
         }

         object postTextArea extends JTextArea {
            location := SwingUtil.point(0, postTitleLabel.location.y + postTitleLabel.size.height + ypad);
            size := SwingUtil.dimension(postPanel.size.width - 4*xpad, postPanel.size.height - (postTitleLabel.size.height + 3*ypad));
            text := StringUtil.insertLinebreaks(post.postText, (int) (postPanel.size.width / 7));
         }
      }
   }
}
