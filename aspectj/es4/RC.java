public class RC implements Resource {
  public RC() {System.out.println("Im creating an RC instance");}
  public void destroy() {System.out.println("Im releasing an RC instance");}
}
