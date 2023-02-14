public aspect MyAspect{

	private int MyClass.count = 0;

	public void MyClass.increment(){
		count++;
	}

	public int MyClass.getCount(){
		return count;
	}
}