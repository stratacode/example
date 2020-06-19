
import java.util.List;

public class BindExample {
   class Product {
   }
   class Category {
      List<Product> findFeatured(boolean showElite) {
         return null;
      }
   }
   class User {
      boolean eliteStatus;
      int recentPurchases;
   }

   Category category;
   User user;

   // Update when eliteStatus or recentPurchases changes
   boolean showElite := user.eliteStatus ||
                        user.recentPurchases > 200;

   // Update when category or showElite changes.
   // findFeatured can be local or remote
   List<Product> promoted := category.findFeatured(showElite);

}
