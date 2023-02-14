public aspect PermissionCheckAspect { 

	private pointcut permissionCheckedExecution() :
		(execution(public * Account.*(..)) && !execution(String Account.toString())) 
		&& within(Account);

	before() : permissionCheckedExecution() {
		System.out.print("[Aspect] ");
		AccessController.checkPermission(new BankingPermission(thisJoinPoint.getSignature().toShortString()));
	} 

	declare warning:
		call(void AccessController.checkPermission(java.security.Permission)) 
		&& within(Account) && !within(PermissionCheckAspect) :
			"Do not call AccessController.checkPermission(..) from Account";
}
