import java.security.Permission;

public class BankingPermission extends Permission{

	public BankingPermission(String str){
		super(str);
	}

	@Override
	public int hashCode(){
		return 0;
	}

	public String getActions(){
		return "getActions";
	}

	public boolean implies(Permission permission){
		return false;
	}

	@Override
	public boolean equals(Object obj){
		return false;
	}

}