package ast;
/*
 * Krakatoa Class
 */
public class KraClass extends Type {

   private String name;
   private KraClass superClass;
   private boolean isStatic;
   private boolean isFinal;
   private InstanceVariableList instanceVariableList;
   private PublicMethodList publicMethodList;
   private PrivateMethodList privateMethodList;

   public KraClass( String name, boolean isFinal, boolean isStatic ) {
      super(name);
      this.isFinal = isFinal;
      this.isStatic = isStatic;
      this.superClass = null;
      this.instanceVariableList = new InstanceVariableList();
      this.publicMethodList = new PublicMethodList();
      this.privateMethodList = new PrivateMethodList();
   }

   public boolean isStatic() {
      return isStatic;
   }

   public boolean isFinal() {
      return isFinal;
   }

   public Type getMethodType(String methodName, TypeList typeList){
      Method method = privateMethodList.search(methodName);
      if (method == null){
         method = publicMethodList.search(methodName);
      }
      return method.getType();
   }

   public KraClass searchMethods(String name,  boolean isStatic, boolean superclass){
      if(!superclass){
         if (privateMethodList.search(name) != null){
            return this;
         }
      }
      if (publicMethodList.search(name) != null){
         return this;
      }
      if (this.superClass != null && !isStatic){
         return this.superClass.searchMethods(name, isStatic, true);
      }
      return null;
   }
   
   public Method getMethod(String methodName){
	  Method method = publicMethodList.search(methodName);
	  if(method == null){
		  if(this.superClass != null)
			  return this.superClass.getMethod(methodName);
		  else
			  return null;
	  }
	  return this.privateMethodList.search(methodName);
   }

   public String getCname() {
      return getName();
   }

   @Override
   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getSuperClassName(){
      return superClass.getName();
   }

   public KraClass getSuperclass() {
      return superClass;
   }

   public void setSuperclass(KraClass superclass) {
      this.superClass = superclass;
   }

   public boolean existsInstanceVariable( Variable v ){
	   Variable variable = this.instanceVariableList.searchVariable(v);
	   
	   if(variable != null)
		   return true;
	   else
		   return false;
   }
   
   public InstanceVariableList getInstanceVariableList() {
      return instanceVariableList;
   }

   public void setInstanceVariableList(InstanceVariableList instanceVariableList) {
      this.instanceVariableList = instanceVariableList;
   }

   public void addInstanceVariable(InstanceVariable instanceVariable){
      this.instanceVariableList.addElement(instanceVariable);
   }

   public PublicMethodList getPublicMethodList() {
      return publicMethodList;
   }

   public void setPublicMethodList(PublicMethodList publicMethodList) {
      this.publicMethodList = publicMethodList;
   }

   public void addPublicMethod(Method method){
      this.publicMethodList.addElement(method);
   }

   public PrivateMethodList getPrivateMethodList() {
      return privateMethodList;
   }

   public void setPrivateMethodList(PrivateMethodList privateMethodList) {
      this.privateMethodList = privateMethodList;
   }

   public void addPrivateMethod(Method method){
      this.privateMethodList.addElement(method);
   }


}