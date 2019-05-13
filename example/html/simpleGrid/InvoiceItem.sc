import java.math.BigDecimal;

@sc.obj.CompilerSettings(needsPropertyNames=true)
@sc.bind.Bindable(skipNull=true)
class InvoiceItem implements Cloneable {
   String description;
   String productId;
   int quantity;
   BigDecimal listPrice;
   BigDecimal discountPercent;
   BigDecimal discountPrice := listPrice.subtract(discountPercent.multiply(listPrice));
   @Bindable
   BigDecimal totalPrice := discountPrice.multiply(new BigDecimal(quantity));

   InvoiceItem(String description,String productId, int quantity, BigDecimal listPrice, BigDecimal discountPercent) {
      this.description = description;
      this.productId = productId;
      this.quantity = quantity;
      this.listPrice = listPrice;
      this.discountPercent = discountPercent;
   }

   // Don't use java's clone because that skips that skips the initialization code which includes the binding expressions
   public InvoiceItem copy() {
      return new InvoiceItem(description, productId, quantity, listPrice, discountPercent);
   }
}