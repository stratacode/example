object Person extends BaseType {
   String firstName, lastName;
   String displayName := firstName + " " + lastName;
}
