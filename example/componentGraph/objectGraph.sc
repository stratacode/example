// Demonstration of components wired into a small graph:
//   - comp1 depends on comp2 and vice versa
@Component
object objectGraph {
   object comp1 extends ComponentClass {
      propA := comp2.propB + 5;
      propB = 9;
   }
   
   object comp2 extends ComponentClass {
      propA := comp1.propB * 3;
      propB = 7;
   }

   boolean componentStarted = false;
   void start() {
      componentStarted = true;
      doAsserts();
   }

   void doAsserts() {
      assertEquals(comp2.propA, 27);
      assertEquals(comp1.propA, 12);
   }

   @Test
   void testObjectGraph() {
      doAsserts();
      assertTrue(componentStarted);
   }
}
