package ast;

public class MessageSendStatement extends Statement { 


   public void genC( PW pw ) {
      pw.printIdent("");
      // messageSend.genC(pw);
      pw.println(";");
   }

   private MessageSend  messageSend;

   
/*
* 
* messageSend;
* 
*/
	@Override
	public void genKra(PW pw) {
		pw.printIdent("");
		this.messageSend.genKra(pw, false);
		pw.println(";");
	}

}


