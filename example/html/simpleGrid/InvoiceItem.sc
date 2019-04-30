import java.math.BigDecimal;

@sc.obj.CompilerSettings(needsPropertyNames=true)
class InvoiceItem {
   String description;
   String productId;
   int quantity;
   BigDecimal listPrice;
   BigDecimal discountPrice;

   InvoiceItem(String description,String productId, int quantity, BigDecimal listPrice, BigDecimal discountPrice) {
      this.description = description;
      this.productId = productId;
      this.quantity = quantity;
      this.listPrice = listPrice;
      this.discountPrice = discountPrice;
   }
}