package ast;

/*
 * Krakatoa Class
 */
public class KraClass extends Type {

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

   public int validateProgram(){
      if (!this.getName().equals("Program")){
         return 1;
      }
      Method run = this.getNameMethod("run");
      if (run == null) {
         return 2;
      }
      return 0;
   }

   public boolean isStatic() {
      return isStatic;
   }

   public boolean isFinal() {
      return isFinal;
   }

   public Type getMethodType(String methodName, TypeList typeList, boolean superclass){
      Method method = null;
      if (!superclass) {
         method = privateMethodList.search(methodName, typeList, isStatic);
      }
      if (method == null){
         method = publicMethodList.search(methodName, typeList, isStatic);
      }
      return method.getType();
   }

   public Method getMethodDeclaration(Type type, String name, TypeList typelist){
      return publicMethodList.searchDeclaration(type, name, typelist, false, true);
   }

   public Method getMethod(String name, TypeList typelist){
      return publicMethodList.search(name, typelist, false);
   }

   public Method getNameMethod (String run){
      Method method = (privateMethodList.searchNameMethod(run));
      if (method == null){
         method = publicMethodList.searchNameMethod(run);
      }
      return method;
   }

   public Method getMethodFromThis(String name, TypeList typelist){
      Method method = (privateMethodList.search(name, typelist, false));
      if (method == null){
         method = publicMethodList.search(name, typelist, false);
      }
      return method;
   }

   public KraClass searchMethods(String name, TypeList typeList, boolean isStatic, boolean superclass){

      if(!superclass){
         if (privateMethodList.search(name,typeList, isStatic) != null){
            return this;
         }
      }
      if (publicMethodList.search(name, typeList, isStatic) != null){
         return this;
      }
      if (this.superClass != null && !isStatic){
         return this.superClass.searchMethods(name, typeList, isStatic, true);
      }
      return null;
   }

   public KraClass searchDeclarationMethods(Type type, String name, TypeList typeList, boolean isStatic, boolean superclass){
      if(!superclass){
         if (privateMethodList.searchDeclaration(type, name, typeList, isStatic, superclass) != null){
            return this;
         }
      }
      if (publicMethodList.searchDeclaration(type, name, typeList, isStatic, superclass) != null){
         return this;
      }
      if (this.superClass != null && !isStatic){
         return this.superClass.searchDeclarationMethods(type, name, typeList, isStatic, true);
      }
      return null;
   }

   public boolean compareCurrentMethod(String name, TypeList typeList, boolean isStatic,Method currentMethod){

      if (currentMethod.getName().equals(name)){
         if (isStatic && currentMethod.isStatic()){
            return true;
         }else if (typeList.compareTo(currentMethod.getParamList().getTypeList()) == 0) {
            return true;
         }
      }
      return false;
   }

   public boolean searchSuperClassName (String name){
      if (superClass != null && superClass.getName().equals(name)){
         return true;
      }else if (superClass != null){
         return superClass.searchSuperClassName(name);
      }else{
         return false;
      }
   }

   public boolean searchSuperClass (KraClass suposedSuperClass){
      if (this.superClass != null){
         if (superClass.getName().equals(suposedSuperClass.getName())){
            return true;
         }else{
            return searchSuperClass(this.superClass);
         }
      }
      return false;
   }

   public String getCname() {
      return getName();
   }

   @Override
   public String getName() {
      return super.getName();
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

   public InstanceVariableList getInstanceVariableList() {
      return instanceVariableList;
   }

   public void setInstanceVariableList(InstanceVariableList instanceVariableList) {
      this.instanceVariableList = instanceVariableList;
   }

   public void addInstanceVariableList(InstanceVariableList instanceVariableList){
      this.instanceVariableList.addInstanceVariableList(instanceVariableList);
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
   
   public void genKra(PW pw){
	   if(this.isFinal)
		   pw.print("final ");
	   if(this.isStatic)
		   pw.print("static ");
	   
	   pw.print("class ");
	   pw.print(this.getName());
	   pw.println(" {");
	   pw.add();
	   this.instanceVariableList.genKra(pw);
	   this.privateMethodList.genKra(pw);
	   this.publicMethodList.genKra(pw);
	   pw.sub();
	   pw.println("}");
	   pw.println("");
   }


}
