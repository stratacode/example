
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

   // Update when user, eliteStatus or recentPurchases changes
   boolean showElite := user.eliteStatus ||
                        user.recentPurchases > 200;

   // Update when category or showElite changes.
   // findFeatured() can be a local or remote method
   List<Product> promoted := category.findFeatured(showElite);

}
