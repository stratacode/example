import java.math.BigDecimal;

@sc.obj.CompilerSettings(needsPropertyNames=true)
class InvoiceItem {
   String description;
   String productId;
   int quantity;
   BigDecimal listPrice;
   BigDecimal discountPercent;
   BigDecimal discountPrice := listPrice.subtract(discountPercent.multiply(listPrice));
   BigDecimal totalPrice := discountPrice.multiply(new BigDecimal(quantity));

   InvoiceItem(String description,String productId, int quantity, BigDecimal listPrice, BigDecimal discountPercent) {
      this.description = description;
      this.productId = productId;
      this.quantity = quantity;
      this.listPrice = listPrice;
      this.discountPercent = discountPercent;
   }
}